package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;
import hg.party.server.login.UserService;
import hg.party.server.org.MemberService;
import hg.util.result.ResultUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component(immediate = true, property = {
		"javax.portlet.name=" + PartyPortletKeys.PersonalInfoPortlet,
		"javax.portlet.name=" + PartyPortletKeys.PersonAddPortlet,
		"mvc.command.name=/org/user/update/isAbleIDCard"
}, service = MVCResourceCommand.class)
public class IDCardCheckResourceCommand implements MVCResourceCommand {
	@Reference
	private MemberService memberService;
	@Reference
	private UserService userService;
	@Override
	@Transactional
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		PrintWriter printWriter = null;
		try {
			printWriter = resourceResponse.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object userId = SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");
		String idCard = HtmlUtil.escape(ParamUtil.getString(resourceRequest, "idCard"));
		try {
			if(userId!= null && !StringUtils.isEmpty(idCard)){
				Boolean isAble = true;
				if(!idCard.equals(userId)){
					Member member = memberService.findMemberByIdentity(idCard);
					User user = userService.findByUserId(idCard);
					if(user!= null || member!= null){
						isAble = false;
					}
				}
				printWriter.write(JSON.toJSONString(ResultUtil.success(isAble)));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("缺少必要参数！")));
			}

		} catch (Exception e) {
			e.printStackTrace();
			printWriter.write(JSON.toJSONString(ResultUtil.fail("请求异常，请联系技术人员！")));
			return false;
		}
		return false;
	}

}
