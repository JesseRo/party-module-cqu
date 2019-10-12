package hg.party.dao.party;
/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月6日上午11:54:12<br>
 */

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.partyMembers.Member;

@Component(immediate = true, service = PartyMemberDao.class)
public class PartyMemberDao extends PostgresqlDaoImpl<Member> {

	// 查询审核人邮箱
	public List<Member> MemberMailbox(String member_name) {
		String sql = "SELECT * from hg_party_member " + "WHERE member_name= ? and historic is false ";
		RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class);
		return this.jdbcTemplate.query(sql, rowMapper, member_name);
	}
	//身份证查询手机号
	public List<Member> Member(String user_id){
		String sql = "SELECT * from hg_party_member WHERE member_identity='"+user_id+"' and historic is false ";
		RowMapper<Member> rowMapper = BeanPropertyRowMapper.newInstance(Member.class);
		return this.jdbcTemplate.query(sql, rowMapper);
	}
	
//获取组织名称通过id
	public List<Map<String, Object>> findOrgNameById(int id  ){
		String sql = "select info.id, org.org_name FROM hg_party_meeting_plan_info as info "
					+"LEFT OUTER JOIN hg_party_org as org on info.organization_id=org.org_id "
					+"where info.id= ? ";
		return jdbcTemplate.queryForList(sql,id);
	}
	public List<Map<String, Object>> findSecondOrgNameById(int id  ){
		String sql = "select * from hg_party_org where org_id=(select org.org_parent FROM hg_party_meeting_plan_info as info "+
					 "LEFT OUTER JOIN hg_party_org as org on info.organization_id=org.org_id "+
					 "where info.id= ?) ";
		return jdbcTemplate.queryForList(sql,id);
	}
	//查询支部人数
	public List<Map<String, Object>> nameNumber(String dep){
		String sql = "SELECT * FROM hg_party_member "+
					"WHERE member_org = ? "+
					"AND historic is false ";
		return jdbcTemplate.queryForList(sql,dep);
	}
	
}
