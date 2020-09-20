package party.portlet.transport.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.entity.ReportTask;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.SecondaryTaskPortlet,
				"mvc.command.name=/task/delete"
	    },
	    service = MVCResourceCommand.class
)
public class TaskDeleteCommand implements MVCResourceCommand {
	@Reference
	private ReportTaskDao taskDao;

	@Reference
	private ReportDao reportDao;

	@Reference
	private TransactionUtil transactionUtil;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "orgId");
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		String taskId = ParamUtil.getString(resourceRequest, "taskId");
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		try {
			transactionUtil.startTransaction();
			ReportTask task = taskDao.findByTaskId(taskId);
			if (!StringUtils.isEmpty(task)) {
				reportDao.deleteByTaskId(task.getTask_id());
				res.getWriter().write(gson.toJson(JsonResponse.Success()));
			}else {
                res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
            }
			transactionUtil.commit();
		} catch (Exception e) {
			transactionUtil.rollback();
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

}
