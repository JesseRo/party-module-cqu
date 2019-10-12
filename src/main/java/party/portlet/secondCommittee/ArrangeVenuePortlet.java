package party.portlet.secondCommittee;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.server.secondCommittee.SecondCommitteeService;
import hg.party.server.secondCommittee.SecondCommitteeUtils;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2017年12月26日上午10:54:05<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */

@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=二级党委--配置活动地点",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/secondCommittee/managePlace.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeArrangeVenue,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class ArrangeVenuePortlet extends MVCPortlet {
	
	@Reference
	SecondCommitteeService secondCommitteeService;
	
	Logger logger = Logger.getLogger(ArrangeVenuePortlet.class);
	
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		List<Map<String, Object>> placeList = null;
		try {
	         String sessionId=renderRequest.getRequestedSessionId();
			HttpServletRequest request = PortalUtil.getHttpServletRequest(renderRequest);
			HttpServletRequest originalRequest = PortalUtil.getOriginalServletRequest(request);
			String classstr = originalRequest.getParameter("classstr");
			classstr = HtmlUtil.escape(classstr);
			String floorstr = originalRequest.getParameter("floorstr");
			floorstr = HtmlUtil.escape(floorstr);
			String roomstr = originalRequest.getParameter("roomstr");
			roomstr = HtmlUtil.escape(roomstr);
			String placesIds = originalRequest.getParameter("placesIds");
			placesIds = HtmlUtil.escape(placesIds);
			String pageNoStr = originalRequest.getParameter("pageNo");
			pageNoStr = HtmlUtil.escape(pageNoStr);
			String customPlace = originalRequest.getParameter("customPlace");
			customPlace = HtmlUtil.escape(customPlace);
			String seconedOrgId= (String)SessionManager.getAttribute(sessionId, "department");
			
			String orgId = null;
			//String sessionId=request.getRequestedSessionId();
			try {
				orgId = SessionManager.getAttribute(sessionId, "department").toString();
			} catch (Exception e) {
				logger.info("获取当前session 数据异常:"+ e.getMessage());
			}
			
			/*orgId=(StringUtils.isEmpty(orgId)) ? "050026623508":orgId;*/
			
			int pageNo = 1;
			if(!StringUtils.isEmpty(pageNoStr)){
				pageNo = Integer.valueOf(pageNoStr);
				pageNo = (pageNo >=1 )? pageNo:1;
				logger.info("pageNo :" + pageNo);
			}
			if(!StringUtils.isEmpty(placesIds)){
				logger.info("placeIds:" + placesIds);
				secondCommitteeService.delectePlaceById(placesIds);
			}
			
			if (!StringUtils.isEmpty(classstr) && !StringUtils.isEmpty(classstr) && !StringUtils.isEmpty(classstr)) {
				logger.info("classstr :" + classstr);
				logger.info("floorstr :" + floorstr);
				logger.info("roomstr :" + roomstr);
				String place = classstr + "教/楼" + floorstr +"层"+ roomstr +"室";
				String placeId = SecondCommitteeUtils.createPlaceeId();
				
				String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date());  
				Timestamp now = Timestamp.valueOf(current); 
				if(!StringUtils.isEmpty(customPlace)){place=customPlace;}
				Boolean placeExist = secondCommitteeService.isPlaceExist(place,orgId);
				if (placeExist) {
					renderRequest.setAttribute("msg1", "该地点已经存在！");
				}
				if(!placeExist){
					secondCommitteeService.savePlace(place, placeId,now,seconedOrgId);
				}
			}else if (!StringUtils.isEmpty(customPlace)) {
				String placeId = SecondCommitteeUtils.createPlaceeId();
				String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( new Date());  
				Timestamp now = Timestamp.valueOf(current); 				
				Boolean placeExist = secondCommitteeService.isPlaceExist(customPlace,orgId);
				if (placeExist) {
					renderRequest.setAttribute("msg2", "该地点已经存在！");
				}
				if(!placeExist){
					secondCommitteeService.savePlace(customPlace, placeId,now,seconedOrgId);
				}
			}
			Map<String, Object>  meetingplaces = secondCommitteeService.queryPlacesByPage(pageNo,seconedOrgId);
			placeList = (List<Map<String, Object>>) meetingplaces.get("list");
			logger.info("placeList :" + placeList);
			renderRequest.setAttribute("pageNo", meetingplaces.get("pageNow"));
			renderRequest.setAttribute("pages", meetingplaces.get("totalPage"));
			renderRequest.setAttribute("placeList", placeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.doView(renderRequest, renderResponse);
	}

}
