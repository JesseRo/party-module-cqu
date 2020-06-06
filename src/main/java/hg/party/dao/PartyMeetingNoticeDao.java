package hg.party.dao;

import hg.party.entity.party.OrgInform;
import hg.util.postgres.HgPostgresqlDaoImpl;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;
import java.util.Map;

@Component(immediate = true, service = PartyMeetingNoticeDao.class)
public class PartyMeetingNoticeDao extends HgPostgresqlDaoImpl<OrgInform> {
    public OrgInform findInformDetail(String informId) {
        String sql = "select * from hg_party_org_inform_info where inform_id= ? ";
        List<OrgInform> list =  jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(OrgInform.class),informId);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public List<Map<String, Object>> findInformOrgList(String informId) {
        String sql = "select o.* from hg_party_inform_group_info i left join hg_party_org o on i.pub_org_id = o.org_id where i.inform_id= ? ";
        return jdbcTemplate.queryForList(sql,informId);
    }

    public Map<String, Object> findInformAttachFile(String informId) {
        String sql = "select a.* from  hg_party_attachment a left join hg_party_inform_group_info i  on i.inform_id = a.resource_id where i.inform_id= ? ";
        List<Map<String, Object>>  list = jdbcTemplate.queryForList(sql,informId);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
