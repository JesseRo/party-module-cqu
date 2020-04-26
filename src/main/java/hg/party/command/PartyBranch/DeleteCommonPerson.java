package hg.party.command.PartyBranch;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.dao.partyBranch.PartyBranchDao;
import party.constants.PartyPortletKeys;
@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
				"javax.portlet.name=" + PartyPortletKeys.NewPlan,
				"mvc.command.name=/hg/deletePerson"
	    },
	    service =MVCResourceCommand.class
)
public class DeleteCommonPerson implements MVCResourceCommand{
    @Reference 
    private PartyBranchDao dao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		try {
			String userId = ParamUtil.getString(resourceRequest, "user_id");
			String groupId = ParamUtil.getString(resourceRequest, "groupId");
			userId = HtmlUtil.escape(userId);
			groupId = HtmlUtil.escape(groupId);
			int n =dao.deletePersons(groupId, userId);
			PrintWriter printWriter=resourceResponse.getWriter();
			  Map<String, Object> map=new HashMap<>();
				   if (n>0) {
					       map.put("state", "ok");
					       printWriter.write(JSON.toJSONString(map));
				    }else {
				    	   map.put("state", "fail");
						   map.put("message", "添加人员失败");
						   printWriter.write(JSON.toJSONString(map));
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
