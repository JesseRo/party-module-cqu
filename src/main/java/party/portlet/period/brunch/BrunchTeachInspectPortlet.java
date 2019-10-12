package party.portlet.period.brunch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.portlet.period.PeriodKeys;
import party.portlet.period.dao.Period;
import party.portlet.period.dao.PeriodDao;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=" + PeriodKeys.BrunchApplicationTeachInspectName,
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/period/brunch/teachInspect.jsp",
                "javax.portlet.name=" + PeriodKeys.BrunchApplicationTeachInspect,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
// 培养教育考察
public class BrunchTeachInspectPortlet extends MVCPortlet {
    @Reference
    private PeriodDao periodDao;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        Period period = periodDao.findByName(PeriodKeys.BrunchApplicationTeachInspect);
        String json = period.getContent();
        List<List<String>> content = gson.fromJson(json, new TypeToken<List<List<String>>>(){}.getType());
        List<String> title = gson.fromJson(period.getTitle(), new TypeToken<List<String>>(){}.getType());
        renderRequest.setAttribute("title", title);
        renderRequest.setAttribute("content", content);
        super.doView(renderRequest, renderResponse);
    }


}
