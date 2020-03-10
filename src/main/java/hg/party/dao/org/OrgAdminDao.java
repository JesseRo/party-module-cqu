package hg.party.dao.org;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.party.OrgAdmin;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import party.constants.PartyOrgAdminTypeEnum;



@Component(immediate = true,service = OrgAdminDao.class)
public class OrgAdminDao extends PostgresqlDaoImpl<OrgAdmin>{


	public OrgAdmin findOrgAdmin(String userId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
		String sql = "select * from hg_party_org_admin where admin_id = ? and org_type = ? ";
		RowMapper<OrgAdmin> rowMapper = BeanPropertyRowMapper.newInstance(OrgAdmin.class);
		return jdbcTemplate.queryForObject(sql,rowMapper,userId,partyOrgAdminTypeEnum.getType());
	}

}