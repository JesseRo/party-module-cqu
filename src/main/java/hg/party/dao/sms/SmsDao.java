package hg.party.dao.sms;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.memberMeeting.MemberMeeting;
import hg.party.entity.partyMembers.Member;
import hg.party.entity.sms.SmsStatus;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

@Component(immediate = true, service = SmsDao.class)
public class SmsDao extends PostgresqlDaoImpl<SmsStatus>{
    public void saveOne(SmsStatus smsStatus){
        String sql = "insert into hg_party_sms(\"event_id\", \"sms_identifier\", \"sms_status\") values('%s', '%s', '%s')";
        sql = String.format(sql, smsStatus.getEvent_id(), smsStatus.getSms_identifier(), smsStatus.getSms_status());
        jdbcTemplate.execute(sql);
    }

    public List<SmsStatus> findByEvent(String eventId){
        String sql = "select * from hg_party_sms where event_id = ?";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SmsStatus.class),eventId);
        }catch (Exception e){
            return  null;
        }
    }
    
    public List<Member> findMembers(String like){
        String sql = "select * from hg_party_member where cast(member_join_date as text ) LIKE '"+like+"' and historic is FALSE";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        }catch (Exception e){
            return  null;
        }
    }
}
