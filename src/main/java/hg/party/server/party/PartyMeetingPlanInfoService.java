package hg.party.server.party;

import hg.party.dao.party.PartyMeetingPlanInfoDao;
import hg.party.entity.party.MeetingPlan;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Map;


/**
 * 文件名称： party<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月2日下午5:50:20<br>
 */
@Component(immediate = true, service = PartyMeetingPlanInfoService.class)
public class PartyMeetingPlanInfoService {
    @Reference
    private PartyMeetingPlanInfoDao partyMeetingPlanInfo;

    //查询二级党委进度分页
    public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql, String department) {
        return partyMeetingPlanInfo.postGresqlFind(pageNo, pageSize, sql, department);
    }

    public List<Map<String, Object>> findChecKStateById(int id) {
        return partyMeetingPlanInfo.findChecKStateById(id);
    }

    //查询二级党委进度分页
    public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql) {
        return partyMeetingPlanInfo.postGresqlFind(pageNo, pageSize, sql);
    }

    //查询二级党委进度分页
    public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql, String... ss) {
        return partyMeetingPlanInfo.postGresqlFind(pageNo, pageSize, sql, ss);
    }

    //根据会议id查询二级党委、
    public List<MeetingPlan> meetingId(String meetingid) {
        return partyMeetingPlanInfo.meetingId(meetingid);
    }

    //上传心得编辑
    public List<Map<String, Object>> experience(String meeting, String userId) {
        return partyMeetingPlanInfo.experience(meeting, userId);
    }

    //修改心得
    public void updateHeart(String content, String meetingId, String userId) {
        partyMeetingPlanInfo.updateHeart(content, meetingId, userId);
    }

    //根据会议id和用户id修改用户该会议的查看状态
    public void findByMeetingStu(String userId, String meetingId) {
        partyMeetingPlanInfo.findByMeetingStu(userId, meetingId);
    }

    //保存审核人
    public void saveOrUpdate(MeetingPlan meetingPlan) {
        try {
            partyMeetingPlanInfo.saveOrUpdate(meetingPlan);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(MeetingPlan meetingPlan) {
        partyMeetingPlanInfo.saveOrUpdate(meetingPlan);
    }

    //根据登录人id查询会议id
    public List<MeetingPlan> meeting_id(String userId) {
        return partyMeetingPlanInfo.meeting_id(userId);
    }

    /**
     * 查询二级党委list
     */
    public List<String> findOrganization_id() {
        return partyMeetingPlanInfo.findOrganization_id();
    }

    /**
     * 查询会议主题list
     */
    public List<String> findMeeting_type() {
        return partyMeetingPlanInfo.findMeeting_type();
    }

    /**
     * 查询会议类型list
     */
    public List<String> findMeeting_theme() {
        return partyMeetingPlanInfo.findMeeting_theme();
    }

    //aa
    public List<Map<String, Object>> find(String startDate, String endDate, String meetType, String theme, String seconedId, String branchId, String checkState) {
        return partyMeetingPlanInfo.find(startDate, endDate, meetType, theme, seconedId, branchId, checkState);
    }

    //aa0统计条数
    public int MeetingSun(String userName, String seconedId, String branchId, String orgType, String orgId) {
        return partyMeetingPlanInfo.MeetingSun(userName, seconedId, branchId, orgType, orgId);
    }

    //aa1 人员会议统计查询
    public List<Map<String, Object>> userMeetingCount(String userName, String seconedId, String branchId, String orgType, String orgId) {
        return partyMeetingPlanInfo.userMeetingCount(userName, seconedId, branchId, orgType, orgId);
    }

    //bb
    public List<Map<String, Object>> find(String startDate, String endDate, String meetType, String theme, String seconedId, String branchId, int pageSize, int startPage, String checkState) {
        return partyMeetingPlanInfo.find(startDate, endDate, meetType, theme, seconedId, branchId, pageSize, startPage, checkState);
    }

    //bb1
    public List<Map<String, Object>> userMeetingCount(String userName, String seconedId, String branchId, int pageSize, int startPage, String orgType, String orgId) {
        return partyMeetingPlanInfo.userMeetingCount(userName, seconedId, branchId, pageSize, startPage, orgType, orgId);
    }

    public Map<String, Object> findAttachmentByMeetingid(String meetingId) {
        return partyMeetingPlanInfo.findAttachmentByMeetingid(meetingId);
    }

    //查询参会人员
    public List<Map<String, Object>> meetingUser(String meeting) {
        return partyMeetingPlanInfo.meetingUser(meeting);
    }

    //会议审核excel
    public List<Map<String, Object>> approvalExcel(String perm) {
        return partyMeetingPlanInfo.approvalExcel(perm);
    }

    //查询发送短信内容
    public List<MeetingPlan> MeetingPlan(String id) {
        return partyMeetingPlanInfo.MeetingPlan(id);
    }

    //获取组织id
    public List<Map<String, Object>> dep(String de) {
        return partyMeetingPlanInfo.dep(de);
    }

    public List<Map<String, Object>> findMeetingNote(String meetingId) {
        return partyMeetingPlanInfo.findMeetingNote(meetingId);
    }

    public PostgresqlPageResult<Map<String, Object>> searchPage(int page, int size, String orgId, String search) {
        return partyMeetingPlanInfo.searchPage(page, size,orgId, search);
    }

    public PostgresqlPageResult<Map<String, Object>> searchOrgPage(int page, int size, String orgId, String search) {
        return partyMeetingPlanInfo.searchOrgPage(page, size,orgId, search);
    }
}
