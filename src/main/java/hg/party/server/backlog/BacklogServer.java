package hg.party.server.backlog;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.backlog.BacklogDao;
import hg.party.entity.organization.PublicInformation;

/**
 * @author zhangzhenqi
 *
 */

@Component(immediate = true, service = BacklogServer.class)
public class BacklogServer {
	@Reference
	private BacklogDao dao;

	public List<PublicInformation> queryAllTasksForSecondCommittee(){
		 List<PublicInformation> taskList = dao.queryAllTasksForSecondCommittee();
		 return taskList;
	}
	
	public PublicInformation findById(int id){
		return dao.findById(id);
	}
}