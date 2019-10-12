package party.portlet.experienceAndEvaluation;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.toDoList.EvaluationDao;
import hg.util.ConstantsKey;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=分类查看评分详情",
			"javax.portlet.init-param.template-path=/",	
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/experienceAndEvaluation/detail.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/tableData/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.ShowDetails,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class ShowDetails extends MVCPortlet{
	@Reference
	private EvaluationDao dao;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String orgType=SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "orgType").toString();
		HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
		String meetingId = PortalUtil.getOriginalServletRequest(request).getParameter("meetingId");
		meetingId = HtmlUtil.escape(meetingId);
		String branchId = PortalUtil.getOriginalServletRequest(request).getParameter("branchId");
		meetingId = HtmlUtil.escape(meetingId);
		List<Map<String, Object>> list = dao.evaluationDtail(meetingId, branchId);
		Map<String, Object> map = null;
		if (list != null && list.size() > 0) {
			map = list.get(0);
			BigDecimal comments_aspects_one = (BigDecimal) map.get("comments_aspects_one");
			map.put("comments_aspects_one", comments_aspects_one.intValue());

			BigDecimal comments_aspects_two = (BigDecimal) map.get("comments_aspects_two");
			map.put("comments_aspects_two", comments_aspects_two.intValue());

			BigDecimal comments_aspects_three = (BigDecimal) map.get("comments_aspects_three");
			map.put("comments_aspects_three", comments_aspects_three.intValue());

			BigDecimal comments_aspects_four = (BigDecimal) map.get("comments_aspects_four");
			map.put("comments_aspects_four", comments_aspects_four.intValue());

		}
		if (orgType.equals("secondary") || orgType.equals("organization")) {
			renderRequest.setAttribute("display", "none");
			
		}
		renderRequest.setAttribute("evaluation", map);

		super.doView(renderRequest, renderResponse);
	}

}
