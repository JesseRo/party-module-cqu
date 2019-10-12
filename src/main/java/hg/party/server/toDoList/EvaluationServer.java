package hg.party.server.toDoList;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.toDoList.EvaluationDao;
import hg.party.entity.toDoList.Evaluation;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2018年1月4日下午3:42:53<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true, service = EvaluationServer.class)
public class EvaluationServer {

	@Reference
	private EvaluationDao evaluationDao;
	
	/*保存评论信息到数据库*/
	public void save(Evaluation evaluation){
		evaluationDao.save(evaluation);
	}
	
	public Map<String,Object> findEvaluationDetails(String meetingId,String userId){
		return evaluationDao.findEvaluation(meetingId, userId);
	}
	//"查看进度组件"查看评分
	public Map<String, Object> meetingRating(String meeting_Id){
		return evaluationDao.meetingRating(meeting_Id);
	}
	//根据会议id查询评论是否为空
	public List<Map<String, Object>> evaluationList(String meeting_Id){
		return evaluationDao.evaluationList(meeting_Id);
	}
}
