package hg.party.command.secondCommittee;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.entity.organization.PublicInformation;
import hg.party.server.secondCommittee.SecondCommitteeService;
import hg.party.server.secondCommittee.SecondCommitteeUtils;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2017年12月27日下午3:11:52<br>
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
			"javax.portlet.name="+ PartyPortletKeys.SeocndCommitteeArrangeVenue,
			"mvc.command.name=/hg/addPlace"
	    },
	    service = MVCActionCommand.class
)
public class PlaceAddCommand implements MVCActionCommand{
	@Reference
	SecondCommitteeService secondCommitteeService;

	Logger logger = Logger.getLogger(PlaceAddCommand.class);
	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		
		return false;
	}
	
	

}
