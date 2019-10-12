package hg.party.entity.contentManagementInfo;

import java.sql.Timestamp;

/**内容管理基本信息表*/
public class Hg_Content_Management_Info {
	/**id 主键 自增长*/
	private int id;
	/**内容资源id 外键 关联点赞、评论、附件、图片、视频等*/
	private String resources_id;
	/** 内容标题 */
	private String content_title;
	/** 内容类型 */
	private String content_type;
	/**内容概述*/
	private String content_description;
	/** 发布人名字*/
	private String publisher_id;
	/** 发布时间*/
	private Timestamp publish_time;
	/** 审核人id*/
	private String reviewer_id;
	/** 内容正文*/
	private String content_body;
	/**站点id*/
	private Integer site_id;
	/**栏目id*/
	private Integer  column_id;
	/**备注，party项目里面存的主题字段*/
	private String remark;
	/**置顶时间*/
	private Timestamp to_top_time;
	/**当前是否置顶*/
	private int istop;
	/**审批状态：0一审审核申请中，1一审审核已通过、二审审核申请中，2一审审核已驳回，3二审审核已通过，
	 * 4二审审核已驳回，5党支部已撤回，6二级党组织已撤回，7组织部发文,8删除状态*/
	private int approve_state;
	/**一审审核人，即二级党支部审核*/
	private String first_approve_id;
	/**二审审核人，即组织部审核*/
	private String second_approve_id;
	/**一审驳回原因*/
	private String first_dismissal;
	/**二审驳回原因*/
	private String second_dismissal;
	//发布人组织id
	private String content_user_id;
	
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
	public String getContent_title() {
		return content_title;
	}
	public void setContent_title(String content_title) {
		this.content_title = content_title;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	public String getContent_description() {
		return content_description;
	}
	public void setContent_description(String content_description) {
		this.content_description = content_description;
	}
	public String getPublisher_id() {
		return publisher_id;
	}
	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}
	public Timestamp getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(Timestamp publish_time) {
		this.publish_time = publish_time;
	}
	public String getReviewer_id() {
		return reviewer_id;
	}
	public void setReviewer_id(String reviewer_id) {
		this.reviewer_id = reviewer_id;
	}
	public String getContent_body() {
		return content_body;
	}
	public void setContent_body(String content_body) {
		this.content_body = content_body;
	}
	public Integer getSite_id() {
		return site_id;
	}
	public void setSite_id(Integer site_id) {
		this.site_id = site_id;
	}
	public Integer getColumn_id() {
		return column_id;
	}
	public void setColumn_id(Integer column_id) {
		this.column_id = column_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getTo_top_time() {
		return to_top_time;
	}
	public void setTo_top_time(Timestamp to_top_time) {
		this.to_top_time = to_top_time;
	}
	public int getIstop() {
		return istop;
	}
	public void setIstop(int istop) {
		this.istop = istop;
	}
	public int getApprove_state() {
		return approve_state;
	}
	public void setApprove_state(int approve_state) {
		this.approve_state = approve_state;
	}
	public String getFirst_approve_id() {
		return first_approve_id;
	}
	public void setFirst_approve_id(String first_approve_id) {
		this.first_approve_id = first_approve_id;
	}
	public String getSecond_approve_id() {
		return second_approve_id;
	}
	public void setSecond_approve_id(String second_approve_id) {
		this.second_approve_id = second_approve_id;
	}
	public String getFirst_dismissal() {
		return first_dismissal;
	}
	public void setFirst_dismissal(String first_dismissal) {
		this.first_dismissal = first_dismissal;
	}
	public String getSecond_dismissal() {
		return second_dismissal;
	}
	public void setSecond_dismissal(String second_dismissal) {
		this.second_dismissal = second_dismissal;
	}
	public String getContent_user_id() {
		return content_user_id;
	}
	public void setContent_user_id(String content_user_id) {
		this.content_user_id = content_user_id;
	}
	@Override
	public String toString() {
		return "Hg_Content_Management_Info [id=" + id + ", resources_id=" + resources_id + ", content_title="
				+ content_title + ", content_type=" + content_type + ", content_description=" + content_description
				+ ", publisher_id=" + publisher_id + ", publish_time=" + publish_time + ", reviewer_id=" + reviewer_id
				+ ", content_body=" + content_body + ", site_id=" + site_id + ", column_id=" + column_id + ", remark="
				+ remark + ", to_top_time=" + to_top_time + ", istop=" + istop + ", approve_state=" + approve_state
				+ ", first_approve_id=" + first_approve_id + ", second_approve_id=" + second_approve_id
				+ ", first_dismissal=" + first_dismissal + ", second_dismissal=" + second_dismissal
				+ ", content_user_id=" + content_user_id + "]";
	}
	
}
