package hg.party.command.personalCenter;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.entity.partyMembers.GroupMember;
import hg.party.entity.partyMembers.Member;
import hg.party.server.org.MemberService;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name="+ PartyPortletKeys.Form,
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "mvc.command.name=/hg/member/list"
        },
        service = MVCResourceCommand.class
)
public class MemberListResourceCommand implements MVCResourceCommand {

    @Reference
    MemberService memberService;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
        String groupId = ParamUtil.getString(resourceRequest, "groupId");
        Boolean isExist = ParamUtil.getBoolean(resourceRequest, "isExist");
        String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
        PrintWriter printWriter = null;
        try {
            printWriter = resourceResponse.getWriter();
            if(isExist){
                List<GroupMember> memberList = memberService.findMemberListByOrg(orgId,groupId);
                printWriter.write(JSON.toJSONString(ResultUtil.success(memberList)));
            }else{
                List<Member> memberList = memberService.findMemberListByOrgNotIn(orgId,groupId);
                printWriter.write(JSON.toJSONString(ResultUtil.success(memberList)));
            }


        } catch (Exception e) {
            e.printStackTrace();
            printWriter.write(JSON.toJSONString(ResultUtil.fail("请求异常。")));
        }
        return false;
    }

}
