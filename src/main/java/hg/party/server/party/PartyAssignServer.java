package hg.party.server.party;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.party.PartyAssignDao;
import hg.party.entity.organization.Assign;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月5日下午12:44:08<br>
 */

@Component(immediate = true, service = PartyAssignServer.class)
public class PartyAssignServer {
	@Reference
	private PartyAssignDao partyAssignDao;
	
	//根据登录人查询指派状态
	public List<Assign> AssignName(String userName){
		return partyAssignDao.AssignName(userName);
	}
	//更改指派人状态
	public void saveOrUpdate(Assign assign){
		partyAssignDao.saveOrUpdate(assign);
	}
}
