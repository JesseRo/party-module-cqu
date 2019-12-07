package hg.party.command.PartyBranch;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.google.gson.Gson;
import dt.session.SessionManager;
import hg.party.dao.downlistdao.DownListDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.Hg_Value_Attribute_Info;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
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

	@Reference
	private OrgDao orgDao;
	@Reference
	private DownListDao downListDao;
	@Override
	public String render(RenderRequest request, RenderResponse response) throws PortletException {
		String meetingId = ParamUtil.getString(request, "meetingId");
		String infromId = ParamUtil.getString(request, "infromId");
		String orgId = SessionManager.getAttribute(request.getRequestedSessionId(), "department").toString();
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
		List<Hg_Value_Attribute_Info> list = downListDao.meetType();
		request.setAttribute("mapNew", new Gson().toJson(list));

		Organization org = orgDao.findByOrgId(orgId);
		request.setAttribute("org", org);
		request.setAttribute("meetingTypes", list);
		String formId = UUID.randomUUID().toString();
		SessionManager.setAttribute(request.getRequestedSessionId(), "formId-Submission", formId);
		request.setAttribute("formId", formId);
		return super.render(request, response);
	}

}
