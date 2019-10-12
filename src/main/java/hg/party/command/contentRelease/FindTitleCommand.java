package hg.party.command.contentRelease;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.contentInfo.ContentInfoServer;
import party.constants.PartyPortletKeys;

/**
 * 内容摘要： 查询
 * 创建人 　： Zhong LiMei
 * 创建日期： 2017年10月23日上午10:55:38
 */
@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
				"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
				"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
				"mvc.command.name=/contentReleaseFindTitleRender"
		},
		service = MVCActionCommand.class
		)
public class FindTitleCommand implements MVCActionCommand {

	@Override
	public boolean processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) throws PortletException {
		return false;
	}
}


