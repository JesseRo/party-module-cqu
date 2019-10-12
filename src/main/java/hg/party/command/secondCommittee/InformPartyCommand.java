package hg.party.command.secondCommittee;


import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.Member;
import hg.party.server.secondCommittee.SecondCommitteeService;
import hg.party.server.sms.SmsService;
import hg.util.TransactionUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.quartz.SchedulerException;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2017年12月27日下午3:11:52<br>
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
                "javax.portlet.name=" + PartyPortletKeys.Form,
                "mvc.command.name=/hg/informParty"
        },
        service = MVCRenderCommand.class
)
public class InformPartyCommand implements MVCRenderCommand {
    @Reference
    SecondCommitteeService secondCommitteeService;

    Logger logger = Logger.getLogger(InformPartyCommand.class);
    @Reference
    private MemberDao memberDao;
    @Reference
    private OrgDao orgDao;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Reference
    TransactionUtil transactionUtil;

    @Override
    public String render(RenderRequest request, RenderResponse response) throws PortletException {
        String sessionId = request.getRequestedSessionId();
        String orgId = (String) SessionManager.getAttribute(sessionId, "department");
        String formId = ParamUtil.getString(request, "formId");
        String path = ParamUtil.getString(request, "sendpath");
        String meetingId = ParamUtil.getString(request, "meetingId");
        String groupId = ParamUtil.getString(request, "groupId");
        formId = HtmlUtil.escape(formId);
        path = HtmlUtil.escape(path);
        meetingId = HtmlUtil.escape(meetingId);
        groupId = HtmlUtil.escape(groupId);
        
        synchronized (PortalUtil.getHttpServletRequest(request).getSession()) {
            String originalFormId = (String) SessionManager.getAttribute(request.getRequestedSessionId(), "formId-informParty");
            if (formId.equals(originalFormId)) {
                List<Member> members;
                String meeting_theme_secondary = ParamUtil.getString(request, "meeting_theme_secondary");
                String theme = ParamUtil.getString(request, "theme");
                String type = ParamUtil.getString(request, "type");
                String startTime = ParamUtil.getString(request, "startTime");
                String place = ParamUtil.getString(request, "place");
                String runtime = ParamUtil.getString(request, "runtime");
                transactionUtil.startTransaction();
                try {
                    List<Map<String, Object>> partyList = secondCommitteeService.queryPartys(groupId);
                    for (Map<String, Object> map : partyList) {
                        String participant_id = (String) map.get("participant_id");
                        System.out.println("participant_id :" + participant_id);
                        secondCommitteeService.informParty(meetingId, participant_id);
                    }
                    List<String> usernames = partyList.stream().map(p -> (String) p.get("participant_id")).collect(Collectors.toList());
                    members = memberDao.findByIdentity(usernames);
                    secondCommitteeService.updateMeetingPlan(meetingId, "6");//未检查
                    //提交事务
                    transactionUtil.commit();
                    //释放独占表单
                    SessionManager.setAttribute(sessionId, "formId-informParty", "null");
                }catch (Exception e) {
                    e.printStackTrace();
                    members=null;
                    transactionUtil.rollback();
                    throw e;
                }
                if (members != null) {
                    Organization org = orgDao.findByOrgId(orgId);
                    try {
                        String informMessage = String.format("【西南大学】\r\n党员同志:请您参加%s。\r\n会议主题：%s\r\n党支部主题：%s\r\n会议时间：%s\r\n会议地点：%s\r\n%s", type, theme,meeting_theme_secondary, runtime, place, org.getOrg_name());
                        List<String> phones = members.stream().filter(p -> !StringUtils.isEmpty(p.getMember_phone_number())).map(Member::getMember_phone_number).collect(Collectors.toList());
                        SmsService.smsSend(meetingId, informMessage, phones);

                        String remindMessage = String.format("【西南大学】\r\n党员同志:您参加的%s即将开始，请准时到场。\r\n会议主题：%s\r\n党支部主题：%s\r\n会议时间：%s\r\n会议地点：%s\r\n%s", type, theme,meeting_theme_secondary, runtime, place, org.getOrg_name());
                        SmsService.smsSend(meetingId, remindMessage, phones, Timestamp.valueOf(LocalDateTime.parse(startTime.substring(0, 19), dateTimeFormatter).plusHours(-4)));
                    } catch (SchedulerException e) {
                        logger.info("短信通知失败");
                    }
                }

                HttpServletResponse response1 = PortalUtil.getHttpServletResponse(response);
                String orgType;
                //orgType 存session 是否生效
                orgType = (String) SessionManager.getAttribute(sessionId, "orgType");
                logger.info("orgType:" + orgType);

                try {
                    if ("secondary".equals(orgType)) {
                        response1.sendRedirect("/web/guest/backlogtwo");
                    } else {
                        response1.sendRedirect("/web/guest/task");
                    }
                } catch (Exception e) {
                    logger.info("页面跳转异常:" + e.getMessage());
                }
            }
        }
        return null;
    }

}
