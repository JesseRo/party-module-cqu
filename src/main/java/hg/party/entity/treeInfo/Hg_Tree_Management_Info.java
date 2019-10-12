package hg.party.entity.treeInfo;
/**
 * 
 * @author caoxm
 * 树形结构表（站点栏目管理信息表）
 */
public class Hg_Tree_Management_Info {
	/**栏目id 主键 自增长*/
	private int id;
	/**栏目名称*/
	private String tree_name;
	/**栏目父节点*/
	private Integer parent_id;
	/**站点id*/
	private int site_id;
	/**备注*/
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTree_name() {
		return tree_name;
	}
	public void setTree_name(String tree_name) {
		this.tree_name = tree_name;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public int getSite_id() {
		return site_id;
	}
	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Hg_Tree_Management_Info [id=" + id + ", tree_name=" + tree_name + ", parent_id=" + parent_id + ", site_id="
				+ site_id + ", remark=" + remark + "]";
	}
	
}
