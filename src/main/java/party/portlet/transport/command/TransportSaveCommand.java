package party.portlet.transport.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.TransportDao;
import party.portlet.transport.entity.Transport;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.TransportApplyPortlet,
                "mvc.command.name=/transport/save"
        },
        service = MVCResourceCommand.class
)
public class TransportSaveCommand implements MVCResourceCommand {
    @Reference
    private MemberDao memberDao;
    @Reference
    private OrgDao orgDao;
    @Reference
    private TransportDao transportDao;
    Gson gson = new Gson();
    @Reference
    TransactionUtil transactionUtil;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String sessionId = resourceRequest.getRequestedSessionId();
        String orgId = (String) SessionManager.getAttribute(sessionId, "orgId");
        String userId = (String) SessionManager.getAttribute(sessionId, "userName");
        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type", "application/json");
        Transport transport = new Transport();
        Member member = memberDao.findByUserId(userId);
        Organization organization = orgDao.findByOrgId(orgId);

        transport.setTransport_id(UUID.randomUUID().toString());
        transport.setBirthday(member.getMember_birthday());
        transport.setDegree(member.getMember_degree());
        transport.setEthnicity(member.getMember_ethnicity());
        transport.setExtra("");
        transport.setIdentity(userId);
        transport.setJoin_date(member.getMember_join_date());
        transport.setMember_type(member.getMember_type());
        transport.setOrg_id(orgId);
        transport.setOrg_name(organization.getOrg_name());
        transport.setPhone_number(member.getMember_phone_number());
        transport.setUser_id(userId);
        transport.setUser_name(member.getMember_name());
        transport.setSex(member.getMember_sex());
        transport.setTime(new Timestamp(System.currentTimeMillis()));
        transactionUtil.startTransaction();
        try {
            String type = ParamUtil.getString(resourceRequest, "type");
            String org = ParamUtil.getString(resourceRequest, "org");
            if (type.equalsIgnoreCase("0") || type.equalsIgnoreCase("1")) {
                Organization toOrg = orgDao.findById(Long.valueOf(org));
                int count = orgDao.findCount(toOrg.getOrg_id());
                if (!toOrg.getOrg_type().equalsIgnoreCase(ConstantsKey.ORG_TYPE_BRANCH) && count > 0) {
                    throw new Exception();
                }
                List<String> toApproveList = new ArrayList<>();
                toApproveList.add(orgId);
                if (type.equalsIgnoreCase("1")) {
                    // 校内
                    if (organization.getOrg_type().equals(ConstantsKey.ORG_TYPE_BRANCH)) {
                        toApproveList.add(organization.getOrg_parent());
                    }
                    if (count > 0) {
                        toApproveList.add(toOrg.getOrg_parent());
                    }
                }
                toApproveList.add(toOrg.getOrg_id());
                transport.setTo_approve_list(gson.toJson(toApproveList));
                transport.setTo_org_id(toOrg.getOrg_id());
                transport.setTo_org_name(toOrg.getOrg_name());
            } else {
                transport.setTo_org_name(org);
                List<String> toApproveList = new ArrayList<>();
                toApproveList.add(orgId);
                toApproveList.add(organization.getOrg_parent());
                transport.setTo_approve_list(gson.toJson(toApproveList));
            }
            transport.setApproved_list("[]");
            transport.setCurrent_approve_org(orgId);

            String form = ParamUtil.getString(resourceRequest, "form");
            String title = ParamUtil.getString(resourceRequest, "title");
            String reason = ParamUtil.getString(resourceRequest, "reason");
            int isResubmit = ParamUtil.getInteger(resourceRequest, "isResubmit");

            transport.setType(type);
            transport.setTo_org_title(title);
            transport.setReason(reason);
            transport.setForm(form);


            if (isResubmit == 1) {
                Transport reTransport = transportDao.findByUser(userId);
                reTransport.setStatus(ConstantsKey.RESUBMIT);
                transportDao.saveOrUpdate(reTransport);
            } else {
                Long already = transportDao.countByUser(userId);
                if (already > 0) {
                    throw new Exception();
                }
            }
            transportDao.save(transport);
            transactionUtil.commit();
            res.getWriter().write(gson.toJson(JsonResponse.Success()));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            transactionUtil.rollback();
        }

        return false;
    }

}
