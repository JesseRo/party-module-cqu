package hg.party.command.organization;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.server.sms.SmsService;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.secondCommittee.OtherDao;
import hg.party.entity.party.OrgInform;
import hg.party.server.organization.AssignedPersonService;
import hg.party.server.organization.GraftService;
import hg.party.server.organization.PublicInformationService;
import hg.party.server.partyBranch.PartyBranchService;
import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Form,
                "mvc.command.name=/hg/publicInformation"
        },
        service = MVCActionCommand.class
)
public class PublicInformationCommand extends BaseMVCActionCommand {
    @Reference
    PublicInformationService service;
    @Reference
    AssignedPersonService a;
    @Reference
    GraftService graft;
    @Reference
    PartyBranchService partyBranchService;
    @Reference
    OtherDao otherDao;
    @Reference
    OrgDao orgDao;

    @Reference
    TransactionUtil transactionUtil;

    DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    @Override
    @Transactional
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        String formId = ParamUtil.getString(actionRequest, "formId");
        String orgId = (String) SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "department");
        String userName = (String) SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "userName");
        Organization organization = orgDao.findByOrgId(orgId);
        String meetingType = ParamUtil.getString(actionRequest, "meetingType");
        String startDate = ParamUtil.getString(actionRequest, "startDate");
        String publicDate = ParamUtil.getString(actionRequest, "publicDate");
        String publicObject = ParamUtil.getString(actionRequest, "publicObject");
        String isComment = ParamUtil.getString(actionRequest, "isComment");
        /*String content=ParamUtil.getString(actionRequest, "content");*/
        String state = ParamUtil.getString(actionRequest, "state");
        String theme = ParamUtil.getString(actionRequest, "theme");
	/*	String note=ParamUtil.getString(actionRequest, "note");*/
        String end_time = ParamUtil.getString(actionRequest, "end_time");
        String deadline_time = ParamUtil.getString(actionRequest, "deadline_time");
        String content = ParamUtil.getString(actionRequest, "contentbody");
        String editState = ParamUtil.getString(actionRequest, "editState");
        String infrom_id = ParamUtil.getString(actionRequest, "infrom_id");
        
        
        formId= HtmlUtil.escape(formId);
        meetingType= HtmlUtil.escape(meetingType);
        startDate= HtmlUtil.escape(startDate);
        publicDate= HtmlUtil.escape(publicDate);
        publicObject= HtmlUtil.escape(publicObject);
        isComment= HtmlUtil.escape(isComment);
        state= HtmlUtil.escape(state);
        theme= HtmlUtil.escape(theme);
        end_time= HtmlUtil.escape(end_time);
        deadline_time= HtmlUtil.escape(deadline_time);
     //   content= HtmlUtil.escape(content);
        editState= HtmlUtil.escape(editState);
        infrom_id= HtmlUtil.escape(infrom_id);

        String fileName = "ajaxFileName";
        UploadPortletRequest uploadPortletRequest = PortalUtil.getUploadPortletRequest(actionRequest);
        File[] uploadFiles = uploadPortletRequest.getFiles(fileName);
        System.out.println(uploadFiles);
        String[] str = publicObject.split(",");
        synchronized (PortalUtil.getHttpServletRequest(actionRequest).getSession()) {
            String originalFormId = (String) SessionManager.getAttribute(actionRequest.getRequestedSessionId(), "formId-publicInformation");
            if (formId.equals(originalFormId)) {
                transactionUtil.startTransaction();
                OrgInform p = new OrgInform();
                if ("orgEdit".equals(editState)) {
                	 p=service.findByInformId(infrom_id).get(0);
				}
                String resourceid = UUID.randomUUID().toString();
                String redirect;
                try {
                    p.setMeeting_type(meetingType);
                    p.setStart_time(paerse(startDate));
                    p.setEnd_time(paerse(end_time));
                    p.setDeadline_time(paerse(deadline_time));
                    //设置发布时间
                    if (StringUtils.isEmpty(publicDate)) {
                        Timestamp timestamp = new Timestamp(new Date().getTime());
                        p.setRelease_time(timestamp);
                    } else {
                        p.setRelease_time(paerse(publicDate));
                    }
                    if ("edit".equals(editState)) {
                        resourceid = infrom_id;
                        graft.deletePublicObject(infrom_id);
                       // String sql = "DELETE from hg_party_org_inform_info WHERE inform_id='" + infrom_id + "'";
                       // service.saveAttachment(sql);
                        service.deleteInformByInformId(infrom_id);
                    } else if ("resend".equals(editState)) {
                        p.setParent(infrom_id);
                        otherDao.updateOrgInform(orgId, infrom_id);
                        state = "2";
                    }else if ("orgEdit".equals(editState)) {
                    	      resourceid = infrom_id;
					}
                    p.setInform_id(resourceid);
                    p.setContent(content);

                    if ("0".equals(state)) {
                        redirect = "/web/guest/graft";
                    } else if ("1".equals(state)) {
                        redirect = "/web/guest/passpublic";
                    } else {
                        redirect = "/web/guest/backlogtwo";
                    }
                    p.setPublic_status(state);
                    p.setEnable_comment(isComment);
                    p.setMeeting_theme(theme);
                    p.setInform_status("未读");
                    p.setPublisher(userName);
                    //发布人部门id
                    p.setOrg_type(orgId);
       
//                    if (uploadFiles != null && uploadFiles.length > 0 && uploadFiles[0] != null) {
//                        p.setAttachment("t");
//                    }
                    if (uploadFiles != null && uploadFiles.length > 0 && uploadFiles[0] != null) {
                        p.setAttachment("t");
                        saveAttchment(uploadFiles, resourceid, uploadPortletRequest);
                    } else if ("resend".equals(editState)) {
                        Map<String, Object> attach = otherDao.findAttachment(infrom_id);
                        if (attach != null) {
                            String sql = "INSERT INTO hg_party_attachment (\"resource_id\", \"attachment_name\", \"attachment_type\", \"attachment_url\", \"attachment_date\") VALUES " +
                                    "('" + resourceid + "','" + attach.get("attachment_name") + "', '" + attach.get("attachment_type") + "', '" + attach.get("attachment_url") + "','" + attach.get("attachment_date") + "')";
                            service.saveAttachment(sql);
                        }
                    }
                   
					if ("orgEdit".equals(editState)) {
						service.updatePublicInformation(p);
						service.updateFormId(paerse(startDate), paerse(end_time), paerse(deadline_time), infrom_id);
					}else{
						   service.savePublicInformation(p);
						   if (!publicObject.trim().equals("") && publicObject.length() > 1) {
					   for (int i = 0; i < str.length; i++) {
					       String sql = "INSERT INTO hg_party_inform_group_info (\"inform_id\", \"pub_org_id\", \"has_resend\", \"read_status\") VALUES ('" + resourceid + "', '" + str[i] + "', NULL, '未读');";
					        service.saveAttachment(sql);
					           }
					       }
					}                 
                    transactionUtil.commit();
                    SessionManager.setAttribute(actionRequest.getRequestedSessionId(), "formId-publicInformation", "null");
                } catch (Exception e) {
                    e.printStackTrace();
                    transactionUtil.rollback();
                    throw e;
                }
                /* 发送短信通知
                if ("1".equals(state) && !"orgEdit".equals(editState)) {
                    String sms = String.format("【西南大学党务工作信息平台通知】\r\n各二级党组织：请研究部署%s。\r\n主题：%s\r\n时间：%s-%s\r\n申报计划截止时间： %s\r\n党委组织部\r\n%s",
                            p.getMeeting_type(), p.getMeeting_theme(), startDate, end_time.substring(11), deadline_time, dateFormat.format(p.getRelease_time()));
                    List<String> numbers = orgDao.findAdminPhoneNumberIn(Arrays.asList(str));
                    SmsService.smsSend(resourceid, sms, numbers);
                } else if ("2".equals(state)) {
                    String sms = String.format("【西南大学党务工作信息平台通知】\r\n各党支部：请研究部署%s。\r\n主题：%s\r\n时间：%s-%s\r\n申报计划截止时间： %s\r\n%s\r\n%s",
                            p.getMeeting_type(), p.getMeeting_theme(), startDate, end_time.substring(11), deadline_time, organization.getOrg_name(), dateFormat.format(p.getRelease_time()));
                    List<String> numbers = orgDao.findAdminPhoneNumberIn(Arrays.asList(str));
                    SmsService.smsSend(resourceid, sms, numbers);
                }
                */

                if ("resend".equals(editState)) {
                    actionResponse.sendRedirect("/web/guest/backlogtwo");
                }
                actionResponse.sendRedirect(redirect);
            }
        }
    }

    //解析时间
    public Timestamp paerse(String string) {
        try {
            Timestamp ts = Timestamp.valueOf(string);
            return ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Timestamp(System.currentTimeMillis());
    }

    //保存附件
    public void saveAttchment(File[] uploadFiles, String resoureid, UploadPortletRequest uploadPortletRequest) {
        if (uploadFiles != null) {
            for (File file : uploadFiles) {
                String sourceFileName = uploadPortletRequest.getFileName("ajaxFileName");
                String[] strArr = sourceFileName.split("\\.");
                String suffix = "";//文件后缀
                if (strArr != null && strArr.length > 0) {
                    suffix = strArr[strArr.length - 1];
                }
                //保存文件最终路径
                ResourceProperties resourceProperties = new ResourceProperties();
                Properties properties = resourceProperties.getResourceProperties();//获取配置文件
                String uploadPath = properties.getProperty("uploadPath");
                File folder = new File(uploadPath);
                String fileUrl = "/" + sourceFileName;
                String saveName = UUID.randomUUID().toString();
                if ("" != suffix) {
                    fileUrl = "/" + "ajaxFileName" + "/" + saveName + sourceFileName;
                }

                File filePath = new File(folder.getAbsolutePath() + fileUrl);
                //保存文件到物理路径
                try {
                    FileUtil.copyFile(file, filePath);
                    Timestamp timestamp = new Timestamp(new Date().getTime());
                    String sql = "INSERT INTO hg_party_attachment (\"resource_id\", \"attachment_name\", \"attachment_type\", \"attachment_url\", \"attachment_date\") VALUES ('" + resoureid + "','" + sourceFileName + "', '" + suffix + "', '" + fileUrl + "','" + timestamp + "')";
                    service.saveAttachment(sql);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}






