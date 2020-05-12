package hg.party.entity.partyMembers;

public class GroupMember extends  Member {
    private int group_member_id;
    private String group_id;
    public int getGroup_member_id() {
        return group_member_id;
    }

    public void setGroup_member_id(int group_member_id) {
        this.group_member_id = group_member_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }
}
