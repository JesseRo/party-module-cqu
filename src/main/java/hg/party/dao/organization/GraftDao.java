package hg.party.dao.organization;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.tree.RowMapper;

import org.apache.xmlbeans.SchemaStringEnumEntry;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.PublicInformation;

@Component(immediate = true, service = GraftDao.class)
public class GraftDao extends PostgresqlDaoImpl<PublicInformation> {
	public List<Map<String, Object>> findGrafts(Date date) {
		String sql = "SELECT * FROM hg_party_public_inform WHERE state='0' AND public_date>'2017-12-12'";
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> findGrafts(int public_status, Map<String, String> map, String orgType) {
		String sql = "SELECT * FROM hg_party_org_inform_info WHERE public_status='" + public_status + "'";
		StringBuffer buffer = new StringBuffer(sql);
		if (map != null && map.size() > 0) {
			if ("normal".equals(map.get("type"))) {
				String dateStr = map.get("date");
				String edateStr = map.get("edate");
				buffer.append(
						" and release_time>'" + dateStr + " 00:00:00' and release_time<'" + edateStr + " 24:00:00'");
			} else if ("more".equals(map.get("type"))) {
				String dateStr = map.get("date");
				buffer.append(" and release_time<'" + dateStr + " 00:00:00'");
			}

		}
		buffer.append(" and org_type='" + orgType + "' ORDER BY release_time desc");
		return jdbcTemplate.queryForList(buffer.toString());
	}

	public List<Map<String, Object>> findGrafts(int public_status, Map<String, String> map, int size, int startPage,
			String orgType) {
		String sql = "SELECT * FROM hg_party_org_inform_info WHERE public_status='" + public_status + "'";
		StringBuffer buffer = new StringBuffer(sql);
		if (map != null && map.size() > 0) {
			if ("normal".equals(map.get("type"))) {
				String dateStr = map.get("date");
				String edateStr = map.get("edate");
				buffer.append(
						" and release_time>'" + dateStr + " 00:00:00' and release_time<'" + edateStr + " 24:00:00'");
			} else if ("more".equals(map.get("type"))) {
				String dateStr = map.get("date");
				buffer.append(" and release_time<'" + dateStr + " 00:00:00'");
			}
		}
		buffer.append(" and org_type='" + orgType + "' ORDER BY release_time desc limit " + size + " offset "
				+ startPage + " ");
		return jdbcTemplate.queryForList(buffer.toString());
	}

	// 删除草稿
	public int deleteGraft(String resourceId) {
		String sql = "DELETE from hg_party_org_inform_info WHERE inform_id= ? ";
		return jdbcTemplate.update(sql, resourceId);
	}

	// 从草稿状态到发布状态
	public int updateGraft(String resourceId) {
		String sql = "UPDATE hg_party_org_inform_info set public_status='1' WHERE inform_id= ? ";
		return jdbcTemplate.update(sql, resourceId);
	}

	// 查询所有已经发布的通知
	public List<Map<String, Object>> findAlreadyPublic(int public_status, Map<String, String> map, String orgType) {
		String sql = "SELECT * FROM hg_party_org_inform_info WHERE public_status='" + public_status + "'";
		StringBuffer buffer = new StringBuffer(sql);
		if (map != null && map.size() > 0) {
			if ("normal".equals(map.get("type"))) {
				String dateStr = map.get("date");
				String edateStr = map.get("edate");
				buffer.append(
						" and release_time>'" + dateStr + " 00:00:00' and release_time<'" + edateStr + " 24:00:00'");
			} else if ("more".equals(map.get("type"))) {
				String dateStr = map.get("date");
				buffer.append(" and release_time<'" + dateStr + " 00:00:00'");
			}

		}
		buffer.append(" and org_type='" + orgType + "' ORDER BY release_time desc");
		return jdbcTemplate.queryForList(buffer.toString());
	}

	public List<Map<String, Object>> findAlreadyPublic(int public_status, Map<String, String> map, int size,
			int startPage, String orgType) {
		String sql = "SELECT * FROM hg_party_org_inform_info WHERE public_status='" + public_status + "'";
		StringBuffer buffer = new StringBuffer(sql);
		if (map != null && map.size() > 0) {
			if ("normal".equals(map.get("type"))) {
				String dateStr = map.get("date");
				String edateStr = map.get("edate");
				buffer.append(
						" and release_time>'" + dateStr + " 00:00:00' and release_time<'" + edateStr + " 24:00:00'");
			} else if ("more".equals(map.get("type"))) {
				String dateStr = map.get("date");
				buffer.append(" and release_time<'" + dateStr + " 00:00:00'");
			}

		}
		buffer.append(" and org_type='" + orgType + "' ORDER BY release_time desc limit " + size + " offset "
				+ startPage + " ");
		return jdbcTemplate.queryForList(buffer.toString());
	}

	//
	public List<Map<String, Object>> findGraftDetail(String informId) {
	//	String sql = "SELECT * from hg_party_org_inform_info WHERE inform_id='" + informId + "'";
		String sql2 = "SELECT info.*,att.attachment_name from hg_party_org_inform_info as info LEFT OUTER JOIN hg_party_attachment as att "
				+ "on info.inform_id=att.resource_id " 
				+ "where  inform_id= ? ";
		return jdbcTemplate.queryForList(sql2, informId);
	}

	public int deletePublicObject(String informId) {
		String sql = "delete from hg_party_inform_group_info where inform_id= ? ";
		return jdbcTemplate.update(sql, informId);
	}

	public List<Map<String, Object>> exportPublicExcel(String orgId, String publicState) {
		String sql = "select info.*,u.user_name FROM hg_party_org_inform_info as info,hg_users_info as u "
				+ "where info.public_status= ? and u.user_id=info.publisher and org_type= ? ";
		return jdbcTemplate.queryForList(sql, publicState, orgId);
	}

}
