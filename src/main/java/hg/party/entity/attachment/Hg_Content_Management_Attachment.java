package hg.party.entity.attachment;

import java.util.Date;

/**内容管理附件表*/
public class Hg_Content_Management_Attachment {
	/**id 主键 自增长*/
	private int id;
	/**内容资源id 外键*/
	private String resources_id;
	/**附件显示名称*/
	private String attachment_show_name;
	/**附件类型*/
	private String attachment_type;
	/**附件名称*/
	private String attachment_name;
	/**附件地址*/
	private String attachment_url;
	/**附件上传时间*/
	private Date  attachment_updatetime;
	/**备注*/
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResources_id() {
		return resources_id;
	}
	public void setResources_id(String resources_id) {
		this.resources_id = resources_id;
	}
	public String getAttachment_show_name() {
		return attachment_show_name;
	}
	public void setAttachment_show_name(String attachment_show_name) {
		this.attachment_show_name = attachment_show_name;
	}
	public String getAttachment_type() {
		return attachment_type;
	}
	public void setAttachment_type(String attachment_type) {
		this.attachment_type = attachment_type;
	}
	public String getAttachment_name() {
		return attachment_name;
	}
	public void setAttachment_name(String attachment_name) {
		this.attachment_name = attachment_name;
	}
	public String getAttachment_url() {
		return attachment_url;
	}
	public void setAttachment_url(String attachment_url) {
		this.attachment_url = attachment_url;
	}
	public Date getAttachment_updatetime() {
		return attachment_updatetime;
	}
	public void setAttachment_updatetime(Date attachment_updatetime) {
		this.attachment_updatetime = attachment_updatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Hg_Content_Management_Attachment [id=" + id + ", resources_id=" + resources_id
				+ ", attachment_show_name=" + attachment_show_name + ", attachment_type=" + attachment_type
				+ ", attachment_name=" + attachment_name + ", attachment_url=" + attachment_url
				+ ", attachment_updatetime=" + attachment_updatetime + ", remark=" + remark + "]";
	}
	
}
