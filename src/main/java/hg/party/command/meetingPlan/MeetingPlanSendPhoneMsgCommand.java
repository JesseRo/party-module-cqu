package hg.party.command.meetingPlan;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.entity.party.MeetingPlan;
import hg.party.server.party.PartyMeetingPlanInfoService;
import hg.party.server.partyBranch.PartyBranchService;
import hg.util.result.Result;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name="+ PartyPortletKeys.Form,
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeToDoList,
                "mvc.command.name=/meetingPlan/sendPhoneMsg"
        },
        service = MVCResourceCommand.class
)
public class MeetingPlanSendPhoneMsgCommand implements MVCResourceCommand {

    @Reference
    PartyBranchService partyBranchService;
    @Reference
    PartyMeetingPlanInfoService partyMeetingPlanInfoService;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

        String meetingId = ParamUtil.getString(resourceRequest, "meetingId");
        meetingId = HtmlUtil.escape(meetingId);
        PrintWriter printWriter = null;
        try {
            printWriter = resourceResponse.getWriter();
            MeetingPlan m  = partyBranchService.findMeetingPlan(meetingId);
            if (m == null) {
                printWriter.write(JSON.toJSONString(ResultUtil.fail("通知失败，活动计划不存在。")));
                return false;
            }else{
                if(Integer.parseInt(m.getTask_status())>=4){
                    Result result = partyMeetingPlanInfoService.sendPhoneNoticeMsg(meetingId);
                    printWriter.write(JSON.toJSONString(result));
                }else{
                    printWriter.write(JSON.toJSONString(ResultUtil.fail("当前流程状态不能进行短信通知。")));
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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
