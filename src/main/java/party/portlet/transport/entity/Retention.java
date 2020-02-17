package party.portlet.transport.entity;

import com.dt.annotation.Table;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Table(name = "hg_party_retention")
public class Retention implements Serializable {

    private int id;

    private String retention_id;

    private String user_id;

    private String user_name;

    private String sex;

    private String ethnicity;

    private String current_degree;

    private String birthday;

    private String join_date;

    private String member_type;

    private String identity;

    private String phone_number;

    private String birth_place;

    private String contact;

    private String qq;

    private String wechat;

    private String email;

    private String foreign_limit;

    private String aboard_date;

    private String return_date;

    private String study_degree;

    private String domestic_address;

    private String target_country;

    private String domestic_contact;

    private String domestic_contact_number;

    private String study_type;

    private String org_id;

    private String org_name;

    private String to_org_id;

    private String to_org_contact;

    private String to_org_title;

    private Timestamp time;

    private String operator;

    private String extra;

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getTo_org_id() {
        return to_org_id;
    }

    public void setTo_org_id(String to_org_id) {
        this.to_org_id = to_org_id;
    }

    public String getTo_org_title() {
        return to_org_title;
    }

    public void setTo_org_title(String to_org_title) {
        this.to_org_title = to_org_title;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRetention_id() {
        return retention_id;
    }

    public void setRetention_id(String retention_id) {
        this.retention_id = retention_id;
    }

    public String getCurrent_degree() {
        return current_degree;
    }

    public void setCurrent_degree(String current_degree) {
        this.current_degree = current_degree;
    }

    public String getBirth_place() {
        return birth_place;
    }

    public void setBirth_place(String birth_place) {
        this.birth_place = birth_place;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getForeign_limit() {
        return foreign_limit;
    }

    public void setForeign_limit(String foreign_limit) {
        this.foreign_limit = foreign_limit;
    }

    public String getAboard_date() {
        return aboard_date;
    }

    public void setAboard_date(String  aboard_date) {
        this.aboard_date = aboard_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getStudy_degree() {
        return study_degree;
    }

    public void setStudy_degree(String study_degree) {
        this.study_degree = study_degree;
    }

    public String getDomestic_address() {
        return domestic_address;
    }

    public void setDomestic_address(String domestic_address) {
        this.domestic_address = domestic_address;
    }

    public String getTarget_country() {
        return target_country;
    }

    public void setTarget_country(String target_country) {
        this.target_country = target_country;
    }

    public String getDomestic_contact() {
        return domestic_contact;
    }

    public void setDomestic_contact(String domestic_contact) {
        this.domestic_contact = domestic_contact;
    }

    public String getDomestic_contact_number() {
        return domestic_contact_number;
    }

    public void setDomestic_contact_number(String domestic_contact_number) {
        this.domestic_contact_number = domestic_contact_number;
    }

    public String getTo_org_contact() {
        return to_org_contact;
    }

    public void setTo_org_contact(String to_org_contact) {
        this.to_org_contact = to_org_contact;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStudy_type() {
        return study_type;
    }

    public void setStudy_type(String study_type) {
        this.study_type = study_type;
    }
}
