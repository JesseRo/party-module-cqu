package hg.party.dao.party;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import hg.party.entity.party.BaseStatistics;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.Organization;
import hg.party.entity.party.UserStatistics;

/**
 * 文件名称： party<br>
 * 创建人 　： Yu Jiang Xia<br>
 * 创建日期： 2018年1月3日下午3:00:35<br>
 */
@Component(immediate = true, service = PartyOrgDao.class)
public class PartyOrgDao extends PostgresqlDaoImpl<Organization> {
    //通过二级党委查询书记
    public List<Organization> findByOrgId(String org_id) {
        String sql = "SELECT * FROM hg_party_org " +
                "WHERE org_id= ? and historic is false";
        RowMapper<Organization> rowMapper = BeanPropertyRowMapper.newInstance(Organization.class);
        return this.jdbcTemplate.query(sql, rowMapper, org_id);
    }

    //报表学院参会情况
    public List<Map<String, Object>> partyEcharts() {
//		String sql = "SELECT org_name as keyname,COUNT(*) as valname FROM hg_party_org_inform_info as info LEFT OUTER JOIN hg_party_org as org "+
//					"ON info.org_type = org.org_id "+
//					"GROUP BY org_name ";

        String sql = "select f.p, org_p.org_name as keyname, f.num as valname from (SELECT org.org_parent as p, count(plan.id) as num from  hg_party_meeting_plan_info as plan " +
                "Left JOIN hg_party_org as org  ON org.org_id=plan.organization_id " +
                "where org.org_type='branch' and org.historic is false  GROUP BY org.org_parent) as f LEFT JOIN hg_party_org as org_p on org_p.org_id = f.p WHERE org_p.historic is false ";
        return jdbcTemplate.queryForList(sql);
    }

    //学院出勤率报表
    public List<Map<String, Object>> attenEcharts() {
//		String sql = "SELECT member_party_committee as keyname,avg(to_number(trim(both '%' from attendance), '999'))valname "+
//				    "from hg_party_meeting_plan_info AS plan "+
//					"LEFT JOIN (SELECT member_party_committee,member_org,historic,count(*) from hg_party_member AS mem "+
//					"GROUP BY member_party_committee,member_org,historic) AS mber "+
//					"ON plan.organization_id=mber.member_org "+
//					"LEFT JOIN hg_party_meeting_notes_info as note "+
//					"ON plan.meeting_id=note.meeting_id "+
//					"WHERE meeting_type='主题党日' "+
//					"AND mber.member_party_committee is not null "+
//					"AND mber.member_party_committee <>'' "+
//					"and mber.historic is false "+
//					"AND note.attendance <>'' "+
//					"AND note.attendance is not null  "+
//					"AND plan.start_time "+
//					"between "+
//					"(CURRENT_DATE-31) and CURRENT_DATE GROUP BY member_party_committee "+
//					"ORDER BY valname desc "+
//					"LIMIT 5 ";
        String sql = "SELECT member_party_committee as keyname,avg(to_number(trim(both '%' from attendance), '999'))valname " +
                "from hg_party_meeting_plan_info AS plan  " +
                "LEFT JOIN (SELECT member_party_committee,member_org,historic,count(*) from hg_party_member AS mem  " +
                "GROUP BY member_party_committee,member_org,historic) AS mber  " +
                "ON plan.organization_id=mber.member_org  " +
                "LEFT JOIN hg_party_meeting_notes_info as note  " +
                "ON plan.meeting_id=note.meeting_id  " +
                "WHERE meeting_type='主题党日'  " +
                "AND mber.member_party_committee is not null  " +
                "AND mber.member_party_committee <>''  " +
                "and mber.historic is false  " +
                "AND note.attendance <>''  " +
                "AND note.attendance is not null   " +
                "GROUP BY member_party_committee  " +
                "ORDER BY valname desc  " +
                "LIMIT 5 ";
        return jdbcTemplate.queryForList(sql);
    }

    //报表会议类型分布
    public List<Map<String, Object>> partyEchartsType() {
        String sql = "SELECT meeting_type as keyname,COUNT(*) as valname FROM hg_party_meeting_plan_info WHERE meeting_type is not null and meeting_type != '' GROUP BY meeting_type ";
        return jdbcTemplate.queryForList(sql);
    }

