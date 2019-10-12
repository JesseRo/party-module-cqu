package hg.party.server.organization;

import java.util.List;
import java.util.Map;
import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;
import hg.party.dao.organization.AssigneDao;
import hg.party.entity.organization.Assign;

@Component(immediate=true,service=AssigneService.class)
public class AssigneService {
	AssigneDao dao=new AssigneDao();
	
	public int save(Assign assign){
		return dao.save(assign);
	}
	/**
	 * 获取指派人员
	 * @param userName
	 * @return
	 */
	public List<Map<String, Object>> findPersonInformation(String userName){
		List<Map<String, Object>> info=null;
		if(!StringUtils.isEmpty(userName)){
			info=dao.findPersonInfromation(userName);
		}
		if (info!=null&&info.size()>0) {
			return info;
		}
		return null;
	}
	public List<Map<String, Object>> findBranchByOrgParentId(String parentId){
		List<Map<String, Object>> info=dao.findBranchByOrgParentId(parentId);
		if (info!=null&&info.size()>0) {
			return info;
		}
		return null;
	}
	public int dalete(String id){
		return dao.delete(id);
	}
	public boolean isExite(String userId,String orgId){
		List<Map<String, Object>> list=dao.find(userId,orgId);
		if (list!=null&&list.size()>0) {
			return true;
		}
		return false;
	}

}
