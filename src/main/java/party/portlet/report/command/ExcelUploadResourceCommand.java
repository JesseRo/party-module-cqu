package party.portlet.report.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.unity.ResourceProperties;
import hg.util.CollectionUtils;
import hg.util.ConstantsKey;
import hg.util.ExcelUtil;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.report.dao.ReportTaskOrgDao;
import party.portlet.report.entity.ReportOrgTask;
import party.portlet.report.entity.view.ExcelHandler;
import party.portlet.report.dao.ReportDao;
import party.portlet.report.dao.ReportTaskDao;
import party.portlet.report.entity.Report;
import party.portlet.report.entity.ReportTask;
import party.portlet.report.entity.view.FileView;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.BrunchReportPortlet,
				"mvc.command.name=/brunch/report/upload"
	    },
	    service = MVCResourceCommand.class
)
public class ExcelUploadResourceCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;

	@Reference
	private OrgDao orgDao;

	@Reference
	private ReportTaskDao reportTaskDao;
	@Reference
	private ReportTaskOrgDao reportTaskOrgDao;
	@Reference
	private ReportDao reportDao;

	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	@Reference
	TransactionUtil transactionUtil;
    
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{

		String taskId = ParamUtil.getString(resourceRequest, "taskId");
		String formId = ParamUtil.getString(resourceRequest, "formId");
		String sessionId = resourceRequest.getRequestedSessionId();
		String userName = SessionManager.getAttribute(sessionId, "userName").toString();
		String department = SessionManager.getAttribute(sessionId, "department").toString();

		String message = "上传成功";

		Report report = new Report();
		report.setOrg_id(department);
		String reportId = UUID.randomUUID().toString();
		report.setReport_id(reportId);
		report.setStatus(0);
		report.setTime(new Timestamp(System.currentTimeMillis()));
		report.setTask_id(taskId);

		String redirect = "";

		synchronized (PortalUtil.getHttpServletRequest(resourceRequest).getSession()) {
			String originalFormId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "formId-report");
			if (originalFormId.equals(formId)) {
				transactionUtil.startTransaction();
				try {
					ReportTask task = reportTaskDao.findByTaskId(taskId);
					String json = task.getFiles();
					List<ExcelHandler> excelHandlers = gson.fromJson(json, new TypeToken<List<ExcelHandler>>() {
					}.getType());
					List<String> filenames = excelHandlers.stream().map(ExcelHandler::getFileName).collect(Collectors.toList());
					UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
					String[] uploadFiles = uploadPortletRequest.getFileNames("files");
					List<String> uploadFilenames = Arrays.asList(uploadFiles);
					if (!CollectionUtils.isEquals(filenames, uploadFilenames)) {
						message = "文件名与模版不一致，请重试！";
					} else {
						List<FileView> fileViews = saveAttachment(uploadPortletRequest, taskId, department);
						List<ExcelHandler> excels = new ArrayList<>();
						for (FileView fileView : fileViews) {
							Map<String, List<Map<String, Object>>> datas = ExcelUtil.importAllSheet(fileView.getFilename(), new FileInputStream(fileView.getPath()));
							ExcelHandler excelHandler = new ExcelHandler(fileView.getFilename(), fileView.getPath(), datas);
							excels.add(excelHandler);
						}
						String files = gson.toJson(excels);
						report.setFiles(files);
						reportDao.saveOrUpdate(report);
						ReportOrgTask reportOrgTask = reportTaskOrgDao.findByTaskIdAndOrgId(taskId, department);
						reportOrgTask.setStatus(ConstantsKey.REPORTED);
						reportTaskOrgDao.saveOrUpdate(reportOrgTask);
					}
					transactionUtil.commit();
					SessionManager.setAttribute(resourceRequest.getRequestedSessionId(), "formId-report", "null");
					redirect = "window.parent.location.href='/brunch_report_list';";
				} catch (Exception e) {
					e.printStackTrace();
					transactionUtil.rollback();
				}
			}
		}

		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);

		try {
			res.getWriter().write("<script>alert('" + message + "');" + redirect + "</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<FileView> saveAttachment(UploadPortletRequest uploadPortletRequest, String taskId, String orgId) {
		File[] uploadFiles = uploadPortletRequest.getFiles("files");
		String[] filenames = uploadPortletRequest.getFileNames("files");
		List<FileView> fileViewList = new ArrayList<>();

		if (uploadFiles != null) {
			int i = 0;
			for (File file : uploadFiles) {
				FileView fileView = new FileView();
				String sourceFileName = filenames[i++];
				//保存文件最终路径
				ResourceProperties resourceProperties = new ResourceProperties();
				Properties properties = resourceProperties.getResourceProperties();//获取配置文件
				String uploadPath = properties.getProperty("uploadPath");
				File folder = new File(uploadPath);
				String fileUrl = "/ajaxFileName/" + taskId + "/" + orgId + "/" + sourceFileName;
				File filePath = new File(folder.getAbsolutePath() + fileUrl);
				//保存文件到物理路径
				try {
					FileUtil.copyFile(file, filePath);
					fileView.setFilename(sourceFileName);
					fileView.setPath(filePath.getAbsolutePath());
					fileViewList.add(fileView);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		return fileViewList;
	}


}
