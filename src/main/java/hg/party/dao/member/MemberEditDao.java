package hg.party.dao.member;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;
import party.constants.PartyOrgAdminTypeEnum;
import party.memberEdit.MemberEdit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Component(immediate = true,service = MemberEditDao.class)
public class MemberEditDao extends PostgresqlDaoImpl<MemberEdit> {
    @Reference
    private OrgDao orgDao;

    public PostgresqlQueryResult<Map<String, Object>> searchPage(int page, int size, String orgId, String search) {
        if (size <= 0){
            size = 10;
        }
        Organization org = orgDao.findOrgByOrgId(String.valueOf(orgId));
        PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(org.getOrg_type());
        StringBuffer sb = new StringBuffer("select e.*, org.org_name from hg_party_member_edit e left join hg_party_org org on e.member_org = org.org_id");
        sb.append(" left join hg_party_org s on org.org_parent = s.org_id left join hg_party_org  o on s.org_parent =o.org_id");
        sb.append(" where 1=1");
        switch(partyOrgAdminTypeEnum){
            case BRANCH:
                sb.append(" and b.org_id = ?");
                break;
            case SECONDARY:
                sb.append(" and s.org_id=? ");
                break;
            case ORGANIZATION:;
                sb.append(" and o.org_id=? ");
                break;
            default: return null;
        }
        if(!StringUtils.isEmpty(search)){
            search = "%" + search + "%";
            sb.append(" and (e.member_name like ? or org.org_name like ?)");
            sb.append(" order by e.status asc,e.submit_time desc");
            return postGresqlFindBySql(page, size, sb.toString(),orgId,search,search);
        }else{
            sb.append(" order by e.status asc,e.submit_time desc");
            return postGresqlFindBySql(page, size, sb.toString(),orgId);
        }
    }

    public int insertMemberEdit(MemberEdit memberEdit) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "insert into hg_party_member_edit (member_name,member_sex,member_ethnicity,member_birthday, member_identity," +
                "member_degree, member_job, member_join_date,member_fomal_date, member_org, " +
                "member_type,member_address, member_phone_number, member_birth_place, member_mailbox," +
                "member_major_title, member_marriage, member_unit, member_province, member_city, " +
                "member_is_leader,submit_by,submit_time,status,job_number,auth_number)" +
                " values " +
                "(?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?,?)";
        jdbcTemplate.update( new PreparedStatementCreator(){
                                 @Override
                                 public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                                     PreparedStatement ps = conn.prepareStatement(sql, new String [] {"id"});
                                     ps.setString(1, memberEdit.getMember_name());
                                     ps.setString(2, memberEdit.getMember_sex());
                                     ps.setString(3, memberEdit.getMember_ethnicity());
                                     try {
                                         ps.setDate(4,  new java.sql.Date(sdf.parse(memberEdit.getMember_birthday()).getTime()));
                                     } catch (ParseException e) {
                                         e.printStackTrace();
                                     }
                                     ps.setString(5, memberEdit.getMember_identity());

                                     ps.setString(6,memberEdit.getMember_degree());
                                     ps.setString(7,memberEdit.getMember_job());
                                     try {
                                         ps.setDate(8, new java.sql.Date(sdf.parse(memberEdit.getMember_join_date()).getTime()));
                                     } catch (ParseException e) {
                                         e.printStackTrace();
                                     }
                                     ps.setString(9,memberEdit.getMember_fomal_date());
                                     ps.setString(10,memberEdit.getMember_org());

                                     ps.setString(11, memberEdit.getMember_type());
                                     ps.setString(12, memberEdit.getMember_address());
                                     ps.setString(13, memberEdit.getMember_phone_number());
                                     ps.setString(14, memberEdit.getMember_birth_place());
                                     ps.setString(15, memberEdit.getMember_mailbox());

                                     ps.setString(16,memberEdit.getMember_major_title());
                                     ps.setString(17,memberEdit.getMember_marriage());
                                     ps.setInt(18,memberEdit.getMember_unit());
                                     ps.setString(19,memberEdit.getMember_province());
                                     ps.setString(20,memberEdit.getMember_city());

                                     ps.setString(21,memberEdit.getMember_is_leader());
                                     ps.setInt(22,memberEdit.getSubmit_by());
                                     ps.setTimestamp(23,memberEdit.getSubmit_time());
                                     ps.setInt(24,memberEdit.getStatus());
                                     ps.setString(24,memberEdit.getJobNumber());
                                     ps.setString(25,memberEdit.getAuthNumber());
                                     return ps;
                                 }
                             },
                keyHolder);
        return keyHolder.getKey().intValue();
    }

    public MemberEdit findLatestMemberEdit(int id) {
        String sql = "select * from hg_party_member_edit where submit_by = ? order by submit_time desc ";
        RowMapper<MemberEdit> rowMapper = BeanPropertyRowMapper.newInstance(MemberEdit.class);
        List<MemberEdit> memberEditList =  this.jdbcTemplate.query(sql,rowMapper,id);
        if(memberEditList.size()>0){
            return memberEditList.get(0);
        }else{
            return null;
        }
    }

    public int approvalMemberEdit(int memberEditId, int status, String reason) {
        String sql = "UPDATE hg_party_member_edit SET status=?,reason=? WHERE id=?";
        return  this.jdbcTemplate.update(sql,status,reason,memberEditId);
    }

    public MemberEdit findMemberEditDetailById(int id) {
        String sql = "select e.*,u.unit_name from hg_party_member_edit e left join hg_party_unit u on e.member_unit = u.id where e.id = ? order by e.submit_time desc";
        RowMapper<MemberEdit> rowMapper = BeanPropertyRowMapper.newInstance(MemberEdit.class);
        List<MemberEdit> memberEditList =  this.jdbcTemplate.query(sql,rowMapper,id);
        if(memberEditList.size()>0){
            return memberEditList.get(0);
        }else{
            return null;
        }
    }
}
