package hg.party.command.organization;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.PublicInformation,
			"mvc.command.name=/hg/uploadFile"
	    },
	    service = MVCResourceCommand.class
)
public class AjaxAttachment implements MVCResourceCommand {

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		String fileName = "ajaxFileName";
		UploadPortletRequest  uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
		File[] uploadFiles = uploadPortletRequest.getFiles(fileName);
		System.out.println(uploadFiles);
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
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
		}
		return false;
	}
                                                        

}
