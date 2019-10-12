package hg.party.command.exportexcl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.util.StringUtils;
public class ExprotUntil {
	public static String getTaskState(String taskState) {
		if ("1".equals(taskState)) {
			return "已提交";
		} else if ("2".equals(taskState)) {
			return "已撤回";
		} else if ("3".equals(taskState)) {
			return "被驳回";
		} else if ("4".equals(taskState)) {
			return "已通过";
		} else if ("5".equals(taskState)) {
			return "已指派";
		} else if ("6".equals(taskState)) {
			return "未检查";
		} else if ("7".equals(taskState)) {
			return "已检查";
		}
		return "";
	}

	// 获取支部操作操作
	public static String getOperation(String taskState) {
		if ("".equals(taskState)) {
			return "拟定计划";
		} else if ("1".equals(taskState)) {
			return "撤回";
		} else if ("2".equals(taskState) || "3".equals(taskState)) {
			return "重拟计划";
		} else if ("4".equals(taskState)) {
			return "通知党员";
		} /*
			 * else if ("5".equals(taskState)) { return "已指派"; }else if
			 * ("6".equals(taskState)) { return "未检查"; }else if
			 * ("7".equals(taskState)) { return "已检查"; }
			 */
		return "";
	}

	public static String getSconedOperation(String taskState, Map<String, Object> map, String orgId) {
		String send_to = StringUtils.isEmpty(map.get("send_to")) ? "" : map.get("send_to") + "";
		String imeetingtype = StringUtils.isEmpty(map.get("imeetingtype")) ? "" : map.get("imeetingtype") + "";
		String org_id = StringUtils.isEmpty(map.get("org_type")) ? "" : map.get("org_type") + "";

		if ("".equals(taskState) && imeetingtype.equals("专题组织生活会") || send_to.equals("t")) {
			return "转派给党支部";
		} else if ("".equals(taskState) && !imeetingtype.equals("专题组织生活会") && send_to.equals("f")) {
			return "拟定计划";
		} else if ("1".equals(taskState)) {
			return "撤回";
		} else if ("2".equals(taskState) || "3".equals(taskState)) {
			return "重拟计划";
		} else if ("".equals(taskState) && org_id.equals(orgId)) {
			return "查看进度";
		} else if ("".equals(send_to)) {
			return "";
		}
		return "查看进度";
	}

	/**
	 * 解析时间
	 * 
	 * @param args
	 */
	public static Date date(String datestr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = sdf.parse(datestr);
		// String datestring=sdf.format(date);
		return date;
	}

	/**
	 * 解析时间
	 * 
	 * @param args
	 */
	public static String getDateString(String datestr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = sdf.parse(datestr);
		String datestring = sdf.format(date);
		return datestring;
	}

	/**
	 * 
	 * @param datestr
	 * @return
	 * @throws ParseException
	 */
	public static String getNote(Map<String, Object> map) throws ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date = sdf.parse(map.get("start_time") + "");
			if (date.getTime() < new Date().getTime()) {
				String plan_state = StringUtils.isEmpty(map.get("plan_state").toString()) ? "0"
						: map.get("plan_state").toString();
				if (Integer.parseInt(plan_state) >= 4) {
					return "已开展";
				} else {
					return "未开展";
				}
			} else {
				return "未开展";
			}
		} catch (Exception e) {
			return "";
		}
	}

	// 获取二级党委操作状态
	public static void main(String[] args) {
		String cc = getTaskState("1");
		System.out.println(cc);
	}
}
