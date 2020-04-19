package party.memberEdit;

import com.alibaba.fastjson.JSON;
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


    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
        int memberEditId = ParamUtil.getInteger(resourceRequest, "id");
        int status = ParamUtil.getInteger(resourceRequest, "status");
        String reason = ParamUtil.getString(resourceRequest, "reason");
        PrintWriter printWriter = null;
        try {
            printWriter = resourceResponse.getWriter();
            if(!StringUtils.isEmpty(memberEditId)){
                MemberEdit memberEdit = memberEditService.findById(memberEditId);
                if(memberEdit == null ){
                    printWriter.write(JSON.toJSONString(ResultUtil.fail("操作数据不存在！")));
                }else{
                    boolean result = false;
                    transactionUtil.startTransaction();
                    try {
                        switch (status) {
                            case ConstantsKey.APPROVED:
                                result = approveMemberEdit(memberEdit, printWriter, null);
                                break;
                            case ConstantsKey.REJECTED:
                                result = rejectMemberEdit(memberEdit, printWriter, reason);
                                break;
                        }
                        if (result) {
                            transactionUtil.commit();
                        } else {
                            transactionUtil.rollback();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        transactionUtil.rollback();
                        printWriter.write(JSON.toJSONString(ResultUtil.fail("请求异常。")));
                    }

                }

            }else{
                printWriter.write(JSON.toJSONString(ResultUtil.fail("id不能为空！")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            printWriter.write(JSON.toJSONString(ResultUtil.fail("请求异常。")));
        }
        return false;
    }

    private boolean approveMemberEdit(MemberEdit memberEdit,PrintWriter printWriter,String reason) {
        int ret =memberEditService.approvalMemberEdit(memberEdit.getId(),ConstantsKey.APPROVED,reason);
        if (ret>0) {
            User user= userService.findById(memberEdit.getSubmit_by());
            //原党员信息
            Member member = memberService.findMemberByEditSubmitBy(memberEdit.getSubmit_by());
            boolean isUpdate = true;
            String message = null;
            if(user!=null){
                //判断是否修改身份证信息
                //身份信息唯一，不与他人重复
                if(!user.getUser_id().equals(memberEdit.getMember_identity())){
                    User user_o = userService.findByUserId(memberEdit.getMember_identity());
                    if(user_o != null){//已存在身份证号
                        isUpdate = false;
                        message = "身份证信息已经被其他用户使用。";
                    }else{
                        if(member!=null){
                            //库中他人同身份证党员信息
                            //身份信息唯一，不与他人重复
                            Member member_o = memberService.findMemberByIdentity(memberEdit.getMember_identity());
                            if(member_o!=null && member_o.getId()!=member.getId()){
                                isUpdate = false;
                                message = "身份证信息已经被其他党员使用。";
                            }
                        }else{
                            isUpdate = false;
                            message = "党员信息不存在。";
                        }

                    }

                }

                if(isUpdate){
                    User updateUser = memberEdit.toUser();
                    updateUser.setId(user.getId());
                    userService.updateUserInfo(updateUser);
                    orgAdminService.updateUserInfo(user.getUser_id(),memberEdit.getMember_identity());
                    Member updateMember = memberEdit.toMember();
                    updateMember.setId(member.getId());
                    memberService.updateMember(updateMember);
                    printWriter.write(JSON.toJSONString(ResultUtil.success(ret)));
                    return true;
                }else{
                    printWriter.write(JSON.toJSONString(ResultUtil.fail(message)));
                    return false;
                }

            }else{
                printWriter.write(JSON.toJSONString(ResultUtil.fail("用户信息不存在。")));
                return false;
            }
        }else{
            printWriter.write(JSON.toJSONString(ResultUtil.fail("审批失败，请刷新再试。")));
            return false;
        }
    }
    private boolean rejectMemberEdit(MemberEdit memberEdit,PrintWriter printWriter,String reason) {

        int ret =memberEditService.approvalMemberEdit(memberEdit.getId(),ConstantsKey.REJECTED,reason);
        if (ret>0) {
            printWriter.write(JSON.toJSONString(ResultUtil.success(ret)));
            return true;
        }else{
            printWriter.write(JSON.toJSONString(ResultUtil.fail("审批失败，请刷新再试。")));
            return false;
        }
    }

}
