package hg.party.command.PartyBranch;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.dao.partyBranch.PartyBranchDao;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
			"mvc.command.name=/hg/getBranchAllPersons"
	    },
	    service =MVCResourceCommand.class
)
/**
 * 获取支部的所有人员
 * @author gongmingbo
 *
 */
public class BranchAllPersons implements MVCResourceCommand{
    @Reference
    private PartyBranchService service;
    @Reference 
    private PartyBranchDao dao;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		try {
			String orgId =ParamUtil.getString(resourceRequest, "orgId");
			orgId = HtmlUtil.escape(orgId);
			if (StringUtils.isEmpty(orgId)) {
				 orgId=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "orgId");
			}
			List<Map<String, Object>> list=dao.findBranchPersons(orgId);
			PrintWriter printWriter=resourceResponse.getWriter();
			printWriter.write(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		 }
		return false;
	}
}
