package hg.party.server.party;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.party.PartyMemberDao;
import hg.party.dao.party.PartyOrgInformDao;
import hg.party.entity.party.OrgInform;
import hg.party.entity.partyMembers.Member;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月6日下午12:00:27<br>
 */
@Component(immediate = true, service = PartyMemberServer.class)
public class PartyMemberServer {
	@Reference
	private PartyMemberDao partyMemberDao;
	@Reference
	private PartyOrgInformDao partyOrgInformDao;
	
	//查询审核人邮箱
	public List<Member> MemberMailbox(String member_name){
		return partyMemberDao.MemberMailbox(member_name);
	}
	//根据通知id查询状态
	public List<OrgInform> orgInform(String inform_id){
		return partyOrgInformDao.orgInform(inform_id);
	}
	//身份证查询手机号
	public List<Member> Member(String user_id){
		return partyMemberDao.Member(user_id);
	}
	
	
	public List<Map<String, Object>> findOrgNameById(int id  ){
		return partyMemberDao.findOrgNameById(id);
	}
	public List<Map<String, Object>> findSecondOrgNameById(int id  ){
		return partyMemberDao.findSecondOrgNameById(id);
	}
	//查询支部人数
	public List<Map<String, Object>> nameNumber(String dep){
		return partyMemberDao.nameNumber(dep);
	}
	public List<Member> findMeetingPlanMember(String meetingId){
		return partyMemberDao.findMeetingPlanMember(meetingId);
	}
}
