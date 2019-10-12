package hg.party.command.secondCommittee;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.secondCommittee.SecondCommitteeService;
import party.constants.PartyPortletKeys;
import party.portlet.form.command.FormRenderCommand;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2017年12月27日下午3:11:52<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
			"mvc.command.name=/hg/todoinformParty"
	    },
	    service = MVCRenderCommand.class
)
public class TodoInformCommand extends FormRenderCommand{
	@Reference
	SecondCommitteeService secondCommitteeService;

	Logger logger = Logger.getLogger(TodoInformCommand.class);

	@Override
	public String render(RenderRequest request, RenderResponse response) throws PortletException {
		String orgType = null;
		try {
			String sessionId=request.getRequestedSessionId();
			//orgType 存session 是否生效
			orgType = SessionManager.getAttribute(sessionId, "orgType").toString();
		} catch (Exception e) {
//			logger.info("GET SESSION INFO ERROR !!!");
		}
		
		String meetingId = ParamUtil.getString(request, "meetingId");
		meetingId = HtmlUtil.escape(meetingId);
		String meetingPlanStr=null;
		try {
			List<Map<String, Object>> object = secondCommitteeService.queryMeetingPlanByMeetingId(meetingId);
			object.get(0).put("content", null);
			meetingPlanStr = new JSONObject(object.get(0)).append("orgType", orgType).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("meetingPlanStr", meetingPlanStr);
		//设置独占表单
		String formId = UUID.randomUUID().toString();
		SessionManager.setAttribute(request.getRequestedSessionId(), "formId-informParty", formId);
		request.setAttribute("formId", formId);
		return super.render(request, response);
	}


}
