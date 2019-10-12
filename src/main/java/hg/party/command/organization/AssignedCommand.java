package hg.party.command.organization;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.entity.organization.AssignedPerson;
import hg.party.server.organization.AssignedPersonService;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.AssignedPersonPorlet,
			"javax.portlet.name="+ PartyPortletKeys.PartySecondary,
			"javax.portlet.name="+ PartyPortletKeys.MeetingRecord,
			"javax.portlet.name="+ PartyPortletKeys.PartyMeetingPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
			"javax.portlet.name=" + PartyPortletKeys.OrgCheckCountPortlet,
			"javax.portlet.name=" + PartyPortletKeys.SecondMeetingRecordPortlet,
			"mvc.command.name=/hg/assigned"
	    },
	    service = MVCResourceCommand.class
)
//
public class AssignedCommand implements MVCResourceCommand{
    @Reference AssignedPersonService ass;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String paramType=ParamUtil.getString(resourceRequest,"paramType" );
		String meetingType=ParamUtil.getString(resourceRequest,"meetingType" );
		String party_branch=ParamUtil.getString(resourceRequest,"party_branch" );
		String start_date=ParamUtil.getString(resourceRequest,"start_date" );
        String pid=ParamUtil.getString(resourceRequest,"pid" );
		paramType = HtmlUtil.escape(paramType);
		meetingType = HtmlUtil.escape(meetingType);
		party_branch = HtmlUtil.escape(party_branch);
		start_date = HtmlUtil.escape(start_date);
		pid = HtmlUtil.escape(pid);
		if (paramType.equals("sconedParty")) {
			   List<Map<String, Object>>lists=ass.findSconedParty();
			   PrintWriter printWriter=null;
			    try {
					printWriter=resourceResponse.getWriter();
					 if (lists!=null&&lists.size()>0) {
							printWriter.write(JSON.toJSONString(lists));
						}
				} catch (IOException e) {
					e.printStackTrace();
				}
			   
				return false;
		}else if (paramType.equals("partyBranch")){
			List<Map<String, Object>>lists=ass.findPartyBranch(pid);
			   PrintWriter printWriter=null;
			    try {
					 printWriter=resourceResponse.getWriter();
					 if (lists!=null&&lists.size()>0) {
							printWriter.write(JSON.toJSONString(lists));
						}else {
							printWriter.write("");	
						}
				} catch (IOException e) {
					e.printStackTrace();
				}
			   
				return false;
		}else{
			//查询
			List<Map<String, Object>>  lists=ass.findMeetingPlan(party_branch, meetingType,start_date);
		    PrintWriter printWriter=null;
		    try {
				printWriter=resourceResponse.getWriter();
				 if (lists!=null&&lists.size()>0) {
						 printWriter.write(JSON.toJSONString(lists));
						 return false;
					}else {
						printWriter.write(JSON.toJSONString(""));
					}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		   
			   return false;
		}
	    
	}

}
