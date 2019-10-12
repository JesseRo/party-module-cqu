package hg.party.server.contentInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.contentInfo.ContentInfoDao;
import hg.party.entity.contentManagementInfo.Hg_Content_Management_Info;


@Component(immediate = true, service = ContentInfoServer.class)
public class ContentInfoServer {
	Logger logger = Logger.getLogger(ContentInfoServer.class);
	@Reference
	private ContentInfoDao contentInfodao;
	
	
	public Hg_Content_Management_Info findById(int id){
		return contentInfodao.findById(id);
	}
	
	/**
	 * 查询一级站点的新闻
	 * @param 设置参数
	 * @return 查询到的新闻信息和附件信息
	 */
	public List<Map<String,Object>> findFirtContentInfoAndAttachment(String leng){
		return contentInfodao.findFirtContentInfoAndAttachment(leng);
	}
	
	public List<Map<String,Object>> findFirtContentInfoAndAttachment2(String leng,String type,String site,String colum){
		return contentInfodao.findFirtContentInfoAndAttachment2(leng,type,site,colum);
	}
	
	
	/**
	 * 根据资源ID查询新闻信息及附件信息
	 * @param resources_id
	 * @return
	 */
	public List<Map<String, Object>> findContentInfoAndAttachmentByResourcesId(String  resources_id){
		return contentInfodao.findContentInfoAndAttachmentByResourcesId(resources_id);
	}
	
	
	/**
	 * 查询附件和新闻信息,图片滚动的server
	 * @param property_value
	 * @return
	 */
	public List<Map<String,Object>> find(String property_value){
		List<Map<String,Object>> list = null;
		try {
			list = contentInfodao.findContentInfoAndAttachment(property_value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return list;
	}
	
	public int saveOrUpdate(Hg_Content_Management_Info info){
		try {
			logger.info("进去保存内容信息方法...");
			return this.contentInfodao.saveOrUpdate(info);
		} catch (Exception e) {
			logger.info("保存内容信息 失败...");
			e.printStackTrace();
		}
		return 0;
	}
	
	/**党务新闻右侧文字*/
	public List<Map<String,Object>> partyNews(){
		return contentInfodao.partyNews();
	}
	/**新闻左侧图片*/
	public List<Map<String,Object>> partyNewsPic(){
		return contentInfodao.partyNewsPic();
	}
	
	public void doSql(String sql){
		try {
			logger.info("执行sql语句 ：" + sql);
			this.contentInfodao.doSql(sql);
		} catch (Exception e) {
			logger.info("执行sql语句 失败...");
			e.printStackTrace();
		}
	}
	
	
	public List<Hg_Content_Management_Info> resInfoList(){
		List<Hg_Content_Management_Info> infos = null;
		try {
			infos = new ArrayList<Hg_Content_Management_Info>();
			infos = contentInfodao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	
	public Hg_Content_Management_Info resInfoById(int id){
		try {
			return contentInfodao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> findByColumnId(int siteId){
		List<Map<String,Object>> infos = null;
		try {
			infos = contentInfodao.findByColumnId(siteId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	
//	public int deleteByResourceId(String resourceId){
//		return contentInfodao.deleteByResourceId(resourceId);
//	}
	//修改给删除状态
	public void deleteByResourceId(String resourceId){
		contentInfodao.deleteByResourceId(resourceId);
	}
	
	//关联查询通知规定次数     
	public List<Map<String,Object>> findFirtContentInfo(String Site,String Column,String size){			
		return contentInfodao.findFirtContentInfo(Site,Column,size);
	}
	
	//关联查询一条数据
	public List<Map<String,Object>> findId(int id){
		return contentInfodao.fandByid(id);
	}
	
	//More查询通知规定次数   
	public List<Map<String,Object>> more_findAll(int pageSize,String Site,String Column){			
		return contentInfodao.more_findAll(pageSize,Site,Column);
	}
	
	//查询下一页
	public List<Map<String,Object>> nextpa(int pageNo,int pageSize,String Site,String Column){
		return contentInfodao.nextPage(pageNo,pageSize,Site,Column);
	}
	//查询上一页
	public List<Map<String, Object>> previouspa(int pageNo,int pageSize,String Site,String Column){
		return contentInfodao.nextPage(pageNo,pageSize,Site,Column);
	}
	//根据站点栏目查询数据条数
	public int fandd(String Site,String Column){
		List<Map<String, Object>> list=contentInfodao.more_sum(Site,Column);
		int size=list.size();
		return size;
	}
	
	//查询站点（学院）
	public List<Map<String, Object>> site(){
		return contentInfodao.site();
	}
	//查询栏目（通知公告）
	public List<Map<String, Object>> column(String id) {
		return contentInfodao.column(id);
	}
	//获取栏目id的名字
	public List<Map<String, Object>> column_name(String id) {
		return contentInfodao.column_name(id);
	}
	//获取站点id的名字
	public List<Map<String, Object>> site_name(String id) {
		return contentInfodao.site_name(id);
	}
	
	public List<Map<String,Object>> find(int property_value){
		List<Map<String,Object>> list = null;
		try {
			list = contentInfodao.findContentInfoAndAttachment(property_value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return list;
	}
	/**根据id来查询？能否用resources_id来查询？*/
	public List<Map<String,Object>> findContentAndAttachmentByResourcesId(String resources_id){
		List<Map<String,Object>> list = null;
		try {
			list = contentInfodao.findContentAndAttachmentByResourcesId(resources_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map<String,Object>> findAttachmentByResourcesId(String resources_id){
		return contentInfodao.findAttachmentByResourcesId(resources_id);
	}
	
	
	public List<Map<String,Object>> findAll(){
		List<Map<String,Object>> list = null;
		try {
			list = contentInfodao.findAllPicTxt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//查询评论量
	public List<Map<String, Object>> findall(){
		List<Map<String, Object>> list=contentInfodao.comment();		
		return list;		
	}
	//查询学院评论量详细
	public List<Map<String, Object>> Detailed(String Column_id){
		List<Map<String, Object>> list=contentInfodao.detailed(Column_id);
		return list;
	}
	
	//根据文章标题模糊查询
	public List<Hg_Content_Management_Info> findbytitle(String title){
		List<Hg_Content_Management_Info> list = null;
		try {
			list = contentInfodao.findbytitle(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}
	
	/*两级门户搜索功能
	 * XiongZG
	 * */
	public List<Map<String, Object>> findBySearchInfo(String searchInfo,int columnId){
		return contentInfodao.findBySearchInfo(searchInfo,columnId);
	}
	
	/**
	 * 搜索页面分页
	 * */
	public Map<String, Object> pagenation(int pageNo,int pageSize,String sql){
		return contentInfodao.pagenation(pageNo,pageSize,sql);
	}
	
	public Map<String, Object> postGresqlFind(int pageNow, int pageSize,String sql){
		return contentInfodao.postGresqlFind(pageNow, pageSize,sql);
	}
	public Map<String, Object> postGresqlFind(int pageNow, int pageSize,String sql,String ss){
		return contentInfodao.postGresqlFind(pageNow, pageSize,sql,ss);
	}
	
	public Hg_Content_Management_Info findByResourceId(String resourceId){
		return contentInfodao.findByResourceId(resourceId);
	}
	
	
	public void saveOrUpdateNews(Hg_Content_Management_Info news){
		try {
			int id = news.getId();
			if(id >= 0){
				contentInfodao.saveOrUpdate(news);
				
			}else{
				contentInfodao.save(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void topback(Hg_Content_Management_Info news){
		try {
			contentInfodao.topBackNormal(news);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void topedBackTop(){
		try {
			contentInfodao.topedBackTop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Map<String, Object>> secondPartyContentFind(String date,String title,String publisher_id,String approve_state){
		return contentInfodao.secondPartyContentFind(date, title, publisher_id,approve_state);
	}
	
	public List<Map<String, Object>> secondPartyContentFind(String date,String title,String publisher_id,String approve_state,int pageSize,int startPage){
		return contentInfodao.secondPartyContentFind(date, title, publisher_id,approve_state, pageSize, startPage);
	}
	
	public List<Map<String, Object>> contentFind(String date,String title,String publisher_id,String approve_state,String department){
		return contentInfodao.contentFind(date, title, publisher_id,approve_state,department);
	}
	
	public List<Map<String, Object>> contentFind(String date,String title,String publisher_id,String approve_state,int pageSize,int startPage,String department){
		return contentInfodao.contentFind(date, title, publisher_id,approve_state, pageSize, startPage,department);
	}
	
	public List<Map<String, Object>> OrganizationContentFind(String date,String title,String publisher_id/*,String approve_state*/){
		return contentInfodao.OrganizationContentFind(date, title, publisher_id/*,approve_state*/);
	}
	
	public List<Map<String, Object>> OrganizationContentFind(String date,String title,String publisher_id,/*String approve_state,*/int pageSize,int startPage){
		return contentInfodao.OrganizationContentFind(date, title, publisher_id,/*approve_state,*/ pageSize, startPage);
	}
	
	
}
