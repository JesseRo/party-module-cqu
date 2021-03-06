package party.portlet.userRoles;

import java.io.IOException;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.dao.org.MemberDao;
import hg.party.entity.partyMembers.Member;
import hg.party.server.login.UserService;
import hg.party.server.organization.UserRoleService;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.util.AssertionUtil;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要：通过ticket到cas服务器获取账号<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月12日下午1:50:02<br>
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
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=选择用户角色",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/userRoles/view.jsp",
                "javax.portlet.name=" + PartyPortletKeys.UserRoles,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class UserRolesPortlet extends MVCPortlet {
    Logger log = Logger.getLogger(UserRolesPortlet.class);
    @Reference
    private UserRoleService userRoleService;
    @Reference
    private UserService userService;
    @Reference
    private MemberDao memberDao;

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        HttpServletRequest request = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest));
        HttpServletResponse response = PortalUtil.getHttpServletResponse(renderResponse);
        long companyId = PortalUtil.getCompanyId(request);
        String ticket = ParamUtil.getString(request, "ticket");
        if (!StringUtils.isEmpty(ticket)) {
            String casLoginName = AssertionUtil.resCasLoginName(request, response, companyId);
            log.info("[casLoginName] :" + casLoginName);
            Member member = memberDao.findByAuthNumber(casLoginName);
            if (member == null) {
                renderRequest.setAttribute("ok", false);
            } else {
                renderRequest.setAttribute("ok", true);
                String identity = member.getMember_identity();
                List<String> role = userRoleService.getRoles(identity);
                if (role != null && role.size() > 0) {
                    HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
                    String ip = httpServletRequest.getHeader("x-forwarded-for");
                    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = httpServletRequest.getHeader("Proxy-Client-IP");
                    }
                    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
                    }
                    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = httpServletRequest.getRemoteAddr();
                    }
                    log.info("[role] :" + role.get(0));
                    String urls = userService.loginCas(identity, renderRequest.getRequestedSessionId(), ip);
                    String url = urls.substring(1);
                    log.info("[url] :" + url);
                    if (!StringUtils.isEmpty(url) && url.length() > 0) {
                        response.sendRedirect(url);
                    }
                }
            }
        } else {
            renderRequest.setAttribute("ok", true);
        }

        super.doView(renderRequest, renderResponse);
    }
}
