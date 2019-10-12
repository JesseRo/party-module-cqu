package party.portlet.org.command;

import java.io.PrintWriter;
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
				"javax.portlet.name=" + PartyPortletKeys.Org,
				"mvc.command.name=/hg/org/move/object"
	    },
	    service = MVCResourceCommand.class
)
/**
 * 获取移动对象
 * @author gongmingbo
 *
 */
public class OrgGetMoveObjectResourceCommand implements MVCResourceCommand {
	@Reference
	private OrgDao orgDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		List<Map<String, Object>> list = orgDao.findMoveObject(orgId);
		try {
			PrintWriter printWriter = resourceResponse.getWriter();
			printWriter.write(JSON.toJSONString(list));
		} catch (Exception e) {

		}
		return false;
	}

}
