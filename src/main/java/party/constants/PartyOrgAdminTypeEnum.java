package party.constants;

/**
 * 党组织管理员类型
 */
public enum PartyOrgAdminTypeEnum {
    NORMAL("normal","普通党员","普通党员"),
    ORGANIZATION("organization","组织部","组织部管理员"),
    SECONDARY("secondary","二级党组织","二级党组织管理员"),
    BRANCH("branch","党支部","党支部管理员");
    //[普通党员, 二级党组织, 组织部, 党支部]

    private String type;    //类型
    private String desc;    //描述
    private String role;    //描述

    PartyOrgAdminTypeEnum(String type,String role, String desc) {
        this.type=type;
        this.role=role;
        this.desc=desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
