package hg.party.dao.party;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.party.MeetingPlan;
import org.osgi.service.component.annotations.Component;


import java.util.List;
import java.util.Map;

@Component(immediate = true, service = JobLevelPerformanceDao.class)
public class JobLevelPerformanceDao extends PostgresqlDaoImpl<MeetingPlan> {
    /**
     * 查询岗位级别绩效查询
     */
    public List<Map<String, Object>> findJobLevelPerformanceAll(){
        String sql = "select p.id,p.job_name jobName,p.job_performance jobPerformance,p.job_type_id jobTypeId,t.name jobTypeName FROM job_level_performance  as p "
                +"LEFT  JOIN job_type as t on p.job_type_id=t.id "
                +"order by t.id asc,p.id asc";
        return jdbcTemplate.queryForList(sql);
    }
}
