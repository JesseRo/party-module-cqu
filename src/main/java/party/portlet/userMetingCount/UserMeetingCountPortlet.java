package party.portlet.userMetingCount;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.command.exportexcl.ExprotUntil;
import hg.party.server.party.PartyMeetingPlanInfo;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 创建人 　： yjx<br>
 * 创建日期： 2018年1月6日下午<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=人员会议统计",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/userMeetingCount/userMeetingCount.jsp",
			"javax.portlet.name=" + PartyPortletKeys.UserMeetingCount,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class UserMeetingCountPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(UserMeetingCountPortlet.class);
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	private int pageSize = 8;//每页条数
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		logger.info("MeetingRecordPortlet doView...");
		String userName = ParamUtil.getString(renderRequest, "userName");//人员姓名
		userName = HtmlUtil.escape(userName);
		String seconedId = ParamUtil.getString(renderRequest, "seconedId");//获取二级党组织的名字
		seconedId = HtmlUtil.escape(seconedId);
		String branchId = ParamUtil.getString(renderRequest, "branchId");//获取党支部的名字
		branchId = HtmlUtil.escape(branchId);
		
		
		String sessionId = renderRequest.getRequestedSessionId();
		String orgType = (String) SessionManager.getAttribute(sessionId, "orgType");//获取用户角色类型
		String orgId = (String) SessionManager.getAttribute(sessionId, "department");//获取组织id
		orgType = HtmlUtil.escape(orgType);
		orgId = HtmlUtil.escape(orgId);
		
		String startTime = ParamUtil.getString(renderRequest, "startTime");//开始时间
		startTime = HtmlUtil.escape(startTime);
		String endTime = ParamUtil.getString(renderRequest, "endTime");//开始时间
		endTime = HtmlUtil.escape(endTime);
		String meetTheme = ParamUtil.getString(renderRequest, "meetTheme");//会议主题
		meetTheme = HtmlUtil.escape(meetTheme);
		String meetType = ParamUtil.getString(renderRequest, "meetType");//会议类型
		meetType = HtmlUtil.escape(meetType);
		int pageNo = ParamUtil.getInteger(renderRequest, "pageNo");
		String checkState="";
		
		if(!"".equals(orgType) && null != orgType){
			
			int totalCount = partyMeetingPlanInfo.MeetingSun(userName,seconedId,branchId,orgType,orgId);//获取总页数
			
	//		 int totalCount=list.size();
		     int pageSize =8;
		     int totalPage=totalCount/pageSize;
		     if (totalCount%pageSize!=0) {
		    	 totalPage=totalPage+1;
		     }
		     if(pageNo==0){
				pageNo = 1;//默认当前页为1
		     }else if(pageNo > totalPage){
				pageNo = totalPage;
		     }
		     List<Map<String, Object>> listResult = partyMeetingPlanInfo.userMeetingCount(userName,seconedId,branchId,pageSize,(pageNo-1)*pageSize,orgType,orgId);
		     
		     for (Map<String, Object> map : listResult) {
		    	 try {
				String note=ExprotUntil.getNote(map);
				map.put("note", note);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			renderRequest.setAttribute("list", listResult);
			renderRequest.setAttribute("pageNo", pageNo);
			renderRequest.setAttribute("totalPage", totalPage);
			renderRequest.setAttribute("startTime", startTime);
			renderRequest.setAttribute("endTime", endTime);
			renderRequest.setAttribute("seconedId", seconedId);
			renderRequest.setAttribute("branchId", branchId);
			renderRequest.setAttribute("meetTheme", meetTheme);
			renderRequest.setAttribute("meetType", meetType);
			renderRequest.setAttribute("userName", userName);
			renderRequest.setAttribute("orgType", orgType);
		}
		super.doView(renderRequest, renderResponse);
	}

}

