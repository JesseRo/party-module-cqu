package hg.party.server.organization;


import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.party.TreeNode;
import hg.util.ConstantsKey;
import hg.util.postgres.PostgresqlPageResult;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyOrgAdminTypeEnum;
import party.portlet.cqu.dao.StatisticsDao;
import party.portlet.transport.entity.PageQueryResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component(immediate = true, service = OrgService.class)
public class OrgService {
    @Reference
    private OrgDao orgDao;
    @Reference
    private StatisticsDao statisticsDao;

    public List<Organization> findChildren(String parentId) {
        return orgDao.findChildren(parentId);
    }

    public List<Organization> findAll() {
        return orgDao.findAll();
    }

    public Organization findOrgById(int id) {
        return orgDao.findOrgById(id);
    }

    public Organization findOrgByPID(int id, String orgName) {
        return orgDao.findOrgByPID(id, orgName);
    }

    public Organization findOrgByOrgId(String orgId) {
        return orgDao.findOrgByOrgId(orgId);
    }

    public List<Organization> findOrgByOrgType(PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
        return orgDao.findOrgByOrgType(partyOrgAdminTypeEnum);
    }

    public int createOrg(Organization organization) {
        return orgDao.createOrg(organization);
    }

    public int updateOrg(Organization organization) {
        return orgDao.updateDetail(organization);
    }

    public int deleteOrg(int id) {
        return orgDao.deleteOrg(id);
    }

    public List<TreeNode> getTreeData(Organization organization) {
        return orgDao.getTreeData(organization);
    }

    public List<Map<String, Object>> findAllUsers(Organization organization) {
        return orgDao.findAllUsers(organization);
    }

    /**
     * @param id 组织主键
     * @return
     */
    public List<Map<String, Object>> findOrgAdminUser(int id) {
        return orgDao.findOrgAdminUser(id);
    }

    /**
     * 查询用户管理的对应角色党组织
     *
     * @param userId
     * @param partyOrgAdminTypeEnum
     * @return
     */
    public Organization findAdminOrg(String userId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
        return orgDao.findAdminOrg(userId, partyOrgAdminTypeEnum);
    }

    /**
     * 通过组织查询对应组织的党员
     *
     * @param orgId
     * @return
     */
    public List<Map<String, Object>> findMembersByOrg(String orgId, PartyOrgAdminTypeEnum partyOrgAdminTypeEnum) {
        return orgDao.findMembersByOrg(orgId, partyOrgAdminTypeEnum);
    }

    /**
     * 统计党委子部门及党员数
     *
     * @param search
     * @return //
     */
    public PageQueryResult<Map<String, Object>> orgStatisticsPage(int page, int size, int id, String search) {
        PageQueryResult<Map<String, Object>> childrenStati = orgDao.orgChildrenStatistics(page, size, id, search);
        List<String> orgIds = childrenStati.getList().stream()
                .map(p -> (String) p.get("org_id")).filter(Objects::nonNull).collect(Collectors.toList());
        List<Map<String, Object>> memberStati = orgDao.orgMemberStatistics(search, orgIds);

        Map<String, Map<String, Object>> childrenStatiMap = childrenStati.getList().stream()
                .collect(Collectors.toMap(p -> (String) p.get("org_id"), p -> p));
        Map<String, List<Map<String, Object>>> memberStatiMap = memberStati.stream()
                .collect(Collectors.groupingBy(p -> (String) p.get("org_id")));

        for (String orgId : orgIds) {
            Map<String, Object> o = childrenStatiMap.get(orgId);
            if ("organization".equalsIgnoreCase((String) o.get("org_type"))) {
                List<Map<String, Object>> orgCounts = statisticsDao.countOrg();
                for (Map<String, Object> orgCount : orgCounts) {
                    if (orgCount.get("org_type").equals(ConstantsKey.ORG_TYPE_BRANCH)) {
                        o.put("c", orgCount.get("count"));
                        break;
                    }
                }
                orgCounts = statisticsDao.countOrgByDescType();
                int committeeCount = 0;
                int grandBranchCount = 0;
                for (Map<String, Object> orgCount : orgCounts) {
                    int type = (Integer) orgCount.get("desc_type");
                    long c = (Long) orgCount.getOrDefault("count", 0L);
                    if (type == 1 || type == 0) {
                        committeeCount += c;
                    } else {
                        grandBranchCount += c;
                    }
                }
                o.put("grand_branch", grandBranchCount);
                o.put("committee", committeeCount);
                orgCounts = statisticsDao.countMemberByType();
                for (Map<String, Object> m : orgCounts) {
                    if (Objects.equals(m.get("member_type"), "正式党员")) {
                        o.put("member_formal", m.getOrDefault("c", 0));
                    } else if (Objects.equals(m.get("member_type"), "预备党员")) {
                        o.put("member_pre", m.getOrDefault("c", 0));
                    }
                }
            } else {
                if ("secondary".equalsIgnoreCase((String) o.get("org_type"))) {
                    int descType = (Integer) o.get("desc_type");
                    if (descType == 1 || descType == 0) {
                        o.put("grand_branch", 0);
                        o.put("committee", 1);
                    } else {
                        o.put("grand_branch", 1);
                        o.put("committee", 0);
                    }
                } else {
                    o.put("grand_branch", 0);
                    o.put("committee", 0);
                    o.put("c", 1);
                }

                List<Map<String, Object>> lm = memberStatiMap.get(orgId);
                o.put("member_formal", 0);
                o.put("member_pre", 0);
                if (lm != null) {
                    for (Map<String, Object> m : lm) {
                        if (Objects.equals(m.get("member_type"), "正式党员")) {
                            o.put("member_formal", m.getOrDefault("c", 0));
                        } else if (Objects.equals(m.get("member_type"), "预备党员")) {
                            o.put("member_pre", m.getOrDefault("c", 0));
                        }
                    }
                }
            }
        }
        return childrenStati;
    }

    public PageQueryResult<Map<String, Object>> searchOrgUsersPage(int page, int size, int orgId,String adminType,String keyword) {
        return orgDao.searchOrgUsersPage(page,size,orgId,adminType,keyword);
    }
}
