package hg.party.server.party;

import org.osgi.service.component.annotations.Component;

/**
 * 党费计算
 */
@Component(immediate = true, service = DuesCalculateService.class)
public class DuesCalculateService {
    /**
     * 月薪党员
     * @return
     */
    public double monthSalaryCal() {
        return monthSalaryCalculate();
    }
    /**
     * 年薪党员
     * @return
     */
    public double yearSalaryCal() {
        return monthSalaryCalculate();
    }

    /**
     * 企业员工/其他协议工资党员
     * @return
     */
    public double companyMemberCal() {
        return companyCalculate();
    }
    /**
     * 离退休教职工党员
     * @return
     */
    public double retireEmployeeCal() {
        return retireEmployeeCalculate();
    }
    /**
     * 学生党员
     * @return
     */
    public double studentCal() {
        return 0.20;
    }
    /**
     * 在职就读硕士/博士党员
     * @return
     */
    public double masterJobCal() {
        return monthSalaryCalculate();
    }


    private double monthSalaryCalculate(){
        return 0;
    }

    private double companyCalculate(){
        return 0;
    }
    private double retireEmployeeCalculate(){
        return 0;
    }
}
