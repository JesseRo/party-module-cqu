package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.Member;
import hg.util.TransactionUtil;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;



@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Org,
				"mvc.command.name=/org/user/recovery"
	    },
	    service = MVCResourceCommand.class
)
public class MemberRecoveryResourceCommand implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;
	@Reference
	private UserDao userDao;
	@Reference
	private MemberDao memberDao;
	@Reference
	private TransactionUtil transactionUtil;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String userId = ParamUtil.getString(resourceRequest, "userId");
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		try {
			Organization org =orgDao.findByOrgId(orgId);
			if(org!=null && PartyOrgAdminTypeEnum.BRANCH.getType().equals(org.getOrg_type())){
				Member member = memberDao.findMemberByUser(userId);
				if(member == null ){
					resourceResponse.getWriter().write(JSON.toJSONString(ResultUtil.fail("党员信息不存在。")));
				}else{
					if(!member.getHistoric()){
						resourceResponse.getWriter().write(JSON.toJSONString(ResultUtil.fail("党员信息不再历史党员中，不需要恢复。")));
					}else{
						transactionUtil.startTransaction();
						int n = orgDao.recoveryMemberByUserId(userId,orgId);
						User user = userDao.findUserByEthnicity(orgId);
						if(user == null){
							User u = new User();
							u.setUser_id(userId);
							u.setUser_name(member.getMember_name());
							u.setUser_sex(member.getMember_sex());
							u.setUser_telephone(member.getMember_phone_number());
							u.setUser_department_id(orgId);
							u.setState("1");
							u.setUser_mailbox(member.getMember_mailbox());
							u.setUserrole("普通党员");
							userDao.updateUserInfo(u);
						}else{
							orgDao.recoveryUserByUserId(userId,orgId);
						}
						if (n > 0) {
							transactionUtil.commit();
							resourceResponse.getWriter().write(JSON.toJSONString(ResultUtil.success(null)));
						} else {
							resourceResponse.getWriter().write(JSON.toJSONString(ResultUtil.fail("恢复党员失败。")));
							transactionUtil.rollback();
						}
					}
				}
			}else{
				resourceResponse.getWriter().write(JSON.toJSONString(ResultUtil.fail("请求的组织orgId不正确")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
