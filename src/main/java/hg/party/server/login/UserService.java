package hg.party.server.login;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import com.alibaba.fastjson.JSONObject;
import dt.session.SessionManager;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.VisitCount;
import hg.party.server.CacheCore;
import hg.party.server.organization.VisitCountService;
import hg.party.server.partyBranch.PartyBranchService;
import hg.util.ConstantsKey;
import hg.util.MD5;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import hg.party.dao.login.UserDao;
import hg.party.entity.login.User;
import org.osgi.service.component.annotations.Reference;


@Component(immediate = true, service = UserService.class)
public class UserService {
    Logger logger = Logger.getLogger(UserService.class);

    @Reference
    private UserDao userDao;
    @Reference
    OrgDao orgDao;
    @Reference
    private PartyBranchService partyBranchService;
    @Reference
    private VisitCountService visitCountService;
    @Reference
    private CacheCore cacheCore;
    private static final String USER_INFO_KEY = "userInfo";
    /**
     * 通过账号查询用户信息
     */
    public User findByUserId(String id) {
        User tt = new User();
        List<Map<String, Object>> listMap = userDao.findAllByUserId(id);
        if (listMap.size() > 0) {
            Map<String, Object> map = listMap.get(0);
            tt.setId(Integer.parseInt(map.get("_id") + ""));
            tt.setUser_password((String) map.get("user_password"));
            tt.setUser_id((String) map.get("user_id"));
            tt.setUser_name((String) map.get("user_name"));
            tt.setUser_department_id((String) map.get("user_department_id"));
            tt.setState(map.get("state").toString());
        } else {
            tt = null;
        }
        return tt;
    }

    public String findRole(String userId) {
        List<Map<String, Object>> roles = userDao.findRoleByUserID(userId);
        if (roles != null && roles.size() > 0) {
            String role = roles.get(0).get("userrole").toString();
            return role;
        }
        return null;
    }

    public List<String> myRoles(String userId) {
        return orgDao.findRoleByUserId(userId);
    }

    public boolean isRole(String userId, String role) {
        List<String> roles = myRoles(userId);
        String orgType = ConstantsKey.PERMISSION_TO_ORGTYPE.get(role);
        if (orgType == null) {
            if (role.equalsIgnoreCase(ConstantsKey.COMMON_PARTY)) {
                return true;
            } else {
                return false;
            }
        }
        return roles.contains(orgType);
    }


    /**
     * 根据公众号查询用户信息
     *
     * @param ethnicity
     * @return
     */
    public User findUserByEthnicity(String ethnicity) {
        return userDao.findUserByEthnicity(ethnicity);
    }

    public String login(String userName, String password, String role, String sessionId, String ip) {
        User user;
        try {
            user = findByUserId(userName);
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        }
        //判断用户不存在
        if (user == null) {
            return "1";
        }
        //判断用户密码错误
        else if (!user.getUser_password().equals(password)) {
            return "2";
        }
        //登录成功
        else {
            /**用户中文名*/
            List<String> roles = myRoles(userName);
            if (roles.contains(ConstantsKey.ORG_TYPE_ROOT)) {
                role = ConstantsKey.ORG_PARTY;
            } else if (roles.contains(ConstantsKey.ORG_TYPE_SECONDARY)) {
                role = ConstantsKey.SECOND_PARTY;
            } else if (roles.contains(ConstantsKey.ORG_TYPE_BRANCH)) {
                role = ConstantsKey.BRANCH_PARTY;
            } else {
                role = ConstantsKey.COMMON_PARTY;
            }
            return afterLogin(user, role, sessionId, ip, false);
        }
    }

