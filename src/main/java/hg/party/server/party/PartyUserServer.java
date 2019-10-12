package hg.party.server.party;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.party.PartyUserDao;
import hg.party.entity.login.User;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月9日下午4:00:54<br>
 */
@Component(immediate = true, service = PartyUserServer.class)
public class PartyUserServer {
	@Reference
	private PartyUserDao partyUserDao;
	
	//查询用户邮箱
	public List<User> userName(String userId){
		return partyUserDao.userName(userId);
	}
	//通过用户名查询用户id
	public List<User> userId(String userName){
		return partyUserDao.userId(userName);
	}
	
}
