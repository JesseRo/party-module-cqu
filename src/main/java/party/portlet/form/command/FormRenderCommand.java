package party.portlet.form.command;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;
import party.portlet.form.FormDesign;
import party.portlet.form.FormDesignDao;
import party.portlet.form.Entity.Column;
import party.portlet.form.Entity.ColumnType;
import party.portlet.form.Entity.FormDefinition;
import party.portlet.form.Entity.Option;

@Component(
		immediate = true,
		property = {
		"javax.portlet.name=" + PartyPortletKeys.Form,
		"mvc.command.name=/form/render"
		},
		service = MVCRenderCommand.class
	)
public class FormRenderCommand implements MVCRenderCommand {
	FormDesignDao formDesignDao = new FormDesignDao();
	
	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		// TODO Auto-generated method stub

		String designId = renderRequest.getPreferences().getValue("designId", null);
		String view = "/jsp/form/formDefault.jsp";
		if (!StringUtils.isEmpty(designId)){
			FormDesign formDesign = formDesignDao.findOne(designId);
			String submmit;
			String design = formDesign.getDesign();
			Gson gson = new Gson();
			FormDefinition formDefinition = gson.fromJson(design, FormDefinition.class);
			Set<Column> chosenColumn = formDefinition.getColumns().stream()
				.filter(p->(p.getType().getName().equals(ColumnType.TYPE_CHOSEN) || p.getType().getName().equals(ColumnType.TYPE_CHOSEN_MULTIPLE))
				&& !StringUtils.isEmpty(p.getType().getOptionSql()))
				.collect(Collectors.toSet());
			for(Column column: chosenColumn){
				try{
					List<Map<String, Object>> cols = formDesignDao.getJdbcTemplate().queryForList(column.getType().getOptionSql());
					String nameKey = column.getType().getOptionNameField();
					String valueKey = column.getType().getOptionValueField();
					List<Option> options = cols.stream().map(p->new Option((String)p.get(nameKey), p.get(valueKey))).collect(Collectors.toList());
					column.getType().setOptions(options);	
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			renderRequest.setAttribute("design", gson.toJson(formDefinition));
			if (!StringUtils.isEmpty(formDesign.getRender_jsp())) {
				view = formDesign.getRender_jsp();
			}
			if (!StringUtils.isEmpty(formDesign.getDesign())) {
				submmit = formDesign.getSubmit_command();
			}else {
				submmit = "/form/submit";
			}
		    renderRequest.setAttribute("map", SessionManager.getAttribute(renderRequest.getRequestedSessionId()+"map", "map"));
			renderRequest.setAttribute("submitCommand", formDesign.getSubmit_command()); 
			renderRequest.setAttribute("title", formDesign.getName()); 
		}
		return view;
	}
	
}