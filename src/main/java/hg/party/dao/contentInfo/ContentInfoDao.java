package hg.party.dao.contentInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.contentManagementInfo.Hg_Content_Management_Info;



/**
 * @author zhangminggang
 *
 */
@Component(immediate = true, service = ContentInfoDao.class)
public class ContentInfoDao extends PostgresqlDaoImpl<Hg_Content_Management_Info> {
	Logger logger = Logger.getLogger(ContentInfoDao.class);
	/**党务新闻组件右侧文字*/
	public List<Map<String,Object>> partyNews(){
		String sql="SELECT * FROM hg_content_management_info WHERE column_id is NULL AND site_id is NULL AND (approve_state = 3 OR approve_state = 7) ORDER BY publish_time DESC LIMIT 10";
//		logger.info("sql "+ sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	/**党务新闻组件左侧图片*/
	public List<Map<String,Object>> partyNewsPic(){
//		String sql="select att.* from (select *, row_number() over (partition by resources_id order by resources_id) as group_idx "
//				+ "from hg_content_management_attachment WHERE attachment_type='png' OR attachment_type='jpg' OR attachment_type='bmp')att "
//				+ "where att.group_idx = 1 ORDER BY attachment_updatetime DESC LIMIT 3";
		String sql = "select att.*,info.content_title,info.approve_state from (select *, row_number() over (partition by resources_id order by resources_id) as group_idx "+
					"from hg_content_management_attachment WHERE attachment_type='png' OR attachment_type='jpg' OR attachment_type='bmp' or attachment_type='PNG' OR attachment_type='JPG' OR attachment_type='BMP')att "+
					"LEFT JOIN hg_content_management_info AS info "+
					"ON att.resources_id = info.resources_id "+
					"where att.group_idx = 1 AND (info.approve_state = 3 OR info.approve_state = 7) "+
					"ORDER BY attachment_updatetime DESC LIMIT 3 ";
//		logger.info("sql "+ sql);
		return this.jdbcTemplate.queryForList(sql);
	}
	/**
	 * 查询一级站点(西南大学)下的新闻
	 * SELECT * FROM table LIMIT 5;     //检索前 5 个记录行
	 * SELECT * FROM table LIMIT 5,10;  // 检索记录行 6-15
	 * column_id=1即案例中首页：查询西南大学站点下的新闻i.site_id=10
	 * @param  property_value 默认检索记录行
	 * @return   hg_content_management_info  ,  hg_content_management_attachment
	 */
	public List<Map<String, Object>> findFirtContentInfoAndAttachment(String property_value){
		String sql = "SELECT t.* from "+ 
						"(select i.*,a.attachment_name,a.attachment_type,a.attachment_url,a.attachment_show_name"+
						" from hg_content_management_info i left join hg_content_management_attachment a "+
						"on a.resources_id=i.resources_id where i.site_id=1 and i.column_id=88 ORDER BY i.id DESC)t "+ 
						" LIMIT ?";	
		return this.jdbcTemplate.queryForList(sql,property_value);
	}
	//张振麒
	public List<Map<String, Object>> findFirtContentInfoAndAttachment2(String property_value, String content_type,String site,String cloum){
		String sql = "SELECT t.* from "+ 
				"(select i.*,a.attachment_name,a.attachment_type,a.attachment_url,a.attachment_show_name"+
				" from hg_content_management_info i left join hg_content_management_attachment a "+
				"on a.resources_id=i.resources_id where i.site_id=? and i.column_id=? and i.content_type=? ORDER BY i.id DESC)t "+ 
				" LIMIT ?";	
		return this.jdbcTemplate.queryForList(sql,site,cloum,content_type,property_value);
	}
	
	public List<Map<String, Object>> findContentInfoAndAttachmentByResourcesId(String resources_id){
		String sql = "select i.*,a.attachment_name,a.attachment_type,a.attachment_url from "
						+ "hg_content_management_info i LEFT JOIN hg_content_management_attachment a "
						+ "ON a.resources_id=i.resources_id where i.resources_id = ?";	
		return this.jdbcTemplate.queryForList(sql,resources_id);
	}
	
	/**
	 * 查询附件和新闻信息,图片滚动的dao
	 * @param property_value
	 * @return
	 */
	public List<Map<String,Object>> findContentInfoAndAttachment(String property_value){
		String sql="select info.resources_id,info.id,info.content_title,att.attachment_type,att.attachment_url,att.attachment_name "
				+"from hg_content_management_info info,hg_content_management_attachment att "
				+"where info.resources_id=att.resources_id and att.attachment_type in ('jpg','png','jpeg','bmp')"
				+"limit ?";
		return this.jdbcTemplate.queryForList(sql,property_value);
	}
	
	public int doSql(String sql){
		try {
			this.jdbcTemplate.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 根据栏目ID查询内容信息
	 * @param siteId 栏目ID
	 * @return 内容信息集合
	 */
	public List<Map<String,Object>> findByColumnId(int column_id){
		String sql="SELECT * FROM hg_content_management_info WHERE column_id=? ORDER BY istop DESC ,to_top_time DESC,publish_time DESC";
		return this.jdbcTemplate.queryForList(sql,column_id);
	}
	
	/**
	 * 根据resourceID删除info
	 * @param resourceId
	 * @return
	 */
//	public int deleteByResourceId(String resourceId){
//		String sql="DELETE FROM "+this.tableName+" WHERE resources_id = ?";
//		return this.jdbcTemplate.update(sql, new Object[] { resourceId });
//	}
	//改为删除状态、
	public void deleteByResourceId(String resourceId){
		String sql = "UPDATE hg_content_management_info SET approve_state = 8 "+
					"WHERE resources_id='"+resourceId+"' ";
		jdbcTemplate.execute(sql);
	}
	
	
	//title查询通知规定次数
	public List<Map<String,Object>>findFirtContentInfo(String Site,String Column,String size){
		
		String sql="SELECT * from "+
					"( "+
					"SELECT * FROM hg_content_management_info "+
					"WHERE "+
					"site_id=? AND "+
					"column_id=? "+
					") as t "+
					"order by id desc LIMIT ?";
		return this.jdbcTemplate.queryForList(sql,Site,Column,size );	
	}
	

	//根据id关联查询一条
	public List<Map<String, Object>> fandByid(int id){
		String sql =  "SELECT t.* from "
				+ "(select i.*,a.attachment_url,a.attachment_name,a.attachment_type from hg_content_management_info i LEFT JOIN hg_content_management_attachment a ON a.resources_id=i.resources_id ORDER BY i.id DESC)t "
					+ " where t.id=?";	
		return this.jdbcTemplate.queryForList(sql,id );
	}


	//More查询通知公告规定次数	
	public List<Map<String,Object>>more_findAll(int pageSize,String Site,String Column){
		
		String sql="SELECT * FROM "+
					"( "+
					"SELECT a.*,b.attachment_url,b.attachment_type FROM "+
					"( "+
					"SELECT * FROM hg_content_management_info "+
					"WHERE "+
					"site_id=? AND "+
					"column_id=? "+
					") as a LEFT JOIN "+
					"hg_content_management_attachment as b "+
					"ON "+
					"a.resources_id=b.resources_id "+
					") as c "+
					"LIMIT ?";
		
		return this.jdbcTemplate.queryForList(sql,Site,Column,pageSize);	
	}
	
	//More查询通知公告的总条数	
			public List<Map<String,Object>>more_sum(String Site,String Column){
				
				String sql="SELECT a.*,b.attachment_url,b.attachment_type FROM "+
							"( "+
							"SELECT * FROM hg_content_management_info "+
							"WHERE "+
							"site_id=? AND "+
							"column_id=? "+
							") as a LEFT JOIN "+
							"hg_content_management_attachment as b "+
							"ON "+
							"a.resources_id=b.resources_id ";
				
				return this.jdbcTemplate.queryForList(sql,Site,Column);	
			}
	
	
	//More上下页查询
	public List<Map<String,Object>> nextPage(int pageNo,int pageSize,String Site,String Column){
		
		String sql="SELECT * FROM "+
				"( "+
				"SELECT a.*,b.attachment_url,b.attachment_type FROM "+
				"( "+
				"SELECT * FROM hg_content_management_info "+
				"WHERE "+
				"site_id=? AND "+
				"column_id=? "+
				") as a LEFT JOIN "+
				"hg_content_management_attachment as b "+
				"ON "+
				"a.resources_id=b.resources_id "+
				") as c "+
				"LIMIT ?,? ";
		
		return this.jdbcTemplate.queryForList(sql,Site,Column,(pageNo-1)*pageSize,pageSize);	
	}
	
	//查询站点（学院）
	public List<Map<String, Object>> site(){
		String sql="SELECT * FROM hg_tree_management_info "+
					"WHERE "+
					"id=site_id ";			
		return this.jdbcTemplate.queryForList(sql);
		
	}
	
	//查询栏目（通知、公告）
	public List<Map<String, Object>> column(String id){
		String sql="SELECT * FROM hg_tree_management_info "+
					"WHERE "+
					"parent_id=site_id AND "+
					"site_id=? " ;
		return this.jdbcTemplate.queryForList(sql,id);
		
	}
	//title获取栏目的名字
	public List<Map<String, Object>> column_name(String id){
		String sql="SELECT * FROM hg_tree_management_info "+
					"WHERE "+
					"parent_id=site_id AND "+
					"id=? " ;
		return this.jdbcTemplate.queryForList(sql,id);
	}
	
	//title获取站点的名字 zlm 20171120
	public List<Map<String, Object>> site_name(String id){
		String sql="SELECT * FROM hg_tree_management_info "+
					"WHERE "+
					"parent_id<>site_id AND "+
					"id=? " ;
		return this.jdbcTemplate.queryForList(sql,id);
	}
		
	public List<Map<String,Object>> findContentInfoAndAttachment(int property_value){
		if(property_value==0){
			property_value=4;
		}
		//查询西南大学的新闻
		String sql = "SELECT t.* from "
				+ "(select i.*,a.attachment_name,a.attachment_type,a.attachment_url "
				+ "from hg_content_management_info i LEFT JOIN hg_content_management_attachment a "
				+ "ON a.resources_id=i.resources_id where i.site_id=1 and i.column_id=88 ORDER BY i.id)t "
				+ " LIMIT ?";	
		return this.jdbcTemplate.queryForList(sql,property_value);
	}
	
	
	public List<Map<String,Object>> findContentAndAttachmentByResourcesId(String resources_id){
		String sql = "select i.*,a.attachment_name,a.attachment_type,a.attachment_url,a.id AS att_id "
				+ "from hg_content_management_info i LEFT JOIN hg_content_management_attachment a "
				+ "ON a.resources_id=i.resources_id where i.resources_id = ?";
		return this.jdbcTemplate.queryForList(sql,resources_id);
	}
	public List<Map<String,Object>> findAttachmentByResourcesId(String resources_id){
		String sql="";
		try {			
			sql = "select a.* from hg_content_management_attachment a,hg_content_management_info i  where a.resources_id=i.resources_id and a.resources_id=? ";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.jdbcTemplate.queryForList(sql,resources_id);
	}
	
	
	/*
	 * XiongZG
	 * 2017/11/2 15:06改
	 * */
	public List<Map<String, Object>> findAllPicTxt(){
		
		String sql = "SELECT t.* from "+
				"(select i.id,i.site_id,i.column_id,i.resources_id,i.content_title,"
				+ "i.content_description,a.attachment_name,a.attachment_type,a.attachment_url "
				+ "from hg_content_management_info i LEFT JOIN "
				+ "hg_content_management_attachment a "
				+ "ON a.resources_id=i.resources_id ORDER BY i.id DESC)t  WHERE t.column_id=88 "+
			   "AND t.site_id=1";

		return this.jdbcTemplate.queryForList(sql);	
	}
	
	//统计评论量
		public List<Map<String, Object>> comment(){		
			String sql="SELECT tree.id ,tree.tree_name,t.sum s FROM "+
										"( "+
										"SELECT i.site_id,SUM(p.v) sum FROM "+ 
										"( "+
										"select resources_id,count(*) v from hg_content_management_attachment "+
										"group by resources_id ) AS p, "+
										"hg_content_management_info AS i "+
										"WHERE p.resources_id=i.resources_id "+
										"GROUP BY i.site_id "+
										") as t, "+
										"hg_tree_management_info as tree "+	
										"WHERE "+
										"t.site_id=tree.site_id AND "+ 
										"tree.parent_id=0 ";
			return this.jdbcTemplate.queryForList(sql);
		}
		
		//学院评论量详细
		public List<Map<String, Object>> detailed(String column_id){
			//详细统计评论量显示的是id而不是栏目名 
			//"(SELECT i.column_id,i.site_id,SUM(p.v) sum FROM "+   20171121 zlm 去掉了i.site_id
			
			String sql="SELECT tree.tree_name,t.sum s FROM "+
					"(SELECT i.column_id,SUM(p.v) sum FROM "+
					"(select resources_id,count(*) v "+
					"from hg_content_management_attachment group by resources_id) AS p, "+
					"hg_content_management_info AS i "+
					"WHERE p.resources_id=i.resources_id "+
					"and i.site_id=(SELECT d.id FROM "+
					"hg_tree_management_info as d "+
					"WHERE "+ 
					"d.tree_name=? ) "+ 
					"GROUP BY i.column_id) as t, "+
					"hg_tree_management_info as tree "+ 
					"WHERE t.column_id=tree.id ";
			return this.jdbcTemplate.queryForList(sql,column_id);		
		}
		
		/**
		 * 20171023 zlm新增根据标题模糊查询
		 * @param title
		 * @return
		 */
		public List<Hg_Content_Management_Info> findbytitle(String title){
			String sql="SELECT * FROM " + this.tableName + " WHERE content_title LIKE '%"+title+"%'";
			RowMapper<Hg_Content_Management_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Content_Management_Info.class);
			return this.jdbcTemplate.query(sql, rowMapper);
		}
		
		/*两级门户系统搜索功能
		 * XiongZG增
		 * */
		public List<Map<String,Object>> findBySearchInfo(String searchInfo,int columnId){
			String sql="SELECT s.* FROM("
							+"SELECT t.*,a.Attachment_url,a.Attachment_type FROM hg_content_management_attachment as a,"
							+"(SELECT i.resources_id,i.content_title,i.content_description,i.publish_time,i.publisher_id FROM hg_content_management_info i "
							+"WHERE column_id="+columnId+" ORDER BY i.publish_time DESC)as t WHERE a.resources_id=t.resources_id)s"
							+" WHERE s.content_title LIKE '%"+searchInfo+"%' OR s.publisher_id LIKE '%"+searchInfo+"%'";
			return this.jdbcTemplate.queryForList(sql);
		}
		
		public Hg_Content_Management_Info findByResourceId(String resourceId){
			List<Hg_Content_Management_Info> infoList  = null;
			try {
				String sql = "SELECT * FROM hg_content_management_info WHERE hg_content_management_info.resources_id = '"+ resourceId +"'";
				RowMapper<Hg_Content_Management_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Content_Management_Info.class);
				infoList = this.jdbcTemplate.query(sql, rowMapper);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return infoList.get(0);
		}
		
		/*取消置顶
		 *	
		 * */
		public void topBackNormal(Hg_Content_Management_Info news){
			String sql = "UPDATE hg_content_management_info SET istop = 0 WHERE resources_id != '"+ news.getResources_id() +"'";
			this.jdbcTemplate.execute(sql);
		}
		
		/*之前被置顶过新闻取消置顶
		 *	
		 * */
		public void topedBackTop(){
			String sql = "UPDATE hg_content_management_info SET istop=1 WHERE resources_id =(SELECT resources_id FROM hg_content_management_info WHERE to_top_time > '1970-01-01 00:00:00' ORDER BY to_top_time DESC LIMIT 1)";
			this.jdbcTemplate.execute(sql);
		}
		
		/**党支部发文根据多参数查询*/
		public List<Map<String, Object>> contentFind(String date,String title,String publisher_id,String approve_state,String department){
			String sql="select * from hg_content_management_info where (approve_state=0 or approve_state=5 or (approve_state<>0 and approve_state<>5 and first_approve_id<>'' ) and approve_state<>8) "+
					"and content_user_id ='"+department+"' ";
			StringBuffer buffer=new StringBuffer(sql);
			if (!StringUtils.isEmpty(date)) {
				buffer.append(" AND publish_time>'"+date+" 00:00:00' and publish_time<'"+date+" 24:00:00'");		
			}
			if (!StringUtils.isEmpty(title)) {
				buffer.append(" and content_title like'%"+title+"%'");
			}
			if (!StringUtils.isEmpty(publisher_id)) {
				buffer.append(" and publisher_id='"+publisher_id+"'");
			}
			if (!StringUtils.isEmpty(approve_state)) {
				buffer.append(" and approve_state="+approve_state+"");
			}
			buffer.append(" ORDER BY to_top_time DESC,publish_time DESC ");
//			logger.info(sql);
			return jdbcTemplate.queryForList(buffer.toString());
		}
		
		/**党支部发文根据多参数查询*/
		public List<Map<String, Object>> contentFind(String date,String title,String publisher_id,String approve_state,int pageSize,int startPage,String department){
			String sql="select * from hg_content_management_info where (approve_state=0 or approve_state=5 or (approve_state<>0 and approve_state<>5 and first_approve_id<>'' ) and approve_state<>8) "+
					"and content_user_id ='"+department+"' ";
			StringBuffer buffer=new StringBuffer(sql);
			if (!StringUtils.isEmpty(date)) {
				buffer.append(" AND publish_time>'"+date+" 00:00:00' and publish_time<'"+date+" 24:00:00'");		
			}
			if (!StringUtils.isEmpty(title)) {
				buffer.append(" and content_title like'%"+title+"%'");
			}
			if (!StringUtils.isEmpty(publisher_id)) {
				buffer.append(" and publisher_id='"+publisher_id+"'");
			}
			if (!StringUtils.isEmpty(approve_state)) {
				buffer.append(" and approve_state="+approve_state+"");
			}
			buffer.append(" ORDER BY to_top_time DESC,publish_time DESC limit "+pageSize+" OFFSET "+startPage+"");
//			logger.info(sql);
			return jdbcTemplate.queryForList(buffer.toString());
		}
		
		/**二级党组织发文根据多参数查询*/
		public List<Map<String, Object>> secondPartyContentFind(String date,String title,String publisher_id,String approve_state){
			String sql="select * from hg_content_management_info where approve_state<>0 and approve_state<>5 and approve_state<>8 and first_approve_id='' ";
//			String sql = "select * from hg_content_management_info as co LEFT JOIN hg_party_member as mem "+
//						"ON co.content_user_id = mem.member_identity  "+
//						"where approve_state<>0 and approve_state<>5  "+
//						"AND mem.member_org =  "+
//						"(SELECT member_org from hg_party_member "+
//						"WHERE member_identity='"+user_id+"') "+
//						"and first_approve_id='' ";
			    StringBuffer buffer=new StringBuffer(sql);
			    if (!StringUtils.isEmpty(date)) {
					buffer.append(" AND publish_time>'"+date+" 00:00:00' and publish_time<'"+date+" 24:00:00'");		
				}
			    if (!StringUtils.isEmpty(title)) {
			    	buffer.append(" and content_title like'%"+title+"%'");
				}
			    if (!StringUtils.isEmpty(publisher_id)) {
			    	buffer.append(" and publisher_id='"+publisher_id+"'");
				}
			    if (!StringUtils.isEmpty(approve_state)) {
			    	buffer.append(" and approve_state="+approve_state+"");
			    }
			    buffer.append(" ORDER BY to_top_time DESC,publish_time DESC ");
//			    logger.info(sql);
			    return jdbcTemplate.queryForList(buffer.toString());
		}
		
		/**二级党组织发文根据多参数查询*/
		public List<Map<String, Object>> secondPartyContentFind(String date,String title,String publisher_id,String approve_state,int pageSize,int startPage){
			String sql="select * from hg_content_management_info where approve_state<>0 and approve_state<>5 and approve_state<>7 and approve_state<>8 and first_approve_id='' ";
			    StringBuffer buffer=new StringBuffer(sql);
			    if (!StringUtils.isEmpty(date)) {
					buffer.append(" AND publish_time>'"+date+" 00:00:00' and publish_time<'"+date+" 24:00:00'");		
				}
			    if (!StringUtils.isEmpty(title)) {
			    	buffer.append(" and content_title like'%"+title+"%'");
				}
			    if (!StringUtils.isEmpty(publisher_id)) {
			    	buffer.append(" and publisher_id='"+publisher_id+"'");
				}
			    if (!StringUtils.isEmpty(approve_state)) {
			    	buffer.append(" and approve_state="+approve_state+"");
			    }
			    buffer.append(" ORDER BY to_top_time DESC,publish_time DESC limit "+pageSize+" OFFSET "+startPage+"");
//			    logger.info(sql);
			    return jdbcTemplate.queryForList(buffer.toString());
		}
		
		/**组织部发文根据多参数查询*/
		public List<Map<String, Object>> OrganizationContentFind(String date,String title,String publisher_id/*,String approve_state*/){
			String sql="select * from hg_content_management_info where approve_state=7 or approve_state=3 ";
			StringBuffer buffer=new StringBuffer(sql);
			if (!StringUtils.isEmpty(date)) {
				buffer.append(" AND publish_time>'"+date+" 00:00:00' and publish_time<'"+date+" 24:00:00'");		
			}
			if (!StringUtils.isEmpty(title)) {
				buffer.append(" and content_title like'%"+title+"%'");
			}
			if (!StringUtils.isEmpty(publisher_id)) {
				buffer.append(" and publisher_id='"+publisher_id+"'");
			}
//			if (!StringUtils.isEmpty(approve_state)) {
//				buffer.append(" and approve_state="+approve_state+"");
//			}
			buffer.append(" ORDER BY to_top_time DESC,publish_time DESC ");
//			    logger.info(sql);
			return jdbcTemplate.queryForList(buffer.toString());
		}
		
		/**组织部发文根据多参数查询*/
		public List<Map<String, Object>> OrganizationContentFind(String date,String title,String publisher_id,/*String approve_state,*/int pageSize,int startPage){
			String sql="select * from hg_content_management_info where approve_state=7 or approve_state=3 ";
			StringBuffer buffer=new StringBuffer(sql);
			if (!StringUtils.isEmpty(date)) {
				buffer.append(" AND publish_time>'"+date+" 00:00:00' and publish_time<'"+date+" 24:00:00'");		
			}
			if (!StringUtils.isEmpty(title)) {
				buffer.append(" and content_title like'%"+title+"%'");
			}
			if (!StringUtils.isEmpty(publisher_id)) {
				buffer.append(" and publisher_id='"+publisher_id+"'");
			}
//			if (!StringUtils.isEmpty(approve_state)) {
//				buffer.append(" and approve_state="+approve_state+"");
//			}
			buffer.append(" ORDER BY to_top_time DESC,publish_time DESC limit "+pageSize+" OFFSET "+startPage+"");
//			    logger.info(sql);
			return jdbcTemplate.queryForList(buffer.toString());
		}
		public Map<String, Object> pagenation(int pageNo, int pageSize, String sql) {
			String sql1=sql+" limit "+pageSize+" offset "+(pageNo-1)*pageSize;
		    Map<String, Object> map=new HashMap<>();
			List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1);
			List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql);
			int total=count.size();
			if(total%pageSize==0){
				map.put("totalPage", total/pageSize);
			}else{
				map.put("totalPage", total/pageSize+1);
			}
			map.put("pageNow", pageNo);
			map.put("list",list);
		   return map;
		}
		public Map<String, Object> postGresqlFind(int pageNow, int pageSize, String sql) {
			String sql1=sql+" limit "+pageSize+" offset "+(pageNow-1)*pageSize;
		    Map<String, Object> map=new HashMap<>();
			List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1);
			List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql);
			int total=count.size();
			if(total%pageSize==0){
				map.put("totalPage", total/pageSize);
			}else{
				map.put("totalPage", total/pageSize+1);
			}
			map.put("pageNow", pageNow);
			map.put("list",list);
		   return map;
		}
		public Map<String, Object> postGresqlFind(int pageNow, int pageSize, String sql, String ss) {
			String sql1=sql+" limit "+pageSize+" offset "+(pageNow-1)*pageSize;
		    Map<String, Object> map=new HashMap<>();
			List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1,ss);
			List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql,ss);
			int total=count.size();
			if(total%pageSize==0){
				map.put("totalPage", total/pageSize);
			}else{
				map.put("totalPage", total/pageSize+1);
			}
			map.put("pageNow", pageNow);
			map.put("list",list);
		   return map;
		}
}