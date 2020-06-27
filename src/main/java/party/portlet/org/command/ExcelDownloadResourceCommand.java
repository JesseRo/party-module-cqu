package party.portlet.org.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.Member;
import hg.party.unity.ExcelUtil;
import party.constants.PartyPortletKeys;


@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.Org,
                "mvc.command.name=/org/export"
        },
        service = MVCResourceCommand.class
)
public class ExcelDownloadResourceCommand implements MVCResourceCommand {
    @Reference
    private MemberDao memberDao;
    @Reference
    private OrgDao orgDao;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String orgId = ParamUtil.getString(resourceRequest, "orgId");
        orgId = HtmlUtil.escape(orgId);
        String type = ParamUtil.getString(resourceRequest, "type");
        type = HtmlUtil.escape(type);
        String orgName = ParamUtil.getString(resourceRequest, "orgName");
        orgName = HtmlUtil.escape(orgName);
        String ishistory = ParamUtil.getString(resourceRequest, "ishistory");
        ishistory = HtmlUtil.escape(ishistory);
        boolean ish=false;
        if ("historic_root".equals(ishistory)) {
			ish=true;
		}
        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        try {
            String filename = orgName;
//			Organization organization = orgDao.findByOrgId(orgId);
//			filename = organization.getOrg_name();
            if (type.equalsIgnoreCase("org")) {
                filename += "-党组织";
            } else {
                filename += "-人员";
            }
            LocalDateTime dt = LocalDateTime.now();
            filename +=".xlsx";
            res.setContentType("application/vnd.ms-excel;charset=utf-8");
            res.addHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("utf-8"), "ISO-8859-1"));
            
            List<Organization> organizations = orgDao.findTree(orgId, true, ish);
            organizations = organizations.stream().filter(p->!p.isHistoric()).collect(Collectors.toList());
            JSONArray ja = new JSONArray();
            String title = "root";
            if (type.equalsIgnoreCase("org")) {
                List<Organization> orgTree = generateTree(orgId, organizations);
                for (Organization organization : orgTree) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", organization.getOrg_name());
                    String orgType = organization.getOrg_type().equalsIgnoreCase("branch") ? "党支部" : "党委";
                    jsonObject.put("type", orgType);
                    jsonObject.put("code", organization.getOrg_code());
                    ja.add(jsonObject);
                }
                Map<String, String> headMap = new LinkedHashMap<String, String>();
                headMap.put("code", "组织编号");
                headMap.put("name", "组织名称");
                headMap.put("type", "组织类别");

               // SXSSFWorkbook workbook = ExcelUtil.exportExcelX(title, headMap, ja, null, 0, null);
                 SXSSFWorkbook workbook = ExcelUtil.exportExcelX(title, headMap, ja, null, 0, null);
                try {
                    workbook.write(res.getOutputStream());
                    workbook.close();
                    workbook.dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                List<Member> members = memberDao.findByOrg(organizations.stream().map(Organization::getOrg_id).collect(Collectors.toList()),ish);
                Map<String, List<Member>> memberGroup = members.stream().collect(Collectors.groupingBy(Member::getMember_org));
                for (Map.Entry<String, List<Member>> entry : memberGroup.entrySet()) {
                    String oName = organizations.stream().filter(p -> p.getOrg_id().equals(entry.getKey())).findAny().map(Organization::getOrg_name).get();
                    entry.getValue().forEach(p -> p.setMember_org(oName));
                }
                for (Member member : members) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("committee", member.getMember_party_committee());
                    jsonObject.put("name", member.getMember_name());
                    jsonObject.put("sex", member.getMember_sex());
                    jsonObject.put("birthPlace", member.getMember_birth_place());
                    jsonObject.put("identity", member.getMember_identity());
                    jsonObject.put("ethnicity", member.getMember_ethnicity());
                    jsonObject.put("phone", member.getMember_phone_number());
                    jsonObject.put("degree", member.getMember_degree());
                    jsonObject.put("type", member.getMember_type());
                    jsonObject.put("joinDate", member.getMember_join_date());
                    jsonObject.put("org", member.getMember_org());
                    jsonObject.put("fomalDate", member.getMember_fomal_date());
                    jsonObject.put("address", member.getMember_address());
                    jsonObject.put("marriage", member.getMember_marriage());
//                    jsonObject.put("position", member.getMember_party_position());
//                    jsonObject.put("title", member.getMember_major_title());
//                    jsonObject.put("class", member.getMember_new_class());
                    jsonObject.put("job", member.getMember_job());
                    jsonObject.put("birthday", member.getMember_birthday());
                    ja.add(jsonObject);
                }
                Map<String, String> headMap = new LinkedHashMap<String, String>();
                headMap.put("committee", "所在二级党组织");
                headMap.put("name", "姓名");
                headMap.put("sex", "性别");
                headMap.put("birthday", "出生日期");
                headMap.put("birthPlace", "籍贯");
                headMap.put("identity", "公民身份证号");
                headMap.put("ethnicity", "民族");
                headMap.put("phone", "电话");
                headMap.put("degree", "学历");
                headMap.put("type", "党员类型");
                headMap.put("joinDate", "入党时间");
                headMap.put("org", "所在支部");
                headMap.put("fomalDate", "转正时间");
                headMap.put("address", "家庭住址");
                headMap.put("marriage", "婚姻状况");
//                headMap.put("position", "党内职务");
//                headMap.put("title", "党费标准（元/月）");
//                headMap.put("class", "学生宿舍");
                headMap.put("job", "人员类型");
                headMap.put("orgDesc", "党组织类型");
                SXSSFWorkbook workbook = ExcelUtil.exportExcelX(title, headMap, ja, null, 0, res.getOutputStream());
                try {
                    workbook.write(res.getOutputStream());
                    workbook.close();
                    workbook.dispose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private List<Organization> generateTree(String orgId, List<Organization> all) {
        if (all == null || all.size() == 0) {
            return Collections.emptyList();
        } else {
            Organization cur = all.stream().filter(p -> p.getOrg_id().equalsIgnoreCase(orgId)).findFirst().get();
            List<Organization> ret = new ArrayList<>();
            ret.add(cur);
            List<Organization> children = all.stream().filter(p -> p.getOrg_parent().equalsIgnoreCase(orgId)).collect(Collectors.toList());
            if (children != null && children.size() > 0) {
                for (Organization organization : children) {
                    ret.addAll(generateTree(organization.getOrg_id(), all));
                }
            }
            return ret;
        }
    }
}
