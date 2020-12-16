package hg.party.command.meetingPlan;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;

import hg.party.dao.org.MemberDao;
import hg.party.entity.party.MeetingPlan;

import hg.party.entity.partyMembers.Member;
import hg.party.server.CacheCore;
import hg.party.server.partyBranch.PartyBranchService;

import hg.util.TransactionUtil;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.UUID;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Form,
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "mvc.command.name=/hg/meetingPlan/save"
        },
        service = MVCResourceCommand.class
)
public class MeetingPlanSaveCommand implements MVCResourceCommand {

    @Reference
    PartyBranchService partyBranchService;

    @Reference
    private MemberDao memberDao;

    @Reference
    private TransactionUtil transactionUtil;

    @Reference
    private CacheCore cacheCore;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
        String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");

        String conferenceType = ParamUtil.getString(resourceRequest, "conferenceType");
        String meeting_theme = ParamUtil.getString(resourceRequest, "subject");

        String startDate = ParamUtil.getString(resourceRequest, "timeDuring");
        String timeLasts = ParamUtil.getString(resourceRequest, "timeLasts");

        String campus = ParamUtil.getString(resourceRequest, "campus");
        int location = ParamUtil.getInteger(resourceRequest, "location");

        String host = ParamUtil.getString(resourceRequest, "host");
        String sitId = ParamUtil.getString(resourceRequest, "sit");

        String linkMan = ParamUtil.getString(resourceRequest, "contact");
        String linkManTelephone = ParamUtil.getString(resourceRequest, "phoneNumber");
        String participate = ParamUtil.getString(resourceRequest, "participate");

        boolean graft = ParamUtil.getBoolean(resourceRequest, "graft");
        String meetingId = ParamUtil.getString(resourceRequest, "meetingId");
        String attachment = ParamUtil.getString(resourceRequest, "attachment");
        attachment = HtmlUtils.htmlUnescape(attachment);
        String content = ParamUtil.getString(resourceRequest, "meetingContent");
        boolean autoPhoneMsg = ParamUtil.getBoolean(resourceRequest, "autoPhoneMsg");

        String speaker = ParamUtil.getString(resourceRequest, "speaker");
        String speakTitle = ParamUtil.getString(resourceRequest, "speakTitle");

        campus = HtmlUtil.escape(campus);
        startDate = HtmlUtil.escape(startDate);
        host = HtmlUtil.escape(host);
        linkMan = HtmlUtil.escape(linkMan);
        linkManTelephone = HtmlUtil.escape(linkManTelephone);
        conferenceType = HtmlUtil.escape(conferenceType);
        meeting_theme = HtmlUtil.escape(meeting_theme);
        timeLasts = HtmlUtil.escape(timeLasts);
        sitId = HtmlUtil.escape(sitId);
        meetingId = HtmlUtil.escape(meetingId);
        PrintWriter printWriter = null;
        try {
            printWriter = resourceResponse.getWriter();
            MeetingPlan m = new MeetingPlan();
            if (StringUtils.isEmpty(meetingId)) {
                String meeting_id = UUID.randomUUID().toString();
                m.setMeeting_id(meeting_id);
            } else {
                m = partyBranchService.findMeetingPlan(meetingId);
                if (m == null) {
                    printWriter.write(JSON.toJSONString(ResultUtil.fail("操作的数据不存在。")));
                    return false;
                }
            }
            m.setMeeting_type(conferenceType);
            m.setMeeting_theme(meeting_theme);
            m.setStart_time(paerse(startDate));
            m.setTotal_time(Integer.parseInt(timeLasts));
            m.setEnd_time(endTime(startDate, timeLasts));
            m.setPlace(location);
            m.setParticipant_group(participate);
            m.setHost(host);
            m.setContact(linkMan);
            m.setContact_phone(linkManTelephone);
            m.setContent(content);
            if (!StringUtils.isEmpty(sitId)) {
                Member member = memberDao.findByUserId(sitId);
                if (member != null) {
                    m.setSit(member.getMember_name());
                    m.setSit_id(sitId);
                }
            }else {
                m.setSit_id(null);
                m.setSit(null);
            }

            m.setCampus(campus);
            m.setAttachment(attachment);
            m.setAutoPhoneMsg(autoPhoneMsg ? 1 : 0);
            LocalDateTime ldTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
            Timestamp t = Timestamp.valueOf(ldTime);
            m.setSubmit_time(t);
            m.setOrganization_id(orgId);
            if (graft) {
                m.setTask_status("0");//草稿
            } else {
                m.setTask_status("1");//非草稿
            }

            int ret = 0;
            String message = graft ? "保存成功" : "发布成功";
            if(1L != cacheCore.incr("meeting:new:" + resourceRequest.getRequestedSessionId(), 2)) {
                printWriter.write(JSON.toJSONString(ResultUtil.fail("请求过于频繁，请稍后再试")));
                return false;
            }
            transactionUtil.startTransaction();
            if (StringUtils.isEmpty(meetingId)) {
                ret = partyBranchService.save(m);
            } else {
                ret = partyBranchService.update(m);
                message = "修改成功";
            }
            if (ret > 0) {
                String[] participateArr = participate.split(",");
                partyBranchService.deleteMeetingMember(m.getMeeting_id());
                for (int i = 0; i < participateArr.length; i++) {
                    if (!StringUtils.isEmpty(participateArr[i])) {
                        partyBranchService.addMeetingMember(m.getMeeting_id(), participateArr[i]);
                    }
                }
                partyBranchService.deleteMeetingSpeak(m.getMeeting_id());
                if (!StringUtils.isEmpty(speaker)) {
                    String[] speakers = speaker.split(",");
                    String[] speakTitles = speakTitle.split(",");
                    for (int i = 0; i < speakers.length; i++) {
                        if (!StringUtils.isEmpty(speakers[i])) {
                            partyBranchService.addMeetingSpeak(m.getMeeting_id(), speakers[i], speakTitles[i]);
                        }
                    }
                }
            }
            //转跳
            //获取组织类型
            String orgType = partyBranchService.findSconedAndBranch(orgId);
            String url;
            if (!StringUtils.isEmpty(meetingId)) {
                url = "/backlogtwo";
            } else {
                url = "/backlogtwo";
            }
            if (ret > 0) {
                transactionUtil.commit();
                printWriter.write(JSON.toJSONString(ResultUtil.success(url, message)));
            } else {
                transactionUtil.rollback();
                printWriter.write(JSON.toJSONString(ResultUtil.fail("操作失败，请刷新后重试...")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            transactionUtil.rollback();
            printWriter.write(JSON.toJSONString(ResultUtil.fail("请求异常。")));
        }
        return false;
    }


    //解析时间
    public static Timestamp paerse(String string) {
        try {
            Timestamp ts = Timestamp.valueOf(string);
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * @param startTime
     * @param totalTime
     * @return 结束时间
     */
    public static Timestamp endTime(String startTime, String totalTime) {
        try {
            Timestamp tt = paerse(startTime);
            long dataLong = tt.getTime() + Integer.parseInt(totalTime) * 1000 * 60;
            tt = new Timestamp(dataLong);
            return tt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
