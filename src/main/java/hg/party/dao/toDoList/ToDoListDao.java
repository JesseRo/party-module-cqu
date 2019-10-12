package hg.party.dao.toDoList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.toDoList.ToDoList;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月22日下午4:54:10<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true,service = ToDoListDao.class)
public class ToDoListDao extends PostgresqlDaoImpl<ToDoList> {

	/**查找党员所属组织和支部*/
	public List<Map<String,Object>> findOrgAndGroup(String userId){
//		String sql="SELECT org.org_name,p.org_name party FROM hg_party_org AS org RIGHT JOIN("
//				+ "SELECT org_name,org_parent FROM hg_party_org WHERE org_id=("
//				+ "SELECT user_department_id FROM hg_users_info WHERE user_id=?))p ON org.org_id=p.org_parent";
		String sql="SELECT org.org_name,p.org_name party FROM hg_party_org AS org RIGHT JOIN("
				+ "SELECT org_name,org_parent FROM hg_party_org WHERE historic is false and org_id=("
				+ "SELECT user_department_id FROM hg_users_info WHERE user_id=?))p ON org.org_id=p.org_parent where org.historic is false ";
		return this.jdbcTemplate.queryForList(sql,userId);
	}

	public Map<String,Object> pageNation(String sql, String sql1) {
		List<Map<String,Object>> listpage=this.jdbcTemplate.queryForList(sql);
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pagelist",listpage);
		map.put("totalpage",list.size());
		return map;
	}
}
