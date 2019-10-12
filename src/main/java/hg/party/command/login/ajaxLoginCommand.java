package hg.party.command.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.swing.event.ListSelectionEvent;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.quartz.SchedulerException;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.entity.login.User;
import hg.party.entity.organization.VisitCount;
import hg.party.server.login.UserService;
import hg.party.server.organization.VisitCountService;
import hg.party.server.partyBranch.PartyBranchService;
import hg.party.server.sms.SmsService;
import hg.util.ConstantsKey;
import hg.util.MD5;
import party.constants.PartyPortletKeys;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Hgg_login,
                "javax.portlet.name=" + PartyPortletKeys.UserRoles,
                "javax.portlet.name=" + PartyPortletKeys.LoginDisplay,
                "javax.portlet.name=" + PartyPortletKeys.TopNavigation,
                "mvc.command.name=/hg/ajaxLogin"/*页面请求到该路径*/
        },
        service = MVCResourceCommand.class
)
public class ajaxLoginCommand implements MVCResourceCommand {
    Logger logger = Logger.getLogger(ajaxLoginCommand.class);
    @Reference
    private UserService userService;
    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws PortletException {

        final TimeZone zone = TimeZone.getTimeZone("GMT+8"); //获取中国时区
        TimeZone.setDefault(zone); //设置时区

        String type = ParamUtil.getString(resourceRequest, "type");
        String sessionId = resourceRequest.getRequestedSessionId();
        String exit = ParamUtil.getString(resourceRequest, "exit");
        String role = ParamUtil.getString(resourceRequest, "role");
        String changType = ParamUtil.getString(resourceRequest, "changType");
        type = HtmlUtil.escape(type);
        exit = HtmlUtil.escape(exit);
        role = HtmlUtil.escape(role);
        changType = HtmlUtil.escape(changType);
        
        /**
         *用户退出功能
         */
        if (exit.equals("exit")) {
            SessionManager.removeSession(sessionId);
            PrintWriter printWriter;
            try {
               printWriter = resourceResponse.getWriter();
                printWriter.write("4");//退出成功
                printWriter.close();
                return false;
            } catch (IOException e) {

            }
        }

        String userName;
        HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(resourceRequest);
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
        logger.info("ip:  " + ip);
        //身份切换登陆
        if ("changeRole".equals(changType)) {
		String result	= userService.loginCas((String)SessionManager.getAttribute(sessionId, "userName"), role, sessionId, ip);
			 try {
	                PrintWriter printWriter = resourceResponse.getWriter();
	                printWriter.write(result);
	                printWriter.close();
	                return false;
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
		}
        
        User user;
        String result;
        if (type.equals("cas")) {
            userName = ParamUtil.getString(resourceRequest, "userName");
            userName = HtmlUtil.escape(userName);
            try {
                user = userService.findByUserId(userName);
            } catch (Exception e) {
                user = null;
            }
            if (user == null){
                result = "1"; //用户不存在
            }else {
                result = userService.login(userName, user.getUser_password(), role, sessionId, ip);

            }
            try {
                PrintWriter printWriter = resourceResponse.getWriter();
                printWriter.write(result);
                printWriter.close();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            userName = ParamUtil.getString(resourceRequest, "userName");
            String password = ParamUtil.getString(resourceRequest, "password");
            userName = HtmlUtil.escape(userName);
            password = HtmlUtil.escape(password);
            password = MD5.getMD5(password);
            try {
                PrintWriter printWriter = resourceResponse.getWriter();
//                try {
//					SmsService.smsSend("myid", "sb", Arrays.asList(new String[]{"13883786116"}));
//				} catch (SchedulerException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
                printWriter.write(userService.login(userName, password, role, sessionId, ip));//代表用户不存在
                printWriter.close();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



}
