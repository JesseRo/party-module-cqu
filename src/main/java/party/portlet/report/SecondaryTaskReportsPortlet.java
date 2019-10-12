package party.portlet.report;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.org.OrgDao;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskOrgDao;
import party.portlet.report.entity.Report;
import party.portlet.report.entity.ReportOrgTask;
import party.portlet.report.entity.view.ExcelHandler;
import party.portlet.report.entity.view.FileView;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=二级党委--数据上报任务-上报详情",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/report/report_detail.jsp",
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskReportPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SecondaryTaskReportsPortlet extends MVCPortlet {
    //上级组织已发送任务列表 上报数据列表
    @Reference
    private ReportDao reportDao;

    @Reference
    private ReportTaskOrgDao reportTaskOrgDao;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Reference
    private OrgDao orgDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
        HttpServletRequest servletRequest = PortalUtil.getOriginalServletRequest(request);
        String taskId = servletRequest.getParameter("task");
        int page = ParamUtil.getInteger(renderRequest, "pageNo");
        if (page <= 0 ){
            page = 1;
        }
        PostgresqlQueryResult<Map<String, Object>> taskPage;
        String statusStr = servletRequest.getParameter("status");

        if (StringUtils.isEmpty(taskId)){
            taskPage = reportDao.findPageByStatus(ConstantsKey.INITIAL, page);
            renderRequest.setAttribute("taskId", "");
        }else{
            if (StringUtils.isEmpty(statusStr)){
                taskPage = reportDao.findPageByTaskId(taskId, page);
            }else {
                int status = Integer.parseInt(statusStr);
                taskPage = reportDao.findPageByTaskIdAndStatus(taskId, status, page);
            }
            renderRequest.setAttribute("taskId", taskId);
        }


        for (Map<String , Object> data : taskPage.getList()){
            String json = (String)data.get("files");
            List<ExcelHandler> excelHandlers = gson.fromJson(json, new TypeToken<List<ExcelHandler>>(){}.getType());
            List<FileView> fileViews = excelHandlers.stream()
                    .map(p->new FileView(p.getFileName(), "/ajaxFileName/" + taskId + "/" + data.get("org_id") + "/" + p.getFileName()))
                    .collect(Collectors.toList());
            data.put("fileView", fileViews);
        }
        renderRequest.setAttribute("pageNo", taskPage.getPageNow());
        renderRequest.setAttribute("totalPage",taskPage.getTotalPage());
        renderRequest.setAttribute("reports", taskPage.getList());
        super.doView(renderRequest, renderResponse);
    }
}
