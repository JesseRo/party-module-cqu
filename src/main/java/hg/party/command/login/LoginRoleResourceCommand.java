package hg.party.command.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import hg.party.dao.login.UserDao;
import hg.party.entity.login.User;
import hg.party.server.login.UserService;
import hg.party.server.organization.UserRoleService;
import hg.util.AssertionUtil;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要：获取角色<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月11日下午3:48:11<br>
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
                "javax.portlet.name=" + PartyPortletKeys.Hgg_login,
                "javax.portlet.name=" + PartyPortletKeys.UserRoles,
                "mvc.command.name=/hg/getRole"
        },
        service = MVCResourceCommand.class
)
public class LoginRoleResourceCommand implements MVCResourceCommand {
    @Reference
    private UserService userService;
    @Reference
    private UserRoleService userRoleService;

    //	UserService u=new UserService();
    @Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String userId = null;
		PrintWriter printWriter;
		/**
		 * 获取cas登录名称
		 */
		String type = ParamUtil.getString(resourceRequest, "type");
		String userName = ParamUtil.getString(resourceRequest, "userName");
		String sessionId = resourceRequest.getRequestedSessionId();
		type = HtmlUtil.escape(type);
		userName = HtmlUtil.escape(userName);
		if (type.equals("cas")) {
			// cas公共认证账号
			User user;
			try {
				user = userService.findByUserId(userName);
			} catch (Exception e) {
				user = null;
			}
			if (user != null) {
				userId = user.getUser_id();
			} else {
				try {
					resourceRequest.setAttribute("userId", "-1");
					printWriter = resourceResponse.getWriter();
					printWriter.write("-1");
					return false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			userId = ParamUtil.getString(resourceRequest, "core");
		}
		if (userId != null && !userId.trim().equals("")) {
			List<String> role = userRoleService.getRoles(userId);
			try {
				printWriter = resourceResponse.getWriter();
				if (role != null) {
					resourceRequest.setAttribute("userId", "1");
					printWriter = resourceResponse.getWriter();
					printWriter.write(String.join(",", role));
					return false;
				} else {

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return false;
	}

}
