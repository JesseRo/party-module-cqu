package hg.party.server;

import hg.party.dao.PartyMeetingNoticeDao;
import hg.party.entity.party.OrgInform;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;
import java.util.Map;

@Component(immediate = true, service = PartyMeetingNoticeService.class)
public class PartyMeetingNoticeService {
    @Reference
    private PartyMeetingNoticeDao partyMeetingNoticeDao;
    public OrgInform findInformDetail(String informId) {
       return  partyMeetingNoticeDao.findInformDetail(informId);
    }

    public List<Map<String, Object>> findInformOrgList(String informId) {
        return  partyMeetingNoticeDao.findInformOrgList(informId);
    }

    public Map<String, Object> findInformAttachFile(String informId) {
        return  partyMeetingNoticeDao.findInformAttachFile(informId);
    }
}
