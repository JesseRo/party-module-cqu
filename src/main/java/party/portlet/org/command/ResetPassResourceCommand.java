package party.portlet.org.command;

import com.google.gson.Gson;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.login.UserDao;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.MD5;
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
                "javax.portlet.name=" + PartyPortletKeys.Org,
                "mvc.command.name=/org/resetPass/user"
        },
        service = MVCResourceCommand.class
)
public class ResetPassResourceCommand implements MVCResourceCommand {
    private Gson gson = new Gson();
    @Reference
    private TransactionUtil transactionUtil;
    @Reference
    private UserDao userDao;

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws PortletException {
        String userId = ParamUtil.getString(resourceRequest, "userId");
        if (userId.length() > 6) {
            String pass = userId.substring(userId.length() - 6);
            userDao.updatePass(userId, MD5.getMD5(pass));
            try {
                HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
                res.addHeader("content-type","application/json");
                res.getWriter().write(gson.toJson(JsonResponse.Success()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
