package hg.party.dao.party;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.party.MeetingNote;
import hg.party.entity.party.OrgInform;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月12日下午5:04:51<br>
 */
@Component(immediate = true,service = PartyOrgInformDao.class)
public class PartyOrgInformDao extends PostgresqlDaoImpl<OrgInform>{
	
	//根据通知id查询状态
	public List<OrgInform> orgInform(String inform_id){
		String sql = "SELECT * from hg_party_org_inform_info "+
					"WHERE inform_id= ? ";
		RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
		return this.jdbcTemplate.query(sql, rowMapper,inform_id);
	}
}
