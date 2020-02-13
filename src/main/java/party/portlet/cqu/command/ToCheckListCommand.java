package party.portlet.cqu.command;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.entity.partyMembers.JsonPageResponse;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.log.LogDao;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.PartyEntryOrg,
                "javax.portlet.name=" + PartyPortletKeys.PersonalCenter,
                "mvc.command.name=/hg/personalCheck/page"
        },
        service = MVCResourceCommand.class
)
public class ToCheckListCommand implements MVCResourceCommand {
    @Reference
    private LogDao logDao;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        int page = ParamUtil.getInteger(resourceRequest, "page");
        int size = ParamUtil.getInteger(resourceRequest, "limit");

        String userId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");

        String sql = "SELECT plan.meeting_id as meeting,plan.start_time AS start_p,* FROM "+
                "(hg_party_meeting_plan_info AS plan "+
                "LEFT JOIN hg_party_meeting_notes_info AS notes "+
                "ON plan.meeting_id=notes.meeting_id) "+
                "LEFT JOIN hg_party_org AS org "+
                "ON plan.organization_id=org.org_id "+
                "WHERE plan.check_person=? "+
                "and org.historic is false "+
                "AND (plan.task_status='5' "+
                "OR plan.task_status='7') "+
                "ORDER BY plan.id desc ";
        PostgresqlQueryResult<Map<String, Object>> data = logDao.postGresqlFindBySql(page, size, sql, userId);

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type","application/json");
        try {
            JsonPageResponse jsonPageResponse = new JsonPageResponse();
            if (data != null){
                jsonPageResponse.setCode(0);
                jsonPageResponse.setCount(data.getTotalPage() * size);
                jsonPageResponse.setData(data.getList());
            }else {
                jsonPageResponse.setCode(0);
                jsonPageResponse.setCount(0);
                jsonPageResponse.setData(Collections.emptyList());
            }
            res.getWriter().write(gson.toJson(jsonPageResponse));
        } catch (Exception e) {
            try {
                e.printStackTrace();
                res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
}
