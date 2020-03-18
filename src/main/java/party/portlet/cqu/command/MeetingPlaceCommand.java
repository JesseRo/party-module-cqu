package party.portlet.cqu.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.cqu.dao.PlaceDao;
import party.portlet.cqu.entity.Place;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import java.io.PrintWriter;
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

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String campus = ParamUtil.getString(resourceRequest, "campus");
        String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");


        PrintWriter printWriter = null;
        try{
            printWriter = resourceResponse.getWriter();
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(!StringUtils.isEmpty(campus)){
                List<Place> places = placeDao.getPlace(orgId, campus);
                printWriter.write(JSON.toJSONString(ResultUtil.success(places)));
            }else{
                printWriter.write(JSON.toJSONString(ResultUtil.fail("校区campus不能为空")));
            }
        } catch (Exception e) {
            printWriter.write(JSON.toJSONString(ResultUtil.fail("访问数据出错，请联系技术人员.")));
            e.printStackTrace();
        }
        return false;
    }
}
