package hg.party.server.dwonlistserver;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.downlistdao.DownListDao;
import hg.party.entity.party.Hg_Value_Attribute_Info;

@Component(immediate=true,service=DownListServer.class)
public class DownListServer {
	@Reference
	private DownListDao listDao;
	public List<Hg_Value_Attribute_Info> findAll(){
		return listDao.findAll();
	}
	
	//分页
	public Map<String, Object> postGresqlFind(int pageNo,int pageSize,String sql){
		return listDao.postGresqlFind(pageNo, pageSize, sql);
	}
	//分页
	public Map<String, Object> postGresqlFind(int pageNo,int pageSize,String sql,String s1,String s2,String s3){
		return listDao.postGresqlFind(pageNo, pageSize, sql,s1,s2,s3);
	}
	//根据id查询
	public Hg_Value_Attribute_Info findById(int id){
		return listDao.findById(id);
	}
	
	//新增或修改
	public void SaveOrUpdate(Hg_Value_Attribute_Info id){
		listDao.saveOrUpdate(id);
	}
	
	//删除
	public void deleteById(int id){
		listDao.delete(id);
	}
	//驳回理由
	public List<Hg_Value_Attribute_Info> reasson(){
		return listDao.reasson();
	}
	//党内职务
	public List<Hg_Value_Attribute_Info> positior(){
		return listDao.positior();
	}
	//学生宿舍园区
	public List<Hg_Value_Attribute_Info> room(){
		return listDao.room();
	}
	//查询否有重复
	public List<Hg_Value_Attribute_Info> repeat(String val,String type){
		return listDao.repeat(val,type);
	}
	
}

