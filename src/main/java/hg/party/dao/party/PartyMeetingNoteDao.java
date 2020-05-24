package hg.party.dao.party;

import java.util.List;
import java.util.Map;

import hg.util.postgres.HgPostgresqlDaoImpl;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;


import hg.party.entity.party.MeetingNote;
import org.springframework.util.StringUtils;

/**
 * 文件名称： party<br>
 * 创建人 ： Yu Jiang Xia<br>
 * 创建日期： 2018年1月2日下午5:21:55<br>
 */
@Component(immediate = true, service = PartyMeetingNoteDao.class)
public class PartyMeetingNoteDao extends HgPostgresqlDaoImpl<MeetingNote> {
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

	public PostgresqlPageResult<Map<String, Object>> meetingNotePageAndSearch(int page, int size, String orgId, String search) {
		if (size <= 0){
			size = 10;
		}
		StringBuffer sb = new StringBuffer("select plan.*,note.id as noteId ");
		sb.append(" from hg_party_meeting_plan_info plan");
		sb.append(" left join hg_party_meeting_notes_info note on plan.meeting_id = note.meeting_id");
		sb.append(" where 1=1 and plan.organization_id = ? ");
		if(!StringUtils.isEmpty(search)){
			search = "%" + search + "%";
			sb.append(" and (meeting_type like ? or meeting_theme like ?)");
			sb.append(" order by plan.id desc");
			return postGresqlFindPageBySql(page, size, sb.toString(),orgId,search,search);
		}else{
			sb.append(" order by plan.id desc");
			return postGresqlFindPageBySql(page, size, sb.toString(),orgId);
		}
	}
}
