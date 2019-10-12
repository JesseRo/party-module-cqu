package hg.party.command.secondCommittee;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.secondCommittee.OrgInformDao;
import hg.party.dao.secondCommittee.OtherDao;
import hg.party.entity.party.OrgInform;
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
			"javax.portlet.name="+ PartyPortletKeys.DetailPortlet,
			"mvc.command.name=/hg/confirmDetail"
	    },
	    service = MVCRenderCommand.class
)
public class ConfirmDetailCommand implements MVCRenderCommand{
	@Reference
	SecondCommitteeService secondCommitteeService;
	@Reference
	OrgInformDao orgInformDao;
	@Reference
	OtherDao otherDao;

	Logger logger = Logger.getLogger(ConfirmDetailCommand.class);
	@Override
	@Transactional
	public String render(RenderRequest request, RenderResponse response) throws PortletException {
		
		
		try {
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			String informId = ParamUtil.get(httpRequest, "informId", "");
			informId = HtmlUtil.escape(informId);
			String taskstatus = ParamUtil.get(httpRequest, "readStatus", "");
			taskstatus = HtmlUtil.escape(taskstatus);
			String resend = ParamUtil.getString(httpRequest, "sendto");
			resend = HtmlUtil.escape(resend);
			logger.info("informId :" + informId);	
			
			String orgId = "050026623506";
			String orgType = "branch";
			String sessionId=request.getRequestedSessionId();
			orgId = (String)SessionManager.getAttribute(sessionId, "department");
			orgType = (String)SessionManager.getAttribute(sessionId, "orgType");
			orgId=(StringUtils.isEmpty(orgId)) ? "050026623506":orgId;
			orgType=(StringUtils.isEmpty(orgType)) ? "branch":orgType;
			
			String redirect = "/backlogtwo";
			String redirectBranch = "/task";
			String send_toBranch;
			if (resend.equalsIgnoreCase("secondary") || resend.isEmpty()) {
				send_toBranch = "f";
			}else{
				send_toBranch = "t";
				redirect = "/newinfo?informId=" + informId + "&resend=resend";
			}
			otherDao.updateOrgInform(orgId, informId, send_toBranch);			
			if(!StringUtils.isEmpty(informId)){
				    logger.info("resourceId   is" + informId );
					String informStatus = (String) secondCommitteeService.queryInformByInformorgId(informId, orgId).get(0).get("read_status");
					logger.info("informStatus   is " + informStatus );				
					if( "未读".equals( informStatus )){
						String nextStatus ="已查看";
						secondCommitteeService.updateInformStatus(informId, orgId, nextStatus);
					}
			}
			HttpServletResponse response2 = PortalUtil.getHttpServletResponse(response);
			String orgType2=(String)SessionManager.getAttribute(sessionId, "orgType");
			try {
				if("secondary".equalsIgnoreCase(orgType)){
					response2.sendRedirect(redirect);
				}else if ("organization".equals(orgType2) || "propaganda".equals(orgType2)) {
					response2.sendRedirect("/passpublic");
				}else{
					response2.sendRedirect(redirectBranch);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return null;
	}

}
