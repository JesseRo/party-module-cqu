package party.portlet.report.dao;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import party.portlet.report.entity.Report;

import java.util.List;
import java.util.Map;

@Component(immediate = true,service = ReportDao.class)
public class ReportDao extends PostgresqlDaoImpl<Report> {
    public List<Report> getMine() {
        String sql = "select * from hg_party_report order by status asc";
        return null;
    }

    public Report findByReportId(String reportId) {
        String sql = "select * from hg_party_report where report_id = '" + reportId +"'";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Report.class));
        } catch (Exception e) {
            return null;
        }
    }


    public List<Report> findByReportIdAndOrgId(String taskId, String orgId) {
        String sql = "select * from hg_party_report where task_id = ? and org_id = ?";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Report.class), taskId, orgId);
        } catch (Exception e) {
            return null;
        }
    }


    public List<Report> findByTaskIdIn(List<String> orgIds) {

        return null;
    }

    public List<Report> findByTaskId(String taskId) {
        String sql = "select * from hg_party_report where task_id = ?";

        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Report.class), taskId);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer countByTaskIdAndStatus(String taskId, Integer status) {
        String sql = "select count(*) from hg_party_report where task_id = ? and status = ?";

        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, taskId, status);
        } catch (Exception e) {
            return null;
        }
    }

    public PostgresqlQueryResult<Map<String, Object>> findPageByTaskId(String taskId, int page) {
        String sql = "select r.report_id as report_id, r.task_id as task_id, r.org_id as org_id, o.org_name as org_name, t.theme as theme, t.description as description, " +
                "t.publish_time as publish_time, r.time as time, r.status as status, r.reason as reason, r.files as files, r.word_files from hg_party_report r " +
                "inner join hg_party_report_task t on r.task_id = t.task_id inner join hg_party_org o on r.org_id = o.org_id " +
                "where r.task_id = ? order by r.status ";
        return postGresqlFindBySql(page, 10, sql, taskId);
    }

    public PostgresqlQueryResult<Map<String, Object>> findPageByTaskIdAndStatus(String taskId, int status, int page) {
        String sql = "select r.report_id as report_id, r.task_id as task_id, r.org_id as org_id, o.org_name as org_name, t.theme as theme, t.description as description, " +
                "t.publish_time as publish_time, r.time as time, r.status as status, r.reason as reason, r.files as files, r.word_files from hg_party_report r " +
                "inner join hg_party_report_task t on r.task_id = t.task_id inner join hg_party_org o on r.org_id = o.org_id " +
                "where r.task_id = ? and r.status = ? order by r.time desc";
        return postGresqlFindBySql(page, 10, sql, taskId, status);
    }

    public PostgresqlQueryResult<Map<String, Object>> findPageByStatus(int status, int page) {
        String sql = "select r.report_id as report_id, r.task_id as task_id, r.org_id as org_id, o.org_name as org_name, t.theme as theme, t.description as description, " +
                "t.publish_time as publish_time, r.time as time, r.status as status, r.reason as reason, r.files as files, r.word_files from hg_party_report r " +
                "inner join hg_party_report_task t on r.task_id = t.task_id inner join hg_party_org o on r.org_id = o.org_id " +
                "where r.status = ? order by r.time desc";
        return postGresqlFindBySql(page, 10, sql, status);
    }

    public void deleteByTaskId(String task_id) {
        String sql = "delete from hg_party_report_task where task_id = ?";
        jdbcTemplate.update(sql, task_id);
        sql = "delete from hg_party_report where task_id = ?";
        jdbcTemplate.update(sql, task_id);
        sql = "delete from hg_party_report_task_org where task_id = ?";
        jdbcTemplate.update(sql, task_id);
    }
}
