package hg.party.dao.chainingDao;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.chainingInfo.Hg_Chaining_Management_Info;

/**
 * @author zhangminggang
 * 
 *
 */

@Component(immediate = true, service = ChainingManagementInfoDao.class)
public class ChainingManagementInfoDao extends PostgresqlDaoImpl<Hg_Chaining_Management_Info> {
	/**
	 * 根据标题查询链接信息
	 * @param chainingName
	 * @return
	public Hg_Chaining_Management_Info findbytitle(String chainingName){
		String sql="SELECT * FROM " + this.tableName + " WHERE chaining_name LIKE '"+chainingName+"'";
		RowMapper<Hg_Chaining_Management_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Chaining_Management_Info.class);
		List<Hg_Chaining_Management_Info> list=this.jdbcTemplate.query(sql, rowMapper);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	 */
	
	/**
	 * 20171023 zlm 替换zmg findbytitle方法的精确查询 为模糊查询
	 * @param chainingName
	 * @return
	 */
	public List<Hg_Chaining_Management_Info> findbytitle(String chainingName){
		String sql="SELECT * FROM " + this.tableName + " WHERE chaining_name LIKE ? ";
		RowMapper<Hg_Chaining_Management_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Chaining_Management_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper,"%"+chainingName+"%");
	}
	
	/**
	 * 根据类型查询链接信息
	 * @param chaining_type
	 * @param chaining_typeval
	 * @return
	 */
	public List<Hg_Chaining_Management_Info> findByChainingType(String chaining_type,String chaining_typeval){
		String sql = "SELECT * from "+ this.tableName +" where "+chaining_type +"= ? ";	
		RowMapper<Hg_Chaining_Management_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Chaining_Management_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper,chaining_typeval);
	}
	
}
