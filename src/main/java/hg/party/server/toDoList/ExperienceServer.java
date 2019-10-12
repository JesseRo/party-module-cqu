package hg.party.server.toDoList;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.toDoList.ExperienceDao;
import hg.party.entity.toDoList.Experience;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年11月1日上午9:52:53<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true, service = ExperienceServer.class)
public class ExperienceServer {

	@Reference
	private ExperienceDao experienceDao;
	
	/*保存评论信息到数据库*/
	public void save(Experience experience){
		experienceDao.save(experience);
	}
	
	public Map<String,Object> findExperienceDetails(String meetingId,String userId){
		return experienceDao.findExperience(meetingId, userId);
	}
}
