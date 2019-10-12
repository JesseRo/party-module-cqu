package party.portlet.org.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.dao.org.OrgDao;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.PersonAddPortlet,
				"mvc.command.name=/hg/org/exist"
	    },
	    service = MVCResourceCommand.class
)
/**
 * 新增人员是查看是否已经存在
 * @author gongmingbo
 *
 */
public class OrgUserExistResourceCommand2 implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String userId = ParamUtil.getString(resourceRequest, "UserId");
		String orgId = ParamUtil.getString(resourceRequest, "OrgId");
		// 判断该人员是否未删除
		List<Map<String, Object>> list = orgDao.findUserExist(userId);
		// 判断该人员是否存在该组织
		List<Map<String, Object>> lists = orgDao.findUserExist(userId, orgId);
		Map<String, Object> map = new HashMap<>();
		if (lists != null && lists.size() > 0) {
			map = getMapMessages(lists);
		} else if (list != null && list.size() > 0) {
			map = getMapMessages(list);
		} else {
			map.put("state", false);
		}
		try {
			resourceResponse.getWriter().write(JSON.toJSONString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Map<String, Object> getMapMessages(List<Map<String, Object>> list) {
		String orgBranchName = "";
		String orgSecondName = "";
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> listOrgBranchMessages = orgDao.findOrgName(list.get(0).get("member_org") + "");
		if (listOrgBranchMessages != null && listOrgBranchMessages.size() > 0) {
			orgSecondName = orgDao.findSecondOrgName(list.get(0).get("member_org") + "").get(0).get("org_name") + "";
			orgBranchName = listOrgBranchMessages.get(0).get("org_name") + "";
		}
		map.put("state", true);
		map.put("message", "该人员已经存于" + orgSecondName + ">" + orgBranchName);
		return map;
	}
}
