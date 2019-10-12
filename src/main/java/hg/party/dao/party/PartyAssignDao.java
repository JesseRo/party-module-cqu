package hg.party.dao.party;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.Assign;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 ： XiongZG<br>
 * 创建日期： 2017年12月15日下午2:04:15<br>
 * 版本号 ： v1.0.0<br>
 * 公司 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 ：<br>
 * 修改人 ：<br>
 * 修改内容： <br>
 */
@Component(immediate = true, service = PartyAssignDao.class)
public class PartyAssignDao extends PostgresqlDaoImpl<Assign> {
	// 根据会议id查询会议记录
	public List<Assign> AssignName(String userName) {
		String sql = "SELECT * FROM hg_party_assigne " + "WHERE assigne_name= ? ";
		RowMapper<Assign> rowMapper = BeanPropertyRowMapper.newInstance(Assign.class);
		return this.jdbcTemplate.query(sql, rowMapper,userName);
	}
}
