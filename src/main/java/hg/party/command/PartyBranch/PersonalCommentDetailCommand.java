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
			"mvc.command.name=/hg/showCommentDetail"
	    },
	    service = MVCRenderCommand.class
)
public class PersonalCommentDetailCommand implements MVCRenderCommand{
	@Reference
	PartyBranchService service;
	Logger logger = Logger.getLogger(PersonalCommentDetailCommand.class);

	@Override
	public String render(RenderRequest renderRequest, RenderResponse response) throws PortletException {
		
		try {
			String meetingId = ParamUtil.getString(renderRequest, "meetingId");
			String userId = ParamUtil.getString(renderRequest, "userId");
			meetingId = HtmlUtil.escape(meetingId);
			userId = HtmlUtil.escape(userId);
			
			meetingId = (StringUtils.isEmpty(meetingId)) ? "26904396-429e-403f-9eff-19455508b7f4":meetingId;
			userId = (StringUtils.isEmpty(userId)) ? "222222":userId;
			Map<String, Object> meetingComment = null;
			try {
				meetingComment = service.findPersonalMeetingComment(meetingId, userId).get(0);
				logger.info("comment :" + meetingComment);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
			renderRequest.setAttribute("meetingType", meetingComment.get("meeting_type"));	
			renderRequest.setAttribute("meetingTheme", meetingComment.get("meeting_theme"));	
			renderRequest.setAttribute("commentTime", meetingComment.get("commnets_time"));	
			renderRequest.setAttribute("remark", meetingComment.get("remark"));	
			renderRequest.setAttribute("commentScore", meetingComment.get("comments_score"));	
			renderRequest.setAttribute("commentScore1", meetingComment.get("comments_aspects_one"));	
			renderRequest.setAttribute("commentScore2", meetingComment.get("comments_aspects_two"));	
			renderRequest.setAttribute("commentScore3", meetingComment.get("comments_aspects_three"));	
			renderRequest.setAttribute("commentScore4", meetingComment.get("comments_aspects_four"));	
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
		}
		
		return "/jsp/partyBranch/personalScoreDetail.jsp";
		
		
	}


}
