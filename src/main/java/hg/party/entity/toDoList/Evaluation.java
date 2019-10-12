package hg.party.entity.toDoList;

import java.sql.Timestamp;

import com.dt.annotation.Column;
import com.dt.annotation.Table;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月2日上午11:42:03<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Table(name="hg_party_comments_info")
public class Evaluation {

	/**主键id*/
	@Column(sqlName="id",sqlType="int4",javaName="id")
	private int id;
	/**会议id*/
	@Column(sqlName="meeting_id",sqlType="varchar",javaName="meetingId")
	private String meetingId;
	/**用户id*/
	@Column(sqlName="participant_id",sqlType="varchar",javaName="participantId")
	private String participantId;
	/**评论状态默认为null:未评论，1:已评论*/
	@Column(sqlName="comments_state",sqlType="int4",javaName="commentsState")
	private int commentsState;
	/**评论时间*/
	@Column(sqlName="comments_time",sqlType="timestamp",javaName="commentsTime")
	private Timestamp commentsTime;
	/**评价打分*/
	@Column(sqlName="comments_score",sqlType="int4",javaName="commentsScore")
	private int commentsScore;
	/**评论方面一*/
	@Column(sqlName="comments_aspects_one",sqlType="int2",javaName="commentsAspectsOne")
	private int commentsAspectsOne;
	/**评论方面二*/
	@Column(sqlName="comments_aspects_two",sqlType="int2",javaName="commentsAspectsTwo")
	private int commentsAspectsTwo;
	/**评论方面三*/
	@Column(sqlName="comments_aspects_three",sqlType="int2",javaName="commentsAspectsThree")
	private int commentsAspectsThree;
	/**评论方面四*/
	@Column(sqlName="comments_aspects_four",sqlType="int2",javaName="commentsAspectsFour")
	private int commentsAspectsFour;
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
	public int getCommentsState() {
		return commentsState;
	}
	public void setCommentsState(int commentsState) {
		this.commentsState = commentsState;
	}
	public Timestamp getCommentsTime() {
		return commentsTime;
	}
	public void setCommentsTime(Timestamp commentsTime) {
		this.commentsTime = commentsTime;
	}
	public int getCommentsScore() {
		return commentsScore;
	}
	public void setCommentsScore(int commentsScore) {
		this.commentsScore = commentsScore;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getCommentsAspectsOne() {
		return commentsAspectsOne;
	}
	public void setCommentsAspectsOne(int commentsAspectsOne) {
		this.commentsAspectsOne = commentsAspectsOne;
	}
	public int getCommentsAspectsTwo() {
		return commentsAspectsTwo;
	}
	public void setCommentsAspectsTwo(int commentsAspectsTwo) {
		this.commentsAspectsTwo = commentsAspectsTwo;
	}
	public int getCommentsAspectsThree() {
		return commentsAspectsThree;
	}
	public void setCommentsAspectsThree(int commentsAspectsThree) {
		this.commentsAspectsThree = commentsAspectsThree;
	}
	public int getCommentsAspectsFour() {
		return commentsAspectsFour;
	}
	public void setCommentsAspectsFour(int commentsAspectsFour) {
		this.commentsAspectsFour = commentsAspectsFour;
	}
	@Override
	public String toString() {
		return "Evaluation [id=" + id + ", meetingId=" + meetingId + ", participantId=" + participantId
				+ ", commentsState=" + commentsState + ", commentsTime=" + commentsTime + ", commentsScore="
				+ commentsScore + ", commentsAspectsOne=" + commentsAspectsOne + ", commentsAspectsTwo="
				+ commentsAspectsTwo + ", commentsAspectsThree=" + commentsAspectsThree + ", commentsAspectsFour="
				+ commentsAspectsFour + ", remark=" + remark + "]";
	}
	
	
	
}
