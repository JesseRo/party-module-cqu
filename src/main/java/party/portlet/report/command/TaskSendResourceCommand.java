package party.portlet.report.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.dao.ReportTaskOrgDao;
import party.portlet.report.entity.Report;
import party.portlet.report.entity.ReportOrgTask;
import party.portlet.report.entity.ReportTask;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskDraftPortlet,
                "mvc.command.name=/hg/report/send"
        },
        service = MVCResourceCommand.class
)
public class TaskSendResourceCommand implements MVCResourceCommand {
    @Reference
    private ReportTaskDao taskDao;
    @Reference
    TransactionUtil transactionUtil;
    @Reference
    private ReportTaskOrgDao reportTaskOrgDao;
    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String taskId = ParamUtil.getString(resourceRequest, "task");
        String[] taskIds = taskId.split(",");

        transactionUtil.startTransaction();
        try {
            for (String id : taskIds){
                ReportTask reportTask = taskDao.findByTaskId(id);
                if (reportTask != null && reportTask.getStatus() == ConstantsKey.DRAFT){
                    reportTask.setStatus(ConstantsKey.PUBLISHED);
                    taskDao.saveOrUpdate(reportTask);
                    List<ReportOrgTask> orgTasks = new ArrayList<>();
                    for (String org: reportTask.getReceivers().split(",")){
                        ReportOrgTask reportOrgTask = new ReportOrgTask();
                        reportOrgTask.setOrg_id(org);
                        reportOrgTask.setStatus(ConstantsKey.UNREPORTED);
                        reportOrgTask.setTask_id(taskId);
                        orgTasks.add(reportOrgTask);
                    }
                    reportTaskOrgDao.saveAll(orgTasks);
                }
            }
            transactionUtil.commit();
        }catch (Exception e){
            e.printStackTrace();
            transactionUtil.rollback();
        }
        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        try {
            res.getWriter().write("{\"success\": true}");
            res.setHeader("content-type", "application/json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
