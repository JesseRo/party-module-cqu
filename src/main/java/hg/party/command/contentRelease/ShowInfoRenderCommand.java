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

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import hg.party.server.contentInfo.ContentInfoServer;
import party.constants.PartyPortletKeys;
/**
 * @author caoxm
 * ajax文件上传
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PartyPortletKeys.ContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.SecondPartyContentRelease,
		"javax.portlet.name=" + PartyPortletKeys.OrganizationContentRelease,
		"mvc.command.name=/info/showContent"
    },
    service = MVCRenderCommand.class
)
public class ShowInfoRenderCommand implements MVCRenderCommand {
	Logger logger = Logger.getLogger(ShowInfoRenderCommand.class);
	@Reference
	private ContentInfoServer infoServer;

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse)  {
		try {
			String content_title = ParamUtil.getString(renderRequest, "content_title_show");//标题
			content_title =	HtmlUtil.escape(content_title);
			String content_body = ParamUtil.getString(renderRequest, "content_body_show");//正文
		//	content_body =	HtmlUtil.escape(content_body);
			renderRequest.setAttribute("content_title", content_title);
			renderRequest.setAttribute("content_body", content_body);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/contentRelease/showContent.jsp";
	}
}