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
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.secondCommittee.SecondCommitteeService;
import party.constants.PartyPortletKeys;
import party.portlet.form.command.FormRenderCommand;

/**
 * 文件名称： 发送上传会议记录<br>
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
			"mvc.command.name=/hg/todoUploadNotes"
	    },
	    service = MVCRenderCommand.class
)
public class TodoUploadCommand extends FormRenderCommand{
	@Reference
	SecondCommitteeService secondCommitteeService;

	Logger logger = Logger.getLogger(TodoUploadCommand.class);

	@Override
	public String render(RenderRequest request, RenderResponse response) throws PortletException {

		String orgId = null;
		String orgType = null;
		try {
			String sessionId=request.getRequestedSessionId();
			orgId = SessionManager.getAttribute(sessionId, "department").toString();
			//orgType 存session 是否生效
			orgType = SessionManager.getAttribute(sessionId, "orgType").toString();
		} catch (Exception e) {
//			logger.info("当前SESSION 获取信息失败!");
		}
		orgId=(StringUtils.isEmpty(orgId)) ? "050026623508":orgId;
		orgType = secondCommitteeService.queryOrgTypeByOrgId(orgId);
		
		String meetingId = ParamUtil.getString(request, "meetingId");
		String meetingPlanStr = null;
		
		Integer shouldCount = null;
		try {
			shouldCount = Integer.valueOf(secondCommitteeService.queryShouldCount(meetingId).get(0).get("count").toString());
		} catch (Exception e) {
//			logger.info(e.getMessage());
		}
		if(!StringUtils.isEmpty(meetingId)){
			Map<String, Object> object = null;
			object = secondCommitteeService.queryMeetingPlanByMeetingId(meetingId);

			object.put("content", null);
			meetingPlanStr = new JSONObject(object).append("orgType", orgType).append("shouldCount", shouldCount).toString();
		}
		
		String from_uuid = UUID.randomUUID().toString();
		SessionManager.setAttribute(request.getRequestedSessionId(), "formId-Submission", from_uuid);
		request.setAttribute("meetingPlanStr", meetingPlanStr);
		request.setAttribute("from_uuid", from_uuid);
		return super.render(request, response);
	}
}
