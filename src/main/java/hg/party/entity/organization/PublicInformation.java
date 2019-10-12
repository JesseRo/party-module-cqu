package hg.party.entity.organization;

import java.sql.Timestamp;
import com.dt.annotation.Table;


@Table(name="hg_party_public_inform")
public class PublicInformation {
   private int id;
   //会议类型
   private String meeting_type;
   //组织部开始时间
   private Timestamp start_date;
  // 组织部发布时间
   private Timestamp public_date;
  //启用评论 t:启用 f：未启用
   private String comment;
  //附件  t f
   private String attachment;
   //内容
   private String content;
   //状态 草稿 0 发布 1
   private String state;
   private String resource_id;
   private String public_user_id;
   //会议地址
   private String place;
   
   private String compere_person;
   private String link_man;
   private String link_telephone;
   private String check_state;
   private String check_person_id;
   private String attend_meeting_persons;
   private String now_pictuer_url;
   
   private String remark;
   private String task_state;
   private int public_object;
   private String theme;
   private String check_person_name;
   private String info_status;
   private String branch_content;
   private Timestamp end_date;
   private Timestamp truth_start_date;
   private Timestamp truth_end_date;
   public void setBranch_content(String branch_content) {
	this.branch_content = branch_content;
}
   public String getBranch_content() {
	return branch_content;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getMeeting_type() {
	return meeting_type;
}
public void setMeeting_type(String meeting_type) {
	this.meeting_type = meeting_type;
}
public Timestamp getStart_date() {
	return start_date;
}
public void setStart_date(Timestamp start_date) {
	this.start_date = start_date;
}
public Timestamp getPublic_date() {
	return public_date;
}
public void setPublic_date(Timestamp public_date) {
	this.public_date = public_date;
}
public String getComment() {
	return comment;
}
public void setComment(String comment) {
	this.comment = comment;
}
public String getAttachment() {
	return attachment;
}
public void setAttachment(String attachment) {
	this.attachment = attachment;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getResource_id() {
	return resource_id;
}
public void setResource_id(String resource_id) {
	this.resource_id = resource_id;
}
public String getPublic_user_id() {
	return public_user_id;
}
public void setPublic_user_id(String public_user_id) {
	this.public_user_id = public_user_id;
}
public String getPlace() {
	return place;
}
public void setPlace(String place) {
	this.place = place;
}
public String getCompere_person() {
	return compere_person;
}
public void setCompere_person(String compere_person) {
	this.compere_person = compere_person;
}
public String getLink_man() {
	return link_man;
}
public void setLink_man(String link_man) {
	this.link_man = link_man;
}
public String getLink_telephone() {
	return link_telephone;
}
public void setLink_telephone(String link_telephone) {
	this.link_telephone = link_telephone;
}
public String getCheck_state() {
	return check_state;
}
public void setCheck_state(String check_state) {
	this.check_state = check_state;
}
public String getCheck_person_id() {
	return check_person_id;
}
public void setCheck_person_id(String check_person_id) {
	this.check_person_id = check_person_id;
}
public String getAttend_meeting_persons() {
	return attend_meeting_persons;
}
public void setAttend_meeting_persons(String attend_meeting_persons) {
	this.attend_meeting_persons = attend_meeting_persons;
}
public String getNow_pictuer_url() {
	return now_pictuer_url;
}
public void setNow_pictuer_url(String now_pictuer_url) {
	this.now_pictuer_url = now_pictuer_url;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
public String getTask_state() {
	return task_state;
}
public void setTask_state(String task_state) {
	this.task_state = task_state;
}
public int getPublic_object() {
	return public_object;
}
public void setPublic_object(int public_object) {
	this.public_object = public_object;
}
public String getTheme() {
	return theme;
}
public void setTheme(String theme) {
	this.theme = theme;
}
public String getCheck_person_name() {
	return check_person_name;
}
public void setCheck_person_name(String check_person_name) {
	this.check_person_name = check_person_name;
}
public String getInfo_status() {
	return info_status;
}
public void setInfo_status(String info_status) {
	this.info_status = info_status;
}
public Timestamp getEnd_date() {
	return end_date;
}
public void setEnd_date(Timestamp end_date) {
	this.end_date = end_date;
}
public Timestamp getTruth_start_date() {
	return truth_start_date;
}
public void setTruth_start_date(Timestamp truth_start_date) {
	this.truth_start_date = truth_start_date;
}
public Timestamp getTruth_end_date() {
	return truth_end_date;
}
public void setTruth_end_date(Timestamp truth_end_date) {
	this.truth_end_date = truth_end_date;
}
@Override
public String toString() {
	return "PublicInformation [id=" + id + ", meeting_type=" + meeting_type + ", start_date=" + start_date
			+ ", public_date=" + public_date + ", comment=" + comment + ", attachment=" + attachment + ", content="
			+ content + ", state=" + state + ", resource_id=" + resource_id + ", public_user_id=" + public_user_id
			+ ", place=" + place + ", compere_person=" + compere_person + ", link_man=" + link_man + ", link_telephone="
			+ link_telephone + ", check_state=" + check_state + ", check_person_id=" + check_person_id
			+ ", attend_meeting_persons=" + attend_meeting_persons + ", now_pictuer_url=" + now_pictuer_url
			+ ", remark=" + remark + ", task_state=" + task_state + ", public_object=" + public_object + ", theme="
			+ theme + ", check_person_name=" + check_person_name + ", info_status=" + info_status + ", end_date="
			+ end_date + ", truth_start_date=" + truth_start_date + ", truth_end_date=" + truth_end_date + "]";
}
   

   
}