    //查询二级党组织个数
    public List<Map<String, Object>> orgNumber() {
        String sql = "SELECT count(*) from hg_party_org WHERE org_type='secondary' and historic is false ";
        return jdbcTemplate.queryForList(sql);
    }

    //查询党支部个数
    public List<Map<String, Object>> branchNumber() {
        String sql = "SELECT count(*) from hg_party_org WHERE org_type='branch' and historic is false ";
        return jdbcTemplate.queryForList(sql);
    }

    //查询二级党组织下党支部个数
    public int branchNumber(String orgId) {
        String sql = "SELECT count(*) num from hg_party_org WHERE org_type='branch' and historic is false and org_parent = ?";
        RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, orgId).getNum();
    }

    //查询党员人数
    public List<Map<String, Object>> userNumber() {
        String sql = "select count(*) from hg_party_member where historic = false ";
        return jdbcTemplate.queryForList(sql);
    }

    //查询
    //党员统计
    public UserStatistics userStatistics() {
        String sql = "select count(*) count ,COALESCE(sum(case when member_sex='男' then 1 else 0 end ),0) maleCount,COALESCE(sum(case when member_sex='女' then 1 else 0 end ),0) femaleCount from hg_party_member where historic = false ";
        RowMapper<UserStatistics> rowMapper = BeanPropertyRowMapper.newInstance(UserStatistics.class);
        return jdbcTemplate.queryForObject(sql, rowMapper);
    }

    //根据组织党员统计
    public UserStatistics userSecondaryStatistics(String orgId) {
        String sql = "select count(*) count ,COALESCE(sum(case when member_sex='男' then 1 else 0 end ),0) maleCount,COALESCE(sum(case when member_sex='女' then 1 else 0 end ),0) femaleCount from hg_party_member m left join hg_party_org  o on m.member_org = o.org_id where o.historic = false and org_parent = ? ";
        RowMapper<UserStatistics> rowMapper = BeanPropertyRowMapper.newInstance(UserStatistics.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, orgId);
    }

    //根据支部党员统计
    public UserStatistics userBranchStatistics(String orgId) {
        String sql = "select count(*) count ,COALESCE(sum(case when member_sex='男' then 1 else 0 end ),0) maleCount,COALESCE(sum(case when member_sex='女' then 1 else 0 end ),0) femaleCount from hg_party_member where historic = false and member_org = ? ";
        RowMapper<UserStatistics> rowMapper = BeanPropertyRowMapper.newInstance(UserStatistics.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, orgId);
    }

    //二级党组织开展活动统计
    public List<BaseStatistics> activitiesStatistics(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
        String secondaryListSql = "select * from hg_party_org where org_type = 'secondary' and historic = false";
        List<Organization> secondaryList = jdbcTemplate.query(secondaryListSql, BeanPropertyRowMapper.newInstance(Organization.class));
        List<BaseStatistics> result = new ArrayList<>();

        List<BaseStatistics> secondaryBranchCounts;
        List<BaseStatistics> secondaryCounts;
        if (year == 0 && month == 0) {//统计所有
            String secondaryBranchSql = "SELECT p.org_id as property, count(pl.id) as num FROM \"hg_party_meeting_plan_info\" pl\n" +
                    "LEFT JOIN hg_party_org o on pl.organization_id = o.org_id\n" +
                    "LEFT JOIN hg_party_org p on o.org_parent = p.org_id\n" +
                    "where p.org_type = 'secondary' and o.historic = false\n" +
                    "GROUP BY p.org_id";
            String secondarySql = "SELECT o.org_id as property, count(pl.id) as num FROM \"hg_party_meeting_plan_info\" pl\n" +
                    "LEFT JOIN hg_party_org o on pl.organization_id = o.org_id\n" +
                    "where o.org_type = 'secondary' and o.historic = false\n" +
                    "GROUP BY o.org_id";
            secondaryBranchCounts = jdbcTemplate.query(secondaryBranchSql, rowMapper);
            secondaryCounts = jdbcTemplate.query(secondarySql, rowMapper);
        } else {
            String secondaryBranchSql = "SELECT p.org_id as property, count(pl.id) as num FROM \"hg_party_meeting_plan_info\" pl\n" +
                    "LEFT JOIN hg_party_org o on pl.organization_id = o.org_id\n" +
                    "LEFT JOIN hg_party_org p on o.org_parent = p.org_id\n" +
                    "where p.org_type = 'secondary' and o.historic = false and pl.start_time >= ? and pl.start_time < ?\n" +
                    "GROUP BY p.org_id";
            String secondarySql = "SELECT o.org_id as property, count(pl.id) as num FROM \"hg_party_meeting_plan_info\" pl\n" +
                    "LEFT JOIN hg_party_org o on pl.organization_id = o.org_id\n" +
                    "where o.org_type = 'secondary' and o.historic = false and pl.start_time >= ? and pl.start_time < ?\n" +
                    "GROUP BY o.org_id";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            Timestamp startTime;
            Timestamp endTime;
            if (year > 0 && month == 0) {//全年统计
                calendar.set(year, Calendar.JANUARY, 1);
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                calendar.set(year, Calendar.DECEMBER, 1);
            } else {//按月统计
                calendar.set(year, month - 1, 1);
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                calendar.set(year, month, 1);
            }
            endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
            secondaryBranchCounts = jdbcTemplate.query(secondaryBranchSql, rowMapper, startTime, endTime);
            secondaryCounts = jdbcTemplate.query(secondarySql, rowMapper, startTime, endTime);
        }
        Map<String, BaseStatistics> secondaryBranchMap = secondaryBranchCounts.stream().collect(Collectors.toMap(BaseStatistics::getProperty, p->p));
        Map<String, BaseStatistics> secondaryMap = secondaryCounts.stream().collect(Collectors.toMap(BaseStatistics::getProperty, p->p));
        for (Organization organization : secondaryList) {
            BaseStatistics sb = secondaryBranchMap.get(organization.getOrg_id());
            BaseStatistics s = secondaryMap.get(organization.getOrg_id());
            BaseStatistics baseStatistics = new BaseStatistics();
            baseStatistics.setProperty(organization.getOrg_name());
            if (s != null){
                baseStatistics.setNum(baseStatistics.getNum() + s.getNum());
            }
            if (sb != null){
                baseStatistics.setNum(baseStatistics.getNum() + sb.getNum());
            }
            result.add(baseStatistics);
        }
        return result;
    }

    //党组织下支部开展活动统计
    public List<BaseStatistics> activitiesStatistics(int year, int month, String orgId) {
        Calendar calendar = Calendar.getInstance();
        RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
        if (year == 0 && month == 0) {//统计所有
            String countSql = "SELECT\n" +
                    "\tbr.org_name as property,\n" +
                    "\tCOUNT ( i.ID ) as num \n" +
                    "FROM\n" +
                    "\thg_party_org br\n" +
                    "\tLEFT JOIN hg_party_meeting_plan_info i ON br.org_id = i.organization_id \n" +
                    "WHERE\n" +
                    "\tbr.historic = FALSE \n" +
                    "\tand br.org_parent = ?\n" +
                    "and br.org_type = 'branch'" +
                    " GROUP BY\n" +
                    "\tbr.org_name";
            return jdbcTemplate.query(countSql, rowMapper, orgId);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            String countSql = "SELECT\n" +
                    "\tbr.org_name as property,\n" +
                    "\tCOUNT ( i.ID ) as num\n" +
                    "FROM\n" +
                    "\thg_party_org br\n" +
                    "\tLEFT JOIN hg_party_meeting_plan_info i ON br.org_id = i.organization_id \n" +
                    "WHERE\n" +
                    "\tbr.historic = FALSE \n" +
                    "\tand br.org_parent = ?\n" +
                    "\tand start_time>=? and start_time < ? \n" +
                    "and br.org_type = 'branch'" +
                    "GROUP BY\n" +
                    "\tbr.org_name";
            Timestamp startTime;
            Timestamp endTime;
            if (year > 0 && month == 0) {//全年统计
                calendar.set(year, 0, 1);
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                calendar.set(year, 11, 1);
            } else {//按月统计
                calendar.set(year, month - 1, 1);
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                calendar.set(year, month, 1);
            }
            endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
            return jdbcTemplate.query(countSql, rowMapper, orgId, startTime, endTime);
        }

    }

    //学院开展活动统计
    public List<BaseStatistics> searchActivitiesStatistics(Timestamp startTime, Timestamp endTime) {
        String secondaryBranchSql = "SELECT p.org_id as property, count(pl.id) as num FROM \"hg_party_meeting_plan_info\" pl\n" +
                "LEFT JOIN hg_party_org o on pl.organization_id = o.org_id\n" +
                "LEFT JOIN hg_party_org p on o.org_parent = p.org_id\n" +
                "where p.org_type = 'secondary' and o.historic = false and pl.start_time >= ? and pl.start_time < ?\n" +
                "GROUP BY p.org_id";
        String secondarySql = "SELECT o.org_id as property, count(pl.id) as num FROM \"hg_party_meeting_plan_info\" pl\n" +
                "LEFT JOIN hg_party_org o on pl.organization_id = o.org_id\n" +
                "where o.org_type = 'secondary' and o.historic = false and pl.start_time >= ? and pl.start_time < ?\n" +
                "GROUP BY o.org_id";
        String secondaryListSql = "select * from hg_party_org where org_type = 'secondary' and historic = false";
        List<Organization> secondaryList = jdbcTemplate.query(secondaryListSql, BeanPropertyRowMapper.newInstance(Organization.class));

        List<BaseStatistics> secondaryBranchCounts = jdbcTemplate.query(secondaryBranchSql, BeanPropertyRowMapper.newInstance(BaseStatistics.class), startTime, endTime);
        List<BaseStatistics> secondaryCounts = jdbcTemplate.query(secondarySql, BeanPropertyRowMapper.newInstance(BaseStatistics.class), startTime, endTime);
        Map<String, BaseStatistics> secondaryBranchMap = secondaryBranchCounts.stream().collect(Collectors.toMap(BaseStatistics::getProperty, p->p));
        Map<String, BaseStatistics> secondaryMap = secondaryCounts.stream().collect(Collectors.toMap(BaseStatistics::getProperty, p->p));
        List<BaseStatistics> result = new ArrayList<>();
        for (Organization organization : secondaryList) {
            BaseStatistics sb = secondaryBranchMap.get(organization.getOrg_id());
            BaseStatistics s = secondaryMap.get(organization.getOrg_id());
            BaseStatistics baseStatistics = new BaseStatistics();
            baseStatistics.setProperty(organization.getOrg_name());
            if (s != null){
                baseStatistics.setNum(baseStatistics.getNum() + s.getNum());
            }
            if (sb != null){
                baseStatistics.setNum(baseStatistics.getNum() + sb.getNum());
            }
            result.add(baseStatistics);
        }
        return result;
    }
    //学院开展活动统计
    public List<BaseStatistics> searchActivitiesStatistics(Timestamp startTime, Timestamp endTime, String orgId) {
        String secondarySql = "SELECT o.org_id as property, count(pl.id) as num FROM \"hg_party_meeting_plan_info\" pl\n" +
                "LEFT JOIN hg_party_org o on pl.organization_id = o.org_id\n" +
                "where o.org_parent = ? and o.historic = false and pl.start_time >= ? and pl.start_time < ?\n" +
                "GROUP BY o.org_id";
        String secondaryListSql = "select * from hg_party_org where org_parent = ? and historic = false";

        List<Organization> secondaryList = jdbcTemplate.query(secondaryListSql, BeanPropertyRowMapper.newInstance(Organization.class), orgId);

        List<BaseStatistics> secondaryCounts = jdbcTemplate.query(secondarySql, BeanPropertyRowMapper.newInstance(BaseStatistics.class), startTime, endTime);
        Map<String, BaseStatistics> secondaryMap = secondaryCounts.stream().collect(Collectors.toMap(BaseStatistics::getProperty, p->p));
        List<BaseStatistics> result = new ArrayList<>();
        for (Organization organization : secondaryList) {
            BaseStatistics s = secondaryMap.get(organization.getOrg_id());
            BaseStatistics baseStatistics = new BaseStatistics();
            baseStatistics.setProperty(organization.getOrg_name());
            if (s != null){
                baseStatistics.setNum(s.getNum());
            }
            result.add(baseStatistics);
        }
        return result;
    }


    //党活动分类年月统计
    public List<BaseStatistics> activitiesTypeStatistic(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
        if (year == 0 && month == 0) {//统计所有
            String sql = "SELECT i.meeting_type property,count(i.meeting_type) num  from hg_party_meeting_plan_info i group by i.meeting_type";
            return jdbcTemplate.query(sql, rowMapper);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String sql = "SELECT i.meeting_type property,count(i.meeting_type) num  from hg_party_meeting_plan_info i  group by i.meeting_type where start_time>=? and start_time < ?";
        if (year > 0 && month == 0) {//全年统计
            calendar.set(year, 0, 1);
            Timestamp startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
            calendar.set(year, 11, 1);
            Timestamp endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
            return jdbcTemplate.query(sql, rowMapper, startTime, endTime);
        } else {//按月统计
            calendar.set(year, month - 1, 1);
            Timestamp startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
            calendar.set(year, month, 1);
            Timestamp endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
            return jdbcTemplate.query(sql, rowMapper, startTime, endTime);
        }
    }

    public List<BaseStatistics> activitiesTypeStatistic(int year, int month, String orgId) {
        Calendar calendar = Calendar.getInstance();
        RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
        if (year == 0 && month == 0) {//统计所有
            String sql = "SELECT i.meeting_type property,count(i.meeting_type) num  from hg_party_meeting_plan_info i left join hg_party_org o on i.organization_id=o.org_id  where o.org_parent = ? group by i.meeting_type";
            return jdbcTemplate.query(sql, rowMapper, orgId);
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            String sql = "SELECT i.meeting_type property,count(i.meeting_type) num  from hg_party_meeting_plan_info i left join hg_party_org o on i.organization_id=o.org_id  where o.org_parent = ? and start_time>=? and start_time < ? group by i.meeting_type";
            Timestamp startTime;
            Timestamp endTime;
            if (year > 0 && month == 0) {//全年统计
                calendar.set(year, 0, 1);
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                calendar.set(year, 11, 1);
                endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
            } else {//按月统计
                calendar.set(year, month - 1, 1);
                startTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
                calendar.set(year, month, 1);
                endTime = Timestamp.valueOf(formatter.format(calendar.getTime()));
            }
            return jdbcTemplate.query(sql, rowMapper, orgId, startTime, endTime);
        }

    }

    //党活动分类起止日期统计
    public List<BaseStatistics> searchActivitiesTypeStatistics(Timestamp startTime, Timestamp endTime) {
        RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
        String sql = "SELECT i.meeting_type property,count(i.meeting_type) num  from hg_party_org_inform_info i  group by i.meeting_type where start_time>=? and start_time < ?";
        return jdbcTemplate.query(sql, rowMapper, startTime, endTime);
    }

    //支部组织生活次数
    public int activitiesStatisticsCount(String orgId) {
        RowMapper<BaseStatistics> rowMapper = BeanPropertyRowMapper.newInstance(BaseStatistics.class);
        String countSql = "SELECT count(id) num  from hg_party_meeting_plan_info " +
                "where organization_id = ?";
        BaseStatistics count = jdbcTemplate.queryForObject(countSql, rowMapper, orgId);
        return count.getNum();
    }

    //查询组织活动个数
    public List<Map<String, Object>> mettingNumber() {
        String sql = "SELECT count(*) from hg_party_org_inform_info ";
        return jdbcTemplate.queryForList(sql);
    }

    //日访问量查询
    public Map<String, Object> dateVisit() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sql = "SELECT count(*) from hg_party_visit_count where visit_time >= '" + sdf.format(date) + "'";
        return this.jdbcTemplate.queryForMap(sql);
    }

    //访问量报表
    public List<Map<String, Object>> echartsVisit() {
//		String sql = "SELECT department_name as keyname,count(*) as valname FROM hg_party_visit_count AS vs LEFT JOIN hg_party_org AS org "+
//					"ON vs.department_id=org.org_id "+
//					"GROUP BY department_name ";
        String sql = "SELECT member_party_committee AS keyname,count(*) as valname FROM " +
                "(SELECT * FROM hg_party_visit_count as vis " +
                "LEFT OUTER JOIN hg_users_info as us " +
                "ON vis.user_id=us.user_id " +
                "LEFT OUTER JOIN hg_party_member AS mem " +
                "ON us.user_id=mem.member_identity WHERE mem.member_identity IS NOT NULL and mem.historic is false) as a " +
                "GROUP BY member_party_committee ";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql, String keyName) {
        String sql1 = sql + " limit " + pageSize + " offset " + (pageNo - 1) * pageSize;
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1, keyName);
        List<Map<String, Object>> count = this.jdbcTemplate.queryForList(sql, keyName);
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

    public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql) {
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

    public static void main(String[] args) {
        System.out.println("aa");
    }

}
