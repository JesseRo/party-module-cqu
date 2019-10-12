package party.portlet.org.command;

import java.io.IOException;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.dt.springjdbc.dao.impl.QueryResult;
import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.util.ConstantsKey;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Org,
				"mvc.command.name=/org/member"
	    },
	    service = MVCResourceCommand.class
)
public class MemberResourceCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
	    
		
		String orgId = ParamUtil.getString(resourceRequest, "orgId");//historic_root  current_root
		orgId = HtmlUtil.escape(orgId);
		String history = ParamUtil.getString(resourceRequest, "history");
		history = HtmlUtil.escape(history);
		String page = ParamUtil.getString(resourceRequest, "page");
		page = HtmlUtil.escape(page);
		String pageNow = ParamUtil.getString(resourceRequest, "pageNow");//当前页
		pageNow = HtmlUtil.escape(pageNow);
		
		if (StringUtils.isEmpty(pageNow)) {
			page = "1";
		}
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		res.addHeader("content-type","application/json");
		Gson gson = new Gson();
		try {
			QueryResult<Member> queryResult =null;
			if("西南大学党委".equals(orgId)){
				queryResult = memberDao.findByOrgAll(orgId, Integer.parseInt(pageNow));
				if ("historic_root".equals(history)) {
					queryResult =  memberDao.findByOrgAll(Integer.parseInt(pageNow));
				}
			}else if(ConstantsKey.ORG_TYPE_SECONDARY.equals(memberDao.ment(orgId))){
			    queryResult = memberDao.findByOrgLevel(orgId,Integer.parseInt(pageNow));
			    if ("historic_root".equals(history)) {
					queryResult =  memberDao.findByOrgLevelHistory(orgId,Integer.parseInt(pageNow));
				}
			}else {
				queryResult = memberDao.findByOrg(orgId, Integer.parseInt(pageNow));
				if ("historic_root".equals(history)) {
					queryResult =  memberDao.findByOrgHistory(orgId, Integer.parseInt(pageNow));
				}
			}
			res.getWriter().write(gson.toJson(JsonResponse.Success(queryResult)));
		} catch (Exception e) {
			// TODO: handle exception
			try {
				e.printStackTrace();
				res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		return false;
	}

}
