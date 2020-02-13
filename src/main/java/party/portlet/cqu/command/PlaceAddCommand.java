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
import party.portlet.cqu.dao.CheckPersonDao;
import party.portlet.cqu.dao.PlaceDao;
import party.portlet.cqu.entity.CheckPerson;
import party.portlet.cqu.entity.Place;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Form,
                "mvc.command.name=/hg/place/add"
        },
        service = MVCResourceCommand.class
)
public class PlaceAddCommand implements MVCResourceCommand {
    @Reference
    private PlaceDao placeDao;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String campus = ParamUtil.getString(resourceRequest, "campus");
        String placeName = ParamUtil.getString(resourceRequest, "place");
        String org = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type","application/json");
        try {
            Place place = new Place();
            place.setCampus(campus);
            place.setPlace(placeName);
            String placeId = UUID.randomUUID().toString();
            place.setPlace_id(placeId);
            place.setAdd_time(new Timestamp(System.currentTimeMillis()));
            place.setOrg_id(org);
            placeDao.saveOrUpdate(place);
            place = placeDao.findByPlaceId(placeId);
            res.getWriter().write(gson.toJson(JsonResponse.Success(place)));
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
