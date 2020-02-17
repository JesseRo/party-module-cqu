package hg.party.command.party;


import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import hg.party.entity.party.*;
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
import java.util.ArrayList;
import java.util.List;


/**
 * 党费计算
 */

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.DuesCalculate,
			"mvc.command.name=/hg/part/jobLevelTree"
	    },
	    service = MVCResourceCommand.class
)
public class JobLevelTreeCommand implements MVCResourceCommand{
	
	Logger logger = Logger.getLogger(JobLevelTreeCommand.class);
	@Reference
	private JobLevelPerformanceService jobLevelPerformanceService;
	
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {

		try {
			PrintWriter printWriter=resourceResponse.getWriter();
			List<JobType> jobTypeList = jobLevelPerformanceService.findAllJobType();
			List<JobLevelPerformance> jobLevelPerformanceList = jobLevelPerformanceService.findAllJobLevelPerformance();
			List<TreeNode> treeNodeList = initJobPerformanceTreeData(jobTypeList,jobLevelPerformanceList);
			printWriter.write(JSON.toJSONString(treeNodeList));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	private List<TreeNode> initJobPerformanceTreeData(List<JobType> jobTypeList,List<JobLevelPerformance> jobLevelPerformanceList){
		List<TreeNode> treeNodeList = new ArrayList<>();
		int nodeId = 1;
		if(jobTypeList.size()>0){
			for(JobType jobType:jobTypeList){
				int jobTypeId = jobType.getId();
				TreeNode parentNode = new TreeNode();
				parentNode.setChecked(false);
				parentNode.setId(nodeId);
				nodeId = nodeId+1;
				parentNode.setName(jobType.getName());
				parentNode.setOpen(false);
				parentNode.setData(jobType);
				List<TreeNode> children = new ArrayList<>();
				if(jobLevelPerformanceList.size()>0){
					for(JobLevelPerformance jobLevelPerformance:jobLevelPerformanceList){
						if(jobLevelPerformance.getJobTypeId() == jobTypeId){
							TreeNode childNode = new TreeNode();
							childNode.setChecked(false);
							childNode.setId(nodeId);
							nodeId = nodeId+1;
							childNode.setName(jobLevelPerformance.getJobName());
							childNode.setOpen(false);
							childNode.setData(jobLevelPerformance);
							children.add(childNode);
						}

					}
				}
				parentNode.setChildren(children);
				treeNodeList.add(parentNode);
			}
		}
		return treeNodeList;
	}

}