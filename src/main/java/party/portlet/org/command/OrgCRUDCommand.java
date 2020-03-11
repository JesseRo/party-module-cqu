package party.portlet.org.command;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.alibaba.fastjson.serializer.JSONSerializer;
import hg.party.entity.organization.Organization;
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
import hg.party.dao.org.OrgDao;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import party.constants.DataOperationEnum;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.OrgAdmin,
				"mvc.command.name=/org/admin/manage"
	    },
	    service = MVCResourceCommand.class
)
public class OrgCRUDCommand implements MVCResourceCommand{
	 Logger log = Logger.getLogger(OrgCRUDCommand.class);
	@Reference
	private OrgDao orgDao;
	@Reference
	private OrgService orgService;
	@Reference
	TransactionUtil transactionUtil;
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		// data:{orgName:orgName,org_type:org_type,orgId:orgId,option:"post"},
		Object userId =SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");
		Map<String, Object> map = new HashMap<>();
		String option = ParamUtil.getString(resourceRequest, "option");
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		String org_type = ParamUtil.getString(resourceRequest, "org_type");
		String orgName = ParamUtil.getString(resourceRequest, "orgName");
		String secondaryType = ParamUtil.getString(resourceRequest, "secondaryType");
		String uuid = UUID.randomUUID().toString();
		String type = "";
		int n = 0;
		DataOperationEnum dataOperationEnum = DataOperationEnum.getEnum(option);
		switch(dataOperationEnum){
			case READ:findOrg(resourceRequest,resourceResponse);break;
			case UPDATE:updateOrg(resourceRequest,resourceResponse);break;
			case CREATE:addOrg(resourceRequest,resourceResponse);break;
			case DELETE:deleteOrg(resourceRequest,resourceResponse);break;
		}
		try {
			transactionUtil.startTransaction();
			if ("post".equals(option)) {
				if (ConstantsKey.ORG_TYPE_SECONDARY.equals(org_type)) {
					type = ConstantsKey.ORG_TYPE_BRANCH;
				} else if (ConstantsKey.ORG_TYPE_ROOT.equals(org_type)) {
					type = ConstantsKey.ORG_TYPE_SECONDARY;
				}

				boolean b = isDoubleName(orgId, orgName);
				if (!b) {
					String code = getCode(orgId);
					if(StringUtils.isEmpty(secondaryType)){
						secondaryType = null;
					}
					String sql = "INSERT INTO hg_party_org (\"org_id\", \"org_name\",\"org_type\", \"org_parent\", \"historic\", \"org_code\", \"secondary_type\") VALUES ('"
							+ uuid + "', '" + orgName + "', '" + type + "','" + orgId + "', '" + false + "', '" + code+ "', '" + secondaryType
							+ "')";
					n = orgDao.insert(sql);
					map.put("state", "ok");
					map.put("message", "添加成功");
					map.put("uuid", uuid);
					map.put("type", type);
					log.info("添加组织 :["+new Date()+"] [by "+userId+"]  orgId :["+orgId+"]");	
					transactionUtil.commit();
				} else {
					map.put("state", "fail");
					map.put("message", "添加失败，该名字已经存在！");
					transactionUtil.rollback();
				}
			} else if ("edit".equals(option)) {

				if (ConstantsKey.ORG_TYPE_SECONDARY.equals(org_type) || ConstantsKey.ORG_TYPE_ROOT.equals(org_type)) {
					int k = Integer.parseInt(orgDao.findOrgName(org_type, orgName).get(0).get("count").toString());
					if (k < 1) {
						String sql = "update hg_party_org set org_name ='" + orgName + "' where org_id='" + orgId + "'";
						n = orgDao.insert(sql);
						map.put("state", "ok");
						map.put("message", "修改成功");
						log.info("修改组织 :["+new Date()+"] [by "+userId+"]  orgId :["+orgId+"]");	
						transactionUtil.commit();
					} else {
						map.put("state", "fail");
						map.put("message", "修改失败，该名字已经存在！");
						transactionUtil.rollback();
					}
				} else if (ConstantsKey.ORG_TYPE_BRANCH.equals(org_type)) {
					int k = Integer
							.parseInt(orgDao.findOrgName(org_type, orgName, orgId).get(0).get("count").toString());
					if (k < 1) {
						String sql = "update hg_party_org set org_name ='" + orgName + "' where org_id='" + orgId + "'";
						n = orgDao.insert(sql);
						map.put("state", "ok");
						map.put("message", "修改成功");
						log.info("修改组织 :["+new Date()+"] [by "+userId+"]  orgId :["+orgId+"]");		
						transactionUtil.commit();
					} else {
						map.put("state", "fail");
						map.put("message", "修改失败，该名字已经存在！");
						transactionUtil.rollback();
					}
				}
			} else if ("delete".equals(option)) {
				if (ConstantsKey.ORG_TYPE_BRANCH.equals(org_type)) {
					int k = orgDao.findBranchPersonCount(orgId);
					if (k < 1) {
						String sql = "UPDATE hg_party_org set historic = true where org_id = '" + orgId + "'";
						n = orgDao.insert(sql);
						map.put("state", "ok");
						map.put("message", "删除成功");
						log.info("删除组织 :["+new Date()+"] [by "+userId+"]  orgId :["+orgId+"]");				
						transactionUtil.commit();
					} else {
						map.put("state", "fail");
						map.put("message", "删除失败，请先删除人员");
						transactionUtil.rollback();
					}
				} else if (ConstantsKey.ORG_TYPE_SECONDARY.equals(org_type)
						|| ConstantsKey.ORG_TYPE_ROOT.equals(org_type)) {
					int k = orgDao.findCount(orgId);
					if (k < 1) {
						String sql = "UPDATE hg_party_org set historic = true where org_id = '" + orgId + "'";
						n = orgDao.insert(sql);
						map.put("state", "ok");
						map.put("message", "删除成功");
						log.info("删除组织 :["+new Date()+"] [by "+userId+"]  orgId :["+orgId+"]");	
						transactionUtil.commit();
					} else {
						map.put("state", "fail");
						map.put("message", "删除失败，请先删除下属部门");
						transactionUtil.rollback();
					}
				}
			}
			// transactionUtil.commit();
			PrintWriter printWriter = resourceResponse.getWriter();
			printWriter.write(JSON.toJSONString(map));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			transactionUtil.rollback();
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查询组织
	 * @param resourceRequest
	 * @param resourceResponse
	 */
	private void findOrg(ResourceRequest resourceRequest,ResourceResponse resourceResponse){
		String id = ParamUtil.getString(resourceRequest, "id");
		try{
			PrintWriter printWriter = resourceResponse.getWriter();
			if(!StringUtils.isEmpty(id)){
				Organization organization = orgService.findOrgById(Integer.parseInt(id));
				printWriter.write(JSON.toJSONString(ResultUtil.success(organization)));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id不能为空！")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加组织
	 * @param resourceRequest
	 * @param resourceResponse
	 */
	private void addOrg(ResourceRequest resourceRequest,ResourceResponse resourceResponse){
		String id = ParamUtil.getString(resourceRequest, "id");
		try{
			PrintWriter printWriter = resourceResponse.getWriter();
			if(!StringUtils.isEmpty(id)){
				log.info("id:"+id);
				Organization organization = orgService.findOrgById(Integer.parseInt(id));
				printWriter.write(JSON.toJSONString(ResultUtil.success(organization)));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id不能为空！")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新组织
	 * @param resourceRequest
	 * @param resourceResponse
	 */
	private void updateOrg(ResourceRequest resourceRequest,ResourceResponse resourceResponse){
		String id = ParamUtil.getString(resourceRequest, "id");
		try{
			PrintWriter printWriter = resourceResponse.getWriter();
			if(!StringUtils.isEmpty(id)){
				log.info("id:"+id);
				Organization organization = orgService.findOrgById(Integer.getInteger(id));
				printWriter.write(JSON.toJSONString(ResultUtil.success(organization)));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id不能为空！")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除组织 逻辑删除
	 * @param resourceRequest
	 * @param resourceResponse
	 */
	private void deleteOrg(ResourceRequest resourceRequest,ResourceResponse resourceResponse){
		String id = ParamUtil.getString(resourceRequest, "id");
		try{
			PrintWriter printWriter = resourceResponse.getWriter();
			if(!StringUtils.isEmpty(id)){
				log.info("id:"+id);
				Organization organization = orgService.findOrgById(Integer.getInteger(id));
				printWriter.write(JSON.toJSONString(ResultUtil.success(organization)));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("组织id不能为空！")));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否重名
	 * 
	 * @param parentId
	 * @param orgName
	 * @return
	 */
	public boolean isDoubleName(String parentId, String orgName) {
		List<Map<String, Object>> list = orgDao.findOrgNameByName(parentId, orgName);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取组织代码
	 * 
	 * @param parentId
	 * @return
	 */
	public String getCode(String parentId) {
		Format f1 = new DecimalFormat("000");
		List<Map<String, Object>> list = orgDao.findCode(parentId);
		System.out.println(list.size());
		if (list != null && list.size() > 0 && !StringUtils.isEmpty(list.get(0).get("code"))) {
			int code = Integer.parseInt(list.get(0).get("code").toString());
			String cString = f1.format(code + 1);
			return cString;
		} else {
			list = orgDao.findScondeCode(parentId);
			int code = Integer.parseInt(list.get(0).get("code").toString());
			String cString = f1.format(code);
			return cString + "001";
		}

	}
	
}
