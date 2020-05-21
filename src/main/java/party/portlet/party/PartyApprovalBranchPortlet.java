package party.portlet.party;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import hg.party.entity.party.Hg_Value_Attribute_Info;
import hg.party.server.dwonlistserver.DownListServer;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.party.PartyMeetingPlanInfoService;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月2日下午6:14:43<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=二级党委审批",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/party/PartyApprovalBranch.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartyApprovalBranch,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartyApprovalBranchPortlet extends MVCPortlet{
	
	Logger logger = Logger.getLogger(PartyApprovalBranchPortlet.class);

	@Reference
	private DownListServer downListServer;

	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		List<Hg_Value_Attribute_Info> listValue = downListServer.reasson();
		renderRequest.setAttribute("reasonList",listValue);
		super.doView(renderRequest, renderResponse);
	}

}

