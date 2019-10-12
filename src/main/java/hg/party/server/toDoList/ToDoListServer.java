package hg.party.server.toDoList;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.toDoList.ToDoListDao;
/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月22日下午4:56:30<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true, service = ToDoListServer.class)
public class ToDoListServer {

	@Reference
	private ToDoListDao toDoListDao;
	/*分页查询*/
	public Map<String,Object> pagenation(int pageSize,int page,String sql,String sql1){
		//return toDoListDao.postGresqlFindBySql(pageNo,pageSize,sql,nameId);
		Map<String,Object> map=toDoListDao.pageNation(sql,sql1);
		map.put("pageNo",page);
		int count=Integer.parseInt(map.get("totalpage").toString());
		if(count%pageSize==0){
			map.put("totalpage",count/pageSize);
		}else{
			map.put("totalpage",count/pageSize+1);
		}
		return map;
	}
	
	/*查询党员所属组织和支部*/
	public List<Map<String,Object>> findOrgAndGroup(String userId){
		return toDoListDao.findOrgAndGroup(userId);
	}
}
