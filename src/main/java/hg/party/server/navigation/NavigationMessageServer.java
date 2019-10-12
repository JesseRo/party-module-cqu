package hg.party.server.navigation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.org.OrgDao;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： YJX<br>
 * 创建日期： 2018年1月9日上午10:03:40<br>
 * 版本号　 ： v1.0.0<br>
 * 修改内容： <br>
 */
@Component(immediate = true, service = NavigationMessageServer.class)
public class NavigationMessageServer {

	@Reference
	private OrgDao orgDao;
	
	public Map<String, String > NavigationPermissions(String sql){
		List<Map<String, Object>> aa = orgDao.NavigationMessage(sql);
		Map<String, String> map=new HashMap<>();
		if(aa!=null&& aa.size()>0){
			map.put("wait", "t");
			map.put("wait_count", aa.size()+"");
			return map;
		}else {
			map.put("wait", "f");
			map.put("wait_count", "");
			return map;
		}
	}
	
}
