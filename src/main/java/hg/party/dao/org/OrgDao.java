package hg.party.dao.org;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.TreeNode;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;
import party.constants.PartyOrgAdminTypeEnum;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.entity.PageQueryResult;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component(immediate = true, service = OrgDao.class)
public class OrgDao extends PostgresqlDaoImpl<Organization> {
    @Reference
    private RetentionDao retentionDao;

    public List<Organization> findAll() {
        String sql = "select * from hg_party_org where historic is false";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public Organization findOrgById(int id) {
        String sql = "select * from hg_party_org where historic is false and id = ?";
        List<Organization> organizationList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class), id);
        if (organizationList.size() > 0) {
            return organizationList.get(0);
        } else {
            return null;
        }
    }

    public Organization findOrgByPID(int id, String orgName) {
        String sql = "select o.* from hg_party_org o left join hg_party_org p on o.org_parent = p.org_id where o.historic is false and p.id = ? and o.org_Name = ?";
        List<Organization> organizationList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class), id, orgName);
        if (organizationList.size() > 0) {
            return organizationList.get(0);
        } else {
            return null;
        }
    }

    public Organization findOrgByOrgId(String orgId) {
        String sql = "select * from hg_party_org where historic is false and org_id = ?";
        List<Organization> organizationList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class), orgId);
        if (organizationList.size() > 0) {
            return organizationList.get(0);
        } else {
            return null;
        }
    }

    public List<Organization> findChildren(String parentId) {
        String sql = "select * from hg_party_org where historic is false and org_parent = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class), parentId);
    }

    public int deleteAdmin(String userId) {
        String sql = "DELETE from hg_party_org_admin where admin_id= ? ";
        return jdbcTemplate.update(sql, userId);
    }

    public List<Organization> findAllHistoric() {
        String sql = "select * from hg_party_org";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findHistoricSecondary() {
        String sql = "select * from hg_party_org where org_type = 'secondary' ";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findSecondary() {
        String sql = "select * from hg_party_org where org_type = 'secondary' and historic is false";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findSecondary(String orgId) {
        String sql = "select * from hg_party_org where org_type = 'secondary' and historic is false and org_id='" + orgId + "'";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findbranchSecondary(String orgId) {
        String sql = "select * from hg_party_org where org_type = 'secondary' and historic is false " +
                " and org_id=(select org_parent from hg_party_org where org_id='" + orgId + "')";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findHistoricBranch() {
        String sql = "select * from hg_party_org where org_type = 'branch' ";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findBranch() {
        String sql = "select * from hg_party_org where org_type = 'branch' and historic is false";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findBranch(String orgId) {
        String sql = "select * from hg_party_org where org_type = 'branch' and historic is false and org_id='" + orgId + "'";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findsecodBranch(String orgId) {
        String sql = "select * from hg_party_org where org_type = 'branch' and historic is false and org_parent='" + orgId + "'";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public List<Organization> findBranchBySecondary(String secondary) {
        String sql = "select * from hg_party_org where org_parent = '" + secondary + "' and historic is false";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
    }

    public Organization findByOrgId(String orgId) {
        String sql = "select * from hg_party_org where org_id = '" + orgId + "'";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Organization.class));
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> findOrgAndPathByOrgId(String orgId) {
        String sql = "select org.*,s.org_name s_org_name,o.org_name o_org_name from hg_party_org org  left join hg_party_org s on org.org_parent = s.org_id left join hg_party_org  o on s.org_parent =o.org_id where org.org_id =?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orgId);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<Organization> findByOrgId(List<String> orgId) {
        String sql = "select * from hg_party_org where org_id in ('" + String.join("','", orgId) + "')";
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
        } catch (Exception e) {
            return null;
        }
    }

    public void updateAll(List<Organization> orgs) {
        if (orgs == null || orgs.size() == 0) {
            return;
        }
        StringBuilder sqlBuilder = new StringBuilder();
        for (Organization org : orgs) {
            sqlBuilder.append("update hg_party_org set org_name = '").append(org.getOrg_name()).append("'")
                    .append(" where id=").append(org.getId()).append(";\n");
        }
        jdbcTemplate.execute(sqlBuilder.toString());
    }

    public void historicAll(List<Organization> orgs) {
        if (orgs == null || orgs.size() == 0) {
            return;
        }
        StringBuilder sqlBuilder = new StringBuilder();
        for (Organization org : orgs) {
            sqlBuilder.append("update hg_party_org set historic = ").append(true)
                    .append(" where id=").append(org.getId()).append(";\n");

        }
        jdbcTemplate.execute(sqlBuilder.toString());
    }

    public void insertAll(List<Organization> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        Field[] fields = Organization.class.getDeclaredFields();

        List<Field> fieldList = Stream.of(fields)
                .peek(p -> p.setAccessible(true))
                .filter(p -> !(p.getName().equalsIgnoreCase("historic") || p.getName().equalsIgnoreCase("id")))
                .collect(Collectors.toList());
        List<String> names = fieldList.stream().map(Field::getName).collect(Collectors.toList());

        StringBuilder sql = new StringBuilder();

        sql.append("insert into hg_party_org (\"").append(String.join("\", \"", names)).append("\") values ");

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


    /*根据org_id获取org_type
     * XZG
     */
    public Map<String, Object> findOrgType(String orgId) {
        String sql = "SELECT org_type FROM hg_party_org WHERE org_id='" + orgId + "'";
        return this.jdbcTemplate.queryForMap(sql);
    }

    public List<Organization> findTree(String pid, boolean recursion, boolean historic) {
        String sql;
        if (recursion) {
            sql = "WITH RECURSIVE T (\n" +
                    "\tID,\n" +
                    "\torg_id,\n" +
                    "\torg_type,\n" +
                    "\torg_name,\n" +
                    "\torg_secretary,\n" +
                    "\torg_contactor,\n" +
                    "\torg_phone_number,\n" +
                    "\torg_unit_situation,\n" +
                    "\torg_unit_name,\n" +
                    "\torg_unit_type,\n" +
                    "\torg_unit_party_situation,\n" +
                    "\torg_unit_code,\n" +
                    "\torg_relation,\n" +
                    "\torg_parent,\n" +
                    "\torg_code,\n" +
                    "\thistoric\n" +
                    ") AS (\n" +
                    "\tSELECT\n" +
                    "\t\t\"public\".hg_party_org.\"id\",\n" +
                    "\t\t\"public\".hg_party_org.org_id,\n" +
                    "\t\t\"public\".hg_party_org.org_type,\n" +
                    "\t\t\"public\".hg_party_org.org_name,\n" +
                    "\t\t\"public\".hg_party_org.org_secretary,\n" +
                    "\t\t\"public\".hg_party_org.org_contactor,\n" +
                    "\t\t\"public\".hg_party_org.org_phone_number,\n" +
                    "\t\t\"public\".hg_party_org.org_unit_situation,\n" +
                    "\t\t\"public\".hg_party_org.org_unit_name,\n" +
                    "\t\t\"public\".hg_party_org.org_unit_type,\n" +
                    "\t\t\"public\".hg_party_org.org_unit_party_situation,\n" +
                    "\t\t\"public\".hg_party_org.org_unit_code,\n" +
                    "\t\t\"public\".hg_party_org.org_relation,\n" +
                    "\t\t\"public\".hg_party_org.org_parent,\n" +
                    "\t\t\"public\".hg_party_org.org_code,\n" +
                    "\t\t\"public\".hg_party_org.historic\n" +
                    "\tFROM\n" +
                    "\t\thg_party_org\n" +
                    "\tWHERE\n" +
                    "\t\torg_id = '" + pid + "'\n" +
                    "\tUNION ALL\n" +
                    "\t\tSELECT\n" +
                    "\t\t\tD. ID,\n" +
                    "\t\t\tD.org_id,\n" +
                    "\t\t\tD.org_type,\n" +
                    "\t\t\tD.org_name,\n" +
                    "\t\t\tD.org_secretary,\n" +
                    "\t\t\tD.org_contactor,\n" +
                    "\t\t\tD.org_phone_number,\n" +
                    "\t\t\tD.org_unit_situation,\n" +
                    "\t\t\tD.org_unit_name,\n" +
                    "\t\t\tD.org_unit_type,\n" +
                    "\t\t\tD.org_unit_party_situation,\n" +
                    "\t\t\tD.org_unit_code,\n" +
                    "\t\t\tD.org_relation,\n" +
                    "\t\t\tD.org_parent,\n" +
                    "\t\t\tD.org_code,\n" +
                    "\t\t\tD.historic\n" +
                    "\t\tFROM\n" +
                    "\t\t\thg_party_org D\n" +
                    "\t\tJOIN T ON D.org_parent = T .org_id\n" +
                    ") SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\tT";
            if (!historic) {
                sql += " where T.historic = false order by T.org_code";
            }
        } else {
            sql = "select * from hg_party_org where (org_parent = '" + pid + "' or org_id = '" + pid + "') and historic = false order by org_code";
        }
        System.out.println(sql);
        try {
            return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class));
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    public Organization findRoot() {
        String sql = "select * from hg_party_org where org_type = 'organization'";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Organization.class));
        } catch (Exception e) {
            return null;
        }
    }

    public Organization findRoot(String orgId, String orgType) {
        String sql = "select * from hg_party_org where org_type = '" + orgType + "' and org_id ='" + orgId + "'";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Organization.class));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean changeAdmin(String org, String... admin) {
        Organization organization = findByOrgId(org);
        deleteOrgAdmin(org);
        if (admin == null || admin.length == 0) {
            return true;
        }
        return saveAdmin(organization, admin);
    }

    public void deleteOrgAdmin(String org) {
        String sql = "delete from hg_party_org_admin where org_id = ? ";
        jdbcTemplate.update(sql, org);
    }

    public List<String> findAdminPhoneNumberIn(List<String> orgs) {
        if (orgs != null && orgs.size() > 0) {
            String in = String.join("','", orgs);
            String sql = String.format("select m.member_phone_number from hg_party_org_admin ad inner join hg_party_member m on " +
                    "ad.admin_id = m.member_identity and m.historic is false " +
                    "where org_id in ('%s')", in);
            return jdbcTemplate.queryForList(sql, String.class);
        } else {
            return null;
        }

    }

    public boolean saveAdmin(String org, String... admin) {
        Organization organization = findByOrgId(org);
        if (organization == null) {
            return false;
        } else {
            return saveAdmin(organization, admin);
        }
    }

    public boolean saveAdmin(Organization org, String... admin) {
        String recordSql = Stream.of(admin).map(p -> org.getOrg_id() + "','" + p + "','" + org.getOrg_type()).collect(Collectors.joining("'),('"));
        String sql = "insert into hg_party_org_admin (\"org_id\",\"admin_id\",\"org_type\") "
                + "values ('" + recordSql + "')";
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<String> findRoleByUserId(String userId) {
        //	String sql = "select org_type from hg_party_org_admin where admin_id = ?";
        String sql = "select adm.org_type from hg_party_org_admin as adm join hg_party_org as org on adm.org_id=org.org_id " +
                "where adm.admin_id = ? AND org.historic=false";
        try {
            return jdbcTemplate.queryForList(sql, String.class, userId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<String> findAdminByOrg(String orgId) {
        String sql = "select admin_id from hg_party_org_admin where org_id = ? ";
        try {
            return jdbcTemplate.queryForList(sql, String.class, orgId);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    //导航消息提醒
    public List<Map<String, Object>> NavigationMessage(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    //删除人员
    public int deletePersonByuserId(String userId) {
        String sql = "update hg_party_member set historic = true " +
                " where member_identity = ? " +
                " and historic is false ";

        return jdbcTemplate.update(sql, userId);
    }
    //恢复人员
    public int recoveryMemberByUserId(String userId,String orgId) {
        String sql = "update hg_party_member set historic = false,member_org=?" +
                " where member_identity = ? " +
                " and historic is true ";

        return jdbcTemplate.update(sql, userId,orgId);
    }

    public int deleteUserByuserId(String userId) {
        String sql = "update hg_users_info set state = '0' where user_id = ? ";
        return jdbcTemplate.update(sql, userId);
    }
    public int recoveryUserByUserId(String userId,String orgId) {
        String sql = "update hg_users_info set state = '1',user_department_id=? where user_id = ? ";
        return jdbcTemplate.update(sql, userId,orgId);
    }

    public List<Map<String, Object>> findPersonByUserId(String userId) {
        String sql = "select m.*,org.org_name,s.org_name s_org_name,o.org_name o_org_name from hg_party_member m " +
                "left join hg_party_org org on org.org_id = m.member_org left join hg_party_org s on org.org_parent = s.org_id left join hg_party_org  o on s.org_parent =o.org_id " +
                " where m.member_identity = ? " +
                " and m.historic is false ";
        return jdbcTemplate.queryForList(sql, userId);
    }

    public List<Map<String, Object>> findOrgNameByName(String parentId, String orgName) {
        String sql = "select * from hg_party_org where org_name  =? and org_parent = ? and historic is false  ";
        return jdbcTemplate.queryForList(sql, orgName, parentId);
    }

    public List<Map<String, Object>> findOrgName(String orgType, String orgName) {
        String sql = "select count(*) as count  from hg_party_org where org_type= ? and org_name= ? and historic is false ";
        return jdbcTemplate.queryForList(sql, orgType, orgName);
    }

    public List<Map<String, Object>> findOrgName(String orgType, String orgName, String orgId) {
        String sql = "select count(*) as count  from hg_party_org " +
                " where org_type= ? and historic is false " +
                " and org_name= ? " +
                " and org_parent =(select org_parent  from hg_party_org where org_id=?) ";
        return jdbcTemplate.queryForList(sql, orgType, orgName, orgId);
    }

    public List<Map<String, Object>> findCode(String parentId) {
        String sql = "select max(org_code) as code from hg_party_org where org_parent = ? ";
        return jdbcTemplate.queryForList(sql, parentId);
    }

    public List<Map<String, Object>> findScondeCode(String orgId) {
        String sql = "select org_code as code from hg_party_org where org_id=? ";
        return jdbcTemplate.queryForList(sql, orgId);
    }

    public List<Map<String, Object>> findOrgName(String orgId) {
        String sql = "select * from hg_party_org where org_id= ? ";
        return jdbcTemplate.queryForList(sql, orgId);
    }

    public List<Map<String, Object>> findSecondOrgName(String orgId) {
        String sql = "select * from hg_party_org where org_id= (select org_parent from hg_party_org where org_id = ? )";
        return jdbcTemplate.queryForList(sql, orgId);
    }

    public int insert(String sql) {

        return jdbcTemplate.update(sql);
    }

    /**
     * 查询支部下面是否还有人员
     */

    public int findBranchPersonCount(String orgId) {
        String sql = "select count(*) as count from hg_party_member where historic is false and member_org = ? ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orgId);
        return Integer.parseInt(list.get(0).get("count").toString());
    }

    /**
     * 查询二级组织下面是否还有人员
     */
    public int findCount(String orgId) {
        String sql = "select count(*) as count from hg_party_org where historic is false and org_parent = ? ";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, orgId);
        return Integer.parseInt(list.get(0).get("count").toString());
    }

    public List<Map<String, Object>> findUserExist(String userId) {
        String sql = "select * from hg_party_member where member_identity = ? and historic is FALSE ";

        return jdbcTemplate.queryForList(sql, userId);
    }

    public List<Map<String, Object>> findUserExist(String userId, String orgId) {
        String sql = "select * from hg_party_member where member_identity = ? and historic is FALSE and member_org = ? ";

        return jdbcTemplate.queryForList(sql, userId, orgId);
    }

    public List<Map<String, Object>> findMoveObject(String orgId) {
        String sql = "select org_id,org_name from hg_party_org where historic is false and org_parent=(select org_parent from hg_party_org where org_id= ? );";

        return jdbcTemplate.queryForList(sql, orgId);
    }

    public Map<String, Object> pagenation(int pageNo, int pageSize, String sql) {
        String sql1 = sql + " limit " + pageSize + " offset " + (pageNo - 1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1);
        List<Map<String, Object>> count = this.jdbcTemplate.queryForList(sql);
        int total = count.size();
        if (total % pageSize == 0) {
            map.put("totalPage", total / pageSize);
        } else {
            map.put("totalPage", total / pageSize + 1);
        }
        map.put("pageNow", pageNo);
        map.put("list", list);
        return map;
    }

    public Map<String, Object> pagenation(int pageNo, int pageSize, String sql, String ss) {
        String sql1 = sql + " limit " + pageSize + " offset " + (pageNo - 1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1, ss);
        List<Map<String, Object>> count = this.jdbcTemplate.queryForList(sql, ss);
        int total = count.size();
        if (total % pageSize == 0) {
            map.put("totalPage", total / pageSize);
        } else {
            map.put("totalPage", total / pageSize + 1);
        }
        map.put("pageNow", pageNo);
        map.put("list", list);
        return map;
    }

    public int createOrg(final Organization org) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into hg_party_org (org_id,org_name,org_type,org_parent,desc_type,org_address,org_contactor,org_contactor_phone,org_email,org_phone_number,org_fax,org_secretary) values (?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                                    PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"});
                                    ps.setString(1, org.getOrg_id());
                                    ps.setString(2, org.getOrg_name());
                                    ps.setString(3, org.getOrg_type());
                                    ps.setString(4, org.getOrg_parent());
                                    ps.setInt(5, org.getDesc_type());
                                    ps.setString(6, org.getOrg_address());
                                    ps.setString(7, org.getOrg_contactor());
                                    ps.setString(8, org.getOrg_contactor_phone());
                                    ps.setString(9, org.getOrg_email());
                                    ps.setString(10, org.getOrg_phone_number());
                                    ps.setString(11, org.getOrg_fax());
                                    ps.setString(12, org.getOrg_secretary());
                                    return ps;
                                }
                            },
                keyHolder);
        return keyHolder.getKey().intValue();
    }

    public int updateDetail(Organization organization) {
        String sql = "update hg_party_org set org_name = ?," +
                "org_address = ?, org_phone_number = ?, org_fax = ?," +
                "org_secretary = ?, org_email = ?, org_contactor = ?," +
                "desc_type = ?,org_contactor_phone = ? where id = ?";

        return jdbcTemplate.update(sql, organization.getOrg_name(),
                organization.getOrg_address(), organization.getOrg_phone_number(), organization.getOrg_fax(),
                organization.getOrg_secretary(), organization.getOrg_email(), organization.getOrg_contactor(),
                organization.getDesc_type(), organization.getOrg_contactor_phone(),
                organization.getId());
    }

    public List<Organization> findOrgByOrgType(PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
        String sql = "select * from hg_party_org where historic is false and org_type = ?";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Organization.class), partyOrgAdminTypeEnum.getType());
    }

    public int deleteOrg(int id) {
        String sql = "update hg_party_org set historic = true where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<TreeNode> getTreeData(Organization organization) {
        List<Organization> organizationList = findAll();
        if (organization == null) {
            return initOrgTreeData(organizationList, "-");
        } else {
            List<TreeNode> treeNodeList = new ArrayList<>();
            TreeNode parentNode = new TreeNode();
            parentNode.setChecked(false);
            parentNode.setId(organization.getId());
            parentNode.setName(organization.getOrg_name());
            parentNode.setOpen(false);
            parentNode.setData(organization);
            List<TreeNode> children = initOrgTreeData(organizationList, organization.getOrg_id());
            parentNode.setChildren(children);
            treeNodeList.add(parentNode);
            return treeNodeList;
        }

    }

    private List<TreeNode> initOrgTreeData(List<Organization> organizationList, String orgParent) {
        List<TreeNode> treeNodeList = new ArrayList<>();
        if (organizationList.size() > 0) {
            for (Organization organization : organizationList) {
                String pId = organization.getOrg_parent();
                if ((orgParent == null && pId == null) || (orgParent != null && orgParent.equals(pId))) {
                    TreeNode parentNode = new TreeNode();
                    parentNode.setChecked(false);
                    parentNode.setId(organization.getId());
                    parentNode.setName(organization.getOrg_name());
                    parentNode.setOpen(false);
                    parentNode.setData(organization);
                    List<TreeNode> children = initOrgTreeData(organizationList, organization.getOrg_id());
                    parentNode.setChildren(children);
                    treeNodeList.add(parentNode);
                }

            }
        }
        return treeNodeList;
    }

    public List<Map<String, Object>> findAllUsers(Organization organization) {
        PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(organization.getOrg_type());
        StringBuffer sb = new StringBuffer("select u.user_id,u.user_name from hg_users_info u");
        List<Map<String, Object>> ret = new ArrayList<>();
        switch (partyOrgAdminTypeEnum) {
            case ORGANIZATION:
                sb.append(" where u.state = '1' order by u.user_name desc");
                ret = jdbcTemplate.queryForList(sb.toString());
                break;
            case SECONDARY:
                sb.append(" left join hg_party_member m on u.user_id=m.member_identity left join hg_party_org o on m.member_org =o.org_id left join hg_party_org p on o.org_parent =p.org_id");
                sb.append(" where u.state = '1' and p.org_id=? order by u.user_name desc");
                ret = jdbcTemplate.queryForList(sb.toString(), organization.getOrg_id());
                break;
            case BRANCH:
                sb.append(" left join hg_party_member m on u.user_id=m.member_identity left join hg_party_org o on m.member_org =o.org_id");
                sb.append(" where u.state = '1' and o.org_id=? order by u.user_name desc");
                ret = jdbcTemplate.queryForList(sb.toString(), organization.getOrg_id());
                break;
        }
        return ret;
    }

    public List<Map<String, Object>> findOrgAdminUser(int id) {
        String sql = "select i.user_id,i.user_name from hg_party_org_admin a left join hg_users_info i on a.admin_id = i.user_id left join hg_party_org o on a.org_id = o.org_id where o.id=?";
        return jdbcTemplate.queryForList(sql, id);
    }

    public Organization findAdminOrg(String userId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
        String sql = "select o.* from hg_party_org_admin a left join hg_party_org o on a.org_id= o.org_id where admin_id = ? and o.org_type = ? ";
        RowMapper<Organization> rowMapper = BeanPropertyRowMapper.newInstance(Organization.class);
        List<Organization> organizationList = this.jdbcTemplate.query(sql, rowMapper, userId, partyOrgAdminTypeEnum.getType());
        if (organizationList.size() > 0) {
            return organizationList.get(0);
        } else {
            return null;
        }
    }

    public List<Map<String, Object>> findMembersByOrg(String orgId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
        StringBuffer sb = new StringBuffer("select i.member_identity,i.member_name,i.member_phone_number,b.org_id branch_id, s.org_id secondary_id," +
                "o.org_id org_id from hg_party_member i left join hg_party_org b on i.member_org = b.org_id left join hg_party_org s on s.org_id = b.org_parent" +
                " left join hg_party_org o on o.org_id = s.org_parent where 1=1 and i.historic is false");
        List<Map<String, Object>> list = null;
        if (partyOrgAdminTypeEnum != null && !StringUtils.isEmpty(orgId)) {
            switch (partyOrgAdminTypeEnum) {
                case BRANCH:
                    sb.append(" and b.org_id = ?");
                    list = jdbcTemplate.queryForList(sb.toString(), orgId);
                    break;
                case SECONDARY:
                    sb.append(" and s.org_id=? or b.org_id = ?");
                    list = jdbcTemplate.queryForList(sb.toString(), orgId, orgId);
                    break;
                case ORGANIZATION:
                    ;
                    list = jdbcTemplate.queryForList(sb.toString());
                    break;
            }
            return list;
        } else {
            return null;
        }

    }

    public PageQueryResult<Map<String, Object>> orgChildrenStatistics(int pageNow, int pageSize, int id, String search) {
        List<Object> params = new ArrayList<>();
        String sql = "SELECT T\n" +
                "\t.*,\n" +
                "\tl.org_name, l.org_type, l.desc_type, l.historic,l.org_secretary \n" +
                "FROM\n" +
                "\t(SELECT COUNT\n" +
                "\t\t( c.org_id ) AS C,\n" +
                "\t\to.org_id \n" +
                "\tFROM\n" +
                "\t\t\"hg_party_org\" o\n" +
                "\t\tleft JOIN hg_party_org c ON o.org_id = c.org_parent and c.historic = FALSE \n" +
                "\t\tleft JOIN hg_party_org p ON o.org_parent = p.org_id\n" +
                "\tWHERE \n" +
                "\t\to.historic = false and (o.id = ? or p.id = ?) \n" +
                "\tGROUP BY\n" +
                "\t\to.org_id\n" +
                "\t) T LEFT JOIN hg_party_org l ON T.org_id = l.org_id";
        params.add(id);
        params.add(id);
        if (!StringUtils.isEmpty(search)) {
            sql += " where l.org_name like ?\n";
            params.add("%" + search + "%");
        }
        sql += "\tORDER BY org_type = 'organization' desc, org_type = 'secondary' desc";
        return pageBySql(pageNow, pageSize, sql, params);
    }

    public List<Map<String, Object>> orgMemberStatistics(String search, List<String> orgIds) {
        List<Object> params = new ArrayList<>();
        params.addAll(orgIds);
        String suffix = orgIds.stream().map(p -> "?").collect(Collectors.joining(","));
        String sql = "SELECT T\n" +
                "\t.*,\n" +
                "\tl.org_name \n" +
                "FROM\n" +
                "\t(\n" +
                "\tSELECT COUNT\n" +
                "\t\t( M ) AS C,\n" +
                "\t\tP.org_id,m.member_type \n" +
                "\tFROM\n" +
                "\t\thg_party_member\n" +
                "\t\tM INNER JOIN hg_party_org o ON M.member_org = o.org_id\n" +
                "\t\tINNER JOIN hg_party_org P ON o.org_parent = P.org_id \n" +
                "\tGROUP BY\n" +
                "\t\tP.org_id, m.member_type \n" +
                "\t)\n" +
                "\tT LEFT JOIN hg_party_org l ON T.org_id = l.org_id" +
                " where l.org_id in (" + suffix + ")";
        if (!StringUtils.isEmpty(search)) {
            sql += " and l.org_name like ?\n";
            params.add("%" + search + "%");
        }
        return jdbcTemplate.queryForList(sql, params.toArray());
    }

    public PageQueryResult<Map<String, Object>> pageBySql(int pageNow, int pageSize, String sql, List<Object> objects) {
        if (pageNow <= 0) {
            pageNow = 0;
        } else {
            --pageNow;
        }

        List<Map<String, Object>> list = this.listBySql(pageNow, pageSize, sql, objects);
        int count = this.countBySql(sql, objects);
        return new PageQueryResult(list, count, pageNow, pageSize);
    }

    public int countBySql(String sql, List<Object> objects) {
        int start = sql.toLowerCase().indexOf("from");
        int end = sql.toLowerCase().indexOf("order");
        if (start == -1) {
			return 0;
        }
        if (end == -1){
            end = sql.length();
        }
        sql = "select count(1) " + sql.substring(start, end);
        Integer c = this.jdbcTemplate.queryForObject(sql, Integer.class, objects.toArray(new Object[0]));
        return null == c ? 0 : c;
    }

    private List<Map<String, Object>> listBySql(int pageNo, int pageSize, String sql, List<Object> objects) {
        StringBuffer exeSql = new StringBuffer();
        exeSql.append(sql);
        exeSql.append(" LIMIT ? OFFSET ? ");
        if (objects != null && objects.size() != 0) {
            List list = new ArrayList();
            list.addAll(objects);
            list.add(pageSize);
            list.add(pageNo * pageSize);
            return this.jdbcTemplate.queryForList(exeSql.toString(), list.toArray(new Object[0]));
        } else {
            return this.jdbcTemplate.queryForList(exeSql.toString(), new Object[]{pageSize, pageNo * pageSize});
        }
    }
}