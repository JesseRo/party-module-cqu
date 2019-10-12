package party.portlet.report.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.util.CollectionUtils;
import hg.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
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

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


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
        String json = task.getFiles();
        try
        {
            List<ExcelHandler> allMerged = new ArrayList<>();
            List<ExcelHandler> demoExcels = gson.fromJson(json, new TypeToken<List<ExcelHandler>>(){}.getType());
            try {
                outer:
                for (ExcelHandler demoExcel: demoExcels){
                    ExcelHandler merged = ExcelHandler.empty(demoExcel.getFileName());
                    for (Report report : reports){
                        List<ExcelHandler> reportExcels =
                                gson.fromJson(report.getFiles(), new TypeToken<List<ExcelHandler>>(){}.getType());

                        ExcelHandler curExcel = reportExcels.stream()
                                .filter(p->p.getFileName().equals(demoExcel.getFileName())).findFirst().orElse(null);
                        if (curExcel == null){
                            throw new NotMatchingExcelDataException(String.format("%s上报文件有误。", ""));
                        }
                        boolean reportLegal = CollectionUtils.isEquals(curExcel.getSheetNames(), demoExcel.getSheetNames());
                        if (!reportLegal){
                            throw new NotMatchingExcelDataException(String.format("%s上报文件:%s格式有误。", "", ""));
                        }
                        merged = merged.merge(curExcel);

                    }
                    allMerged.add(merged);
                }
            }catch (NotMatchingExcelDataException e ){
                e.printStackTrace();
                return false;
            }

            Workbook workbook = ExcelUtil.exportExcelX(null, allMerged.get(0).getAll(), "yyyy-MM-dd", 0);
            String filename = allMerged.get(0).getFileName();
            HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);

            res.setContentType("application/vnd.ms-excel;charset=utf-8");

            res.addHeader("Content-Disposition",
                    "attachment; filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));

            workbook.write(res.getOutputStream());
            workbook.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }


}
