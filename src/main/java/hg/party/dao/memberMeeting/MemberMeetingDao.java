package hg.party.dao.memberMeeting;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.memberMeeting.MemberMeeting;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月4日下午2:08:05<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true,service = MemberMeetingDao.class)
public class MemberMeetingDao extends PostgresqlDaoImpl<MemberMeeting> {

	/**根据会议id和用户id修改用户该会议的查看状态*/
	public void findByMeetingIdAndUserId(String userId,String meetingId){
		String sql="UPDATE hg_party_meeting_member_info SET check_status='已查看' WHERE meeting_id= ? AND participant_id= ? ";
		//this.jdbcTemplate.execute(sql);
		jdbcTemplate.update(sql,meetingId,userId);
	}
}
