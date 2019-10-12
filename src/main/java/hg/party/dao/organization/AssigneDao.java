package hg.party.dao.organization;

import java.util.List;
import java.util.Map;
import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.Assign;

@Component(immediate = true, service = AssigneDao.class)
public class AssigneDao extends PostgresqlDaoImpl<Assign> {
	// 查询 人员信息

	public List<Map<String, Object>> findPersonInfromation(String userNmae) {

		//String sql = "select * from hg_users_info where user_name LIKE '%" + userNmae + "%' or user_id='" + userNmae
		//		+ "'";
		String sql ="select u.*,org.org_name from hg_users_info as u "+
					"LEFT OUTER JOIN hg_party_org as org on u.user_department_id=org.org_id "+
					"where u.user_name LIKE ? or u.user_id= ? ";
		return jdbcTemplate.queryForList(sql,"%"+userNmae+"%",userNmae);
	}

	public int delete(String id) {
		String sql = "DELETE from hg_party_assigne WHERE assigne_user_id= ? ";
		return jdbcTemplate.update(sql, id);
	}

	// 查询指派人员是否存在
	public List<Map<String, Object>> find(String userId,String orgId) {
		String sql = "select * FROM hg_party_assigne WHERE assigne_user_id= ? and department_name= ? ";
		return jdbcTemplate.queryForList(sql, userId,orgId);
	}

	public List<Map<String, Object>> findBranchByOrgParentId(String parntId) {
		String sql = "select * from hg_party_org WHERE org_parent= ? and historic is false ";
		return jdbcTemplate.queryForList(sql, parntId);
	}
	public static void main(String[] args) {
		AssigneDao dao = new AssigneDao();
		System.out.println(dao.findPersonInfromation("张"));
	}
}
