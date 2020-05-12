package party.portlet.transport.command;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.unity.ExcelUtil;
import hg.util.ConstantsKey;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.dao.TransportDao;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static hg.util.ConstantsKey.STATUS_LIST;


@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.TransportApproval,
                "mvc.command.name=/transport/export"
        },
        service = MVCResourceCommand.class
)
public class ExportCommand implements MVCResourceCommand {
    @Reference
    private MemberDao memberDao;
    @Reference
    private OrgDao orgDao;
    @Reference
    private TransportDao transportDao;
    @Reference
    private RetentionDao retentionDao;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String orgId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");
        Organization organization = orgDao.findByOrgId(orgId);
        String name = ParamUtil.getString(resourceRequest, "memberName");
        String type = ParamUtil.getString(resourceRequest, "type");
        String action = ParamUtil.getString(resourceRequest, "action");
        String datePattern = "yyyy-MM-dd";
        List<String> types;
        if (!StringUtils.isEmpty(type)) {
            types = Arrays.stream(type.split(",")).collect(Collectors.toList());
        } else {
            types = null;
        }
        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        if (!StringUtils.isEmpty(name)) {
            name = "%" + name + "%";
        }
        if (action.equalsIgnoreCase("retention")) {
            List<Map<String, Object>> retentions;
            if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_SECONDARY)) {
                retentions = retentionDao.findSecondary(orgId, name);
            } else if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_ROOT)) {
                retentions = retentionDao.findRoot(name);
            } else if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_BRANCH)) {
                retentions = retentionDao.findBrunch(orgId, name);
            } else {
                retentions = null;
            }
            if (retentions != null) {
                for (Map<String, Object> map : retentions) {
                    Integer status = (Integer) map.get("status");
                    if (status != null && status >= 0 && status <= 5) {
                        map.put("status", STATUS_LIST[status]);
                    } else {
                        map.remove("status");
                    }
                }

                JSONArray jsonArray = new JSONArray(new ArrayList<>(retentions));
                SXSSFWorkbook workbook = ExcelUtil.exportExcelX("root", ConstantsKey.RETENTION_MAPPING, jsonArray, datePattern, 0, null);
                try {
                    String filename = "组织关系保留详情.xlsx";
                    res.setContentType("application/vnd.ms-excel;charset=utf-8");
                    res.addHeader("Content-Disposition",
                            "attachment; filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                    workbook.write(resourceResponse.getPortletOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (action.equalsIgnoreCase("transport")) {
            List<Map<String, Object>> transports;
            if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_SECONDARY)) {
                transports = transportDao.findSecondary(orgId, types, name);
            } else if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_ROOT)) {
                transports = transportDao.findRoot(types, name);
            } else if (organization.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_BRANCH)) {
                transports = transportDao.findBranch(orgId, types, name);
            } else {
                transports = null;
            }
            if (transports != null) {
                for (Map<String, Object> map : transports) {
                    Integer status = (Integer) map.get("status");
                    if (status != null && status >= 0 && status <= 5) {
                        map.put("status", STATUS_LIST[status]);
                    } else {
                        map.remove("status");
                    }
                }

                JSONArray jsonArray = new JSONArray(new ArrayList<>(transports));
                SXSSFWorkbook workbook = ExcelUtil.exportExcelX("root", ConstantsKey.TRANSPORT_MAPPING, jsonArray, datePattern, 0, null);
                try {
                    String filename = "组织关系转接详情.xlsx";
                    res.setContentType("application/vnd.ms-excel;charset=utf-8");
                    res.addHeader("Content-Disposition",
                            "attachment; filename=" + new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                    workbook.write(resourceResponse.getPortletOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

}
