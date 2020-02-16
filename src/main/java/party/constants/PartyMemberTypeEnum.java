package party.constants;

/**
 * 党费党员计算种类
 */
public enum PartyMemberTypeEnum {
    MONTH_SALARY("1","月薪制党员"),
    YEAR_SALARY("2","年薪制党员"),
    COMPANY_MEMBER("3","企业员工/其他协议工资党员"),
    RETIRE_EMPLOYEE("4","离退休教职工党员"),
    STUDENT("5","学生党员"),
    MASTER_JOB("6","在职就读硕士/博士党员");

    private String type;    //类型
    private String desc;    //描述

    PartyMemberTypeEnum(String type, String desc) {
        this.type=type;
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

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public static PartyMemberTypeEnum getEnum(String type) {
        PartyMemberTypeEnum[] partyMemberTypeEnums = values();
        for (PartyMemberTypeEnum imageFormatType : partyMemberTypeEnums) {
            if (imageFormatType.getType().equals(type)) {
                return imageFormatType;
            }
        }
        return null;
    }
}
