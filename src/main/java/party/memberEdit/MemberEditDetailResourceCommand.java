package party.memberEdit;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;
import hg.party.server.login.UserService;
import hg.party.server.member.MemberEditService;
import hg.party.server.org.MemberService;
import hg.party.server.organization.OrgAdminService;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
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
                "javax.portlet.name=" + PartyPortletKeys.MemberEditPortlet,
                "mvc.command.name=/hg/memberEdit/detail"
        },
        service = MVCResourceCommand.class
)
public class MemberEditDetailResourceCommand implements MVCResourceCommand {
    @Reference
    private MemberEditService memberEditService;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
        int memberEditId = ParamUtil.getInteger(resourceRequest, "id");
        PrintWriter printWriter = null;
        try {
            printWriter = resourceResponse.getWriter();
            if(!StringUtils.isEmpty(memberEditId)){
                MemberEdit memberEdit = memberEditService.findMemberEditDetailById(memberEditId);
                if(memberEdit == null ){
                    printWriter.write(JSON.toJSONString(ResultUtil.fail("操作数据不存在！")));
                }else{
                    printWriter.write(JSON.toJSONString(ResultUtil.success(memberEdit)));
                }

            }else{
                printWriter.write(JSON.toJSONString(ResultUtil.fail("id不能为空！")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
