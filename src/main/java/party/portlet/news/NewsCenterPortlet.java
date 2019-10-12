package party.portlet.news;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.contentInfo.ContentInfoServer;
import party.constants.PartyPortletKeys;


/**
 * 文件名称： activiti<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月18日下午3:20:00<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(
		immediate = true,
		property = {
			"com.liferay.portlet.display-category=category.sample",
			"com.liferay.portlet.instanceable=true",
			"javax.portlet.display-name=新闻中心",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/news/newsCenter/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.NewsCenter,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class NewsCenterPortlet extends MVCPortlet {

	@Reference
	private ContentInfoServer contentInfoServer;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		String sql=generalSql();
		Map<String, Object> postgre;//查询结果集
		postgre=pagenation(renderRequest,sql);
		List<Map<String,Object>> lists=(List<Map<String, Object>>) postgre.get("list");
		//存处理后的数据，传到jsp页面
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:lists){
			Map<String,Object> map1=new HashMap<String,Object>();
			//发布人
			map1.put("publisher", map.get("publisher_id"));
			//resources_id
			map1.put("resourcesId", map.get("resources_id"));
			/*//columnId
			map1.put("columnId", map.get("column_id"));
			//siteId
			map1.put("siteId", map.get("site_id"));*/
			//概述
			map1.put("content_title", map.get("content_title"));
			//距离现在过去时间
			Date date=(Date) map.get("publish_time");
			Date date1=new Date();
			long time=(date1.getTime()-date.getTime())/(60*60*1000);
//			time+=8;//解决时区导致的时间误差
			String str;
			if(time>=24){
				time/=24;
				str=time+"天";
			}else{
				str=time+"小时";
			}
			map1.put("time", str);
			list.add(map1);
		}
		int pageNo=(int) postgre.get("pageNow");//当前页
		int pages=(int) postgre.get("totalPage");//总页数
		renderRequest.setAttribute("pageNo", pageNo);
		renderRequest.setAttribute("pages", pages);
		renderRequest.setAttribute("list", list);
		super.doView(renderRequest, renderResponse);
	}
	
	//分页
	public Map<String, Object> pagenation(RenderRequest req,String sql){
		//当前页
		int pageNo = 0;
		int page = ParamUtil.getInteger(req, "pageNo");
		int total_page = ParamUtil.getInteger(req, "total_page_");
		if(page==0){
			pageNo = 1;//默认当前页为1
		}else if(page > total_page){
			pageNo = total_page;
		}else{
			pageNo = page;
		}
		int pageSize=8;//每页显示条数
		return  contentInfoServer.pagenation(pageNo, pageSize, sql);
	}
	
	//sql
	public String generalSql(){
		String sql="SELECT * FROM hg_content_management_info WHERE column_id is NULL AND site_id is NULL AND approve_state = 3 or approve_state=7 ORDER BY publish_time DESC";
		return sql;
	}
}
