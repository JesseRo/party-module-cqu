package hg.party.command.userMeetingCount;

import java.io.IOException;
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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.organization.AssignedPersonService;
import party.constants.PartyPortletKeys;
/**
 * 获取党支部名称
 * @author Administrator
 *
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.UserMeetingCount,
			"mvc.command.name=/hg/assignedd/branch"
	    },
	    service = MVCResourceCommand.class
)

public class BranchAssignedCommand implements MVCResourceCommand{
    @Reference AssignedPersonService ass;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String userName = ParamUtil.getString(resourceRequest, "userName");//人员姓名
		userName = HtmlUtil.escape(userName);
		String pid = ParamUtil.getString(resourceRequest,"pid" );  //二级党组织姓名
		
		String sessionId = resourceRequest.getRequestedSessionId();
		String orgType = (String) SessionManager.getAttribute(sessionId, "orgType");//获取用户角色类型
		String orgId = (String) SessionManager.getAttribute(sessionId, "department");//获取二级党组织id
		orgType = HtmlUtil.escape(orgType);
		orgId = HtmlUtil.escape(orgId);
		String paramType = ParamUtil.getString(resourceRequest,"paramType" ); //党支部类型
		
		pid = HtmlUtil.escape(pid);
		paramType = HtmlUtil.escape(paramType);
		
		if (!"".equals(pid) && null != pid) {
			List<Map<String, Object>> lists = null;
			if("organization".equals(orgType)){
				lists = ass.brinch(userName,pid);
			}else if ("secondary".equals(orgType)) {
				lists = ass.brinchs(userName,orgId);
			}
		   PrintWriter printWriter=null;
		    try {
				printWriter = resourceResponse.getWriter();
				 if (lists != null && lists.size() > 0) {
						printWriter.write(JSON.toJSONString(lists));
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
		   
			return false;
		}else{
		   
			   return false;
		}
	    
	}

}
