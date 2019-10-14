package hg.party.dao.secondCommittee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.party.MeetingPlan;
@Component(immediate=true,service=MeetingPlanDao.class)
public class MeetingPlanDao extends PostgresqlDaoImpl<MeetingPlan>{
	Logger logger = Logger.getLogger(MeetingPlanDao.class);
   private static int IGNORED_STATUS_CODE = 4;
   
   //二级党委根据当前用户查询所有计划
   public List<MeetingPlan> queryMeetingList(){
	   List<MeetingPlan> meetingList = new ArrayList<MeetingPlan>();
	   String sql = "SELECT * FROM hg_party_meeting_plan_info ORDER BY submit_time DESC";
//	   logger.info("sql :" + sql);
	   RowMapper<MeetingPlan> rowMapper = BeanPropertyRowMapper.newInstance(MeetingPlan.class);
	   meetingList = this.jdbcTemplate.query(sql, rowMapper);
	   return meetingList;
   }


	public Map<String, Object> queryMeetingListByPage(int pageNow) {
		String sql = "SELECT * FROM hg_party_meeting_plan_info plan left join hg_party_org org on " +
				"plan.organization_id = org.org_id where org.historic is false ORDER BY submit_time DESC limit "+10+" offset "+(pageNow-1)*10;
		String sql1 = "SELECT * FROM hg_party_meeting_plan_info plan left join hg_party_org org on " +
				"plan.organization_id = org.org_id where org.historic is false ORDER BY submit_time DESC";
		Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql1);
		int total=count.size();
		if(total%10==0){
			map.put("totalPage", total/10);
		}else{
			map.put("totalPage", total/10+1);
		}
		map.put("pageNow", pageNow);
		map.put("list",list);
		return map;
	}

	//   //二级党委根据组织ID查询所有计划
