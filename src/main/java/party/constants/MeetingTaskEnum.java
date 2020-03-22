package party.constants;

public enum  MeetingTaskEnum {
    NO_SUBMIT("0","待提交"),
    SUBMIT("1","已提交"),
    WITHDRAWN("2","已撤回"),
    REJECT("3","被驳回"),
    PASS("4","已通过"),
    ASSIGNED("5","已指派"),
    NO_CHECKED("6","未检查"),
    CHECKED("7","已检查");

    MeetingTaskEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;    //类型
    private String desc;    //描述

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
