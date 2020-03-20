package hg.party.server.partyBranch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import com.alibaba.fastjson.JSON;

import hg.party.dao.partyBranch.PartyBranchDao;
import hg.party.entity.organization.PublicInformation;
import hg.party.entity.party.MeetingPlan;

@Component(immediate = true, service = PartyBranchService.class)
public class PartyBranchService {
    Logger logger = Logger.getLogger(PartyBranchService.class);
    //@Reference
    //private PartyBranchDao dao;
    PartyBranchDao dao = new PartyBranchDao();

    public int save(String sql) {
        return dao.UpdateInfrom(sql);
    }

    public int deleteGroupByGroupId(String groupId) {
        return dao.deleteGroupByGroupId(groupId);
    }

    public int cancleMeetingPlan(String cancleReson, String infromid, String orgId) {
        return dao.cancleMeetingPlan(cancleReson, infromid, orgId);
    }

    public int deleteMeetingPlan(String meetingId) {
        return dao.deleteMeetingPlan(meetingId);
    }

    public List<Map<String, Object>> find(String sql) {
        return dao.find(sql);
    }

    public int save(MeetingPlan meetingPlan) {
        return dao.save(meetingPlan);
    }

    public PublicInformation findResult(int id) {
        return dao.findByID(id);
    }

    public List<Map<String, Object>> findTaskByOrgId(String orgId, String type, String name) {
        return dao.findTaskByBOrgId(orgId, type, name);
    }

    public List<Map<String, Object>> findTaskByOrgId(String orgId, String type, String name, int size, int startPage) {
        List<Map<String, Object>> list = dao.findTaskByBOrgId(orgId, type, name, size, startPage);
        if (list != null && list.size() > 0) {
            for (Map<String, Object> map : list) {
                String dateTime = date(map.get("release_time").toString());
                map.put("release_time", dateTime);
            }
        }
        return list;
    }

    //党支部根据组织ID查询所有通知计划
    public Map<String, Object> queryInformMeetingsByOrgId(String orgId, String meetingType, String taskStatus, int page) {
        return dao.queryInformMeetingsByOrgId(orgId, meetingType, taskStatus, page);
    }


    public Map<String, Object> queryInformMeetingsByOrgIdAndInformId(String orgId, String meetingType,
								 String taskStatus, String informId, int page) {
        return dao.queryInformMeetingsByOrgIdAndInformId(orgId, meetingType, taskStatus, informId, page);
    }


