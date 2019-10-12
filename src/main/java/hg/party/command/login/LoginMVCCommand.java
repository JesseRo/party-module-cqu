package hg.party.command.login;
//package command;
//
//import javax.portlet.ActionRequest;
//import javax.portlet.ActionResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.osgi.service.component.annotations.Component;
//import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
//import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
//import com.liferay.portal.kernel.struts.LastPath;
//import com.liferay.portal.kernel.util.ParamUtil;
//import com.liferay.portal.kernel.util.PortalUtil;
//
//import dt.session.SessionManager;
//import entity.User;
//import hgg_login.constants.Hgg_loginPortletKeys;
//import service.UserService;
//
//@Component(
//		immediate = true,
//		property = {
//			"javax.portlet.name="+ Hgg_loginPortletKeys.Hgg_login,/*在重定向方法下，会进入指定portlet类下的sendRedirect()方法*/
//			"mvc.command.name=/hg_login"/*页面请求到该路径*/
//	    },
//	    service = MVCActionCommand.class
//)
//public class LoginMVCCommand extends BaseMVCActionCommand{
//     //@Reference
//    //private UserService UserService;
//   UserService userService=new UserService();
//	 @Override
//	protected void doProcessAction(ActionRequest actionRequest,
//			ActionResponse actionResponse) throws Exception {
//		
//		// HttpServletRequest request=PortalUtil.getHttpServletRequest(actionRequest);
//		// HttpSession session=PortalUtil.getOriginalServletRequest(request).getSession();
//		
//		/**这个就是user_ID*/
//		String userName=
//				ParamUtil.getString(actionRequest, "userName");
//		String password=ParamUtil.getString(actionRequest, "password");
//		String exit=ParamUtil.getString(actionRequest, "exit");
//		/**退出登录*/
//		if (exit.equals("exit")) {
//			SessionManager.removeSession("sessionId");
//			actionResponse.setRenderParameter("mvcPath","/view.jsp");
//		}
//		User user=new User();
//		try {
//			user=userService.findByUserId(userName);
//			
//		} catch (Exception e) {
//			user=null;
//		}
//		if (user.getUser_id()==null){
//			
//			actionRequest.setAttribute("error", "用户不存在");
//		}
//		else if (user.getPassword().trim().equals(password.trim())) {
//			/**用户中文名*/
//			String name=user.getUser_name();
//			SessionManager.setAttribute("sessionId", "userName", userName);
//			SessionManager.setAttribute("ses", "user_name", name);
// 
//			//actionResponse.setRenderParameter("mvcPath","/view.jsp");
//			
//		}
//		else {
//			//actionRequest.setAttribute("userName", userName);
//			actionRequest.setAttribute("error", "密码错误");
//		}
//	}
//}
