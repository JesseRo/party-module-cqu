package hg.party.entity.party;

/**
 * 党费计算结果
 */
public class DuesResult {
    private double personalTax;
    private double basicDues;
    private double percentDues;
    private double duesPerMonth;
    public DuesResult() {

    }

    public DuesResult(double personalTax, double basicDues, double percentDues, double duesPerMonth) {
        this.personalTax = personalTax;
        this.basicDues = basicDues;
        this.percentDues = percentDues;
        this.duesPerMonth = duesPerMonth;
    }


    public double getPersonalTax() {
        return personalTax;
    }

    public void setPersonalTax(double personalTax) {
        this.personalTax = personalTax;
    }

    public double getBasicDues() {
        return basicDues;
    }

    public void setBasicDues(double basicDues) {
        this.basicDues = basicDues;
    }

    public double getPercentDues() {
        return percentDues;
    }

    public void setPercentDues(double percentDues) {
        this.percentDues = percentDues;
    }

    public double getDuesPerMonth() {
        return duesPerMonth;
    }

    public void setDuesPerMonth(double duesPerMonth) {
        this.duesPerMonth = duesPerMonth;
    }
}
