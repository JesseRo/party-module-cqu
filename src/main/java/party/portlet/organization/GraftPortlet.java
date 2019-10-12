package party.portlet.organization;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.organization.GraftService;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=组织部-草稿箱",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/organization/graft.jsp",
			"javax.portlet.name=" + PartyPortletKeys.Graft,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class GraftPortlet extends MVCPortlet{
	GraftService grafservice=new GraftService();
	PartyBranchService service=new PartyBranchService();
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		    int pageNo=ParamUtil.getInteger(renderRequest, "pageNo");
		    String  date = ParamUtil.getString(renderRequest, "date");
		    date = HtmlUtil.escape(date);
		    String orgId=(String)SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
		    orgId = HtmlUtil.escape(orgId);
	        String orgType=service.findSconedAndBranch(orgId); 
	        orgType = HtmlUtil.escape(orgType);
		    List<Map<String, Object>> list=grafservice.findGrafts(0,date,orgId);
		  if (list!=null&&list.size()>0) {
		     int totalCount=list.size();
		     int pageSize =8;
		     int totalPage=totalCount/pageSize;
		     if (totalCount%pageSize!=0) {
		    	 totalPage=totalPage+1;
			}
		     if (pageNo==0) {
		    	 pageNo=1;
			}else if(pageNo > totalPage){
				pageNo = totalPage;
			}
		     List<Map<String, Object>> listGrafts=grafservice.findGrafts(0,date,pageSize,(pageNo-1)*pageSize,orgId);
		
			renderRequest.setAttribute("grafts", listGrafts);
		 	renderRequest.setAttribute("pageNo",pageNo);
			renderRequest.setAttribute("totalPage",totalPage);
			renderRequest.setAttribute("date",date);
		     }
		super.doView(renderRequest, renderResponse);
	}
	@Override
	protected void sendRedirect(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException {
		 String orgId=(String)SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "department");
		 String orgType=service.findSconedAndBranch(orgId);

		List<Map<String, Object>> list=grafservice.findGrafts(0,"",orgType);
		   int pageNo=1;
			  if (list!=null&&list.size()>0) {
				 if (pageNo==0) {
					 pageNo=1;
				}
				 int totalCount=list.size();
				 int pageSize =10;
				 int totalPage=totalCount/pageSize;
				 if (totalCount%pageSize!=0) {
					 totalPage=totalPage+1;
				}
				  List<Map<String, Object>> listGrafts = grafservice.findGrafts(0, "", pageSize, (pageNo - 1) * pageSize, orgType);

				  actionRequest.setAttribute("grafts", listGrafts);
				  actionRequest.setAttribute("pageNo", pageNo);
				  actionRequest.setAttribute("totalPage", totalPage);
				  actionRequest.setAttribute("date", "");
			  }
		super.sendRedirect(actionRequest, actionResponse);
	}
}
