package party.portlet.personal.dao;


import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.memberEdit.MemberEdit;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.entity.PageQueryResult;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component(immediate = true, service = PersonalDao.class)
public class PersonalDao extends PostgresqlDaoImpl<MemberEdit> {
    @Reference
    private RetentionDao retentionDao;

    public PageQueryResult<Map<String, Object>> searchMeetings(int page, int pageSize, String userId, String search) {
        page = Math.max(page, 0);
        pageSize = pageSize <= 0 ? 10 : pageSize;
        List<Object> params = new ArrayList<>();
        params.add(userId);
        String sql = "SELECT\n" +
                "\tinfo.*,\n" +
                "\torg.org_name,\n" +
                "\tcontact.member_name as contact_name\n" +
                "FROM\n" +
                "\thg_party_meeting_member_info\n" +
                "\tM INNER JOIN hg_party_meeting_plan_info info ON M.meeting_id = info.meeting_id\n" +
                "\tINNER JOIN hg_party_org org ON org.org_id = info.organization_id \n" +
                "\tleft join hg_party_member contact on contact.member_identity = info.contact\n" +
                "WHERE\n" +
                "\tM.participant_id = ? \n" +
                "\tand info.task_status > '4'\n";
        if (!StringUtils.isEmpty(search)) {
            sql += "\tAND (info.meeting_theme LIKE ? or info.meeting_type like ?)\n";
            params.add("%" + search + "%");
            params.add("%" + search + "%");
        }
        sql += "ORDER BY\n" +
                "\tinfo.ID DESC";
        return retentionDao.pageBySql(page, pageSize, sql, params);
    }
}
