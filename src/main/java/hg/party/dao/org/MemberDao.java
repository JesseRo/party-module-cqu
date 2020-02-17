package hg.party.dao.org;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.dt.springjdbc.dao.impl.QueryResult;
import hg.party.entity.partyMembers.Member;
import hg.util.MD5;
import party.portlet.org.NotMatchingExcelDataException;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.object.SqlCall;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component(immediate = true, service = MemberDao.class)
@Transactional
public class MemberDao extends PostgresqlDaoImpl<Member> {
    private static final int PAGE_SIZE = 10;

    public List<Member> findAll() {
        String sql = "select * from hg_party_member where historic is false";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
    }
    public List<Member> findMemberByUserId(String userId) {
        String sql = "select * from hg_party_member where historic is false and member_identity= ? ";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class),userId);
    }

    public List<Member> findMemberByUserId(List<String> userIds) {
        if (userIds == null || userIds.isEmpty())
        {
            return Collections.emptyList();
        }
        String symbols = userIds.stream().map(p->"?").collect(Collectors.joining(","));
        String sql = "select * from hg_party_member where historic is false and member_identity in (" + symbols + ") ";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class), userIds.toArray(new Object[0]));
    }

    public Member findByUserId(String userId) {
        String sql = "select * from hg_party_member where historic is false and member_identity= ? ";
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Member.class),userId);
    }

    public List<Map<String , Object>> findMemeberJob() {
    	String sql = "select DISTINCT member_job  from hg_party_member where member_job is not null";
    	return jdbcTemplate.queryForList(sql);
    }

    public List<Member> findAllHistoric() {
        String sql = "select * from hg_party_member";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
    }

    public QueryResult<Member> findByOrg(String orgId, int page) {
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * PAGE_SIZE;
        String sql = "select * from hg_party_member where  historic = false and member_org = '" + orgId + "' ORDER BY \"id\" DESC limit " + PAGE_SIZE + " offset " + offset;
        String countSql = "select count(*) from hg_party_member where  historic = false and member_org = '" + orgId + "'";
        List<Member> members = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        try {
            int count = jdbcTemplate.queryForObject(countSql, Integer.class);
            return new QueryResult<>(members, count);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    //查询党支部人员包含历史记录
    public QueryResult<Member> findByOrgHistory(String orgId, int page) {
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * PAGE_SIZE;
        String sql = "select * from hg_party_member where member_org = '" + orgId + "' ORDER BY \"id\" DESC limit " + PAGE_SIZE + " offset " + offset;
        String countSql = "select count(*) from hg_party_member where  member_org = '" + orgId + "'";
        List<Member> members = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        try {
            int count = jdbcTemplate.queryForObject(countSql, Integer.class);
            return new QueryResult<>(members, count);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    //查询二级党组织
    public QueryResult<Member> findByOrgLevel(String orgId, int page) {
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * PAGE_SIZE;
        String sql = "select * from hg_party_member "+
                     "where  historic = false and member_org "+
                     "in (select org_id as member_org from  hg_party_org where  historic is FALSE and org_parent = '"+orgId+"') limit " + PAGE_SIZE + " offset " + offset;
        String countSql = "select count(*) from hg_party_member "+
			              "where  historic = false and member_org "+
			              "in (select org_id as member_org from  hg_party_org where historic is FALSE and  org_parent = '"+orgId+"')" ;
        List<Member> members = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        try {
            int count = jdbcTemplate.queryForObject(countSql, Integer.class);
            return new QueryResult<>(members, count);
        } catch (Exception e) {
           System.out.println(e);
            return null;
        }
    }
    //查询二级党组织(历史纪录)
    public QueryResult<Member> findByOrgLevelHistory(String orgId, int page) {
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * PAGE_SIZE;
        String sql = "select * from hg_party_member "+
                     "where  member_org "+
                     "in (select org_id as member_org from  hg_party_org where org_parent = '"+orgId+"') limit " + PAGE_SIZE + " offset " + offset;
        String countSql = "select count(*) from hg_party_member "+
			              "where  member_org "+
			              "in (select org_id as member_org from  hg_party_org where org_parent = '"+orgId+"')" ;
        List<Member> members = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        try {
            int count = jdbcTemplate.queryForObject(countSql, Integer.class);
            return new QueryResult<>(members, count);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    //查询全部
    public QueryResult<Member> findByOrgAll(String orgId, int page) {
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * PAGE_SIZE;
        String sql = "select * from hg_party_member where  historic = false limit " + PAGE_SIZE + " offset " + offset;
        String countSql = "select count(*) from hg_party_member where  historic = false ";
        List<Member> members = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        try {
            int count = jdbcTemplate.queryForObject(countSql, Integer.class);
            return new QueryResult<>(members, count);
        } catch (Exception e) {
            return null;
        }
    }
    //查询全部(历史记录)
    public QueryResult<Member> findByOrgAll(int page) {
        if (page < 1) {
            page = 1;
        }
        int offset = (page - 1) * PAGE_SIZE;
        String sql = "select * from hg_party_member limit " + PAGE_SIZE + " offset " + offset;
        String countSql = "select count(*) from hg_party_member ";
        List<Member> members = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        try {
            int count = jdbcTemplate.queryForObject(countSql, Integer.class);
            return new QueryResult<>(members, count);
        } catch (Exception e) {
            return null;
        }
    }
    //判断是否为二级党组织
    /*public List<Member> ment(String orgName) {
        String sql = "SELECT * FROM hg_party_member WHERE member_party_committee='" + orgName + "' ";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
    }*/
    public String ment(String orgId) {
       String sql = "select org_type from hg_party_org where org_id = ? ";
       List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,orgId);
       if (list!=null&&list.size()>0) {
		return list.get(0).get("org_type")+"";
	   }
       return null;
    }

    public List<Member> findByOrg(String orgId) {
        String sql = "select * from hg_party_member where historic = false and member_org = ?";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class), orgId);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public List<Member> findByOrg(List<String> orgIds) {
        if (orgIds == null || orgIds.size() == 0) {
            return null;
        }
        String sql = "select * from hg_party_member where historic = false and member_org in ('" + String.join("','", orgIds) + "') order by member_party_committee, member_org";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    public List<Member> findByOrg(List<String> orgIds,boolean is) {
        if (orgIds == null || orgIds.size() == 0) {
            return null;
        }
        String sql ="";
        if (is) {
        	sql = "select * from hg_party_member where member_org in ('" + String.join("','", orgIds) + "') order by member_party_committee, member_org";

		}else {
	         sql = "select * from hg_party_member where historic = false and member_org in ('" + String.join("','", orgIds) + "') order by member_party_committee, member_org";
	
		}
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Member.class));
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public List<Member> findByIdentity(List<String> ids) {
        if (ids == null || ids.size() == 0) {
            return null;
        }
        String idSql = String.join("','", ids);
        String sql = "SELECT * from hg_party_member where member_identity in ('%s')";
        try {
            return jdbcTemplate.query(String.format(sql, idSql), BeanPropertyRowMapper.newInstance(Member.class));
        } catch (Exception e) {
            return null;
        }
    }

    public void updateAll(List<Member> members) {
        if (members == null || members.size() == 0) {
            return;
        }
        Field[] fields = Member.class.getDeclaredFields();

        List<Field> fieldList = Stream.of(fields)
                .peek(p -> p.setAccessible(true))
                .filter(p -> !(p.getName().equalsIgnoreCase("historic") || p.getName().equalsIgnoreCase("id")))
                .collect(Collectors.toList());
        StringBuilder sqlBuilder = new StringBuilder();
        for (Member member : members) {
            sqlBuilder.append("update hg_party_member set ");
            List<String> fieldStrs = new ArrayList<>();
            for (Field field : fieldList) {
                StringBuilder fieldBuilder = new StringBuilder();
                Object vObject;
                fieldBuilder.append(field.getName()).append(" = ");
                ;
                try {
                    vObject = field.get(member);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                    vObject = null;
                }
                if (vObject == null) {
                    fieldBuilder.append("null");
                } else {
                    fieldBuilder.append("'" + vObject.toString() + "'");
                }
                fieldStrs.add(fieldBuilder.toString());
            }
            sqlBuilder.append(String.join(",", fieldStrs));
            sqlBuilder.append(" where historic = false and member_identity = '")
                    .append(member.getMember_identity()).append("';\n");
        }
        jdbcTemplate.execute(sqlBuilder.toString());
    }

    public void historicAll(List<Member> members) {
        if (members == null || members.size() == 0) {
            return;
        }
        StringBuilder sqlBuilder = new StringBuilder();
        for (Member org : members) {
            sqlBuilder.append("update hg_party_member set historic = ").append(true)
                    .append(" where id='").append(org.getId()).append("' and historic=false;\n");

        }
        jdbcTemplate.execute(sqlBuilder.toString());
    }

    @Transactional
    public void insertAll(List<Member> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        Field[] fields = Member.class.getDeclaredFields();

        List<Field> fieldList = Stream.of(fields)
                .peek(p -> p.setAccessible(true))
                .filter(p -> !(p.getName().equalsIgnoreCase("historic") || p.getName().equalsIgnoreCase("id")))
                .collect(Collectors.toList());
        List<String> names = fieldList.stream().map(Field::getName).collect(Collectors.toList());

        StringBuilder sql = new StringBuilder();

        sql.append("insert into hg_party_member (\"").append(String.join("\", \"", names)).append("\") values ");

        List<String> sqList = list.stream().map(p -> {
            StringBuilder vStringBuilder = new StringBuilder();
            vStringBuilder.append("(");
            List<String> values = new ArrayList<>();
            for (Field field : fieldList) {
                Object vObject;
                try {
                    vObject = field.get(p);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                    vObject = null;
                }
                if (vObject == null) {
                    values.add("null");
                } else {
                    values.add("'" + vObject.toString() + "'");
                }
            }
            vStringBuilder.append(String.join(",", values)).append(")");
            return vStringBuilder.toString();
        }).collect(Collectors.toList());
        sql.append(String.join(",", sqList)).append(";");
        jdbcTemplate.execute(sql.toString());
    }

    public void newUsers(List<Member> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        String[] columns = {"user_id", "user_name", "user_password", "user_department_id", "state", "userrole"};
        StringBuilder sql = new StringBuilder();
        sql.append("insert into hg_users_info (\"");
        sql.append(String.join("\", \"", columns)).append("\") values ");

        List<String> userList = list.stream().map(p -> {
            StringBuilder vStringBuilder = new StringBuilder();
            vStringBuilder.append("(");
            List<String> values = new ArrayList<>();
            values.add("'" + p.getMember_identity() + "'");
            values.add("'" + p.getMember_name() + "'");
            values.add("'" + MD5.getMD5(p.getMember_identity().substring(p.getMember_identity().length() - 6)) + "'");
            values.add("'" + p.getMember_org() + "'");
            values.add("1");
            values.add("'普通党员'");
            vStringBuilder.append(String.join(",", values)).append(")");
            return vStringBuilder.toString();
        }).collect(Collectors.toList());
        sql.append(String.join(",", userList)).append(";");
        jdbcTemplate.execute(sql.toString());
    }

    public void deleteUsers(List<Member> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        String sql = "delete from hg_users_info where user_id in ('" +
                String.join("','", list.stream().map(p -> p.getMember_identity()).collect(Collectors.toList())) + "')";
        jdbcTemplate.execute(sql);
    }

    public void deleteUser(Member member) {
        String sql = "update hg_party_member set historic = true where id = ?";
        jdbcTemplate.update(sql, member.getId());
    }

    public void updateUsers(List<Member> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        StringBuilder sql = new StringBuilder();
        for (Member member : list) {
            sql.append(String.format("update hg_users_info set user_department_id = '%s' where user_id = '%s';", member.getMember_org(), member.getMember_identity()));
        }
        jdbcTemplate.execute(sql.toString());
    }
    public int insertOrUpate(String sql) {
     
      return  jdbcTemplate.update(sql);
    }
}