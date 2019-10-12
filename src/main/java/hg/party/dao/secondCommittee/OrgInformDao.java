package hg.party.dao.secondCommittee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.party.OrgInform;

@Component(immediate=true,service=OrgInformDao.class)
public class OrgInformDao extends PostgresqlDaoImpl<OrgInform>{
	
	Logger logger = Logger.getLogger(OrgInformDao.class);
   
   //二级党委查询所有通知
   public List<OrgInform> queryInforms(){
	   List<OrgInform> taskList = new ArrayList<OrgInform>();
	   String sql = "SELECT * FROM hg_party_org_inform_info WHERE public_status = '1'  ORDER BY release_time DESC";
//	   logger.info("sql :" + sql);
	   RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
	   taskList = this.jdbcTemplate.query(sql, rowMapper);
	   return taskList;
   }
   
   
 //二级党委查询根据当前通知
   public List<OrgInform> queryInformsByUser(String user){
	   List<OrgInform> taskList = new ArrayList<OrgInform>();
	   String sql = "SELECT * FROM hg_party_org_inform_info WHERE inform_id IN ((SELECT inform_id FROM hg_party_inform_group_info WHERE hg_party_inform_group_info.pub_org_id IN (SELECT hg_party_org.org_id FROM hg_party_org WHERE hg_party_org.org_contactor = ? AND hg_party_org.historic is false and hg_party_org.org_type = 'secondary'))) AND public_status = '1'  ORDER BY release_time DESC";
//	   logger.info("sql :" + sql);
	   RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
	   taskList = this.jdbcTemplate.query(sql, rowMapper,user);
	   return taskList;
   }
   
   //二级党委查询没有提交计划的通知
   public List<OrgInform> queryInformsNoPlan(){
	   List<OrgInform> taskList = new ArrayList<OrgInform>();
	   String sql = "SELECT * FROM hg_party_org_inform_info WHERE public_status = '1' AND task_status !='已提交' ORDER BY release_time DESC";
//	   logger.info("sql :" + sql);
	   RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
	   taskList = this.jdbcTemplate.query(sql, rowMapper);
	   return taskList;
   }
   
   //二级党委根据orgId查询通知
   public List<Map<String, Object>> queryInformsByInformOrgId(String orgId){
	   String sql = "SELECT i.*,o.pub_org_id,o.has_resend,o.read_status FROM hg_party_org_inform_info AS i ,hg_party_inform_group_info AS o WHERE i.inform_id = o.inform_id AND o.inform_id IN ((SELECT i.inform_id FROM hg_party_inform_group_info AS i WHERE i.pub_org_id = ?)) AND o.pub_org_id = ?";
//	   logger.info("sql :" + sql);
	   return this.jdbcTemplate.queryForList(sql,orgId,orgId);
   }
   
   
   
   
   //二级党委根据informId查询通知信息
   public List<Map<String, Object>> queryInformByInformorgId(String informId,String orgId){
	   String sql = "SELECT i.*,g.pub_org_id,o.org_id,o.org_name,g.read_status,g.inform_id FROM hg_party_org_inform_info AS i ,hg_party_inform_group_info AS g ,hg_party_org AS o WHERE i.inform_id = g.inform_id AND g.pub_org_id = o.org_id and o.historic is false AND i.inform_id = ? AND o.org_id = ?";
//	   logger.info("sql :" + sql);
	   return this.jdbcTemplate.queryForList(sql,informId,orgId);
   }
   
   
   //二级党委根据informId查询通知
   public OrgInform queryInformByInformId(String informId){
	   OrgInform inform = new OrgInform();
	   String sql = "SELECT DISTINCT * FROM hg_party_org_inform_info WHERE inform_id = ?";
//	   logger.info("sql :" + sql);
	   RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
	   inform = this.jdbcTemplate.queryForObject(sql, rowMapper,informId);

	   return inform;
   }
   
   //二级党委根据informId查询通知附件
   public List<Map<String, Object>> queryInformAttachmentByInformId(String informId){
	   String sql = "SELECT a.* FROM hg_party_attachment AS a WHERE a.resource_id = ?";
//	   logger.info("sql :" + sql);
	   return this.jdbcTemplate.queryForList(sql,informId);
   }
   
   
   //二级党委根据informId查询通知信息与附件
   public List<Map<String, Object>> queryInformAttachment(String informId){
	   String sql = " SELECT i.*,a.* FROM hg_party_org_inform_info AS i LEFT JOIN hg_party_attachment AS a ON i.inform_id = a.resource_id WHERE i.inform_id = ?";
//	   logger.info("sql :" + sql);
	   return this.jdbcTemplate.queryForList(sql,informId);
   }
   
   
   
   //二级党委根据informId修改状态
   public void updateInformStatus(String informId,String orgId,String nextStatus){
	   String sql ="UPDATE hg_party_inform_group_info  SET read_status=? WHERE inform_id = ? AND pub_org_id =? ";
//	   logger.info("sql :" + sql);
//	   this.jdbcTemplate.execute(sql);
	   jdbcTemplate.update(sql,nextStatus,informId,orgId);
   }
   
   
   //二级党委根据type，status查询通知
   public List<OrgInform> queryInformsByTypeStatus(String type,String status){
	   List<OrgInform> informList = new ArrayList<OrgInform>();
	   String sql = "SELECT * FROM hg_party_org_inform_info WHERE meeting_type = ? AND task_state = ? ORDER BY public_date DESC";
//	   logger.info("sql :" + sql);
	   RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
	   informList = this.jdbcTemplate.query(sql, rowMapper,type,status);
//	   logger.info("taskList :" + informList);
	   return informList;
   }
   
   //二级党委根据type查询通知
   public List<OrgInform> queryInformsByType(String type){
	   List<OrgInform> informList = new ArrayList<OrgInform>();
	   String sql = "SELECT * FROM hg_party_org_inform_info WHERE meeting_type = ? ORDER BY public_date DESC";
//	   logger.info("sql :" + sql);
	   RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
	   informList = this.jdbcTemplate.query(sql, rowMapper,type);
//	   logger.info("taskList :" + informList);
	   return informList;
   }
   
   //二级党委根据status查询通知
   public List<OrgInform> queryInformsByStatus(String status){
	   List<OrgInform> informList = new ArrayList<OrgInform>();
	   String sql = "SELECT * FROM hg_party_org_inform_info WHERE  task_state = ? ORDER BY public_date DESC";
//	   logger.info("sql :" + sql);
	   RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
	   informList = this.jdbcTemplate.query(sql, rowMapper,status);
//	   logger.info("taskList :" + informList);
	   return informList;
   }
   
  
   
   
   
   
}
