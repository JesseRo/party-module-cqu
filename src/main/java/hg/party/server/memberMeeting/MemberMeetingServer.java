package hg.party.server.memberMeeting;
/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月4日下午2:29:54<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.memberMeeting.MemberMeetingDao;

@Component(immediate = true, service = MemberMeetingServer.class)
public class MemberMeetingServer {

	@Reference
	private MemberMeetingDao memberMeetingDao;
	public void findByMeetingIdAndUserId(String userId,String meetingId){
		memberMeetingDao.findByMeetingIdAndUserId(userId, meetingId);
	}
}
