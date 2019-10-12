package hg.party.entity.organization;

import java.sql.Timestamp;

import com.dt.annotation.Column;
import com.dt.annotation.Table;
@Table(name="hg_party_attachment")
public class Attachment {
      private int id;
      @Column(javaName = "resourceId", sqlName = "String", sqlType = "resource_id")
      private String resourceId;
      @Column(javaName = "attachmentName", sqlName = "String", sqlType = "attachment-name")
      private String attachmentName;
      @Column(javaName = "attachmentType", sqlName = "String", sqlType = "attachment-type")

      private String attachmentType;
      @Column(javaName = "attachmentUrl", sqlName = "String", sqlType = "attachment-url")
      private String attachmentUrl;
      @Column(javaName = "attachmentDtae", sqlName = "Timestamp", sqlType = "attachment-date")
      private Timestamp attachmentDtae;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
	public Timestamp getAttachmentDtae() {
		return attachmentDtae;
	}
	public void setAttachmentDtae(Timestamp attachmentDtae) {
		this.attachmentDtae = attachmentDtae;
	}
	@Override
	public String toString() {
		return "Attachment [id=" + id + ", resourceId=" + resourceId + ", attachmentName=" + attachmentName
				+ ", attachmentType=" + attachmentType + ", attachmentUrl=" + attachmentUrl + ", attachmentDtae="
				+ attachmentDtae + "]";
	}
      
      
}
