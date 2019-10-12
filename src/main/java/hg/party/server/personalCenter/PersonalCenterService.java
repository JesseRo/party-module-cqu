package hg.party.server.personalCenter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import hg.party.dao.personalCenter.PersonalCenterDao;
import hg.party.entity.login.User;

/**
 * 文件名称： party<br>
 * 内容摘要： 个人中心<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月16日下午1:51:01<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate=true,service=PersonalCenterService.class)
public class PersonalCenterService {
	@Reference
	private PersonalCenterDao dao;
	
	
	/**
	 * 根据用户账号查询用户基本信息
	 * @param userId
	 * @return
	 */
	public Map<String,Object> findUserInfo(String userId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map = new HashMap<>();
		
		User user = dao.findUserByUserId(userId);
		Map<String,Object> member = dao.findMemberByMemberIdentity(user.getUser_id());
		try {
			if(!member.isEmpty()){
//				map.put("org_name", member.get("org_name"));//支部信息
				map.put("member_sex", member.get("member_sex"));//性别
				
				java.util.Date mydate = sdf.parse(member.get("member_birthday")+"");
				map.put("member_age", userAge(mydate));//年龄
				
				map.put("member_phone_number", member.get("member_phone_number"));//电话号码
				map.put("member_birthday", member.get("member_birthday"));//出生日期
				map.put("member_join_date", member.get("member_join_date"));//入党时间
				map.put("member_degree", member.get("member_degree"));//学历
				map.put("user_ethnicity", member.get("member_ethnicity"));//民族
			}
		} catch (Exception e) {
		}
		map.put("member_name", user.getUser_name());//姓名
		map.put("member_mailbox", user.getUser_mailbox());//邮箱
//		map.put("member_phone_number", user.getUser_telephone());//电话
		map.put("user_id", user.getUser_id());//身份证号
		map.put("user_role", user.getUserrole());//角色
//		map.put("user_ethnicity", user.getMember_ethnicity());//民族
		map.put("user_password", user.getUser_password());//账号密码
		return map;
	}
	
	
	
	/**
	 * 更改用户电话信息
	 * @param userPhone
	 * @param userId
	 * @return
	 */
	public int updateUserPhone(String userPhone,String memberMailbox,String userPassword,String userId){
		return dao.updateUserPhone(userPhone,memberMailbox,userPassword,userId);
	}
	public int updateMemberPhone(String userPhone,String userId){
		return dao.updateMemberPhone(userPhone,userId);
	}
	
	//由出生日期获得年龄  
	public static int userAge(Date dateOfBirth) {
		        int age = 0;
		        Calendar born = Calendar.getInstance();
		        Calendar now = Calendar.getInstance();
		        if (dateOfBirth != null) {
		            now.setTime(new Date());
		            born.setTime(dateOfBirth);
		            if (born.after(now)) {
		                throw new IllegalArgumentException("年龄不能超过当前日期");
		            }
		            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
		            int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
		            int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
		            System.out.println("nowDayOfYear:" + nowDayOfYear + " bornDayOfYear:" + bornDayOfYear);
		            if (nowDayOfYear < bornDayOfYear) {
		                age -= 1;
		            }
		        }
		        return age;
		    }
		
		    public static void main(String[] args) throws Exception {
		        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		        java.util.Date mydate = myFormatter.parse("2013-09-29");
//		        System.out.println(userAge(mydate));
		    }
		    //查看密码是否变更
		    public List<User> passWord(String user_id){
		    	return dao.passWord(user_id);
		    }
}
