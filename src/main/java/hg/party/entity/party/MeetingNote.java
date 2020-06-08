package hg.party.entity.party;

import java.sql.Timestamp;

import com.dt.annotation.Table;

@Table(name="hg_party_meeting_notes_info")
public class MeetingNote {
	
	private	int id;
	//会议Id
	private String meeting_id;
	//会议附件
	private String attachment;
	// 会议应到人数
	private int shoule_persons;
	// 会议实到人数
	private int actual_persons;
	// 会议请假员
	private String leave_persons;
	//会议出勤率
	private String attendance;
	//照片
	private String image;
	//备注
	private String remarks;
	//会议状态是否异常
    private String meeting_state;
    //会议开始时间
    private Timestamp start_time;
   //会议结束时间
    private Timestamp end_time;
    //会议缺勤人员
    private String  absence;
    //z组织部抽查图片
    private String image_org;
    //组织部抽查备注
    private String remarks_org;
    //组织部抽查结果
    private String meeting_state_org;
	//会议纪要流程状态
	private int status;
    
    
	public String getImage_org() {
		return image_org;
	}
	public void setImage_org(String image_org) {
		this.image_org = image_org;
	}
	public String getRemarks_org() {
		return remarks_org;
	}
	public void setRemarks_org(String remarks_org) {
		this.remarks_org = remarks_org;
	}
	public String getMeeting_state_org() {
		return meeting_state_org;
	}
	public void setMeeting_state_org(String meeting_state_org) {
		this.meeting_state_org = meeting_state_org;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeeting_id() {
		return meeting_id;
	}
	public void setMeeting_id(String meeting_id) {
		this.meeting_id = meeting_id;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	
	public int getShoule_persons() {
		return shoule_persons;
	}
	public void setShoule_persons(int shoule_persons) {
		this.shoule_persons = shoule_persons;
	}
	public int getActual_persons() {
		return actual_persons;
	}
	public void setActual_persons(int actual_persons) {
		this.actual_persons = actual_persons;
	}
	public String getLeave_persons() {
		return leave_persons;
	}
	public void setLeave_persons(String leave_persons) {
		this.leave_persons = leave_persons;
	}
	public String getAttendance() {
		return attendance;
	}
	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMeeting_state() {
		return meeting_state;
	}
	public void setMeeting_state(String meeting_state) {
		this.meeting_state = meeting_state;
	}
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public String getAbsence() {
		return absence;
	}
	public void setAbsence(String absence) {
		this.absence = absence;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MeetingNote [id=" + id + ", meeting_id=" + meeting_id + ", attachment=" + attachment
				+ ", shoule_persons=" + shoule_persons + ", actual_persons=" + actual_persons + ", leave_persons="
				+ leave_persons + ", attendance=" + attendance + ", image=" + image + ", remarks=" + remarks
				+ ", meeting_state=" + meeting_state + ", start_time=" + start_time + ", end_time=" + end_time
				+ ", absence=" + absence + ", image_org=" + image_org + ", remarks_org=" + remarks_org
				+ ", meeting_state_org=" + meeting_state_org + "]";
	}

    
}
