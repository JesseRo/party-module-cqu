package hg.party.dao.party;

import java.util.List;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.party.Hg_Value_Attribute_Info;

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
@Component(immediate = true, service = ValueAttributeDao.class)
public class ValueAttributeDao extends PostgresqlDaoImpl<Hg_Value_Attribute_Info> {
	/** 查询type为news的所有value */
	public List<String> findValues() {
		String sql = "select resources_value from Hg_Value_Attribute_Info where resources_type='news'";
		return this.jdbcTemplate.queryForList(sql, String.class);
	}

	/** 根据key查询value */
	public List<String> value(String key) {
		String sql = "SELECT value from Hg_Value_Attribute_Info where resources_key= ? ";
		return this.jdbcTemplate.queryForList(sql, String.class, key);
	}
}
