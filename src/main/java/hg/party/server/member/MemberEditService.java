package hg.party.server.member;

import hg.party.dao.member.MemberEditDao;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.memberEdit.MemberEdit;

import java.sql.Timestamp;
import java.util.Date;

@Component(immediate = true, service = MemberEditService.class)
public class MemberEditService {
    @Reference
    private MemberEditDao memberEditDao;

    public int insertMemberEdit(MemberEdit memberEdit){
        memberEdit.setStatus(0);
        memberEdit.setSubmit_time(new Timestamp(new Date().getTime()));
        return memberEditDao.insertMemberEdit(memberEdit);
    }
}
