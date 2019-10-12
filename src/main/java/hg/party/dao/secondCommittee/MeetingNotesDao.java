package hg.party.dao.secondCommittee;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.party.MeetingNote;


@Component(immediate=true,service=MeetingNotesDao.class)
public class MeetingNotesDao extends PostgresqlDaoImpl<MeetingNote>{
	
	Logger logger = Logger.getLogger(MeetingNotesDao.class);
   
	
	  //查询下拉属性值
	   public  List<Map<String, Object>> queryAttributesByType(String resources_type){
		   String sql = "SELECT * FROM hg_value_attribute_info WHERE resources_type = ?";
//		   logger.info("sql :" + sql);
		   return this.jdbcTemplate.queryForList(sql,resources_type);
	   }
	   
	   
//		 //根据当前用户，会议ID查询会议记录
//	   public  Object querySecondCommitteeByUser(String user,String meetingId){
//		   String sql = "SELECT n.*,o.org_name,o.org_contactor FROM hg_party_meeting_notes_info AS n ,hg_party_org AS o WHERE n.meeting_id = '" + meetingId + "' AND o.org_contactor = '"+ user +"' AND o.org_type = 'secondary'";
//		   return this.jdbcTemplate.queryForObject(sql, Object.class);
//	   }
	   
	   //根据meetingId查询会议记录
	   public MeetingNote queryMeetingNoteByMeetingId(String meetingId){
		   String sql = "SELECT * FROM hg_value_attribute_info WHERE resources_type = ?";
//		   logger.info("sql :" + sql);
		   RowMapper<MeetingNote> rowMapper = BeanPropertyRowMapper.newInstance(MeetingNote.class);
		   return this.jdbcTemplate.queryForObject(sql, rowMapper,meetingId);
	   }
	   
	   
	   
  
   
   
   
   
}
