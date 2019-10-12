package hg.party.entity.userMeetingCount;
/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年9月10日下午2:50:16<br>
 */
public class UserMeetingCount {
	private String org_names;
	private String org_name;
	private String user_name;
	private String meeting_type;
	private String meeting_theme;
	private String meeting_theme_secondary;
	private String start_time;
	private String place;
	private String host;
	private String contact;
	private String contact_phone;
	private String task_status;
	private String auditor;
	
	
	public String getOrg_names() {
		return org_names;
	}
	public void setOrg_names(String org_names) {
		this.org_names = org_names;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
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
	public String getMeeting_theme_secondary() {
		return meeting_theme_secondary;
	}
	public void setMeeting_theme_secondary(String meeting_theme_secondary) {
		this.meeting_theme_secondary = meeting_theme_secondary;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
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
	@Override
	public String toString() {
		return "UserMeetingCount [org_names=" + org_names + ", org_name=" + org_name + ", user_name=" + user_name
				+ ", meeting_type=" + meeting_type + ", meeting_theme=" + meeting_theme + ", meeting_theme_secondary="
				+ meeting_theme_secondary + ", start_time=" + start_time + ", place=" + place + ", host=" + host
				+ ", contact=" + contact + ", contact_phone=" + contact_phone + ", task_status=" + task_status
				+ ", auditor=" + auditor + "]";
	}
	
}
