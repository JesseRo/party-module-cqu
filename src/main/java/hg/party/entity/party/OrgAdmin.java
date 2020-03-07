package hg.party.entity.party;

import com.dt.annotation.Column;
import com.dt.annotation.Table;

import java.sql.Timestamp;


@Table(name = "hg_party_org_admin")
public class OrgAdmin {
    //组织id
    @Column(sqlName = "org_id", sqlType = "varchar", javaName = "orgId")
    private String orgId;

    //管理员用户id
    @Column(sqlName = "admin_id", sqlType = "varchar", javaName = "adminId")
    private String adminId;

    //组织类型
    @Column(sqlName = "org_type", sqlType = "varchar", javaName = "orgType")
    private String orgType;

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
}
