package party.portlet.transport.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.party.unity.ResourceProperties;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.entity.view.FileView;
import party.portlet.transport.dao.TransportDao;
import party.portlet.transport.entity.Transport;

import javax.portlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.TransportApplyPortlet,
				"mvc.command.name=/transport/receipt"
	    },
	    service = MVCResourceCommand.class
)
public class TransportUploadReceiptCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;
	@Reference
	private OrgDao orgDao;
	@Reference
	private TransportDao transportDao;


	@Override
	public boolean serveResource(ResourceRequest request, ResourceResponse resourceResponse) throws PortletException {
		String sessionId = request.getRequestedSessionId();
		String orgId = (String)SessionManager.getAttribute(sessionId, "orgId");
		String userId = (String) SessionManager.getAttribute(sessionId, "userName");

		UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(request);
		File receipt = upload.getFile("receipt");
		String sourceFileName = upload.getFileName("receipt");
		Transport transport = transportDao.findByUser(userId);
		transport.setStatus(ConstantsKey.RECEIPT);

		ResourceProperties resourceProperties = new ResourceProperties();
		Properties properties = resourceProperties.getResourceProperties();//获取配置文件
		String uploadPath = properties.getProperty("uploadPath");
		File folder = new File(uploadPath);

		String fileUrl = "/ajaxFileName/receipt/" + orgId + "/" + sourceFileName + "_" + System.currentTimeMillis();
		File filePath = new File(folder.getAbsolutePath() + fileUrl);

		try {
			FileUtil.copyFile(receipt, filePath);
			transport.setReceipt(fileUrl);
			transportDao.saveOrUpdate(transport);
			resourceResponse.getWriter().write("<script>alert('已上传回执');window.location.reload();</script>");
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
