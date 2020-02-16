package hg.party.command.party;


import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.entity.organization.Organization;
import hg.party.server.party.DuesCalculateService;
import hg.party.server.party.PartyOrgServer;
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
import java.util.List;

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
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		
		String partyType = HtmlUtil.escape(ParamUtil.getString(resourceRequest, "partyType"));
		try {
			PrintWriter printWriter=resourceResponse.getWriter();
			if(!"".equals(partyType) && null != partyType && PartyMemberTypeEnum.getEnum(partyType) !=null){
				double calculate  = 0;
				switch(PartyMemberTypeEnum.getEnum(partyType)){
					case MONTH_SALARY:
						logger.info("月薪制党员会费计算。");
						calculate = duesCalculateService.monthSalaryCal();
						break;
					case YEAR_SALARY:
						logger.info("年薪制党员会费计算。");
						calculate = duesCalculateService.yearSalaryCal();
						break;
					case COMPANY_MEMBER:
						logger.info("企业员工/其他协议工资党员");
						calculate = duesCalculateService.companyMemberCal();break;
					case RETIRE_EMPLOYEE:
						logger.info("离退休教职工党员。");
						calculate = duesCalculateService.retireEmployeeCal();
						break;
					case STUDENT:
						logger.info("学生党员会费计算。");
						calculate = duesCalculateService.studentCal();
						break;
					case MASTER_JOB:
						logger.info("在职就读硕士/博士党员会费计算。");
						calculate = duesCalculateService.masterJobCal();
						break;
				}
				printWriter.write(JSON.toJSONString(ResultUtil.success(calculate)));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("党员类型（partyType）错误！")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}


}