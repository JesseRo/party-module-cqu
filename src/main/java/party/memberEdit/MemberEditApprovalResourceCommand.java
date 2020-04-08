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

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.MemberEditPortlet,
                "mvc.command.name=/hg/memberEdit/approval"
        },
        service = MVCResourceCommand.class
)
public class MemberEditApprovalResourceCommand implements MVCResourceCommand {
    @Reference
    private MemberEditService memberEditService;
    @Reference
    TransactionUtil transactionUtil;
    @Reference
    UserService userService;
    @Reference
    MemberService memberService;

    @Reference
    OrgAdminService orgAdminService;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
        int memberEditId = ParamUtil.getInteger(resourceRequest, "id");
        int status = ParamUtil.getInteger(resourceRequest, "status");
        Object userId = SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");
        PrintWriter printWriter = null;
        transactionUtil.startTransaction();
        try {
            printWriter = resourceResponse.getWriter();
            if(!StringUtils.isEmpty(memberEditId)){
                MemberEdit memberEdit = memberEditService.findById(memberEditId);
                if(memberEdit == null ){
                    printWriter.write(JSON.toJSONString(ResultUtil.fail("操作数据不存在！")));
                }else{
                    String reason = null;
                    if (status == ConstantsKey.REJECTED){
                        reason = ParamUtil.getString(resourceRequest, "reason");
                    }
                    int ret = memberEditService.approvalMemberEdit(memberEditId,status,reason);
                    if (ret>0){
                        if(status == 1){//审批通过，修改用户信息
                            Member member = memberService.findMemberByUser(String.valueOf(userId));
                            if(member!=null){
                                Member updateMember = memberEdit.toMember();
                                updateMember.setId(member.getId());
                                memberService.updateMember(member);
                            }
                            User user = userService.findByUserId(String.valueOf(userId));
                            if(user!=null){
                                User updateUser = memberEdit.toUser();
                                updateUser.setId(user.getId());
                                userService.updateUserInfo(updateUser);
                                if(!user.getUser_id().equals(memberEdit.getMember_identity())){
                                    orgAdminService.updateUserInfo(user.getUser_id(),memberEdit.getMember_identity());
                                }
                            }

                        }
                        transactionUtil.commit();
                        printWriter.write(JSON.toJSONString(ResultUtil.success(ret)));
                    }else {
                        transactionUtil.rollback();
                        printWriter.write(JSON.toJSONString(ResultUtil.fail("失败")));
                    }
                }

            }else{
                printWriter.write(JSON.toJSONString(ResultUtil.fail("id不能为空！")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            transactionUtil.rollback();
        }
        return false;
    }
}
