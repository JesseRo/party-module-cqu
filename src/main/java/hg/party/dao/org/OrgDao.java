package hg.party.dao.org;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.login.User;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component(immediate = true,service = OrgDao.class)
public class OrgDao extends PostgresqlDaoImpl<Organization>{
	
	public List<Organization> findAll() {
		String sql = "select * from hg_party_org where historic is false";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}

	public int deleteAdmin(String userId) {
		String sql = "DELETE from hg_party_org_admin where admin_id= ? ";
		return jdbcTemplate.update(sql,userId);
	}
	
	public List<Organization> findAllHistoric() {
		String sql = "select * from hg_party_org";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	
	public List<Organization> findHistoricSecondary() {
		String sql = "select * from hg_party_org where org_type = 'secondary' ";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	public List<Organization> findSecondary() {
		String sql = "select * from hg_party_org where org_type = 'secondary' and historic is false";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	public List<Organization> findSecondary(String orgId) {
		String sql = "select * from hg_party_org where org_type = 'secondary' and historic is false and org_id='"+orgId+"'";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	public List<Organization> findbranchSecondary(String orgId) {
		String sql = "select * from hg_party_org where org_type = 'secondary' and historic is false "+
                     " and org_id=(select org_parent from hg_party_org where org_id='"+orgId+"')";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	public List<Organization> findHistoricBranch() {
		String sql = "select * from hg_party_org where org_type = 'branch' ";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	public List<Organization> findBranch() {
		String sql = "select * from hg_party_org where org_type = 'branch' and historic is false";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	
	public List<Organization> findBranch(String orgId) {
		String sql = "select * from hg_party_org where org_type = 'branch' and historic is false and org_id='"+orgId+"'";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	
	public List<Organization> findsecodBranch(String orgId) {
		String sql = "select * from hg_party_org where org_type = 'branch' and historic is false and org_parent='"+orgId+"'";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
	}
	public List<Organization> findBranchBySecondary(String secondary){
		String sql = "select * from hg_party_org where org_parent = '" + secondary + "' and historic is false";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));	
	}
	
	public Organization findByOrgId(String orgId) {
		String sql = "select * from hg_party_org where org_id = '" + orgId +"'";
		try {
			return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Organization.class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public void updateAll(List<Organization> orgs) {
		if (orgs == null || orgs.size() == 0) {
			return;
		}
		StringBuilder sqlBuilder = new StringBuilder();
		for(Organization org : orgs){
			sqlBuilder.append("update hg_party_org set org_name = '").append(org.getOrg_name()).append( "'")
					  .append(" where id=").append(org.getId()).append(";\n");				
		}
		jdbcTemplate.execute(sqlBuilder.toString());
	}
	
	public void historicAll(List<Organization> orgs) {
		if (orgs == null || orgs.size() == 0) {
			return;
		}
		StringBuilder sqlBuilder = new StringBuilder();
		for(Organization org : orgs){
			sqlBuilder.append("update hg_party_org set historic = ").append(true)
					  .append(" where id=").append(org.getId()).append(";\n");
							
		}
		jdbcTemplate.execute(sqlBuilder.toString());
	}
 
	public void insertAll(List<Organization> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		Field[] fields = Organization.class.getDeclaredFields();
		
		List<Field> fieldList = Stream.of(fields)
				.peek(p-> p.setAccessible(true))
				.filter(p->!(p.getName().equalsIgnoreCase("historic") || p.getName().equalsIgnoreCase("id")))
				.collect(Collectors.toList());
		List<String> names = fieldList.stream().map(Field::getName).collect(Collectors.toList());

		StringBuilder sql = new StringBuilder();
		
		sql.append("insert into hg_party_org (\"").append(String.join("\", \"", names)).append("\") values ");
		
		List<String> sqList = list.stream().map(p->{
			StringBuilder vStringBuilder = new StringBuilder();
			vStringBuilder.append("(");
			List<String> values = new ArrayList<>();
			for (Field field : fieldList) {
				Object vObject;
				try {
					vObject = field.get(p);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
					vObject = null;
				}
				if (vObject == null) {
					values.add("null");
				}else {
					values.add("'" + vObject.toString() + "'");
				}
			}
			vStringBuilder.append(String.join(",", values)).append(")");
			return vStringBuilder.toString();
		}).collect(Collectors.toList());
		sql.append(String.join(",", sqList)).append(";");
		jdbcTemplate.execute(sql.toString());
	}
	
	
	/*根据org_id获取org_type
	 * XZG
	 */
	public Map<String,Object> findOrgType(String orgId){
		String sql="SELECT org_type FROM hg_party_org WHERE org_id='" + orgId + "'";
		return this.jdbcTemplate.queryForMap(sql);
	}
	
	public List<Organization> findTree(String pid, boolean recursion, boolean historic) {
		String sql;
		if (recursion) {
			sql = "WITH RECURSIVE T (\n"+
					"\tID,\n"+
					"\torg_id,\n"+
					"\torg_type,\n"+
					"\torg_name,\n"+
					"\torg_secretary,\n"+
					"\torg_contactor,\n"+
					"\torg_phone_number,\n"+
					"\torg_unit_situation,\n"+
					"\torg_unit_name,\n"+
					"\torg_unit_type,\n"+
					"\torg_unit_party_situation,\n"+
					"\torg_unit_code,\n"+
		            "\torg_relation,\n"+
		            "\torg_parent,\n"+
		            "\torg_code,\n"+
		            "\thistoric\n"+
		            ") AS (\n"+
		            "\tSELECT\n"+
		            "\t\t\"public\".hg_party_org.\"id\",\n"+
		            "\t\t\"public\".hg_party_org.org_id,\n"+
		            "\t\t\"public\".hg_party_org.org_type,\n"+
		            "\t\t\"public\".hg_party_org.org_name,\n"+
		            "\t\t\"public\".hg_party_org.org_secretary,\n"+
		            "\t\t\"public\".hg_party_org.org_contactor,\n"+
		            "\t\t\"public\".hg_party_org.org_phone_number,\n"+
		            "\t\t\"public\".hg_party_org.org_unit_situation,\n"+
		            "\t\t\"public\".hg_party_org.org_unit_name,\n"+
		            "\t\t\"public\".hg_party_org.org_unit_type,\n"+
		            "\t\t\"public\".hg_party_org.org_unit_party_situation,\n"+
		            "\t\t\"public\".hg_party_org.org_unit_code,\n"+
		            "\t\t\"public\".hg_party_org.org_relation,\n"+
		            "\t\t\"public\".hg_party_org.org_parent,\n"+
		            "\t\t\"public\".hg_party_org.org_code,\n"+
		            "\t\t\"public\".hg_party_org.historic\n"+
		            "\tFROM\n"+
		            "\t\thg_party_org\n"+
		            "\tWHERE\n"+
		            "\t\torg_id = '" + pid + "'\n"+
		            "\tUNION ALL\n"+
		            "\t\tSELECT\n"+
		            "\t\t\tD. ID,\n"+
		            "\t\t\tD.org_id,\n"+
		            "\t\t\tD.org_type,\n"+
		            "\t\t\tD.org_name,\n"+
		            "\t\t\tD.org_secretary,\n"+
		            "\t\t\tD.org_contactor,\n"+
		            "\t\t\tD.org_phone_number,\n"+
		            "\t\t\tD.org_unit_situation,\n"+
		            "\t\t\tD.org_unit_name,\n"+
		            "\t\t\tD.org_unit_type,\n"+
		            "\t\t\tD.org_unit_party_situation,\n"+
		            "\t\t\tD.org_unit_code,\n"+
		            "\t\t\tD.org_relation,\n"+
		            "\t\t\tD.org_parent,\n"+
		            "\t\t\tD.org_code,\n"+
		            "\t\t\tD.historic\n"+
		            "\t\tFROM\n"+
		            "\t\t\thg_party_org D\n"+
		            "\t\tJOIN T ON D.org_parent = T .org_id\n"+
		            ") SELECT\n"+
		            "\t*\n"+
		            "FROM\n"+
		            "\tT";
			if(!historic){
				sql += " where T.historic = false order by T.org_code";
			}
		}else {
			sql = "select * from hg_party_org where (org_parent = '" + pid + "' or org_id = '" + pid + "') and historic = false order by org_code";
		}
		System.out.println(sql);
		try {
			return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(Organization.class));
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	public Organization findRoot() {
		String sql = "select * from hg_party_org where org_type = 'organization'";
		try {
			return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Organization.class));
		} catch (Exception e) {
			return null;
		}
	}
	public Organization findRoot(String orgId,String orgType) {
		String sql = "select * from hg_party_org where org_type = '"+orgType+"' and org_id ='"+orgId+"'";
		try {
			return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Organization.class));
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean changeAdmin(String org, String... admin){
		Organization organization = findByOrgId(org);
		if (organization == null) {
			return false;
		}else{
			deleteOrgAdmin(org);
			if (admin == null || admin.length == 0){
				return true;
			}
			return saveAdmin(organization, admin);
		}
	}
	
	public void deleteOrgAdmin(String org){
		String sql = "delete from hg_party_org_admin where org_id = ? ";
		jdbcTemplate.update(sql,org);
	}

	public List<String> findAdminPhoneNumberIn(List<String > orgs){
		if (orgs != null && orgs.size() > 0){
			String in = String.join("','", orgs);
			String sql = String.format("select m.member_phone_number from hg_party_org_admin ad inner join hg_party_member m on " +
					"ad.admin_id = m.member_identity and m.historic is false " +
					"where org_id in ('%s')", in);
			return jdbcTemplate.queryForList(sql, String.class);
		}else{
			return null;
		}

	}

	public boolean saveAdmin(String org, String... admin){
		Organization organization = findByOrgId(org);
		if (organization == null) {
			return false;
		}else{
			return saveAdmin(organization, admin);
		}
	}
	
	public boolean saveAdmin(Organization org, String... admin) {
		String recordSql = Stream.of(admin).map(p->org.getOrg_id() +  "','" + p + "','" + org.getOrg_type()).collect(Collectors.joining("'),('"));
		String sql = "insert into hg_party_org_admin (\"org_id\",\"admin_id\",\"org_type\") "
				+ "values ('" + recordSql + "')";
		try{
			jdbcTemplate.execute(sql);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public List<String> findRoleByUserId(String userId) {
	//	String sql = "select org_type from hg_party_org_admin where admin_id = ?";
		String sql="select adm.org_type from hg_party_org_admin as adm join hg_party_org as org on adm.org_id=org.org_id "+
                   "where adm.admin_id = ? AND org.historic=false";
		try{
			return jdbcTemplate.queryForList(sql, String.class, userId);
		}catch (Exception e) {
			return new ArrayList<>();
		}
	}

	public List<String> findAdminByOrg(String orgId){
		String sql = "select admin_id from hg_party_org_admin where org_id = ? ";
		try{
			return jdbcTemplate.queryForList(sql, String.class,orgId);
		}catch (Exception e) {
			return new ArrayList<>();
		}
	}
	//导航消息提醒
	public List<Map<String, Object>> NavigationMessage(String sql){
		return jdbcTemplate.queryForList(sql);
	}
	//删除人员
	public  int deletePersonByuserId(String userId){
		String sql  = "update hg_party_member set historic = true "+
                      " where member_identity = ? "+
                      " and historic is false ";
		
		return jdbcTemplate.update(sql,userId);
	}
	public  int deleteUserByuserId(String userId){
		String sql  = "delete from hg_users_info where user_id = ? ";
		return jdbcTemplate.update(sql,userId);
	}
	public  List<Map<String, Object>> findPersonByuserId(String userId){
		String sql  = "select * from hg_party_member "+ 
				      " where member_identity = ? "+
				      " and historic is false ";
		return jdbcTemplate.queryForList(sql,userId);
	}
	public  List<Map<String, Object>> findOrgNameByName(String parentId,String orgName){
		String sql  = "select * from hg_party_org where org_name  =? and org_parent = ? and historic is false  ";
		return jdbcTemplate.queryForList(sql,orgName,parentId);
	}
	public  List<Map<String, Object>> findOrgName(String orgType,String orgName){
		String sql  = "select count(*) as count  from hg_party_org where org_type= ? and org_name= ? and historic is false ";
		return jdbcTemplate.queryForList(sql,orgType,orgName);
	}
	public  List<Map<String, Object>> findOrgName(String orgType,String orgName,String orgId){
		String sql  = "select count(*) as count  from hg_party_org "+
					  " where org_type= ? and historic is false "+
					  " and org_name= ? "+
					  " and org_parent =(select org_parent  from hg_party_org where org_id=?) ";
		return jdbcTemplate.queryForList(sql,orgType,orgName,orgId);
	}
	public  List<Map<String, Object>> findCode(String parentId){
		String sql  = "select max(org_code) as code from hg_party_org where org_parent = ? ";
		return jdbcTemplate.queryForList(sql,parentId);
	}
	
	public  List<Map<String, Object>> findScondeCode(String orgId){
		String sql  = "select org_code as code from hg_party_org where org_id=? ";
		return jdbcTemplate.queryForList(sql,orgId);
	}
	public  List<Map<String, Object>> findOrgName(String orgId){
		String sql  = "select * from hg_party_org where org_id= ? ";
		return jdbcTemplate.queryForList(sql,orgId);
	}
	public  List<Map<String, Object>> findSecondOrgName(String orgId){
		String sql  = "select * from hg_party_org where org_id= (select org_parent from hg_party_org where org_id = ? )";
		return jdbcTemplate.queryForList(sql,orgId);
	}
	public  int insert(String sql){
		
		return jdbcTemplate.update(sql);
	}
	/**
	 *查询支部下面是否还有人员
	 */
	
	public int  findBranchPersonCount(String orgId){
		String sql  = "select count(*) as count from hg_party_member where historic is false and member_org = ? ";
		List<Map<String, Object>> list =jdbcTemplate.queryForList(sql,orgId);
		return Integer.parseInt(list.get(0).get("count").toString());
	}
	/**
	 * 查询二级组织下面是否还有人员
	 */
	public int  findCount(String orgId){
		String sql  = "select count(*) as count from hg_party_org where historic is false and org_parent = ? ";
		List<Map<String, Object>> list =jdbcTemplate.queryForList(sql,orgId);
		return Integer.parseInt(list.get(0).get("count").toString());
	}
	public List<Map<String, Object>>  findUserExist(String userId){
		String sql  = "select * from hg_party_member where member_identity = ? and historic is FALSE ";
		
		return jdbcTemplate.queryForList(sql,userId);
	}
	public List<Map<String, Object>>  findUserExist(String userId,String orgId){
		String sql  = "select * from hg_party_member where member_identity = ? and historic is FALSE and member_org = ? ";
		
		return jdbcTemplate.queryForList(sql,userId,orgId);
	}
	
	public List<Map<String, Object>>  findMoveObject(String orgId){
		String sql  = "select org_id,org_name from hg_party_org where historic is false and org_parent=(select org_parent from hg_party_org where org_id= ? );";
		
		return jdbcTemplate.queryForList(sql,orgId);
	}

	public Map<String, Object> pagenation(int pageNo, int pageSize, String sql) {
		String sql1=sql+" limit "+pageSize+" offset "+(pageNo-1)*pageSize;
	    Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql);
		int total=count.size();
		if(total%pageSize==0){
			map.put("totalPage", total/pageSize);
		}else{
			map.put("totalPage", total/pageSize+1);
		}
		map.put("pageNow", pageNo);
		map.put("list",list);
	   return map;
	}

	public Map<String, Object> pagenation(int pageNo, int pageSize, String sql, String ss) {
		String sql1=sql+" limit "+pageSize+" offset "+(pageNo-1)*pageSize;
	    Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1,ss);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql,ss);
		int total=count.size();
		if(total%pageSize==0){
			map.put("totalPage", total/pageSize);
		}else{
			map.put("totalPage", total/pageSize+1);
		}
		map.put("pageNow", pageNo);
		map.put("list",list);
	   return map;
	}
}