package hg.party.entity.party;


import java.math.BigDecimal;

/**
 * 党费计算实体
 */
public class DuesCal {
    /*岗位工资*/
    private BigDecimal basicSalary;

    /*薪级工资*/
    private BigDecimal levelSalary;

    /*工改*/
    private BigDecimal wageReform;

    /*绩效工资理论值*/
    private BigDecimal performance;

    /*住房公积金*/
    private BigDecimal housingFund;

    /*失业保险*/
    private BigDecimal unemployedInsurance;

    /*医疗保险*/
    private BigDecimal treatmentInsurance;

    /*养老保险*/
    private BigDecimal pensionInsurance;

    /*生育保险*/
    private BigDecimal birthInsurance;

    /*工伤保险*/
    private BigDecimal employmentInjuryInsurance;

    /*预扣职业年金*/
    private BigDecimal occupationalAnnuities;

    public DuesCal(){
        this.basicSalary = new BigDecimal(0);
        this.levelSalary = new BigDecimal(0);
        this.wageReform = new BigDecimal(0);
        this.performance = new BigDecimal(0);
        this.housingFund = new BigDecimal(0);
        this.unemployedInsurance = new BigDecimal(0);
        this.treatmentInsurance = new BigDecimal(0);
        this.pensionInsurance = new BigDecimal(0);
        this.birthInsurance = new BigDecimal(0);
        this.employmentInjuryInsurance = new BigDecimal(0);
        this.occupationalAnnuities = new BigDecimal(0);
    }

    public DuesCal(float basicSalary, float levelSalary, float wageReform,float performance, float housingFund, float unemployedInsurance,float treatmentInsurance,float pensionInsurance, float birthInsurance, float employmentInjuryInsurance, float occupationalAnnuities) {
        this.basicSalary = new BigDecimal(basicSalary);
        this.levelSalary = new BigDecimal(levelSalary);
        this.wageReform = new BigDecimal(wageReform);
        this.performance = new BigDecimal(performance);
        this.housingFund = new BigDecimal(housingFund);
        this.unemployedInsurance = new BigDecimal(unemployedInsurance);
        this.treatmentInsurance = new BigDecimal(treatmentInsurance);
        this.pensionInsurance = new BigDecimal(pensionInsurance);
        this.birthInsurance = new BigDecimal(birthInsurance);
        this.employmentInjuryInsurance = new BigDecimal(employmentInjuryInsurance);
        this.occupationalAnnuities = new BigDecimal(occupationalAnnuities);
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public BigDecimal getLevelSalary() {
        return levelSalary;
    }

    public void setLevelSalary(BigDecimal levelSalary) {
        this.levelSalary = levelSalary;
    }

    public BigDecimal getWageReform() {
        return wageReform;
    }

    public void setWageReform(BigDecimal wageReform) {
        this.wageReform = wageReform;
    }

    public BigDecimal getPerformance() {
        return performance;
    }

    public void setPerformance(BigDecimal performance) {
        this.performance = performance;
    }

    public BigDecimal getHousingFund() {
        return housingFund;
    }

    public void setHousingFund(BigDecimal housingFund) {
        this.housingFund = housingFund;
    }

    public BigDecimal getUnemployedInsurance() {
        return unemployedInsurance;
    }

    public void setUnemployedInsurance(BigDecimal unemployedInsurance) {
        this.unemployedInsurance = unemployedInsurance;
    }

    public BigDecimal getTreatmentInsurance() {
        return treatmentInsurance;
    }

    public void setTreatmentInsurance(BigDecimal treatmentInsurance) {
        this.treatmentInsurance = treatmentInsurance;
    }

    public BigDecimal getPensionInsurance() {
        return pensionInsurance;
    }

    public void setPensionInsurance(BigDecimal pensionInsurance) {
        this.pensionInsurance = pensionInsurance;
    }

    public BigDecimal getBirthInsurance() {
        return birthInsurance;
    }

    public void setBirthInsurance(BigDecimal birthInsurance) {
        this.birthInsurance = birthInsurance;
    }

    public BigDecimal getEmploymentInjuryInsurance() {
        return employmentInjuryInsurance;
    }

    public void setEmploymentInjuryInsurance(BigDecimal employmentInjuryInsurance) {
        this.employmentInjuryInsurance = employmentInjuryInsurance;
    }

    public BigDecimal getOccupationalAnnuities() {
        return occupationalAnnuities;
    }

    public void setOccupationalAnnuities(BigDecimal occupationalAnnuities) {
        this.occupationalAnnuities = occupationalAnnuities;
    }
}
