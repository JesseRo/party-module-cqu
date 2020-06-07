package party.portlet.cqu.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.server.partyBranch.PartyBranchService;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.CheckPersonDao;
import party.portlet.cqu.entity.CheckPerson;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "mvc.command.name=/hg/groupAndMember"
        },
        service = MVCResourceCommand.class
)
public class GroupAndMemberCommand implements MVCResourceCommand {
    @Reference
    PartyBranchService service;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
        List<Map<String, Object>> groups = service.getGroupsMap(orgId);
        List<String > groupsIds = groups.stream().map(p->(String)p.get("group_id")).collect(Collectors.toList());
        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type", "application/json");
        try {
            List<Map<String, Object>> groupMemberResponse = new ArrayList<>();
            if(!groups.isEmpty()){
                List<Map<String, Object>> groupMembers = service.getGroupMembers(groupsIds);
                Map<String, List<Map<String, Object>>> map = groupMembers.stream()
                        .collect(Collectors.groupingBy(p -> (String) p.get("group_id")));
                for (Map<String, Object> group : groups) {
                    Map<String, Object> groupMap = new HashMap<>();
                    groupMap.put("id", group.get("group_id"));
                    groupMap.put("name", group.get("group_name"));
                    groupMap.put("member", map.getOrDefault(group.get("group_id"), Collections.emptyList()));
                    groupMemberResponse.add(groupMap);
                }
            }
            res.getWriter().write(gson.toJson(JsonResponse.Success(groupMemberResponse)));
        } catch (Exception e) {
            try {
                e.printStackTrace();
                res.getWriter().write(gson.toJson(JsonResponse.Failure("失败")));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
}
