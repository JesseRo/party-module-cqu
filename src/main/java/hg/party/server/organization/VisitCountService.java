package hg.party.server.organization;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.organization.VisitCountDao;
import hg.party.entity.organization.VisitCount;

@Component(immediate=true,service=VisitCountService.class)
public class VisitCountService {
	@Reference
	private VisitCountDao dao;

	public int save(VisitCount visitCount) {
		return dao.save(visitCount);

	}

	public String findOrgNameByOrgId(String id) {
		List<Map<String, Object>> list = dao.findOrgNameByOrgId(id);
		if (list != null && list.size() > 0) {
			String org_name = list.get(0).get("org_name").toString();
			return org_name;
		}
		return null;
	}

	public String findOrgNameByBranchId(String branchId) {
		List<Map<String, Object>> list = dao.findOrgNameByBranchId(branchId);
		if (list != null && list.size() > 0) {
			String org_name = list.get(0).get("org_name").toString();
			return org_name;
		}
		return null;
	}
   
}
