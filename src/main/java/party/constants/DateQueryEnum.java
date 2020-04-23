package party.constants;

import org.springframework.util.StringUtils;

public enum DateQueryEnum {
    DAY_1("day_1","今天"),
    DAY_7("day_7","本周"),
    MONTH_1("month_1","本月"),
    MORE("more","更早");
    private String type;    //类型
    private String desc;    //描述

    DateQueryEnum(String type, String desc) {
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
    public static DateQueryEnum getEnum(String type) {
        DateQueryEnum[] dateQueryEnums = values();
        if(!StringUtils.isEmpty(type)){
            for (DateQueryEnum dateQueryEnum : dateQueryEnums) {
                if (dateQueryEnum.getType().equals(type)) {
                    return dateQueryEnum;
                }
            }
        }
        return null;
    }
}
