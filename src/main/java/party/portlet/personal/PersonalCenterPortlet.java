package party.portlet.personal;

import java.io.IOException;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import dt.session.SessionManager;
import hg.party.server.personalCenter.PersonalCenterService;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： 个人中心<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月16日上午11:47:19<br>
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
		"javax.portlet.display-name=个人中心",
		"javax.portlet.init-param.template-path=/",
		
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.portlet-mode=text/html;view,edit",
		"javax.portlet.init-param.view-template=/jsp/personalCenter/pc.jsp",
		"javax.portlet.name=" + PartyPortletKeys.PersonalCenter,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class PersonalCenterPortlet extends MVCPortlet {
	Logger log = Logger.getLogger(PersonalCenterPortlet.class);
	@Reference
	private PersonalCenterService personalCenterService;
	
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		Map<String,Object> map = null;
		String sessionId=renderRequest.getRequestedSessionId();
		String userName = null;
		try {
			userName = SessionManager.getAttribute(sessionId, "userName").toString();
		} catch (Exception e) {
			log.info("用户未登录");
		}
		if(userName!=null){
			map = personalCenterService.findUserInfo(userName);
		}
		
		renderRequest.setAttribute("userInfo", map);
		super.doView(renderRequest, renderResponse);
	}
}
