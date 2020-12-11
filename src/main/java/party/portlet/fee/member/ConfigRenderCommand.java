package party.portlet.fee.member;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.fee.dao.FeeDao;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.List;
import java.util.Map;

@Component(
		immediate = true,
		property = {
		"javax.portlet.name=" + PartyPortletKeys.MemberFeeConfigPortlet,
		"mvc.command.name=/member/config/render"
		},
		service = MVCRenderCommand.class
	)
public class ConfigRenderCommand implements MVCRenderCommand {
	@Reference
	private FeeDao feeDao;

	@Reference
	private OrgDao orgDao;
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		String memberId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "userName");
		String resubmit = ParamUtil.getString(renderRequest, "resubmit");
		String view;

		Map<String, Object> config = feeDao.getCurrentConfig(memberId);


		if (config == null && !StringUtils.isEmpty(resubmit)) {
			view = "/jsp/fee/member/config.jsp";
		} else {
			renderRequest.setAttribute("config", config);
			view = "/jsp/fee/member/current_config.jsp";
		}
		return view;
	}
	
}