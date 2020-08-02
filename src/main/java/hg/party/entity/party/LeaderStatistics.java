package hg.party.entity.party;

/**
 * @author jesse
 * @Filename MeetingStatistics
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/6/29</li>
 */
public class LeaderStatistics {

    private String member_identity;

    private String member_name;

    private String org_id;

    private String org_name;

    private int join_count;

    private int sit_count;

    private int teach_count;

    public int getTeach_count() {
        return teach_count;
    }

    public void setTeach_count(int teach_count) {
        this.teach_count = teach_count;
    }

    public int getSit_count() {
        return sit_count;
    }

    public void setSit_count(int sit_count) {
        this.sit_count = sit_count;
    }

    public int getJoin_count() {
        return join_count;
    }

    public void setJoin_count(int join_count) {
        this.join_count = join_count;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_identity() {
        return member_identity;
    }

    public void setMember_identity(String member_identity) {
        this.member_identity = member_identity;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }
}
