package party.memberEdit;

import com.dt.annotation.Table;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Objects;

@Table(name = "hg_party_member_edit")
public class MemberEdit {
	private int id;
	private String member_name;
	private String member_sex;
	//民族
	private String member_ethnicity;
	private Integer member_age;
	private String member_birthday;
	//身份证号
	private String member_identity;
	//学历
	private String member_degree;
	//工作
	private String member_job;
	private String member_join_date;
	//转正日期
	private String member_fomal_date;
	//所属组织
	private String member_org;
	//党员类型
	private String member_type;
	private String member_address;
	private String member_phone_number;
	//座机
	private String member_landline_number;
	private String member_is_outofcontact;
	private String member_outofcontact_date;
	private String member_is_flow;
	private String member_flow_to;
	private String member_membership_state;
	private String member_birth_place;

	private Boolean historic;
	private String member_mailbox;
	//专业技能职称
	private String member_major_title;

	private String member_marriage;

	private int status;

	private Timestamp submit_time;

	private String reason;

	private String member_unit;
	private String member_province;
	private String member_city;
	//是否是干部
	private String member_is_leader;
	//提交人
	private int submit_by;

	public String getMember_major_title() {
		return member_major_title;
	}

	public void setMember_major_title(String member_major_title) {
		this.member_major_title = member_major_title;
	}

	public String getMember_new_class() {
		return member_new_class;
	}

	public void setMember_new_class(String member_new_class) {
		this.member_new_class = member_new_class;
	}

	public String getMember_front_line() {
		return member_front_line;
	}

	public void setMember_front_line(String member_front_line) {
		this.member_front_line = member_front_line;
	}

	public String getMember_party_committee() {
		return member_party_committee;
	}
	
	public String getMember_birth_place() {
		return member_birth_place;
	}

	public void setMember_birth_place(String member_birth_place) {
		this.member_birth_place = member_birth_place;
	}
	
	public void setMember_party_committee(String member_party_committee) {
		this.member_party_committee = member_party_committee;
	}

	public String getMember_party_position() {
		return member_party_position;
	}

	public void setMember_party_position(String member_party_position) {
		this.member_party_position = member_party_position;
	}

	//新社会阶层
	private String member_new_class;
	//一线情况
	private String member_front_line;
	//所在党委
	private String member_party_committee;
	//党内职务
	private String member_party_position;
	public MemberEdit(){
	}

