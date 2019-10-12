package hg.party.command.PartyBranch;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import dt.session.SessionManager;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;
import party.portlet.form.command.FormRenderCommand;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
			"mvc.command.name=/hg/aginPlanSconed"
	    },
	    service = MVCRenderCommand.class
)
public class AgainPlanSconed extends FormRenderCommand{
	Logger logger=Logger.getLogger(AgainPlanSconed.class);
	PartyBranchService service=new PartyBranchService();
	@Override
	public String render(RenderRequest request, RenderResponse response) throws PortletException {
		String meetingId = ParamUtil.getString(request, "meetingId");
		String infromId = ParamUtil.getString(request, "infromId");
		String orgId = ParamUtil.getString(request, "orgId");
		String type = ParamUtil.getString(request, "type");
		meetingId = HtmlUtil.escape(meetingId);
		infromId = HtmlUtil.escape(infromId);
		orgId = HtmlUtil.escape(orgId);
		type = HtmlUtil.escape(type);
		
		if (!StringUtils.isEmpty(meetingId)&&!StringUtils.isEmpty(type)&&type.equals("edit")) {
			//计划编辑
			 Map<String, Object> mapold=  service.findPlanSconed(meetingId);
			 Object planContent=mapold.get("content");			 
			 mapold.put("content", null);			 
			 mapold.put("state", "edit");			 
			 logger.info(mapold);
			 request.setAttribute("planContent", planContent);
	         request.setAttribute("mapedit",JSON.toJSON(mapold));
		}else if(meetingId!=null&&!meetingId.trim().equals("")) {
			//重新提交计划
			 Map<String, Object> mapold=  service.findPlanSconed(meetingId);
			 Object planContent=mapold.get("content");			 
			 mapold.put("content", null);			 
			 logger.info(mapold);
			 request.setAttribute("planContent", planContent);
	         request.setAttribute("mapold",JSON.toJSON(mapold));
		}else if (infromId!=null&&orgId!=null&&!infromId.trim().equals("")&&!orgId.trim().equals("")) {
			//提交计划
			 String map=service.resultMapSconed(infromId, orgId);
			 logger.info(map);
			 request.setAttribute("mapNew", map);
		}
		String formId = UUID.randomUUID().toString();
		SessionManager.setAttribute(request.getRequestedSessionId(), "formId-Submission", formId);
		request.setAttribute("formId", formId);
		return super.render(request, response);
	}

}
