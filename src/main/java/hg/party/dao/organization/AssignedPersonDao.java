package hg.party.dao.organization;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.liferay.portal.kernel.dao.search.DAOParamUtil;

import hg.party.entity.organization.AssignedPerson;

@Component(immediate = true, service = AssignedPersonDao.class)
public class AssignedPersonDao extends PostgresqlDaoImpl<AssignedPerson> {
	public List<Map<String, Object>> findMeeting(String partyNmae, String meetingType, String partyBranch,
			Date startDate) {
		String sql = "SELECT * from hg_partymeeting WHERE party_branch_id= ? AND meeting_type= ? AND second_party_id= ? ";
		return jdbcTemplate.queryForList(sql, partyBranch, meetingType, partyNmae);
	}

	// 根据查询条件查询数据
	public List<Map<String, Object>> findMeetingPlan(String branchId, String meetingType, String startDate) {
		String sql = "SELECT  u.user_name,tt.*,note.image from "
				+ "(select plan.*,org.org_name ,(SELECT count(*) from hg_party_group_member_info WHERE group_id=plan.participant_group) AS count "
				+ "from hg_party_meeting_plan_info as plan ,hg_party_org as org " + "where plan.organization_id= ? "
				+ "and plan.meeting_type= ? " + "and plan.start_time>'" + startDate + " 00:00:00' "
				+ "and plan.start_time<'" + startDate + " 24:00:00' " + "and plan.organization_id=org.org_id and org.historic is false) as tt "
				+ "LEFT OUTER JOIN hg_party_meeting_notes_info as note " + "on tt.meeting_id=note.meeting_id "
				+ "LEFT OUTER JOIN hg_users_info as u " + "on tt.check_person=u.user_id";
		return jdbcTemplate.queryForList(sql, branchId, meetingType);
	}

	public List<Map<String, Object>> findSconedParty() {
		String sql = "SELECT * from hg_party_org where org_type='secondary' and historic is false order by org_code ";
		return jdbcTemplate.queryForList(sql);
	}
	//根据输入人员获取组织名称
	public List<Map<String, Object>> pid(String name){
		String sql = "SELECT org_names,count(org_names) from "+
					"(SELECT *,orgg.org_name AS org_names FROM hg_party_meeting_plan_info AS meet "+
					"LEFT JOIN hg_party_meeting_member_info AS mem "+
					"ON meet.meeting_id=mem.meeting_id "+
					"LEFT JOIN hg_users_info AS use "+
					"ON mem.participant_id=use.user_id "+
					"LEFT JOIN hg_party_org AS org "+
					"ON use.user_department_id=org.org_id "+
					"LEFT JOIN hg_party_org AS orgg "+
					"ON org.org_parent=orgg.org_id "+
					"WHERE use.user_name LIKE ? "+
					"ORDER BY meet.start_time "+
					") AS gg "+
					"GROUP BY gg.org_names ";
		return jdbcTemplate.queryForList(sql,"%"+name+"%");
	}
	//组织部根据二级党组织名称查询支部
	public List<Map<String, Object>> brinch(String userName,String orgName){
		String sql = "SELECT org_na,count(org_na) FROM "+
					"(SELECT *,org.org_name as org_na,orgg.org_name AS org_names FROM hg_party_meeting_plan_info AS meet "+
					"LEFT JOIN hg_party_meeting_member_info AS mem "+
					"ON meet.meeting_id=mem.meeting_id "+
					"LEFT JOIN hg_users_info AS use "+
					"ON mem.participant_id=use.user_id "+
					"LEFT JOIN hg_party_org AS org "+
					"ON use.user_department_id=org.org_id "+
					"LEFT JOIN hg_party_org AS orgg "+
					"ON org.org_parent=orgg.org_id "+
					"WHERE use.user_name LIKE ? "+
					"AND orgg.org_name = ? "+
					"ORDER BY meet.start_time "+
					") AS mm "+
					"GROUP BY mm.org_na ";
		return jdbcTemplate.queryForList(sql,"%"+userName+"%",orgName);
	}
	//二级党组织查询支部名称
	public List<Map<String, Object>>brinchs(String userName,String orgId){
		String sql = "SELECT org_nam AS org_na,count(org_nam) FROM "+
					"(SELECT *,orgg.org_name AS org_names,org.org_name AS org_nam FROM hg_party_meeting_plan_info AS meet  "+
					"LEFT JOIN hg_party_meeting_member_info AS mem   "+
					"ON meet.meeting_id=mem.meeting_id   "+
					"LEFT JOIN hg_users_info AS use   "+
					"ON mem.participant_id=use.user_id   "+
					"LEFT JOIN hg_party_org AS org   "+
					"ON use.user_department_id=org.org_id   "+
					"LEFT JOIN hg_party_org AS orgg   "+
					"ON org.org_parent=orgg.org_id   "+
					"WHERE use.user_name LIKE ?  "+ 
					"AND orgg.org_id=? "+
					") AS aa "+
					"GROUP BY aa.org_nam ";
		return jdbcTemplate.queryForList(sql,"%"+userName+"%",orgId);
	}
	public List<Map<String, Object>> findPartyBranch(int pid) {
		String sql ="SELECT * from hg_party_org where org_parent= ? and historic is false ";
		return jdbcTemplate.queryForList(sql, pid);
	}

