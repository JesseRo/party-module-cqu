package party.portlet.partyBranch;

import java.io.IOException;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=个人学习心得详情页面",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/partyBranch/personalExperienceDetail.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PersonalExperienceDetail,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PersonalExperienceDetailPortlet extends MVCPortlet{
	@Reference 
	PartyBranchService service;
	
	Logger logger = Logger.getLogger(PersonalExperienceDetailPortlet.class);
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		super.doView(renderRequest, renderResponse);
	}

}
