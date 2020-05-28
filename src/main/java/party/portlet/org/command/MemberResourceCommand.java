package party.portlet.org.command;

import java.io.PrintWriter;
import java.util.Map;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.OrgAdmin;
import hg.party.server.org.MemberService;
import hg.party.server.organization.OrgAdminService;
import hg.party.server.organization.OrgService;
import hg.util.postgres.PostgresqlPageResult;
import hg.util.result.Page;
import hg.util.result.ResultUtil;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import dt.session.SessionManager;

import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Org,
				"mvc.command.name=/org/member"
	    },
	    service = MVCResourceCommand.class
)
public class MemberResourceCommand implements MVCResourceCommand {
	Logger log = Logger.getLogger(OrgCRUDCommand.class);
	@Reference
	private MemberService memberService;
	@Reference
	private OrgService orgService;
	@Reference
	private OrgAdminService orgAdminService;

	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		try {
			PrintWriter printWriter=resourceResponse.getWriter();
			String id = ParamUtil.getString(resourceRequest, "id");
			boolean isHasPermission = isHasPermissions(resourceRequest,id);
			if(isHasPermission){
				int page = ParamUtil.getInteger(resourceRequest, "page",1);//当前页
				int pageSize = ParamUtil.getInteger(resourceRequest, "limit",10);
				Page pageObj = new Page(page,pageSize);
				Organization org = orgService.findOrgById(Integer.parseInt(id));
				PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(org.getOrg_type());
				String memberType = HtmlUtil.escape(ParamUtil.getString(resourceRequest, "memberType"));
				String keyword = HtmlUtil.escape(ParamUtil.getString(resourceRequest, "keyword"));
				String history =  HtmlUtil.escape(ParamUtil.getString(resourceRequest, "history"));
				PostgresqlPageResult<Map<String, Object>> data = memberService.pageMembersByOrg(org.getOrg_id(),partyOrgAdminTypeEnum,pageObj,memberType,history,keyword);
				Gson gson = new Gson();
				printWriter.write(gson.toJson(data.toJsonPageResponse()));
			}else{
				printWriter.write(JSON.toJSONString(ResultUtil.noAuthority("你没有该组织信息权限。")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
		
		//String orgId = ParamUtil.getString(resourceRequest, "orgId");//historic_root  current_root
//		orgId = HtmlUtil.escape(orgId);
//		String history = ParamUtil.getString(resourceRequest, "history");
//		history = HtmlUtil.escape(history);
//		String page = ParamUtil.getString(resourceRequest, "page");
//		page = HtmlUtil.escape(page);
//		String pageNow = ParamUtil.getString(resourceRequest, "pageNow");//当前页
//		pageNow = HtmlUtil.escape(pageNow);
//
//		if (StringUtils.isEmpty(pageNow)) {
//			page = "1";
//		}
//			QueryResult<Member> queryResult =null;
//			if("西南大学党委".equals(orgId)){
//				queryResult = memberDao.findByOrgAll(orgId, Integer.parseInt(pageNow));
//				if ("historic_root".equals(history)) {
//					queryResult =  memberDao.findByOrgAll(Integer.parseInt(pageNow));
//				}
//			}else if(ConstantsKey.ORG_TYPE_SECONDARY.equals(memberDao.ment(orgId))){
//			    queryResult = memberDao.findByOrgLevel(orgId,Integer.parseInt(pageNow));
//			    if ("historic_root".equals(history)) {
//					queryResult =  memberDao.findByOrgLevelHistory(orgId,Integer.parseInt(pageNow));
//				}
//			}else {
//				queryResult = memberDao.findByOrg(orgId, Integer.parseInt(pageNow));
//				if ("historic_root".equals(history)) {
//					queryResult =  memberDao.findByOrgHistory(orgId, Integer.parseInt(pageNow));
//				}
//			}


	}

	/**
	 * 查询用户对组织为id的组织的数据权限
	 * @param id
	 * @return
	 */
	public boolean isHasPermissions(ResourceRequest resourceRequest,String id){
		boolean permission = false;
		String sessionId=resourceRequest.getRequestedSessionId();
		Object user =	SessionManager.getAttribute(sessionId, "userName");
		if(user!=null && id != null){
			Organization org = orgService.findOrgById(Integer.parseInt(id));
			log.info("org:"+org);
			if(org != null ){
				String userId = user.toString();
				PartyOrgAdminTypeEnum partyOrgAdminTypeEnum = PartyOrgAdminTypeEnum.getEnum(org.getOrg_type());
				OrgAdmin orgAdmin = orgAdminService.findOrgAdmin(userId, PartyOrgAdminTypeEnum.ORGANIZATION);
				switch(partyOrgAdminTypeEnum){//党组织类型
					case ORGANIZATION:
						log.info("orgType:"+partyOrgAdminTypeEnum.getRole());
						if(orgAdmin != null && userId.equals(orgAdmin.getAdminId())){// 校组织管理员
							permission = true;
						}
						break;
					case SECONDARY:
						log.info("orgType:"+partyOrgAdminTypeEnum.getRole());
						if(orgAdmin != null && userId.equals(orgAdmin.getAdminId())){// 校组织管理员
							permission = true;
						}else{
							OrgAdmin secondaryAdmin = orgAdminService.findOrgAdmin(userId, org.getOrg_id());
							if(secondaryAdmin != null){// 二级党组织管理员
								permission = true;
							}
						}
						break;
					case BRANCH:
						log.info("orgType:"+partyOrgAdminTypeEnum.getRole());
						if(orgAdmin != null && userId.equals(orgAdmin.getAdminId())){//校组织管理员
							permission = true;
						}else{
							OrgAdmin secondaryAdmin = orgAdminService.findOrgAdmin(userId, org.getOrg_parent());
							if(secondaryAdmin != null){// 二级党组织管理员
								permission = true;
							}else{
								OrgAdmin branchAdmin = orgAdminService.findOrgAdmin(userId, org.getOrg_id());
								if(branchAdmin != null){// 支部管理员
									permission = true;
								}
							}
						}
						break;
				}

			}
		}
		return permission;
	}
}
