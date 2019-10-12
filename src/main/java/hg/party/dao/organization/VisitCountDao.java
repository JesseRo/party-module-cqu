package hg.party.dao.organization;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.VisitCount;

@Component(immediate = true, service = VisitCountDao.class)
public class VisitCountDao extends PostgresqlDaoImpl<VisitCount> {

	public List<Map<String, Object>> findOrgNameByOrgId(String id) {
		String sql = " SELECT org_name from hg_party_org WHERE org_id=? and historic is false";
		return jdbcTemplate.queryForList(sql,id);
	}

	public List<Map<String, Object>> findOrgNameByBranchId(String branchId) {
		String sql = "SELECT org_name from hg_party_org WHERE historic is false and org_id=(SELECT org_id from hg_party_org WHERE historic is false and org_id='"
				+ branchId + "')";
		return jdbcTemplate.queryForList(sql);
	}
}
