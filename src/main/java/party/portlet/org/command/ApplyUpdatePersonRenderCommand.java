package party.portlet.org.command;

import com.alibaba.fastjson.JSON;


import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyConst;
import party.constants.PartyPortletKeys;
import party.portlet.unit.UnitDao;

import javax.portlet.*;

import java.util.*;


@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.PersonalInfoPortlet,
			"mvc.command.name=/org/user/applyUpdate"
	    },
	    service = MVCRenderCommand.class
	)
public class ApplyUpdatePersonRenderCommand implements MVCRenderCommand {

	@Reference
	private OrgDao orgDao;
	@Reference
	private UserDao UserDao;
	@Reference
	private MemberDao memberDao;
	@Reference
	private UnitDao unitDao;

	public String render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws PortletException {
		String userId = (String)SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "userName");
		List<Map<String, Object>> jobs =memberDao.findMemeberJob();
		List<Map<String, Object>> list = orgDao.findPersonByUserId(userId);
		if (list!=null&&list.size()>0) {
			User user = UserDao.findUserByEthnicity(userId);
			list.get(0).put("email", user.getUser_mailbox());
			renderRequest.setAttribute("info", list.get(0));
		}
		renderRequest.setAttribute("jobs", JSON.toJSON(jobs));
		renderRequest.setAttribute("jobArr", PartyConst.JOBS);
		renderRequest.setAttribute("nationalArr", PartyConst.NATIONAL);
		renderRequest.setAttribute("units", unitDao.findAll());
		return "/jsp/member/applyUpdatePerson.jsp";
	}
}
