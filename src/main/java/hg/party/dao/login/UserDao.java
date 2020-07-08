package hg.party.dao.login;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.login.User;
import hg.util.ConstantsKey;

@Component(immediate = true, service = UserDao.class)
public class UserDao extends PostgresqlDaoImpl<User> {
	public List<Map<String, Object>> findAllByUserId(String id) {
		String sql = "SELECT u.id as _id, * " + "FROM "
				+ "hg_users_info u LEFT JOIN hg_party_member member on u.user_id = member.member_identity "
				+ "WHERE (member.historic = false or member.historic is null) and u.state = '1' and " + "u.user_id=? ";

		return this.jdbcTemplate.queryForList(sql, id);
	}

	public List<Map<String, Object>> findRoleByUserID(String userID) {
		String sql = "SELECT userrole FROM hg_users_info WHERE user_id=? ";
		return jdbcTemplate.queryForList(sql, userID);

	}

	public int insertLogin(String userId, String userName) {
		String sql = "INSERT INTO hg_login (\"user_id\", \"user_name\") VALUES ('" + userId + "', '" + userName + "')";
		return jdbcTemplate.update(sql);

	}
	public int update(String userId, String moveToOrgId) {
		String sql = "update hg_users_info set user_department_id= ? where user_id=? ";
		return jdbcTemplate.update(sql,moveToOrgId,userId);

	}
	public List<Map<String, Object>> findLogin(String userId) {
		String sql = "select * from hg_login where user_id=? ";
		return jdbcTemplate.queryForList(sql, userId);

	}

	public boolean exist(String userId) {
		String sql = "select count(*) from hg_users_info where user_id=? ";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
		return count > 0;
	}

	public void logicDelete(String userID) {
		String sql = "update hg_users_info set state = '0' where user_id = ?";
		jdbcTemplate.update(sql, userID);
	}

	/**
	 * 根据公众号查询用户信息
	 *
	 * @param ethnicity
	 * @return
	 */
	public User findUserByEthnicity(String ethnicity) {
		List<Object> args = new ArrayList<Object>();
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM hg_users_info WHERE user_id= ? ";
		args.add(ethnicity);
		RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
		users = jdbcTemplate.query(sql, rowMapper, args.toArray());
		if(users.size()>0){
			return users.get(0);
		}else{
			return null;
		}
	}

	// 根据 角色查询和账号查询 orgId;
	public String findOrgId(String role, String userId, String depId) {
		String sql = "select * from hg_party_org where org_id=? and historic is false";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, depId);
		if (list.size() > 0) {
			if (list.get(0).get("org_type").toString().equals("secondary")) {
				if (role.equals(ConstantsKey.ORG_PARTY)) {
					String org_id = list.get(0).get("org_parent").toString();
					return org_id;
				} else {
					return depId;
				}
			} /*
				 * else if
				 * (list.get(0).get("org_type").toString().equals("branch")) {
				 * if (role.equals(ConstantsKey.SECOND_PARTY)) { String org_id =
				 * list.get(0).get("org_parent").toString(); return org_id; }
				 * else { return depId; } }
				 */else if (list.get(0).get("org_type").toString().equals("branch")) {
				if (role.equals(ConstantsKey.ORG_PARTY)) {
					String org_id = list.get(0).get("org_parent").toString();
					List<Map<String, Object>> list1 = jdbcTemplate
							.queryForList("select * from hg_party_org where org_id=? and historic is false", org_id);
					if (list1 != null && list1.size() > 0) {
						org_id = list1.get(0).get("org_parent").toString();
					}
					return org_id;
				} else if (role.equals(ConstantsKey.SECOND_PARTY)) {
					String org_id = list.get(0).get("org_parent").toString();
					return org_id;
				} else {
					return depId;
				}
			}
		}
		return depId;
	}

    public int updateUserInfo(User user) {
		String sql = "UPDATE hg_users_info SET user_id=?,user_mailbox=?,user_sex=?,user_department_id=? WHERE id=?";
		return  this.jdbcTemplate.update(sql,user.getUser_id(),user.getUser_mailbox(),user.getUser_sex(),user.getUser_department_id(),user.getId());
    }
}
