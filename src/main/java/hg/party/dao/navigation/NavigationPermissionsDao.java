package hg.party.dao.navigation;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.navigation.NavigationPermissions;
/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月9日上午10:02:04<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true,service = NavigationPermissionsDao.class)
public class NavigationPermissionsDao extends PostgresqlDaoImpl<NavigationPermissions> {

	/*根据部门和角色查询导航菜单*/
	public List<Map<String,Object>> findNavigationByRoleAndDepartment(String department,String role,String location){
		String sql="SELECT * FROM hg_party_navigation_permissions n "
				+ "where n.navigation_to_role=? AND n.show_location=? "
				+ "AND n.parent_id IS NULL ORDER BY navigation_sort";
		return this.jdbcTemplate.queryForList(sql,role,location);
	}
	
	/*查询导航二级页面*/
	public List<Map<String,Object>> findNavigationSecondaryPage(String department,String role){
		String sql="SELECT * FROM hg_party_navigation_permissions n "
				+ "where n.department=? AND n.navigation_to_role=? "
				+ "AND n.parent_id IS NOT NULL ORDER BY navigation_sort";
		return this.jdbcTemplate.queryForList(sql,department,role);
	}
	
	/*查询"网站首页"导航*/
	public Map<String,Object> findHomePage(String name){
		String sql="SELECT navigation_name,navigation_url,remark FROM hg_party_navigation_permissions WHERE navigation_name=? ";
		return this.jdbcTemplate.queryForMap(sql,name);
	}
	
	/*根据navigation_id删除其中一条数据*/
	public void delNavigation(String navigationId){
		String sql = "DELETE FROM hg_party_navigation_permissions WHERE navigation_id=? ";
		//this.jdbcTemplate.execute(sql);
		jdbcTemplate.update(sql,navigationId);
		
	}
	
	/*根据navigation_id查询一条数据*/
	public Map<String,Object> findByNavigationId(String navigationId){
		String sql="SELECT * FROM hg_party_navigation_permissions WHERE navigation_id=? ";
		return this.jdbcTemplate.queryForMap(sql,navigationId);
	}
	
	/*导航后台管理页面修改功能*/
	public void updateNavigation(String navName,String location,String role,String navigationId, String path){
		String sql="UPDATE hg_party_navigation_permissions "
				+ "SET navigation_name= ?,show_location=? ,navigation_to_role=?, navigation_url = ? "
				+ "WHERE navigation_id= ? ";
		//this.jdbcTemplate.execute(sql);
		jdbcTemplate.update(sql,navName,location,role,path,navigationId);
	}
	
	/*根据角色和位置获取一级导航*/
	public List<Map<String,Object>> findParentNode(String role,String location){
		String sql="SELECT navigation_name FROM hg_party_navigation_permissions "
				 + "WHERE navigation_to_role= ? AND parent_id IS NULL AND show_location= ? ";
		return this.jdbcTemplate.queryForList(sql,role ,location);
	}
	
	/*根据父节点名字和角色查询所有子节点*/
	public List<Map<String,Object>> findSonNode(String parentName,String role){
		String sql="SELECT navigation_name FROM hg_party_navigation_permissions WHERE parent_id=("
				 + "SELECT navigation_id FROM hg_party_navigation_permissions "
				 + "WHERE navigation_name= ? AND navigation_to_role= ? AND parent_id IS NULL)";
		return this.jdbcTemplate.queryForList(sql,parentName,role);
	}
	
	/*根据父节点名字和角色查询其navigationId*/
	public Map<String,Object> findNavId(String parentName,String role){
		String sql="SELECT navigation_id FROM hg_party_navigation_permissions "
				+ "WHERE navigation_name= ? AND navigation_to_role= ? ";
		return this.jdbcTemplate.queryForMap(sql,parentName,role);
	}
	
	/*根据父节点名字、角色、兄弟节点、显示位置，插入排序*/
	public Map<String,Object> findNavSort(String navigationName,String parentId){
		String sql="SELECT navigation_sort FROM hg_party_navigation_permissions WHERE parent_id= ? "
				+ "AND navigation_name= ? ";
		return this.jdbcTemplate.queryForMap(sql,parentId,navigationName);
	}
}
