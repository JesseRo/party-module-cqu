package party.portlet.report;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.entity.Report;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=二级党委--数据上报任务草稿箱",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/report/draft.jsp",
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskDraftPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SecondaryTaskDraftPortlet extends MVCPortlet {
    //上级组织任务草稿箱
    @Reference
    private ReportTaskDao taskDao;

    @Reference
    private OrgDao orgDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        String orgId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
        int page = ParamUtil.getInteger(renderRequest, "pageNo");
        if (page <= 0 ){
            page = 1;
        }
        PostgresqlQueryResult<Map<String, Object>> taskPage = taskDao.findPageByTaskIdAndStatus(orgId, ConstantsKey.DRAFT, page);

        renderRequest.setAttribute("pageNo", taskPage.getPageNow());
        renderRequest.setAttribute("totalPage",taskPage.getTotalPage());
        renderRequest.setAttribute("tasks", taskPage.getList());
        super.doView(renderRequest, renderResponse);
    }


}
