package hg.party.server.party;

import hg.party.dao.party.JobLevelPerformanceDao;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Map;

/**
 * 岗位级别绩效
 */
@Component(immediate = true, service = JobLevelPerformanceService.class)
public class JobLevelPerformanceService {
    @Reference
    private JobLevelPerformanceDao jobLevelPerformanceDao;

    public List<Map<String, Object>> findJobLevelPerformanceAll(){
        return jobLevelPerformanceDao.findJobLevelPerformanceAll();
    }
}
