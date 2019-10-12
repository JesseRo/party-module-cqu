package hg.party.server.navigation;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.navigation.NavigationPermissionsDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.navigation.NavigationPermissions;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月9日上午10:03:40<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true, service = NavigationPermissionsServer.class)
public class NavigationPermissionsServer {

	@Reference
	private NavigationPermissionsDao navigationPermissionsDao;
	@Reference
	private OrgDao orgDao;
	public List<Map<String,Object>> findNavigationByRoleAndDepartment(String department,String role,String location){
		return navigationPermissionsDao.findNavigationByRoleAndDepartment(department, role, location);
	}
	
	public List<Map<String,Object>> findNavigationSecondaryPage(String department,String role){
		return navigationPermissionsDao.findNavigationSecondaryPage(department, role);
	}
	
	public Map<String,Object> findHomePage(String name){
		return navigationPermissionsDao.findHomePage(name);
	}
	
	public Map<String,Object> findOrgType(String orgId){
		return orgDao.findOrgType(orgId);
	}
	
	public Map<String, Object> pagenation(int pageNo,int pageSize,String sql){
		return orgDao.pagenation(pageNo,pageSize,sql);
	}
	public Map<String, Object> pagenation(int pageNo,int pageSize,String sql,String ss){
		return orgDao.pagenation(pageNo,pageSize,sql,ss);
	}
	
	public void delNavigation(String navigationId){
		navigationPermissionsDao.delNavigation(navigationId);
	}
	
	public Map<String,Object> findByNavigationId(String navigationId){
		return navigationPermissionsDao.findByNavigationId(navigationId);
	}
	
	public void updateNavigation(String navName,String location,String role,String navigationId){
		navigationPermissionsDao.updateNavigation(navName, location, role, navigationId);
	}
	
	public List<Map<String,Object>> findParentNode(String role,String location){
		return navigationPermissionsDao.findParentNode(role, location);
	}
	
	public List<Map<String,Object>> findSonNode(String parentName,String role){
		return navigationPermissionsDao.findSonNode(parentName, role);
	}
	
	/*新增导存库*/
	public void saveNewNavigation(NavigationPermissions navigationPermissions){
		navigationPermissionsDao.saveOrUpdate(navigationPermissions);
	}
	
	public Map<String,Object> findNavId(String parentName,String role){
		return navigationPermissionsDao.findNavId(parentName, role);
	}
	
	public Map<String,Object> findNavSort(String navigationName,String parentId){
		return navigationPermissionsDao.findNavSort(navigationName, parentId);
	}
}
