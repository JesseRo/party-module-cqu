package hg.party.dao.partyBranch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.PublicInformation;
import hg.party.entity.party.MeetingPlan;

@Component(immediate = true, service = PartyBranchDao.class)
public class PartyBranchDao extends PostgresqlDaoImpl<MeetingPlan> {
    private static int IGNORED_STATUS_CODE = 4;
    Logger logger = Logger.getLogger(PartyBranchDao.class);

    public int UpdateInfrom(String sql) {
        return jdbcTemplate.update(sql);
    }

    public int cancleMeetingPlan(String cancleReson, String infromid, String orgId) {
        String sql = "UPDATE hg_party_meeting_plan_info set task_status='2' , " +
                "cancel_reason= ? WHERE inform_id= ? and organization_id= ? ";
        return jdbcTemplate.update(sql, cancleReson, infromid, orgId);
    }

    public int deleteGroupByGroupId(String groupId) {
        String sql = "update hg_party_group_org_info set group_state='0' where group_id= ? ";
        return jdbcTemplate.update(sql, groupId);
    }

    public int deleteMeetingPlan(String meetingId) {
        String sql = "DELETE from hg_party_meeting_plan_info WHERE meeting_id = ?";
        return jdbcTemplate.update(sql, meetingId);
    }

    public PublicInformation findByID(int id) {
        String sql = "select * from hg_party_public_inform where id=?";
//    	   logger.info("sql :" + sql);
        RowMapper<PublicInformation> rowMapper = BeanPropertyRowMapper.newInstance(PublicInformation.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);

    }

    public List<Map<String, Object>> findTaskByBOrgId(String orgId, String state, String name) {

        String sql = "SELECT  tt.*,plan.id ,plan.task_status as state, plan.meeting_id from " +
                "(select info.*,gup.pub_org_id from hg_party_inform_group_info as gup " +
                "JOIN hg_party_org_inform_info as info on gup.inform_id=info.inform_id " +
                "WHERE gup.pub_org_id='" + orgId + "' and info.public_status='1'";

        StringBuffer buffer = new StringBuffer(sql);
        if (state != null && !state.trim().equals("") && name != null && !name.trim().equals("")) {
            if ("meetingType".equals(state)) {
                buffer.append(" and info.meeting_type='" + name + "'");
                buffer.append(") as tt LEFT JOIN hg_party_meeting_plan_info as plan ON tt.inform_id=plan.inform_id ORDER BY plan.task_status DESC");
            }
            if ("taskState".equals(state)) {
                buffer.append(") as tt LEFT JOIN hg_party_meeting_plan_info as plan ON tt.inform_id=plan.inform_id and plan.task_status='" + name + "'");
            }
        }
        if (name == null || name.trim().equals("")) {
            buffer.append(") as tt LEFT JOIN hg_party_meeting_plan_info as plan ON tt.inform_id=plan.inform_id ORDER BY plan.task_status DESC");
        }
//    	  logger.info("sql :" + buffer.toString());
        return jdbcTemplate.queryForList(buffer.toString());
    }

    public List<Map<String, Object>> findTaskByBOrgId(String orgId, String state, String name, int size, int startPage) {
        String sql = "SELECT DISTINCT  tt.*,plan.id ,plan.task_status as state, plan.meeting_id ,plan.check_status from " +
                "(select info.*,gup.pub_org_id from hg_party_inform_group_info as gup " +
                "JOIN hg_party_org_inform_info as info on gup.inform_id=info.inform_id " +
                "WHERE gup.pub_org_id='" + orgId + "' and (info.public_status='1' OR info.public_status='2')";

        StringBuffer buffer = new StringBuffer(sql);
        if (state != null && !state.trim().equals("") && name != null && !name.trim().equals("")) {
            if ("meetingType".equals(state)) {
                buffer.append(" and info.meeting_type='" + name + "'");
                buffer.append(") as tt left JOIN hg_party_meeting_plan_info as plan ON tt.inform_id=plan.inform_id ORDER BY plan.task_status DESC LIMIT " + size + " OFFSET " + startPage + "");
            }
            if ("taskState".equals(state)) {
                buffer.append(") as tt JOIN hg_party_meeting_plan_info as plan ON tt.inform_id=plan.inform_id and plan.task_status='" + name + "' LIMIT " + size + " OFFSET " + startPage + "");
            }
        }
        if (name == null || name.trim().equals("")) {
            buffer.append(") as tt LEFT JOIN hg_party_meeting_plan_info as plan ON tt.inform_id=plan.inform_id ORDER BY plan.task_status DESC LIMIT " + size + " OFFSET " + startPage + "");
        }

//        	  logger.info("sql :" + buffer.toString());
        return jdbcTemplate.queryForList(buffer.toString());

    }


