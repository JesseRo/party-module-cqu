package hg.party.command.party;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.entity.unity.ResourceProperties;
import party.constants.PartyPortletKeys;

/**
 * 图片上传
 * @author yujx
 *
 */

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
				"javax.portlet.name=" + PartyPortletKeys.PartyEntry,
				"javax.portlet.name=" + PartyPortletKeys.PartyEntryOrg,
				"mvc.command.name=/PartyImageCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyImageCommand implements MVCResourceCommand{
	Logger logger = Logger.getLogger(PartyImageCommand.class);
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse){
		PrintWriter printWriter=null;
		try {
			printWriter = resourceResponse.getWriter();
			Map<String,String> map=new HashMap<String,String>();
			UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(resourceRequest);
			String fileName="imageUrlfile";
			File file=upload.getFile(fileName);
			String uploadfilename = upload.getFileName(fileName);
			       uploadfilename = UUID.randomUUID().toString()+uploadfilename;
			ResourceProperties resourceProperties = new ResourceProperties();
			Properties properties = resourceProperties.getResourceProperties();//获取配置文件
			String uploadPath = properties.getProperty("uploadPath");
			File folder = new File(uploadPath+"/labImages");
			File filePath = new File(folder.getAbsolutePath() + File.separator + uploadfilename);
			
			try {
				FileUtil.copyFile(file, filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String filePathString=filePath.toString();
			String imageurl="/labImages/"+ uploadfilename;
			
			map.put("state", "true");
			map.put("url", imageurl);
			map.put("filePath", filePathString);
			printWriter.write(JSON.toJSONString(map));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}

