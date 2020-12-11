package party.portlet.fee.dao;

import hg.party.entity.partyMembers.Member;
import hg.util.postgres.HgPostgresqlDaoImpl;
import org.osgi.service.component.annotations.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Component(immediate = true, service = FeeDao.class)
@Transactional
public class FeeDao extends HgPostgresqlDaoImpl<Member> {

    public List<Map<String, Object>> allDonate() {
        String sql = "select * from hg_party_donate";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getCurrentConfig(String memberId) {
        String sql = "select * from hg_party_fee_config where member_id = ? order by create_time desc limit 1";
        try {
            return jdbcTemplate.queryForMap(sql, memberId);
        } catch (Exception e) {
            return null;
        }
    }
}
