package hg.party.command.party;


import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import  hg.party.entity.party.DuesResult;
import hg.party.entity.party.DuesCal;
import hg.party.server.party.DuesCalculateService;
import hg.party.server.party.JobLevelPerformanceService;

import hg.util.result.ResultUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyMemberTypeEnum;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.PrintWriter;


/**
 * 党费计算
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.DuesCalculate,
			"mvc.command.name=/hg/part/duesCalculate"
	    },
	    service = MVCResourceCommand.class
)
public class DuesCalculateCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(DuesCalculateCommand.class);
	@Reference
	private DuesCalculateService duesCalculateService;
	@Reference
	private JobLevelPerformanceService jobLevelPerformanceService;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		String partyType = HtmlUtil.escape(ParamUtil.getString(resourceRequest, "partyType"));
		float basicSalary = ParamUtil.getFloat(resourceRequest, "basicSalary");
		float levelSalary = ParamUtil.getFloat(resourceRequest, "levelSalary");
		float priceSubsidy = ParamUtil.getFloat(resourceRequest, "priceSubsidy");
		float placeSubsidy = ParamUtil.getFloat(resourceRequest, "placeSubsidy");
		float performance = ParamUtil.getFloat(resourceRequest, "performance");
		float housingFund = ParamUtil.getFloat(resourceRequest, "housingFund");
		float unemployedInsurance = ParamUtil.getFloat(resourceRequest, "unemployedInsurance");
		float treatmentInsurance = ParamUtil.getFloat(resourceRequest, "treatmentInsurance");
		float pensionInsurance = ParamUtil.getFloat(resourceRequest, "pensionInsurance");
		float occupationalAnnuities = ParamUtil.getFloat(resourceRequest, "occupationalAnnuities");
		DuesCal duesCal = new DuesCal(basicSalary,levelSalary,priceSubsidy,placeSubsidy,performance,housingFund,unemployedInsurance,treatmentInsurance,pensionInsurance,occupationalAnnuities);
		try {
			PrintWriter printWriter=resourceResponse.getWriter();
			if(!"".equals(partyType) && null != partyType && PartyMemberTypeEnum.getEnum(partyType) !=null){
				DuesResult duesResult = new DuesResult();
				switch(PartyMemberTypeEnum.getEnum(partyType)){
					case MONTH_SALARY:
						logger.info("月薪制党员会费计算。");
						duesResult = duesCalculateService.monthSalaryCal(duesCal);
						break;
					case YEAR_SALARY:
						logger.info("年薪制党员会费计算。");
						duesResult = duesCalculateService.yearSalaryCal(duesCal);
						break;
					case COMPANY_MEMBER:
						logger.info("企业员工/其他协议工资党员");
						duesResult = duesCalculateService.companyMemberCal(duesCal);break;
					case RETIRE_EMPLOYEE:
						logger.info("离退休教职工党员。");
						duesResult = duesCalculateService.retireEmployeeCal(duesCal);
						break;
					case STUDENT:
						logger.info("学生党员会费计算。");
						duesResult = duesCalculateService.studentCal();
						break;
					case MASTER_JOB:
						logger.info("在职就读硕士/博士党员会费计算。");
						duesResult = duesCalculateService.masterJobCal(duesCal);
						break;
				}
				printWriter.write(JSON.toJSONString(ResultUtil.success(duesResult)));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("党员类型（partyType）错误！")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


}