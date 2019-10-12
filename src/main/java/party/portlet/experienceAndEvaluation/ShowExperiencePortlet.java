package party.portlet.experienceAndEvaluation;

import java.io.IOException;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.server.toDoList.ExperienceServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月8日下午2:55:32<br>
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
			"javax.portlet.display-name=查看党员心得",
			"javax.portlet.init-param.template-path=/",	
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/experienceAndEvaluation/experienceView.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/tableData/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.ShowExperience,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class ShowExperiencePortlet extends MVCPortlet {

	@Reference
	private ExperienceServer experienceServer;
	Logger logger = Logger.getLogger(ShowExperiencePortlet.class);
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		
		String meetingId = PortalUtil.getOriginalServletRequest(request).getParameter("meetingId");
		meetingId = HtmlUtil.escape(meetingId);
		String userId = PortalUtil.getOriginalServletRequest(request).getParameter("userId");
		userId = HtmlUtil.escape(userId);
		logger.info("meetingId :" + meetingId);
		logger.info("userId :" + userId);
		
		if(!StringUtils.isEmpty(meetingId) && !StringUtils.isEmpty(userId)){
			Map<String,Object> map=experienceServer.findExperienceDetails(meetingId, userId);
			renderRequest.setAttribute("experience", map);
		}else{
			logger.info("查看学习心得参数错误。");
		}
		super.doView(renderRequest, renderResponse);
	}
}
