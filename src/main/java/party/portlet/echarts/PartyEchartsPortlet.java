package party.portlet.echarts;
import java.io.IOException;
import java.util.*;

import java.text.DecimalFormat;  

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import hg.party.server.party.PartyOrgServer;
import party.constants.PartyPortletKeys;
import hg.party.entity.party.UserStatistics;

/**
 * 文件名称： 报表<br>
 * 创建人 　： Yu Jiang Xia<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=定制报表",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/echarts/partyEcharts.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartyEcharts,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartyEchartsPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(PartyEchartsPortlet.class);
	@Reference
	private PartyOrgServer partyOrgServer;
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		List<Map<String, Object>> list = partyOrgServer.partyEcharts();//学院参会统计
		List<Map<String, Object>> listType = partyOrgServer.partyEchartsType();//会议分布
		int orgNumber = Integer.parseInt(String.valueOf(partyOrgServer.orgNumber().get(0).get("count")));//二级党组织个数
		int branchNumber = Integer.parseInt(String.valueOf(partyOrgServer.branchNumber().get(0).get("count")));//党支部个数
		//int userNumber = Integer.parseInt(String.valueOf(partyOrgServer.userNumber().get(0).get("count")));//党员人数
		UserStatistics userStatistics = partyOrgServer.userStatistics();//党员统计
		int mettingNumber = Integer.parseInt(String.valueOf(partyOrgServer.mettingNumber().get(0).get("count")));//组织活动数
		int visitNumber = Integer.parseInt(String.valueOf(partyOrgServer.dateVisit().get("count")));//查询日访问量
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		//主题党日出勤率统计
		List<Map<String, Object>> atten = partyOrgServer.attenEchartss();
		double f = 0.0;
		String f1 = "";
		DecimalFormat df=new DecimalFormat(".##");
		for (Map<String, Object> map : atten) {
			f = Double.parseDouble(map.get("valname").toString());
			f1 = df.format(f);
			map.put("valname", f1);
		}
		
        try {
        	//学院参会情况key值
        	String[] keyName = new String[list.size()];
        	//学院参会情况val值
        	int[] valName = new int[list.size()];
        	//会议分布key
        	String[] keyNameType = new String[listType.size()];
        	//会议分布val
        	List<Map<String, Object>> valNameType = new ArrayList<Map<String,Object>>();
        	Map<String, Object> valMap = null;
        	
        	for(int i=0;i<list.size();i++){
        		keyName[i] = String.valueOf(list.get(i).get("keyname"));
        		valName[i] = Integer.parseInt(String.valueOf(list.get(i).get("valname")));
        	}
        	for(int i=0;i<listType.size();i++){
        		keyNameType[i] = String.valueOf(listType.get(i).get("keyname"));
        		
        		valMap = new HashMap<>();
        		valMap.put("value", Integer.parseInt(String.valueOf(listType.get(i).get("valname"))));
        		valMap.put("name", String.valueOf(listType.get(i).get("keyname")));
        		valNameType.add(valMap);
        	}
        	//参会情况存入map里
        	Map<String, Object> map = new HashMap<>();
        	map.put("keyName", keyName);
        	map.put("valName", valName);
        	String mapStr = JSON.toJSONString(map);
        	//会议分布存入map里
        	Map<String, Object> mapType = new HashMap<>();
        	mapType.put("keyNameType", keyNameType);
        	mapType.put("valNameType", valNameType);
        	String mapStrType = JSON.toJSONString(mapType);
        	
        	int hiet = list.size()*35+115;//给div加高度
        	if(hiet<395){
        		hiet = 395;
        	}
        	renderRequest.setAttribute("hiet", hiet);
			renderRequest.setAttribute("currentYear", currentYear);
        	renderRequest.setAttribute("mapStr", mapStr);  
        	renderRequest.setAttribute("mapStrType", mapStrType); 
        	renderRequest.setAttribute("orgNumber", orgNumber); 
        	renderRequest.setAttribute("branchNumber", branchNumber); 
        	renderRequest.setAttribute("userStatistics", userStatistics);//党员统计
        	renderRequest.setAttribute("mettingNumber", mettingNumber); 
        	renderRequest.setAttribute("atten",atten); //出勤率
        	renderRequest.setAttribute("dateVisit", visitNumber);
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.doView(renderRequest, renderResponse);
	}

}

