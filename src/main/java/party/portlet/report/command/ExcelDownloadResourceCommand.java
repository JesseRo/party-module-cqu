package party.portlet.report.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.unity.ResourceProperties;
import hg.party.unity.WordUtils;
import hg.util.CollectionUtils;
import hg.util.ConstantsKey;
import hg.util.ExcelOperationUtil;
import hg.util.ExcelUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.org.NotMatchingExcelDataException;
import party.portlet.report.entity.view.ExcelHandler;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.dao.ReportTaskOrgDao;
import party.portlet.report.entity.Report;
import party.portlet.report.entity.ReportTask;
import party.portlet.report.entity.view.FileHandler;
import party.portlet.report.entity.view.FileView;
import party.portlet.report.entity.view.WordHandler;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Component(
        immediate = true,
        property = {
//                "javax.portlet.name=" + PartyPortletKeys.BrunchReportPortlet,
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskReportPortlet,
                "mvc.command.name=/brunch/report/download"
        },
        service = MVCResourceCommand.class
)
public class ExcelDownloadResourceCommand implements MVCResourceCommand {
    private static final int BUFFER_SIZE = 4 * 1024;
    @Reference
    private ReportTaskDao reportTaskDao;

    @Reference
    private ReportTaskOrgDao reportTaskOrgDao;
    @Reference
    private ReportDao reportDao;
    @Reference
    private OrgDao orgDao;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String sessionId = resourceRequest.getRequestedSessionId();
        String department = SessionManager.getAttribute(sessionId, "department").toString();
        String taskId = ParamUtil.getString(resourceRequest, "taskId");

        ReportTask task = reportTaskDao.findByTaskId(taskId);
        List<Report> reports = reportDao.findByTaskId(taskId);


