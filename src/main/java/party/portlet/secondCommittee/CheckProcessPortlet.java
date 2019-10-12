package party.portlet.secondCommittee;

import java.io.IOException;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import hg.party.server.secondCommittee.SecondCommitteeService;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2017年12月26日上午10:54:05<br>
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
			"javax.portlet.display-name=二级党委--查看进度",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/secondCommittee/checkProgress.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeUploadNotes,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class CheckProcessPortlet extends MVCPortlet {
	@Reference
	SecondCommitteeService secondCommitteeService;
	Logger logger = Logger.getLogger(CheckProcessPortlet.class);
	
	@Override
	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		
//		String resourceId = ParamUtil.getString(request, "resourceId");
//		
//		request.setAttribute("conferenceNote", "");
//		request.setAttribute("leaveStaffs", "");
		super.doView(request, response);
	}

}
