package party.portlet.news;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import hg.party.server.contentInfo.ContentInfoServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月19日上午11:09:55<br>
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
			"javax.portlet.display-name=党务新闻",
			"javax.portlet.init-param.template-path=/",
			
			"com.liferay.portlet.requires-namespaced-parameters=false",
			"javax.portlet.portlet-mode=text/html;view,edit",
			"javax.portlet.init-param.view-template=/jsp/news/partyNews/view.jsp",
//			"javax.portlet.init-param.edit-template=/jsp/search/edit.jsp",
			
			"javax.portlet.name=" + PartyPortletKeys.PartyNews,
			"javax.portlet.resource-bundle=content.Language",
			"javax.portlet.security-role-ref=power-user,user"
		},
		service = Portlet.class
	)
public class PartyNewsPortlet extends MVCPortlet {
	@Reference
	private ContentInfoServer contentInfoServer;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		List<Map<String,Object>> lists = contentInfoServer.partyNews();
		//存处理后的数据，传到jsp页面
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		//存放附件图片地址
		List<Map<String, Object>> url=contentInfoServer.partyNewsPic();
		Calendar cal=Calendar.getInstance();//使用日历类
		int count=0;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		for(Map<String,Object> map:lists){
			Map<String,Object> map1=new HashMap<String,Object>();
			//标题
			map1.put("title", map.get("content_title"));
			//正文
			String str = (String) map.get("content_body");
			String content_body = delHTMLTag(str);
			
			map1.put("body", content_body);
			//PC端时间
			Date time=(Date) map.get("publish_time");
			//手机端时间格式
			String date=sdf.format(time);
			map1.put("date", date);
			//手机端发布距离现在时间
			Date time1=new Date();
			long min=(time1.getTime()-time.getTime())/(1000*60);
			String ago;
			if(min/(60*24)==0){
				if(min/60==0){
					ago=min+"分钟前";
				}else{
					ago=min/60+"小时前";
				}
			}else{
				ago=min/(60*24)+"天前";
			}
			map1.put("ago", ago);
			cal.setTime(time);
			int year=cal.get(Calendar.YEAR);
			int month=cal.get(Calendar.MONTH)+1;
			int day=cal.get(Calendar.DAY_OF_MONTH);
			map1.put("year", year);
			map1.put("day", month+"/"+day);
			map1.put("resourceId", map.get("resources_id"));
			list.add(map1);
		}
		renderRequest.setAttribute("count", count);
		renderRequest.setAttribute("url", url);
		renderRequest.setAttribute("list", list);
		super.doView(renderRequest, renderResponse);
	}
	public static String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    } 
}
