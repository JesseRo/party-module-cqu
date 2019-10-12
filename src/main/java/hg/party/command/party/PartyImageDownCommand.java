package hg.party.command.party;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.entity.unity.ResourceProperties;
import party.constants.PartyPortletKeys;


/**
 * 内容摘要： 图片下载
 * 创建人 ： yjx
 *
 */

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.PartyEntry,
				"javax.portlet.name=" + PartyPortletKeys.PartySecondary,
				"javax.portlet.name=" + PartyPortletKeys.PartyMeetingPlan,
				"javax.portlet.name=" + PartyPortletKeys.PartyEntryOrg,
				"javax.portlet.name=" + PartyPortletKeys.PartySecondaryBranch,
				"javax.portlet.name=" + PartyPortletKeys.Form,
				"mvc.command.name=/PartyImageDownCommand"
	    },
	    service = MVCResourceCommand.class
)
public class PartyImageDownCommand implements MVCResourceCommand {

	Logger logger = Logger.getLogger(PartyImageDownCommand.class);
	public boolean serveResource(ResourceRequest resourceRequest,ResourceResponse resourceResponse){
		try {	
			HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
			//下载附件名 
			String imageNeme=ParamUtil.getString(resourceRequest, "imageNeme");//图片名字
			ResourceProperties resourceProperties = new ResourceProperties();
			Properties properties = resourceProperties.getResourceProperties();//获取配置文件
			String uploadPath = properties.getProperty("uploadPath");
			
	        String downPath = uploadPath + imageNeme;
	        
	        InputStream in = new FileInputStream(downPath);
			res.addHeader("content-type","application/x-msdownload");//浏览器自己辨别文件类型
			res.addHeader("Content-Disposition", "attachment; filename=" + imageNeme);
			int nRead = 0;
			byte[] buffer = new byte[1024*8];
			while((nRead = in.read(buffer)) > 0){
				res.getOutputStream().write(buffer, 0, nRead);//文件流输出
			}
			in.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
