package hg.party.entity.memberMeeting;

import com.dt.annotation.Column;
import com.dt.annotation.Table;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月4日下午2:01:32<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Table(name="hg_party_inform_member_info")
public class MemberMeeting {

	@Column(sqlName="id",sqlType="int4",javaName="id")
	private int id;
	@Column(sqlName="meeting_id",sqlType="varchar",javaName="meetingId")
	private String meetingId;
	@Column(sqlName="participant_id",sqlType="varchar",javaName="participantId")
	private String participantId;
	@Column(sqlName="check_status",sqlType="varchar",javaName="checkStatus")
	private String checkStatus;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getParticipantId() {
		return participantId;
	}
	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	@Override
	public String toString() {
		return "MemberMeeting [id=" + id + ", meetingId=" + meetingId + ", participantId=" + participantId
				+ ", checkStatus=" + checkStatus + "]";
	}
	
}
