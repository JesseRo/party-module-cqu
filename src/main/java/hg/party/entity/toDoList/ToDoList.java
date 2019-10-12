package hg.party.entity.toDoList;

import java.util.Date;

import com.dt.annotation.Column;
import com.dt.annotation.Table;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月22日下午4:46:45<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Table(name="hg_party_submission_plan")
public class ToDoList {

	@Column(sqlName="id",sqlType="int4",javaName="id")
	private int id;
	@Column(sqlName="message_id",sqlType="varchar",javaName="messageId")
	private String messageId;
	@Column(sqlName="tab_content",sqlType="jsonb",javaName="tabContent")
	private String tabContent;
	@Column(sqlName="publisher_id",sqlType="varchar",javaName="publisherId")
	private String publisherId;
	@Column(sqlName="publish_time",sqlType="date",javaName="publishTime")
	private Date publishTime;
	@Column(sqlName="state",sqlType="int4",javaName="state")
	private int state;
	@Column(sqlName="remark",sqlType="varchar",javaName="remark")
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getTabContent() {
		return tabContent;
	}
	public void setTabContent(String tabContent) {
		this.tabContent = tabContent;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ToDoList [id=" + id + ", messageId=" + messageId + ", tabContent=" + tabContent + ", publisherId="
				+ publisherId + ", publishTime=" + publishTime + ", state=" + state + ", remark=" + remark + "]";
	}
	
}
