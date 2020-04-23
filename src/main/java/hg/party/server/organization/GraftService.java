package hg.party.server.organization;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import hg.party.dao.organization.GraftDao;
@Component(immediate=true,service=GraftService.class)
public class GraftService {
	//@Reference GraftDao dao;
	GraftDao dao=new GraftDao();
	public String findGrafts(Date date){
		List<Map<String, Object>> list=dao.findGrafts(date);
		if (list.size()>0) {
			for (Map<String, Object> map : list) {
				String minutes=null;
				try {
					minutes = date(map.get("public_date").toString());
				} catch (ParseException e) {				
					e.printStackTrace();
				}
				map.put("minutes", minutes);
			}
			return JSON.toJSONString(list);
		}
		return null;
	}
	public List<Map<String, Object>> findGrafts(int state,String dateStr,String orgType) {
		List<Map<String, Object>> list=dao.findGrafts(state,dateParse(dateStr),orgType);
		if (list.size()>0) {
			for (Map<String, Object> map : list) {
				String minutes=null;
				try {
					minutes = date(map.get("release_time").toString());
				} catch (ParseException e) {
				
					e.printStackTrace();
				}
				map.put("minutes", minutes);
			}
			return list;
		}
		return null;
	}
	public List<Map<String, Object>> findGrafts(int state,String dateStr,int size,int startPage,String orgType) {
		Map<String, String> mapDate=dateParse(dateStr);
		List<Map<String, Object>> list=dao.findGrafts(state,mapDate,size,startPage,orgType);
		if (list.size()>0) {
			for (Map<String, Object> map : list) {
				String minutes=null;
				try {
					minutes = date(map.get("release_time").toString());
				} catch (ParseException e) {
				
					e.printStackTrace();
				}
				map.put("minutes", minutes);
			}
			return list;
		}
		return null;
	}
	
	public int deleteGraft(String resourceId){
		return dao.deleteGraft(resourceId);
	}
	/**
	 * 改变状态 从草稿状态变成发布状态 
	 * @param resourceId  通知id
	 * @return
	 */
	public int updateGraft(String resourceId){
		return dao.updateGraft(resourceId);
	}
	  public List<Map<String, Object>> findAlreadyPublic(int state,String dateStr,String orgType){
		  Map<String, String> map=dateParse(dateStr);
		  return dao.findAlreadyPublic(state,map,orgType);
	  }
	  /**
	   * 查询已经发布的通知
	   * @param state     1代表发布状态 0 代表草稿状态哦
	   * @param dateStr   时间（用于根据时间进行查询）
	   * @param pageSize  分页大小
	   * @param startPage 开始页
	   * @param orgType   组织类型
	   * @return
	   */
	  public List<Map<String, Object>> findAlreadyPublic(int state,String dateStr,int pageSize,int startPage,String orgType){
		  Map<String, String> map=dateParse(dateStr);
		  List<Map<String, Object>>list=dao.findAlreadyPublic(state,map,pageSize,startPage,orgType);
		  if (list!=null&&list.size()>0) {
			  for (Map<String, Object> mapData : list) {
				  try {
					mapData.put("release_time", date(mapData.get("release_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		  return list;
	  }
	  // 本日，本周，更早
	  public static Map<String, String> dateParse(String datestr){
		  Map<String,String> map=new HashMap<>();
		  Date date=new Date();
		  Calendar calendar=Calendar.getInstance();
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		 if ("nowDate".equals(datestr)) {
			map.put("type", "normal");
			map.put("date", sdf.format(date));
			map.put("edate", sdf.format(date));
			return map;
		}else if ("nowWeek".equals(datestr)){
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			date=new Date(calendar.getTimeInMillis());
			map.put("type", "normal");
			map.put("date", sdf.format(date));
			map.put("edate", sdf.format(new Date()));
			return map;
		}else if ("more".equals(datestr)){
			calendar.setTime(date);
			calendar.add(calendar.DATE, -7);
			date=new Date(calendar.getTimeInMillis());
			map.put("type", "more");
			map.put("date", sdf.format(date));
			return map;
		}	  	
		  return null;
	  }
	public static String date(String datestr) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date=sdf.parse(datestr);
		String datestring=sdf.format(date);
		return datestring;
	}
	/**
	 * 
	 * @param informId 
	 * @return
	 */
	public List<Map<String, Object>> findGraftDetail( String informId){
		  List<Map<String, Object>> list=dao.findGraftDetail(informId);
		  Map<String, Object> map=list.get(0);
		  try {
			map.put("start_time", date(map.get("start_time").toString()));
			map.put("end_time", date(map.get("end_time").toString()));
			map.put("release_time", date(map.get("release_time").toString()));
			String dedetime=StringUtils.isEmpty(map.get("deadline_time"))?"2018-03-13 10:27:26.379":map.get("deadline_time").toString();
			map.put("deadline_time", date(dedetime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 return list;
	}
	public int deletePublicObject(String informId){
		return dao.deletePublicObject(informId);
	}

	public PostgresqlPageResult<Map<String, Object>> searchPage(int page, int size, String dateType,String orgId,int publicStatus, String keyword) {
		return dao.searchPage(page,size,dateType,orgId,publicStatus,keyword);
	}
}
