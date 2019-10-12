package hg.party.command.PartyBranch;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name="+ PartyPortletKeys.DetailPortlet,
			"mvc.command.name=/hg/detailPage"
	    },
	      service = MVCResourceCommand.class
)
/**
 * 附件下载
 * @author gmb
 *
 */
public class DownFile implements MVCResourceCommand{

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws PortletException {
		    String url=ParamUtil.getString(resourceRequest, "url");
		    String name=ParamUtil.getString(resourceRequest, "name"); 
		    url = HtmlUtil.escape(url);
		    name = HtmlUtil.escape(name);
		try {	
		    HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
			ResourceProperties resourceProperties = new ResourceProperties();
			Properties properties = resourceProperties.getResourceProperties();//获取配置文件
			String uploadPath = properties.getProperty("uploadPath");
			InputStream in = new FileInputStream(uploadPath+url);
			//浏览器自己辨别文件类型
			res.addHeader("content-type","application/x-msdownload");
			res.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
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
