package party.portlet.transport.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgAdminDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.organization.Organization;
import hg.party.entity.partyMembers.JsonResponse;
import hg.party.entity.partyMembers.Member;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.beans.BeanUtils;
import party.constants.PartyOrgAdminTypeEnum;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.dao.TransportDao;
import party.portlet.transport.entity.Retention;
import party.portlet.transport.entity.Transport;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.TransportApproval,
                "mvc.command.name=/transport/approval"
        },
        service = MVCResourceCommand.class
)
public class TransportApprovalCommand implements MVCResourceCommand {
    @Reference
    private TransportDao transportDao;
    @Reference
    private UserDao userDao;
    @Reference
    private MemberDao memberDao;
    @Reference
    private TransactionUtil transactionUtil;
    @Reference
    private RetentionDao retentionDao;
    @Reference
    private OrgAdminDao orgAdminDao;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type", "application/json");
        Gson gson = new Gson();
        String userId = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "userName");
        String department = (String) SessionManager.getAttribute(resourceRequest.getRequestedSessionId(), "department");

        String transportId = ParamUtil.getString(resourceRequest, "id");
        String type = ParamUtil.getString(resourceRequest, "type");
        int status = ParamUtil.getInteger(resourceRequest, "status");
        transactionUtil.startTransaction();
        try {
            if (type.equalsIgnoreCase("transport")) {
                // 转出审批
                Transport transport = transportDao.findById(transportId);
                if (status == ConstantsKey.APPROVED) {
                    // 通过
//
                    if (!transport.getCurrent_approve_org().equalsIgnoreCase(department)) {
                        throw new Exception();
                    }
                    List<String> toApproveList = gson.fromJson(transport.getTo_approve_list(), new TypeToken<List<String>>() {
                    }.getType());
                    List<String> approvedList = gson.fromJson(transport.getApproved_list(), new TypeToken<List<String>>() {
                    }.getType());
                    toApproveList.remove(department);
                    approvedList.add(department);
                    transport.setTo_approve_list(gson.toJson(toApproveList));
                    transport.setApproved_list(gson.toJson(approvedList));
                    if (toApproveList.size() == 0) {
                        // 最后一道审核
                        User user = userDao.findUserByEthnicity(transport.getUser_id());
                        Member member = memberDao.findByUserId(transport.getUser_id());
                        if ((transport.getType().equalsIgnoreCase("0")
                            || transport.getType().equalsIgnoreCase("1"))) {
                            // 校内
                            Member newMember = new Member();
                            BeanUtils.copyProperties(member, newMember, "id");
                            newMember.setMember_org(transport.getTo_org_id());
                            user.setUser_department_id(transport.getTo_org_id());
                            userDao.saveOrUpdate(user);
                            memberDao.insertAll(Collections.singletonList(newMember));
                            memberDao.historic(true, member);
                            if (transport.getType().equalsIgnoreCase("1")) {
                                orgAdminDao.deleteOrgAdmin(member.getMember_identity(), PartyOrgAdminTypeEnum.SECONDARY);
                            }
                            orgAdminDao.deleteOrgAdmin(member.getMember_identity(), PartyOrgAdminTypeEnum.BRANCH);
                        } else if (transport.getType().equalsIgnoreCase("2")) {
                            //市内
                            userDao.logicDelete(user.getUser_id());
                            memberDao.historic(member);
                        }
                        transport.setStatus(status);
                    } else {
                        transport.setCurrent_approve_org(toApproveList.get(0));
                    }
                } else if (status == ConstantsKey.CONFIRM) {
                    //市外
                    if (transport.getType().equalsIgnoreCase("2")
                            || transport.getType().equalsIgnoreCase("3")) {
                        User user = userDao.findUserByEthnicity(transport.getUser_id());
                        Member member = memberDao.findByUserId(transport.getUser_id());
                        userDao.logicDelete(user.getUser_id());
                        memberDao.historic(member);
                        transport.setStatus(status);
                    }
                } else {
                    transport.setStatus(status);
                    transport.setReject_org(department);
                }
                transport.setOperator(userId);
                transportDao.update(transport);
            } else {
                // 保留审核
                Retention retention = retentionDao.findById(transportId);
                retention.setStatus(status);
                retention.setOperator(userId);
                retentionDao.update(retention);
            }
            transactionUtil.commit();
            res.getWriter().write(gson.toJson(JsonResponse.Success()));
        } catch (Exception e) {
            transactionUtil.rollback();
            try {
                e.printStackTrace();
                res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }

}
