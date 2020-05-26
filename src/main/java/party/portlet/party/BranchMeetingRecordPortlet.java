package party.portlet.party;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.command.exportexcl.ExprotUntil;
import hg.party.dao.partyBranch.PartyBranchDao;
import hg.party.server.party.PartyMeetingPlanInfoService;
import party.constants.PartyPortletKeys;

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
                "javax.portlet.display-name=党支部-会议记录",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/party/branchmeetingcount.jsp",
                "javax.portlet.name=" + PartyPortletKeys.BranchMeetingRecordPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class BranchMeetingRecordPortlet extends MVCPortlet {
    Logger logger = Logger.getLogger(BranchMeetingRecordPortlet.class);
    @Reference
    private PartyBranchDao dao;
    @Reference
    private PartyMeetingPlanInfoService partyMeetingPlanInfoService;
    private int pageSize = 8;//每页条数

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        logger.info("MeetingRecordPortlet doView...");
        String orgId = SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "department").toString();
        String startTime = ParamUtil.getString(renderRequest, "startTime");//开展时间
        startTime = HtmlUtil.escape(startTime);
        String endTime = ParamUtil.getString(renderRequest, "endTime");//开展时间
        endTime = HtmlUtil.escape(endTime);
//		String seconedId = ParamUtil.getString(renderRequest, "seconedId");//二级党委
//		seconedId = HtmlUtil.escape(seconedId);
//		String branchId = ParamUtil.getString(renderRequest, "branchId");//二级党委
//		branchId = HtmlUtil.escape(branchId);
        String meetTheme = ParamUtil.getString(renderRequest, "meetTheme");//会议主题
        meetTheme = HtmlUtil.escape(meetTheme);
        String meetType = ParamUtil.getString(renderRequest, "meetType");//会议类型
        meetType = HtmlUtil.escape(meetType);
        int pageNo = ParamUtil.getInteger(renderRequest, "pageNo");
        String checkState = "";
        String seconedId = dao.getOrgParentIdByBranchId(orgId).get(0).get("org_parent").toString();
        //获取当前页
        int totalCount = partyMeetingPlanInfoService.count(startTime, endTime, meetType, meetTheme,
                seconedId, orgId, "");
        int pageSize = 8;
        int totalPage = totalCount / pageSize;
        if (totalCount % pageSize != 0) {
            totalPage = totalPage + 1;
        }
        if (pageNo == 0) {
            pageNo = 1;//默认当前页为1
        } else if (pageNo > totalPage) {
            pageNo = totalPage;
        }
        List<Map<String, Object>> listResult =
                partyMeetingPlanInfoService.find(startTime, endTime, meetType, meetTheme, seconedId, orgId, pageSize,
                        (pageNo - 1) * pageSize, "");
        renderRequest.setAttribute("list", listResult);
        renderRequest.setAttribute("pageNo", pageNo);
        renderRequest.setAttribute("totalPage", totalPage);
        renderRequest.setAttribute("startTime", startTime);
        renderRequest.setAttribute("endTime", endTime);
        renderRequest.setAttribute("seconedId", seconedId);
        renderRequest.setAttribute("branchId", orgId);
        renderRequest.setAttribute("meetTheme", meetTheme);
        renderRequest.setAttribute("meetType", meetType);
        super.doView(renderRequest, renderResponse);
    }

}

