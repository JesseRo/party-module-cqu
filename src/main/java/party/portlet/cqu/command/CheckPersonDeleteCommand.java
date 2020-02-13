package party.portlet.cqu.command;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.entity.partyMembers.JsonPageResponse;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.CheckPersonDao;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.CheckPerson,
                "mvc.command.name=/hg/check/delete"
        },
        service = MVCResourceCommand.class
)
public class CheckPersonDeleteCommand implements MVCResourceCommand {
    @Reference
    private CheckPersonDao checkPersonDao;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        int id = ParamUtil.getInteger(resourceRequest, "id");

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type","application/json");
        try {
            checkPersonDao.delete(id);
            res.getWriter().write(gson.toJson(JsonResponse.Success()));
        } catch (Exception e) {
            try {
                e.printStackTrace();
                res.getWriter().write(gson.toJson(JsonResponse.Failure("删除失败")));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
}
