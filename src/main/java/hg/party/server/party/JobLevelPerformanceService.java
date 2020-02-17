package hg.party.server.party;

import hg.party.dao.party.JobTypeDao;
import hg.party.dao.party.JobLevelPerformanceDao;

import hg.party.entity.party.JobLevelPerformance;
import hg.party.entity.party.JobType;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * 岗位级别绩效
 */
@Component(immediate = true, service = JobLevelPerformanceService.class)
public class JobLevelPerformanceService {
    @Reference
    private JobTypeDao jobTypeDao;
    @Reference
    private JobLevelPerformanceDao jobLevelPerformanceDao;

    public List<JobType> findAllJobType(){
        return jobTypeDao.findAllJobType();
    }
    public List<JobLevelPerformance> findAllJobLevelPerformance(){
        return jobLevelPerformanceDao.findAllJobLevelPerformance();
    }
}
