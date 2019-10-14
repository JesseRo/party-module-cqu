package party.portlet.org.command;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.Member;
import hg.party.unity.ExcelUtil;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.org.NotMatchingExcelDataException;
import party.portlet.org.OrgUtil;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + PartyPortletKeys.Org,
				"mvc.command.name=/org/import"
	    },
	    service = MVCResourceCommand.class
)
public class ExcelUploadResourceCommand implements MVCResourceCommand {
	@Reference
	private MemberDao memberDao;
	@Reference
	private OrgDao orgDao;

	@Reference
	TransactionUtil transactionUtil;
    
	@Override
	public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException{
		String orgId = ParamUtil.getString(resourceRequest, "orgId");
		orgId = HtmlUtil.escape(orgId);
		String type = ParamUtil.getString(resourceRequest, "type");
		type = HtmlUtil.escape(type);
		
		UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(resourceRequest);
	 	File excel = upload.getFile("excel");		
	 	
		HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
		String message;
		boolean succeed = true;
		try {
			if (StringUtils.isEmpty(orgId)) {
				message = "必须指定一个组织节点";
			}else {
				List<Map<Object, Object>> maps = ExcelUtil.importExcel(excel);
				if (type.equalsIgnoreCase("org")) {
					savingOrg(maps, orgId);
				} else if (type.equalsIgnoreCase("member")) {
					savingMember(maps, orgId);
				}
				message = "导入成功";
			}
	 	} catch (NotMatchingExcelDataException e) {
			e.printStackTrace();
			message = e.getMessage();
			succeed = false;
		} catch (Exception e) {
			message = "未知错误";
			e.printStackTrace();
			succeed = false;
		}
		try {
			String reload = succeed ? "parent.location.reload();" : "";
			res.getWriter().write("<script>alert('" + message + "');" + reload +"</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Transactional
	void savingMember(List<Map<Object, Object>> maps, String orgId) throws NotMatchingExcelDataException {
		List<Organization> orgs = orgDao.findAll();
		List<Organization> orgTree = orgDao.findTree(orgId, true, true);
		List<Member> originals;
		if (orgTree == null) {
			originals = new ArrayList<>();
		}else{
			originals = memberDao.findByOrg(orgTree.stream().map(Organization::getOrg_id).collect(Collectors.toList()));
			if (originals == null) {
				originals = new ArrayList<>();
			}
		}
		
		List<Member> current = maps.stream().map(row->{
			Member member = new Member(); 
			member.setMember_party_committee(Optional.of(row.get("所在二级组织党委").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_name(Optional.of(row.get("姓名").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_birthday(Optional.of(row.get("出生日期").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_sex(Optional.of(row.get("性别").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_birth_place(Optional.of(row.get("籍贯").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_identity(Optional.of(row.get("公民身份证号").toString().trim().toUpperCase()).filter(p->!p.equals("")).orElse(null));
			member.setMember_ethnicity(Optional.of(row.get("民族").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_phone_number(Optional.of(row.get("电话").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_degree(Optional.of(row.get("学历").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_type(Optional.of(row.get("党员类型").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_join_date(Optional.of(row.get("入党时间").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_org(Optional.of(row.get("所在支部").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_fomal_date(Optional.of(row.get("转正时间").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_address(Optional.of(row.get("家庭住址").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_party_position(Optional.of(row.get("党内职务").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_major_title(Optional.of(row.get("党费标准（元/月）").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_new_class(Optional.of(row.get("学生宿舍").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_job(Optional.of(row.get("工作岗位").toString().trim()).filter(p->!p.equals("")).orElse(null));
			member.setMember_marriage(Optional.of(row.get("婚姻状况").toString().trim()).filter(p->!p.equals("")).orElse(null));
			return member;
		}).collect(Collectors.toList());
		
		List<String> committees = current.stream().map(Member::getMember_party_committee).distinct().collect(Collectors.toList());
		if(committees.size() != current.size()){
			for (String committee : committees){
				if(committee.lastIndexOf("委员会")!=committee.length()-3){
					throw new NotMatchingExcelDataException("第一行必须是\\n中国共产党西南大学\\n委员会:" + String.join("\\n", committee+"(错误数据!)"));
				}else{
					continue;
				}
			}
			
		}
		List<String> identities = current.stream().map(Member::getMember_identity).distinct().collect(Collectors.toList());
		if(identities.size() != current.size()){
			List<String> repeated = current.stream().map(Member::getMember_identity).collect(Collectors.toList());
			for (String identity : identities){
				repeated.remove(identity);
			}
			throw new NotMatchingExcelDataException("有重复的身份证号:" + String.join("\\n", repeated));
		}
		
		Map<String, List<Organization>> orgGroup = orgs.stream().collect(Collectors.groupingBy(Organization::getOrg_parent));
		Map<String, List<Member>> currentGroup = current.stream().collect(Collectors.groupingBy(Member::getMember_party_committee));
		for(Map.Entry<String, List<Member>> entry : currentGroup.entrySet()){
			Optional<Organization> par = orgs.stream().filter(p->p.getOrg_name().equalsIgnoreCase(entry.getKey())).findAny();
			String pid = par.map(Organization::getOrg_id).orElseThrow(()->NotMatchingExcelDataException.create(entry.getKey(), null, entry.getValue().get(0).getMember_name()));
			List<Organization> singleSecondary = orgGroup.get(pid);
			Map<String, List<Member>> singleSecondaryGroup = entry.getValue().stream().collect(Collectors.groupingBy(Member::getMember_org));
			for (Map.Entry<String, List<Member>> et : singleSecondaryGroup.entrySet()) {
				String sid;
				if (et.getKey().equalsIgnoreCase(par.get().getOrg_name())) {
					// 人员直接属于二级党委
					sid = pid;
				}else{
					Optional<Organization> s = singleSecondary.stream()
							.filter(p->p.getOrg_name().equalsIgnoreCase(et.getKey()))
							.findAny();
					sid = s.map(Organization::getOrg_id)
							.orElseThrow(()->NotMatchingExcelDataException.create(entry.getKey(), 
											et.getKey(), et.getValue().get(0).getMember_name()));
				}
				et.getValue().forEach(p->p.setMember_org(sid));
			}
		}
		currentGroup = current.stream().collect(Collectors.groupingBy(Member::getMember_org));
		Map<String,List<Member>> originalGroup = originals.stream().collect(Collectors.groupingBy(Member::getMember_org));
		Map<String, List<Member>> ret = OrgUtil.upgradeMember(originalGroup, currentGroup, Member::getMember_identity, p->p.setHistoric(true));
		ret.size();

		transactionUtil.startTransaction();
        try{
			List<Member> historic = ret.get("historic");
			List<Member> update = ret.get("update");
			List<Member> insert = ret.get("insert");
	
			memberDao.updateAll(update);
			memberDao.insertAll(insert);
			memberDao.historicAll(historic);
			Set<String > historicIdentities = historic.stream().map(Member::getMember_identity).collect(Collectors.toSet());
			Set<String > insertIdentities = insert.stream().map(Member::getMember_identity).collect(Collectors.toSet());
			
			List<Member> insertUser = insert.stream().filter(p->!historicIdentities.contains(p.getMember_identity())).collect(Collectors.toList());
			memberDao.newUsers(insertUser);
			List<Member> updateUser = insert.stream().filter(p->historicIdentities.contains(p.getMember_identity())).collect(Collectors.toList());
			memberDao.updateUsers(updateUser);
			List<Member> deleteUser = historic.stream().filter(p->!insertIdentities.contains(p.getMember_identity())).collect(Collectors.toList());
			memberDao.deleteUsers(deleteUser);
			transactionUtil.commit();
		}catch (Exception e) {
			transactionUtil.rollback();
			throw e;
		}
	}
	
	private void pre(List<Organization> orgs) throws NotMatchingExcelDataException {
		if (orgs.size() <= 2) {
			throw new NotMatchingExcelDataException("内容太少了");
		}
		Organization first = orgs.get(0);

		if (!first.getOrg_name().equals("中共重庆三峡职业学院委员会")){
			throw new NotMatchingExcelDataException("第一行必须是中共重庆三峡职业学院委员会");
		}
		if (!first.getOrg_code().equals("000000")){
			throw new NotMatchingExcelDataException("第一行必须是中共重庆三峡职业学院委员会，编号必须为000000");
		}

		List<String > oddNames = orgs.stream()
				.filter(org -> org.getOrg_code().length() != 3 && org.getOrg_code().length() != 6)
				.map(Organization::getOrg_name)
				.collect(Collectors.toList());
		if(oddNames != null && !oddNames.isEmpty()){
			throw new NotMatchingExcelDataException("组织编号必须为3位或者6位:" + String.join("\\n", oddNames));
		}
		
		if(orgs.stream().map(Organization::getOrg_code).distinct().count() != orgs.size()){
			throw new NotMatchingExcelDataException("组织编码有重复");
		}

		orgs = orgs.stream()
				.filter(org -> !org.getOrg_code().equals("000000"))
				.collect(Collectors.toList());
		
		oddNames = orgs.stream()
				.filter(org -> !org.getOrg_code().equals("000000"))
				.filter(org -> (org.getOrg_code().length() == 3 && !org.getOrg_type().equals("党委")) || (org.getOrg_code().length() == 6 && !org.getOrg_type().equals("党支部")))
				.map(Organization::getOrg_name)
				.collect(Collectors.toList());
		if (oddNames != null && !oddNames.isEmpty()){
			throw new NotMatchingExcelDataException("组织编号的格式必须匹配组织类别:" + String.join("\\n", oddNames));
		}

		first.setOrg_type("organization");

		List<Organization> secondary = orgs.stream()
				.filter(org -> org.getOrg_code().length() == 3)
				.peek(org -> {org.setOrg_type("secondary"); org.setOrg_parent("000000");})
				.collect(Collectors.toList());

		List<Organization> branch = orgs.stream().filter(org -> org.getOrg_code().length() == 6)
				.peek(org -> {org.setOrg_type("branch"); org.setOrg_parent(org.getOrg_code().substring(0, 3));})
				.collect(Collectors.toList());

		if(secondary == null || secondary.isEmpty()){
			throw new NotMatchingExcelDataException("必须要有党委");
		}
		
		if(branch == null || branch.isEmpty()){
			throw new NotMatchingExcelDataException("必须要有党支部");
		}

		if(secondary.stream().map(Organization::getOrg_name).distinct().count() != secondary.size()){
			throw new NotMatchingExcelDataException("党委不能重名");
		}

		for(Organization org : secondary){
			if(branch.stream().noneMatch(p -> p.getOrg_code().substring(0, 3).equals(org.getOrg_code()))){
				throw new NotMatchingExcelDataException("没有下级部门:" + org.getOrg_name());
			}

			List<Organization> thisBranch = branch.stream()
					.filter(p -> p.getOrg_code().substring(0, 3).equals(org.getOrg_code()))
					.collect(Collectors.toList());

			if (thisBranch.size() != thisBranch.stream().map(Organization::getOrg_name).distinct().count()){
				throw new NotMatchingExcelDataException("同一党委下的党支部不能重名");
			}
		}
		
		for(Organization org : branch){
			if(secondary.stream().noneMatch(p -> p.getOrg_code().equals(org.getOrg_code().substring(0, 3)))){
				throw new NotMatchingExcelDataException("没有上级部门:" + org.getOrg_name());
			}
		}
		
		orgs.forEach(ins -> ins.setOrg_id(UUID.randomUUID().toString()));
	}

	@Transactional
	public void savingOrg(List<Map<Object, Object>> maps, String orgId) throws NotMatchingExcelDataException {
		List<Organization> originals = orgDao.findAll();

		Organization root = orgDao.findByOrgId(orgId);
		if (!root.getOrg_type().equals("organization")){
			throw new NotMatchingExcelDataException("必须选中共重庆三峡职业学院委员会进行导入");
		}
		List<Organization> current = maps.stream().map(row->{
			Organization org = new Organization();
			org.setOrg_name(row.get("组织名称").toString().trim());
			org.setOrg_type(row.get("组织类别").toString().trim());
			org.setOrg_code(row.get("组织编号").toString().trim());
			return org;
		}).collect(Collectors.toList());
		
		pre(current);
				
		Map<String,List<Organization>> currentGroup = current.stream()
				.filter(p->!p.getOrg_type().equals("organization"))
				.collect(Collectors.groupingBy(Organization::getOrg_parent));
		
		Map<String,List<Organization>> orgGroup = originals.stream()
				.filter(p->!p.getOrg_type().equals("organization"))
				.collect(Collectors.groupingBy(Organization::getOrg_parent));
		for(Map.Entry<String, List<Organization>> entry : orgGroup.entrySet()){
			Optional<Organization> par = originals.stream().filter(p->p.getOrg_id().equalsIgnoreCase(entry.getKey())).findAny();
			String pCode = par.map(Organization::getOrg_code).orElseThrow(()->new NotMatchingExcelDataException("导入错误：" + entry.getValue().get(0).getOrg_name() ));
			entry.getValue().forEach(p->p.setOrg_parent(pCode));
		}
		
		orgGroup = originals.stream()
				.filter(p->!p.getOrg_type().equals("organization"))
				.collect(Collectors.groupingBy(Organization::getOrg_parent));
	
		Map<String,List<Organization>> ret = OrgUtil.updateOrg(root.getOrg_code(), orgGroup, currentGroup, Organization::getOrg_code, p->p.setHistoric(true));
		ret.size();

		transactionUtil.startTransaction();
		
        try{
        	List<Organization> forInsert = ret.get("insert");
        	List<Organization> forHistoric = ret.get("historic");
        	List<Organization> forUpdate = ret.get("update");
        	if (false) {
        		orgDao.getJdbcTemplate().execute("insert into to_be_import(code, name, type) values('sb11','sb','sb');");
        		orgDao.getJdbcTemplate().execute("insert into to_be_import(code, name, type) values('sb','sb','sb');");
			}else{
				if (ret.containsKey("insert") && ret.get("insert").size() > 0) {
					for(int i = 0; i < forInsert.size(); i++){
						Organization cur = forInsert.get(i);
						Optional<Organization> par = forInsert.stream().filter(p->p.getOrg_code().equalsIgnoreCase(cur.getOrg_parent())).findAny();
						String pid;
						if (par.isPresent()) {
							pid = par.get().getOrg_id();
						}else{
							Optional<String> pidOpt = originals.stream()
								.filter(p->p.getOrg_code().equalsIgnoreCase(cur.getOrg_parent()))
								.findAny().map(Organization::getOrg_id);
							if (pidOpt.isPresent()) {
								pid = pidOpt.get();
							}else {
								throw new NotMatchingExcelDataException("could not find parent org error:" + cur.getOrg_parent() + ", name is " + cur.getOrg_name());
							}
						}
						cur.setOrg_parent(pid);
					}
					orgDao.insertAll(forInsert);
				}
				if (ret.containsKey("historic") && ret.get("historic").size() > 0) {
					orgDao.historicAll(ret.get("historic"));
				}
				if (ret.containsKey("update") && ret.get("update").size() > 0) {
					orgDao.updateAll(ret.get("update"));
				}
			}
			transactionUtil.commit();
        }catch (Exception e) {
			transactionUtil.rollback();
        	throw e;
		}
	}
}
