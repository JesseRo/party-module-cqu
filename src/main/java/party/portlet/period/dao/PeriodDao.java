package party.portlet.period.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Component(immediate = true,service = PeriodDao.class)
public class PeriodDao extends PostgresqlDaoImpl<Period> {
    public Period findByName(String name){
        return jdbcTemplate.queryForObject("select * from hg_party_period where name = ?",  BeanPropertyRowMapper.newInstance(Period.class), name);
    }
}
