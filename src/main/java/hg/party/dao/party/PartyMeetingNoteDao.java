package hg.party.dao.party;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.party.MeetingNote;

/**
 * 文件名称： party<br>
 * 创建人 ： Yu Jiang Xia<br>
 * 创建日期： 2018年1月2日下午5:21:55<br>
 */
@Component(immediate = true, service = PartyMeetingNoteDao.class)
public class PartyMeetingNoteDao extends PostgresqlDaoImpl<MeetingNote> {
	// 根据会议id查询会议记录
	public List<MeetingNote> meetingNote(String meetingid) {
		String sql = "SELECT * FROM hg_party_meeting_notes_info " + "WHERE meeting_id= ? ";
		RowMapper<MeetingNote> rowMapper = BeanPropertyRowMapper.newInstance(MeetingNote.class);
		return this.jdbcTemplate.query(sql, rowMapper, meetingid);
	}

	// 根据通知id查询会议记录
	public List<MeetingNote> meeting_note(String meetingId) {
		String sql = "SELECT * from hg_party_meeting_notes_info " + "WHERE meeting_id= ? ";
		RowMapper<MeetingNote> rowMapper = BeanPropertyRowMapper.newInstance(MeetingNote.class);
		return this.jdbcTemplate.query(sql, rowMapper,meetingId);
	}
}
