package hg.party.entity.party;
/**
 * 新闻主题下拉选
 */
public class Hg_Value_Attribute_Info {
	/**主键id 自增长*/
	private int id;
	/**key，唯一*/
	private String resources_key;
	/**value*/
	private String resources_value;
	/**主题类型：如新闻主题，会议主题。。。*/
	private String resources_type;
	/**备注*/
	private String remark;
	
	
	private String user_id;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	private String org_id;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResources_key() {
		return resources_key;
	}
	public void setResources_key(String resources_key) {
		this.resources_key = resources_key;
	}
	public String getResources_value() {
		return resources_value;
	}
	public void setResources_value(String resources_value) {
		this.resources_value = resources_value;
	}
	public String getResources_type() {
		return resources_type;
	}
	public void setResources_type(String resources_type) {
		this.resources_type = resources_type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Hg_Value_Attribute_Info [id=" + id + ", resources_key=" + resources_key + ", resources_value="
				+ resources_value + ", resources_type=" + resources_type + ", remark=" + remark + "]";
	}
	
}
