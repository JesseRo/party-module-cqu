package hg.party.server.party;

import java.math.BigDecimal;


import org.osgi.service.component.annotations.Component;

import  hg.party.entity.party.DuesCal;
import  hg.party.entity.party.DuesResult;
/**
 * 党费计算
 */
@Component(immediate = true, service = DuesCalculateService.class)
public class DuesCalculateService {
    /**
     * 月薪党员
     * @return
     */
    public DuesResult monthSalaryCal(DuesCal duesCal) {
        return monthSalaryCalculate(duesCal);
    }
    /**
     * 年薪党员
     * @return
     */
    public DuesResult yearSalaryCal(DuesCal duesCal) {
        return monthSalaryCalculate(duesCal);
    }

    /**
     * 企业员工/其他协议工资党员
     * @return
     */
    public DuesResult companyMemberCal(DuesCal duesCal) {
        return companyCalculate(duesCal);
    }
    
    /**
     * 离退休教职工党员
     * @return
     */
    public DuesResult retireEmployeeCal(DuesCal duesCal) {
        return retireEmployeeCalculate(duesCal);
    }
    
    /**
     * 学生党员
     * @return
     */
    public DuesResult studentCal() {
        return new DuesResult(0,0,0,0.2);
    }
    
    /**
     * 在职就读硕士/博士党员
     * @return
     */
    public DuesResult masterJobCal(DuesCal duesCal) {
        return monthSalaryCalculate(duesCal);
    }

   // 系统自动计算理论税值，不填写，公示按照国家标准）：应纳税额=岗位工资+薪级工资+工改（保留两位小数）+绩效工资理论值-住房公积金-医保-养老个人-失业个人-职业年金
    private   DuesResult monthSalaryCalculate(DuesCal duesCal){
        BigDecimal personTax =  getPersonalTax(duesCal);//个税
        BigDecimal duesBasic = duesCal.getBasicSalary().add(duesCal.getLevelSalary())
                .add(duesCal.getPriceSubsidy())
                .add(duesCal.getPlaceSubsidy())
                .add(duesCal.getPerformance())
                .subtract(duesCal.getHousingFund())
                .subtract(duesCal.getUnemployedInsurance())
                .subtract(duesCal.getTreatmentInsurance())
                .subtract(duesCal.getPensionInsurance())
                .subtract(duesCal.getOccupationalAnnuities())
                .subtract(personTax);
        double duesBasicNum  = duesBasic.doubleValue();//党费基数
        BigDecimal duesPercent;//党费比例
        BigDecimal duesMoney =  new BigDecimal(0);//每月党费
        if(duesBasicNum <= 0){
            duesPercent = new BigDecimal(0.00);
        }else if(duesBasicNum <= 3000){
            duesPercent = new BigDecimal(0.005);
        }else if(duesBasicNum <= 5000){
            duesPercent = new BigDecimal(0.010);
        }else if(duesBasicNum <= 10000){
            duesPercent = new BigDecimal(0.015);
        }else{
            duesPercent = new BigDecimal(0.020);
        }
        duesMoney = duesBasic.multiply(duesPercent);
        return new DuesResult(personTax.doubleValue(),duesBasicNum,duesPercent.doubleValue(),duesMoney.doubleValue());
    }

    private  DuesResult companyCalculate(DuesCal duesCal){
        BigDecimal personTax =  getPersonalTax(duesCal);//个税
        BigDecimal duesBasic = duesCal.getBasicSalary().add(duesCal.getLevelSalary())
                .add(duesCal.getPriceSubsidy())
                .add(duesCal.getPlaceSubsidy())
                .add(duesCal.getPerformance())
                .subtract(duesCal.getHousingFund())
                .subtract(duesCal.getUnemployedInsurance())
/*                .subtract(duesCal.getTreatmentInsurance())
                .subtract(duesCal.getPensionInsurance())*/
                .subtract(duesCal.getOccupationalAnnuities())
                .subtract(personTax);
        double duesBasicNum  = duesBasic.doubleValue();//党费基数
        BigDecimal duesPercent;//党费比例
        BigDecimal duesMoney =  new BigDecimal(0);//每月党费
        if(duesBasicNum <= 0){
            duesPercent = new BigDecimal(0.00);
        }else if(duesBasicNum <= 3000){
            duesPercent = new BigDecimal(0.005);
        }else if(duesBasicNum <= 5000){
            duesPercent = new BigDecimal(0.010);
        }else if(duesBasicNum <= 10000){
            duesPercent = new BigDecimal(0.015);
        }else{
            duesPercent = new BigDecimal(0.020);
        }
        duesMoney = duesBasic.multiply(duesPercent);
        return new DuesResult(personTax.doubleValue(),duesBasicNum,duesPercent.doubleValue(),duesMoney.doubleValue());
    }
    private DuesResult retireEmployeeCalculate(DuesCal duesCal){

        double duesBasicNum  = duesCal.getBasicSalary().doubleValue();//党费基数
        BigDecimal duesPercent;//党费比例
        BigDecimal duesMoney =  new BigDecimal(0);//每月党费
        if(duesBasicNum <= 0){
            duesPercent = new BigDecimal(0.00);
        }else if(duesBasicNum <= 5000){
            duesPercent = new BigDecimal(0.005);
        }else{
            duesPercent = new BigDecimal(0.010);
        }
        duesMoney = duesCal.getBasicSalary().multiply(duesPercent);
        return new DuesResult(0,duesBasicNum,duesPercent.doubleValue(),duesMoney.doubleValue());
    }


