package party.portlet.org.command;

import com.alibaba.fastjson.JSON;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import dt.session.SessionManager;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;
import hg.party.server.login.UserService;
import hg.party.server.member.MemberEditService;
import hg.party.server.org.MemberService;
import hg.util.result.ResultUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.memberEdit.MemberEdit;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Component(immediate = true, property = {
		"javax.portlet.name=" + PartyPortletKeys.PersonalInfoPortlet,
		"mvc.command.name=/org/user/applyUpdate"
}, service = MVCResourceCommand.class)
public class ApplyUpdatePersonResourceCommand implements MVCResourceCommand {
	Logger log = Logger.getLogger(ApplyUpdatePersonResourceCommand.class);
	@Reference
	private MemberEditService memberEditService;
	@Reference
	private MemberService memberService;
	@Reference
	private UserService userService;
	@Override
	@Transactional
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) {
		Object userId = SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");
		PrintWriter printWriter = null;
		try {
			printWriter = resourceResponse.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String userName = ParamUtil.getString(resourceRequest, "userName");
		String sex = ParamUtil.getString(resourceRequest, "sex");
		String ethnicity = ParamUtil.getString(resourceRequest, "ethnicity");
		String birth_place = ParamUtil.getString(resourceRequest, "birth_place");
		String province = ParamUtil.getString(resourceRequest, "province");
		String city = ParamUtil.getString(resourceRequest, "city");
		String birthday = ParamUtil.getString(resourceRequest, "birthday");
		String join_party_time = ParamUtil.getString(resourceRequest, "join_party_time");
		String turn_Time = ParamUtil.getString(resourceRequest, "turn_Time");
		String telephone = ParamUtil.getString(resourceRequest, "telephone");
		String ID_card = ParamUtil.getString(resourceRequest, "ID_card");
		String member_degree = ParamUtil.getString(resourceRequest, "member_degree");
		String party_type = ParamUtil.getString(resourceRequest, "party_type");
		String home_addrss = ParamUtil.getString(resourceRequest, "home_addrss");
		String email = ParamUtil.getString(resourceRequest, "email");
		String job = ParamUtil.getString(resourceRequest, "job");
		String marriage = ParamUtil.getString(resourceRequest, "marriage");
		String id = ParamUtil.getString(resourceRequest, "id");
		String prevID_card = ParamUtil.getString(resourceRequest, "prevID_card");
		String title = ParamUtil.getString(resourceRequest, "major_title");
		String unit = ParamUtil.getString(resourceRequest, "unit");
		String isLeader = ParamUtil.getString(resourceRequest, "isLeader");
		ID_card = ID_card.toUpperCase();
		try {
			if(userId!= null && !StringUtils.isEmpty(id)){
				User user= userService.findByUserId(String.valueOf(userId));
				Member member = memberService.findMemberByIdentity(String.valueOf(userId));
				if(member!=null){
					MemberEdit memberEdit = new MemberEdit(userName, sex, ethnicity, birthday, prevID_card,member_degree, job, join_party_time, turn_Time, member.getMember_org(), party_type,home_addrss, telephone, birth_place, email, title, marriage, unit, province, city, isLeader,user.getId());
					int ret = memberEditService.insertMemberEdit(memberEdit);
					if(ret > 0 ){
						log.info("成功信息:[" + new Date() + "] [by " + userId + "]  ID_card :[" + ID_card + "]");
						printWriter.write(JSON.toJSONString(ResultUtil.success(ret)));
					}else{
						printWriter.write(JSON.toJSONString(ResultUtil.fail("用户信息异常！")));
					}

				}else{
					printWriter.write(JSON.toJSONString(ResultUtil.fail("用户信息异常！")));
				}

			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.fail("缺少必要参数！")));
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.info("异常信息:[" + new Date() + "] [by " + userId + "]  ID_card :[" + ID_card + "]");
			printWriter.write(JSON.toJSONString(ResultUtil.fail("操作异常，请联系技术人员！")));
			return false;
		}
		return false;
	}

}
