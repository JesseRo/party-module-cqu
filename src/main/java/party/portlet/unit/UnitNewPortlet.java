package party.portlet.unit;

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
                "javax.portlet.display-name=新增编辑行政机构",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/unit/new_unit.jsp",
                "javax.portlet.name=" + PartyPortletKeys.UnitNewPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class UnitNewPortlet extends MVCPortlet {
    //上级组织新任务
    @Reference
    private UnitDao unitDao;

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
        HttpServletRequest servletRequest = PortalUtil.getOriginalServletRequest(request);
        String id = servletRequest.getParameter("id");
        if (!StringUtils.isEmpty(id)){
            Unit unit =  unitDao.findById(Integer.valueOf(id));
            renderRequest.setAttribute("unit", unit);
        }else {
            renderRequest.setAttribute("unit", Collections.emptyMap());
        }

        String formId = UUID.randomUUID().toString();
        SessionManager.setAttribute(servletRequest.getRequestedSessionId(), "formId-unit", formId);
        renderRequest.setAttribute("formId", formId);
        super.doView(renderRequest, renderResponse);
    }


}
