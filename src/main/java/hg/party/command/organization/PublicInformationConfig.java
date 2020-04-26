package hg.party.command.organization;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.print.DocFlavor.STRING;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import org.w3c.dom.ls.LSInput;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.organization.GraftService;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;
import party.portlet.form.command.FormRenderCommand;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Form,
				"javax.portlet.name=" + PartyPortletKeys.NewPlan,
				"mvc.command.name=/hg/graftEdit"
        },
        service = MVCRenderCommand.class
)
public class PublicInformationConfig extends FormRenderCommand {
    @Reference
    GraftService service;
    @Reference
    PartyBranchService partyBranchService;

    @Override
    public String render(RenderRequest request, RenderResponse response)
            throws PortletException {
		String informId = ParamUtil.getString(request, "informId");
		String orgId = (String) SessionManager.getAttribute(request.getRequestedSessionId(), "department");
		String orgType = partyBranchService.findSconedAndBranch(orgId);
		String resend = ParamUtil.getString(request, "resend");
		String orgEdit = ParamUtil.getString(request, "orgEdit");
		informId = HtmlUtil.escape(informId);
		resend = HtmlUtil.escape(resend);
		orgEdit = HtmlUtil.escape(orgEdit);

		if (!StringUtils.isEmpty(informId)) {
			List<Map<String, Object>> list = service.findGraftDetail(informId);
			Map<String, Object> map = list.get(0);
			Object informationContent = map.get("content");
			map.put("content", null);
			request.setAttribute("informationContent", informationContent);
			request.setAttribute("informData", JSON.toJSON(map));
					if (!StringUtils.isEmpty(resend)) {
						list.get(0).put("graftEditState", "resend");
						request.setAttribute("resend", "resend");
					} else if(!StringUtils.isEmpty(orgEdit)){
						list.get(0).put("graftEditState", "orgEdit");
						request.setAttribute("orgEdit", "orgEdit");
					}else {
						list.get(0).put("graftEditState", "edit");
					}
			request.setAttribute("informData", JSON.toJSON(list.get(0)));
		} else if (StringUtils.isEmpty(informId)) {
			request.setAttribute("informData", null);
		}
		if ("organization".equals(orgType)) {
			request.setAttribute("publicObjectTitle", "二级党组织");
			request.setAttribute("orgType", orgType);
		} else {
			request.setAttribute("publicObjectTitle", "党支部");
			request.setAttribute("orgType", orgType);
		}
		String formId = UUID.randomUUID().toString();
		SessionManager.setAttribute(request.getRequestedSessionId(), "formId-publicInformation", formId);
		request.setAttribute("formId", formId);
		
		return super.render(request, response);
	}

}
