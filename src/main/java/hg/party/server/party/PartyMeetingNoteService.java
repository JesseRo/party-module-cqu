package hg.party.server.party;

import hg.party.dao.party.PartyMeetingNoteDao;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


import java.util.Map;



@Component(immediate = true, service = PartyMeetingNoteService.class)
public class PartyMeetingNoteService {
    @Reference
    private PartyMeetingNoteDao partyMeetingNoteDao;

    public PostgresqlPageResult<Map<String, Object>> meetingNotePageAndSearch(int page, int size, String orgId, String keyword) {
       return  partyMeetingNoteDao.meetingNotePageAndSearch(page,size,orgId,keyword);
    }
}
