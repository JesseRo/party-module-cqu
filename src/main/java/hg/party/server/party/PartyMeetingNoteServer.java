package hg.party.server.party;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.party.PartyMeetingNoteDao;
import hg.party.entity.party.MeetingNote;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月5日下午12:44:08<br>
 */

@Component(immediate = true, service = PartyMeetingNoteServer.class)
public class PartyMeetingNoteServer {
	@Reference
	private PartyMeetingNoteDao partyMeetingNoteDao;
	
	//根据会议id查询会议记录
	public List<MeetingNote> meetingNote(String meetingid){
		return partyMeetingNoteDao.meetingNote(meetingid);
	}
	//保存会议录入记录
	public void saveOrUpdate(MeetingNote meetingNote){
		partyMeetingNoteDao.saveOrUpdate(meetingNote);
	}
	//根据通知id查询会议记录
	public List<MeetingNote> meeting_note(String meetingId){
		return partyMeetingNoteDao.meeting_note(meetingId);
	}
}
