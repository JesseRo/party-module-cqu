package hg.party.entity.toDoList;

import java.sql.Timestamp;

import com.dt.annotation.Column;
import com.dt.annotation.Table;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月2日上午11:27:03<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Table(name="hg_party_learning_experience")
public class Experience {

	/**主键id*/
	@Column(sqlName="id",sqlType="int4",javaName="id")
	private int id;
	/**会议id*/
	@Column(sqlName="meeting_id",sqlType="varchar",javaName="meetingId")
	private String meetingId;
	/**用户id*/
	@Column(sqlName="participant_id",sqlType="varchar",javaName="participantId")
	private String participantId;
	/**上传状态默认null:未上传，1:已上传*/
	@Column(sqlName="upload_state",sqlType="int4",javaName="uploadState")
	private int uploadState;
	/**上传时间*/
	@Column(sqlName="upload_time",sqlType="timestamp",javaName="uploadTime")
	private Timestamp uploadTime;
	/**心得体会内容*/
	@Column(sqlName="experience_content",sqlType="text",javaName="content")
	private String content;
	/**备注*/
	@Column(sqlName="remark",sqlType="varchar",javaName="remark")
	private String remark;
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
	public int getUploadState() {
		return uploadState;
	}
	public void setUploadState(int uploadState) {
		this.uploadState = uploadState;
	}
	public Timestamp getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Experience [id=" + id + ", meetingId=" + meetingId + ", participantId=" + participantId
				+ ", uploadState=" + uploadState + ", uploadTime=" + uploadTime + ", content=" + content + ", remark="
				+ remark + "]";
	}
	
}
