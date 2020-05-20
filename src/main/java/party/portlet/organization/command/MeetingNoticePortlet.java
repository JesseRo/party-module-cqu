package party.portlet.organization.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.server.organization.GraftService;
import hg.party.server.partyBranch.PartyBranchService;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=活动内容通知",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/organization/meetingNotice.jsp",
			"javax.portlet.name=" + PartyPortletKeys.MeetingNotice,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class MeetingNoticePortlet extends MVCPortlet{
	GraftService grafservice=new GraftService();
	PartyBranchService service=new PartyBranchService();
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		 int pageNo=ParamUtil.getInteger(renderRequest, "pageNo");
		 String  date = ParamUtil.getString(renderRequest, "date");
		 date = HtmlUtil.escape(date);
		 String orgId = (String)SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
		 orgId = HtmlUtil.escape(orgId);
		 String orgType=(String)SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "orgType");
		 orgType = HtmlUtil.escape(orgType);
		 List<Map<String, Object>> list=grafservice.findAlreadyPublic(1,date,orgId);
		 if (list!=null&&list.size()>0) {
	     
	     int totalCount=list.size();
	     int pageSize =8;
	     int totalPage=totalCount/pageSize;
	     if (totalCount%pageSize!=0){
	    	 totalPage=totalPage+1;
		     }
	     	if(pageNo==0 || pageNo<0) {
	     		pageNo=1;
			}else if(pageNo > totalPage){
				pageNo = totalPage;
			}
	        List<Map<String, Object>> list1=grafservice.findAlreadyPublic(1, date,pageSize,(pageNo-1)*pageSize,orgId);
			renderRequest.setAttribute("grafts", list1);		
	        renderRequest.setAttribute("pageNo",pageNo);
			renderRequest.setAttribute("totalPage",totalPage);
			renderRequest.setAttribute("date",date);
			renderRequest.setAttribute("orgType",orgType);
		 }
		
		super.doView(renderRequest, renderResponse);
	}
	@Override
	protected void sendRedirect(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException {
		
		super.sendRedirect(actionRequest, actionResponse);
	}
}
