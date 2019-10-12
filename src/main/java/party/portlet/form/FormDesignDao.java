package party.portlet.form;

import java.util.List;
import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;


@Component(immediate = true,service = FormDesignDao.class)
public class FormDesignDao extends PostgresqlDaoImpl<FormDesign>{
	
	public List<FormDesign> findAll() {
		String sql = "select * from hg_form_design where show is true";
		return jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(FormDesign.class));
	}
	
	public FormDesign findOne(String id) {
		String sql = "select * from hg_form_design where id = ? ";
		return jdbcTemplate.queryForObject(sql,  BeanPropertyRowMapper.newInstance(FormDesign.class),id);
	}
	
	public void saveOne(FormDesign formDesign) {
		String sql;
		// pgsql 转义
		formDesign.setDesign(formDesign.getDesign().replaceAll("'", "''")); 
		if (StringUtils.isEmpty(formDesign.getId())) {
			formDesign.setId(UUID.randomUUID().toString());
			sql = "insert into hg_form_design(id, name, description, design, submit_command, render_jsp, show) values(";
			String[] values = {formDesign.getId(), formDesign.getName(), formDesign.getDescription(), 
					formDesign.getDesign(), formDesign.getSubmit_command(), formDesign.getRender_jsp()};
			String v = "'" + String.join("','", values) + "'";
			sql = sql + v + "," + formDesign.isShow() + ")";
		}else{
			sql = "update hg_form_design set name = '" + formDesign.getName() + "'," + 
											"description = '" + formDesign.getDescription() + "'," +
											"design = '" + formDesign.getDesign() + "'," +
											"submit_command = '" + formDesign.getSubmit_command() + "'," +
											"render_command = '" + formDesign.getRender_command() + "'," +
											"render_jsp = '" + formDesign.getRender_jsp() + "'," +
											"show = " + formDesign.isShow() +
											" where id = '" + formDesign.getId() + "'";

		}

		jdbcTemplate.execute(sql);
	}
}