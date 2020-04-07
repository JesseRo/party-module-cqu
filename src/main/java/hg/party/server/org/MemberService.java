package hg.party.server.org;



import hg.party.dao.org.MemberDao;

import hg.party.entity.partyMembers.Member;
import hg.util.result.Page;
import hg.util.result.PageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;

import java.util.List;


@Component(immediate = true, service = MemberService.class)
public class MemberService {
	@Reference
	private MemberDao memberDao;

	/**
	 * 根据党组织id查询党员
	 * @param orgId
	 * @return
	 */
	public Page pageMembersByOrg(String orgId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum,Page page,String memberType,String history,String keyword) {
		return memberDao.pageMembersByOrg(orgId,partyOrgAdminTypeEnum,page,memberType,history,keyword);
	}

	/**
	 * 根据身份证号查询Member
	 * @param identity
	 * @return
	 */
	public Member findMemberByIdentity(String identity) {
		return memberDao.findByUserId(identity);
	}

    public int updateMember(Member member) {
		return memberDao.updateMember(member);
    }

	public Member findMemberByUser(String userId) {
		return memberDao.findMemberByUser(userId);
	}
}