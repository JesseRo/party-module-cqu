package hg.party.dao.publication;


import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.publication.Hg_Publication_Pushing;

/**
 * 内容摘要： 
 * 创建人 　： Zhong LiMei
 * 创建日期： 2017年10月26日下午12:01:10
 */
@Component(immediate=true,service=PublicationDao.class)
public class PublicationDao extends PostgresqlDaoImpl<Hg_Publication_Pushing> {
	public List<Map<String, Object>> findByUser(String user,String type){
		String sql = "select * from Hg_Publication_Pushing where rocket_chat_user=? and publication_type=?";
		return this.jdbcTemplate.queryForList(sql,user,type);
	}
	public Integer findIntegerUser(String user,String type){
		String sql = "select count(*) from Hg_Publication_Pushing where rocket_chat_userand publication_type=?";
		return jdbcTemplate.queryForObject(sql.toString(),Integer.class,type);
	}
	public List<Map<String,Object>> findAllType(){
		String sql = "select publication_type from Hg_Publication_Pushing";
		return this.jdbcTemplate.queryForList(sql);
	}
	public List<Hg_Publication_Pushing> findByUser(String user){
		String sql = "select * from Hg_Publication_Pushing where rocket_chat_user like ? ";
		RowMapper<Hg_Publication_Pushing> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Publication_Pushing.class);
		return this.jdbcTemplate.query(sql, rowMapper,"%"+user+"%");
	}
	
}

