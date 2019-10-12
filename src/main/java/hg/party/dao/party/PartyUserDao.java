package hg.party.dao.party;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.login.User;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月9日下午3:50:12<br>
 */
@Component(immediate = true,service = PartyUserDao.class)
public class PartyUserDao extends PostgresqlDaoImpl<User> {
	//查询用户邮箱
	public List<User> userName(String userId){
		String sql = "SELECT * FROM hg_users_info "+
					"WHERE user_id= ? ";			
		RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
		return this.jdbcTemplate.query(sql, rowMapper,userId);
	}
	//通过用户名查询用户id
	public List<User> userId(String userName){
		String sql = "SELECT * FROM hg_users_info "+
					"WHERE user_name= ? ";
		RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
		return this.jdbcTemplate.query(sql, rowMapper,userName);
	}
	
}
