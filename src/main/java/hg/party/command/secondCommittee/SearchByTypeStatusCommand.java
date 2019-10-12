package hg.party.command.secondCommittee;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.secondCommittee.SecondCommitteeService;
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
			"javax.portlet.name="+ PartyPortletKeys.SeocndCommitteeToDoList,
			"mvc.command.name=/hg/searchByTypeStatus"
	    },
	    service = MVCRenderCommand.class
)
public class SearchByTypeStatusCommand implements MVCRenderCommand{
	@Reference
	SecondCommitteeService secondCommitteeService;
	
	Logger logger = Logger.getLogger(SearchByTypeStatusCommand.class);


	@Override
	public String render(RenderRequest request, RenderResponse response) throws PortletException {
		
		return "/jsp/secondCommittee/backlogtwo.jsp";
	}

}
