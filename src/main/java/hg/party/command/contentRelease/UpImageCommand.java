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

import hg.party.unity.ResourceProperties;
import party.constants.PartyPortletKeys;
/**
 * @author caoxm
 * 百度编辑器上传图片
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.MeetingNotePortlet,
		"mvc.command.name=/ueditor/uploadImage"
    },
    service = MVCResourceCommand.class
)
public class UpImageCommand implements MVCResourceCommand {
	Logger logger = Logger.getLogger(UpImageCommand.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse){
		PrintWriter printWriter = null;
		try {
			logger.info("UpImageCommand serveResource.............");
			printWriter = resourceResponse.getWriter();
			Map map = new HashMap<String, String>();
			
			String fileName = "upfile";
			UploadPortletRequest  uploadPortletRequest = PortalUtil.getUploadPortletRequest(resourceRequest);
			File[] uploadFiles = uploadPortletRequest.getFiles(fileName);
			if(uploadFiles != null){
				for(File file : uploadFiles){
					String sourceFileName = uploadPortletRequest.getFileName(fileName);
					String[] strArr = sourceFileName.split("\\.");
					String suffix = "";
					if(strArr != null && strArr.length > 0){
						suffix = strArr[strArr.length - 1];
					}
					//保存文件最终路径
					ResourceProperties resourceProperties = new ResourceProperties();
					Properties properties = resourceProperties.getResourceProperties();//获取配置文件
					String uploadPath = properties.getProperty("uploadPath");
					File folder = new File(uploadPath+"/ueditorUpload");
					
					String imgUrl = "/" + sourceFileName;
					if("" != suffix){
						imgUrl = "/" +"imgShow" + "/" + UUID.randomUUID().toString() + "." + suffix;
					}
					
					File filePath = new File(folder.getAbsolutePath() + imgUrl);
					//保存文件到物理路径
					FileUtil.copyFile(file, filePath);
					//保存到文档库
					map.put("state", "SUCCESS");
					map.put("url", imgUrl);
					printWriter.write(JSON.toJSONString(map));
				}
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}