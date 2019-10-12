package hg.party.command.party;

import java.io.File;
import java.util.Properties;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;
/**
 * yjx
 * 抽查图片删除
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.PartyEntryOrg,
		"javax.portlet.name=" + PartyPortletKeys.PartyEntry,
		"mvc.command.name=/DelAjaxImageCommand"
    },
    service = MVCResourceCommand.class
)
public class DelAjaxImageCommand implements MVCResourceCommand {
	Logger logger = Logger.getLogger(DelAjaxImageCommand.class);

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse){
		try {
			String imgUrl = ParamUtil.getString(resourceRequest, "id");
			//保存文件最终路径
			ResourceProperties resourceProperties = new ResourceProperties();
			Properties properties = resourceProperties.getResourceProperties();//获取配置文件
			String fileUrl = properties.getProperty("uploadPath");
			fileUrl += imgUrl;
			File file = new File(fileUrl);
			FileUtil.delete(file);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}