//   public List<MeetingPlan> queryMeetingsByOrgId(String orgId){
//	   List<MeetingPlan> meetingList = new ArrayList<MeetingPlan>();
//	   String sql = "SELECT * FROM hg_party_meeting_plan_info WHERE organization_id = '"+ orgId+"' ORDER BY submit_time DESC";
//	   logger.info("sql :" + sql);
//	   RowMapper<MeetingPlan> rowMapper = BeanPropertyRowMapper.newInstance(MeetingPlan.class);
//	   meetingList = this.jdbcTemplate.query(sql, rowMapper);
//	   return meetingList;
//   }
   
   //二级党委根据组织ID查询所有通知计划
   public Map<String,Object> queryInformMeetingsByOrgId(String orgId, String meetingType, String taskStatus, int page){
	   
	   String sql = "SELECT\n" +
               "\ti.meeting_type AS imeetingtype,\n" +
               "\ti.meeting_theme AS imeetingtheme,\n" +
               "\ti.inform_id,\n" +
               "\ti.remark AS iremark,\n" +
               "\ti.deadline_time,\n" +
               "\tG .read_status,\n" +
               "\tG .pub_org_id,\n" +
               "\ti .org_type,\n" +
               "\ti.release_time,\n" +
//               "\tM .meeting_type AS mmeetingtype,\n" +
//               "\tM .meeting_theme AS mmeetingtheme,\n" +
//               "\tM .meeting_id,\n" +
//               "\tM .submit_time,\n" +
//               "\tcast(M .task_status as INTEGER),\n" +
               "\ti.send_branch,\n" +
               "\tG .has_resend,\n" +
               "\tG .send_to,\n" +
//               "\tM .organization_id,\n" +
//               "\tM .place,\n" +
//               "\tM .remark AS mremark,\n" +
//               "\tM .remark AS mremark,\n" +
               "\ti.start_time AS istarttime,\n" +
               "\ti.end_time AS iendtime\n" +
//               "\tM .start_time AS mstarttime,\n" +
//               "\tM .end_time AS mendtime,\n" +
//               "\tM .check_status\n" +
               "FROM\n" +
               "\t(\n" +
               "\t\thg_party_org_inform_info AS i\n" +
               "\t\tLEFT JOIN PUBLIC .hg_party_inform_group_info AS G ON G .inform_id = i.inform_id\n" +
               "\t\tAND G .pub_org_id = '" + orgId + "'\n" + 
               "\t\tAND has_resend IS NOT TRUE\n" +
               "\t)\n" +
//               "LEFT JOIN PUBLIC .hg_party_meeting_plan_info AS M ON i.inform_id = M .inform_id\n" +
//               "AND G .pub_org_id = M .organization_id\n" +
               " WHERE\n" +
               "\t(\n" +
               "\t\tG .pub_org_id = '" + orgId + "' AND has_resend IS NOT TRUE \n" +
               "\t\tOR i.org_type = '" + orgId + "'\n" +
               "\t)\n" +
               "AND (i.public_status = '1' or i.public_status = '2')\n";
	   if (!StringUtils.isEmpty(meetingType) ) {
		   sql += "AND i.meeting_type = '" + meetingType + "'\n";
	   }
	   if (!StringUtils.isEmpty(taskStatus)) {
		   try {
			   int statusCode = Integer.valueOf(taskStatus);
			   if(statusCode > 0){
				   if (statusCode < IGNORED_STATUS_CODE) {
					   sql += "AND cast(M .task_status as INTEGER) = " + statusCode + "\n";
				   }else{
					   sql += "AND cast(M .task_status as INTEGER) >= " + IGNORED_STATUS_CODE + "\n";
				   }
			   }
			} catch (Exception e) {
				e.printStackTrace();
			}
	   }
	    sql +="order by (case when G.read_status='未读' then '0' "+
				             "when G.read_status is NULL then '1' "+ 
				             "when G.read_status='已查看' then '1'  "+
				             "else G.read_status end) ASC ,i.release_time DESC";
	    logger.info("sql :" + sql);
	    String sql1=sql+" limit 8 offset "+(page-1)*8;
	    Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql);
		int total=count.size();
		if(total%8==0){
			map.put("totalPage", total/8);
		}else{
			map.put("totalPage", total/8+1);
		}
		map.put("pageNow", page);
		map.put("list",list);
	   return map;
   }

	public Map<String,Object> queryInformMeetingsByInformId(String orgId, String meetingType, String taskStatus,String inform_id, int page){

		String sql = "SELECT\n" +
				"\ti.meeting_type AS imeetingtype,\n" +
				"\ti.meeting_theme AS imeetingtheme,\n" +
				"\ti.inform_id,\n" +
				"\ti.remark AS iremark,\n" +
				"\ti.deadline_time,\n" +
				"\tG .read_status,\n" +
				"\tG .pub_org_id,\n" +
				"\ti .org_type,\n" +
				"\ti.release_time,\n" +
				"\tM .meeting_type AS mmeetingtype,\n" +
				"\tM .meeting_theme AS mmeetingtheme,\n" +
				"\tM .meeting_theme_secondary AS meetingthemesecondary,\n" +
				"\tM .meeting_id,\n" +
				"\tM .submit_time,\n" +
				"\tcast(M .task_status as INTEGER),\n" +
				"\ti.send_branch,\n" +
				"\tG .has_resend,\n" +
				"\tG .send_to,\n" +
				"\tM .organization_id,\n" +
				"\tM .place,\n" +
				"\tM .remark AS mremark,\n" +
				"\tM .remark AS mremark,\n" +
				"\ti.start_time AS istarttime,\n" +
				"\ti.end_time AS iendtime,\n" +
				"\tM .start_time AS mstarttime,\n" +
				"\tM .end_time AS mendtime,\n" +
				"\tM .check_status\n" +
				"FROM\n" +
				"\t(\n" +
				"\t\thg_party_org_inform_info AS i\n" +
				"\t\tLEFT JOIN PUBLIC .hg_party_inform_group_info AS G ON G .inform_id = i.inform_id\n" +
				"\t\tAND G .pub_org_id = '" + orgId + "'\n" +
				"\t\tAND has_resend IS NOT TRUE\n" +
				"\t)\n" +
				"INNER JOIN PUBLIC .hg_party_meeting_plan_info AS M ON i.inform_id = M .inform_id\n" +
				"AND G .pub_org_id = M .organization_id\n" +
				"WHERE\n" +
				"\t(\n" +
				"\t\tG .pub_org_id = '" + orgId + "' AND has_resend IS NOT TRUE \n" +
				"\t\tOR i.org_type = '" + orgId + "'\n" +
				"\t)\n" +
				"AND (i.public_status = '1' or i.public_status = '2')\n " +
				"AND i.inform_id= '" + inform_id + "'";
		if (!StringUtils.isEmpty(meetingType) ) {
			sql += "AND i.meeting_type = '" + meetingType + "'\n";
		}
		if (!StringUtils.isEmpty(taskStatus)) {
			try {
				int statusCode = Integer.valueOf(taskStatus);
				if(statusCode > 0){
					if (statusCode < IGNORED_STATUS_CODE) {
						sql += "AND cast(M .task_status as INTEGER) = " + statusCode + "\n";
					}else{
						sql += "AND cast(M .task_status as INTEGER) >= " + IGNORED_STATUS_CODE + "\n";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sql +="order by (case when G.read_status='未读' then '0' "+
				"when G.read_status is NULL then '1' "+
				"when G.read_status='已查看' then '1'  "+
				"else G.read_status end) ASC ,i.release_time DESC";
		logger.info("sql :" + sql);
		String sql1=sql+" limit 8 offset "+(page-1)*8;
		Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql);
		int total=count.size();
		if(total%8==0){
			map.put("totalPage", total/8);
		}else{
			map.put("totalPage", total/8+1);
		}
		map.put("pageNow", page);
		map.put("list",list);
		return map;
	}


	public List<Map<String, Object>> exportExcel(String orgId, String meetingType, String taskStatus){
	  String sql = "SELECT\n" +
              "\ti.meeting_type AS imeetingtype,\n" +
              "\ti.meeting_theme AS imeetingtheme,\n" +
              "\ti.inform_id,\n" +
              "\ti.remark AS iremark,\n" +
              "\ti.deadline_time,\n" +
              "\tG .read_status,\n" +
              "\tG .pub_org_id,\n" +
              "\ti .org_type,\n" +
              "\ti.release_time,\n" +
              "\tM .meeting_type AS mmeetingtype,\n" +
              "\tM .meeting_theme AS mmeetingtheme,\n" +
              "\tM .meeting_id,\n" +
              "\tM .submit_time,\n" +
              "\tcast(M .task_status as INTEGER),\n" +
              "\ti.send_branch,\n" +
              "\tG .has_resend,\n" +
              "\tG .send_to,\n" +
              "\tM .organization_id,\n" +
              "\tM .place,\n" +
              "\tM .remark AS mremark,\n" +
              "\tM .remark AS mremark,\n" +
              "\ti.start_time AS istarttime,\n" +
              "\ti.end_time AS iendtime,\n" +
              "\tM .start_time AS mstarttime,\n" +
              "\tM .end_time AS mendtime,\n" +
              "\tM .check_status\n" +
              "FROM\n" +
              "\t(\n" +
              "\t\thg_party_org_inform_info AS i\n" +
              "\t\tLEFT JOIN PUBLIC .hg_party_inform_group_info AS G ON G .inform_id = i.inform_id\n" +
              "\t\tAND G .pub_org_id = '" + orgId + "'\n" + 
              "\t\tAND has_resend IS NOT TRUE\n" +
              "\t)\n" +
              "LEFT JOIN PUBLIC .hg_party_meeting_plan_info AS M ON i.inform_id = M .inform_id\n" +
              "AND G .pub_org_id = M .organization_id\n" +
              "WHERE\n" +
              "\t(\n" +
              "\t\tG .pub_org_id = '" + orgId + "' AND has_resend IS NOT TRUE \n" +
              "\t\tOR i.org_type = '" + orgId + "'\n" +
              "\t)\n" +
              "AND (i.public_status = '1' or i.public_status = '2')\n";
	   if (!StringUtils.isEmpty(meetingType) ) {
		   sql += "AND i.meeting_type = '" + meetingType + "'\n";
	   }
	   if (!StringUtils.isEmpty(taskStatus)) {
		   try {
			   int statusCode = Integer.valueOf(taskStatus);
			   if(statusCode > 0){
				   if (statusCode < IGNORED_STATUS_CODE) {
					   sql += "AND cast(M .task_status as INTEGER) = " + statusCode + "\n";
				   }else{
					   sql += "AND cast(M .task_status as INTEGER) >= " + IGNORED_STATUS_CODE + "\n";
				   }
			   }
			} catch (Exception e) {
				e.printStackTrace();
			}
	   }
     // sql += "ORDER BY G.read_status , i.release_time DESC";
	    sql +="order by (case when G.read_status='未读' then '0' "+
				             "when G.read_status is NULL then '1' "+ 
				             "when G.read_status='已查看' then '1'  "+
				             "else G.read_status end) ASC ,i.release_time DESC";
//	   logger.info("sql :" + sql);
	   return jdbcTemplate.queryForList(sql);
  }
   
   
   
   //二级党委根据meetingId查询会议
   public MeetingPlan queryMeetingByMeetingId(String meetingId){
	   String sql = "SELECT  * FROM hg_party_meeting_plan_info WHERE meeting_id = '" + meetingId+ "'";
//	   logger.info("sql :" + sql);
	   RowMapper<MeetingPlan> rowMapper = BeanPropertyRowMapper.newInstance(MeetingPlan.class);
	   return this.jdbcTemplate.queryForObject(sql, rowMapper);
   }
   
   
 //二级党委根据meetingId附件
   public List<Map<String, Object>> queryMeetingAttachment(String meetingId){
	   String sql = "SELECT a.* FROM hg_party_attachment AS a WHERE a.resource_id = '"+ meetingId +"'";
//	   logger.info("sql :" + sql);
	   return this.jdbcTemplate.queryForList(sql);
   }
   
   
   //二级党委根据meetingId查询会议
   public List<Map<String, Object>> queryMeetingPlanByMeetingId(String meetingId){
//	   String sql = "SELECT m.*,s.org_id AS sid,s.org_name AS sname,g.org_id AS gid,g.org_name AS gname FROM hg_party_org AS s ,hg_party_meeting_plan_info AS m ,hg_party_org AS g WHERE m.meeting_id = '"+ meetingId +"' AND s.org_id = m.organization_id AND m.participant_group = g.org_id";
	 //  String sql = "SELECT m.*,s.org_id AS sid,s.org_name AS sname,g.group_name ,g.group_id FROM public.hg_party_org AS s ,public.hg_party_meeting_plan_info AS m ,public.hg_party_group_org_info AS g WHERE m.meeting_id = '"+ meetingId +"' AND s.org_id = m.organization_id AND m.participant_group = g.group_id";
	  String sql ="select  m.*,s.org_id AS sid,s.org_name AS sname,g.group_name ,g.group_id from hg_party_meeting_plan_info as m "+
				  "LEFT JOIN  hg_party_group_org_info as g on m.participant_group = g.group_id  "+
				  "LEFT JOIN hg_party_org as s on s.org_id = m.organization_id "+
				  "where m.meeting_id = '"+meetingId+"' and s.historic is false";
//	   logger.info("sql :" + sql);
	   return this.jdbcTemplate.queryForList(sql);
   }
   
   
   //党支部根据meetingId查询会议
   public List<Map<String, Object>> queryMeetingPlanByMeetingIdBranch(String meetingId){
	   String sql = "SELECT m.*,b.org_id AS sid,b.org_name AS bname,s.org_name AS sname,s.org_id,s.org_type FROM public.hg_party_org AS b ,public.hg_party_meeting_plan_info AS m ,public.hg_party_org AS s ,public.hg_party_org AS p WHERE m.meeting_id = '"+ meetingId +"' AND m.organization_id = b.org_id and b.historic is false AND m.organization_id = p.org_id AND p.org_parent = s.org_id";
//	   logger.info("sql :" + sql);
	   return this.jdbcTemplate.queryForList(sql);
   }
   
 //党支部根据meetingId查询应到人数
   public List<Map<String, Object>> queryShouldCount(String meetingId){
	   String sql = " SELECT COUNT(*) FROM hg_party_meeting_member_info AS m WHERE m.meeting_id = '"+ meetingId +"'";
//	   logger.info("sql :" + sql);
	   return this.jdbcTemplate.queryForList(sql);
   }
  
   
   
   //通知人员
   public void informParty(String meetingId,String participant_id){
	   String sql = "INSERT INTO hg_party_meeting_member_info (meeting_id,participant_id,check_status) VALUES('"+ meetingId +"','"+ participant_id +"','未读') ";
	   this.jdbcTemplate.execute(sql);
   }
   
   
   //查询人员
   public  List<Map<String, Object>> queryPartys(String orgId){
	   String sql ="SELECT participant_id FROM hg_party_group_member_info WHERE hg_party_group_member_info.group_id = '"+orgId+"'";
	   return this.jdbcTemplate.queryForList(sql);
   }
   
   
   //更新会议任务状态
   public  void updateMeetingPlan(String meetingId,String nextStatus){
	   String sql ="UPDATE hg_party_meeting_plan_info  SET task_status='"+ nextStatus +"', task_status_org = '6' WHERE meeting_id = '"+ meetingId +"'";
//	   logger.info(sql);
	   this.jdbcTemplate.execute(sql);
   }
   
   //更新会议检查状态
   public  void updateMeetingPlanCheckStarus(String meetingId,String nextStatus){
	   String sql ="UPDATE hg_party_meeting_plan_info  SET check_status='"+ nextStatus +"' WHERE meeting_id = '"+ meetingId +"'";
//	   logger.info(sql);
	   this.jdbcTemplate.execute(sql);
   }
   
   
   
   //二级党委撤回会议
   public  void revokeMeetingPlan(String meetingId,String revokeReason){
	   String sql ="UPDATE hg_party_meeting_plan_info  SET cancel_reason = '"+ revokeReason +"',task_status = '已撤回' WHERE meeting_id = '"+ meetingId +"'";
	   this.jdbcTemplate.execute(sql);
   }
   
   
}
