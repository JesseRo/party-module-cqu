package party.portlet.personalCenter;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.dao.TransportDao;
import party.portlet.transport.entity.Retention;
import party.portlet.transport.entity.Transport;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.ChangePasswordPortlet,
				"mvc.command.name=/password/reset"
	    },
	    service = MVCResourceCommand.class
)
public class ChangePasswordCommand implements MVCResourceCommand {
	@Reference
	private UserDao userDao;
	@Reference
	private MemberDao memberDao;
	@Reference
	private TransactionUtil transactionUtil;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		String userId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");

		String original = ParamUtil.getString(resourceRequest, "original");
		String newPass = ParamUtil.getString(resourceRequest, "newPass");
		transactionUtil.startTransaction();
		try {
			if (original.equalsIgnoreCase(newPass)) {
				res.getWriter().write(gson.toJson(JsonResponse.Failure("新密码不得和原密码相同")));
			}else{
				if (StringUtils.isEmpty(newPass)){
					res.getWriter().write(gson.toJson(JsonResponse.Failure("密码不得为空")));
				}
				User user = userDao.findUserByEthnicity(userId);
				if (user.getUser_password().equals(original)){
					user.setUser_password(newPass);
					userDao.saveOrUpdate(user);
					res.getWriter().write(gson.toJson(JsonResponse.Success()));
				}else {
					res.getWriter().write(gson.toJson(JsonResponse.Failure("密码错误")));
				}
			}
			transactionUtil.commit();
		}catch (Exception e){
			transactionUtil.rollback();
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}

}
