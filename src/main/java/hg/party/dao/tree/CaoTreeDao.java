package hg.party.dao.tree;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.treeInfo.Hg_Tree_Management_Info;

/**
 * @author caoxm
 * 树形接口处理
 */
@Component(immediate = true, service = CaoTreeDao.class)
public class CaoTreeDao extends PostgresqlDaoImpl<Hg_Tree_Management_Info>{

	public List<Map<String, Object>> resParentCaoTrees(){
		String sql = "select * from hg_tree_management_info where parent_id = 0 order by id asc";
		return this.jdbcTemplate.queryForList(sql);
	}
	
	public List<Map<String, Object>> reCaoTreesBysParentId(int id){
		String sql = "select * from hg_tree_management_info where parent_id =? order by id asc";
		return this.jdbcTemplate.queryForList(sql,id);
	}
	
	/**
	 * 根据站点查找相应的栏目
	 * @return
	 */
	public List<Hg_Tree_Management_Info> findColumnBySite(int site_id){
		String sql = "SELECT * FROM "+this.tableName+" WHERE parent_id=? AND site_id=? ";
		RowMapper<Hg_Tree_Management_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Tree_Management_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper,site_id,site_id); 
	}
	
	/**
	 * 查询新插入的站点
	 * @param parentId
	 * @param siteName
	 * @return
	 */
	public Hg_Tree_Management_Info findNullColumnId(int site_id,String siteName){
		String sql = "SELECT * FROM "+this.tableName+" WHERE parent_id=? AND tree_name=? ";
		RowMapper<Hg_Tree_Management_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Tree_Management_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper,site_id,siteName).get(0);
	}
	
	/**
	 * 删除站点
	 * @param siteId
	 * @return
	 */
	public int delByColumnId(int siteId){
		String sql = " DELETE FROM " + this.tableName + " WHERE site_id=?";
		return this.jdbcTemplate.update(sql, new Object[] { siteId });
	}
	
	/**获取treeName对应的siteId*/
	public Map<String,Object> findColumnId(String treeName){
		String sql="SELECT site_id FROM hg_tree_management_info WHERE tree_name=? ";
		return this.jdbcTemplate.queryForMap(sql,treeName);
	}
	
	/**根据site_id 查询 parent_id 20171120 zlm新增*/
	public String Site_id (String site_id){
		String sql = "select parent_id from hg_tree_management_info where site_id=? and site_id <> parent_id";
		return jdbcTemplate.queryForObject(sql.toString(), String.class,site_id);
	}
}
