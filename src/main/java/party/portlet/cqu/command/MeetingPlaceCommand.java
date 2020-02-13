package party.portlet.cqu.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.PlaceDao;
import party.portlet.cqu.entity.Place;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Form,
                "mvc.command.name=/hg/place/list"
        },
        service = MVCResourceCommand.class
)
public class MeetingPlaceCommand implements MVCResourceCommand {
    @Reference
    private PlaceDao placeDao;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
//        String orgId = ParamUtil.getString(resourceRequest, "orgId");
        String campus = ParamUtil.getString(resourceRequest, "campus");
        String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");

        List<Place> places = placeDao.getPlace(orgId, campus);

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type","application/json");
        try {
            res.getWriter().write(gson.toJson(JsonResponse.Success(places)));
        } catch (Exception e) {
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
