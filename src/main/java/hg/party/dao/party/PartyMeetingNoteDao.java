package hg.party.dao.party;

import java.util.List;
import java.util.Map;

import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.util.postgres.HgPostgresqlDaoImpl;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;


import hg.party.entity.party.MeetingNote;
import org.springframework.util.StringUtils;
import party.constants.PartyOrgAdminTypeEnum;

/**
 * 文件名称： party<br>
 * 创建人 ： Yu Jiang Xia<br>
 * 创建日期： 2018年1月2日下午5:21:55<br>
 */
@Component(immediate = true, service = PartyMeetingNoteDao.class)
public class PartyMeetingNoteDao extends HgPostgresqlDaoImpl<MeetingNote> {
	@Reference
	private OrgDao orgDao;

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
		StringBuffer sb = new StringBuffer("select plan.*,note.id as note_id,note.status note_status");
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
	public PostgresqlPageResult<Map<String, Object>> meetingNoteAuditPageAndSearch(int page, int size, String orgId, String search) {
		if (size <= 0){
			size = 10;
		}
		Organization org = orgDao.findOrgByOrgId(orgId);
		PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(org.getOrg_type());
		StringBuffer sb = new StringBuffer("select plan.*,note.id as note_id,note.status note_status,org.org_name,m.member_name,m.member_phone_number");
		sb.append(" from  hg_party_meeting_notes_info note ");
		sb.append(" left join hg_party_meeting_plan_info plan on plan.meeting_id = note.meeting_id");
		sb.append(" LEFT JOIN hg_party_org org ON org.org_id = plan.organization_id");
		sb.append(" LEFT JOIN hg_party_member m ON plan.contact = m.member_identity");
		sb.append(" where 1=1 and note.status > 0");
		switch(partyOrgAdminTypeEnum){
			case BRANCH:
				sb.append(" and org.org_id = ?");
				break;
			case SECONDARY:
				sb.append(" and org.org_parent = ? and org.org_type ='"+ PartyOrgAdminTypeEnum.BRANCH.getType()+"'");
				break;
			case ORGANIZATION:;
				sb.append(" and org.org_parent = ? and org.org_type ='"+PartyOrgAdminTypeEnum.SECONDARY.getType()+"'");
				break;
			default: return new PostgresqlPageResult(null, 0,0);
		}
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

	public int passMeetingNote(int noteId) {
		String sql = "UPDATE hg_party_meeting_notes_info SET status=2 WHERE id=?";
		return  this.jdbcTemplate.update(sql,noteId);
	}

	public int rejectMeetingNote(int noteId, String rejectReason) {
		String sql = "UPDATE hg_party_meeting_notes_info SET status=3,reason=? WHERE id=?";
		return  this.jdbcTemplate.update(sql,rejectReason,noteId);
	}
}
