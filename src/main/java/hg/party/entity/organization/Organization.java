package hg.party.entity.organization;

import java.util.Objects;
import com.dt.annotation.Table;

@Table(name = "hg_party_org")
public class Organization{
	private int id;
	private String org_id;
	private String org_name;
	private String org_type;
	private String org_secretary;
	private String org_contactor;
	private String org_phone_number;
	private String org_unit_situation;
	private String org_unit_name;
	private String org_unit_type;
	private String org_unit_party_situation;
	private String org_unit_code;
	private String org_relation;
	private String org_parent;
	private String org_code;
	
	public String getOrg_code() {
		return org_code;
	}

	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) {
			return false;
		}
		if (obj instanceof Organization) {
			Organization other = (Organization)obj;
			return Objects.equals(org_name, other.org_name)
					&& Objects.equals(org_type, other.org_type) 
					&& Objects.equals(org_code, other.org_code) 
					&& Objects.equals(org_secretary, other.org_secretary) 
					&& Objects.equals(org_contactor, other.org_contactor) 
					&& Objects.equals(org_phone_number, other.org_phone_number) 
					&& Objects.equals(org_unit_situation, other.org_unit_situation) 
					&& Objects.equals(org_unit_name, other.org_unit_name) 
					&& Objects.equals(org_unit_type, other.org_unit_type) 
					&& Objects.equals(org_relation, other.org_relation) 
					&& Objects.equals(org_unit_code, other.org_unit_code) 
					&& Objects.equals(org_unit_party_situation, other.org_unit_party_situation)
					&& Objects.equals(org_parent, other.org_parent);
		}else {
			return super.equals(obj);
		}
	}
	
	public boolean isHistoric() {
		return historic;
	}
	public void setHistoric(boolean historic) {
		this.historic = historic;
	}
	private boolean historic;

	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_type() {
		return org_type;
	}
	public void setOrg_type(String org_type) {
		this.org_type = org_type;
	}
	public String getOrg_secretary() {
		return org_secretary;
	}
	public void setOrg_secretary(String org_secretary) {
		this.org_secretary = org_secretary;
	}
	public String getOrg_contactor() {
		return org_contactor;
	}
	public void setOrg_contactor(String org_contactor) {
		this.org_contactor = org_contactor;
	}
	public String getOrg_phone_number() {
		return org_phone_number;
	}
	public void setOrg_phone_number(String org_phone_number) {
		this.org_phone_number = org_phone_number;
	}
	public String getOrg_unit_situation() {
		return org_unit_situation;
	}
	public void setOrg_unit_situation(String org_unit_situation) {
		this.org_unit_situation = org_unit_situation;
	}
	public String getOrg_unit_name() {
		return org_unit_name;
	}
	public void setOrg_unit_name(String org_unit_name) {
		this.org_unit_name = org_unit_name;
	}
	public String getOrg_unit_type() {
		return org_unit_type;
	}
	public void setOrg_unit_type(String org_unit_type) {
		this.org_unit_type = org_unit_type;
	}
	public String getOrg_unit_party_situation() {
		return org_unit_party_situation;
	}
	public void setOrg_unit_party_situation(String org_unit_party_situation) {
		this.org_unit_party_situation = org_unit_party_situation;
	}
	public String getOrg_unit_code() {
		return org_unit_code;
	}
	public void setOrg_unit_code(String org_unit_code) {
		this.org_unit_code = org_unit_code;
	}
	public String getOrg_relation() {
		return org_relation;
	}
	public void setOrg_relation(String org_relation) {
		this.org_relation = org_relation;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getOrg_parent() {
		return org_parent;
	}
	public void setOrg_parent(String org_parent) {
		this.org_parent = org_parent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Organization [id=" + id + ", org_id=" + org_id + ", org_name=" + org_name + ", org_type=" + org_type
				+ ", org_secretary=" + org_secretary + ", org_contactor=" + org_contactor + ", org_phone_number="
				+ org_phone_number + ", org_unit_situation=" + org_unit_situation + ", org_unit_name=" + org_unit_name
				+ ", org_unit_type=" + org_unit_type + ", org_unit_party_situation=" + org_unit_party_situation
				+ ", org_unit_code=" + org_unit_code + ", org_relation=" + org_relation + ", org_parent=" + org_parent
				+ ", org_code=" + org_code + ", historic=" + historic + "]";
	}

}