        String type = task.getType();
        List<Workbook> allMerged = new ArrayList<>();
        List<String> allMergedNames = new ArrayList<>();
        try {
            if (type.equalsIgnoreCase(FileView.EXCEL) || type.equalsIgnoreCase(FileView.BOTH)) {
                String json = task.getFiles();
                List<ExcelHandler> demoExcels = gson.fromJson(json, new TypeToken<List<ExcelHandler>>() {
                }.getType());
                try {
                    outer:
                    for (ExcelHandler demoExcel : demoExcels) {
                        Workbook dest = new SXSSFWorkbook();
                        allMerged.add(dest);
                        String fullName = demoExcel.getFileName();
                        if (fullName.contains(".")) {
                            allMergedNames.add(fullName.substring(0, fullName.lastIndexOf(".")));
                        } else {
                            allMergedNames.add(fullName);
                        }
                        for (Report report : reports) {
                            if (report.getStatus() == ConstantsKey.APPROVED) {
                                List<ExcelHandler> reportExcels =
                                        gson.fromJson(report.getFiles(), new TypeToken<List<ExcelHandler>>() {
                                        }.getType());

                                ExcelHandler curExcel = reportExcels.stream()
                                        .filter(p -> p.getFileName().equals(demoExcel.getFileName())).findFirst().orElse(null);
                                if (curExcel == null) {
                                    throw new NotMatchingExcelDataException(String.format("%s上报文件有误。", ""));
                                }
                                boolean reportLegal = CollectionUtils.isEquals(curExcel.getSheetNames(), demoExcel.getSheetNames());
                                if (!reportLegal) {
                                    throw new NotMatchingExcelDataException(String.format("%s上报文件:%s格式有误。", "", ""));
                                }
                                ExcelOperationUtil.mergeByAppending(demoExcel, curExcel, dest);
                            }
                        }
                    }
                } catch (NotMatchingExcelDataException | InvalidFormatException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            List<WordHandler> allMergedWord = new ArrayList<>();
            if (type.equalsIgnoreCase(FileView.WORD) || type.equalsIgnoreCase(FileView.BOTH)) {
                String json = task.getWord_files();
                List<WordHandler> demoWords = gson.fromJson(json, new TypeToken<List<WordHandler>>() {
                }.getType());
                for (WordHandler demoWord : demoWords) {
                    WordHandler merged = WordHandler.empty(demoWord.getFileName() + "-汇总.docx");
                    for (Report report : reports) {
                        if (report.getStatus() == ConstantsKey.APPROVED) {
                            List<WordHandler> reportWords =
                                    gson.fromJson(report.getWord_files(), new TypeToken<List<WordHandler>>() {
                                    }.getType());

                            WordHandler curWord = reportWords.stream()
                                    .filter(p -> p.getFileName().equals(demoWord.getFileName())).findFirst().orElse(null);
                            if (curWord == null) {
                                throw new NotMatchingExcelDataException(String.format("%s上报文件有误。", ""));
                            }
                            merged = merged.merge(curWord);
                        }
                    }
                    allMergedWord.add(merged);
                }
            }
            HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
            if (allMerged.size() + allMergedWord.size() == 1) {
                if (allMerged.size() > 0) {
                    Workbook workbook = allMerged.get(0);
                    String filename = allMergedNames.get(0).concat("-汇总.xlsx");
                    res.setContentType("application/vnd.ms-excel;charset=utf-8");
                    res.addHeader("Content-Disposition",
                            "attachment; filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                    workbook.write(res.getOutputStream());
                    workbook.close();
                } else {
                    WordHandler wordHandler = allMergedWord.get(0);
                    XWPFDocument document = WordUtils.export(wordHandler);
                    String fullName = wordHandler.getFileName();
                    String filename = fullName.contains(".") ? fullName.substring(0, fullName.lastIndexOf(".")) : fullName;
                    filename = filename.concat("-汇总.docx");
                    res.setContentType("application/msword;charset=utf-8");
                    res.addHeader("Content-Disposition",
                            "attachment; filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                    document.write(res.getOutputStream());
                    document.close();
                }
            } else {
                res.setContentType("application/zip;charset=utf-8");
                res.addHeader("Content-Disposition",
                        "attachment; filename=" + new String("数据汇总.zip".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));

                ResourceProperties resourceProperties = new ResourceProperties();
                Properties properties = resourceProperties.getResourceProperties();//获取配置文件
                String uploadPath = properties.getProperty("uploadPath");
                File folder = new File(uploadPath + "/ajaxFileName/" + taskId);
//                File zip = new File(folder, "汇总.zip");
                ZipOutputStream zipOutputStream = new ZipOutputStream(res.getOutputStream());
//                if (!zip.exists()) {
//                    zip.createNewFile();
//                }
                for (int i = 0; i < allMerged.size(); i++) {
                    Workbook workbook = allMerged.get(i);
                    String filename = allMergedNames.get(i) + ".xlsx";
                    File merged = new File(folder, filename);
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(folder, filename));
                    workbook.write(fileOutputStream);
                    workbook.close();
                    FileInputStream in = new FileInputStream(merged);
                    zipOutputStream.putNextEntry(new ZipEntry(filename));
                    byte[] buf = new byte[BUFFER_SIZE];
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        zipOutputStream.write(buf, 0, len);
                    }
                    zipOutputStream.closeEntry();
                    in.close();
                }
                for (WordHandler word : allMergedWord) {
                    XWPFDocument document = WordUtils.export(word);
                    String filename = word.getFileName();
                    File merged = new File(folder, filename);
                    FileOutputStream fileOutputStream = new FileOutputStream(new File(folder, word.getFileName()));
                    document.write(fileOutputStream);
                    fileOutputStream.close();
                    document.close();
                    FileInputStream in = new FileInputStream(merged);
                    zipOutputStream.putNextEntry(new ZipEntry(filename));
                    byte[] buf = new byte[BUFFER_SIZE];
                    int len;
                    while ((len = in.read(buf)) != -1) {
                        zipOutputStream.write(buf, 0, len);
                    }
                    zipOutputStream.closeEntry();
                    in.close();
                }
                zipOutputStream.close();
//                FileInputStream fileInputStream = new FileInputStream(zip);
//                byte[] buf = new byte[BUFFER_SIZE];
//                int len;
//                while ((len = fileInputStream.read(buf)) != -1) {
//                    res.getOutputStream().write(buf, 0, len);
//                }
//                fileInputStream.close();
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return false;
    }
}
