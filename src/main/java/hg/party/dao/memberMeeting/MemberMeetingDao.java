package hg.party.dao.memberMeeting;

import hg.party.entity.party.MeetingStatistics;
import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.memberMeeting.MemberMeeting;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;
import party.portlet.transport.entity.PageQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月4日下午2:08:05<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true,service = MemberMeetingDao.class)
public class MemberMeetingDao extends PostgresqlDaoImpl<MemberMeeting> {

	/**根据会议id和用户id修改用户该会议的查看状态*/
	public void findByMeetingIdAndUserId(String userId,String meetingId){
		String sql="UPDATE hg_party_meeting_member_info SET check_status='已查看' WHERE meeting_id= ? AND participant_id= ? ";
		//this.jdbcTemplate.execute(sql);
		jdbcTemplate.update(sql,meetingId,userId);
	}

	public List<MeetingStatistics> secondaryMeetingStatistics(List<String> orgIds, String start, String end) {
		String suffix = orgIds.stream().map(p -> "?").collect(Collectors.joining(","));
		List<Object> params = new ArrayList<>(orgIds);
		params.addAll(orgIds);
		params.add(start);
		params.add(end);
		String sql = "SELECT \n" +
				"\tT.*,\n" +
				"\tl.id,\n" +
				"\tl.org_name,\n" +
				"\tl.org_type,\n" +
				"\tl.org_secretary \n" +
				"FROM\n" +
				"\t(\n" +
				"\tSELECT P.org_id,\n" +
				"\t\tCOUNT ( DISTINCT o.org_id ) as branch_count,\n" +
				"\tcount(plan.id)\tas plan_count\n" +
				"\tFROM\n" +
				"\t\thg_party_meeting_plan_info plan\n" +
				"\t\tLEFT JOIN hg_party_org o ON plan.organization_id = o.org_id\n" +
				"\t\tLEFT JOIN hg_party_org P ON P.org_id = o.org_parent \n" +
				"\tWHERE\n" +
				"\t\to.historic = FALSE and plan.task_status > '4'\n" +
				"\t\tand (p.org_id in (" + suffix + ") or o.org_id in (" + suffix + ")) \n" +
				"\t\tand plan.start_time >= ?::date and plan.start_time <= ?::date\n" +
				"\tGROUP BY\n" +
				"\t\tP.org_id \n" +
				"\t)\n" +
				"\tT LEFT JOIN hg_party_org l ON T.org_id = l.org_id";
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MeetingStatistics.class), params.toArray());
	}

	public List<Map<String, Object>> secondaryMeetingJoinStatistics(List<String> orgIds, String start, String end) {
		String suffix = orgIds.stream().map(p -> "?").collect(Collectors.joining(","));
		List<Object> params = new ArrayList<>(orgIds);
		params.addAll(orgIds);
		params.add(start);
		params.add(end);
		String sql = "SELECT\n" +
				"\t (case when o.org_parent = 'ddddd' then o.org_id else o.org_parent end) as org_id,\n" +
				"\t( M.member_is_leader = '是' AND M.member_is_leader IS NOT NULL ) AS leader,\n" +
				"\tCOUNT ( DISTINCT M.ID ) \n" +
				"FROM\n" +
				"\t\"hg_party_meeting_member_info\" par\n" +
				"\tINNER JOIN hg_party_member M ON par.participant_id = M.member_identity\n" +
				"\tLEFT JOIN hg_party_org o ON M.member_org = o.org_id \n" +
				"\tLEFT JOIN hg_party_meeting_plan_info plan ON par.meeting_id = plan.meeting_id \n" +
				"\tWHERE\n" +
				"\t\to.historic = FALSE and plan.task_status > '4'" +
				"\t\tand (o.org_parent in (" + suffix + ") or o.org_id in (" + suffix + "))\n" +
				"\t\tand plan.start_time >= ?::date and plan.start_time <= ?::date\n" +
				" GROUP BY\n" +
				"\t( CASE WHEN o.org_parent = 'ddddd' THEN o.org_id ELSE o.org_parent END ),\n" +
				"\t( M.member_is_leader = '是' AND M.member_is_leader IS NOT NULL )";
		return jdbcTemplate.queryForList(sql, params.toArray());
	}


	public List<MeetingStatistics> branchMeetingStatistics(List<String> orgIds, String start, String end) {
		String suffix = orgIds.stream().map(p -> "?").collect(Collectors.joining(","));
		List<Object> params = new ArrayList<>(orgIds);
		params.add(start);
		params.add(end);
		String sql = "SELECT\n" +
				"\to.ID,O.org_type,o.org_name, o.org_id,o.org_secretary,\n" +
				"\t1 AS branch_count,\n" +
				"\tCOUNT ( plan.ID ) AS plan_count \n" +
				"FROM\n" +
				"\thg_party_meeting_plan_info plan\n" +
				"\tLEFT JOIN hg_party_org o ON plan.organization_id = o.org_id\n" +
				"\tLEFT JOIN hg_party_org P ON o.org_parent = P.org_id \n" +
				"WHERE\n" +
				"\to.historic = FALSE \n" +
				"\tAND plan.task_status > '4' \n" +
				"\t\tand o.org_id in (" + suffix + ")\n" +
				"\t\tand plan.start_time >= ?::date and plan.start_time <= ?::date\n" +
				"GROUP BY\n" +
				"\to.ID";
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MeetingStatistics.class), params.toArray());
	}


	public List<Map<String, Object>> branchMeetingJoinStatistics(List<String> orgIds, String start, String end) {
		String suffix = orgIds.stream().map(p -> "?").collect(Collectors.joining(","));
		List<Object> params = new ArrayList<>(orgIds);
		params.add(start);
		params.add(end);
		String sql = "SELECT\n" +
				"\to.org_id,\n" +
				"\t( M.member_is_leader = '是' AND M.member_is_leader IS NOT NULL ) AS leader,\n" +
				"\tCOUNT ( DISTINCT M.ID ) \n" +
				"FROM\n" +
				"\t\"hg_party_meeting_member_info\" par\n" +
				"\tINNER JOIN hg_party_member M ON par.participant_id = M.member_identity\n" +
				"\tLEFT JOIN hg_party_org o ON M.member_org = o.org_id \n" +
				"\tLEFT JOIN hg_party_meeting_plan_info plan ON par.meeting_id = plan.meeting_id \n" +
				"WHERE plan.task_status > '4' o.org_id in (" + suffix + ")\n" +
				"\t\tand plan.start_time >= ?::date and plan.start_time <= ?::date\n" +
				"GROUP BY\n" +
				"\to.org_id,\n" +
				"\t( M.member_is_leader = '是' AND M.member_is_leader IS NOT NULL )";
		return jdbcTemplate.queryForList(sql, params.toArray());
	}

	public PageQueryResult<Map<String, Object>> orgIdsPage(int pageNow, int pageSize, int id, String search) {
		List<Object> params = new ArrayList<>();
		String sql = "select o.org_id, o.id, o.org_type, o.org_name, o.org_secretary from hg_party_org o " +
				" left join hg_party_org p on o.org_parent = p.org_id" +
				" where o. historic = false and (o.id = ? or p.id = ?)";
		params.add(id);
		params.add(id);
		if (!StringUtils.isEmpty(search)) {
			sql += " and o.org_name like ?\n";
			params.add("%" + search + "%");
		}
		sql += "\tORDER BY o. org_type = 'organization' desc, o. org_type = 'secondary' desc";
		return pageBySql(pageNow, pageSize, sql, params);
	}

	public PageQueryResult<Map<String, Object>> pageBySql(int pageNow, int pageSize, String sql, List<Object> objects) {
		if (pageNow <= 0) {
			pageNow = 0;
		} else {
			--pageNow;
		}

		List<Map<String, Object>> list = this.listBySql(pageNow, pageSize, sql, objects);
		int count = this.countBySql(sql, objects);
		return new PageQueryResult(list, count, pageNow, pageSize);
	}

	public int countBySql(String sql, List<Object> objects) {
		int start = sql.toLowerCase().indexOf("from");
		int end = sql.toLowerCase().indexOf("order");
		if (start == -1) {
			return 0;
		}
		if (end == -1){
			end = sql.length();
		}
		sql = "select count(1) " + sql.substring(start, end);
		Integer c = this.jdbcTemplate.queryForObject(sql, Integer.class, objects.toArray(new Object[0]));
		return null == c ? 0 : c;
	}

	private List<Map<String, Object>> listBySql(int pageNo, int pageSize, String sql, List<Object> objects) {
		StringBuffer exeSql = new StringBuffer();
		exeSql.append(sql);
		exeSql.append(" LIMIT ? OFFSET ? ");
		if (objects != null && objects.size() != 0) {
			List list = new ArrayList();
			list.addAll(objects);
			list.add(pageSize);
			list.add(pageNo * pageSize);
			return this.jdbcTemplate.queryForList(exeSql.toString(), list.toArray(new Object[0]));
		} else {
			return this.jdbcTemplate.queryForList(exeSql.toString(), new Object[]{pageSize, pageNo * pageSize});
		}
	}
}