	public List<Map<String, Object>> findPartyBranch(String pid) {
		String sql = "SELECT * from hg_party_org where org_parent= ? and historic is false order by org_code ";
		return jdbcTemplate.queryForList(sql, pid);
	}

	// 通过资源id和发布对象id
	public List<Map<String, Object>> findDtail(String resourceId, String public_object) {
		String sql = "SELECT infrom.id as infromId, * FROM hg_party_public_inform as infrom ,hg_party_organization as org WHERE infrom.resource_id= ? and infrom.public_object= ? AND infrom.public_object=org.\"id\"";
		return jdbcTemplate.queryForList(sql, resourceId, public_object);
	}

	public List<Map<String, Object>> findDtail(String resourceId) {
		String sql = "SELECT tt.*,plan.meeting_id,plan.start_time as time,plan.id as plan_id," +
				"plan.place,plan.check_person ,plan.task_status as state,plan.host ,plan.contact," +
				"plan.contact_phone, plan.participant_group,hg_users_info.user_name as member_name," +
				"(SELECT count(*)  from hg_party_group_member_info WHERE group_id=plan.participant_group) " +
				"AS count  from "
				+ "(select DISTINCT info.*,goin.pub_org_id ,org.org_name from "
				+ "hg_party_org_inform_info as info ,hg_party_inform_group_info as goin ,hg_party_org as org "
				+ "WHERE info.inform_id=goin.inform_id and info.inform_id= ? and org.org_id=goin.pub_org_id and " +
				"org.historic is false AND org.org_type='branch') as tt left outer  JOIN "
				+ "hg_party_meeting_plan_info as plan on tt.inform_id=plan.inform_id and " +
				"tt.pub_org_id=plan.organization_id LEFT  JOIN hg_users_info on plan.check_person=hg_users_info.user_id";
		return jdbcTemplate.queryForList(sql, resourceId);
	}

	public List<Map<String, Object>> findAssignPerson() {
		String sql = "select * from hg_party_assigne";
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> findAssignPerson(int state) {
		String sql = "select * from hg_party_assigne where state='" + state + "'";
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> findAssignPersonAll() {
		String sql = "select * from hg_party_assigne ";
		return jdbcTemplate.queryForList(sql);
	}
	public List<Map<String, Object>> findOrgAssignPerson(String orgId) {
		String sql = "select * from hg_party_assigne where department_name = ? ";
		return jdbcTemplate.queryForList(sql,orgId);
	}
	// 通过组id查询 人数

	public List<Map<String, Object>> findCountPersonsByGroupId(String groupId) {
		String sql = "select * from hg_party_assigne where state='0'";
		return jdbcTemplate.queryForList(sql);
	}

	// 通过计划id 查询该会议的开始于结束时间
	public List<Map<String, Object>> findMeetingStartTimeAndEndTimeById(String id) {
		String sql = "SELECT start_time,end_time from hg_party_meeting_plan_info where id= ? ";
		return jdbcTemplate.queryForList(sql, id);
	}

	// 通过人员id查询属于哪一个组
	public List<Map<String, Object>> findGroupIdByUserId(String participant_id) {
		String sql = "select * from hg_party_group_member_info WHERE participant_id= ? ";
		return jdbcTemplate.queryForList(sql, participant_id);
	}

	// 通过组查询开始于结束时间
	public List<Map<String, Object>> findMeetingStartTimeAndEndTimeByGrroupId(String groupId) {
		String sql = "select * from hg_party_meeting_plan_info WHERE participant_group= ? ";
		return jdbcTemplate.queryForList(sql, groupId);
	}

	public List<Map<String, Object>> findImages(String meetingId) {
		String sql = "select * from hg_party_meeting_notes_info WHERE meeting_id= ? ";
		return jdbcTemplate.queryForList(sql, meetingId);
	}

	public List<Map<String, Object>> findDpartment(String userId) {
		String sql = "SELECT user_department_id from hg_users_info WHERE user_id= ? ";
		return jdbcTemplate.queryForList(sql, userId);
	}

}
