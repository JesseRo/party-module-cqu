package party.constants;

/**
 * 数据操作枚举
 */
public enum DataOperationEnum {
    CREATE("create","创建"),
    UPDATE("update","更新"),
    READ("read","读取"),
    DELETE("delete","删除");
    private String type;    //类型
    private String desc;    //描述

    DataOperationEnum(String type, String desc) {
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
    public static DataOperationEnum getEnum(String type) {
        DataOperationEnum[] dataOperationEnums = values();
        for (DataOperationEnum dataOperationEnum : dataOperationEnums) {
            if (dataOperationEnum.getType().equals(type)) {
                return dataOperationEnum;
            }
        }
        return null;
    }
}
