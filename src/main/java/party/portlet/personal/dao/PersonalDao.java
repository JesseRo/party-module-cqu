package party.portlet.personal.dao;


import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;

import org.osgi.service.component.annotations.Component;
import party.memberEdit.MemberEdit;

import java.util.Map;

@Component(immediate = true,service = PersonalDao.class)
public class PersonalDao extends PostgresqlDaoImpl<MemberEdit> {
    public  PostgresqlQueryResult<Map<String, Object>> meetings(int page, int pageSize, String userId){
        page = Math.max(page, 0);
        pageSize = pageSize <= 0 ? 10 : pageSize;
        String sql = "SELECT info.*, org.org_name FROM hg_party_meeting_member_info m " +
                "inner join hg_party_meeting_plan_info info on m.meeting_id = info.meeting_id " +
                "inner join hg_party_org org on org.org_id = info.organization_id " +
                "where m.participant_id = ? order by info.id desc";
        return postGresqlFindBySql(page, pageSize, sql, userId);
    }

    public  PostgresqlQueryResult<Map<String, Object>> searchMeetings(int page, int pageSize, String userId, String search){
        page = Math.max(page, 0);
        pageSize = pageSize <= 0 ? 10 : pageSize;
        String sql = "SELECT info.*, org.org_name FROM hg_party_meeting_member_info m " +
                "inner join hg_party_meeting_plan_info info on m.meeting_id = info.meeting_id " +
                "inner join hg_party_org org on org.org_id = info.organization_id " +
                "where m.participant_id = ? and info.meeting_theme like ? order by info.id desc";
        return postGresqlFindBySql(page, pageSize, sql, userId, "%" + search + "%");
    }
}
