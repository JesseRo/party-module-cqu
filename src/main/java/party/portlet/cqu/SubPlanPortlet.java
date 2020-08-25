package party.portlet.cqu;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.downlistdao.DownListDao;
import hg.party.dao.org.MemberDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.Hg_Value_Attribute_Info;
import hg.party.entity.party.MeetingPlan;
import hg.party.entity.partyMembers.Member;
import hg.party.server.organization.OrgService;
import hg.party.server.partyBranch.PartyBranchService;
import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static party.constants.PartyMeetingConst.*;

/**
 * @author jesse
 * @Filename MeetingPlanPortlet
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/2/22</li>
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.cqu",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=拟定计划",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.portlet-mode=text/html;view,edit",
                "javax.portlet.init-param.view-template=/jsp/partyBranch/sconedParty.jsp",
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SubPlanPortlet extends MVCPortlet {
    Logger logger = Logger.getLogger(SubPlanPortlet.class);

    private Gson gson = new Gson();
    @Reference
    private OrgService orgService;

    @Reference
    private MemberDao memberDao;

    @Reference
    private DownListDao downListDao;
    @Reference
    private PartyBranchService partyBranchService;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String sessionId=renderRequest.getRequestedSessionId();
        String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
        String role =	SessionManager.getAttribute(sessionId, "role").toString();//用户选中角色
        String meetingId = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest)).getParameter("meetingId");
        PartyOrgAdminTypeEnum orgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnumByRole(role);
        Organization organization = new Organization() ;
        MeetingPlan meetingPlan = new MeetingPlan();
        List<Map<String,Object>> members = new ArrayList<>();
        List<Map<String, Object>> speakers = Collections.emptyList();
        if(orgAdminTypeEnum!=null){
            organization = orgService.findAdminOrg(userId, orgAdminTypeEnum);
            members = orgService.findMembersByOrg(organization.getOrg_id(),orgAdminTypeEnum);
            if(!StringUtils.isEmpty(meetingId)){
                meetingPlan = partyBranchService.findMeetingPlan(meetingId);
            }else{
                meetingPlan = partyBranchService.findNoSubmitPlan(userId,organization.getOrg_id());
            }
            if(meetingPlan.getMeeting_id() != null){
                List<Map<String, Object>> memberList = partyBranchService.findMeetingMember(meetingPlan.getMeeting_id());
                String participate = "";
                if(memberList.size() > 0){
                    for(int i=0;i<memberList.size();i++){
                        if(i==0){
                            participate = participate + memberList.get(i).get("participant_id");
                        }else{
                            participate = participate +","+ memberList.get(i).get("participant_id");
                        }
                    }
                }
                renderRequest.setAttribute("participate", participate);
                speakers = partyBranchService.getMeetingSpeak(meetingPlan.getMeeting_id());
            }
        }
        renderRequest.setAttribute("speaks", gson.toJson(speakers));

        List<Map<String, Object>> leaders = memberDao.leaders();

        logger.info("members size:"+members.size());
        List<Hg_Value_Attribute_Info> meetingTypes = downListDao.meetType();
        renderRequest.setAttribute("organization",organization);
        renderRequest.setAttribute("meetingPlan",meetingPlan);
        renderRequest.setAttribute("conferenceTypes",meetingTypes);
        renderRequest.setAttribute("timeLasts",TIME_LASTS);
        renderRequest.setAttribute("campus",CAMPUS);
        renderRequest.setAttribute("members", members);
        renderRequest.setAttribute("leaders", leaders);
        Date now = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        renderRequest.setAttribute("now", sdf.format(now));
        super.doView(renderRequest, renderResponse);
    }
}
