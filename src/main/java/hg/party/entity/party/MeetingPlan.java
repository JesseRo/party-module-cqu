package hg.party.entity.party;

import java.sql.Timestamp;

import com.dt.annotation.Column;
import com.dt.annotation.Table;


@Table(name="hg_party_meeting_plan_info")
public class MeetingPlan {
   //主键id
	private int id;
   //通知id
   private String inform_id;
   //会议id
   private String meeting_id;
   //会议类型
   private String meeting_type;
   //会议主题
   private String meeting_theme;
   //内容
   private String content;
   //会议地点
   private int place;
   //组织id
   private String organization_id;
   // 二级党委开始时间
   private Timestamp start_time;
   // 二级党委结束时间
   private Timestamp end_time;
   // 会议进行时长 “分钟”
   private int total_time;
   // 会议主持人
   private String host;
   // 会议参会组织
   private String participant_group;
   // 会议联系人
   private String contact;
   // 会议联系人电话
   private String contact_phone;
   // 会议附件
   private String attachment;
   // 会议计划任务状态
   private String task_status;
   // 会议审核人
   private String auditor;
   // 会议已读回执
   private String read_receipt;
   //上传会议记录
   private String upload_notes;
   // 会议评价得分
   private String evaluation_score;
   // 会议审核人
   private String check_person;
   // 会议审核人
   private String check_status;
   // 会议备注
   private String remark;
   
   // 会议提交时间
   private Timestamp submit_time;
   // 撤回理由
   private String cancel_reason;
   //列席人员
   private String sit;
	//列席人员
	private String sit_id;
   //组织部抽查人
   private String check_person_org;
   //组织部抽查状态
   private String task_status_org;
   //会议二级主题
   private String meeting_theme_secondary;

   private String campus;

   @Column(sqlName="auto_phone_msg",sqlType="int4",javaName="autoPhoneMsg")
   private int autoPhoneMsg;
   
   
   public String getMeeting_theme_secondary() {
	return meeting_theme_secondary;
	}
	public void setMeeting_theme_secondary(String meeting_theme_secondary) {
		this.meeting_theme_secondary = meeting_theme_secondary;
	}
	public String getCheck_person_org() {
		return check_person_org;
	}
	public void setCheck_person_org(String check_person_org) {
		this.check_person_org = check_person_org;
	}
	public String getTask_status_org() {
		return task_status_org;
	}
	public void setTask_status_org(String task_status_org) {
		this.task_status_org = task_status_org;
	}
	public void setSit(String sit) {
		this.sit = sit;
	}
	   public String getSit() {
		return sit;
	}
	   
	public void setCancel_reason(String cancel_reason) {
		this.cancel_reason = cancel_reason;
	}
	   public String getCancel_reason() {
		return cancel_reason;
	}
	public String getInform_id() {
		return inform_id;
	}
	public void setInform_id(String inform_id) {
		this.inform_id = inform_id;
	}
	public String getOrganization_id() {
		return organization_id;
	}
	public void setOrganization_id(String organization_id) {
		this.organization_id = organization_id;
	}
	public Timestamp getSubmit_time() {
		return submit_time;
	}
	public void setSubmit_time(Timestamp submit_time) {
		this.submit_time = submit_time;
	}
	public String getCheck_status() {
		return check_status;
	}
	public void setCheck_status(String check_status) {
		this.check_status = check_status;
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
	public String getMeeting_type() {
		return meeting_type;
	}
	public void setMeeting_type(String meeting_type) {
		this.meeting_type = meeting_type;
	}
	public String getMeeting_theme() {
		return meeting_theme;
	}
	public void setMeeting_theme(String meeting_theme) {
		this.meeting_theme = meeting_theme;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPlace() {
		return place;
	}
	public void setPlace(int place) {
		this.place = place;
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
	public int getTotal_time() {
		return total_time;
	}
	public void setTotal_time(int total_time) {
		this.total_time = total_time;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getParticipant_group() {
		return participant_group;
	}
	public void setParticipant_group(String participant_group) {
		this.participant_group = participant_group;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getTask_status() {
		return task_status;
	}
	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getRead_receipt() {
		return read_receipt;
	}
	public void setRead_receipt(String read_receipt) {
		this.read_receipt = read_receipt;
	}
	public String getUpload_notes() {
		return upload_notes;
	}
	public void setUpload_notes(String upload_notes) {
		this.upload_notes = upload_notes;
	}
	public String getEvaluation_score() {
		return evaluation_score;
	}
	public void setEvaluation_score(String evaluation_score) {
		this.evaluation_score = evaluation_score;
	}
	public String getCheck_person() {
		return check_person;
	}
	public void setCheck_person(String check_person) {
		this.check_person = check_person;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "MeetingPlan [id=" + id + ", inform_id=" + inform_id + ", meeting_id=" + meeting_id + ", meeting_type="
				+ meeting_type + ", meeting_theme=" + meeting_theme + ", content=" + content + ", place=" + place
				+ ", organization_id=" + organization_id + ", start_time=" + start_time + ", end_time=" + end_time
				+ ", total_time=" + total_time + ", host=" + host + ", participant_group=" + participant_group
				+ ", contact=" + contact + ", contact_phone=" + contact_phone + ", attachment=" + attachment
				+ ", task_status=" + task_status + ", auditor=" + auditor + ", read_receipt=" + read_receipt
				+ ", upload_notes=" + upload_notes + ", evaluation_score=" + evaluation_score + ", check_person="
				+ check_person + ", check_status=" + check_status + ", remark=" + remark + ", submit_time=" + submit_time
				+ ", cancel_reason=" + cancel_reason + ", sit=" + sit + ", check_person_org=" + check_person_org
				+ ", task_status_org=" + task_status_org + ", meeting_theme_secondary=" + meeting_theme_secondary + "]";
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public int getAutoPhoneMsg() {
		return autoPhoneMsg;
	}

	public void setAutoPhoneMsg(int autoPhoneMsg) {
		this.autoPhoneMsg = autoPhoneMsg;
	}

	public String getSit_id() {
		return sit_id;
	}

	public void setSit_id(String sit_id) {
		this.sit_id = sit_id;
	}
}
