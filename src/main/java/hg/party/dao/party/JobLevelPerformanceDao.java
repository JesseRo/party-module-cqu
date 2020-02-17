package hg.party.dao.party;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.party.JobLevelPerformance;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;


@Component(immediate = true, service = JobLevelPerformanceDao.class)
public class JobLevelPerformanceDao extends PostgresqlDaoImpl<JobLevelPerformance> {


    /**
     * 查询所有岗位级别绩效
     */
    public List<JobLevelPerformance> findAllJobLevelPerformance(){
        String sql = "select * from job_level_performance order by id asc";
        RowMapper<JobLevelPerformance> rowMapper = BeanPropertyRowMapper.newInstance(JobLevelPerformance.class);
        return this.jdbcTemplate.query(sql,rowMapper);
    }
}
