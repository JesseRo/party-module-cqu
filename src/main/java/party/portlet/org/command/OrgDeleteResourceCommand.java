package party.portlet.org.command;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.util.TransactionUtil;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Org,
				"mvc.command.name=/org/delete/user"
	    },
	    service = MVCResourceCommand.class
)
public class OrgDeleteResourceCommand implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;
	@Reference
	private TransactionUtil transactionUtil;
	@Reference
	private MemberDao memberDao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String userIds = ParamUtil.getString(resourceRequest, "userIds");
		String extra = ParamUtil.getString(resourceRequest, "extra");
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		String userIdInfo[] = userIds.split(",");
		//String deleteSsql = "delete from hg_party_group_member_info where participant_id='"+userId+"'";
		try {
			transactionUtil.startTransaction();
			//逻辑删除，可恢复
			int n = 0;
			int j = 0;
			for (int i = 0; i < userIdInfo.length; i++) {
				String deleteAssigneSql = "DELETE from hg_party_assigne  where assigne_user_id='"+userIdInfo[i]+"'";
				String deleteSsql = "delete from hg_party_group_member_info where participant_id='"+userIdInfo[i]+"'";
				n = orgDao.deletePersonByuserId(userIdInfo[i], extra);
				j = orgDao.deleteUserByuserId(userIdInfo[i]);
				//orgDao.deleteAdmin(userIdInfo[i]);
			    //memberDao.insertOrUpate(deleteSsql);
			 	//int k= memberDao.insertOrUpate(deleteAssigneSql);
			}
			Map<String, Object> map = new HashMap<>();
			if (n == 1 && j == 1) {
				map.put("state", true);
				transactionUtil.commit();
			} else {
				map.put("state", false);
				transactionUtil.rollback();
			}
			resourceResponse.getWriter().write(JSON.toJSONString(map));
		} catch (Exception e) {
			transactionUtil.rollback();
			e.printStackTrace();
		}
		return false;
	}
}
