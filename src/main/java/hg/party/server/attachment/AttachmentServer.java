package hg.party.server.attachment;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.attachment.AttachmentDao;
import hg.party.entity.attachment.Hg_Content_Management_Attachment;

@Component(immediate=true,service=AttachmentServer.class)
public class AttachmentServer {
	Logger logger = Logger.getLogger(AttachmentServer.class);
	
	@Reference
	private AttachmentDao dao;
	
	public Hg_Content_Management_Attachment findById(int id){
		Hg_Content_Management_Attachment att = null;
		try {
			att = dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return att;
	}
	
	public List<Hg_Content_Management_Attachment> findAll(){
		List<Hg_Content_Management_Attachment> list = null;
		try {
			list = dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Hg_Content_Management_Attachment> resListTree(){
		List<Hg_Content_Management_Attachment> attachments = null;
		try {
			attachments = dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attachments;
	}
	
	public int saveAttachment(Hg_Content_Management_Attachment attachment){
		try {
			return dao.save(attachment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int resAttachmentByName(String Attachment_name){
		try {
			List<Map<String, Object>> maps = dao.resAttachmentByName(Attachment_name);
			if(null != maps && maps.size() > 0){
				return Integer.parseInt(String.valueOf(maps.get(0).get("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int delAttachmentById(int id){
		try {
			return dao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Hg_Content_Management_Attachment queryAttachmentById(int id){
		Hg_Content_Management_Attachment attachment = null;
		try {
			return dao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attachment;
	}
	
	public int deleteByResourceId(String resourceId){
		return dao.deleteByresourceId(resourceId);
	}
	
	public List<Map<String, Object>> findByResourceId(String resourceId){
		return dao.findByResourceId(resourceId);
	}
}
