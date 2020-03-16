package hg.party.server.organization;


import hg.party.dao.org.OrgAdminDao;

import hg.party.entity.party.OrgAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;

@Component(immediate = true, service = OrgAdminService.class)
public class OrgAdminService {
	@Reference
	private OrgAdminDao orgAdminDao;

	public OrgAdmin findOrgAdmin(String userId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum){
		return orgAdminDao.findOrgAdmin(userId,partyOrgAdminTypeEnum);
	}

	/**
	 * 根据org_id查询党组织管理人员
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public OrgAdmin findOrgAdmin(String userId, String orgId){
		return orgAdminDao.findOrgAdmin(userId,orgId);
	}
}