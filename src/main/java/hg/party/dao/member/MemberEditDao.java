package hg.party.dao.member;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import party.memberEdit.MemberEdit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Component(immediate = true,service = MemberEditDao.class)
public class MemberEditDao extends PostgresqlDaoImpl<MemberEdit> {
    public PostgresqlQueryResult<Map<String, Object>> findPage(int page, int size) {
        if (size <= 0){
            size = 10;
        }
        String sql = "select e.*, org.org_name from hg_party_member_edit e left join hg_party_org org on e.member_org = org.org_id" +
                " order by status asc, submit_time desc";
        return postGresqlFindBySql(page, size, sql);
    }

    public PostgresqlQueryResult<Map<String, Object>> searchPage(int page, int size, String search) {
        if (size <= 0){
            size = 10;
        }
        search = "%" + search + "%";
        String sql = "select * from hg_party_visit_count  where user_id like ? or type like ? or remark like ? or ip like ? order by time desc";
        return postGresqlFindBySql(page, size, sql, search, search, search, search);
    }

    public int insertMemberEdit(MemberEdit memberEdit) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "insert into hg_party_member_edit (member_name,member_sex,member_ethnicity,member_birthday, member_identity," +
                "member_degree, member_job, member_join_date,member_fomal_date, member_org, " +
                "member_type,member_address, member_phone_number, member_birth_place, member_mailbox," +
                "member_major_title, member_marriage, member_unit, member_province, member_city, " +
                "member_is_leader,submit_by,submit_time,status)" +
                " values " +
                "(?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?,?," +
                "?,?,?,?)";
        jdbcTemplate.update( new PreparedStatementCreator(){
                                 @Override
                                 public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                                     PreparedStatement ps = conn.prepareStatement(sql, new String [] {"id"});
                                     ps.setString(1, memberEdit.getMember_name());
                                     ps.setString(2, memberEdit.getMember_sex());
                                     ps.setString(3, memberEdit.getMember_ethnicity());
                                     try {
                                         ps.setDate(4,  (Date) sdf.parse(memberEdit.getMember_birthday()));
                                     } catch (ParseException e) {
                                         e.printStackTrace();
                                     }
                                     ps.setString(5, memberEdit.getMember_identity());

                                     ps.setString(6,memberEdit.getMember_degree());
                                     ps.setString(7,memberEdit.getMember_job());
                                     try {
                                         ps.setDate(8, (Date) sdf.parse(memberEdit.getMember_join_date()));
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
                                     ps.setString(18,memberEdit.getMember_unit());
                                     ps.setString(19,memberEdit.getMember_province());
                                     ps.setString(20,memberEdit.getMember_city());

                                     ps.setString(21,memberEdit.getMember_is_leader());
                                     ps.setString(22,memberEdit.getSubmit_by());
                                     ps.setTimestamp(23,memberEdit.getSubmit_time());
                                     ps.setInt(24,memberEdit.getStatus());

                                     return ps;
                                 }
                             },
                keyHolder);
        return keyHolder.getKey().intValue();
    }
}
