package party.portlet.cqu.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.util.TransactionUtil;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.PlaceDao;
import party.portlet.cqu.entity.Place;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.PrintWriter;
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

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String campus = ParamUtil.getString(resourceRequest, "campus");
        String placeName = ParamUtil.getString(resourceRequest, "place");
        String org = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
        PrintWriter printWriter = null;
        try{
            printWriter = resourceResponse.getWriter();
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Place place = new Place();
            place.setCampus(campus);
            place.setPlace(placeName);
            String placeId = UUID.randomUUID().toString();
            place.setPlace_id(placeId);
            place.setAdd_time(new Timestamp(System.currentTimeMillis()));
            place.setOrg_id(org);
            int res = placeDao.saveOrUpdate(place);
            if(res > 0 ){
                printWriter.write(JSON.toJSONString(ResultUtil.success(place)));
            }else{
                printWriter.write(JSON.toJSONString(ResultUtil.fail("添加失败")));
            }

        } catch (Exception e) {
            printWriter.write(JSON.toJSONString(ResultUtil.fail("访问数据出错，请联系技术人员.")));
            e.printStackTrace();
        }
        return false;
    }
}
