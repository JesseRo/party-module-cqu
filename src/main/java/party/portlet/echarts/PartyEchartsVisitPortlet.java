package party.portlet.echarts;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

/**
 * 文件名称： 访问量报表<br>
 * 创建人 　： Yu Jiang Xia<br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=访问量统计报表",
			"javax.portlet.init-param.template-path=/",
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.init-param.view-template=/jsp/echarts/partyEchartsVisit.jsp",
			"javax.portlet.name=" + PartyPortletKeys.PartyEchartsVisit,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartyEchartsVisitPortlet extends MVCPortlet{
	Logger logger = Logger.getLogger(PartyEchartsVisitPortlet.class);
	@Reference
	private PartyOrgServer partyOrgServer;
	
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		List<Map<String, Object>> list = partyOrgServer.echartsVisit();//访问量统计
        try {
        	String keyStr = "";
        	//访问量key值
        	String[] keyName = new String[list.size()];
        	//访问量val值
        	int[] valName = new int[list.size()];
        	
        	for(int i=0;i<list.size();i++){
        		keyStr = String.valueOf(list.get(i).get("keyname"));
        		keyStr = keyStr.substring(6);
        		keyName[i] = keyStr;
        		valName[i] = Integer.parseInt(String.valueOf(list.get(i).get("valname")));
        	}
        	//访问量存入map里
        	Map<String, Object> map = new HashMap<>();
        	map.put("keyName", keyName);
        	map.put("valName", valName);
        	String mapStr = JSON.toJSONString(map);
        	
        	int hiet = list.size()*35+135;//给div加高度
        	if(hiet<415){
        		hiet = 415;
        	}
        	renderRequest.setAttribute("hiet", hiet);
        	renderRequest.setAttribute("mapStr", mapStr);  
        	
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.doView(renderRequest, renderResponse);
	}

}

