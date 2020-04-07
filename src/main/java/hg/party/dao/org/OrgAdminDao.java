package hg.party.dao.org;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.party.OrgAdmin;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import party.constants.PartyOrgAdminTypeEnum;

import java.util.List;


@Component(immediate = true,service = OrgAdminDao.class)
public class OrgAdminDao extends PostgresqlDaoImpl<OrgAdmin>{


	public OrgAdmin findOrgAdmin(String userId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
		String sql = "select * from hg_party_org_admin where admin_id = ? and org_type = ? ";
		RowMapper<OrgAdmin> rowMapper = BeanPropertyRowMapper.newInstance(OrgAdmin.class);
		List<OrgAdmin> orgAdminList =  this.jdbcTemplate.query(sql,rowMapper,userId,partyOrgAdminTypeEnum.getType());
		if(orgAdminList.size()>0){
			return orgAdminList.get(0);
		}else{
			return null;
		}
	}
	public OrgAdmin findOrgAdmin(String userId, String id) {
		String sql = "select * from hg_party_org_admin where admin_id = ? and org_id = ? ";
		RowMapper<OrgAdmin> rowMapper = BeanPropertyRowMapper.newInstance(OrgAdmin.class);
		List<OrgAdmin> orgAdminList =  this.jdbcTemplate.query(sql,rowMapper,userId,id);
		if(orgAdminList.size()>0){
			return orgAdminList.get(0);
		}else{
			return null;
		}
	}
	public void deleteOrgAdmin(String userId, PartyOrgAdminTypeEnum type) {
		String sql = "delete from hg_party_org_admin where admin_id = ? and org_type = ? ";
		this.jdbcTemplate.update(sql,userId,type.getType());
	}

    public int updateUserInfo(String user_id, String member_identity) {
		String sql = "update hg_party_org_admin set admin_id = ? where admin_id = ?";
		return jdbcTemplate.update(sql, user_id,member_identity);
    }
}