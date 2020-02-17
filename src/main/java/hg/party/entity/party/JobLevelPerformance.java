package hg.party.entity.party;

import com.dt.annotation.Table;
import com.dt.annotation.Column;

/**
 * 岗位级别绩效
 */
@Table(name="job_level_performance")
public class JobLevelPerformance {
    private	int id;

    //岗位名称
    @Column(sqlName="job_name",sqlType="varchar",javaName="jobName")
    private String jobName;

    //岗位绩效
    @Column(sqlName="job_performance",sqlType="float4",javaName="jobPerformance")
    private Float jobPerformance;

    //岗位类型id
    @Column(sqlName="job_type_id",sqlType="int4",javaName="jobTypeId")
    private	int jobTypeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Float getJobPerformance() {
        return jobPerformance;
    }

    public void setJobPerformance(Float jobPerformance) {
        this.jobPerformance = jobPerformance;
    }

    public int getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(int jobTypeId) {
        this.jobTypeId = jobTypeId;
    }
}
