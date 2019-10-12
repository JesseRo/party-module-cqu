package party.portlet.report;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.persistence.PortletUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.entity.Report;
import party.portlet.report.entity.ReportTask;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=二级党委--新建数据上报任务",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/report/new_task.jsp",
                "javax.portlet.name=" + PartyPortletKeys.SecondaryNewTaskPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SecondaryNewTaskPortlet extends MVCPortlet {
    //上级组织新任务
    @Reference
    private ReportDao reportDao;

    @Reference
    private ReportTaskDao reportTaskDao;

    @Reference
    private OrgDao orgDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
        HttpServletRequest servletRequest = PortalUtil.getOriginalServletRequest(request);
        String taskId = servletRequest.getParameter("taskId");
        if (!StringUtils.isEmpty(taskId)){
            ReportTask task =  reportTaskDao.findByTaskId(taskId);
            renderRequest.setAttribute("task", task);
        }

        String formId = UUID.randomUUID().toString();
        SessionManager.setAttribute(servletRequest.getRequestedSessionId(), "formId-report-task", formId);
        renderRequest.setAttribute("formId", formId);
        super.doView(renderRequest, renderResponse);
    }


}
