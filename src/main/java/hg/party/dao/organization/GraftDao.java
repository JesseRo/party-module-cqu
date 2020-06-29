package hg.party.dao.organization;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import hg.party.entity.organization.Organization;
import hg.util.HgDateQueryUtil;
import hg.util.date.DateQueryVM;
import hg.util.postgres.HgPostgresqlDaoImpl;
import hg.util.postgres.PostgresqlPageResult;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import hg.party.entity.organization.PublicInformation;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.util.StringUtils;
import party.constants.DateQueryEnum;
import party.constants.PartyOrgAdminTypeEnum;
import party.portlet.transport.entity.PageQueryResult;

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
	public Map<String, Object> findGraftDetail(String informId) {
	//	String sql = "SELECT * from hg_party_org_inform_info WHERE inform_id='" + informId + "'";
		String sql2 = "SELECT info.*,att.attachment_name from hg_party_org_inform_info as info LEFT OUTER JOIN hg_party_attachment as att "
				+ "on info.inform_id=att.resource_id "
				+ "where  inform_id= ? ";
		List<Map<String, Object>>  list =jdbcTemplate.queryForList(sql2, informId);
		if(list.size()> 0 ){
			return list.get(0);
		}else{
			return null;
		}
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
		Organization org = findOrgByOrgId(orgId);
		PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(org.getOrg_type());
		if(PartyOrgAdminTypeEnum.BRANCH.getType().equals(partyOrgAdminTypeEnum.getType())){
			orgId = org.getOrg_parent();
		}
		StringBuffer sb = new StringBuffer("select * from (select inform_id,read_status from hg_party_inform_group_info where pub_org_id = '"+orgId+"') s left join (SELECT a.attachment_url,a.attachment_name,info.* FROM hg_party_org_inform_info info left join hg_party_attachment a on info.inform_id = a.resource_id) i on i.inform_id = s.inform_id WHERE i.public_status='" + publicStatus + "'");
		DateQueryVM dateQueryVM = HgDateQueryUtil.toDateQueryVM(DateQueryEnum.getEnum(dateType));
		if(!StringUtils.isEmpty(keyword)){
			String search = "%" + keyword + "%";
			sb.append(" and (i.meeting_theme like '"+search+"')");
		}
		if(dateQueryVM.getStartTime()!=null){
			sb.append(" and i.release_time>=?");
			if(dateQueryVM.getEndTime()!=null){
				sb.append(" and i.release_time<=?");
				sb.append(" ORDER BY i.release_time desc");
				return postGresqlFindPageBySql(page, size, sb.toString(),dateQueryVM.getStartTime(),dateQueryVM.getEndTime());
			}else{
				sb.append(" ORDER BY i.release_time desc");
				return postGresqlFindPageBySql(page, size, sb.toString(),dateQueryVM.getStartTime());
			}
		}else{
			sb.append(" ORDER BY i.release_time desc");
			return postGresqlFindPageBySql(page, size, sb.toString());
		}
	}
	public Organization findOrgByOrgId(String orgId) {
		String sql = "select * from hg_party_org where historic is false and org_id = ?";
		List<Organization> organizationList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class), orgId);
		if (organizationList.size() > 0) {
			return organizationList.get(0);
		} else {
			return null;
		}
	}
	public PageQueryResult<Map<String, Object>> findSecondaryPage(int page, int size, String orgId) {
		String sql = "select o.* from hg_party_inform_group_info t " +
				" left join hg_party_org_inform_info o on t.inform_id = o.inform_id" +
				" where t.pub_org_id = ? and o.public_status = '1' order by o.id desc";
		if (size <= 0) {
			size = 10;
		}
		try {
			return pageBySql(page, size, sql, orgId);
		} catch (Exception e) {
			return null;
		}
	}


	public PageQueryResult<Map<String, Object>> pageBySql(int pageNow, int pageSize, String sql, Object... object) {
		if (pageNow <= 0) {
			pageNow = 0;
		} else {
			--pageNow;
		}

		List<Map<String, Object>> list = this.listBySql(pageNow, pageSize, sql, object);
		int count = this.postGresql_countBySql(sql, object);
		return new PageQueryResult(list, count, pageNow, pageSize);
	}

	private List<Map<String, Object>> listBySql(int pageNo, int pageSize, String sql, Object... objects) {
		StringBuffer exeSql = new StringBuffer();
		exeSql.append(sql);
		exeSql.append(" LIMIT ? OFFSET ? ");
		if (objects != null && objects.length != 0) {
			List list = Arrays.stream(objects).collect(Collectors.toList());
			list.add(pageSize);
			list.add(pageNo * pageSize);
			return this.jdbcTemplate.queryForList(exeSql.toString(), list.toArray(new Object[0]));
		} else {
			return this.jdbcTemplate.queryForList(exeSql.toString(), new Object[]{pageSize, pageNo * pageSize});
		}
	}

	public PostgresqlPageResult<Map<String, Object>> searchSendInformPage(int page, int size,String dateType, String orgId, String keyword) {
		if (size <= 0){
			size = 10;
		}
		StringBuffer sb = new StringBuffer("SELECT a.attachment_url,a.attachment_name,i.* FROM hg_party_org_inform_info i left join hg_party_attachment a on i.inform_id = a.resource_id WHERE 1=1 and i.org_type=? and i.public_status='1'");
		DateQueryVM dateQueryVM = HgDateQueryUtil.toDateQueryVM(DateQueryEnum.getEnum(dateType));
		if(!StringUtils.isEmpty(keyword)){
			String search = "%" + keyword + "%";
			sb.append(" and (i.meeting_theme like '"+search+"')");
		}
		if(dateQueryVM.getStartTime()!=null){
			sb.append(" and i.release_time>=?");
			if(dateQueryVM.getEndTime()!=null){
				sb.append(" and i.release_time<=?");
				sb.append(" ORDER BY i.release_time desc");
				return postGresqlFindPageBySql(page, size, sb.toString(),orgId,dateQueryVM.getStartTime(),dateQueryVM.getEndTime());
			}else{
				sb.append(" ORDER BY i.release_time desc");
				return postGresqlFindPageBySql(page, size, sb.toString(),orgId,dateQueryVM.getStartTime());
			}
		}else{
			sb.append(" ORDER BY i.release_time desc");
			return postGresqlFindPageBySql(page, size, sb.toString(),orgId);
		}
	}
}