    private String afterLogin(User user, String role, String sessionId, String ip, boolean changeRole) {
        String userName = user.getUser_id();
        String name = user.getUser_name();
        SessionManager.setAttribute(sessionId, "userName", userName);
        SessionManager.setAttribute(sessionId, "user_name", name);
        String orgId = userDao.findOrgId(role, userName, user.getUser_department_id());
        SessionManager.setAttribute(sessionId, "department", orgId);
        SessionManager.setAttribute(sessionId, "role", role);
        String orgType = partyBranchService.findSconedAndBranch(orgId);
        SessionManager.setAttribute(sessionId, "orgType", orgType);
        SessionManager.setAttribute(sessionId, "orgId", user.getUser_department_id());
        SessionManager.setAttribute(sessionId, "loginCount", 1);
        UserInfo userInfo = new UserInfo();
        userInfo.setDepartment(orgId);
        userInfo.setMemberId(userName);
        userInfo.setOrgId(user.getUser_department_id());
        userInfo.setUserId(user.getId());
        cacheCore.getJedis().hsetnx(String.format("baixun:session:%s", MD5.getMD5(sessionId)), USER_INFO_KEY, JSONObject.toJSONString(userInfo));
        //判断是否是第一次登陆
        boolean bool = isFirstLogin(userName, name);
        SessionManager.setAttribute(sessionId, "firstLogin", bool);
        VisitCount count = new VisitCount();
        count.setDepartment_id(orgId);
        count.setIp(ip);
        count.setUser_role(role);
        count.setUser_id(userName);
        count.setUser_name(name);
        count.setType("登陆系统");
        LocalDateTime ldTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        Timestamp t = Timestamp.valueOf(ldTime);
        count.setVisit_time(t);
        if (ConstantsKey.COMMON_PARTY.equals(role)) {
            count.setDepartment_name(name);
        } else if (ConstantsKey.BRANCH_PARTY.equals(role)) {
            count.setDepartment_name(visitCountService.findOrgNameByBranchId(orgId) + "  " + visitCountService.findOrgNameByOrgId(orgId));
        } else {
            count.setDepartment_name(visitCountService.findOrgNameByOrgId(orgId));
        }
        visitCountService.save(count);
        logger.info("dateLong:  " + t);
        String url;
        if (ConstantsKey.SECOND_PARTY.equals(role)) {
            url = "/backlogtwo";
//            url = "/screen";
        } else if (ConstantsKey.ORG_PARTY.equals(role)) {
            url = "/screen";
            if (changeRole) {
                url = "/statisticalreport";
            }
        } else if (ConstantsKey.BRANCH_PARTY.equals(role)) {
            url = "/backlogtwo";
        } else if (ConstantsKey.COMMON_PARTY.equals(role)) {
            url = "/personalcenter";
        } else if (ConstantsKey.OTHER_PARTY.equals(role)) {
            url = "/membertodolist";
        } else if (ConstantsKey.ORG_PROPAGANDA.equals(role)) {
            url = "/passpublic";
        } else {
            url = "/errorpage";
        }
        return "3" + url;//成功登录
    }

    public String changeRole(String userName, String role, String sessionId, String ip) {
        User user;
        try {
            user = findByUserId(userName);
        } catch (Exception e) {
            user = null;
        }
        //判断用户不存在
        if (user == null) {
            return "1";
        } else {
            boolean isRole = isRole(userName, role);
            if (isRole) {
                return afterLogin(user, role, sessionId, ip, true);
            } else {
                return "0"; //角色不匹配
            }
        }
    }

    public String loginCas(String userName, String sessionId, String ip) {
        User user;
        try {
            user = findByUserId(userName);
        } catch (Exception e) {
            user = null;
        }
        //判断用户不存在
        if (user == null) {
            return "1";
        } else {
            String role;
            List<String> roles = myRoles(userName);
            if (roles.contains(ConstantsKey.ORG_TYPE_ROOT)) {
                role = ConstantsKey.ORG_PARTY;
            } else if (roles.contains(ConstantsKey.ORG_TYPE_SECONDARY)) {
                role = ConstantsKey.SECOND_PARTY;
            } else if (roles.contains(ConstantsKey.ORG_TYPE_BRANCH)) {
                role = ConstantsKey.BRANCH_PARTY;
            } else {
                role = ConstantsKey.COMMON_PARTY;
            }
            return afterLogin(user, role, sessionId, ip, false);
        }
    }

    public boolean isFirstLogin(String userId, String userName) {
        List<Map<String, Object>> list = userDao.findLogin(userId);
        if (list != null && list.size() > 0) {
            return true;
        } else {
            userDao.insertLogin(userId, userName);
            return false;
        }
    }

    public int updateUserInfo(User user) {
        return userDao.updateUserInfo(user);
    }

    public User findById(int id) {
        return userDao.findById(id);
    }
}
