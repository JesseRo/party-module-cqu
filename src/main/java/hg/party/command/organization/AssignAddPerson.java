package hg.party.command.organization;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.collections4.functors.ForClosure;
import org.junit.experimental.theories.FromDataPoints;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import hg.party.entity.organization.Assign;
import hg.party.server.organization.AssigneService;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.AssignedPersonPorlet,
			"javax.portlet.name="+ PartyPortletKeys.Form,
			"javax.portlet.name="+ PartyPortletKeys.PartySecondary,
			"javax.portlet.name="+ PartyPortletKeys.PartyMeetingPlan,
			"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
			"mvc.command.name=/hg/assignedAddPerson"
	    },
	    service = MVCResourceCommand.class
)
/**
 * 指派，抽查人员的 增，删，查，操作
 * @author gongmingbo
 *
 */
public class AssignAddPerson implements MVCResourceCommand{
	@Reference AssigneService service;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String path=ParamUtil.getString(resourceRequest, "path");
		String userName=ParamUtil.getString(resourceRequest, "userName");
		String assigne_user_id=ParamUtil.getString(resourceRequest, "assigne_user_id");
		String name=ParamUtil.getString(resourceRequest, "name");
		String assignId=ParamUtil.getString(resourceRequest, "assignId");
		path = HtmlUtil.escape(path);
		userName = HtmlUtil.escape(userName);
		assigne_user_id = HtmlUtil.escape(assigne_user_id);
		name = HtmlUtil.escape(name);
		assignId = HtmlUtil.escape(assignId);
		String orgId=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
		String orgType=(String)SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "orgType");
		
		if (path.equals("find")) {
			try {
				PrintWriter printWriter=resourceResponse.getWriter();
				List<Map<String, Object>> info=new ArrayList<>();
				if ("organization".equals(orgType)) {
					info=service.findPersonInformation(userName);
				}else if("secondary".equals(orgType)){
					List<Map<String, Object>> list=service.findBranchByOrgParentId(orgId);
					List<Map<String, Object>> listPersons=service.findPersonInformation(userName);
					if (listPersons!=null&&listPersons.size()>0) {
						for (Map<String, Object> map : listPersons) {
							String org_id= map.get("user_department_id").toString();
							 if (list!=null&&list.size()>0) {
								 for (Map<String, Object> map2 : list) {
									String org_ids=map2.get("org_id").toString();
									if (org_id.equals(org_ids)||org_id.equals(orgId)) {
										info.add(map);
										break;
									}		
								}
							}
						}
					}
				}else {
					List<Map<String, Object>> listPersons=service.findPersonInformation(userName);
					if (listPersons!=null&&listPersons.size()>0) {
						for (Map<String, Object> map : listPersons) {
							String org_id= map.get("user_department_id").toString();
							if (org_id.equals(orgId)) {
								info.add(map);
								continue;
							}
						}
					}
				}
				if (info!=null&&info.size()>0) {
					Map<String, Object> map=new HashMap<>();
					map.put("state", "succeed");
					map.put("data", info);
					String string=JSON.toJSONString(map);
					System.out.println(string);
					printWriter.write(JSON.toJSONString(map));
					return false;
				}else if(StringUtils.isEmpty(userName)){
					Map<String, Object> map=new HashMap<>();
					map.put("state", "fail");
					map.put("message", "输入不能为空！");
					printWriter.write(JSON.toJSONString(map));
				}else {
					Map<String, Object> map=new HashMap<>();
					map.put("state", "fail");
					map.put("message", "无此人或不属于该党委及其支部");
					printWriter.write(JSON.toJSONString(map));
				}
			} catch (Exception e) {
				
			}
		}
		///
		if (path.equals("add")) {
			try {
				PrintWriter printWriter = resourceResponse.getWriter();
				Assign assign = new Assign();
				assign.setAssigne_name(name);
				assign.setAssigne_user_id(assigne_user_id);
				assign.setDepartment_name(orgId);
				assign.setState(0);
				boolean tt = true;
				if (StringUtils.isEmpty(assigne_user_id)) {
					tt = false;
				}
				if (service.isExite(assigne_user_id, orgId) || !tt) {
					Map<String, Object> map = new HashMap<>();
					map.put("state", "fail");
					map.put("message", "该人员已经存在！");
					printWriter.write(JSON.toJSONString(map));
					return false;
				}
				int n = service.save(assign);
				if (n == 1) {
					Map<String, Object> map = new HashMap<>();
					map.put("state", "succeed");
					printWriter.write(JSON.toJSONString(map));
					return false;
				} else {
					Map<String, Object> map = new HashMap<>();
					map.put("state", "fail");
					map.put("message", "保存失败");
					printWriter.write(JSON.toJSONString(map));
				}
			} catch (Exception e) {

			}
		}
		
		if (path.equals("delete")) {
			try {
				PrintWriter printWriter=resourceResponse.getWriter();				    
				        
				int n =service.dalete(assignId);
				if (n==1) {
					Map<String, Object> map=new HashMap<>();
					map.put("state", "succeed");
					printWriter.write(JSON.toJSONString(map));
					return false;
				}
				else {
					Map<String, Object> map=new HashMap<>();
					map.put("state", "fail");
					map.put("message", "保存失败");
					printWriter.write(JSON.toJSONString(map));
				}
			} catch (Exception e) {
				
			}	
		}
		//
		return false;
	}

}
