package hg.party.command.common;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.entity.unity.ResourceProperties;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.NewPlan,
                "javax.portlet.name=" + PartyPortletKeys.SeocndCommitteeToDoList,
                "javax.portlet.name=" + PartyPortletKeys.PartyApproval,
                "mvc.command.name=/api/download"
        },
        service = MVCResourceCommand.class
)
public class FileDownCommand implements MVCResourceCommand {

    Logger logger = Logger.getLogger(FileDownCommand.class);

    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
        try {
            HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
            //下载附件名
            String fileName = ParamUtil.getString(resourceRequest, "fileName");
            //String fileName = ParamUtil.getString(resourceRequest, "fileName");
            ResourceProperties resourceProperties = new ResourceProperties();
            Properties properties = resourceProperties.getResourceProperties();//获取配置文件
            String uploadPath = properties.getProperty("uploadPath");

            String downPath = uploadPath + fileName;

            InputStream in = new FileInputStream(downPath);
            res.addHeader("content-type", "application/x-msdownload");//浏览器自己辨别文件类型
            res.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            int nRead = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((nRead = in.read(buffer)) > 0) {
                res.getOutputStream().write(buffer, 0, nRead);//文件流输出
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
