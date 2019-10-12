package hg.party.command.secondCommittee;

import java.sql.Timestamp;

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

import hg.party.entity.party.MeetingPlan;
import hg.party.entity.party.OrgInform;
import hg.party.server.secondCommittee.SecondCommitteeService;
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
			"javax.portlet.name="+ PartyPortletKeys.SeocndCommitteeToDoList,
			"mvc.command.name=/hg/showConferenceDetail"
	    },
	    service = MVCRenderCommand.class
)
public class ShowDetailCommand implements MVCRenderCommand{
	@Reference
	SecondCommitteeService secondCommitteeService;
	
	Logger logger = Logger.getLogger(ShowDetailCommand.class);

	@Override
	public String render(RenderRequest request, RenderResponse response) throws PortletException {
		String informId = ParamUtil.getString(request, "informId");
		String meetingId = ParamUtil.getString(request, "meetingId");
		informId = HtmlUtil.escape(informId);
		meetingId = HtmlUtil.escape(meetingId);
		
		OrgInform inform = null;
		MeetingPlan meetingPlan = null;
		
		try {
			if (!StringUtils.isEmpty(informId)) {
				inform= secondCommitteeService.queryInformByInformId(informId);
				logger.info("inform is" + inform);
				request.setAttribute("informMeeting", inform);
			}
			if (!StringUtils.isEmpty(meetingId)) {
				meetingPlan = secondCommitteeService.queryMeetingByMeetingId(meetingId);
				logger.info("meetingPlan is" + meetingPlan);
				request.setAttribute("informMeeting", meetingPlan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/jsp/secondCommittee/showDetails.jsp";
	}

}
