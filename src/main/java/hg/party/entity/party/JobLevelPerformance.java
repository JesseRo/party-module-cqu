package hg.party.entity.party;

import com.dt.annotation.Table;

/**
 * 岗位级别绩效
 */
@Table(name="job_level_performance")
public class JobLevelPerformance {
    private	int id;

    //岗位名称
    private String job_name;

    //岗位绩效
    private String job_performance;

    //岗位类型id
    private	int job_type_id;
}
