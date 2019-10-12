package hg.party.dao.resetPasswd;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.login.ResetPasswd;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;

/**
 * 文件名称： party<br>
 * 内容摘要：重置密码<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月10日上午9:26:48<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate=true,service=ResetPasswdDao.class)
public class ResetPasswdDao extends PostgresqlDaoImpl<User> {
	
	/**
	 * 判断用户是否存在
	 * @param userId
	 * @param userName
	 * @return
	 */
	public User findUserByUserIdAndUserName(String userId,String userName){
		List<Object> args=new ArrayList<Object>();
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM hg_users_info u WHERE u.user_id=? AND u.user_name=?";
		args.add(userId);
		args.add(userName);
		RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
		users = jdbcTemplate.query(sql,rowMapper,args.toArray());
		if(users.size()==0){
			return null;
		}else{
			return users.get(0);
		}
	}
	
	
	
	/**
	 * 更改用户密码
	 * @param passwd
	 * @param userId
	 * @return
	 */
	public int updateUserPasswd(String passwd,String userId){
		List<Object> args=new ArrayList<Object>();
		String sql = "UPDATE hg_users_info SET user_password=? WHERE user_id=?";
		args.add(passwd);
		args.add(userId);
		return jdbcTemplate.update(sql, args.toArray());
	}
	
	
	
	/**
	 * 查询用户的邮箱信息
	 * @param itentity
	 * @return
	 */
	public User findUserByIdentity(String itentity){
		List<Object> args=new ArrayList<Object>();
		String sql = "SELECT user_mailbox FROM hg_users_info WHERE user_id=?";
		args.add(itentity);
		RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
		try {
			return jdbcTemplate.query(sql,rowMapper,args.toArray()).get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	/**
	 * 增加一条resetpasswd记录
	 * @param user_id 账号
	 * @param user_name 用户名
	 * @param passwd 生成的密码
	 * @param update_time 修改密码的时间
	 */
	public void saveResetPasswd(String user_id,String user_name,String passwd,Timestamp update_time){
		String sql = "INSERT INTO hg_reset_passwd(user_id,user_name,passwd,update_time) "
					+ "VALUES(?,?,?,?)";
//		jdbcTemplate.execute(sql);
		jdbcTemplate.update(sql,user_id,user_name,passwd,update_time);
	}
	
	
	
	/**
	 * 根据账号查询resetpasswd信息
	 * @param user_id
	 * @return
	 */
	public ResetPasswd findResetPasswdByUserId(String user_id){
		List<Object> args=new ArrayList<Object>();
		String sql = "SELECT * FROM hg_reset_passwd WHERE user_id=? ORDER BY update_time DESC";
		args.add(user_id);
		RowMapper<ResetPasswd> rowMapper = BeanPropertyRowMapper.newInstance(ResetPasswd.class);
		try {
			return jdbcTemplate.query(sql, rowMapper,args.toArray()).get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
