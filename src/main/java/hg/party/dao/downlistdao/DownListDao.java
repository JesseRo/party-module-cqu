package hg.party.dao.downlistdao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.party.Hg_Value_Attribute_Info;


@Component(immediate = true,service = DownListDao.class)
public class DownListDao  extends PostgresqlDaoImpl<Hg_Value_Attribute_Info>{
	public List<Hg_Value_Attribute_Info> findByasdasdId(String id){
		int Id = Integer.parseInt(id);
		String sql = "select * from Hg_Value_Attribute_Info where id=? ";
		RowMapper<Hg_Value_Attribute_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Value_Attribute_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper,Id);
	}
	
	//获取驳回理由
	public List<Hg_Value_Attribute_Info> reasson(){
		String sql = "SELECT * FROM hg_value_attribute_info "+
					"WHERE resources_type = 'reason' ";
		RowMapper<Hg_Value_Attribute_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Value_Attribute_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper);
		
	}
	//党内职务
	public List<Hg_Value_Attribute_Info> positior(){
		String sql="SELECT * FROM hg_value_attribute_info "+
				"WHERE resources_type = 'positior' ";
		RowMapper<Hg_Value_Attribute_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Value_Attribute_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper);
	}
	//学生宿舍园区
	public List<Hg_Value_Attribute_Info> room(){
		String sql="SELECT * FROM hg_value_attribute_info "+
				"WHERE resources_type = 'room' ";
		RowMapper<Hg_Value_Attribute_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Value_Attribute_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper);
	}

	//学生宿舍园区
	public List<Hg_Value_Attribute_Info> meetType(){
		String sql="SELECT * FROM hg_value_attribute_info "+
				"WHERE resources_type = 'meetingType' ";
		RowMapper<Hg_Value_Attribute_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Value_Attribute_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper);
	}
	//查询否有重复
	public List<Hg_Value_Attribute_Info> repeat(String val,String type){
		String sql = "SELECT * from hg_value_attribute_info "+
					 "WHERE resources_value =? "+
					 "and resources_type =? ";
		RowMapper<Hg_Value_Attribute_Info> rowMapper = BeanPropertyRowMapper.newInstance(Hg_Value_Attribute_Info.class);
		return this.jdbcTemplate.query(sql, rowMapper,val,type);
	}

	public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql) {
		String sql1=sql+" limit "+pageSize+" offset "+(pageNo-1)*pageSize;
	    Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForList(sql1);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql);
		int total=count.size();
		if(total%pageSize==0){
			map.put("totalPage", total/pageSize);
		}else{
			map.put("totalPage", total/pageSize+1);
		}
		map.put("pageNow", pageNo);
		map.put("list",list);
	   return map;
	}

	public Map<String, Object> postGresqlFind(int pageNo, int pageSize, String sql, String... s1) {
		String sql1=sql+" limit "+pageSize+" offset "+(pageNo-1)*pageSize;
	    Map<String, Object> map=new HashMap<>();
		List<Map<String,Object>> list=this.jdbcTemplate.queryForLis]]]]]]=(sql1,s1);
		List<Map<String,Object>> count=this.jdbcTemplate.queryForList(sql,s1);
		int total=count.size();
		if(total%pageSize==0){
			map.put("totalPage", total/pageSize);
		}else{
			map.put("totalPage", total/pageSize+1);
		}
		map.put("pageNow", pageNo);
		map.put("list",list);
	   return map;
	}
	
}
