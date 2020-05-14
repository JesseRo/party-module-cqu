package party.portlet.report;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.dao.ReportTaskOrgDao;
import party.portlet.report.entity.ReportTask;
import party.portlet.report.entity.view.ExcelHandler;
import party.portlet.report.entity.view.FileView;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=支部数据上报",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/report/brunch.jsp",
                "javax.portlet.name=" + PartyPortletKeys.BrunchReportPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class BrunchReportPortlet extends MVCPortlet {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Reference
    private ReportTaskDao taskDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
        HttpServletRequest servletRequest = PortalUtil.getOriginalServletRequest(request);

        String taskId = servletRequest.getParameter("task");
        String redo = servletRequest.getParameter("redo");
        String formId = UUID.randomUUID().toString();

        String sessionId = renderRequest.getRequestedSessionId();
        SessionManager.setAttribute(sessionId, "formId-report", formId);

        ReportTask task = taskDao.findByTaskId(taskId);
        List<ExcelHandler> excelHandlers = gson.fromJson(task.getFiles(), new TypeToken<List<ExcelHandler>>(){}.getType());

        renderRequest.setAttribute("fileViews", excelHandlers);
        renderRequest.setAttribute("taskId", taskId);
        renderRequest.setAttribute("formId", formId);
        renderRequest.setAttribute("redo", redo);


        super.doView(renderRequest, renderResponse);
    }


}
