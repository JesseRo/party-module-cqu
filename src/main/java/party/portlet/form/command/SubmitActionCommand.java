package party.portlet.form.command;
/**
 * 鍐呭鎽樿锛� 
 * 鍒涘缓浜� 銆�锛� Zhong LiMei
 * 鍒涘缓鏃ユ湡锛� 2017骞�10鏈�30鏃ヤ笅鍗�3:35:28
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;

import party.constants.PartyPortletKeys;
import party.portlet.form.FormDesign;
import party.portlet.form.FormDesignDao;
import party.portlet.form.Entity.Column;
import party.portlet.form.Entity.ColumnType;
import party.portlet.form.Entity.FormDefinition;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + PartyPortletKeys.Form,
			"mvc.command.name=/form/submit"
	    },
	    service = MVCActionCommand.class
	)
public class SubmitActionCommand extends BaseMVCActionCommand  {
	private Gson gson = new Gson();
	FormDesignDao formDesignDao = new FormDesignDao();

	
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		PortletPreferences preferences = actionRequest.getPreferences();
		String designId = preferences.getValue("designId", null);
		UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(actionRequest);

		Map<String, String> formValue = new HashMap<>();
		Map<String, File[]> formFiles = new HashMap<>();
		if (!StringUtils.isEmpty(designId)) {
			FormDesign design = formDesignDao.findOne(designId);
			FormDefinition formDefinition = gson.fromJson(design.getDesign(), FormDefinition.class);
			for(Column col : formDefinition.getColumns()){
				if (!col.getType().getName().equals(ColumnType.TYPE_FILE)) {
					String colValue = ParamUtil.getString(actionRequest, col.getId());
					formValue.put(col.getId(), colValue);
				}else {
				 	File[] files = upload.getFiles(col.getId());		
				 	formFiles.put(col.getId(), files);
				}
			}
		}
		actionRequest.setAttribute("formValue", formValue);
		actionRequest.setAttribute("formFile", formFiles);
	}

}

