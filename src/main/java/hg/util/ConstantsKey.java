package hg.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConstantsKey {
    public static final int DRAFT = 1;
    public static final int PUBLISHED = 2;

    public static final int INITIAL = 0;    // 待审批
    public static final int APPROVED = 1;   //已审批
    public static final int REJECTED = 2;   //已驳回

    public static final int UNREPORTED = 1;
    public static final int REPORTED = 2;

    public static final String ORG_TYPE_ROOT = "organization";
    public static final String ORG_TYPE_SECONDARY = "secondary";
    public static final String ORG_TYPE_BRANCH = "branch";
    
    public static final String ORG_TYPE_Pro = "propaganda";//宣传部
    public static final String ORG_PROPAGANDA = "宣传部";
    
    public static final String SECOND_PARTY = "二级党组织";
    public static final String ORG_PARTY = "组织部";
    public static final String BRANCH_PARTY = "党支部";
    public static final String COMMON_PARTY = "普通党员";
    public static final String OTHER_PARTY = "其他";



    public static final HashMap<String, String > PERMISSION_TO_ORGTYPE = new HashMap<>(); // 权限 - 组织类型对应
    public static final HashMap<String, String > ORGTYPE_TO_PERMISSION = new HashMap<>(); // 组织类型 - 权限对应
    static {
        PERMISSION_TO_ORGTYPE.putIfAbsent(SECOND_PARTY, ORG_TYPE_SECONDARY);
        PERMISSION_TO_ORGTYPE.putIfAbsent(ORG_PARTY, ORG_TYPE_ROOT);
        PERMISSION_TO_ORGTYPE.putIfAbsent(BRANCH_PARTY, ORG_TYPE_BRANCH);
        PERMISSION_TO_ORGTYPE.putIfAbsent(ORG_PROPAGANDA, ORG_TYPE_Pro);

        ORGTYPE_TO_PERMISSION.putIfAbsent( ORG_TYPE_SECONDARY, SECOND_PARTY);
        ORGTYPE_TO_PERMISSION.putIfAbsent(ORG_TYPE_ROOT, ORG_PARTY);
        ORGTYPE_TO_PERMISSION.putIfAbsent(ORG_TYPE_BRANCH, BRANCH_PARTY);
        ORGTYPE_TO_PERMISSION.putIfAbsent(ORG_TYPE_Pro, ORG_PROPAGANDA);
        
    }

    public static Map<String, String> REPORT_CONTENT_HEAD = new LinkedHashMap<>();

    static
    {
        REPORT_CONTENT_HEAD.put("code", "教学工号");
        REPORT_CONTENT_HEAD.put("name", "党员姓名");
        REPORT_CONTENT_HEAD.put("sex", "党员性别");
        REPORT_CONTENT_HEAD.put("position", "党员职位");
        REPORT_CONTENT_HEAD.put("party_age", "党员党龄");
        REPORT_CONTENT_HEAD.put("join_date", "入党日期");
        REPORT_CONTENT_HEAD.put("should_fee", "应缴党费");
        REPORT_CONTENT_HEAD.put("paid_fee", "实缴党费");
        REPORT_CONTENT_HEAD.put("extra", "备注信息");
    }

    public static Map<String, String> BRUNCH_REPORT_CONTENT_HEAD = new LinkedHashMap<>();

    static
    {
        BRUNCH_REPORT_CONTENT_HEAD.put("orgName", "支部名称");
        BRUNCH_REPORT_CONTENT_HEAD.put("paid_number", "缴清人数");
        BRUNCH_REPORT_CONTENT_HEAD.put("total_number", "总人数");
        BRUNCH_REPORT_CONTENT_HEAD.put("paid_fee", "实缴党费总计");
        BRUNCH_REPORT_CONTENT_HEAD.put("should_fee", "应缴党费总计");
    }

    public static Map<String, String> DROPDOWN_TYPES_MAPPING = new LinkedHashMap<>();

    static
    {
        DROPDOWN_TYPES_MAPPING.put("meetingType", "活动类型");
        DROPDOWN_TYPES_MAPPING.put("reason", "驳回原因");
        DROPDOWN_TYPES_MAPPING.put("taskStatus", "任务状态");
        DROPDOWN_TYPES_MAPPING.put("positior", "党内职务");
        DROPDOWN_TYPES_MAPPING.put("room", "学生宿舍园区");
    }
}
