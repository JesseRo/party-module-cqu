package party.portlet.report.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
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
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.dao.ReportTaskOrgDao;
import party.portlet.report.entity.Report;
import party.portlet.report.entity.ReportTask;
import party.portlet.report.entity.view.ExcelHandler;
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
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Component(
        immediate = true,
        property = {
//                "javax.portlet.name=" + PartyPortletKeys.BrunchReportPortlet,
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskReportPortlet,
                "mvc.command.name=/brunch/report/download/sheet"
        },
        service = MVCResourceCommand.class
)
public class ExcelMergeAsSheetResourceCommand implements MVCResourceCommand {
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

        List<Organization> organizations = orgDao.findByOrgId(reports.stream().map(Report::getOrg_id).collect(Collectors.toList()));
        Map<String, Organization> orgMap = organizations.stream().collect(Collectors.toMap(Organization::getOrg_id, p->p));
        String json = task.getFiles();
        String type = task.getType();

        if (type.equalsIgnoreCase(FileView.EXCEL)) {
            try {
                List<Workbook> allMerged = new ArrayList<>();
                List<String> allMergedNames = new ArrayList<>();
                List<ExcelHandler> demoExcels = gson.fromJson(json, new TypeToken<List<ExcelHandler>>() {
                }.getType());
                try {
                    outer:
                    for (ExcelHandler demoExcel : demoExcels) {
                        Workbook dest = new SXSSFWorkbook();
                        allMerged.add(dest);
                        String fullName = demoExcel.getFileName();
                        if (fullName.contains(".")) {
                            allMergedNames.add(fullName.substring(0, fullName.indexOf(".")));
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
                                ExcelOperationUtil.mergeByMultipleSheet(orgMap.get(report.getOrg_id()).getOrg_name(), curExcel, dest);
                            }
                        }
                    }
                } catch (NotMatchingExcelDataException | InvalidFormatException e) {
                    e.printStackTrace();
                    return false;
                }
                HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);

                if (allMerged.size() == 1) {
                    Workbook workbook = allMerged.get(0);
                    String filename = allMergedNames.get(0).concat("-汇总.xlsx");;
                    res.setContentType("application/vnd.ms-excel;charset=utf-8");
                    res.addHeader("Content-Disposition",
                            "attachment; filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                    workbook.write(res.getOutputStream());
                    workbook.close();
                } else {
                    ResourceProperties resourceProperties = new ResourceProperties();
                    Properties properties = resourceProperties.getResourceProperties();//获取配置文件
                    String uploadPath = properties.getProperty("uploadPath");
                    File folder = new File(uploadPath + "/ajaxFileName/" + taskId);
                    File zip = new File(folder, "汇总.zip");
                    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zip));
                    if (!zip.exists()) {
                        zip.createNewFile();
                    }
                    for (int i = 0; i < allMerged.size(); i++) {
                        Workbook workbook = allMerged.get(i);
                        String filename = allMergedNames.get(i) + ".xlsx";
                        File merged = new File(folder, filename);
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(folder, filename));
                        workbook.write(fileOutputStream);
                        fileOutputStream.close();
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

                        res.setContentType("application/zip;charset=utf-8");
                        res.addHeader("Content-Disposition",
                                "attachment; filename=" + new String("数据汇总.zip".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                    }
                    zipOutputStream.close();
                    FileInputStream fileInputStream = new FileInputStream(zip);
                    byte[] buf = new byte[BUFFER_SIZE];
                    int len;
                    while ((len = fileInputStream.read(buf)) != -1) {
                        res.getOutputStream().write(buf, 0, len);
                    }
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
