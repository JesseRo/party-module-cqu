package hg.party.command.toDoList;

import java.sql.Timestamp;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.entity.toDoList.Experience;
import hg.party.server.party.PartyMeetingPlanInfo;
import hg.party.server.toDoList.ExperienceServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： 保存上传心得<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年11月1日下午4:16:56<br>
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
			"mvc.command.name=/party/experience"
	    },
	    service = MVCActionCommand.class
	)
public class ExperienceSaveActionCommand extends BaseMVCActionCommand {

	@Reference
	private ExperienceServer experienceServer;
	@Reference
	private PartyMeetingPlanInfo partyMeetingPlanInfo;
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		Experience experience = new Experience();
		String meetingId = ParamUtil.getString(actionRequest, "meetingId");
		String userId = ParamUtil.getString(actionRequest, "userId");
		String content = ParamUtil.getString(actionRequest, "experience");
		String formId = ParamUtil.getString(actionRequest, "formId");	//过滤重复提交
		meetingId = HtmlUtil.escape(meetingId);
		userId = HtmlUtil.escape(userId);
		formId = HtmlUtil.escape(formId);
		
		synchronized (PortalUtil.getHttpServletRequest(actionRequest).getSession()) {
			String originalFormId = (String) SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "formId-informParty");
            if (formId.equals(originalFormId)) {
				int a = partyMeetingPlanInfo.experience(meetingId,userId).size();
				if("".equals(content) || content==null){
					return;
				}else{
					if(a == 0){
						experience.setMeetingId(meetingId);
						experience.setParticipantId(userId);
						experience.setUploadState(1);
						experience.setUploadTime(new Timestamp(new Date().getTime()));
						experience.setContent(content);
						experienceServer.save(experience);
					}else {
						partyMeetingPlanInfo.updateHeart(content,meetingId,userId);
					}
				}
            }
		}
	}

}
