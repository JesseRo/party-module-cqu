package party.portlet.cqu.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.CheckPersonDao;
import party.portlet.cqu.entity.CheckPerson;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.CheckPerson,
                "mvc.command.name=/hg/check/add"
        },
        service = MVCResourceCommand.class
)
public class CheckPersonAddCommand implements MVCResourceCommand {
    @Reference
    private CheckPersonDao checkPersonDao;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String campus = ParamUtil.getString(resourceRequest, "campus");
        String userId = ParamUtil.getString(resourceRequest, "userId");
        String type = ParamUtil.getString(resourceRequest, "type");

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type","application/json");
        try {
            CheckPerson checkPerson = new CheckPerson();
            checkPerson.setCampus(campus);
            checkPerson.setUser_id(userId);
            checkPerson.setType(type);
            checkPersonDao.saveOrUpdate(checkPerson);
            res.getWriter().write(gson.toJson(JsonResponse.Success()));
        } catch (Exception e) {
            try {
                e.printStackTrace();
                res.getWriter().write(gson.toJson(JsonResponse.Failure("新增失败")));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
}