    private  BigDecimal getPersonalTax(DuesCal duesCal){
        double taxMoney = duesCal.getBasicSalary().add(duesCal.getLevelSalary())
                .add(duesCal.getPriceSubsidy())
                .add(duesCal.getPlaceSubsidy())
                .add(duesCal.getPerformance())
                .subtract(duesCal.getHousingFund())
                .subtract(duesCal.getUnemployedInsurance())
                .subtract(duesCal.getTreatmentInsurance())
                .subtract(duesCal.getPensionInsurance())
                .subtract(duesCal.getOccupationalAnnuities())
                .doubleValue();
        BigDecimal personTax = new BigDecimal(0);
        if(taxMoney > 80000){
            personTax= new BigDecimal(taxMoney).subtract(new BigDecimal(80000)).multiply(new BigDecimal(0.45));
            personTax = personTax .add(new BigDecimal((80000-55000)*0.35));
            personTax = personTax .add(new BigDecimal((55000-35000)*0.30));
            personTax = personTax .add(new BigDecimal((35000-25000)*0.25));
            personTax = personTax .add(new BigDecimal((25000-12000)*0.20));
            personTax = personTax .add(new BigDecimal((12000-3000)*0.10));
            personTax = personTax .add(new BigDecimal(3000*0.03));
        }else if(taxMoney>55000){
            personTax= new BigDecimal(taxMoney).subtract(new BigDecimal(55000)).multiply(new BigDecimal(0.35));
            personTax = personTax .add(new BigDecimal((55000-35000)*0.30));
            personTax = personTax .add(new BigDecimal((35000-25000)*0.25));
            personTax = personTax .add(new BigDecimal((25000-12000)*0.20));
            personTax = personTax .add(new BigDecimal((12000-3000)*0.10));
            personTax = personTax .add(new BigDecimal(3000*0.03));
        }else if(taxMoney>35000){
            personTax= new BigDecimal(taxMoney).subtract(new BigDecimal(35000)).multiply(new BigDecimal(0.30));
            personTax = personTax .add(new BigDecimal((35000-25000)*0.25));
            personTax = personTax .add(new BigDecimal((25000-12000)*0.20));
            personTax = personTax .add(new BigDecimal((12000-3000)*0.10));
            personTax = personTax .add(new BigDecimal(3000*0.03));
        }else if(taxMoney>25000){
            personTax= new BigDecimal(taxMoney).subtract(new BigDecimal(25000)).multiply(new BigDecimal(0.25));
            personTax = personTax .add(new BigDecimal((25000-12000)*0.20));
            personTax = personTax .add(new BigDecimal((12000-3000)*0.10));
            personTax = personTax .add(new BigDecimal(3000*0.03));
        } else if(taxMoney>12000){
            personTax= new BigDecimal(taxMoney).subtract(new BigDecimal(12000)).multiply(new BigDecimal(0.20));
            personTax = personTax .add(new BigDecimal((12000-3000)*0.10));
            personTax = personTax .add(new BigDecimal(3000*0.03));
        }else if(taxMoney>3000){
            personTax= new BigDecimal(taxMoney).subtract(new BigDecimal(3000)).multiply(new BigDecimal(0.10));
            personTax = personTax .add(new BigDecimal(3000*0.03));
        }else if(taxMoney>0){
            personTax= new BigDecimal(taxMoney).multiply(new BigDecimal(0.03));
        }
        return personTax;
    }

}
