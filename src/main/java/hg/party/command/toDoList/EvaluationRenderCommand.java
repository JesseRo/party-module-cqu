package hg.party.command.toDoList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyMeetingPlanInfoService;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月28日下午2:18:09<br>
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
		"javax.portlet.name=" + PartyPortletKeys.ToDoList,
		"mvc.command.name=/party/evalution"
    },
    service = MVCRenderCommand.class
)
public class EvaluationRenderCommand implements MVCRenderCommand {

	@Reference
	private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		String meetingId = ParamUtil.getString(renderRequest, "meetingId");
		meetingId = HtmlUtil.escape(meetingId);
		String userId = ParamUtil.getString(renderRequest, "userId");
		userId = HtmlUtil.escape(userId);
		//根据meetingId获取该会议信息
		MeetingPlan meetingPlan= partyMeetingPlanInfoService.meetingId(meetingId).get(0);
		//会议主题
		String meetingTheme=meetingPlan.getMeeting_theme();
		//会议类型
		String type=meetingPlan.getMeeting_type();
		//会议时间
		String time=dateFormat(meetingPlan.getStart_time(),meetingPlan.getEnd_time());
		
		String formSeeion = UUID.randomUUID().toString();
		SessionManager.setAttribute(renderRequest.getRequestedSessionId(), "formSeeionEvaluation", formSeeion);
		renderRequest.setAttribute("formSeeion", formSeeion);
		renderRequest.setAttribute("meetingId", meetingId);
		renderRequest.setAttribute("userId", userId);
		renderRequest.setAttribute("meetingTheme", meetingTheme);
		renderRequest.setAttribute("type", type);
		renderRequest.setAttribute("time", time);
		return "/jsp/toDoList/evaluation.jsp";
	}

	//党员待办事项中时间格式化
	public String dateFormat(Date startTime,Date endTime){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat sdf1=new SimpleDateFormat("HH:mm");
		String str1=sdf.format(startTime);
		String str3=sdf1.format(startTime);
		String str2=sdf.format(endTime);
		String str4=sdf1.format(endTime);
		String time="";
		if(str1.equals(str2)){
			time += str1+" "+str3+"-"+str4;
		}else{
			time += str1+" "+str3+"-"+str2+" "+str4;
		}
		return time;
	}

	
}
