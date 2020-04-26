package hg.party.command.secondCommittee;


import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.entity.party.MeetingNote;
import hg.party.server.party.PartyMeetingNoteServer;
import hg.party.server.secondCommittee.SecondCommitteeService;
import hg.party.unity.ResourceProperties;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * 文件名称： 获取上传会议记录<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2017年12月27日下午3:11:52<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.Form,
				"javax.portlet.name=" + PartyPortletKeys.NewPlan,
				"mvc.command.name=/hg/uploadMeetingNotes"
	    },
	    service = MVCRenderCommand.class
)
public class UploadNoteCommand implements MVCRenderCommand{
	@Reference
	SecondCommitteeService secondCommitteeService;
	@Reference
	private PartyMeetingNoteServer partyMeetingNoteServer;
	Logger logger = Logger.getLogger(UploadNoteCommand.class);
	@Override
	public String render(RenderRequest request, RenderResponse response) throws PortletException {
		try {
			String from_uuid = ParamUtil.getString(request, "from_uuid");//uuid
			String meetingId = ParamUtil.getString(request, "meetingId");
			String starttime = ParamUtil.getString(request, "starttime");
			String endtime = ParamUtil.getString(request, "endtime");
			String shouldcount = ParamUtil.getString(request, "shouldcount");
			String actualcount = ParamUtil.getString(request, "actualcount");
			String leaves = ParamUtil.getString(request, "leaves");
			String absence = ParamUtil.getString(request, "absence");
			String attendance = ParamUtil.getString(request, "attendance");
			from_uuid = HtmlUtil.escape(from_uuid);
			meetingId = HtmlUtil.escape(meetingId);
			starttime = HtmlUtil.escape(starttime);
			endtime = HtmlUtil.escape(endtime);
			shouldcount = HtmlUtil.escape(shouldcount);
			actualcount = HtmlUtil.escape(actualcount);
			leaves = HtmlUtil.escape(leaves);
			absence = HtmlUtil.escape(absence);
			attendance = HtmlUtil.escape(attendance);
			
			String fileName ="attachment";
			UploadPortletRequest  uploadPortletRequest = PortalUtil.getUploadPortletRequest(request);
			File[] uploadFiles = uploadPortletRequest.getFiles(fileName);
			String url=null;
			if (uploadFiles!=null&&uploadFiles.length>0&&uploadFiles[0]!=null){
			    url=saveAttchment(uploadFiles,fileName,uploadPortletRequest);
				}
			
			synchronized (PortalUtil.getHttpServletRequest(request).getSession()) {
		        String originalFormId = (String) SessionManager.getAttribute(request.getRequestedSessionId(), "formId-Submission");
		        if(from_uuid.equals(originalFormId)){
		        	
					List<MeetingNote> meetingNotes = partyMeetingNoteServer.meetingNote(meetingId);
					MeetingNote meetingNote;
					if(meetingNotes.size() == 0){
						meetingNote = new MeetingNote();
					}else{
						meetingNote = meetingNotes.get(0);
					}
					meetingNote.setMeeting_id(meetingId);
					meetingNote.setStart_time(Timestamp.valueOf(starttime));
					meetingNote.setEnd_time(Timestamp.valueOf(endtime));
					meetingNote.setShoule_persons(Integer.valueOf(shouldcount));
					meetingNote.setActual_persons(Integer.valueOf(actualcount));
					meetingNote.setAttendance(attendance);
					meetingNote.setLeave_persons(leaves);
					meetingNote.setAttachment(url);
					secondCommitteeService.saveMeetingNote(meetingNote);
					secondCommitteeService.updateMeetingPlanCheckStarus(meetingId,"已上传"); // 已上传
					
					
					String orgId = null;
					String orgType = null;
					try {
						String sessionId=request.getRequestedSessionId();
						orgId = SessionManager.getAttribute(sessionId, "department").toString();
						//orgType 存session 是否生效
						orgType = SessionManager.getAttribute(sessionId, "orgType").toString();
					} catch (Exception e) {
//						logger.info("当前SESSION 获取信息失败!");
					}
					orgId=(StringUtils.isEmpty(orgId)) ? "050026623506":orgId;
					orgType = secondCommitteeService.queryOrgTypeByOrgId(orgId);
					
					HttpServletResponse response1 = PortalUtil.getHttpServletResponse(response);
					try {
						if("secondary".equals(orgType)){
							response1.sendRedirect("/backlogtwo");
						}else{
							response1.sendRedirect("/task");
						}
					} catch (Exception e) {
//						logger.info("页面跳转异常:" + e.getMessage());
					}
					SessionManager.setAttribute(request.getRequestedSessionId(), "formId-Submission", "null");
		        }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
/**
 * 保存附件
 * @param uploadFiles
 * @param fileName
 * @param uploadPortletRequest
 * @return
 */
	public String  saveAttchment(File[] uploadFiles,String fileName,UploadPortletRequest  uploadPortletRequest ){
		if(uploadFiles != null){
			for(File file : uploadFiles){
				String sourceFileName = uploadPortletRequest.getFileName(fileName);
				String[] strArr = sourceFileName.split("\\.");
				String suffix = "";//文件后缀
				if(strArr != null && strArr.length > 0){
					suffix = strArr[strArr.length - 1];
				}
				//保存文件最终路径
				ResourceProperties resourceProperties = new ResourceProperties();
				Properties properties = resourceProperties.getResourceProperties();//获取配置文件
				String uploadPath = properties.getProperty("uploadPath");
			    File folder=new File(uploadPath);
				String fileUrl = "/" + sourceFileName;
				String saveName = UUID.randomUUID().toString();
				if("" != suffix){
					fileUrl = "/" +"ajaxFileName" + "/" + saveName+sourceFileName;
				}
				
				File filePath = new File(folder.getAbsolutePath() + fileUrl);
				//保存文件到物理路径
				try {
					FileUtil.copyFile(file, filePath);
					return fileUrl;
					//Timestamp timestamp=new Timestamp(new Date().getTime());
		            //String sql="INSERT INTO hg_party_attachment (\"resource_id\", \"attachment_name\", \"attachment_type\", \"attachment_url\", \"attachment_date\") VALUES ('"+resoureid+"','"+sourceFileName+"', '"+suffix+"', '"+fileUrl+"','"+timestamp+"')";
				    //service.saveAttachment(sql);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				
				}
		}
		            return null;
				
	}

}
