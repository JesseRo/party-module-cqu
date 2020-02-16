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

    /*物价补贴*/
    private BigDecimal priceSubsidy;

    /*物价补贴*/
    private BigDecimal placeSubsidy;

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

    /*预扣职业年金*/
    private BigDecimal occupationalAnnuities;

    public DuesCal(float basicSalary, float levelSalary, float priceSubsidy, float placeSubsidy, float performance, float housingFund, float unemployedInsurance,float treatmentInsurance,float pensionInsurance, float occupationalAnnuities) {
        this.basicSalary = new BigDecimal(basicSalary);
        this.levelSalary = new BigDecimal(levelSalary);
        this.priceSubsidy = new BigDecimal(priceSubsidy);
        this.placeSubsidy = new BigDecimal(placeSubsidy);
        this.performance = new BigDecimal(performance);
        this.housingFund = new BigDecimal(housingFund);
        this.unemployedInsurance = new BigDecimal(unemployedInsurance);
        this.treatmentInsurance = new BigDecimal(treatmentInsurance);
        this.pensionInsurance = new BigDecimal(pensionInsurance);
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

    public BigDecimal getPriceSubsidy() {
        return priceSubsidy;
    }

    public void setPriceSubsidy(BigDecimal priceSubsidy) {
        this.priceSubsidy = priceSubsidy;
    }

    public BigDecimal getPlaceSubsidy() {
        return placeSubsidy;
    }

    public void setPlaceSubsidy(BigDecimal placeSubsidy) {
        this.placeSubsidy = placeSubsidy;
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

    public BigDecimal getOccupationalAnnuities() {
        return occupationalAnnuities;
    }

    public void setOccupationalAnnuities(BigDecimal occupationalAnnuities) {
        this.occupationalAnnuities = occupationalAnnuities;
    }
}
