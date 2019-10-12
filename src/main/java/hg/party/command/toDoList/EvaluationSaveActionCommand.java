package hg.party.command.toDoList;

import java.sql.Timestamp;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.entity.toDoList.Evaluation;
import hg.party.server.toDoList.EvaluationServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年11月1日下午5:04:46<br>
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
			"mvc.command.name=/party/uploadEvaluation"
	    },
	    service = MVCActionCommand.class
	)
public class EvaluationSaveActionCommand extends BaseMVCActionCommand {

	@Reference
	private EvaluationServer evaluationServer;
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		Evaluation evaluation = new Evaluation();
		String meetingId = ParamUtil.getString(actionRequest, "meetingId");
		meetingId = HtmlUtil.escape(meetingId);
		String userId = ParamUtil.getString(actionRequest, "userId");
		userId = HtmlUtil.escape(userId);
		String score = ParamUtil.getString(actionRequest, "score");
		score = HtmlUtil.escape(score);
		String score1 = ParamUtil.getString(actionRequest, "score1");
		score1 = HtmlUtil.escape(score1);
		String score2 = ParamUtil.getString(actionRequest, "score2");
		score2 = HtmlUtil.escape(score2);
		String score3 = ParamUtil.getString(actionRequest, "score3");
		score3 = HtmlUtil.escape(score3);
		String formSeeion = ParamUtil.getString(actionRequest, "formSeeion");
		formSeeion = HtmlUtil.escape(formSeeion);

		if (StringUtils.isEmpty(score)) {
			score="0";
		}
		if (StringUtils.isEmpty(score1)) {
			score1="0";
		}
		if (StringUtils.isEmpty(score2)) {
			score2="0";
		}
		if (StringUtils.isEmpty(score3)) {
			score3="0";
		}
		int total = (Integer.parseInt(score)+Integer.parseInt(score1)+Integer.parseInt(score2)+Integer.parseInt(score3))*5;
		evaluation.setMeetingId(meetingId);
		evaluation.setParticipantId(userId);
		evaluation.setCommentsState(1);
		evaluation.setCommentsTime(new Timestamp(new Date().getTime()));
		evaluation.setCommentsAspectsOne(Integer.parseInt(score));
		evaluation.setCommentsAspectsTwo(Integer.parseInt(score1));
		evaluation.setCommentsAspectsThree(Integer.parseInt(score2));
		evaluation.setCommentsAspectsFour(Integer.parseInt(score3));
		evaluation.setCommentsScore(total);
		
		synchronized (PortalUtil.getHttpServletRequest(actionRequest).getSession()) {
			String fromId=(String)SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "formSeeionEvaluation");
			if (formSeeion.equals(fromId)) {
				evaluationServer.save(evaluation);
				SessionManager.setAttribute(actionRequest.getRequestedSessionId(), "formSeeionEvaluation", "null");
			}
		}		
	}
	
}
