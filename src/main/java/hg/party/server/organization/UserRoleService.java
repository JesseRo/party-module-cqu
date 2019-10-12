package hg.party.server.organization;

import java.util.ArrayList;
import java.util.List;

import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.login.UserDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;

@Component(immediate = true, service = UserRoleService.class)
public class UserRoleService {
	@Reference
	private OrgDao orgDao;

	@Reference
	private UserDao userDao;

	public List<String> getRoles(String userId) {
		if (!userDao.exist(userId)) {
			return null;
		}
		List<String> roles = orgDao.findRoleByUserId(userId);
		List<String> chineseRoles = new ArrayList<>();
		chineseRoles.add("普通党员");
		for (String role : roles) {
			String chineseRole = ConstantsKey.ORGTYPE_TO_PERMISSION.get(role);
			if (chineseRole != null) {
				chineseRoles.add(chineseRole);
			}
		}
		return chineseRoles;
	}

	public List<String> getRoles(User user) {
		return getRoles(user.getUser_id());
	}
}