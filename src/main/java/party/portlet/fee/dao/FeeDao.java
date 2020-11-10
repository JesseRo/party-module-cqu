package party.portlet.fee.dao;

import com.dt.annotation.Column;
import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.QueryResult;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.BaseStatistics;
import hg.party.entity.partyMembers.GroupMember;
import hg.party.entity.partyMembers.Member;
import hg.util.MD5;
import hg.util.postgres.HgPostgresqlDaoImpl;
import hg.util.postgres.PostgresqlPageResult;
import hg.util.result.Page;
import org.osgi.service.component.annotations.Reference;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import party.constants.PartyOrgAdminTypeEnum;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component(immediate = true, service = FeeDao.class)
@Transactional
public class FeeDao extends HgPostgresqlDaoImpl<Member> {

    public List<Map<String, Object>> allDonate() {
        String sql = "select * from hg_party_donate";
        return jdbcTemplate.queryForList(sql);
    }
}
