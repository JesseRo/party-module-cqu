package party.portlet.report.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import party.portlet.report.entity.Report;
import party.portlet.report.entity.ReportTask;

import java.util.List;
import java.util.Map;

@Component(immediate = true,service = ReportTaskDao.class)
public class ReportTaskDao extends PostgresqlDaoImpl<ReportTask> {
    public void saveAll(List<ReportTask> contents) {
        contents.forEach(this::saveOrUpdate);
    }

    public ReportTask findByTaskId(String id){
        String sql = "select * from hg_party_report_task where task_id = ?";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(ReportTask.class), id);
    }


    public PostgresqlQueryResult<Map<String, Object>> findPageByTaskIdAndStatus(String orgId, int status, int page) {
        String sql = "select * from hg_party_report_task where publisher = ? and status = ?";
        return postGresqlFindBySql(page, 10, sql, orgId, status);
    }
}
