package hg.party.entity.party;

import java.sql.Timestamp;
import com.dt.annotation.Table;


@Table(name="hg_party_org_inform_info")
public class OrgInform {
   private int id;
   //通知id
   private String inform_id;
   //通知状态
   private String inform_status;
   //会议类型
   private String meeting_type;
   //会议主题
   private String meeting_theme;
   //内容
   private String content;
   //组织部开始时间
   private Timestamp start_time;
   //组织部结束时间
   private Timestamp end_time;
   //截止时间
   private Timestamp deadline_time;
   //组织部发布通知时间
   private Timestamp release_time;
   //启用评论，“t” "f"
   private String enable_comment;
   //组织部上传附件
   private String attachment;
   //通知发布级别
   private String release_level;
   //通知发布人
   private String publisher;
   //通知发布状态
   private String public_status;
   //通知备注
   private String remark;
   
   //通知任务状态
   private String task_status;
   //是否转发支部 (t 转发)
   private String send_branch;
   //发布对象的组织类型
   private String org_type;
   
   private String parent;

   
   
   public Timestamp getDeadline_time() {
	return deadline_time;
}
   public void setDeadline_time(Timestamp deadline_time) {
	this.deadline_time = deadline_time;
}
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}
	public String getOrg_type() {
		return org_type;
	}
	
	public String getSend_branch() {
		return send_branch;
	}
	public void setSend_branch(String send_branch) {
		this.send_branch = send_branch;
	}
	public String getTask_status() {
		return task_status;
	}
	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInform_id() {
		return inform_id;
	}
	public void setInform_id(String inform_id) {
		this.inform_id = inform_id;
	}
	public String getInform_status() {
		return inform_status;
	}
	public void setInform_status(String inform_status) {
		this.inform_status = inform_status;
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
	public Timestamp getRelease_time() {
		return release_time;
	}
	public void setRelease_time(Timestamp release_time) {
		this.release_time = release_time;
	}
	public String getEnable_comment() {
		return enable_comment;
	}
	public void setEnable_comment(String enable_comment) {
		this.enable_comment = enable_comment;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getRelease_level() {
		return release_level;
	}
	public void setRelease_level(String release_level) {
		this.release_level = release_level;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublic_status() {
		return public_status;
	}
	public void setPublic_status(String public_status) {
		this.public_status = public_status;
	}
	
	@Override
	public String toString() {
		return "OrgInform [id=" + id + ", inform_id=" + inform_id + ", inform_status=" + inform_status + ", meeting_type="
				+ meeting_type + ", meeting_theme=" + meeting_theme + ", content=" + content + ", start_time=" + start_time
				+ ", end_time=" + end_time + ", release_time=" + release_time + ", enable_comment=" + enable_comment
				+ ", attachment=" + attachment + ", release_level=" + release_level + ", publisher=" + publisher
				+ ", public_status=" + public_status + "]";
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	   
	   
	   

   
}
