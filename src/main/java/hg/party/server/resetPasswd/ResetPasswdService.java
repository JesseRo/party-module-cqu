package hg.party.server.resetPasswd;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import hg.party.dao.resetPasswd.ResetPasswdDao;
import hg.party.entity.login.ResetPasswd;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;


/**
 * 文件名称： party<br>
 * 内容摘要：重置密码<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月10日上午9:30:25<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate=true,service=ResetPasswdService.class)
public class ResetPasswdService {
	@Reference
	private ResetPasswdDao dao;
	
	
	/**
	 * 判断用户是否存在
	 * @param userId
	 * @param userName
	 * @return
	 */
	public User findUserByUserIdAndUserName(String userId,String userName){
		return dao.findUserByUserIdAndUserName(userId, userName);
	}
	
	
	/**
	 * 更改用户密码
	 * @param passwd
	 * @param identity
	 * @return
	 */
	public int updateUserPasswd(String passwd,String identity){
		return dao.updateUserPasswd(passwd, identity);
	}
	
	
	/**
	 * 查询用户的邮箱信息
	 * @param itentity
	 * @return
	 */
	public User findMemberByIdentity(String itentity){
		return dao.findUserByIdentity(itentity);
	}
	
	
	/**
	 * 增加一天resetpasswd记录
	 * @param user_id 账号
	 * @param user_name 用户名
	 * @param passwd 生成的密码
	 * @param update_time 修改密码的时间
	 */
	public void saveResetPasswd(String user_id,String user_name,String passwd,Timestamp update_time){
		dao.saveResetPasswd(user_id, user_name, passwd, update_time);
	}
	
	
	
	/**
	 * 根据账号查询resetpasswd信息
	 * @param user_id
	 * @return
	 */
	public ResetPasswd findResetPasswdByUserId(String user_id){
		return dao.findResetPasswdByUserId(user_id);
	}
}
