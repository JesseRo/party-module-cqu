package party.memberEdit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.member.MemberEditDao;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.MemberEditPortlet,
                "mvc.command.name=/hg/memberEdit/approval"
        },
        service = MVCResourceCommand.class
)
public class MemberEditApprovalResourceCommand implements MVCResourceCommand {
    @Reference
    private MemberEditDao memberEditDao;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        String memberEditId = ParamUtil.getString(resourceRequest, "id");
        int status = ParamUtil.getInteger(resourceRequest, "status");
        MemberEdit memberEdit = memberEditDao.findById(memberEditId);
        memberEdit.setStatus(status);
        if (status == ConstantsKey.REJECTED){
            String reason = ParamUtil.getString(resourceRequest, "reason");
            memberEdit.setReason(reason);
        }
        memberEditDao.saveOrUpdate(memberEdit);

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);

        try {
            res.getWriter().write("{\"success\": true}");
            res.setHeader("content-type", "application/json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
