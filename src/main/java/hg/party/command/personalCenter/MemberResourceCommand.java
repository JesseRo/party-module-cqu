package hg.party.command.personalCenter;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
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

@Component(
        immediate = true,
        property = {
                "javax.portlet.name="+ PartyPortletKeys.Form,
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "mvc.command.name=/hg/member/getMember"
        },
        service = MVCResourceCommand.class
)
public class MemberResourceCommand implements MVCResourceCommand {

    @Reference
    MemberService memberService;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
        String memberIdentity = ParamUtil.getString(resourceRequest, "memberIdentity");
        PrintWriter printWriter = null;
        try {
            printWriter = resourceResponse.getWriter();
            if(!StringUtils.isEmpty(memberIdentity)){
                Member member = memberService.findMemberByIdentity(memberIdentity);
                printWriter.write(JSON.toJSONString(ResultUtil.success(member)));
            }else{
                printWriter.write(JSON.toJSONString(ResultUtil.fail("memberIdentity不能为空！")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            printWriter.write(JSON.toJSONString(ResultUtil.fail("请求异常。")));
        }
        return false;
    }

}
