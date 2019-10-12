package hg.party.server.chainingServer;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.chainingDao.ChainingManagementInfoDao;
import hg.party.entity.chainingInfo.Hg_Chaining_Management_Info;

/**
 * @author zhangminggang
 *
 */

@Component(immediate = true, service = ChainingManagementInfoServer.class)
public class ChainingManagementInfoServer {
	@Reference
	private ChainingManagementInfoDao dao;

	public List<Hg_Chaining_Management_Info> findAll(){
		return dao.findAll();
	}
	
	public Hg_Chaining_Management_Info findById(int id){
		return dao.findById(id);
	}
	

	public void deleteById(int id){
		dao.delete(id);
	}
	
	

	public void saveOrUpdate(Hg_Chaining_Management_Info chainManagementInfo){
		dao.saveOrUpdate(chainManagementInfo);
	}
	

	public List<Hg_Chaining_Management_Info> findbytitle(String chainingName){
		List<Hg_Chaining_Management_Info> list = null;
		try {
			list = dao.findbytitle(chainingName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 根据类型查询链接信息
	 * @param chaining_type
	 * @param chaining_typeval
	 * @return
	 */
	public List<Hg_Chaining_Management_Info> findByChainingType(String chaining_type,String chaining_typeval){
		List<Hg_Chaining_Management_Info> list = null;
		try {
			list = dao.findByChainingType(chaining_type, chaining_typeval);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
