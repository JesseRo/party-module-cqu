package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
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
	private TransactionUtil transactionUtil;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String userId = ParamUtil.getString(resourceRequest, "userId");
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		try {
			transactionUtil.startTransaction();
			Organization org =orgDao.findByOrgId(orgId);
			if(org!=null && org.getOrg_type()== PartyOrgAdminTypeEnum.BRANCH.getType()){
				int n = orgDao.recoveryMemberByUserId(userId,orgId);
				int j = orgDao.recoveryUserByUserId(userId,orgId);
				if (n == 1 && j == 1) {
					resourceResponse.getWriter().write(JSON.toJSONString(ResultUtil.success(null)));
				} else {
					resourceResponse.getWriter().write(JSON.toJSONString(ResultUtil.fail("恢复党员失败。")));
					transactionUtil.rollback();
				}
			}else{
				resourceResponse.getWriter().write(JSON.toJSONString(ResultUtil.fail("请求的组织orgId不正确")));
			}
		} catch (Exception e) {
			transactionUtil.rollback();
			e.printStackTrace();
		}
		return false;
	}
}
