package hg.party.entity.navigation;

import com.dt.annotation.Column;
import com.dt.annotation.Table;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月9日上午9:51:33<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Table(name="hg_party_navigation_permissions")
public class NavigationPermissions {

	/**主键id*/
	@Column(sqlName="id",sqlType="int4",javaName="id")
	private int id;
	/**导航id*/
	@Column(sqlName="navigation_id",sqlType="varchar",javaName="navigationId")
	private String navigationId;
	/**父节点id*/
	@Column(sqlName="parent_id",sqlType="varchar",javaName="parentId")
	private String parentId;
	/**导航名字*/
	@Column(sqlName="navigation_name",sqlType="varchar",javaName="navigationName")
	private String navigationName;
	/**导航链接地址*/
	@Column(sqlName="navigation_url",sqlType="varchar",javaName="navigationUrl")
	private String navigationUrl;
	/**所属部门*/
	@Column(sqlName="department",sqlType="varchar",javaName="department")
	private String department;
	/**导航所属角色*/
	@Column(sqlName="navigation_to_role",sqlType="varchar",javaName="navigationToRole")
	private String navigationToRole;
	/**显示位置*/
	@Column(sqlName="show_location",sqlType="varchar",javaName="showLocation")
	private String showLocation;
	/**导航排序*/
	@Column(sqlName="navigation_sort",sqlType="int4",javaName="navigationSort")
	private int navigationSort;
	/**备注*/
	@Column(sqlName="remark",sqlType="varchar",javaName="remark")
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNavigationId() {
		return navigationId;
	}
	public void setNavigationId(String navigationId) {
		this.navigationId = navigationId;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getNavigationName() {
		return navigationName;
	}
	public void setNavigationName(String navigationName) {
		this.navigationName = navigationName;
	}
	public String getNavigationUrl() {
		return navigationUrl;
	}
	public void setNavigationUrl(String navigationUrl) {
		this.navigationUrl = navigationUrl;
	}
	public String getNavigationToRole() {
		return navigationToRole;
	}
	public void setNavigationToRole(String navigationToRole) {
		this.navigationToRole = navigationToRole;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getShowLocation() {
		return showLocation;
	}
	public void setShowLocation(String showLocation) {
		this.showLocation = showLocation;
	}
	public int getNavigationSort() {
		return navigationSort;
	}
	public void setNavigationSort(int navigationSort) {
		this.navigationSort = navigationSort;
	}
	@Override
	public String toString() {
		return "NavigationPermissions [id=" + id + ", navigationId=" + navigationId + ", parentId=" + parentId
				+ ", navigationName=" + navigationName + ", navigationUrl=" + navigationUrl + ", department="
				+ department + ", navigationToRole=" + navigationToRole + ", showLocation=" + showLocation
				+ ", navigationSort=" + navigationSort + ", remark=" + remark + "]";
	}
	
}
