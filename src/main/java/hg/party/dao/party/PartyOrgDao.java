package hg.party.dao.party;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import hg.party.entity.party.BaseStatistics;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.Organization;
import hg.party.entity.party.UserStatistics;

/**
 * 文件名称： party<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月3日下午3:00:35<br>
 */
@Component(immediate = true,service = PartyOrgDao.class)
public class PartyOrgDao extends PostgresqlDaoImpl<Organization>{
	//通过二级党委查询书记
	public List<Organization> findByOrgId(String org_id){
		String sql = "SELECT * FROM hg_party_org "+
					"WHERE org_id= ? and historic is false";	
		RowMapper<Organization> rowMapper = BeanPropertyRowMapper.newInstance(Organization.class);
		return this.jdbcTemplate.query(sql, rowMapper,org_id);
	}
	//报表学院参会情况
	public List<Map<String, Object>> partyEcharts(){
//		String sql = "SELECT org_name as keyname,COUNT(*) as valname FROM hg_party_org_inform_info as info LEFT OUTER JOIN hg_party_org as org "+
//					"ON info.org_type = org.org_id "+
//					"GROUP BY org_name ";
		
		String sql = "select f.p, org_p.org_name as keyname, f.num as valname from (SELECT org.org_parent as p, count(plan.id) as num from  hg_party_meeting_plan_info as plan "+
					"Left JOIN hg_party_org as org  ON org.org_id=plan.organization_id "+
				    "where org.org_type='branch' and org.historic is false  GROUP BY org.org_parent) as f LEFT JOIN hg_party_org as org_p on org_p.org_id = f.p WHERE org_p.historic is false ";
		return jdbcTemplate.queryForList(sql);
	}
	//学院出勤率报表
	public List<Map<String, Object>> attenEcharts(){
//		String sql = "SELECT member_party_committee as keyname,avg(to_number(trim(both '%' from attendance), '999'))valname "+
//				    "from hg_party_meeting_plan_info AS plan "+
//					"LEFT JOIN (SELECT member_party_committee,member_org,historic,count(*) from hg_party_member AS mem "+
//					"GROUP BY member_party_committee,member_org,historic) AS mber "+
//					"ON plan.organization_id=mber.member_org "+
//					"LEFT JOIN hg_party_meeting_notes_info as note "+
//					"ON plan.meeting_id=note.meeting_id "+
//					"WHERE meeting_type='主题党日' "+
//					"AND mber.member_party_committee is not null "+
//					"AND mber.member_party_committee <>'' "+
//					"and mber.historic is false "+
//					"AND note.attendance <>'' "+
//					"AND note.attendance is not null  "+
//					"AND plan.start_time "+
//					"between "+
//					"(CURRENT_DATE-31) and CURRENT_DATE GROUP BY member_party_committee "+
//					"ORDER BY valname desc "+
//					"LIMIT 5 ";
		String sql = "SELECT member_party_committee as keyname,avg(to_number(trim(both '%' from attendance), '999'))valname "+
					"from hg_party_meeting_plan_info AS plan  "+
					"LEFT JOIN (SELECT member_party_committee,member_org,historic,count(*) from hg_party_member AS mem  "+
					"GROUP BY member_party_committee,member_org,historic) AS mber  "+
					"ON plan.organization_id=mber.member_org  "+
					"LEFT JOIN hg_party_meeting_notes_info as note  "+
					"ON plan.meeting_id=note.meeting_id  "+
					"WHERE meeting_type='主题党日'  "+
					"AND mber.member_party_committee is not null  "+
					"AND mber.member_party_committee <>''  "+
					"and mber.historic is false  "+
					"AND note.attendance <>''  "+
					"AND note.attendance is not null   "+
					"GROUP BY member_party_committee  "+
					"ORDER BY valname desc  "+
					"LIMIT 5 ";
		return jdbcTemplate.queryForList(sql);
	}
	
