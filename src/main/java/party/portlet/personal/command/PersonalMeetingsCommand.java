package party.portlet.personal.command;

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
import party.portlet.personal.dao.PersonalDao;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.PersonalMeetingPortlet,
                "javax.portlet.name=" + PartyPortletKeys.PersonalCenter,
                "mvc.command.name=/hg/personal/page"
        },
        service = MVCResourceCommand.class
)
public class PersonalMeetingsCommand implements MVCResourceCommand {
    @Reference
    private PersonalDao personalDao;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        int page = ParamUtil.getInteger(resourceRequest, "page");
        int size = ParamUtil.getInteger(resourceRequest, "limit");
        String userId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");
        String search = ParamUtil.getString(resourceRequest, "search");
        PostgresqlQueryResult<Map<String, Object>> data;
        if (StringUtils.isEmpty(search)){
            data = personalDao.meetings(page, size, userId);
        }else {
            data = personalDao.searchMeetings(page, size, userId, search);
        }

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
