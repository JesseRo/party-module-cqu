package hg.party.dao.organization;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.PublicInformation;
import hg.party.entity.party.MeetingNote;
import hg.party.entity.party.OrgInform;

@Component(immediate = true, service = PublicInformationDao.class)
public class PublicInformationDao extends PostgresqlDaoImpl<OrgInform> {
	public int saveAttachment(String sql) {
		int n = jdbcTemplate.update(sql);
		return n;
	}

	public int deleteInformByInformId(String infromId) {	
		String sql="DELETE from hg_party_org_inform_info WHERE inform_id= ? ";
		int n = jdbcTemplate.update(sql,infromId);
		return n;
	}
	
	
	public int updateFormId(Timestamp startTime,Timestamp endTime,Timestamp deadline,String informId) {	
		String sql="UPDATE hg_party_org_inform_info set start_time= ? , end_time= ? ,deadline_time= ? "+
                   " WHERE parent= ? ";
		int n = jdbcTemplate.update(sql,startTime,endTime,deadline,informId);
		return n;
	}
	
	
	public List< OrgInform> findByInformId(String infromId) {	
		String sql="select * from hg_party_org_inform_info WHERE inform_id= ? ";
		RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
		return jdbcTemplate.query(sql, rowMapper,infromId);
	}
	
	public int seconedAssign(String assignId,int id) {	
		String sql="update hg_party_meeting_plan_info set check_person= ? ,task_status='5', assign_time = CURRENT_TIMESTAMP where id= ? ";
		int n = jdbcTemplate.update(sql,assignId,id);
		return n;
	}
	
	
	
	public int orgAssign(String assignId,int id) {	
		String sqlOrg = "update hg_party_meeting_plan_info set check_person_org= ? ,task_status_org='5', assign_time = CURRENT_TIMESTAMP where id= ? ";
		int n = jdbcTemplate.update(sqlOrg,assignId,id);
		return n;
	}
	
   public int updateAssignPersonState(String assignId) {	
		String sql="update hg_party_assigne set state='1' where assigne_user_id= ? ";
		int n = jdbcTemplate.update(sql,assignId);
		return n;
	}
	
	public List<Map<String, Object>> findMeetingTypeAndTheme() {
		String sql = "select * from hg_party_base_info where \"type\"='meetingtype' or \"type\"='theme'";
		String sql2 = "select * ,resources_type as type from hg_value_attribute_info where resources_type='news' or resources_type='meetingType' or resources_type='taskStatus'";
		return jdbcTemplate.queryForList(sql2);
	}
}