    public String resultMap(String infromid, String orgId) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = dao.findTask(infromid, orgId);
        map.put("meetingType", list.get(0).get("meeting_type"));
        map.put("meetingTheme", list.get(0).get("meeting_theme"));
        map.put("start_time", list.get(0).get("start_time"));
        map.put("end_time", list.get(0).get("end_time"));
        List<Map<String, Object>> orgList = dao.findOrgMessage(orgId);
        map.put("branchName", orgList.get(0).get("org_name"));
        map.put("branchId", orgList.get(0).get("org_id"));
        List<Map<String, Object>> org = dao.findOrgMessage(orgList.get(0).get("org_parent").toString());
        map.put("sconedName", org.get(0).get("org_name"));
        map.put("sconedId", org.get(0).get("org_id"));
        map.put("infromid", infromid);
        map.put("state", "未读");
        logger.info(map);
        System.out.println(JSON.toJSONString(map));
        return JSON.toJSONString(map);

    }

    public List<Map<String, Object>> getGroupsMap(String orgId) {
        List<Map<String, Object>> list = dao.getGroupByOrgId(orgId);
        return list;
    }

    public String getGroups(String orgId) {
        List<Map<String, Object>> list = dao.getGroupByOrgId(orgId);
        return JSON.toJSONString(list);

    }

    public String findGroupPersons(String groupId) {
        List<Map<String, Object>> list = dao.findGroupPersons(groupId);
        return JSON.toJSONString(list);

    }

    public String findReplyState(String infromId, String orgId) {
        List<Map<String, Object>> list = dao.findReplyState(infromId, orgId);
        return JSON.toJSONString(list);

    }

    //查看已读未读回执,评分，心得
    public String findReplysState(String infromId, String orgId, String status) {
//	  List<Map<String,Object>> list = dao.findReplysState(infromId, orgId);
//	  return JSON.toJSONString(list);
        return JSON.toJSONString(dao.findReplysState(infromId, orgId, status));
    }

    //查看回执,评分，心得
    public String findMeetingReplays(String meetingId) {
        List<Map<String, Object>> list = dao.findMeetingReplays(meetingId);
        return JSON.toJSONString(list);
    }


    //查看会议的个人评论信息
    public List<Map<String, Object>> findPersonalMeetingComment(String meetingId, String userId) {
        List<Map<String, Object>> list = dao.findPersonalMeetingComment(meetingId, userId);
        return list;
    }


    //查看个人学习心得
    public List<Map<String, Object>> findPersonalMeetingExperience(String meetingId, String userId) {
        List<Map<String, Object>> list = dao.findPersonalMeetingExperience(meetingId, userId);
        return list;
    }


    public String queryMeetingTimeByMeetingId(String meetingId) {
        return JSON.toJSONString(dao.queryMeetingTimeByMeetingId(meetingId));
    }


    public String findReplyState(String infromId, String orgId, String staet) {
        List<Map<String, Object>> list = dao.findReplyState(infromId, orgId, staet);
        return JSON.toJSONString(list);

    }

    public List<Map<String, Object>> getPlace(String OrgID) {
        List<Map<String, Object>> list = dao.getPlance(OrgID);
        return list;
    }

    public List<Map<String, Object>> getPlace() {
        List<Map<String, Object>> list = dao.getPlanceAll();
        return list;
    }

    public String getParentId(String branchId) {
        List<Map<String, Object>> list = dao.getOrgParentIdByBranchId(branchId);
        if (list != null && list.size() > 0) {
            return (String) list.get(0).get("org_parent");
        }
        return null;
    }

    public List<String> getPlace(Object start, Object end) {
        try {
            List<String> list = dao.getSelectedPlace(start, end);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map<String, Object> findPlan(String meetingId) {
	/*  String sql="select tt.*,att.attachment_name,att.attachment_url from "+ 
		          "(select plan.*,org.org_name ,info.start_time as info_start ,info.end_time as info_end "+
				  "from hg_party_meeting_plan_info as plan ,hg_party_org as org ,hg_party_org_inform_info as info "+
				  "where  plan.organization_id=org.org_id and org.historic is false AND "+
				  "plan.meeting_id='"+meetingId+"' and plan.inform_id=info.inform_id) as tt "+
				  "LEFT OUTER JOIN hg_party_attachment  as att "+
				  "on tt.meeting_id=att.resource_id";*/
        // List<Map<String, Object>> list=find(sql);
        List<Map<String, Object>> list = dao.findAttachmentSeconed(meetingId);
        Map<String, Object> map = list.get(0);
        map.put("state", "old");
        String dataLong = map.get("start_time").toString();
        map.put("state", "old");
        map.put("start_time", date(dataLong));
        map.put("attachment_name", map.get("attachment_name"));
        map.put("attachment_url", map.get("attachment_url"));
        String orgId = map.get("organization_id").toString();
        // String sqlFindSeconedParty=
        //"SELECT * FROM hg_party_org WHERE historic is false and org_id=(select org_parent from hg_party_org where historic is false and org_id='"+orgId+"')";
        // List<Map<String, Object>> listOrgMessage=find(sqlFindSeconedParty);
        List<Map<String, Object>> listOrgMessage = dao.listOrgMessage(orgId);
        String sconedParty = listOrgMessage.get(0).get("org_name").toString();
        map.put("sconedParty", sconedParty);
        return map;

    }

    public static String date(String datestr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date;
        try {
            date = sdf.parse(datestr);
            String datestring = sdf.format(date);
            return datestring;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String resultMapSconed(String infromid, String orgId) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = dao.findTask(infromid, orgId);
        map.put("meetingType", list.get(0).get("meeting_type"));
        map.put("meetingTheme", list.get(0).get("meeting_theme"));
        map.put("start_time", list.get(0).get("start_time"));
        map.put("end_time", list.get(0).get("end_time"));
        List<Map<String, Object>> orgList = dao.findOrgMessage(orgId);
        map.put("branchName", orgList.get(0).get("org_name"));
        map.put("branchId", orgList.get(0).get("org_id"));
        map.put("infromid", infromid);
        map.put("state", "未读");
        return JSON.toJSONString(map);
    }

    public Map<String, Object> findPlanSconed(String meetingId) {
		 /* String sql="select tt.*,att.attachment_name,att.attachment_url from "+ 
		          "(select plan.*,org.org_name ,info.start_time as info_start ,info.end_time as info_end "+
				  "from hg_party_meeting_plan_info as plan ,hg_party_org as org ,hg_party_org_inform_info as info "+
				  "where  plan.organization_id=org.org_id and org.historic is false AND "+
				  "plan.meeting_id='"+meetingId+"' and plan.inform_id=info.inform_id) as tt "+
				  "LEFT OUTER JOIN hg_party_attachment  as att "+
				  "on tt.meeting_id=att.resource_id";*/
        //List<Map<String, Object>> list=find(sql);
        List<Map<String, Object>> list = dao.findAttachmentSeconed(meetingId);
        Map<String, Object> map = list.get(0);
        map.put("state", "old");
        String dataLong = map.get("start_time").toString();
        map.put("state", "old");
        map.put("start_time", date(dataLong));
        map.put("attachment_name", map.get("attachment_name"));
        map.put("attachment_url", map.get("attachment_url"));
        return map;

    }


    public String findSconedAndBranch(String orgId) {
        List<Map<String, Object>> list = dao.findSconedAndBranch(orgId);
        if (list.size() > 0) {
            String org_type = list.get(0).get("org_type").toString();
            return org_type;
        }
        return null;
    }

    /**
     * 判断 该人员是否已经存在于该小组
     *
     * @param groupId
     * @param userId
     * @return
     */
    public boolean isExist(String groupId, String userId) {
        List<Map<String, Object>> list = dao.isExist(groupId, userId);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public List<Map<String, Object>> findDetail(String infromId, String orgId) {
        return dao.findDetail(infromId, orgId);
    }

    //根据会议id和用户id修改用户该会议的查看状态
    public void findByMeetingStatus(String inform_id, String org_id) {
        dao.findByMeetingStatus(inform_id, org_id);
    }

    public List<Map<String, Object>> findInformDetail(String infromId, String orgId) {
        return dao.findInformDetail(infromId, orgId);
    }

    public List<Map<String, Object>> findInformAttachment(String infromId) {
        return dao.findInformAttachment(infromId);
    }


    public List<Map<String, Object>> findMeetingDetail(String meetingId) {
        return dao.findMeetingDetail(meetingId);
    }

    public List<Map<String, Object>> findMeetingAttachment(String meetingId) {
        return dao.findMeetingAttachment(meetingId);
    }


    public List<Map<String, Object>> getGroupMembers(List<String> groupsIds) {
        String sql = "SELECT m.member_identity as id, m.member_name as name, g.group_id, g.group_name FROM \"hg_party_group_member_info\"  gm\n" +
                "left join hg_party_group_org_info g on gm.group_id = g.group_id\n" +
                "left join hg_party_member m on gm.participant_id = m.member_identity\n" +
                "where g.group_id in ('" + String.join("','", groupsIds) + "')";
        return dao.getJdbcTemplate().queryForList(sql);
    }

    public MeetingPlan findNoSubmitPlan(String userId, String org_id) {
        return dao.findNoSubmitPlan(userId,org_id);
    }
}
