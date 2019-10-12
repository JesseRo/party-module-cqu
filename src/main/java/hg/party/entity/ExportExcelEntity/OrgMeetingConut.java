package hg.party.entity.ExportExcelEntity;

public class OrgMeetingConut {
private	    String second_name;
private		String branch_name;
private		String meeting_type;
private		String release_time;
private		String meeting_theme;
private		String start_time;
private		String place;
private		String host;
private		String contact;
private		String contact_phone;
private		String plan_state;
private		String auditor;
private		String check_person_name;
private		String check_person_org_name;
private		String note;
private     String meeting_theme_secondary;

private     String shoule_persons;
private     String actual_persons;
private     String leave_persons;
private     String attendance;



public String getShoule_persons() {
	return shoule_persons;
}
public void setShoule_persons(String shoule_persons) {
	this.shoule_persons = shoule_persons;
}
public String getActual_persons() {
	return actual_persons;
}
public void setActual_persons(String actual_persons) {
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


public String getMeeting_theme_secondary() {
	return meeting_theme_secondary;
}
public void setMeeting_theme_secondary(String meeting_theme_secondary) {
	this.meeting_theme_secondary = meeting_theme_secondary;
}
public String getSecond_name() {
	return second_name;
}
public void setSecond_name(String second_name) {
	this.second_name = second_name;
}
public String getBranch_name() {
	return branch_name;
}
public void setBranch_name(String branch_name) {
	this.branch_name = branch_name;
}
public String getMeeting_type() {
	return meeting_type;
}
public void setMeeting_type(String meeting_type) {
	this.meeting_type = meeting_type;
}
public String getRelease_time() {
	return release_time;
}
public void setRelease_time(String release_time) {
	this.release_time = release_time;
}
public String getMeeting_theme() {
	return meeting_theme;
}
public void setMeeting_theme(String meeting_theme) {
	this.meeting_theme = meeting_theme;
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
public String getPlan_state() {
	return plan_state;
}
public void setPlan_state(String plan_state) {
	this.plan_state = plan_state;
}
public String getAuditor() {
	return auditor;
}
public void setAuditor(String auditor) {
	this.auditor = auditor;
}
public String getCheck_person_name() {
	return check_person_name;
}
public void setCheck_person_name(String check_person_name) {
	this.check_person_name = check_person_name;
}
public String getCheck_person_org_name() {
	return check_person_org_name;
}
public void setCheck_person_org_name(String check_person_org_name) {
	this.check_person_org_name = check_person_org_name;
}
public String getNote() {
	return note;
}
public void setNote(String note) {
	this.note = note;
}
@Override
public String toString() {
	return "OrgMeetingConut [second_name=" + second_name + ", branch_name=" + branch_name + ", meeting_type="
			+ meeting_type + ", release_time=" + release_time + ", meeting_theme=" + meeting_theme + ", start_time="
			+ start_time + ", place=" + place + ", host=" + host + ", contact=" + contact + ", contact_phone="
			+ contact_phone + ", plan_state=" + plan_state + ", auditor=" + auditor + ", check_person_name="
			+ check_person_name + ", check_person_org_name=" + check_person_org_name + ", note=" + note
			+ ", meeting_theme_secondary=" + meeting_theme_secondary + ", shoule_persons=" + shoule_persons
			+ ", actual_persons=" + actual_persons + ", leave_persons=" + leave_persons + ", attendance=" + attendance
			+ "]";
}


}
