package hg.util;

import java.util.*;

public class ConstantsKey {
    public static final int DRAFT = 1;
    public static final int PUBLISHED = 2;

    public static final int INITIAL = 0;    // 待审批
    public static final int APPROVED = 1;   //已审批
    public static final int REJECTED = 2;   //已驳回

    public static final int RECEIPT = 3;   //已回执
    public static final int CONFIRM = 4;   //已确认回执
    public static final int RESUBMIT = 5;   //已重新提交
    public static final String[] STATUS_LIST = {"审核中", "已审核", "已驳回","已上传回执", "已确认回执", "已重新申请"};

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

    public static final String MEETING_STATE_UNSUBMITTED = "暂存中";
    public static final String MEETING_STATE_SUBMIT = "已提交";
    public static final String MEETING_STATE_WITHDRAW = "已撤回";
    public static final String MEETING_STATE_REJECTED = "被驳回";
    public static final String MEETING_STATE_PASSED = "已通过";
    public static final String MEETING_STATE_ASSIGNED = "已指派";
    public static final String MEETING_STATE_UNCHECKED = "未检查";
    public static final String MEETING_STATE_CHECKED = "已检查";

    public static final Map<String, String> MEETING_STATES = new HashMap<>(); // 权限 - 组织类型对应

    static {
        MEETING_STATES.put("0", MEETING_STATE_UNSUBMITTED);
        MEETING_STATES.put("1",MEETING_STATE_SUBMIT);
        MEETING_STATES.put("2", MEETING_STATE_WITHDRAW);
        MEETING_STATES.put("3", MEETING_STATE_REJECTED);
        MEETING_STATES.put("4", MEETING_STATE_PASSED);
        MEETING_STATES.put("5", MEETING_STATE_ASSIGNED);
        MEETING_STATES.put("6", MEETING_STATE_UNCHECKED);
        MEETING_STATES.put("7", MEETING_STATE_CHECKED);
    }
    public static final String ORG_DESC_COMMITTEE = "党委";
    public static final String ORG_DESC_GRAND_BRANCH = "党总支";
    public static final String ORG_DESC_BRANCH = "党支部";
    public static final String ORG_DESC_FACULTY_BRANCH = "教工党支部";
    public static final String ORG_DESC_SPE_TEACHER_BRANCH = "专任教师支部";
    public static final String ORG_DESC_DOCTOR_BRANCH = "博士生党支部";
    public static final String ORG_DESC_MASTER_BRANCH = "硕士生党支部";
    public static final String ORG_DESC_BACHELOR_BRANCH = "本科生党支部";
    public static final String ORG_DESC_RETIRED_BRANCH = "离退休党支部";

    public static final Map<Integer, String> ORG_DESC_MAP = new HashMap<>(); // 权限 - 组织类型对应

    static {
        ORG_DESC_MAP.put(1, ORG_DESC_COMMITTEE);
        ORG_DESC_MAP.put(2, ORG_DESC_GRAND_BRANCH);
        ORG_DESC_MAP.put(3, ORG_DESC_BRANCH);
        ORG_DESC_MAP.put(4, ORG_DESC_FACULTY_BRANCH);
        ORG_DESC_MAP.put(5, ORG_DESC_SPE_TEACHER_BRANCH);
        ORG_DESC_MAP.put(6, ORG_DESC_DOCTOR_BRANCH);
        ORG_DESC_MAP.put(7, ORG_DESC_MASTER_BRANCH);
        ORG_DESC_MAP.put(8, ORG_DESC_BACHELOR_BRANCH);
        ORG_DESC_MAP.put(9, ORG_DESC_RETIRED_BRANCH);
    }

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

    public static Map<String, String> RETENTION_MAPPING = new LinkedHashMap<>();

    static
    {
        RETENTION_MAPPING.put("user_name", "人员姓名");
        RETENTION_MAPPING.put("org_name", "所在支部");
        RETENTION_MAPPING.put("second_name", "二级党委");
        RETENTION_MAPPING.put("time", "申请时间");
        RETENTION_MAPPING.put("status", "状态");
    }

    public static Map<String, String> TRANSPORT_MAPPING = new LinkedHashMap<>();

    static
    {
        TRANSPORT_MAPPING.put("user_name", "人员姓名");
        TRANSPORT_MAPPING.put("org_name", "所在支部");
        TRANSPORT_MAPPING.put("second_name", "二级党委");
        TRANSPORT_MAPPING.put("to_org_name", "去往单位");
        TRANSPORT_MAPPING.put("time", "申请时间");
        TRANSPORT_MAPPING.put("status", "状态");
    }
}
