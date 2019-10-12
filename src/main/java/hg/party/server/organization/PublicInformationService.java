package hg.party.server.organization;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import org.osgi.service.component.annotations.Component;
import hg.party.dao.organization.PublicInformationDao;
import hg.party.entity.party.OrgInform;

@Component(immediate=true,service=PublicInformationService.class)
public class PublicInformationService {
	PublicInformationDao dao=new PublicInformationDao();

	public void savePublicInformation(OrgInform p) {
		dao.save(p);
	}
	public int updatePublicInformation(OrgInform p) {
		return dao.update(p);
	}
	public int updateFormId(Timestamp startTime,Timestamp endTime,Timestamp deadline,String informId) {	
		return dao.updateFormId(startTime, endTime, deadline, informId);
	}
	public List< OrgInform> findByInformId(String infromId) {	
		return dao.findByInformId(infromId);
	}
	public int saveAttachment(String sql) {
		int n = dao.saveAttachment(sql);
		return n;
	}

	public int seconedAssign(String assignId, int id) {
		int n = dao.seconedAssign(assignId, id);
		return n;
	}
	public int deleteInformByInformId(String informId) {
		int n = dao.deleteInformByInformId(informId);
		return n;
	}

	public int orgAssign(String assignId, int id) {
		int n = dao.orgAssign(assignId, id);
		return n;
	}
	public int updateAssignPersonState(String assignId) {
		int n = dao.updateAssignPersonState(assignId);
		return n;
	}

	public List<Map<String, Object>> findMeetingTypeAndTheme() {

		return dao.findMeetingTypeAndTheme();
	}

	public static void main(String[] args) throws ParseException {
		PublicInformationService p = new PublicInformationService();

	}
}
