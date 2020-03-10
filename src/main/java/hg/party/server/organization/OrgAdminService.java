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
}