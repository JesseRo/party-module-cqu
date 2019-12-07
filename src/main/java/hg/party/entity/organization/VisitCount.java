package hg.party.entity.organization;

import java.sql.Timestamp;

import com.dt.annotation.Table;

@Table(name = "hg_party_visit_count")
public class VisitCount {
    private int id;
    private String user_id;
    private String user_name;
    private String user_role;
    private String department_id;
    private String department_name;
    private String ip;
    private Timestamp visit_time;
    private String remark;
    private String type;

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

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(Timestamp visit_time) {
        this.visit_time = visit_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "VisitCount [id=" + id + ", user_id=" + user_id + ", user_name=" + user_name + ", user_role="
                + user_role + ", department_id=" + department_id + ", department_name=" + department_name + ", ip="
                + ip + ", visit_time=" + visit_time + ", remark=" + remark + "]";
    }


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
