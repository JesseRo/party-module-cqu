package hg.party.dao.meetingPlan;

import hg.party.entity.meetingPlan.MeetingPlanMemberGroup;
import hg.party.entity.organization.Organization;
import hg.util.postgres.HgPostgresqlDaoImpl;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;
import party.constants.PartyOrgAdminTypeEnum;

import java.util.Map;

@Component(immediate = true,service = MeetingPlanMemberGroupDao.class)
public class MeetingPlanMemberGroupDao extends HgPostgresqlDaoImpl<MeetingPlanMemberGroup> {

    public PostgresqlPageResult<Map<String, Object>> searchMemberGroupPage(int page, int size, String orgId, String search) {
        if (size <= 0){
            size = 10;
        }
        StringBuffer sb = new StringBuffer("SELECT * from hg_party_group_org_info WHERE organization_id=? and group_state='1'");
        if(!StringUtils.isEmpty(search)){
            search = "%" + search + "%";
            sb.append(" and group_name like ?");
            sb.append(" ORDER BY id desc");
            return postGresqlFindPageBySql(page, size, sb.toString(),orgId,search);
        }else{
            sb.append(" ORDER BY id desc");
            return postGresqlFindPageBySql(page, size, sb.toString(),orgId);
        }
    }
}
