package hg.party.entity.chainingInfo;

public class Hg_Chaining_Management_Info {
	/**主键id*/
	private int id;
	/**链接标题 非空*/
	private String chaining_name;
	/**链接跳转地址*/
	private String chaining_action_url;
	/**链接图片地址*/
	private String chaining_image_url;
	/**管理员id*/
	private String chaining_manager_id;
	/**更新时间*/
	private String chaining_updatetime;
	/**链接类型*/
	private String chaining_type;
	/**备注*/
	private String remark;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChaining_name() {
		return chaining_name;
	}
	public void setChaining_name(String chaining_name) {
		this.chaining_name = chaining_name;
	}
	public String getChaining_action_url() {
		return chaining_action_url;
	}
	public void setChaining_action_url(String chaining_action_url) {
		this.chaining_action_url = chaining_action_url;
	}
	public String getChaining_image_url() {
		return chaining_image_url;
	}
	public void setChaining_image_url(String chaining_image_url) {
		this.chaining_image_url = chaining_image_url;
	}
	public String getChaining_manager_id() {
		return chaining_manager_id;
	}
	public void setChaining_manager_id(String chaining_manager_id) {
		this.chaining_manager_id = chaining_manager_id;
	}
	public String getChaining_updatetime() {
		return chaining_updatetime;
	}
	public void setChaining_updatetime(String chaining_updatetime) {
		this.chaining_updatetime = chaining_updatetime;
	}
	
	public String getChaining_type() {
		return chaining_type;
	}
	public void setChaining_type(String chaining_type) {
		this.chaining_type = chaining_type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Hg_Chaining_Management_Info [id=" + id + ", chaining_name=" + chaining_name + ", chaining_action_url="
				+ chaining_action_url + ", chaining_image_url=" + chaining_image_url + ", chaining_manager_id="
				+ chaining_manager_id + ", chaining_updatetime=" + chaining_updatetime + ", chaining_type="
				+ chaining_type + ", remark=" + remark + "]";
	}
	
}
