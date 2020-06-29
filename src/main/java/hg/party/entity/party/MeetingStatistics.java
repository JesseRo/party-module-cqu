package hg.party.entity.party;

/**
 * @author jesse
 * @Filename MeetingStatistics
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/6/29</li>
 */
public class MeetingStatistics {
    private int id;
    private String org_id;
    private String org_name;
    private String org_type;
    private String org_secretary;
    private long branch_count;
    private long plan_count;
    private long member_count;
    private long leader_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getBranch_count() {
        return branch_count;
    }

    public void setBranch_count(long branch_count) {
        this.branch_count = branch_count;
    }

    public long getPlan_count() {
        return plan_count;
    }

    public void setPlan_count(long plan_count) {
        this.plan_count = plan_count;
    }

    public long getMember_count() {
        return member_count;
    }

    public void setMember_count(long member_count) {
        this.member_count = member_count;
    }

    public long getLeader_count() {
        return leader_count;
    }

    public void setLeader_count(long leader_count) {
        this.leader_count = leader_count;
    }
}
