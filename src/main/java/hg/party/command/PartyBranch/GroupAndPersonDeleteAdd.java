package hg.party.command.PartyBranch;

import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.server.partyBranch.PartyBranchService;
import party.constants.PartyPortletKeys;
import party.portlet.partyBranch.partyBranchPortlet;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
				"javax.portlet.name=" + PartyPortletKeys.NewPlan,
				"mvc.command.name=/hg/paersonAndGroupAddDelete"
	    },
	    service = MVCResourceCommand.class
)
public class GroupAndPersonDeleteAdd implements MVCResourceCommand{
	@Reference
	PartyBranchService service;
	@Reference
	TransactionUtil transactionUtil;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String path=ParamUtil.getString(resourceRequest, "path");
		String groupId=ParamUtil.getString(resourceRequest, "groupId");
		String groupName=ParamUtil.getString(resourceRequest, "groupName");
		String groupMember=ParamUtil.getString(resourceRequest, "groupMember");
		String orgId=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		String participant_id=ParamUtil.getString(resourceRequest, "participant_id");
		String groupMemberId=ParamUtil.getString(resourceRequest, "groupMemberId");
		path = HtmlUtil.escape(path);
		groupId = HtmlUtil.escape(groupId);
		groupName = HtmlUtil.escape(groupName);
		participant_id = HtmlUtil.escape(participant_id);
		groupMember = HtmlUtil.escape(groupMember);
		groupMemberId = HtmlUtil.escape(groupMemberId);
		PrintWriter printWriter=null;
		try { 
			printWriter=resourceResponse.getWriter();
				 if ("getGroupPersons".equals(path)) {
					 printWriter.write(service.findGroupPersons(groupId));
				}else if ("addGroup".equals(path)) {
					String uuid=UUID.randomUUID().toString();
					String sql="INSERT INTO hg_party_group_org_info ( \"group_id\", \"group_name\", \"organization_id\",\"group_state\") VALUES ( '"+uuid+"', '"+groupName+"', '"+orgId+"','1');";
					int n=	service.save(sql);
						if (n==1) {
							Map<String, Object> map=new HashMap<>();
							map.put("uuid", uuid);
							 printWriter.write(JSON.toJSONString(map));
						    }
				}else if ("deleteGroup".equals(path)) {
					//String sql="update hg_party_group_org_info set group_state='0' where group_id='"+groupId+"'";
					//String sql="DELETE FROM hg_party_group_org_info WHERE group_id='"+groupId+"'";
					//int n=	service.save(sql);
					int n = service.deleteGroupByGroupId(groupId);
					if (n==1) {
						 Map<String, Object> map=new HashMap<>();
						 map.put("state", "ok");
						 printWriter.write(JSON.toJSONString(map));
					    }
				}else if ("editGroup".equals(path)) {
				 	if(!StringUtils.isEmpty(groupId) && !StringUtils.isEmpty(groupName)){
						int n = service.updateGroupNameByGroupId(groupName,groupId);
						if (n==1) {
							Map<String, Object> map=new HashMap<>();
							map.put("state", "ok");
							printWriter.write(JSON.toJSONString(map));
						}
					}
				 }else if ("addPerson".equals(path)) {
					int n=0;					
					if (!StringUtils.isEmpty(participant_id)) {
						String []users=participant_id.split(",");
						if (users.length>0) {
							for (int i = 0; i < users.length; i++) {
								if (!service.isExist(groupId, users[i])) {
								//}else {
									String sql="INSERT INTO hg_party_group_member_info (\"group_id\", \"participant_id\") VALUES ( '"+groupId+"', '"+users[i]+"')";
									 n=service.save(sql);
								}
							}
						}else{
							if (!service.isExist(groupId, participant_id)) {
								String sql="INSERT INTO hg_party_group_member_info (\"group_id\", \"participant_id\") VALUES ( '"+groupId+"', '"+participant_id+"')";
								 n=service.save(sql);
							}
						}
					}
					//boolean isExist=service.isExist(groupId, participant_id);
					/*if (isExist) {
						 Map<String, Object> map=new HashMap<>();
						  map.put("state", "fail");
						  map.put("message", "该人员已经存在于该组");
						 printWriter.write(JSON.toJSONString(map));
						 return false;
					}*/
					//String sql="INSERT INTO hg_party_group_member_info (\"group_id\", \"participant_id\") VALUES ( '"+groupId+"', '"+participant_id+"')";
					//int n=service.save(sql);
					  Map<String, Object> map=new HashMap<>();
					if (n>0) {
						  map.put("state", "ok");
						 printWriter.write(JSON.toJSONString(map));
					    }else {
					    	   map.put("state", "ok");
							   map.put("message", "添加人员失败");
							 printWriter.write(JSON.toJSONString(map));
						}
				}else if("addGroupMember".equals(path)){
					 String[] userArr = groupMember.split(",");
					 if (userArr.length > 0) {
						 transactionUtil.startTransaction();
						 try{
							 for (int i = 0; i < userArr.length; i++) {
								 if (!service.isExist(groupId, userArr[i])) {
									 service.addGroupMember(groupId,userArr[i]);
								 }
							 }
							 transactionUtil.commit();
							 Map<String, Object> map=new HashMap<>();
							 map.put("state", "ok");
							 map.put("message", "添加人员成功");
							 printWriter.write(JSON.toJSONString(map));
						 }catch(Exception e){
							 e.printStackTrace();
							 transactionUtil.rollback();
						 }
					 }
				 }else if("deleteGroupMember".equals(path)){
					 String[] memberIdArr = groupMemberId.split(",");
					 if (memberIdArr.length > 0) {
						 transactionUtil.startTransaction();
						 try{
							 for (int i = 0; i < memberIdArr.length; i++) {
								 service.deleteGroupMember(Integer.valueOf(memberIdArr[i]));
							 }
							 transactionUtil.commit();
							 Map<String, Object> map=new HashMap<>();
							 map.put("state", "ok");
							 map.put("message", "移除人员成功");
							 printWriter.write(JSON.toJSONString(map));
						 }catch(Exception e){
							 e.printStackTrace();
							 transactionUtil.rollback();
						 }
					 }
				 }
				  	 
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
	}

}
