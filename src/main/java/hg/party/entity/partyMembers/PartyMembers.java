package hg.party.entity.partyMembers;
/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月14日下午4:31:23<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */

import java.util.Date;

import com.dt.annotation.Column;
import com.dt.annotation.Table;

@Table(name="hg_party_members_info")
public class PartyMembers {
	/**主键id*/
	@Column(sqlName="id",sqlType="int4",javaName="id")
	private int id;
	/**党员姓名*/
	@Column(sqlName="name",sqlType="varchar",javaName="name")
	private String name;
	/**性别*/
	@Column(sqlName="sex",sqlType="varchar",javaName="sex")
	private String sex;
	/**民族*/
	@Column(sqlName="ethnic",sqlType="varchar",javaName="ethnic")
	private String ethnic;
	/**年龄*/
	@Column(sqlName="age",sqlType="int4",javaName="age")
	private int age;
	/**出生日期*/
	@Column(sqlName="date_birth",sqlType="date",javaName="dateBirth")
	private Date dateBirth;
	/**身份证号码*/
	@Column(sqlName="id_card",sqlType="varchar",javaName="IDCard")
	private String IDCard;
	/**学历*/
	@Column(sqlName="degree",sqlType="varchar",javaName="degree")
	private String degree;
	/**工作岗位*/
	@Column(sqlName="job_position",sqlType="varchar",javaName="jobPosition")
	private String jobPosition;
	/**入党时间*/
	@Column(sqlName="join_party_time",sqlType="date",javaName="joinPartyTime")
	private Date joinPartyTime;
	/**转正日期*/
	@Column(sqlName="positive_date",sqlType="date",javaName="positiveDate")
	private Date positiveDate;
	/**所在支部*/
	@Column(sqlName="branch_party",sqlType="varchar",javaName="branchParty")
	private String branchParty;
	/**党员性质*/
	@Column(sqlName="party_members_nature",sqlType="varchar",javaName="partyMembersNature")
	private String partyMembersNature;
	/**家庭住址*/
	@Column(sqlName="home_address",sqlType="varchar",javaName="homeAddress")
	private String homeAddress;
	/**联系人电话*/
	@Column(sqlName="mobile_phone",sqlType="varchar",javaName="mobilePhone")
	private String mobilePhone;
	/**固定电话*/
	@Column(sqlName="fixed_phone",sqlType="varchar",javaName="fixedPhone")
	private String fixedPhone;
	/**是否为失联党员*/
	@Column(sqlName="lost_union_state",sqlType="varchar",javaName="lostUnionState")
	private String lostUnionState;
	/**失联日期*/
	@Column(sqlName="lost_union_date",sqlType="date",javaName="lostUnionDate")
	private Date lostUnionDate;
	/**是否为流动党员*/
	@Column(sqlName="mobile_party_members",sqlType="varchar",javaName="mobilePartyMembers")
	private String mobilePartyMembers;
	/**流向*/
	@Column(sqlName="streamwise",sqlType="varchar",javaName="streamWise")
	private String streamWise;
	/**党籍状态*/
	@Column(sqlName="party_affiliation_status",sqlType="varchar",javaName="partyAffiliationStatus")
	private String partyAffiliationStatus;
	/**备注*/
	@Column(sqlName="remark",sqlType="varchar",javaName="remark")
	private String remark;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEthnic() {
		return ethnic;
	}
	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public Date getDateBirth() {
		return dateBirth;
	}
	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}
	public String getIDCard() {
		return IDCard;
	}
	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getJobPosition() {
		return jobPosition;
	}
	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
	
	public Date getJoinPartyTime() {
		return joinPartyTime;
	}
	public void setJoinPartyTime(Date joinPartyTime) {
		this.joinPartyTime = joinPartyTime;
	}
	public Date getPositiveDate() {
		return positiveDate;
	}
	public void setPositiveDate(Date positiveDate) {
		this.positiveDate = positiveDate;
	}
	public String getBranchParty() {
		return branchParty;
	}
	public void setBranchParty(String branchParty) {
		this.branchParty = branchParty;
	}
	public String getPartyMembersNature() {
		return partyMembersNature;
	}
	public void setPartyMembersNature(String partyMembersNature) {
		this.partyMembersNature = partyMembersNature;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getFixedPhone() {
		return fixedPhone;
	}
	public void setFixedPhone(String fixedPhone) {
		this.fixedPhone = fixedPhone;
	}
	public String getLostUnionState() {
		return lostUnionState;
	}
	public void setLostUnionState(String lostUnionState) {
		this.lostUnionState = lostUnionState;
	}
	
	public Date getLostUnionDate() {
		return lostUnionDate;
	}
	public void setLostUnionDate(Date lostUnionDate) {
		this.lostUnionDate = lostUnionDate;
	}
	public String getMobilePartyMembers() {
		return mobilePartyMembers;
	}
	public void setMobilePartyMembers(String mobilePartyMembers) {
		this.mobilePartyMembers = mobilePartyMembers;
	}
	public String getStreamWise() {
		return streamWise;
	}
	public void setStreamWise(String streamWise) {
		this.streamWise = streamWise;
	}
	public String getPartyAffiliationStatus() {
		return partyAffiliationStatus;
	}
	public void setPartyAffiliationStatus(String partyAffiliationStatus) {
		this.partyAffiliationStatus = partyAffiliationStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "PartyMembers [id=" + id + ", name=" + name + ", sex=" + sex + ", ethnic=" + ethnic + ", age=" + age
				+ ", dateBirth=" + dateBirth + ", IDCard=" + IDCard + ", degree=" + degree + ", jobPosition="
				+ jobPosition + ", joinPartyTime=" + joinPartyTime + ", positiveDate=" + positiveDate + ", branchParty="
				+ branchParty + ", partyMembersNature=" + partyMembersNature + ", homeAddress=" + homeAddress
				+ ", mobilePhone=" + mobilePhone + ", fixedPhone=" + fixedPhone + ", lostUnionState=" + lostUnionState
				+ ", lostUnionDate=" + lostUnionDate + ", mobilePartyMembers=" + mobilePartyMembers + ", streamWise="
				+ streamWise + ", partyAffiliationStatus=" + partyAffiliationStatus + ", remark=" + remark + "]";
	}
	
	
	
}
