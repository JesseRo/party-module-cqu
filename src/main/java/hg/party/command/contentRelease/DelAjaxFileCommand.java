/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hg.party.command.contentRelease;

import java.io.File;
import java.util.Properties;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.entity.attachment.Hg_Content_Management_Attachment;
import hg.party.server.attachment.AttachmentServer;
import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;
/**
 * @author caoxm
 * ajax文件删除
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
		"mvc.command.name=/ueditor/delUploadAjaxFile"
    },
    service = MVCResourceCommand.class
)
public class DelAjaxFileCommand implements MVCResourceCommand {
	Logger logger = Logger.getLogger(DelAjaxFileCommand.class);
	@Reference
	private AttachmentServer attachmentServer;
	

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse){
		try {
			int id = ParamUtil.getInteger(resourceRequest, "id");
			if(id != 0){
				Hg_Content_Management_Attachment attachment = attachmentServer.queryAttachmentById(id);
				attachmentServer.delAttachmentById(id);
				if(null != attachment && null != attachment.getAttachment_url() && "" != attachment.getAttachment_url()){
					//保存文件最终路径
					ResourceProperties resourceProperties = new ResourceProperties();
					Properties properties = resourceProperties.getResourceProperties();//获取配置文件
					String fileUrl = properties.getProperty("uploadPath");
					fileUrl += attachment.getAttachment_url();
					File file = new File(fileUrl);
					FileUtil.delete(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}