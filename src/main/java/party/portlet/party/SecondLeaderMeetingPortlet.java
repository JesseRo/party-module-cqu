package party.portlet.party;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.dao.party.PartyMeetingPlanInfoDao;
import hg.party.entity.organization.Organization;
import hg.util.ConstantsKey;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.transport.entity.PageQueryResult;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 文件名称： party<br>
 * 创建人 　： zlm<br>
 * 创建日期： 2018年1月6日下午<br>
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=领导参会检索",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/party/leaderMeeting.jsp",
                "javax.portlet.name=" + PartyPortletKeys.LeaderMeetingStatistics,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class SecondLeaderMeetingPortlet extends MVCPortlet {
    Logger logger = Logger.getLogger(SecondLeaderMeetingPortlet.class);
    @Reference
    private PartyMeetingPlanInfoDao partyMeetingPlanInfoDao;
    //	private DateTimeFormatter dateTimeFormatter = new PortalSimpleDateFormat("yyyy-MM-dd HH:")
    private int pageSize = 8;//每页条数
    @Reference
    private OrgDao orgDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        logger.info("MeetingRecordPortlet doView...");
        String orgId = SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department").toString();
        String startTime = ParamUtil.getString(renderRequest, "startTime");//开展时间
        startTime = HtmlUtil.escape(startTime);
        String endTime = ParamUtil.getString(renderRequest, "endTime");//开展时间
        endTime = HtmlUtil.escape(endTime);
		String seconedId = ParamUtil.getString(renderRequest, "seconedId");//二级党委
		seconedId = HtmlUtil.escape(seconedId);
		String branchId = ParamUtil.getString(renderRequest, "branchId");//党支部
		branchId = HtmlUtil.escape(branchId);
        String leader = ParamUtil.getString(renderRequest, "leader");
        leader = HtmlUtil.escape(leader);

        Organization organization = orgDao.findByOrgId(orgId);
        String orgType = organization.getOrg_type();

        int pageNo = ParamUtil.getInteger(renderRequest, "pageNo");

        if (StringUtils.isEmpty(branchId)) {
            if (StringUtils.isEmpty(seconedId)) {
                if (orgType.equals(ConstantsKey.ORG_TYPE_SECONDARY)) {
                    seconedId = orgId;
                } else if (orgType.equals(ConstantsKey.ORG_TYPE_ROOT)) {
                    orgId = null;
                }
            } else {
                orgId = seconedId;
            }
        } else {
            orgId = branchId;
        }
        PageQueryResult<Map<String, Object>> pageResult = partyMeetingPlanInfoDao.leaderMeetingPage(pageNo, pageSize, seconedId, branchId, startTime, endTime, leader,orgId);
        //获取当前页

        int totalPage = pageResult.getTotalPage();

        renderRequest.setAttribute("list", pageResult.getList());
        renderRequest.setAttribute("pageNo", pageNo);
        renderRequest.setAttribute("totalPage", totalPage);
        renderRequest.setAttribute("totalCount", pageResult.getCount());
        renderRequest.setAttribute("startTime", startTime);
        renderRequest.setAttribute("endTime", endTime);
        renderRequest.setAttribute("seconedId", seconedId);
		renderRequest.setAttribute("branchId", branchId);
		renderRequest.setAttribute("leader", leader);
        renderRequest.setAttribute("orgType", orgType);
        super.doView(renderRequest, renderResponse);
    }

}
