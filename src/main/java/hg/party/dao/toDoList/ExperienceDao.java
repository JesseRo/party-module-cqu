package hg.party.dao.toDoList;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.toDoList.Experience;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年11月1日上午9:51:36<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true,service = ExperienceDao.class)
public class ExperienceDao extends PostgresqlDaoImpl<Experience> {

	/**根据用户id和会议id获取心得详情*/
	public Map<String,Object> findExperience(String meetingId,String userId){
		String sql="SELECT c.upload_time,c.experience_content,p.meeting_theme,p.meeting_type FROM ("
				+ "SELECT * FROM hg_party_learning_experience WHERE meeting_id=? AND participant_id=?)c LEFT JOIN hg_party_meeting_plan_info p "
				+ "ON p.meeting_id=c.meeting_id";
		return this.jdbcTemplate.queryForMap(sql,meetingId,userId);
	}
}
