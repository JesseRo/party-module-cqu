package party.portlet.org.command;

import java.io.PrintWriter;
import java.util.*;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.alibaba.fastjson.JSONObject;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.OrgAdmin;
import hg.party.server.organization.OrgAdminService;
import hg.party.server.organization.OrgService;
import hg.util.result.ResultUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;
import party.constants.DataOperationEnum;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgCRUD,
				"mvc.command.name=/org/manage"
	    },
	    service = MVCResourceCommand.class
)
public class OrgCRUDCommand implements MVCResourceCommand{
	 Logger log = Logger.getLogger(OrgCRUDCommand.class);
	@Reference
	private OrgAdminService orgAdminService;
	@Reference
	private OrgService orgService;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String option = ParamUtil.getString(resourceRequest, "option");
		DataOperationEnum dataOperationEnum = DataOperationEnum.getEnum(option);
		PrintWriter printWriter = null;
		try{
			printWriter = resourceResponse.getWriter();
		}catch (Exception e) {
			e.printStackTrace();
		}
		try {
			switch(dataOperationEnum){
				case READ:findOrg(resourceRequest,resourceResponse);break;
				case UPDATE:updateOrg(resourceRequest,resourceResponse);break;
				case CREATE:addOrg(resourceRequest,resourceResponse);break;
				case DELETE:deleteOrg(resourceRequest,resourceResponse);break;
			}
		} catch (Exception e) {
			printWriter.write(JSON.toJSONString(ResultUtil.fail("访问数据出错，请联系技术人员.")));
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查询组织
	 * @param resourceRequest
	 * @param resourceResponse
	 */
	private void findOrg(ResourceRequest resourceRequest,ResourceResponse resourceResponse) throws Exception {
		String id = ParamUtil.getString(resourceRequest, "id");
		String sessionId=resourceRequest.getRequestedSessionId();
		String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
		PrintWriter printWriter = resourceResponse.getWriter();
		if(!StringUtils.isEmpty(id)){
			List<String> list = getPermissions(userId,Integer.parseInt(id));
			if(list.contains(DataOperationEnum.READ.getType())){
				Organization organization = orgService.findOrgById(Integer.parseInt(id));
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("organization",organization);
				jsonObject.put("permissions",list);
				printWriter.write(JSON.toJSONString(ResultUtil.success(jsonObject)));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.noAuthority("你没有该组织信息查看权限。")));
			}
		}else{
			printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id不能为空！")));
		}
	}

	/**
	 * 添加组织
	 * @param resourceRequest
	 * @param resourceResponse
	 */
	private void addOrg(ResourceRequest resourceRequest,ResourceResponse resourceResponse) throws Exception {
		String id = ParamUtil.getString(resourceRequest, "id");
		String sessionId=resourceRequest.getRequestedSessionId();
		String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
		String descType = ParamUtil.getString(resourceRequest, "descType");
		String orgName = ParamUtil.getString(resourceRequest, "orgName");
		String address = ParamUtil.getString(resourceRequest, "address");
		String contactNumber = ParamUtil.getString(resourceRequest, "contactNumber");
		String fax = ParamUtil.getString(resourceRequest, "fax");
		String secretary = ParamUtil.getString(resourceRequest, "secretary");
		String email = ParamUtil.getString(resourceRequest, "email");
		String postal = ParamUtil.getString(resourceRequest, "postal");
		String contactor = ParamUtil.getString(resourceRequest, "contactor");
		String contactorNumber = ParamUtil.getString(resourceRequest, "contactorNumber");
		PrintWriter printWriter = resourceResponse.getWriter();
		if(!StringUtils.isEmpty(id) && !StringUtils.isEmpty(orgName) && !StringUtils.isEmpty(descType)){
			List<String> list = getPermissions(userId,Integer.parseInt(id));
			if(list.contains(DataOperationEnum.CREATE.getType())){
				Organization org = orgService.findOrgByPID(Integer.parseInt(id), orgName);
				Organization orgP = orgService.findOrgById(Integer.parseInt(id));
				if(org== null){
					Organization organization = new Organization();
					organization.setOrg_name(orgName);
					String uuid = UUID.randomUUID().toString();
					organization.setOrg_id(uuid);
					if(PartyOrgAdminTypeEnum.ORGANIZATION.getType().equals(orgP.getOrg_type())){
						organization.setOrg_type(PartyOrgAdminTypeEnum.SECONDARY.getType());
					}else{
						organization.setOrg_type(PartyOrgAdminTypeEnum.BRANCH.getType());
					}
					organization.setOrg_parent(orgP.getOrg_id());
					organization.setDesc_type(Integer.parseInt(descType));
					organization.setOrg_address(address);
					organization.setOrg_contactor(contactor);
					organization.setOrg_contactor_phone(contactorNumber);
					organization.setOrg_email(email);
					organization.setOrg_phone_number(contactNumber);
					organization.setOrg_fax(fax);
					organization.setOrg_code(postal);
					organization.setOrg_secretary(secretary);
					int createId = orgService.createOrg(organization);
					if(createId>0){
						printWriter.write(JSON.toJSONString(ResultUtil.success(createId,"创建成功！")));
					}else{
						printWriter.write(JSON.toJSONString(ResultUtil.fail("创建失败！")));
					}
				}else{
					printWriter.write(JSON.toJSONString(ResultUtil.fail("该组织名已经存在")));
				}
			} else {
				printWriter.write(JSON.toJSONString(ResultUtil.fail("组织信息不存在！")));
			}
		}else{
			printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id、orgType、orgName不能为空！")));
		}
	}

	/**
	 * 更新组织
	 * @param resourceRequest
	 * @param resourceResponse
	 */
	private void updateOrg(ResourceRequest resourceRequest,ResourceResponse resourceResponse) throws Exception {
		String id = ParamUtil.getString(resourceRequest, "id");
		String sessionId=resourceRequest.getRequestedSessionId();
		String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
		PrintWriter printWriter = resourceResponse.getWriter();
		if(!StringUtils.isEmpty(id)){
			List<String> list = getPermissions(userId,Integer.parseInt(id));
			if(list.contains(DataOperationEnum.UPDATE.getType())){
				String descType = ParamUtil.getString(resourceRequest, "descType");
				String orgName = ParamUtil.getString(resourceRequest, "orgName");
				String address = ParamUtil.getString(resourceRequest, "address");
				String contactNumber = ParamUtil.getString(resourceRequest, "contactNumber");
				String fax = ParamUtil.getString(resourceRequest, "fax");
				String postal = ParamUtil.getString(resourceRequest, "postal");
				String secretary = ParamUtil.getString(resourceRequest, "secretary");
				String email = ParamUtil.getString(resourceRequest, "email");
				String contactor = ParamUtil.getString(resourceRequest, "contactor");
				String contactorNumber = ParamUtil.getString(resourceRequest, "contactorNumber");
				Organization organization = orgService.findOrgById(Integer.parseInt(id));
				if (organization != null){
					organization.setOrg_name(orgName);
					organization.setOrg_address(address);
					organization.setOrg_phone_number(contactNumber);
					organization.setOrg_fax(fax);
					organization.setOrg_secretary(secretary);
					organization.setOrg_email(email);
					organization.setOrg_code(postal);
					organization.setOrg_contactor(contactor);
					organization.setOrg_contactor_phone(contactorNumber);
					organization.setDesc_type(Integer.parseInt(descType));
					int rows = orgService.updateOrg(organization);
					if(rows>0){
						printWriter.write(JSON.toJSONString(ResultUtil.success(null,"修改成功！")));
					}else{
						printWriter.write(JSON.toJSONString(ResultUtil.fail("修改失败！")));
					}

				} else {
					printWriter.write(JSON.toJSONString(ResultUtil.fail("组织信息不存在！")));
				}

			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.noAuthority("你没有该组织信息修改权限。")));
			}
		}else{
			printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id不能为空！")));
		}
	}

	/**
	 * 删除组织 逻辑删除
	 * @param resourceRequest
	 * @param resourceResponse
	 */
	private void deleteOrg(ResourceRequest resourceRequest,ResourceResponse resourceResponse) throws Exception {
		String id = ParamUtil.getString(resourceRequest, "id");
		String sessionId=resourceRequest.getRequestedSessionId();
		String userId =	SessionManager.getAttribute(sessionId, "userName").toString();
		PrintWriter printWriter = resourceResponse.getWriter();
		if(!StringUtils.isEmpty(id)){
			List<String> list = getPermissions(userId,Integer.parseInt(id));
			if(list.contains(DataOperationEnum.DELETE.getType())){
				Organization organization = orgService.findOrgById(Integer.parseInt(id));
				if (organization != null){
					List<Map<String,Object>> users = orgService.findAllUsers(organization);
					if(users.size()>0){
						printWriter.write(JSON.toJSONString(ResultUtil.fail("删除失败！请先删除该组织下的用户。")));
					}else{
						int rows = orgService.deleteOrg(Integer.parseInt(id));
						if(rows>0){
							printWriter.write(JSON.toJSONString(ResultUtil.success(null,"删除成功！")));
						}else{
							printWriter.write(JSON.toJSONString(ResultUtil.fail("删除失败！")));
						}
					}

				} else {
					printWriter.write(JSON.toJSONString(ResultUtil.fail("组织信息不存在！")));
				}

			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.noAuthority("你没有该组织删除权限。")));
			}
		}else{
			printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id不能为空！")));
		}
	}

	/**
	 * 查询用户userId对组织为id的组织的数据权限
	 * @param userId
	 * @param id
	 * @return
	 */
	public List<String> getPermissions(String userId,int id){
		List<String> permissionList = new ArrayList<>();
		if(userId!=null){
			Organization org = orgService.findOrgById(id);
			log.info("org:"+org);
			if(org == null ){
				return permissionList;
			}
			PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(org.getOrg_type());
			OrgAdmin orgAdmin = orgAdminService.findOrgAdmin(userId, PartyOrgAdminTypeEnum.ORGANIZATION);
			switch(partyOrgAdminTypeEnum){//党组织类型
				case ORGANIZATION:
					log.info("orgType:"+partyOrgAdminTypeEnum.getRole());
					if(orgAdmin != null && userId.equals(orgAdmin.getAdminId())){// 校组织管理员
						permissionList.add(DataOperationEnum.CREATE.getType());
						permissionList.add(DataOperationEnum.UPDATE.getType());
						permissionList.add(DataOperationEnum.READ.getType());
					}
					break;
				case SECONDARY:
					log.info("orgType:"+partyOrgAdminTypeEnum.getRole());
					if(orgAdmin != null && userId.equals(orgAdmin.getAdminId())){// 校组织管理员
						permissionList.add(DataOperationEnum.CREATE.getType());
						permissionList.add(DataOperationEnum.UPDATE.getType());
						permissionList.add(DataOperationEnum.DELETE.getType());
						permissionList.add(DataOperationEnum.READ.getType());
					}else{
						OrgAdmin secondaryAdmin = orgAdminService.findOrgAdmin(userId, org.getOrg_id());
						if(secondaryAdmin != null){// 二级党组织管理员
							permissionList.add(DataOperationEnum.CREATE.getType());
							permissionList.add(DataOperationEnum.UPDATE.getType());
							permissionList.add(DataOperationEnum.READ.getType());
						}
					}
					break;
				case BRANCH:
					log.info("orgType:"+partyOrgAdminTypeEnum.getRole());
					if(orgAdmin != null && userId.equals(orgAdmin.getAdminId())){//校组织管理员
						permissionList.add(DataOperationEnum.CREATE.getType());
						permissionList.add(DataOperationEnum.UPDATE.getType());
						permissionList.add(DataOperationEnum.DELETE.getType());
						permissionList.add(DataOperationEnum.READ.getType());
					}else{
						OrgAdmin secondaryAdmin = orgAdminService.findOrgAdmin(userId, org.getOrg_parent());
						if(secondaryAdmin != null){// 二级党组织管理员
							permissionList.add(DataOperationEnum.CREATE.getType());
							permissionList.add(DataOperationEnum.UPDATE.getType());
							permissionList.add(DataOperationEnum.DELETE.getType());
							permissionList.add(DataOperationEnum.READ.getType());
						}else{
							OrgAdmin branchAdmin = orgAdminService.findOrgAdmin(userId, org.getOrg_id());
							if(branchAdmin != null){// 支部管理员
								permissionList.add(DataOperationEnum.UPDATE.getType());
								permissionList.add(DataOperationEnum.READ.getType());
							}
						}
					}
					break;
			}
		}
		return permissionList;
	}


	
}
