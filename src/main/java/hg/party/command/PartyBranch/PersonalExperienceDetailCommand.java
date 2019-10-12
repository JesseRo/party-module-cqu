package hg.party.command.PartyBranch;

import java.util.Map;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;

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
			"javax.portlet.name="+ PartyPortletKeys.TaskPortlet,
			"javax.portlet.name="+ PartyPortletKeys.SeocndCommitteeToDoList,
			"javax.portlet.name="+ PartyPortletKeys.PartySecondaryBranch,
			"javax.portlet.name="+ PartyPortletKeys.PartyMeetingPlan,
			"mvc.command.name=/hg/showExperienceDetail"
	    },
	    service = MVCRenderCommand.class
)
public class PersonalExperienceDetailCommand implements MVCRenderCommand{
	@Reference
	PartyBranchService service;
	Logger logger = Logger.getLogger(PersonalExperienceDetailCommand.class);

	@Override
	public String render(RenderRequest renderRequest, RenderResponse response) throws PortletException {
		
		try {
			String meetingId = ParamUtil.getString(renderRequest, "meetingId");
			String userId = ParamUtil.getString(renderRequest, "userId");
			meetingId = HtmlUtil.escape(meetingId);
			userId = HtmlUtil.escape(userId);
			
			meetingId = (StringUtils.isEmpty(meetingId)) ? "26904396-429e-403f-9eff-19455508b7f4":meetingId;
			userId = (StringUtils.isEmpty(userId)) ? "222222":userId;
			Map<String, Object> meetingExperience = null;
			try {
				meetingExperience = service.findPersonalMeetingExperience(meetingId, userId).get(0);
				logger.info("comment :" + meetingExperience);
			} catch (Exception e) {
				logger.info(e.getMessage()); 
			}
			
			renderRequest.setAttribute("meetingType", meetingExperience.get("meeting_type"));	
			renderRequest.setAttribute("meetingTheme", meetingExperience.get("meeting_theme"));	
			renderRequest.setAttribute("experienceContent", meetingExperience.get("experience_content"));	
			renderRequest.setAttribute("uploadTime", meetingExperience.get("upload_time"));	
			renderRequest.setAttribute("userName", meetingExperience.get("user_name"));	
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		
		return "/jsp/partyBranch/personalExperienceDetail.jsp";
		
		
	}


}
