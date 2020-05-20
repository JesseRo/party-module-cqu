package party.portlet.report;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportTaskDao;
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
                "javax.portlet.display-name=二级党委--已发送数据上报任务",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/report/secondary.jsp",
                "javax.portlet.name=" + PartyPortletKeys.SecondaryTaskPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SecondaryTaskPortlet extends MVCPortlet {
    //上级组织已发送任务列表
    @Reference
    private ReportTaskDao taskDao;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Reference
    private OrgDao orgDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {

        String orgId = (String) SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department");
        int page = ParamUtil.getInteger(renderRequest, "pageNo");
        if (page <= 0 ){
            page = 1;
        }
        PostgresqlQueryResult<Map<String, Object>> taskPage = taskDao.findPageByOrgIdAndStatus(orgId, ConstantsKey.PUBLISHED, page);
        for (Map<String , Object> data : taskPage.getList()){
            String json = (String)data.get("files");
            String taskId = (String)data.get("task_id");
            List<ExcelHandler> excelHandlers = gson.fromJson(json, new TypeToken<List<ExcelHandler>>(){}.getType());
            List<FileView> fileViews = excelHandlers.stream()
                    .map(p->new FileView(p.getFileName(), "/ajaxFileName/" + taskId + "/" + p.getFileName()))
                    .collect(Collectors.toList());
            String wordJson = (String)data.get("word_files");
            List<WordHandler> wordHandlers = gson.fromJson(wordJson, new TypeToken<List<WordHandler>>(){}.getType());
            List<FileView> wordFileViews = wordHandlers.stream()
                    .map(p->new FileView(p.getFileName(), "/ajaxFileName/" + taskId + "/" + data.get("org_id") + "/" + p.getFileName()))
                    .collect(Collectors.toList());
            fileViews.addAll(wordFileViews);
            data.put("fileView", fileViews);
        }
        renderRequest.setAttribute("pageNo", taskPage.getPageNow());
        renderRequest.setAttribute("totalPage",taskPage.getTotalPage());
        renderRequest.setAttribute("tasks", taskPage.getList());
        super.doView(renderRequest, renderResponse);
    }


}
