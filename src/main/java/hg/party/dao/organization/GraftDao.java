package hg.party.dao.organization;

import java.util.Date;
import java.util.List;
import java.util.Map;
import hg.util.HgDateQueryUtil;
import hg.util.date.DateQueryVM;
import hg.util.postgres.HgPostgresqlDaoImpl;
import hg.util.postgres.PostgresqlPageResult;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;

import hg.party.entity.organization.PublicInformation;
import org.springframework.util.StringUtils;
import party.constants.DateQueryEnum;

@Component(immediate = true, service = GraftDao.class)
public class GraftDao extends HgPostgresqlDaoImpl<PublicInformation> {
	Logger logger = Logger.getLogger(GraftDao.class);

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

	/**
	 * 分页查询活动通知
	 * @param page
	 * @param size
	 * @param dateType
	 * @param orgId
	 * @param publicStatus
	 * @param keyword
	 * @return
	 */
	public PostgresqlPageResult<Map<String, Object>> searchPage(int page, int size, String dateType,String orgId,int publicStatus, String keyword) {
		if (size <= 0){
			size = 10;
		}
		StringBuffer sb = new StringBuffer("SELECT info.* FROM hg_party_org_inform_info info WHERE info.public_status='" + publicStatus + "'");
		DateQueryVM dateQueryVM = HgDateQueryUtil.toDateQueryVM(DateQueryEnum.getEnum(dateType));
		if(!StringUtils.isEmpty(keyword)){
			String search = "%" + keyword + "%";
			sb.append(" and (info.meeting_theme like '"+search+"')");
		}
		if(!StringUtils.isEmpty(orgId)){
			sb.append(" and info.org_type = '"+orgId+"'");
		}
		if(dateQueryVM.getStartTime()!=null){
			sb.append(" and info.release_time>=?");
			if(dateQueryVM.getEndTime()!=null){
				sb.append(" and info.release_time<=?");
				sb.append(" ORDER BY info.release_time desc");
				return postGresqlFindPageBySql(page, size, sb.toString(),dateQueryVM.getStartTime(),dateQueryVM.getEndTime());
			}else{
				sb.append(" ORDER BY info.release_time desc");
				return postGresqlFindPageBySql(page, size, sb.toString(),dateQueryVM.getStartTime());
			}
		}else{
			sb.append(" ORDER BY info.release_time desc");
			return postGresqlFindPageBySql(page, size, sb.toString());
		}
	}
}