	//报表会议类型分布
	public List<Map<String, Object>> partyEchartsType(){
		String sql = "SELECT meeting_type as keyname,COUNT(*) as valname FROM hg_party_meeting_plan_info WHERE meeting_type is not null and meeting_type != '' GROUP BY meeting_type ";
		return jdbcTemplate.queryForList(sql);
	}
	//查询二级党组织个数
	public List<Map<String, Object>> orgNumber(){
		String sql = "SELECT count(*) from hg_party_org WHERE org_type='secondary' and historic is false ";
		return jdbcTemplate.queryForList(sql);
	}
	//查询党支部个数
	public List<Map<String, Object>> branchNumber(){
		String sql = "SELECT count(*) from hg_party_org WHERE org_type='branch' and historic is false ";
		return jdbcTemplate.queryForList(sql);
	}
	//查询党员人数
	public List<Map<String, Object>> userNumber(){
		String sql = "select count(*) from hg_party_member where historic = false ";
		return jdbcTemplate.queryForList(sql);
	}
	//党员统计
	public UserStatistics userStatistics(){
		String sql = "select count(*) count ,sum(case when member_sex='男' then 1 else 0 end ) maleCount,sum(case when member_sex='女' then 1 else 0 end ) femaleCount from hg_party_member where historic = false ";
		RowMapper<UserStatistics> rowMapper = BeanPropertyRowMapper.newInstance(UserStatistics.class);
		return jdbcTemplate.queryForObject(sql,rowMapper);
	}
	//学院开展活动统计
	public List<BaseStatistics> collegeActivitiesStatistics(int year, int month){
		Calendar calendar = Calendar.getInstance();
		RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
		if(year==0 && month ==0){//统计所有
			String sql = "select  o.org_name property,COALESCE(i.num,0) num  from (select org_name,org_id from hg_party_org where org_type = 'secondary') o left join (select org_type, count(org_type) num from hg_party_org_inform_info group by org_type) i on  i.org_type = o.org_id";
			return jdbcTemplate.query(sql,rowMapper);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String sql = "select  o.org_name property,COALESCE(i.num,0) num  from (select org_name,org_id from hg_party_org where org_type = 'secondary') o left join (select org_type, count(org_type) num from hg_party_org_inform_info where start_time>=? and start_time < ? group by org_type) i on  i.org_type = o.org_id";
		if(year>0 && month==0){//全年统计
			calendar.set(year,0,1);
			Timestamp startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
			calendar.set(year,11,1);
			Timestamp  endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
			return jdbcTemplate.query(sql,rowMapper,startTime,endTime);
		}else{//按月统计
			calendar.set(year,month-1,1);
			Timestamp startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
			calendar.set(year,month,1);
			Timestamp  endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
			return jdbcTemplate.query(sql,rowMapper,startTime,endTime);
		}

	}
	//学院开展活动统计
	public List<BaseStatistics> searchCollegeActivitiesStatistics(Timestamp startTime, Timestamp  endTime){
		RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
		String sql = "select  o.org_name property,COALESCE(i.num,0) num  from (select org_name,org_id from hg_party_org where org_type = 'secondary') o left join (select org_type, count(org_type) num from hg_party_org_inform_info where start_time>=? and start_time < ? group by org_type) i on  i.org_type = o.org_id";
		return jdbcTemplate.query(sql,rowMapper,startTime,endTime);

	}
	//党活动分类年月统计
	public List<BaseStatistics> activitiesTypeStatistic(int year, int month){
		Calendar calendar = Calendar.getInstance();
		RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
		if(year==0 && month ==0){//统计所有
			String sql = "SELECT i.meeting_type property,count(i.meeting_type) num  from hg_party_org_inform_info i group by i.meeting_type";
			return jdbcTemplate.query(sql,rowMapper);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String sql = "SELECT i.meeting_type property,count(i.meeting_type) num  from hg_party_org_inform_info i  group by i.meeting_type where start_time>=? and start_time < ?";
		if(year>0 && month==0){//全年统计
			calendar.set(year,0,1);
			Timestamp startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
			calendar.set(year,11,1);
			Timestamp  endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
			return jdbcTemplate.query(sql,rowMapper,startTime,endTime);
		}else{//按月统计
			calendar.set(year,month-1,1);
			Timestamp startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
			calendar.set(year,month,1);
			Timestamp  endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
			return jdbcTemplate.query(sql,rowMapper,startTime,endTime);
		}
	}
	//党活动分类起止日期统计
	public List<BaseStatistics> searchActivitiesTypeStatistics(Timestamp startTime, Timestamp  endTime){
		RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
		String sql = "SELECT i.meeting_type property,count(i.meeting_type) num  from hg_party_org_inform_info i  group by i.meeting_type where start_time>=? and start_time < ?";
		return jdbcTemplate.query(sql,rowMapper,startTime,endTime);
	}

	//查询组织活动个数
	public List<Map<String, Object>> mettingNumber(){
		String sql = "SELECT count(*) from hg_party_org_inform_info ";
		return jdbcTemplate.queryForList(sql);
	}
	//日访问量查询
	public Map<String, Object> dateVisit(){
		Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sql="SELECT count(*) from hg_party_visit_count where visit_time >= '"+sdf.format(date)+"'";
		return this.jdbcTemplate.queryForMap(sql);
	}
	//访问量报表
	public List<Map<String, Object>> echartsVisit(){
//		String sql = "SELECT department_name as keyname,count(*) as valname FROM hg_party_visit_count AS vs LEFT JOIN hg_party_org AS org "+
//					"ON vs.department_id=org.org_id "+
//					"GROUP BY department_name ";
		String sql = "SELECT member_party_committee AS keyname,count(*) as valname FROM "+
					"(SELECT * FROM hg_party_visit_count as vis "+
					"LEFT OUTER JOIN hg_users_info as us "+
					"ON vis.user_id=us.user_id "+
					"LEFT OUTER JOIN hg_party_member AS mem "+
					"ON us.user_id=mem.member_identity WHERE mem.member_identity IS NOT NULL and mem.historic is false) as a "+
					"GROUP BY member_party_committee ";	
		return jdbcTemplate.queryForList(sql);
	}
	public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql, String keyName) {
		String sql1=sql+" limit "+pageSize+" offset "+(pageNo-1)*pageSize;
	    Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1,keyName);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql,keyName);
		int total=count.size();
		if(total%pageSize==0){
			map.put("totalPage", total/pageSize);
		}else{
			map.put("totalPage", total/pageSize+1);
		}
		map.put("pageNow", pageNo);
		map.put("list",list);
	   return map;
	}
	public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql) {
		String sql1=sql+" limit "+pageSize+" offset "+(pageNo-1)*pageSize;
	    Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql);
		int total=count.size();
		if(total%pageSize==0){
			map.put("totalPage", total/pageSize);
		}else{
			map.put("totalPage", total/pageSize+1);
		}
		map.put("pageNow", pageNo);
		map.put("list",list);
	   return map;
	}

}
