package hg.party.command.organization;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.IntToDoubleFunction;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.quartz.SchedulerException;
import org.springframework.util.StringUtils;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.entity.party.MeetingPlan;
import hg.party.entity.partyMembers.Member;
import hg.party.server.organization.PublicInformationService;
import hg.party.server.party.PartyMeetingPlanInfo;
import hg.party.server.party.PartyMemberServer;
import hg.party.server.sms.SmsService;
import party.constants.PartyPortletKeys;
@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.AssignedPersonPorlet,
			"javax.portlet.name="+ PartyPortletKeys.PartySecondary,
			"javax.portlet.name="+ PartyPortletKeys.PartyMeetingPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
			"mvc.command.name=/hg/hg/assignPersonCheck"
	    },
	    service = MVCResourceCommand.class
)
/**
 * 指派人员
 * @author gmb
 *
 */
public class AssingnPersonCommend implements MVCResourceCommand{
	 PublicInformationService service=new PublicInformationService();
	 @Reference
	 private PartyMemberServer partyMemberServer;
	 @Reference
	 private PartyMeetingPlanInfo partyMeetingPlanInfo;
	 
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String orgType = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "orgType");
		String id = ParamUtil.getString(resourceRequest, "id");
		String dep = (String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		String assignId = ParamUtil.getString(resourceRequest, "assignId");//身份证号
		List<Member> Member = partyMemberServer.Member(assignId);//查询电话
		List<String> Member_phone = new ArrayList<String>();
		Member_phone.add((String)Member.get(0).getMember_phone_number());
		List<MeetingPlan> p = partyMeetingPlanInfo.MeetingPlan(id);//获取短信内容
		List<Map<String, Object>> depp = partyMeetingPlanInfo.dep(dep);
		String start_time = String.valueOf(p.get(0).getStart_time()).substring(0,19); //开始时间
		String end_time = String.valueOf(p.get(0).getEnd_time()).substring(11,19); //结束时间
		
		id = HtmlUtil.escape(id);
		assignId = HtmlUtil.escape(assignId);

		
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		Date date = new Date();
		
		//查询支部人数
		String party_org_id = p.get(0).getOrganization_id();   //会议所属组织id
		List<Map<String, Object>> nameNumber = partyMemberServer.nameNumber(party_org_id);
		int number = nameNumber.size();
		
		//String assign_name=ParamUtil.getString(resourceRequest, "assign_name");
		//String sql="update hg_party_meeting_plan_info set check_person='"+assignId+"' ,task_status='"+5+"' where id='"+id+"'";
		//String sqlOrg="update hg_party_meeting_plan_info set check_person_org='"+assignId+"' ,task_status_org='"+5+"' where id='"+id+"'";
	    int n = 0;
	    List<Map<String , Object>> list =partyMemberServer.findOrgNameById(Integer.parseInt(id));
	    List<Map<String , Object>> list2 =partyMemberServer.findSecondOrgNameById(Integer.parseInt(id));
	    String orgName=StringUtils.isEmpty(list.get(0)) ? "":list.get(0).get("org_name").toString();
	    String seconedOrgName=StringUtils.isEmpty(list2.get(0)) ? "":list2.get(0).get("org_name").toString();
//		if ("organization".equals(orgType)) {
		if (false) {
	    	//  n = service.saveAttachment(sqlOrg);
	    	  n = service.orgAssign(assignId, Integer.parseInt(id));
	    	  String sms = String.format("【西南大学】"
	    	  	                     	+ "\r\n党员同志，你好。"
	    	  	                     	+ "\r\n中国共产党西南大学委员会指派你抽查基层党支部组织生活，抽查完毕后请及时登录系统录入检查结果。谢谢！"
	    			                	+ "\r\n主题：%s"
	    			                	+ "\r\n二级党组织：%s"
	    			                	+ "\r\n党支部：%s"
	    			                	+ "\r\n党支部主题：%s"
	    			                	+ "\r\n支部人数：%s人"
	    			                	+ "\r\n时间：%s-%s"
	    			                	+ "\r\n地点： %s"
	    			                	+ "\r\n联系人：%s"
	    			                	+ "\r\n联系人电话:%s"
	    			                	+ "\r\n%s组织部"
	    			                	+ "\r\n%s",
//	    			  	depp.get(0).get("org_name"),
	    			  	p.get(0).getMeeting_theme(),
	    			    seconedOrgName,
	    			    orgName,
	    			    p.get(0).getMeeting_theme_secondary(), 
	    			    number,
	    			  	start_time,
	    			  	end_time,
	    			  	p.get(0).getPlace(),
	    			  	p.get(0).getContact(),
	    			  	p.get(0).getContact_phone(),
	    			  	depp.get(0).get("org_name"),
	    			  	dateFormat.format(date));
	    	try {
				SmsService.smsSend(assignId, sms, Member_phone);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}else {
			 // n= service.saveAttachment(sql);
			  n = service.seconedAssign(assignId, Integer.parseInt(id));
			  String sms = String.format("【西南大学】"
							  		+ "\r\n党员同志，你好。"
							  		+ "\r\n中国共产党西南大学委员会指派你检查基层党支部组织生活，检查完毕后请及时登录系统录入检查结果。谢谢！"
					    			+ "\r\n主题：%s"
					    			+ "\r\n二级党组织：%s"
					    			+ "\r\n党支部：%s"
					    			+ "\r\n党支部主题：%s"
					    			+ "\r\n支部人数：%s人"
					    			+ "\r\n时间：%s-%s"
					    			+ "\r\n地点： %s"
					    			+ "\r\n联系人：%s"
					    			+ "\r\n联系人电话：%s"
					    			+ "\r\n%s"
					    			+ "\r\n%s ",
//	    			  	depp.get(0).get("org_name"),
	    			  	p.get(0).getMeeting_theme(),
	    			  	depp.get(0).get("org_name"),
	    			  	orgName,
	    			  	p.get(0).getMeeting_theme_secondary(),
	    			  	number,
	    			  	start_time,
	    			  	end_time,
	    			  	p.get(0).getPlace(),
	    			  	p.get(0).getContact(),
	    			  	p.get(0).getContact_phone(),
	    			  	depp.get(0).get("org_name"),
	    			  	dateFormat.format(date));
	    	try {
				SmsService.smsSend(assignId, sms, Member_phone);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	   // service.saveAttachment("update hg_party_assigne set state='1' where assigne_user_id='"+assignId+"'");
	    service.updateAssignPersonState(assignId);
	    PrintWriter printWriter=null;
	    try {
			if (n==1) {
				printWriter=resourceResponse.getWriter();
				printWriter.write("SUCCESS");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
