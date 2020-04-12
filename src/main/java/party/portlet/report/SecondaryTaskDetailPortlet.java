package party.portlet.report;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.entity.ReportTask;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=二级党委--新建数据上报任务详情",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/report/task_detail.jsp",
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskDetailPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SecondaryTaskDetailPortlet extends MVCPortlet {
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
        }else {
            renderRequest.setAttribute("task", Collections.emptyMap());
        }

        String formId = UUID.randomUUID().toString();
        SessionManager.setAttribute(servletRequest.getRequestedSessionId(), "formId-report-task", formId);
        renderRequest.setAttribute("formId", formId);
        super.doView(renderRequest, renderResponse);
    }


}