	public MemberEdit(String member_name, String member_sex, String member_ethnicity, String member_birthday, String member_identity, String member_degree, String member_job, String member_join_date, String member_fomal_date, String member_org, String member_type, String member_address, String member_phone_number,String member_birth_place, String member_mailbox, String member_major_title, String member_marriage, String member_unit, String member_province, String member_city,String member_is_leader,int submit_by) {
		this.member_name = member_name;
		this.member_sex = member_sex;
		this.member_ethnicity = member_ethnicity;
		this.member_birthday = member_birthday;
		this.member_identity = member_identity;
		this.member_degree = member_degree;
		this.member_job = member_job;
		this.member_join_date = member_join_date;
		this.member_fomal_date = member_fomal_date;
		this.member_org = member_org;
		this.member_type = member_type;
		this.member_address = member_address;
		this.member_phone_number = member_phone_number;
		this.member_birth_place = member_birth_place;
		this.member_mailbox = member_mailbox;
		this.member_major_title = member_major_title;
		this.member_marriage = member_marriage;
		this.member_unit = member_unit;
		this.member_province = member_province;
		this.member_city = member_city;
		this.member_is_leader = member_is_leader;
		this.submit_by = submit_by;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof MemberEdit){
			Field[] fields = this.getClass().getDeclaredFields();
			boolean ret = true;
			try {
				for(Field field : fields){
					field.setAccessible(true);
					if (!(field.getName().equalsIgnoreCase("id") || field.getName().equalsIgnoreCase("historic"))) {
						ret = ret && Objects.equals(field.get(this), field.get(obj));
					}
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				System.out.println("额额..");
				return false;
			}
			return ret;
		}else {
			return super.equals(obj);
		}
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_sex() {
		return member_sex;
	}
	public void setMember_sex(String member_sex) {
		this.member_sex = member_sex;
	}
	public String getMember_birthday() {
		return member_birthday;
	}
	public void setMember_birthday(String member_birthday) {
		this.member_birthday = member_birthday;
	}
	public String getMember_identity() {
		return member_identity;
	}
	public void setMember_identity(String member_identity) {
		this.member_identity = member_identity;
	}
	public String getMember_degree() {
		return member_degree;
	}
	public void setMember_degree(String member_degree) {
		this.member_degree = member_degree;
	}
	public String getMember_job() {
		return member_job;
	}
	public void setMember_job(String member_job) {
		this.member_job = member_job;
	}
	public String getMember_join_date() {
		return member_join_date;
	}
	public void setMember_join_date(String member_join_date) {
		this.member_join_date = member_join_date;
	}
	public String getMember_fomal_date() {
		return member_fomal_date;
	}
	public void setMember_fomal_date(String member_fomal_date) {
		this.member_fomal_date = member_fomal_date;
	}
	public String getMember_org() {
		return member_org;
	}
	public void setMember_org(String member_org) {
		this.member_org = member_org;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public String getMember_address() {
		return member_address;
	}
	public void setMember_address(String member_address) {
		this.member_address = member_address;
	}
	public String getMember_phone_number() {
		return member_phone_number;
	}
	public void setMember_phone_number(String member_phone_number) {
		this.member_phone_number = member_phone_number;
	}
	public String getMember_landline_number() {
		return member_landline_number;
	}
	public void setMember_landline_number(String member_landline_number) {
		this.member_landline_number = member_landline_number;
	}
	public String getMember_is_outofcontact() {
		return member_is_outofcontact;
	}
	public void setMember_is_outofcontact(String member_is_outofcontact) {
		this.member_is_outofcontact = member_is_outofcontact;
	}
	public String getMember_outofcontact_date() {
		return member_outofcontact_date;
	}
	public void setMember_outofcontact_date(String member_outofcontact_date) {
		this.member_outofcontact_date = member_outofcontact_date;
	}
	public String getMember_is_flow() {
		return member_is_flow;
	}
	public void setMember_is_flow(String member_is_flow) {
		this.member_is_flow = member_is_flow;
	}
	public String getMember_flow_to() {
		return member_flow_to;
	}
	public void setMember_flow_to(String member_flow_to) {
		this.member_flow_to = member_flow_to;
	}
	public String getMember_membership_state() {
		return member_membership_state;
	}
	public void setMember_membership_state(String member_membership_state) {
		this.member_membership_state = member_membership_state;
	}

	public Integer getMember_age() {
		return member_age;
	}

	public void setMember_age(Integer member_age) {
		this.member_age = member_age;
	}

	public String getMember_mailbox() {
		return member_mailbox;
	}

	public void setMember_mailbox(String member_mailbox) {
		this.member_mailbox = member_mailbox;
	}

	public Boolean getHistoric() {
		return historic;
	}

	public void setHistoric(Boolean historic) {
		this.historic = historic;
	}

	public String getMember_ethnicity() {
		return member_ethnicity;
	}

	public void setMember_ethnicity(String member_ethnicity) {
		this.member_ethnicity = member_ethnicity;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", member_name=" + member_name + ", member_sex=" + member_sex
				+ ", member_ethnicity=" + member_ethnicity + ", member_age=" + member_age + ", member_birthday="
				+ member_birthday + ", member_identity=" + member_identity + ", member_degree=" + member_degree
				+ ", member_job=" + member_job + ", member_join_date=" + member_join_date + ", member_fomal_date="
				+ member_fomal_date + ", member_org=" + member_org + ", member_type=" + member_type
				+ ", member_address=" + member_address + ", member_phone_number=" + member_phone_number
				+ ", member_landline_number=" + member_landline_number + ", member_is_outofcontact="
				+ member_is_outofcontact + ", member_outofcontact_date=" + member_outofcontact_date
				+ ", member_is_flow=" + member_is_flow + ", member_flow_to=" + member_flow_to
				+ ", member_membership_state=" + member_membership_state + ", member_birth_place=" + member_birth_place
				+ ", historic=" + historic + ", member_mailbox=" + member_mailbox + ", member_major_title="
				+ member_major_title + ", member_new_class=" + member_new_class + ", member_front_line="
				+ member_front_line + ", member_party_committee=" + member_party_committee + ", member_party_position="
				+ member_party_position + "]";
	}

	public String getMember_marriage() {
		return member_marriage;
	}

	public void setMember_marriage(String member_marriage) {
		this.member_marriage = member_marriage;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getSubmit_time() {
		return submit_time;
	}

	public void setSubmit_time(Timestamp submit_time) {
		this.submit_time = submit_time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMember_unit() {
		return member_unit;
	}

	public void setMember_unit(String member_unit) {
		this.member_unit = member_unit;
	}

	public String getMember_province() {
		return member_province;
	}

	public void setMember_province(String member_province) {
		this.member_province = member_province;
	}

	public String getMember_city() {
		return member_city;
	}

	public void setMember_city(String member_city) {
		this.member_city = member_city;
	}

	public String getMember_is_leader() {
		return member_is_leader;
	}

	public void setMember_is_leader(String member_is_leader) {
		this.member_is_leader = member_is_leader;
	}

	public int getSubmit_by() {
		return submit_by;
	}

	public void setSubmit_by(int submit_by) {
		this.submit_by = submit_by;
	}

    public User toUser() {
		User user = new User();
		user.setId(this.getSubmit_by());
		user.setUser_id(this.getMember_identity());
		user.setUser_mailbox(this.getMember_name());
		user.setUser_sex(this.getMember_sex());
		return user;
    }

	public Member toMember() {
		Member member = new Member();
		member.setMember_name(this.getMember_name());
		member.setMember_sex(this.getMember_sex());
		member.setMember_address(this.getMember_address());
		member.setMember_unit(this.getMember_unit());
		member.setMember_birth_place(this.getMember_birth_place());
		member.setMember_birthday(this.getMember_birthday());
		member.setMember_join_date(this.getMember_join_date());
		member.setMember_identity(this.getMember_identity());
		member.setMember_degree(this.getMember_degree());
		member.setMember_type(this.getMember_type());
		member.setMember_address(this.getMember_address());
		member.setMember_fomal_date(this.getMember_fomal_date());
		member.setMember_phone_number(this.getMember_phone_number());
		member.setMember_mailbox(this.getMember_mailbox());
		member.setMember_marriage(this.getMember_marriage());
		member.setMember_province(this.getMember_province());
		member.setMember_city(this.getMember_city());
		member.setMember_org(this.getMember_org());
		member.setMember_is_leader(this.getMember_is_leader());
		member.setMember_job(this.getMember_job());
		member.setMember_ethnicity(this.getMember_ethnicity());
		return member;
	}
}