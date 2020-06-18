package hg.party.dao.party;


import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.MeetingPlan;
import hg.util.postgres.HgPostgresqlDaoImpl;
import hg.util.postgres.PostgresqlPageResult;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import party.constants.PartyOrgAdminTypeEnum;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

/**
 * 文件名称： party<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月2日下午5:21:55<br>
 */
@Component(immediate = true, service = PartyMeetingPlanInfoDao.class)
public class PartyMeetingPlanInfoDao extends HgPostgresqlDaoImpl<MeetingPlan> {
    Logger logger = Logger.getLogger(PartyMeetingPlanInfoDao.class);
    @Reference
    private OrgDao orgDao;

    //根据会议id查询
    public List<MeetingPlan> meetingId(String meetingid) {
        String sql = "SELECT * FROM hg_party_meeting_plan_info " +
                "WHERE meeting_id= ? ";
        RowMapper<MeetingPlan> rowMapper = BeanPropertyRowMapper.newInstance(MeetingPlan.class);
        return this.jdbcTemplate.query(sql, rowMapper, meetingid);
    }
    //根据会议id查询
    public MeetingPlan findMeetingPlanByMeetingId(String meetingId) {
        String sql = "SELECT * FROM hg_party_meeting_plan_info WHERE meeting_id= ? ";
        RowMapper<MeetingPlan> rowMapper = BeanPropertyRowMapper.newInstance(MeetingPlan.class);
        List<MeetingPlan>  list = this.jdbcTemplate.query(sql, rowMapper, meetingId);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public Map<String, Object> meetingDetail(String meetingId) {
        String sql = "SELECT plan.*, m.member_name as contactName, p.place as placeName, host.member_name as hostName " +
                "FROM hg_party_meeting_plan_info plan " +
                "left join hg_party_member m on plan.contact = m.member_identity and m.historic = false " +
                "left join hg_party_place p on p.id = plan.place " +
                "left join hg_party_member host on host.member_identity = plan.host and m.historic = false " +
                "WHERE meeting_id= ? ";
        return this.jdbcTemplate.queryForMap(sql, meetingId);
    }

    public PostgresqlQueryResult<Map<String, Object>> leaderMeetingPage(int page, int size,
                                                                        String secondId, String brunchId, String startTime, String endTime, String leader) {
        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;
        List<Object> params = new ArrayList<>();
        String sql = "SELECT\n" +
                "\tplan.meeting_type,\n" +
                "\tplan.meeting_theme ,\n" +
                "\torg.org_name as org_name,\n" +
                "\tconcat ( place.campus, place.place ) as place,\n" +
                "\tplan.start_time ,\n" +
                "\tplan.end_time,\n" +
                "\tplan.total_time,\n" +
                "\thost.member_name as host,\n" +
                "\tcontact.member_name as contact,\n" +
                "\tplan.contact_phone,\n" +
                "\tchecker.member_name as checker,\n" +
                "\tplan.check_status,\n" +
                "\tleader.member_name as leader_name\n" +
                "\tfrom\n" +
                "\thg_party_meeting_member_info participant \n" +
                "\tleft join hg_party_meeting_plan_info plan on plan.meeting_id = participant.meeting_id\n" +
                "\tLEFT JOIN hg_party_meeting_notes_info note ON plan.meeting_id = note.meeting_id\n" +
                "\tLEFT JOIN hg_party_org org ON plan.organization_id = org.org_id\n" +
                "\tLEFT JOIN hg_party_member contact ON plan.contact = contact.member_identity\n" +
                "\tLEFT JOIN hg_party_member HOST ON plan.HOST = HOST.member_identity\n" +
                "\tLEFT JOIN hg_party_member checker ON plan.HOST = checker.member_identity\n" +
                "\tLEFT JOIN hg_party_member leader ON participant.participant_id = leader.member_identity\n" +
                "\tLEFT JOIN hg_party_place place ON place.\"id\" = plan.place\n" +
                "\twhere leader.member_is_leader = '是' ";
        if (!StringUtils.isEmpty(brunchId)){
            sql += "and org.org_id = ? ";
            params.add(brunchId);
        }else if (!StringUtils.isEmpty(secondId)){
            sql += "and org.org_parent = ? ";
            params.add(secondId);
        }
        if (!StringUtils.isEmpty(startTime)){
            sql += "and plan.end_time > ? ";
            Timestamp start = Timestamp.valueOf(LocalDate.parse(startTime).atStartOfDay());
            params.add(start);
        }
        if (!StringUtils.isEmpty(endTime)){
            sql += "and plan.start_time < ? ";
            Timestamp end = Timestamp.valueOf(LocalDate.parse(endTime).atStartOfDay());
            params.add(end);
        }
        if (!StringUtils.isEmpty(leader)){
            sql += "and leader.member_name = ? ";
            params.add(leader);
        }
        sql += "order by plan.id asc ";
        return postGresqlFindBySql(page, size, sql, params.toArray(new Object[0]));
    }


    public List<Map<String, Object>> leaderMeeting(String secondId, String brunchId, String startTime, String endTime, String leader) {
        List<Object> params = new ArrayList<>();
        String sql = "SELECT\n" +
                "\tplan.meeting_type,\n" +
                "\tplan.meeting_theme ,\n" +
                "\torg.org_name as org_name,\n" +
                "\tconcat ( place.campus, place.place ) as place,\n" +
                "\tplan.start_time ,\n" +
                "\tplan.end_time,\n" +
                "\tplan.total_time,\n" +
                "\thost.member_name as host,\n" +
                "\tcontact.member_name as contact,\n" +
                "\tplan.contact_phone,\n" +
                "\tchecker.member_name as checker,\n" +
                "\tplan.check_status,\n" +
                "\tleader.member_name as leader_name,\n" +
                "\tplan.auditor,\n" +
                "\tplan.submit_time\n" +
                "\tfrom\n" +
                "\thg_party_meeting_member_info participant \n" +
                "\tleft join hg_party_meeting_plan_info plan on plan.meeting_id = participant.meeting_id\n" +
                "\tLEFT JOIN hg_party_meeting_notes_info note ON plan.meeting_id = note.meeting_id\n" +
                "\tLEFT JOIN hg_party_org org ON plan.organization_id = org.org_id\n" +
                "\tLEFT JOIN hg_party_member contact ON plan.contact = contact.member_identity\n" +
                "\tLEFT JOIN hg_party_member HOST ON plan.HOST = HOST.member_identity\n" +
                "\tLEFT JOIN hg_party_member checker ON plan.HOST = checker.member_identity\n" +
                "\tLEFT JOIN hg_party_member leader ON participant.participant_id = leader.member_identity\n" +
                "\tLEFT JOIN hg_party_place place ON place.\"id\" = plan.place\n" +
                "\twhere leader.member_is_leader = '是' ";
        if (!StringUtils.isEmpty(brunchId)){
            sql += "and org.org_id = ? ";
            params.add(brunchId);
        }else if (!StringUtils.isEmpty(secondId)){
            sql += "and org.org_parent = ? ";
            params.add(secondId);
        }
        if (startTime != null){
            sql += "and plan.end_time > ? ";
            Timestamp start = Timestamp.valueOf(LocalDate.parse(startTime).atStartOfDay());
            params.add(start);
        }
        if (endTime != null){
            sql += "and plan.start_time < ? ";
            Timestamp end = Timestamp.valueOf(LocalDate.parse(endTime).atStartOfDay());
            params.add(end);
        }
        if (!StringUtils.isEmpty(leader)){
            sql += "and leader.member_name = ? ";
            params.add(leader);
        }
        sql += "order by plan.id asc ";
        return jdbcTemplate.queryForList(sql, params.toArray(new Object[0]));
    }

    //上传心得编辑
    public List<Map<String, Object>> experience(String meeting, String userId) {
        String sql = "SELECT * FROM hg_party_learning_experience " +
                "WHERE meeting_id=? " +
                "AND participant_id=? ";
        return this.jdbcTemplate.queryForList(sql, meeting, userId);
    }

    //修改心得
    public void updateHeart(String content, String meetingId, String userId) {
        String sql = "UPDATE hg_party_learning_experience SET experience_content=? " +
                "WHERE meeting_id=? " +
                "AND participant_id=? ";
        jdbcTemplate.update(sql, content, meetingId, userId);
    }

    //根据会议id和用户id修改用户该会议的查看状态
    public void findByMeetingStu(String userId, String meetingId) {
        String sql = "UPDATE hg_party_meeting_member_info SET check_status='已查看' WHERE meeting_id= ? AND participant_id= ? ";
        //this.jdbcTemplate.execute(sql);
        jdbcTemplate.update(sql, meetingId, userId);
    }

    //根据登录人id查询会议id
    public List<MeetingPlan> meeting_id(String userId) {
        String sql = "SELECT * from hg_party_meeting_plan_info " +
                "WHERE check_person= ? ";
        RowMapper<MeetingPlan> rowMapper = BeanPropertyRowMapper.newInstance(MeetingPlan.class);
        return this.jdbcTemplate.query(sql, rowMapper, userId);
    }

    /**
     * 查询二级党委list
     */
    public List<String> findOrganization_id() {
        String sql = "select organization_id from hg_party_meeting_plan_info group by organization_id";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    /**
     * 查询会议主题list
     */
    public List<String> findMeeting_type() {
        String sql = "select meeting_type from hg_party_meeting_plan_info group by meeting_type";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    /**
     * 查询会议类型list
     */
    public List<String> findMeeting_theme() {
        String sql = "select meeting_theme from hg_party_meeting_plan_info group by meeting_theme";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    //aa
    public List<Map<String, Object>> find(String starDdate, String endDate, String meetType, String theme, String seconedId, String branchId, String checkState) {
        String sql = "select cc.*,cus.user_name as check_person_org_name from " +
                "(SELECT tt.*,us.user_name as check_person_name FROM " +
                "(SELECT plan.task_status as plan_state, (select org_name from hg_party_org WHERE org_id=plan.organization_id) as branch_name," +
                "(select org_name from hg_party_org WHERE org_id=(SELECT org_parent FROM hg_party_org WHERE org_id=plan.organization_id) and org_type!='organization' ) as second_name," +
                " plan.*,info.release_time from hg_party_meeting_plan_info  as plan ,hg_party_org_inform_info as info";
        StringBuffer buffer = new StringBuffer(sql);
        if (!StringUtils.isEmpty(seconedId) && StringUtils.isEmpty(branchId)) {
            buffer.append(" ,hg_party_org as org_o");
        }
        buffer.append(" WHERE plan.inform_id=info.inform_id");

        if (!StringUtils.isEmpty(starDdate) && StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + starDdate + " 00:00:00' and plan.start_time<'" + starDdate + " 24:00:00'");
        }
        if (!StringUtils.isEmpty(starDdate) && !StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + starDdate + " 00:00:00' and plan.start_time<'" + endDate + " 24:00:00'");
        }
        if (StringUtils.isEmpty(starDdate) && !StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + endDate + " 00:00:00' and plan.start_time<'" + endDate + " 24:00:00'");
        }
        if (!StringUtils.isEmpty(meetType)) {
            buffer.append(" and plan.meeting_type='" + meetType + "'");
        }
        if (!StringUtils.isEmpty(theme)) {
            buffer.append(" and plan.meeting_theme like '%" + theme + "%'");
        }
        if (!StringUtils.isEmpty(seconedId) && !StringUtils.isEmpty(branchId)) {
            buffer.append(" AND plan.organization_id='" + branchId + "'");
        }
        if (!StringUtils.isEmpty(seconedId) && StringUtils.isEmpty(branchId)) {
            buffer.append(" and (org_o.org_parent='" + seconedId + "'  or org_o.org_id='" + seconedId + "') and plan.organization_id=org_o.org_id");
        }
        if (!StringUtils.isEmpty(checkState)) {
            if ("t".equals(checkState)) {
                buffer.append(" and plan.check_person_org is not null");
            } else {
                buffer.append(" and plan.check_person_org is null");
            }
        }
        buffer.append(" ) as tt LEFT OUTER JOIN hg_users_info as us" +
                " on tt.check_person=us.user_id ) as cc LEFT OUTER JOIN hg_users_info as cus" +
                " on cc.check_person_org=cus.user_id");
//		    logger.info(buffer.toString());
        return jdbcTemplate.queryForList(buffer.toString());
    }

    //aa0统计条数
    public int MeetingSun(String userName, String seconedId, String branchId, String orgType, String orgId) {
        String sql = "";
        int sun = 0;
        if ("organization".equals(orgType)) {
            sql = "SELECT count(*) FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem  " +
                    "ON meet.meeting_id=mem.meeting_id  " +
                    "LEFT JOIN hg_users_info AS use  " +
                    "ON mem.participant_id=use.user_id  " +
                    "LEFT JOIN hg_party_org AS org  " +
                    "ON use.user_department_id=org.org_id  " +
                    "LEFT JOIN hg_party_org AS orgg  " +
                    "ON org.org_parent=orgg.org_id  " +
                    "WHERE use.user_name LIKE ?  " +
                    "AND orgg.org_name LIKE ? " +
                    "AND org.org_name LIKE ? ";
            List<Map<String, Object>> count = jdbcTemplate.queryForList(sql, "%" + userName + "%", "%" + seconedId + "%", "%" + branchId + "%");
            sun = Integer.parseInt(count.get(0).get("count").toString());
        } else if ("secondary".equals(orgType)) {
            sql = "SELECT count(*) FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem   " +
                    "ON meet.meeting_id=mem.meeting_id   " +
                    "LEFT JOIN hg_users_info AS use  " +
                    "ON mem.participant_id=use.user_id   " +
                    "LEFT JOIN hg_party_org AS org   " +
                    "ON use.user_department_id=org.org_id   " +
                    "LEFT JOIN hg_party_org AS orgg   " +
                    "ON org.org_parent=orgg.org_id   " +
                    "WHERE use.user_name LIKE ?  " +
                    "AND orgg.org_id=? " +
                    "AND org.org_name LIKE ? ";
            List<Map<String, Object>> count = jdbcTemplate.queryForList(sql, "%" + userName + "%", orgId, "%" + branchId + "%");
            sun = Integer.parseInt(count.get(0).get("count").toString());
        } else if ("branch".equals(orgType)) {
            sql = "SELECT count(*) FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem   " +
                    "ON meet.meeting_id=mem.meeting_id   " +
                    "LEFT JOIN hg_users_info AS use  " +
                    "ON mem.participant_id=use.user_id   " +
                    "LEFT JOIN hg_party_org AS org   " +
                    "ON use.user_department_id=org.org_id   " +
                    "LEFT JOIN hg_party_org AS orgg  " +
                    "ON org.org_parent=orgg.org_id   " +
                    "WHERE use.user_name LIKE ?  " +
                    "AND meet.organization_id=? ";
            List<Map<String, Object>> count = jdbcTemplate.queryForList(sql, "%" + userName + "%", orgId);
            sun = Integer.parseInt(count.get(0).get("count").toString());
        }
        return sun;
    }

    //aa1 人员会议统计查询
    public List<Map<String, Object>> userMeetingCount(String userName, String seconedId, String branchId, String orgType, String orgId) {
        String sql = "";
        List<Map<String, Object>> listObj = null;
        if ("organization".equals(orgType)) {
            sql = "SELECT *,orgg.org_name AS org_names,org.org_name AS org_nam FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem  " +
                    "ON meet.meeting_id=mem.meeting_id  " +
                    "LEFT JOIN hg_users_info AS use  " +
                    "ON mem.participant_id=use.user_id  " +
                    "LEFT JOIN hg_party_org AS org  " +
                    "ON use.user_department_id=org.org_id  " +
                    "LEFT JOIN hg_party_org AS orgg  " +
                    "ON org.org_parent=orgg.org_id  " +
                    "WHERE use.user_name LIKE ?  " +
                    "AND orgg.org_name LIKE ? " +
                    "AND org.org_name LIKE ? " +
                    "ORDER BY meet.start_time ";
            listObj = jdbcTemplate.queryForList(sql, "%" + userName + "%", "%" + seconedId + "%", "%" + branchId + "%");
        } else if ("secondary".equals(orgType)) {
            sql = "SELECT *,orgg.org_name AS org_names,org.org_name AS org_nam FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem   " +
                    "ON meet.meeting_id=mem.meeting_id   " +
                    "LEFT JOIN hg_users_info AS use  " +
                    "ON mem.participant_id=use.user_id   " +
                    "LEFT JOIN hg_party_org AS org   " +
                    "ON use.user_department_id=org.org_id   " +
                    "LEFT JOIN hg_party_org AS orgg   " +
                    "ON org.org_parent=orgg.org_id   " +
                    "WHERE use.user_name LIKE ?  " +
                    "AND orgg.org_id=? " +
                    "AND org.org_name LIKE ? " +
                    "ORDER BY meet.start_time";
            listObj = jdbcTemplate.queryForList(sql, "%" + userName + "%", orgId, "%" + branchId + "%");
        } else if ("branch".equals(orgType)) {
            sql = "SELECT *,orgg.org_name AS org_names,org.org_name AS org_nam FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem   " +
                    "ON meet.meeting_id=mem.meeting_id   " +
                    "LEFT JOIN hg_users_info AS use   " +
                    "ON mem.participant_id=use.user_id   " +
                    "LEFT JOIN hg_party_org AS org   " +
                    "ON use.user_department_id=org.org_id   " +
                    "LEFT JOIN hg_party_org AS orgg   " +
                    "ON org.org_parent=orgg.org_id  " +
                    "WHERE use.user_name LIKE ?   " +
                    "AND meet.organization_id=? " +
                    "ORDER BY meet.start_time";
            listObj = jdbcTemplate.queryForList(sql, "%" + userName + "%", orgId);
        }
        return listObj;
    }

    //bb
    public List<Map<String, Object>> find(String starDdate, String endDate, String meetType, String theme,
                              String seconedId, String branchId, Integer pageSize, Integer startPage, String checkState) {
        String sql = "\n" +
                "\tSELECT\n" +
                "\t\tplan.task_status AS plan_state,org_o.org_name AS branch_name,plc.place as place_name," +
                " us.user_name as check_person_name, contact.user_name as contact_name, host.user_name as host_name, \n" +
                "\t\t( SELECT org_name FROM hg_party_org WHERE org_id = ( SELECT org_parent FROM hg_party_org WHERE org_id = plan.organization_id ) AND org_type != 'organization' ) AS second_name,\n" +
                "\t\tplan.*\n" +
                "\tFROM\n" +
                "\t\thg_party_meeting_plan_info AS plan\n" +
                "\t\tLEFT OUTER JOIN hg_users_info AS us ON plan.check_person = us.user_id \n" +
                "\t\tLEFT OUTER JOIN hg_users_info AS contact ON plan.contact = contact.user_id \n" +
                "\t\tLEFT OUTER JOIN hg_users_info AS host ON plan.host = host.user_id \n" +
                "\t\tleft outer join hg_party_org as org_o on plan.organization_id = org_o.org_id \n" +
                "left join hg_party_org as org_p on org_o.org_parent = org_p.org_id and org_p.org_type != 'organization' " +
                "left join hg_party_place as plc on plan.place = plc.id " +
                "\twhere 1 = 1 ";
        StringBuffer buffer = new StringBuffer(sql);
        if (!StringUtils.isEmpty(starDdate) && StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + starDdate + " 00:00:00' and plan.start_time<'" + starDdate + " 24:00:00'");
        }
        if (!StringUtils.isEmpty(starDdate) && !StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + starDdate + " 00:00:00' and plan.start_time<'" + endDate + " 24:00:00'");
        }
        if (StringUtils.isEmpty(starDdate) && !StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + endDate + " 00:00:00' and plan.start_time<'" + endDate + " 24:00:00'");
        }
        if (!StringUtils.isEmpty(meetType)) {
            buffer.append(" and plan.meeting_type='" + meetType + "'");
        }
        if (!StringUtils.isEmpty(theme)) {
            buffer.append(" and plan.meeting_theme like '%" + theme + "%'");
        }
        if (!StringUtils.isEmpty(seconedId) && !StringUtils.isEmpty(branchId)) {
            buffer.append(" AND plan.organization_id='" + branchId + "'");
        }
        if (!StringUtils.isEmpty(seconedId) && StringUtils.isEmpty(branchId)) {
            buffer.append(" and (org_o.org_parent='" + seconedId + "'  or org_o.org_id='" + seconedId + "') and plan.organization_id=org_o.org_id");
        }
        if (!StringUtils.isEmpty(checkState)) {
            if ("t".equals(checkState)) {
                buffer.append(" and plan.check_person_org is not null");
            } else {
                buffer.append(" and plan.check_person_org is null");
            }
        }
        if (pageSize != null && startPage != null){
            buffer.append(" order by plan.id desc limit " + pageSize + " OFFSET " + startPage + "");
        }
        return jdbcTemplate.queryForList(buffer.toString());
    }

    public int count(String startDate, String endDate, String meetType, String theme, String seconedId, String branchId, String checkState) {
        String sql = "\n" +
                "\tSELECT\n" +
                " count(1) " +
                "\tFROM\n" +
                "\t\thg_party_meeting_plan_info AS plan\n" +
                "\t\tLEFT OUTER JOIN hg_users_info AS us ON plan.check_person = us.user_id \n" +
                "\t\tleft outer join hg_party_org as org_o on plan.organization_id = org_o.org_id \n" +
                "left join hg_party_org as org_p on org_o.org_parent = org_p.org_id and org_p.org_type != 'organization' " +
                "\twhere 1 = 1 ";
        StringBuffer buffer = new StringBuffer(sql);
        if (!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + startDate + " 00:00:00' and plan.start_time<'" + startDate + " 24:00:00'");
        }
        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + startDate + " 00:00:00' and plan.start_time<'" + endDate + " 24:00:00'");
        }
        if (StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + endDate + " 00:00:00' and plan.start_time<'" + endDate + " 24:00:00'");
        }
        if (!StringUtils.isEmpty(meetType)) {
            buffer.append(" and plan.meeting_type='" + meetType + "'");
        }
        if (!StringUtils.isEmpty(theme)) {
            buffer.append(" and plan.meeting_theme like '%" + theme + "%'");
        }
        if (!StringUtils.isEmpty(seconedId) && !StringUtils.isEmpty(branchId)) {
            buffer.append(" AND plan.organization_id='" + branchId + "'");
        }
        if (!StringUtils.isEmpty(seconedId) && StringUtils.isEmpty(branchId)) {
            buffer.append(" and (org_o.org_parent='" + seconedId + "'  or org_o.org_id='" + seconedId + "') and plan.organization_id=org_o.org_id");
        }
        if (!StringUtils.isEmpty(checkState)) {
            if ("t".equals(checkState)) {
                buffer.append(" and plan.check_person_org is not null");
            } else {
                buffer.append(" and plan.check_person_org is null");
            }
        }
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    //bb1
    public List<Map<String, Object>> userMeetingCount(String userName, String seconedId, String branchId, int pageSize, int startPage, String orgType, String orgId) {
        String sql = "";
        List<Map<String, Object>> listObject = null;
        if ("organization".equals(orgType)) {
            sql = "SELECT *,orgg.org_name AS org_names,org.org_name AS org_nam FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem " +
                    "ON meet.meeting_id=mem.meeting_id " +
                    "LEFT JOIN hg_users_info AS use " +
                    "ON mem.participant_id=use.user_id " +
                    "LEFT JOIN hg_party_org AS org " +
                    "ON use.user_department_id=org.org_id " +
                    "LEFT JOIN hg_party_org AS orgg " +
                    "ON org.org_parent=orgg.org_id " +
                    "WHERE use.user_name LIKE ? " +
                    "AND orgg.org_name LIKE ? " +
                    "AND org.org_name LIKE ? " +
                    "ORDER BY meet.start_time " +
                    "limit ? OFFSET ? ";
            listObject = jdbcTemplate.queryForList(sql, "%" + userName + "%", "%" + seconedId + "%", "%" + branchId + "%", pageSize, startPage);
        } else if ("secondary".equals(orgType)) {
            sql = "SELECT *,orgg.org_name AS org_names,org.org_name AS org_nam FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem   " +
                    "ON meet.meeting_id=mem.meeting_id   " +
                    "LEFT JOIN hg_users_info AS use   " +
                    "ON mem.participant_id=use.user_id   " +
                    "LEFT JOIN hg_party_org AS org   " +
                    "ON use.user_department_id=org.org_id   " +
                    "LEFT JOIN hg_party_org AS orgg   " +
                    "ON org.org_parent=orgg.org_id   " +
                    "WHERE use.user_name LIKE ?  " +
                    "AND orgg.org_id=? " +
                    "AND org.org_name LIKE ? " +
                    "ORDER BY meet.start_time " +
                    "limit ? OFFSET ? ";
            listObject = jdbcTemplate.queryForList(sql, "%" + userName + "%", orgId, "%" + branchId + "%", pageSize, startPage);
        } else if ("branch".equals(orgType)) {
            sql = "SELECT *,orgg.org_name AS org_names,org.org_name AS org_nam FROM hg_party_meeting_plan_info AS meet " +
                    "LEFT JOIN hg_party_meeting_member_info AS mem " +
                    "ON meet.meeting_id=mem.meeting_id " +
                    "LEFT JOIN hg_users_info AS use " +
                    "ON mem.participant_id=use.user_id " +
                    "LEFT JOIN hg_party_org AS org " +
                    "ON use.user_department_id=org.org_id " +
                    "LEFT JOIN hg_party_org AS orgg " +
                    "ON org.org_parent=orgg.org_id " +
                    "WHERE use.user_name LIKE ? " +
                    "AND meet.organization_id=? " +
                    "ORDER BY meet.start_time " +
                    "limit ? OFFSET ? ";
            listObject = jdbcTemplate.queryForList(sql, "%" + userName + "%", orgId, pageSize, startPage);
        }
        return listObject;
    }

    //根据meetingId查询附件
    public Map<String, Object> findAttachmentByMeetingid(String meetingId) {
        String sql = "SELECT attachment_url,attachment_name FROM hg_party_attachment WHERE resource_id='" + meetingId + "'";
        try {
            return this.jdbcTemplate.queryForList(sql).get(0);
        } catch (Exception e) {
            return null;
        }
    }

    //查询参会人员
    public List<Map<String, Object>> meetingUser(String meeting) {
        String sql = "SELECT * from hg_party_group_member_info AS menber JOIN hg_users_info users " +
                "ON menber.participant_id = users.user_id " +
                "WHERE group_id = '" + meeting + "' ";
        return jdbcTemplate.queryForList(sql);
    }

    //会议审核导出excel
    public List<Map<String, Object>> approvalExcel(String perm) {
        String sql = "SELECT plan.meeting_id as meeting,plan.start_time as start_p,plan.end_time as end_p,* from " +
                "((hg_party_meeting_plan_info as plan " +
                "LEFT JOIN hg_party_meeting_notes_info as note on " +
                "plan.meeting_id = note.meeting_id) LEFT JOIN hg_party_org as org on " +
                "org.org_id = plan.organization_id) LEFT JOIN hg_users_info as usr on " +
                "usr.user_id = auditor " +
                "WHERE org.org_type= ? " +
                "and org.historic is false " +
                "AND (plan.task_status='1' " +
                "OR plan.task_status='3' " +
                "OR plan.task_status='4') " +
                "ORDER BY plan.id ";
        return jdbcTemplate.queryForList(sql, perm);
    }

    //查询发送短信内容
    public List<MeetingPlan> MeetingPlan(String id) {
        int Id = Integer.parseInt(id);
        String sql = "SELECT * FROM hg_party_meeting_plan_info WHERE id = ? ";
        RowMapper<MeetingPlan> rowMapper = BeanPropertyRowMapper.newInstance(MeetingPlan.class);
        return this.jdbcTemplate.query(sql, rowMapper, Id);
    }

    //获取组织id
    public List<Map<String, Object>> dep(String de) {
        String sql = "SELECT * FROM hg_party_org WHERE org_id = ? and historic is false ";
        return jdbcTemplate.queryForList(sql, de);
    }

    //二级党组织会议统计
    public List<Map<String, Object>> findSecondMeetingCount(String starDdate, String endDate, String meetType, String theme, String seconedId, String branchId) {
        String sql = "select cc.*,cus.user_name as check_person_org_name from " +
                "(SELECT tt.*,us.user_name as check_person_name FROM " +
                "(SELECT plan.task_status as plan_state, (select org_name from hg_party_org WHERE org_id=plan.organization_id) as branch_name," +
                "(select org_name from hg_party_org WHERE org_id=(SELECT org_parent FROM hg_party_org WHERE org_id=plan.organization_id) and org_type!='organization' ) as second_name," +
                " plan.*,info.release_time from hg_party_meeting_plan_info  as plan ,hg_party_org_inform_info as info";
        StringBuffer buffer = new StringBuffer(sql);
        if (!StringUtils.isEmpty(seconedId) && StringUtils.isEmpty(branchId)) {
            buffer.append(" ,hg_party_org as org_o");
        }
        buffer.append(" WHERE plan.inform_id=info.inform_id");

        if (!StringUtils.isEmpty(starDdate) && StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + starDdate + " 00:00:00' and plan.start_time<'" + starDdate + " 24:00:00'");
        }
        if (!StringUtils.isEmpty(starDdate) && !StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + starDdate + " 00:00:00' and plan.start_time<'" + endDate + " 24:00:00'");
        }
        if (StringUtils.isEmpty(starDdate) && !StringUtils.isEmpty(endDate)) {
            buffer.append(" AND plan.start_time>'" + endDate + " 00:00:00' and plan.start_time<'" + endDate + " 24:00:00'");
        }
        if (!StringUtils.isEmpty(meetType)) {
            buffer.append(" and plan.meeting_type='" + meetType + "'");
        }
        if (!StringUtils.isEmpty(theme)) {
            buffer.append(" and plan.meeting_theme like '%" + theme + "%'");
        }
        if (!StringUtils.isEmpty(seconedId) && !StringUtils.isEmpty(branchId)) {
            buffer.append(" AND plan.organization_id='" + branchId + "'");
        }
        if (!StringUtils.isEmpty(seconedId) && StringUtils.isEmpty(branchId)) {
            buffer.append(" and (org_o.org_parent='" + seconedId + "' or org_o.org_id='" + seconedId + "') and plan.organization_id=org_o.org_id ");
        }
        buffer.append(" ) as tt LEFT OUTER JOIN hg_users_info as us" +
                " on tt.check_person=us.user_id ) as cc LEFT OUTER JOIN hg_users_info as cus" +
                " on cc.check_person_org=cus.user_id");
        return jdbcTemplate.queryForList(buffer.toString());
    }

    public List<Map<String, Object>> findChecKStateById(int id) {
        String sql = "select * from  hg_party_meeting_plan_info as plan " +
                " where plan.organization_id in (select organization_id from hg_party_meeting_plan_info where id= ? ) " +
                " and plan.check_person_org is not  null";
        return jdbcTemplate.queryForList(sql, id);
    }

    public List<Map<String, Object>> findMeetingNote(String meetingId) {
        String sql = "select shoule_persons, actual_persons ,leave_persons ,attendance "
                + " from hg_party_meeting_notes_info where meeting_id= ? ";
        return jdbcTemplate.queryForList(sql, meetingId);


    }

    public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql, String... ss) {
        pageNo = Math.max(1, pageNo);
        String sql1 = sql + " limit " + pageSize + " offset " + (pageNo - 1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1, ss);
        List<Map<String, Object>> count = this.jdbcTemplate.queryForList(sql, ss);
        int total = count.size();
        if (total % pageSize == 0) {
            map.put("totalPage", total / pageSize);
        } else {
            map.put("totalPage", total / pageSize + 1);
        }
        map.put("pageNow", pageNo);
        map.put("list", list);
        return map;
    }


    public PostgresqlPageResult<Map<String, Object>> searchPlanPage(int page, int size, String orgId, String search) {
        if (size <= 0){
            size = 10;
        }
        Organization org = orgDao.findOrgByOrgId(orgId);
        PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(org.getOrg_type());
        StringBuffer sb = new StringBuffer("SELECT plan.*,org.org_name,member.member_name");
        sb.append(" FROM hg_party_meeting_plan_info AS plan");
        sb.append(" LEFT JOIN hg_party_meeting_notes_info AS note ON plan.meeting_id = note.meeting_id");
        sb.append(" left join hg_party_member member on member.member_identity = plan.contact");
        sb.append(" LEFT JOIN hg_party_org org ON org.org_id = plan.organization_id");
        sb.append(" where 1=1");
        sb.append(" and org.historic IS FALSE and member.historic = false AND plan.task_status in('1','3','4')");
        switch(partyOrgAdminTypeEnum){
            case SECONDARY:
                sb.append(" and org.org_parent = ? and org.org_type ='"+PartyOrgAdminTypeEnum.BRANCH.getType()+"'");
                break;
            case ORGANIZATION:;
                sb.append(" and org.org_parent = ? and org.org_type ='"+PartyOrgAdminTypeEnum.SECONDARY.getType()+"'");
                break;
            default: return new PostgresqlPageResult(null, 0,0);
        }
        if(!StringUtils.isEmpty(search)){
            search = "%" + search + "%";
            sb.append(" and (plan.meeting_theme like ? or org.org_name like ? or member.member_name like ?)");
            sb.append(" ORDER BY plan.task_status asc, plan.id desc");
            logger.debug("searchPage:"+sb.toString());
            return postGresqlFindPageBySql(page, size, sb.toString(),orgId,search,search,search);
        }else{
            sb.append(" ORDER BY plan.task_status asc, plan.id desc");
            logger.debug("searchPage:"+sb.toString());
            return postGresqlFindPageBySql(page, size, sb.toString(),orgId);
        }
    }
    public PostgresqlPageResult<Map<String, Object>> searchCheckPage(int page, int size, String orgId, String search) {
        if (size <= 0){
            size = 10;
        }
        Organization org = orgDao.findOrgByOrgId(orgId);
        PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(org.getOrg_type());
        StringBuffer sb = new StringBuffer("SELECT  note.attachment,org.org_id,users.user_name as checker,org.org_name, place.place as placeName, plan.*");
        sb.append(" FROM hg_party_meeting_plan_info AS plan");
        sb.append(" LEFT JOIN hg_party_meeting_notes_info AS note ON plan.meeting_id = note.meeting_id");
        sb.append(" left join hg_users_info users on users.user_id = plan.check_person");
        sb.append(" LEFT JOIN hg_party_org org ON org.org_id = plan.organization_id");
        sb.append(" LEFT JOIN hg_party_place place on place.id = plan.place");
        sb.append(" where 1=1");
        sb.append(" and org.historic IS FALSE AND plan.task_status in ('5','6','7')");
        switch(partyOrgAdminTypeEnum){
            case BRANCH:
                sb.append(" and org.org_id = ?");
                break;
            case SECONDARY:
                sb.append(" and org.org_parent = ? and org.org_type ='"+PartyOrgAdminTypeEnum.BRANCH.getType()+"'");
                break;
            case ORGANIZATION:;
                sb.append(" and org.org_parent = ? and org.org_type ='"+PartyOrgAdminTypeEnum.SECONDARY.getType()+"'");
                break;
            default: return new PostgresqlPageResult(null, 0,0);
        }
        if(!StringUtils.isEmpty(search)){
            search = "%" + search + "%";
            sb.append(" and (plan.meeting_theme like ? or org.org_name like ?)");
            sb.append(" ORDER BY plan.id desc");
            logger.debug("checkPage:"+sb.toString());
            return postGresqlFindPageBySql(page, size, sb.toString(),orgId,search,search,search);
        }else{
            sb.append(" ORDER BY plan.id desc");
            logger.debug("checkPage:"+sb.toString());
            return postGresqlFindPageBySql(page, size, sb.toString(),orgId);
        }
    }

    public PostgresqlPageResult<Map<String, Object>> searchOrgPage(int page, int size, String orgId, String search) {
        if (size <= 0){
            size = 10;
        }
        StringBuffer sb = new StringBuffer("SELECT plan.*,member.member_name");
        sb.append(" FROM hg_party_meeting_plan_info AS plan");
        sb.append(" left join hg_party_member member on member.member_identity = plan.contact");
        sb.append(" where 1=1");
        sb.append(" and plan.organization_id = ?");
        if(!StringUtils.isEmpty(search)){
            search = "%" + search + "%";
            sb.append(" and (plan.meeting_theme like ? or member.member_name like ?)");
            sb.append(" ORDER BY plan.task_status asc, plan.id desc");
            logger.info("searchPage:"+sb.toString());
            return postGresqlFindPageBySql(page, size, sb.toString(),orgId,search,search,search);
        }else{
            sb.append(" ORDER BY plan.task_status asc, plan.id desc");
            logger.info("searchPage:"+sb.toString());
            return postGresqlFindPageBySql(page, size, sb.toString(),orgId);
        }
    }

}
