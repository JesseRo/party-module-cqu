package hg.party.entity.ExportExcelEntity;

public class BranchTaskExportExcelEntity {
	private  	String read_status;
	private		String imeetingtype;
	private		String imeetingtheme;
	private		String release_time;
	private		String task_status;
	private		String upload_meeting;
	private		String remark;
	private		String operation;
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperation() {
		return operation;
	}
	public String getRead_status() {
		return read_status;
	}
	public void setRead_status(String read_status) {
		this.read_status = read_status;
	}
	public String getImeetingtype() {
		return imeetingtype;
	}
	public void setImeetingtype(String imeetingtype) {
		this.imeetingtype = imeetingtype;
	}
	public String getImeetingtheme() {
		return imeetingtheme;
	}
	public void setImeetingtheme(String imeetingtheme) {
		this.imeetingtheme = imeetingtheme;
	}
	public String getRelease_time() {
		return release_time;
	}
	public void setRelease_time(String release_time) {
		this.release_time = release_time;
	}
	public String getTask_status() {
		return task_status;
	}
	public void setTask_status(String task_status) {
		this.task_status = task_status;
	}
	public String getUpload_meeting() {
		return upload_meeting;
	}
	public void setUpload_meeting(String upload_meeting) {
		this.upload_meeting = upload_meeting;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
