package hg.party.server.publication;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.publication.PublicationDao;
import hg.party.entity.publication.Hg_Publication_Pushing;

/**
 * 内容摘要： 
 * 创建人 　： Zhong LiMei
 * 创建日期： 2017年10月26日下午1:37:31
 */
@Component(immediate=true,service=PublicationServer.class)
public class PublicationServer {
	@Reference
	private PublicationDao dao;
	public void save(Hg_Publication_Pushing publication){
		dao.save(publication);
	}
	
	public void update(Hg_Publication_Pushing publication){
		dao.update(publication);
	}
	
	public Hg_Publication_Pushing findByUser(String user,String type){
		List<Map<String, Object>> listMap = null;
		Hg_Publication_Pushing publication = null;
		try {
			listMap = dao.findByUser(user,type);
			publication = new Hg_Publication_Pushing();
			for (Map<String, Object> map : listMap) {
				publication.setId(Integer.parseInt(map.get("id")+""));
				publication.setChat_channel((String)map.get("chat_channel"));
				publication.setPublication_text((String)map.get("publication_text"));
				publication.setPublication_type((String)map.get("publication_type"));
				publication.setRocket_chat_password((String)map.get("rocket_chat_password"));
				publication.setRocket_chat_url((String)map.get("rocket_chat_url"));
				publication.setRocket_chat_user((String)map.get("rocket_chat_user"));
				publication.setUser_alias((String)map.get("user_alias"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return publication;
	}
	public int findIntegerUser(String user,String type){
		return dao.findIntegerUser(user,type);
	}
	public void delete(Hg_Publication_Pushing publication){
		dao.delete(publication);
	}
	public List<Hg_Publication_Pushing> findAll(){
		return dao.findAll();
	}
	public Hg_Publication_Pushing findById(int id){
		return dao.findById(id);
	}
	public void deleteById(int id){
		dao.delete(id);
	}
	public void saveOrUpdate(Hg_Publication_Pushing publication_Pushing){
		dao.saveOrUpdate(publication_Pushing);
	}
	public List<Hg_Publication_Pushing> findByUser(String user){
		return dao.findByUser(user);
	}
}

