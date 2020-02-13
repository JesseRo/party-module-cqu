package party.portlet.transport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.MemberDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.party.entity.partyMembers.Member;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=组织关系保留申请",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/transport/retention.jsp",
                "javax.portlet.name=" + PartyPortletKeys.RelationRetentionPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class RetentionApplyPortlet extends MVCPortlet {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Reference
    private OrgDao orgDao;
    @Reference
    private UserDao userDao;
    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String userId = (String)SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "userName");
        List<Map<String, Object>> list = orgDao.findPersonByuserId(userId);
        if (list!=null&&list.size()>0) {
            User user = userDao.findUserByEthnicity(userId);
            list.get(0).put("email", user.getUser_mailbox());
            renderRequest.setAttribute("member", list.get(0));
        }
        super.doView(renderRequest, renderResponse);
    }


}
