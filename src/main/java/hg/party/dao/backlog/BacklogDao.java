package hg.party.dao.backlog;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.organization.PublicInformation;


/**
 * @author zhangzhenqi
 * 
 *
 */


@Component(immediate=true,service=BacklogDao.class)
public class BacklogDao extends PostgresqlDaoImpl<PublicInformation>{
	
	Logger logger = Logger.getLogger(BacklogDao.class);
   public void saveAttachment(String sql){
	   jdbcTemplate.update(sql);
	   
   }
   
   
   public List<PublicInformation> queryAllTasksForSecondCommittee(){
	   List<PublicInformation> taskList = new ArrayList<PublicInformation>();
	   String sql = "SELECT * FROM hg_party_public_inform ORDER BY public_date DESC";
	   logger.info("sql :" + sql);
	   RowMapper<PublicInformation> rowMapper = BeanPropertyRowMapper.newInstance(PublicInformation.class);
	   taskList = this.jdbcTemplate.query(sql, rowMapper);
	   logger.info("taskList :" + taskList);
	   return taskList;
   }
   
   
   
   
}
