package hg.party.dao.toDoList;

import java.util.List;
import java.util.Map;

import org.apache.avalon.framework.logger.Logger;
import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.resource.StringResourceRetriever;

import hg.party.entity.toDoList.Evaluation;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月4日下午3:39:50<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true,service = EvaluationDao.class)
public class EvaluationDao extends PostgresqlDaoImpl<Evaluation> {
        org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(EvaluationDao.class);
	/**根据会议id和用户id查询该会议评价详情*/
	public Map<String,Object> findEvaluation(String meetingId,String userId){
		String sql="SELECT c.comments_time,c.comments_aspects_one,c.comments_aspects_two,c.comments_aspects_three,c.comments_aspects_four,p.meeting_theme,p.meeting_type "
				+ "FROM ("
				+ "SELECT * FROM hg_party_comments_info WHERE meeting_id=? AND participant_id=?)c LEFT JOIN hg_party_meeting_plan_info p "
				+ "ON p.meeting_id=c.meeting_id";
		return this.jdbcTemplate.queryForMap(sql,meetingId,userId);
	}
	//"查看进度组件"查看评分
	public Map<String, Object> meetingRating(String meeting_Id){
		String sql = "SELECT sum(comments_score)/count(*) as ave,count(*) AS peo FROM hg_party_comments_info "+
					 "WHERE meeting_id = ? "+
					 "GROUP BY meeting_id ";
		return this.jdbcTemplate.queryForMap(sql,meeting_Id);
	}
	//根据会议id查询评论是否为空
	public List<Map<String, Object>> evaluationList(String meeting_Id){
		String sql = "SELECT * FROM hg_party_comments_info "+
					"WHERE meeting_id = ? ";
		return this.jdbcTemplate.queryForList(sql,meeting_Id);
	}
	//根据支部ID和会议分类查询评价详情
	public List<Map<String, Object>> evaluationDtail(String meeting_Id,String branchId){
		String sql ="select AVG(ct.comments_aspects_one)*20 as comments_aspects_one, "+
					"AVG(ct.comments_aspects_two)*20 as comments_aspects_two, "+
					"AVG(ct.comments_aspects_three)*20 as comments_aspects_three, "+
					"AVG(ct.comments_aspects_four)*20 as comments_aspects_four "+
					"from hg_users_info  as us "+
					"JOIN hg_party_comments_info as ct on us.user_id=ct.participant_id "+
					"where us.user_department_id=?"+
					"and  ct.meeting_id=? ";
//		logger.info("sql : "+sql);
		return this.jdbcTemplate.queryForList(sql,branchId,meeting_Id);
	}
}
