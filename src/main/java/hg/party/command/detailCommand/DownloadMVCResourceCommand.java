package hg.party.command.detailCommand;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;


/**
 * 附件下载command  来自龚明波
 * @author zhangminggang
 *
 */

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Details,
				"javax.portlet.name=" + PartyPortletKeys.NoticeDetails,
				"javax.portlet.name=" + PartyPortletKeys.PartyApproval,
				"mvc.command.name=/dowloadResourceCommand"
	    },
	    service = MVCResourceCommand.class
)
public class DownloadMVCResourceCommand implements MVCResourceCommand {
	Logger logger = Logger.getLogger(DownloadMVCResourceCommand.class);
	@Override
	public boolean serveResource(ResourceRequest resourceRequest,
			                                ResourceResponse resourceResponse)
                                                    throws PortletException {
		  try {	
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		//下载附件名 
		String name=ParamUtil.getString(resourceRequest, "attachment_url");
		name = HtmlUtil.escape(name);
		logger.info("name="+name);
//		name=name+".jpg";
		ResourceProperties resourceProperties = new ResourceProperties();
		Properties properties = resourceProperties.getResourceProperties();//获取配置文件
		String uploadPath = properties.getProperty("uploadPath");
        String downPath=uploadPath+name;
        InputStream in = new FileInputStream(downPath);
		//String newFeileName = "bitronix.properties";//指定下载时显示的名字	
		res.addHeader("content-type","application/x-msdownload");//浏览器自己辨别文件类型
		res.addHeader("Content-Disposition", "attachment; filename=" + name);
		//res.addHeader("Content-Length", String.valueOf(in.available()));
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
