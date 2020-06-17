package hg.party.dao.secondCommittee;

import java.util.List;
import java.util.Map;

import hg.party.entity.partyMembers.Member;
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


	public MeetingNote findByMeetingId(String meetingId) {
	   	String sql = "select * from hg_party_meeting_notes_info where meeting_id = ?";
	   	try{
	   		List<MeetingNote> meetingNotes = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MeetingNote.class), meetingId);
			if(meetingNotes.size()>0){
				return meetingNotes.get(0);
			}else{
				return null;
			}
		}catch (Exception e){
	   		e.printStackTrace();
	   		return null;
		}
	}

	public void deleteMember(String meetingId) {
		String sql = "DELETE from hg_party_meeting_note_member where meeting_id= ? ";
		jdbcTemplate.update(sql, meetingId);
	}

	public int addMember(String meetingId, String identity) {
		String sql = "INSERT INTO hg_party_meeting_note_member(meeting_id, member_identity) VALUES (?,?)";
		return jdbcTemplate.update(sql,meetingId,identity);
	}

	public List<Member> findAttendMember(String meetingId) {
		String sql = "select m.* from (select * from hg_party_meeting_note_member where meeting_id=?) n left join hg_party_member m on n.member_identity = m.member_identity";
		RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class);
		return  this.jdbcTemplate.query(sql, rowMapper,meetingId);
	}
}
