package party.portlet.report;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportTaskOrgDao;
import party.portlet.report.entity.view.ExcelHandler;
import party.portlet.report.entity.view.FileView;
import party.portlet.report.entity.view.WordHandler;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=支部数据上报列表",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/report/brunch_list.jsp",
                "javax.portlet.name=" + PartyPortletKeys.BrunchReportListPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class BrunchReportListPortlet extends MVCPortlet {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Reference
    private ReportTaskOrgDao taskOrgDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String sessionId = renderRequest.getRequestedSessionId();
        int page = ParamUtil.getInteger(renderRequest, "pageNo");
        if (page <= 0 ){
            page = 1;
        }
        String userName = SessionManager.getAttribute(sessionId, "userName").toString();
        String department = SessionManager.getAttribute(sessionId, "department").toString();
        PostgresqlQueryResult<Map<String, Object>> pageData = taskOrgDao.findPage(department, page);
        for (Map<String , Object> data : pageData.getList()){
            String json = (String)data.get("templateFiles");
            String taskId = (String)data.get("task_id");
            List<ExcelHandler> templateExcelHandlers = gson.fromJson(json, new TypeToken<List<ExcelHandler>>(){}.getType());
            List<FileView> templateFileViews = templateExcelHandlers.stream()
                    .map(p->new FileView(p.getFileName(), "/ajaxFileName/" + taskId + "/" + p.getFileName()))
                    .collect(Collectors.toList());
            String templateWordJson = (String)data.get("wordTemplateFiles");
            List<WordHandler> templateWordHandlers = gson.fromJson(templateWordJson, new TypeToken<List<WordHandler>>(){}.getType());
            List<FileView> templateWordFileViews = templateWordHandlers.stream()
                    .map(p->new FileView(p.getFileName(), "/ajaxFileName/" + taskId + "/" + p.getFileName()))
                    .collect(Collectors.toList());
            templateFileViews.addAll(templateWordFileViews);
            data.put("templateFileView", templateFileViews);
            if (data.get("status").equals(ConstantsKey.REPORTED)){
                String uploadJson = (String)data.get("uploadFiles");
                List<ExcelHandler> uploadExcelHandlers = gson.fromJson(uploadJson, new TypeToken<List<ExcelHandler>>(){}.getType());
                List<FileView> uploadFileViews = uploadExcelHandlers.stream()
                        .map(p->new FileView(p.getFileName(), "/ajaxFileName/" + taskId + "/" + p.getFileName()))
                        .collect(Collectors.toList());
                String wordJson = (String)data.get("wordUploadFiles");
                List<WordHandler> wordHandlers = gson.fromJson(wordJson, new TypeToken<List<WordHandler>>(){}.getType());
                List<FileView> wordFileViews = wordHandlers.stream()
                        .map(p->new FileView(p.getFileName(), "/ajaxFileName/" + taskId + "/" + p.getFileName()))
                        .collect(Collectors.toList());
                uploadFileViews.addAll(wordFileViews);
                data.put("uploadFileView", uploadFileViews);
            }
        }
        renderRequest.setAttribute("pageNo", pageData.getPageNow());
        renderRequest.setAttribute("totalPage",pageData.getTotalPage());
        renderRequest.setAttribute("tasks", pageData.getList());
        super.doView(renderRequest, renderResponse);
    }


}