    //党支部根据组织ID查询所有通知计划
    public Map<String, Object> queryInformMeetingsByOrgId(String orgId, String meetingType, String taskStatus, int page) {

        String sql = "SELECT\n" +
                "i.inform_id,\n" +
                "i.inform_status,\n" +
                "i.deadline_time,\n" +
                "i.meeting_type AS imeetingtype,\n" +
                "i.meeting_theme AS imeetingtheme,\n" +
                "g.pub_org_id,\n" +
                "g.has_resend,\n" +
                "g.read_status,\n" +
                "g.send_to,\n" +
                "i.content AS icontent,\n" +
                "i.start_time AS istarttime,\n" +
                "i.end_time AS iendtime,\n" +
                "i.publisher,\n" +
                "i.public_status,\n" +
                "i.release_time,\n" +
                "i.remark AS iremark,\n" +
                "i.send_branch,\n" +
                "i.org_type,\n" +
                "i.parent\n" +
//                "m.meeting_id,\n" +
//                "m.organization_id,\n" +
//                "m.meeting_type AS mmeetingtype,\n" +
//                "m.meeting_theme AS mmeetingtheme,\n" +
//                "m.place,\n" +
//                "m.start_time AS mstarttime,\n" +
//                "m.end_time AS mendtime,\n" +
//                "m.total_time,\n" +
//                "m.participant_group,\n" +
//                "m.contact,\n" +
//                "m.contact_phone,\n" +
//                "m.content AS mcontent,\n" +
//                "m.task_status,\n" +
//                "m.read_receipt,\n" +
//                "m.check_status,\n" +
//                "m.submit_time\n" +
                "FROM\n" +
                "hg_party_org_inform_info AS i\n" +
                "INNER JOIN hg_party_inform_group_info AS g ON i.inform_id = g.inform_id\n" +
//                "LEFT JOIN hg_party_meeting_plan_info AS m ON g.inform_id = m.inform_id AND g.pub_org_id = m.organization_id\n" +
                "WHERE\n" +
                "g.pub_org_id = '" + orgId + "' AND (i.public_status = '2' OR i.public_status = '1') \n";
//    		   if (!StringUtils.isEmpty(meetingType) ) {
//    			   sql += "AND i.meeting_type = '" + meetingType + "'\n";
//    		   }
//    		   if (!StringUtils.isEmpty(taskStatus)) {
//    			   sql += "AND i.task_status = '" + taskStatus + "'\n";
//    		   }
        if (!StringUtils.isEmpty(meetingType)) {
            sql += "AND i.meeting_type = '" + meetingType + "'\n";
        }
        if (!StringUtils.isEmpty(taskStatus)) {
            try {
                int statusCode = Integer.valueOf(taskStatus);
                if (statusCode > 0) {
                    if (statusCode < IGNORED_STATUS_CODE) {
                        sql += "AND cast(m .task_status as INTEGER) = " + statusCode + "\n";
                    } else {
                        sql += "AND cast(m .task_status as INTEGER) >= " + IGNORED_STATUS_CODE + "\n";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (page == 0) {
            page = 1;
        }

        sql += "ORDER BY g.read_status ,release_time DESC ";
        logger.info("sql 111111:" + sql);
        //return postGresqlFindBySql(page, 8, sql );
        String sql1 = sql + " limit 8 offset " + (page - 1) * 8;
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1);
        List<Map<String, Object>> count = this.jdbcTemplate.queryForList(sql);
        int total = count.size();
        if (total % 8 == 0) {
            map.put("totalPage", total / 8);
        } else {
            map.put("totalPage", total / 8 + 1);
        }
        map.put("pageNow", page);
        map.put("list", list);
        return map;
    }

	public Map<String, Object> queryInformMeetingsByOrgIdAndInformId(String orgId, String meetingType, String taskStatus, String informId,int page) {

		String sql = "SELECT\n" +
				"i.inform_id,\n" +
				"i.inform_status,\n" +
				"i.deadline_time,\n" +
				"i.meeting_type AS imeetingtype,\n" +
				"i.meeting_theme AS imeetingtheme,\n" +
				"g.pub_org_id,\n" +
				"g.has_resend,\n" +
				"g.read_status,\n" +
				"g.send_to,\n" +
				"i.content AS icontent,\n" +
				"i.start_time AS istarttime,\n" +
				"i.end_time AS iendtime,\n" +
				"i.publisher,\n" +
				"i.public_status,\n" +
				"i.release_time,\n" +
				"i.remark AS iremark,\n" +
				"i.send_branch,\n" +
				"i.org_type,\n" +
				"i.parent,\n" +
				"m.meeting_id,\n" +
				"m.organization_id,\n" +
				"m.meeting_type AS mmeetingtype,\n" +
				"m.meeting_theme AS mmeetingtheme,\n" +
				"m.place,\n" +
				"m.start_time AS mstarttime,\n" +
				"m.end_time AS mendtime,\n" +
				"m.total_time,\n" +
				"m.participant_group,\n" +
				"m.contact,\n" +
				"m.contact_phone,\n" +
				"m.content AS mcontent,\n" +
				"m.task_status,\n" +
				"m.read_receipt,\n" +
				"m.check_status,\n" +
				"m.submit_time\n" +
				"FROM\n" +
				"hg_party_org_inform_info AS i\n" +
				"INNER JOIN hg_party_inform_group_info AS g ON i.inform_id = g.inform_id\n" +
				"INNER JOIN hg_party_meeting_plan_info AS m ON g.inform_id = m.inform_id AND g.pub_org_id = m.organization_id\n" +
				"WHERE\n" +
				"g.pub_org_id = '" + orgId + "' AND (i.public_status = '2' OR i.public_status = '1') " +
				"and i.inform_id = '" + informId + "'";
//    		   if (!StringUtils.isEmpty(meetingType) ) {
//    			   sql += "AND i.meeting_type = '" + meetingType + "'\n";
//    		   }
//    		   if (!StringUtils.isEmpty(taskStatus)) {
//    			   sql += "AND i.task_status = '" + taskStatus + "'\n";
//    		   }
		if (!StringUtils.isEmpty(meetingType)) {
			sql += "AND i.meeting_type = '" + meetingType + "'\n";
		}
		if (!StringUtils.isEmpty(taskStatus)) {
			try {
				int statusCode = Integer.valueOf(taskStatus);
				if (statusCode > 0) {
					if (statusCode < IGNORED_STATUS_CODE) {
						sql += "AND cast(m .task_status as INTEGER) = " + statusCode + "\n";
					} else {
						sql += "AND cast(m .task_status as INTEGER) >= " + IGNORED_STATUS_CODE + "\n";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (page == 0) {
			page = 1;
		}

		sql += "ORDER BY g.read_status ,release_time DESC ";
		logger.info("sql 111111:" + sql);
		//return postGresqlFindBySql(page, 8, sql );
		String sql1 = sql + " limit 8 offset " + (page - 1) * 8;
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1);
		List<Map<String, Object>> count = this.jdbcTemplate.queryForList(sql);
		int total = count.size();
		if (total % 8 == 0) {
			map.put("totalPage", total / 8);
		} else {
			map.put("totalPage", total / 8 + 1);
		}
		map.put("pageNow", page);
		map.put("list", list);
		return map;
	}


	public List<Map<String, Object>> exportBranchTask(String orgId, String meetingType, String taskStatus, int page) {

        String sql = "SELECT\n" +
                "i.inform_id,\n" +
                "i.inform_status,\n" +
                "i.deadline_time,\n" +
                "i.meeting_type AS imeetingtype,\n" +
                "i.meeting_theme AS imeetingtheme,\n" +
                "g.pub_org_id,\n" +
                "g.has_resend,\n" +
                "g.read_status,\n" +
                "g.send_to,\n" +
                "i.content AS icontent,\n" +
                "i.start_time AS istarttime,\n" +
                "i.end_time AS iendtime,\n" +
                "i.publisher,\n" +
                "i.public_status,\n" +
                "i.release_time,\n" +
                "i.remark AS iremark,\n" +
                "i.send_branch,\n" +
                "i.org_type,\n" +
                "i.parent,\n" +
                "m.meeting_id,\n" +
                "m.organization_id,\n" +
                "m.meeting_type AS mmeetingtype,\n" +
                "m.meeting_theme AS mmeetingtheme,\n" +
                "m.place,\n" +
                "m.start_time AS mstarttime,\n" +
                "m.end_time AS mendtime,\n" +
                "m.total_time,\n" +
                "m.participant_group,\n" +
                "m.contact,\n" +
                "m.contact_phone,\n" +
                "m.content AS mcontent,\n" +
                "m.task_status,\n" +
                "m.read_receipt,\n" +
                "m.check_status,\n" +
                "m.submit_time\n" +
                "FROM\n" +
                "hg_party_org_inform_info AS i\n" +
                "INNER JOIN hg_party_inform_group_info AS g ON i.inform_id = g.inform_id\n" +
                "LEFT JOIN hg_party_meeting_plan_info AS m ON g.inform_id = m.inform_id AND g.pub_org_id = m.organization_id\n" +
                "WHERE\n" +
                "g.pub_org_id = '" + orgId + "' AND (i.public_status = '2' OR i.public_status = '1') \n";
//    		   if (!StringUtils.isEmpty(meetingType) ) {
//    			   sql += "AND i.meeting_type = '" + meetingType + "'\n";
//    		   }
//    		   if (!StringUtils.isEmpty(taskStatus)) {
//    			   sql += "AND i.task_status = '" + taskStatus + "'\n";
//    		   }
        if (!StringUtils.isEmpty(meetingType)) {
            sql += "AND i.meeting_type = '" + meetingType + "'\n";
        }
        if (!StringUtils.isEmpty(taskStatus)) {
            try {
                int statusCode = Integer.valueOf(taskStatus);
                if (statusCode > 0) {
                    if (statusCode < IGNORED_STATUS_CODE) {
                        sql += "AND cast(m .task_status as INTEGER) = " + statusCode + "\n";
                    } else {
                        sql += "AND cast(m .task_status as INTEGER) >= " + IGNORED_STATUS_CODE + "\n";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sql += "ORDER BY g.read_status,release_time DESC ";
//    		   logger.info("sql 111111:" + sql);
        return jdbcTemplate.queryForList(sql);
    }

    //查询
    public List<Map<String, Object>> findTask(String informId, String orgId) {
        String sql = "SELECT * from hg_party_org_inform_info as info ,hg_party_inform_group_info as grop " +
                "WHERE info.inform_id=? " +
                "AND info.inform_id=grop.inform_id " +
                "and pub_org_id=? ";
//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, informId, orgId);
    }

    public List<Map<String, Object>> getGroupByOrgId(String orgId) {
        String sql = "SELECT * from hg_party_group_org_info WHERE organization_id=? and group_state='1'";
//	       	logger.info("sql :" + sql);	   
        return jdbcTemplate.queryForList(sql, orgId);
    }

    public List<Map<String, Object>> findOrgMessage(String orgId) {
        String sql = "SELECT * from hg_party_org WHERE org_id=? and historic is false";
//       	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, orgId);
    }

    public List<Map<String, Object>> findGroupPersons(String groupId) {
        String sql =
                "SELECT info.group_name, g.*,m.user_name as member_name from hg_party_group_member_info as  g JOIN hg_party_group_org_info as info on g.group_id=info.group_id " +
                        "JOIN  hg_users_info  as m ON  g.participant_id=m.user_id " +
                        "WHERE g.group_id=?";
//   	 	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, groupId);
    }

    //查看回执
    public List<Map<String, Object>> findReplyState(String infromId, String orgId) {
        String sql =
                "SELECT meet.*  ,mber.user_name as member_name from hg_party_meeting_member_info as meet,hg_users_info as mber " +
                        "where meet.meeting_id=(SELECT info.meeting_id from hg_party_meeting_plan_info as info WHERE " +
                        " info.inform_id=? AND info.organization_id=?) " +
                        " AND meet.participant_id=mber.user_id ";
//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, infromId, orgId);
    }

    //查看已读未读回执
    public List<Map<String, Object>> findReplysState(String infromId, String orgId, String status) {
        String sql = "SELECT m.meeting_id,m.meeting_type,m.meeting_theme,c.comments_time,u.user_name,e.upload_state,e.upload_time,mm.check_status,c.comments_score,c.comments_aspects_two,c.comments_aspects_three,c.comments_aspects_four,c.comments_aspects_one,c.comments_state ,u.user_id FROM hg_party_meeting_plan_info AS m LEFT JOIN hg_party_meeting_member_info AS mm ON m.meeting_id = mm.meeting_id LEFT JOIN hg_users_info AS u ON mm.participant_id = u.user_id LEFT JOIN hg_party_comments_info AS c ON mm.meeting_id = c.meeting_id AND u.user_id = c.participant_id LEFT JOIN hg_party_learning_experience AS e ON mm.meeting_id = e.meeting_id AND u.user_id = e.participant_id WHERE m.organization_id = ? AND m.inform_id = ? ";
        if (!StringUtils.isEmpty(status)) {
            sql += "AND mm.check_status = '" + status + "'";
        }
//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, orgId, infromId);
    }


    //查看回执,评分，心得
    public List<Map<String, Object>> findMeetingReplays(String meetingId) {

        String sql = " SELECT r.check_status,r.meeting_id,u.user_id,u.user_name,"
                + "c.comments_score,e.upload_time FROM hg_party_meeting_member_info AS r"
                + " INNER JOIN hg_users_info AS u ON r.participant_id = u.user_id LEFT JOIN"
                + " hg_party_comments_info AS c ON r.meeting_id = c.meeting_id AND"
                + " r.participant_id = c.participant_id LEFT JOIN hg_party_learning_experience"
                + " AS e ON r.meeting_id = e.meeting_id AND r.participant_id = e.participant_id"
                + " WHERE r.meeting_id = ?";
//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, meetingId);
    }

    //查看个人会议评论
    public List<Map<String, Object>> findPersonalMeetingComment(String meetingId, String userId) {
        String sql = "SELECT m.meeting_type,m.meeting_theme,m.place,c.* FROM hg_party_comments_info AS c LEFT JOIN hg_party_meeting_plan_info AS m ON c.meeting_id = m.meeting_id WHERE c.meeting_id = ? AND c.participant_id = ?";

//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, meetingId, userId);
    }


    //查看个人学习心得
    public List<Map<String, Object>> findPersonalMeetingExperience(String meetingId, String userId) {
        String sql = "SELECT e.meeting_id,e.participant_id,e.upload_state,e.upload_time,e.experience_content,e.remark,m.meeting_type,m.meeting_theme,u.user_name FROM hg_party_learning_experience AS e LEFT JOIN public.hg_party_meeting_plan_info AS m ON e.meeting_id = m.meeting_id LEFT JOIN public.hg_users_info AS u ON e.participant_id = u.user_id WHERE e.meeting_id = ? AND e.participant_id = ?";

//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, meetingId, userId);
    }


    //根据会议Id查询会议信息
    public List<Map<String, Object>> queryMeetingTimeByMeetingId(String meetingId) {
        String sql = "SELECT m.meeting_id,m.start_time,m.end_time FROM hg_party_meeting_plan_info AS m WHERE m.meeting_id = ?";
//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, meetingId);
    }


    public List<Map<String, Object>> findReplyState(String infromId, String orgId, String state) {
        String sql =
                "SELECT meet.*  ,mber.user_name as member_name from hg_party_meeting_member_info as meet,hg_users_info as mber " +
                        "where meet.meeting_id=(SELECT info.meeting_id from hg_party_meeting_plan_info as info WHERE " +
                        "info.inform_id=? AND info.organization_id=?) " +
                        "AND meet.participant_id=mber.user_id  and meet.check_status=?";
//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, infromId, orgId, state);
    }

    public List<Map<String, Object>> find(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> findAttachment(String meetingId) {
        String sql = "select tt.*,att.attachment_name,att.attachment_url from " +
                "(select plan.*,org.org_name ,info.start_time as info_start ,info.end_time as info_end " +
                "from hg_party_meeting_plan_info as plan ,hg_party_org as org ,hg_party_org_inform_info as info " +
                "where  plan.organization_id=org.org_id and org.historic is false AND " +
                "plan.meeting_id= ? and plan.inform_id=info.inform_id) as tt " +
                "LEFT OUTER JOIN hg_party_attachment  as att " +
                "on tt.meeting_id=att.resource_id";
        return jdbcTemplate.queryForList(sql, meetingId);
    }

    public List<Map<String, Object>> findAttachmentSeconed(String meetingId) {
//        String sql = "select tt.*,att.attachment_name,att.attachment_url from " +
//                "(select plan.*,org.org_name ,info.start_time as info_start ,info.end_time as info_end " +
//                "from hg_party_meeting_plan_info as plan ,hg_party_org as org ,hg_party_org_inform_info as info " +
//                "where  plan.organization_id=org.org_id and org.historic is false AND " +
//                "plan.meeting_id= ?  and plan.inform_id=info.inform_id) as tt " +
//                "LEFT OUTER JOIN hg_party_attachment  as att " +
//                "on tt.meeting_id=att.resource_id";
        String sql = "select plan.*, org.org_name, att.attachment_name,att.attachment_url " +
                "from hg_party_meeting_plan_info as plan left join hg_party_org as org " +
                "on  plan.organization_id=org.org_id and org.historic is false " +
                "LEFT OUTER JOIN hg_party_attachment as att " +
                "on plan.meeting_id=att.resource_id " +
                "where plan.meeting_id= ? ";
        return jdbcTemplate.queryForList(sql, meetingId);
    }

    public List<Map<String, Object>> getPlance(String orgId) {
        String sql = "select * from hg_party_place where org_id=? ORDER BY add_time DESC";
        return jdbcTemplate.queryForList(sql, orgId);
    }

    public List<Map<String, Object>> listOrgMessage(String orgId) {
        String sql = "SELECT * FROM hg_party_org "
                + "WHERE historic is false and org_id=(select org_parent from hg_party_org where historic is false and org_id= ? )";

        return jdbcTemplate.queryForList(sql, orgId);
    }

    public List<Map<String, Object>> getPlanceAll() {
        String sql = "select * from hg_party_place  ORDER BY add_time DESC";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getOrgParentIdByBranchId(String branchId) {
        String sql = "select * from hg_party_org  where org_id=? and historic is false";
        return jdbcTemplate.queryForList(sql, branchId);
    }

    public List<String> getSelectedPlace(Object start, Object end) {
        String sql = "SELECT\n" +
                "\tplace.place_id\n" +
                "FROM\n" +
                "\thg_party_meeting_plan_info plan\n" +
                "INNER JOIN hg_party_place place ON plan.place = place.place\n" +
                "WHERE\n" +
                "\tstart_time BETWEEN '" + start + "'\n" +
                "AND '" + end + "'\n" +
                "OR end_time BETWEEN '" + start + "'\n" +
                "AND '" + end + "'";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<Map<String, Object>> findSconedAndBranch(String orgId) {
        String sql = "select org_type from hg_party_org where org_id=? and historic is false";
//		   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, orgId);
    }

    public List<Map<String, Object>> isExist(String gropId, String userId) {
        String sql = "select * from hg_party_group_member_info WHERE group_id=? and participant_id=? ";
//		   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, gropId, userId);
    }

    public List<Map<String, Object>> findDetail(String infromId, String orgId) {
        String sql = "select * from hg_party_org_inform_info as info LEFT OUTER JOIN hg_party_attachment as att ON info.inform_id=att.resource_id left join hg_party_inform_group_info grp on info.inform_id = grp.inform_id and grp.pub_org_id = ?" +
                "WHERE info.inform_id=?";
//    	   logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, orgId, infromId);
    }

    //根据会议id和用户id修改用户该会议的查看状态
    public void findByMeetingStatus(String inform_id, String org_id) {
        String sql = "UPDATE hg_party_inform_group_info set read_status='已查看' " +
                "WHERE inform_id=? " +
                "AND pub_org_id=? ";
//	   		this.jdbcTemplate.execute(sql);
        jdbcTemplate.update(sql, inform_id, org_id);
    }

    public List<Map<String, Object>> findInformDetail(String infromId, String orgId) {
        String sql = "SELECT i.inform_id,i.meeting_type,i.meeting_theme,i.content,i.start_time,i.end_time,i.publisher,i.release_time,"
                + "g.has_resend,g.read_status FROM public.hg_party_org_inform_info AS i LEFT JOIN "
                + "public.hg_party_inform_group_info AS g ON i.inform_id = g.inform_id "
                + "WHERE g.pub_org_id = ? AND g.inform_id = ?";
//    	  logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, orgId, infromId);
    }

    public List<Map<String, Object>> findInformAttachment(String infromId) {
        String sql = "SELECT a.* FROM public.hg_party_attachment AS a WHERE a.resource_id = ?";
//     	  logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, infromId);
    }


    public List<Map<String, Object>> findMeetingDetail(String meetingId) {
        String sql = "SELECTm.meeting_id,m.inform_id,m.organization_id,m.meeting_type,m.meeting_theme,"
                + "m.start_time,m.end_time,m.place,m.total_time,m.content,m.remark,m.cancel_reason,"
                + "g.has_resend,g.read_status FROM public.hg_party_meeting_plan_info AS m "
                + "LEFT JOIN public.hg_party_inform_group_info AS g ON m.inform_id = g.inform_id "
                + "AND m.organization_id = g.pub_org_idWHERE m.meeting_id = ?";
//       	  logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, meetingId);
    }

    public List<Map<String, Object>> findMeetingAttachment(String meetingId) {
        String sql = "SELECT a.*,a.attachment_date,m.meeting_id,m.inform_id FROM hg_party_attachment"
                + " AS a LEFT JOIN hg_party_meeting_plan_info AS m ON a.resource_id = m.inform_id"
                + " WHERE m.meeting_id = ?";

//      			  logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, meetingId);
    }

    public List<Map<String, Object>> personalScoreDetail(String meetingId, String userId) {

        String sql = "SELECT a.*,a.attachment_date,m.meeting_id,m.inform_id FROM hg_party_attachment"
                + " AS a LEFT JOIN hg_party_meeting_plan_info AS m ON a.resource_id = m.inform_id"
                + " WHERE m.meeting_id = ?";

//       			  logger.info("sql :" + sql);
        return jdbcTemplate.queryForList(sql, meetingId);
    }

    public List<Map<String, Object>> findBranchPersons(String branchId) {
        // String sql="select * from hg_users_info WHERE user_department_id=?";
        String sql = "select u.* from hg_users_info as u JOIN hg_party_member as mb on u.user_id = mb.member_identity " +
                "where mb.historic is false " +
                "AND u.user_department_id= ? ";

        return jdbcTemplate.queryForList(sql, branchId);
    }

    public int deletePersons(String groupId, String userId) {
        String sql = "delete from hg_party_group_member_info where group_id=? and participant_id=?";
        return jdbcTemplate.update(sql, groupId, userId);
    }

    public MeetingPlan findMeetingPlan(String meetingId) {
        String sql = "select * FROM  hg_party_meeting_plan_info where meeting_id=?";
        RowMapper<MeetingPlan> rowMapper = BeanPropertyRowMapper.newInstance(MeetingPlan.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, meetingId);

    }
}
