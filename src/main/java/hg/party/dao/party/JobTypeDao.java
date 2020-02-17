package hg.party.dao.party;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.party.JobType;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;


import java.util.List;

@Component(immediate = true, service = JobTypeDao.class)
public class JobTypeDao extends PostgresqlDaoImpl<JobType> {


    /**
     * 查询岗位类型
     */
    public List<JobType> findAllJobType(){
        String sql = "select * from job_type order by id asc";
        RowMapper<JobType> rowMapper = BeanPropertyRowMapper.newInstance(JobType.class);
        return this.jdbcTemplate.query(sql,rowMapper);
    }

}
