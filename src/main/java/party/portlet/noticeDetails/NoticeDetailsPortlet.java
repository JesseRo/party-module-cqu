package party.portlet.noticeDetails;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyMeetingPlanInfo;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年11月2日上午11:32:50<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : 普通党员通知详情<br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=通知详情",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/noticeDetails/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.NoticeDetails,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class NoticeDetailsPortlet extends MVCPortlet {

	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		try {
			String sessionId=renderRequest.getRequestedSessionId();
			String nameId=(String) SessionManager.getAttribute(sessionId, "userName");//该党员id唯一，通过登录session获取
			
			HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
			String meetingId = PortalUtil.getOriginalServletRequest(request).getParameter("meetingId");
			meetingId = HtmlUtil.escape(meetingId);
			if("".equals(meetingId) || null == meetingId){
				meetingId = "aca7500c-8603-4bac-b7c3-e6404b7dd720";
			}
			List<MeetingPlan> listMeetingPlan = partyMeetingPlanInfo.meetingId(meetingId);
			if(listMeetingPlan.size()>0){ 
				//根据meetingId获取该会议信 息
				MeetingPlan meetingPlan=partyMeetingPlanInfo.meetingId(meetingId).get(0);
				//会议主题
				String meetingTheme=meetingPlan.getMeeting_theme();
				//会议类型
				String type=meetingPlan.getMeeting_type();
				//会议时间
				String time=dateFormat(meetingPlan.getStart_time(),meetingPlan.getEnd_time());
				//会议内容
				String content=meetingPlan.getContent();
				//附件
				String attachment = meetingPlan.getAttachment();
				String attName = null;
				if("t".equals(attachment)){
					Map<String,Object> map = partyMeetingPlanInfo.findAttachmentByMeetingid(meetingId);
					attachment = (String) map.get("attachment_url");
					attName = (String) map.get("attachment_name");
				}else{
					attachment = "";
				}
				//改为已读状态
				partyMeetingPlanInfo.findByMeetingStu(nameId,meetingId);
				
				renderRequest.setAttribute("meetingTheme", meetingTheme);
				renderRequest.setAttribute("type", type);
				renderRequest.setAttribute("time", time);
				renderRequest.setAttribute("content", content);
				renderRequest.setAttribute("meetingId", meetingId);
				renderRequest.setAttribute("attachment", attachment);
				renderRequest.setAttribute("attName", attName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.doView(renderRequest, renderResponse);
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
