package hg.party.server.organization;


import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;

import java.util.List;

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

	public List<Organization> findOrgByOrgType(PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
		return orgDao.findOrgByOrgType(partyOrgAdminTypeEnum);
	}
}