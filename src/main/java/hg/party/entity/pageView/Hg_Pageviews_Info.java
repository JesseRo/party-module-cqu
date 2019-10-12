package hg.party.entity.pageView;

/**访问量功能信息表*/

import java.util.Date;


public class Hg_Pageviews_Info {
	/**id 主键*/
	private int id;
	/**资源id*/
	private String resources_id;
	/**访问人id*/
	private String visit_user_id;
	/**访问时间*/
	private Date  visit_time;
	/**访问人IP*/
	private String visit_ip;
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
	public String getVisit_user_id() {
		return visit_user_id;
	}
	public void setVisit_user_id(String visit_user_id) {
		this.visit_user_id = visit_user_id;
	}
	public Date getVisit_time() {
		return visit_time;
	}
	public void setVisit_time(Date visit_time) {
		this.visit_time = visit_time;
	}
	public String getVisit_ip() {
		return visit_ip;
	}
	public void setVisit_ip(String visit_ip) {
		this.visit_ip = visit_ip;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Hg_Pageviews_Info [id=" + id + ", resources_id=" + resources_id + ", visit_userid=" + visit_user_id
				+ ", visit_time=" + visit_time + ", visit_ip=" + visit_ip + ", remark=" + remark + "]";
	}
	
}
