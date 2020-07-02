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
import hg.util.ConstantsKey;
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

import static hg.util.ConstantsKey.ORG_DESC_MAP_REVERSE;


@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Org,
                "javax.portlet.name=" + PartyPortletKeys.OrgCRUD,
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
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String type = ParamUtil.getString(resourceRequest, "type");
        type = HtmlUtil.escape(type);

        UploadPortletRequest upload = PortalUtil.getUploadPortletRequest(resourceRequest);
        File excel = upload.getFile("excel");

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        String message;
        boolean succeed = true;
        try {
            List<Map<Object, Object>> maps = ExcelUtil.importExcel(excel);
            if (type.equalsIgnoreCase("org")) {
                savingOrg(maps);
            } else if (type.equalsIgnoreCase("member")) {
                savingMember(maps, "ddddd");
            }
            message = "导入成功";
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
            res.getWriter().write("<script>alert('" + message + "');" + reload + "</script>");
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
        } else {
            originals = memberDao.findByOrg(orgTree.stream().map(Organization::getOrg_id).collect(Collectors.toList()));
            if (originals == null) {
                originals = new ArrayList<>();
            }
        }

        List<Member> current = maps.stream().map(row -> {
            Member member = new Member();
            member.setMember_party_committee(Optional.of(row.get("所在二级党组织").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_name(Optional.of(row.get("姓名").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_birthday(Optional.of(row.get("出生日期").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_sex(Optional.of(row.get("性别").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_birth_place(Optional.of(row.get("籍贯").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_identity(Optional.of(row.get("公民身份证号").toString().trim().toUpperCase()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_ethnicity(Optional.of(row.get("民族").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_phone_number(Optional.of(row.get("电话").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_degree(Optional.of(row.get("学历").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_type(Optional.of(row.get("党员类型").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_join_date(Optional.of(row.get("入党时间").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_org(Optional.of(row.get("所属支部").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_fomal_date(Optional.of(row.get("转正时间").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            member.setMember_address(Optional.of(row.get("家庭住址").toString().trim()).filter(p -> !p.equals("")).orElse(null));
            return member;
        }).collect(Collectors.toList());

        List<String> identities = current.stream().map(Member::getMember_identity).distinct().collect(Collectors.toList());
        if (identities.size() != current.size()) {
            List<String> repeated = current.stream().map(Member::getMember_identity).collect(Collectors.toList());
            for (String identity : identities) {
                repeated.remove(identity);
            }
            throw new NotMatchingExcelDataException("有重复的身份证号:" + String.join("\\n", repeated));
        }

        Map<String, List<Organization>> orgGroup = orgs.stream().collect(Collectors.groupingBy(Organization::getOrg_parent));
        Map<String, List<Member>> currentGroup = current.stream().collect(Collectors.groupingBy(Member::getMember_party_committee));
        for (Map.Entry<String, List<Member>> entry : currentGroup.entrySet()) {
            Optional<Organization> par = orgs.stream().filter(p -> p.getOrg_fullname().equalsIgnoreCase(entry.getKey())).findAny();
            String pid = par.map(Organization::getOrg_id).orElseThrow(() -> NotMatchingExcelDataException.create(entry.getKey(), null, entry.getValue().get(0).getMember_name()));
            List<Organization> singleSecondary = orgGroup.get(pid);
            Map<String, List<Member>> singleSecondaryGroup = entry.getValue().stream().collect(Collectors.groupingBy(Member::getMember_org));
            for (Map.Entry<String, List<Member>> et : singleSecondaryGroup.entrySet()) {
                String sid;
                if (et.getKey().equalsIgnoreCase(par.get().getOrg_fullname())) {
                    // 人员直接属于二级党委
                    sid = pid;
                } else {
                    Optional<Organization> s = singleSecondary.stream()
                            .filter(p -> p.getOrg_fullname().equalsIgnoreCase(et.getKey()))
                            .findAny();
                    sid = s.map(Organization::getOrg_id)
                            .orElseThrow(() -> NotMatchingExcelDataException.create(entry.getKey(),
                                    et.getKey(), et.getValue().get(0).getMember_name()));
                }
                et.getValue().forEach(p -> p.setMember_org(sid));
            }
        }
        currentGroup = current.stream().collect(Collectors.groupingBy(Member::getMember_org));
        Map<String, List<Member>> originalGroup = originals.stream().collect(Collectors.groupingBy(Member::getMember_org));
        Map<String, List<Member>> ret = OrgUtil.upgradeMember(originalGroup, currentGroup, Member::getMember_identity, p -> p.setHistoric(true));
        ret.size();

        transactionUtil.startTransaction();
        try {
            List<Member> historic = ret.get("historic");
            List<Member> update = ret.get("update");
            List<Member> insert = ret.get("insert");

            memberDao.updateAll(update);
            memberDao.insertAll(insert);
            memberDao.historicAll(historic);
            Set<String> historicIdentities = historic.stream().map(Member::getMember_identity).collect(Collectors.toSet());
            Set<String> insertIdentities = insert.stream().map(Member::getMember_identity).collect(Collectors.toSet());

            List<Member> insertUser = insert.stream().filter(p -> !historicIdentities.contains(p.getMember_identity())).collect(Collectors.toList());
            memberDao.newUsers(insertUser);
            List<Member> updateUser = insert.stream().filter(p -> historicIdentities.contains(p.getMember_identity())).collect(Collectors.toList());
            memberDao.updateUsers(updateUser);
            List<Member> deleteUser = historic.stream().filter(p -> !insertIdentities.contains(p.getMember_identity())).collect(Collectors.toList());
            memberDao.deleteUsers(deleteUser);
            transactionUtil.commit();
        } catch (Exception e) {
            transactionUtil.rollback();
            throw e;
        }
    }

    @Transactional
    public void savingOrg(List<Map<Object, Object>> maps) throws NotMatchingExcelDataException {
        List<Organization> current = maps.stream().map(row -> {
            Organization org = new Organization();
            org.setOrg_fullname(row.get("党组织名称").toString().trim());
            org.setOrg_name(row.get("党组织简称").toString().trim());
            org.setOrg_secretary(row.get("党组织书记").toString().trim());
            org.setOrg_contactor(row.get("党组织联系人").toString().trim());
            org.setOrg_phone_number(row.get("联系电话").toString().trim());
            org.setOrg_type(row.get("党组织类型").toString().trim());
            org.setDesc_type(ORG_DESC_MAP_REVERSE.get(org.getOrg_type()));
            if (org.getOrg_type().equals("党委") || org.getOrg_type().equals("党工委") || org.getOrg_type().equals("党总支")) {
                org.setOrg_type(ConstantsKey.ORG_TYPE_SECONDARY);
            } else {
                org.setOrg_type(ConstantsKey.ORG_TYPE_BRANCH);
            }
            org.setOrg_parent(row.get("上级党组织").toString().trim());
            return org;
        }).collect(Collectors.toList());

        transactionUtil.startTransaction();

        try {
            Map<String, List<Organization>> orgMap = current.stream().collect(Collectors.groupingBy(Organization::getOrg_parent));
            String rootName = "中共重庆大学委员会";
            List<Organization> secondaries = orgMap.get(rootName);
            List<Organization> organizationList = new ArrayList<>(secondaries);
            for (Organization secondary : secondaries) {
                secondary.setOrg_parent("ddddd");
                String pid = UUID.randomUUID().toString();
                secondary.setOrg_id(pid);
                List<Organization> branches = orgMap.get(secondary.getOrg_fullname());
                if (branches != null) {
                    branches.forEach(p -> {
                        p.setOrg_parent(pid);
                        p.setOrg_id(UUID.randomUUID().toString());
                    });
                    organizationList.addAll(branches);
                }
            }
            orgDao.insertAll(organizationList);
            transactionUtil.commit();
        } catch (Exception e) {
            transactionUtil.rollback();
            throw e;
        }
    }
}
