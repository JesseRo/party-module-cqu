package hg.party.server.tree;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.tree.CaoTreeDao;
import hg.party.entity.treeInfo.Hg_Tree_Management_Info;

/**
 * @author caoxm
 * 树形结构server管理类
 */

@Component(immediate = true, service = CaoTreeServer.class)
public class CaoTreeServer {
	Logger logger = Logger.getLogger(CaoTreeServer.class);
	@Reference
	private CaoTreeDao caoTreeDao;
	
	public List<Hg_Tree_Management_Info> resListTree(){
		List<Hg_Tree_Management_Info> caoTrees = null;
		try {
			caoTrees = caoTreeDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return caoTrees;
	}
	
	public String resTreeJsonDate(){
		String resUlHtml = "";
		try {
			resUlHtml += "[";
			List<Map<String, Object>> listMaps = caoTreeDao.resParentCaoTrees();
			resUlHtml += resUiHtml(listMaps);
			
			if(resUlHtml.length() > 1){
				resUlHtml = resUlHtml.substring(0, resUlHtml.length() - 1);
			}
			
			resUlHtml += "]";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resUlHtml;
	}
	
	public String resUiHtml(List<Map<String, Object>> listMaps){
		String html = "";
		if(listMaps != null && listMaps.size() > 0){
			for(Map<String, Object> map : listMaps){
				html += "{id:"+map.get("id")+", pId:"+map.get("parent_Id")+", name:'"+map.get("tree_name")+"'},";
				List<Map<String, Object>> listMapTemp = caoTreeDao.reCaoTreesBysParentId(Integer.parseInt(String.valueOf(map.get("id"))));
				if(listMapTemp != null && listMapTemp.size() > 0){
					html += resUiHtml(listMapTemp);
				}
			}
		}
		return html;
	}
	
	public Hg_Tree_Management_Info findById(int id){
		if(id==0) return null;
		return caoTreeDao.findById(id);
	}
	
	/**
	 * 
	 * @return 所有站点栏目信息
	 */
	public List<Hg_Tree_Management_Info> findAll(){
		return caoTreeDao.findAll();
	}
	
	/**
	 * 根据站点查找相应的栏目
	 * @param parentId
	 * @return
	 */
	public List<Hg_Tree_Management_Info> findColumnBySite(int id){
		return caoTreeDao.findColumnBySite(id);
	}
	
	/**
	 * 保存新增站点
	 * @param tree
	 * @return
	 */
	public int saveOrUpdateColumn(Hg_Tree_Management_Info tree){
		return caoTreeDao.saveOrUpdate(tree);
	}
	
	/**
	 * 查询新插入的站点
	 * @param parentId
	 * @param columnName
	 * @return
	 */
	public Hg_Tree_Management_Info findNullColumnId(int parentId,String columnName){
		return caoTreeDao.findNullColumnId(parentId, columnName);
	}
	
	/**
	 * 根据ID删除栏目
	 * @param id
	 * @return
	 */
	public int delById(int id){
		return caoTreeDao.delete(id);
	}
	
	/**
	 * 根据columnId删除站点及站点下的栏目信息
	 * @param columnId
	 * @return
	 */
	public int delByColumnId(int columnId){
		return caoTreeDao.delByColumnId(columnId);
	}	
	/**
	 * 11.2 14:16 钟离妹新增save方法，准备替代saveOrUpdate方法新增站点栏目
	 * @param tree
	 * @return
	 */
	public int save(Hg_Tree_Management_Info tree){
		return caoTreeDao.save(tree);
	}
	
	
	/**两级门户搜索功能
	 * XiongZG
	 * */
	public Map<String,Object> findColumnId(String treeName){
		return caoTreeDao.findColumnId(treeName);
	}
	
	/**查找上级站点id*/
	public String Site_id(String site_id){
		return caoTreeDao.Site_id(site_id);
	}
}
