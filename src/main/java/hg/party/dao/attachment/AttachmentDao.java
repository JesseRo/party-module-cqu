package hg.party.dao.attachment;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.dt.springjdbc.dao.impl.PostgresqlDaoImpl;

import hg.party.entity.attachment.Hg_Content_Management_Attachment;

/**
 * 
 * 
 * @author Xzg
 *
 */

@Component(immediate = true,service = AttachmentDao.class)
public class AttachmentDao extends PostgresqlDaoImpl<Hg_Content_Management_Attachment> {
	public List<Map<String, Object>> resAttachmentByName(String Attachment_name){
		String sql = "select * from hg_content_management_attachment where attachment_name = ?";
		return this.jdbcTemplate.queryForList(sql,Attachment_name);
	}
	
	/**
	 * 根据resourceID查询attachment信息
	 * @param resourceId
	 * @return
	 */
	public List<Map<String, Object>> findByResourceId(String resourceId){
		String sql="select * from hg_content_management_attachment where resources_id = ?";
		return this.jdbcTemplate.queryForList(sql,resourceId);
	}
	
	/**
	 * 根据resourceID删除attachment信息
	 * @param resourceId
	 * @return
	 */
	public int deleteByresourceId(String resourceId){
		String sql="DELETE FROM "+this.tableName+" WHERE resources_id = ?";
		return this.jdbcTemplate.update(sql, new Object[] { resourceId });
	}
}
