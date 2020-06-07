package hg.party.dao.secondCommittee;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.party.OrgInform;

@Component(immediate = true, service = OtherDao.class)
public class OtherDao extends PostgresqlDaoImpl<Object> {

    Logger logger = Logger.getLogger(OtherDao.class);


    //查询下拉属性值
    public List<Map<String, Object>> queryAttributesByType(String resources_type) {
        String sql = "SELECT * FROM hg_value_attribute_info WHERE resources_type = '" + resources_type + "' order by resources_key asc";
//		   logger.info("sql :" + sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> queryTaskStatus(int maxCode) {
        String sql = "SELECT * FROM hg_value_attribute_info WHERE resources_key <= '" + maxCode + "' and resources_type = 'taskStatus' order by resources_key asc";
//		   logger.info("sql :" + sql);
        return this.jdbcTemplate.queryForList(sql);
    }

    //查询会议类型
    public List<OrgInform> queryAllMeetingTheme() {
        List<OrgInform> taskList = new ArrayList<OrgInform>();
        String sql = "SELECT * FROM hg_party_org_inform_info WHERE public_status = '1'  ORDER BY release_time DESC";
//		   logger.info("sql :" + sql);
        RowMapper<OrgInform> rowMapper = BeanPropertyRowMapper.newInstance(OrgInform.class);
        taskList = this.jdbcTemplate.query(sql, rowMapper);
        return taskList;
    }


    //查询会议地址
    public List<Map<String, Object>> queryAllPlace() {
        List<Map<String, Object>> placeList = null;
        String sql = "SELECT place_id, place FROM hg_party_place ORDER BY add_time DESC";
//		   logger.info("sql :" + sql);
        placeList = this.jdbcTemplate.queryForList(sql);
        return placeList;
    }

    //查询会议地址
    public Map<String, Object> queryPlacesByPage(int pageno, String orgId) {
        List<Map<String, Object>> placeList = null;
        String sql = "SELECT * FROM hg_party_place WHERE org_id='" + orgId + "'  ORDER BY add_time DESC";
        String sql1 = sql + " limit 8 offset " + (pageno - 1) * 8;
//		   logger.info("sql :" + sql);
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql1);
        List<Map<String, Object>> count = this.jdbcTemplate.queryForList(sql);
        int total = count.size();
        if (total % 8 == 0) {
            map.put("totalPage", total / 8);
        } else {
            map.put("totalPage", total / 8 + 1);
        }
        map.put("pageNow", pageno);
        map.put("list", list);
        return map;
    }


    //删除地址
    public void delectePlaceById(String placeId) {
        String sql = "DELETE FROM hg_party_place WHERE place_id = '" + placeId + "'";
        this.jdbcTemplate.execute(sql);
    }


    //保存地址
    public void savePlace(String place, String placeId, Timestamp now, String orgid) {
        String sql = "INSERT INTO hg_party_place (place_id,place,add_time,org_id) VALUES('" + placeId + "','" + place + "','" + now + "','" + orgid + "') ";
        this.jdbcTemplate.execute(sql);
    }


    //根据二级党委用户名查询二级党委名称
    public List<Map<String, Object>> queryOrgByUser(String user) {
        String sql = "SELECT org_id,org_name FROM hg_party_org WHERE hg_party_org.org_contactor = '" + user + "' AND hg_party_org.org_type = 'secondary' and historic is false";
//		   logger.info("sql :" + sql);
        return this.jdbcTemplate.queryForList(sql);
    }


    public void insertOrgInform(List<String> orgIds, String informId) {

        List<String> inserts = orgIds.stream()
                .map(p -> "('" + informId + "','" + p + "')")
                .collect(Collectors.toList());
        String sql = "INSERT INTO hg_party_inform_group_info ( \"inform_id\", \"pub_org_id\") values " + String.join(",", inserts);
        jdbcTemplate.execute(sql);
    }

    public void updateOrgInform(String orgId, String informId) {
        String sql = "update hg_party_inform_group_info set has_resend = true where pub_org_id='" + orgId + "'" +
                " and inform_id ='" + informId + "'";
        jdbcTemplate.execute(sql);
    }

    public void updateOrgInform(String orgId, String informId, String send_to) {
        String sql = "update hg_party_inform_group_info set send_to = '" + send_to + "' where pub_org_id='" + orgId + "'" +
                " and inform_id ='" + informId + "'";
        jdbcTemplate.execute(sql);
    }

    //根据组织id 查询组织类型
    public List<Map<String, Object>> queryOrgById(String orgId) {
        String sql = "SELECT g.org_id,g.org_name,g.org_type FROM public.hg_party_org AS g WHERE g.org_id = '" + orgId + "' and g.historic is false";
        return this.jdbcTemplate.queryForList(sql);
    }


    //根据地点查询有误记录
    public List<Map<String, Object>> queryPlaceByPlace(String place, String orgId) {
        String sql = "SELECT * FROM hg_party_place WHERE place = '" + place + "' and org_id='" + orgId + "'";
        return this.jdbcTemplate.queryForList(sql);
    }

    //报送计划查询地点使用情况
    public List<Map<String, Object>> queryPlaceUseStatus(String place, Timestamp start, Timestamp end) {
        String sql = "SELECT m.* FROM hg_party_meeting_plan_info AS m WHERE m.place = '" + place + "' AND m.start_time BETWEEN '" + start + "' AND '" + end + "' OR m.end_time BETWEEN '" + start + "' AND '" + end + "'";
        return this.jdbcTemplate.queryForList(sql);
    }


    public Map<String, Object> findAttachment(String resourceId) {
        String sql = "select * from hg_party_attachment where resource_id = '" + resourceId + "'";
        try {
            return jdbcTemplate.queryForMap(sql);
        } catch (IncorrectResultSizeDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
	public void deleteAttachment(String resourceId) {
		String sql = "delete from hg_party_attachment where resource_id = ?";
		try {
			jdbcTemplate.update(sql, resourceId);
		} catch (IncorrectResultSizeDataAccessException e) {
			e.printStackTrace();
		}
	}

}
