package hg.party.server.organization;


import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.TreeNode;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;

import java.util.List;
import java.util.Map;

@Component(immediate = true, service = OrgService.class)
public class OrgService {
	@Reference
	private OrgDao orgDao;

	public List<Organization> findChildren(String parentId){
		return orgDao.findChildren(parentId);
	}

	public List<Organization> findAll(){
		return orgDao.findAll();
	}
	public Organization findOrgById(int id){
		return orgDao.findOrgById(id);
	}
	public Organization findOrgByPID(int id,String orgName){
		return orgDao.findOrgByPID(id,orgName);
	}
	public Organization findOrgByOrgId(String orgId){
		return orgDao.findOrgByOrgId(orgId);
	}

	public List<Organization> findOrgByOrgType(PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
		return orgDao.findOrgByOrgType(partyOrgAdminTypeEnum);
	}

	public int createOrg(Organization organization) {
		return orgDao.createOrg(organization);
	}
    public int updateOrg(Organization organization) {
		return orgDao.updateDetail(organization);
    }

	public int deleteOrg(int id) {
		return orgDao.deleteOrg(id);
	}

	public List<TreeNode> getTreeData(Organization organization) {
		return orgDao.getTreeData(organization);
	}

	public List<Map<String,Object>> findAllUsers(Organization organization) {
		return orgDao.findAllUsers(organization);
	}
	/**
	 *
	 * @param id 组织主键
	 * @return
	 */
	public List<Map<String,Object>> findOrgAdminUser(int id) {
		return orgDao.findOrgAdminUser(id);
	}

	/**
	 *查询用户管理的对应角色党组织
	 * @param userId
	 * @param partyOrgAdminTypeEnum
	 * @return
	 */
    public Organization findAdminOrg(String userId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
		return orgDao.findAdminOrg(userId,partyOrgAdminTypeEnum);
    }

	/**
	 * 通过组织查询对应组织的党员
	 * @param orgId
	 * @return
	 */
	public List<Map<String, Object>> findMembersByOrg(String orgId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
		return orgDao.findMembersByOrg(orgId,partyOrgAdminTypeEnum);
	}
}