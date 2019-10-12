package hg.party.dao.personalCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;

/**
 * 文件名称： party<br>
 * 内容摘要：个人中心信息<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月16日下午1:15:12<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate=true,service=PersonalCenterDao.class)
public class PersonalCenterDao extends PostgresqlDaoImpl<Member> {
	
	/**
	 * 根据身份证号查询党员基本信息及支部信息情况
	 * @param memberIdentity
	 * @return
	 */
	public Map<String,Object> findMemberByMemberIdentity(String memberIdentity){
		List<Object> args=new ArrayList<Object>();
		List<Map<String, Object>> members = new ArrayList<>();
		String sql = "SELECT m.* FROM hg_party_member m WHERE m.member_identity=? and m.historic is false ";
		args.add(memberIdentity);
		members = jdbcTemplate.queryForList(sql,memberIdentity);
		if(members.size()>0){
			return members.get(0);
		}else{
			return null;
		}
	}
	
	
	/**
	 * 根据用户账号查询用户基本信息
	 * @param userId
	 * @return
	 */
	public User findUserByUserId(String userId){
		List<Object> args=new ArrayList<Object>();
		List<User> user = new ArrayList<>();
		String sql = "SELECT * FROM hg_users_info u WHERE u.user_id=?";
		args.add(userId);
		RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
		user = jdbcTemplate.query(sql,rowMapper,args.toArray());
		try {
			return user.get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	
	/**
	 * 更改用户电话信息
	 * @param userPhone
	 * @param userId
	 * @return
	 */
	public int updateUserPhone(String userPhone,String memberMailbox,String userPassword,String userId){
		String sql = "UPDATE hg_users_info SET user_telephone=?,user_mailbox=?,user_password=? WHERE user_id=?";
		
		return jdbcTemplate.update(sql,userPhone,memberMailbox,userPassword,userId);
	}
	public int updateMemberPhone(String userPhone,String userId){
		String sql = "UPDATE hg_party_member SET member_phone_number=? WHERE member_identity=? and historic is false ";
		
		return jdbcTemplate.update(sql,userPhone,userId);
	}
	//判断密码是否变更
	public List<User> passWord(String user_id){
		String sql = "SELECT * from hg_users_info WHERE user_id=? ";
		RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
		return this.jdbcTemplate.query(sql, rowMapper,user_id);
	}
}
