package party.portlet.transport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.PortalUtil;
import dt.session.SessionManager;
import hg.party.dao.login.UserDao;
import hg.party.dao.org.OrgDao;
import hg.party.entity.login.User;
import hg.util.ConstantsKey;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import party.constants.PartyPortletKeys;
import party.portlet.transport.dao.RetentionDao;
import party.portlet.transport.dao.TransportDao;
import party.portlet.transport.entity.Retention;
import party.portlet.transport.entity.Transport;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author jesse
 * @Filename TransportDetailPortlet
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/3/8</li>
 */

@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=category.sample",
                "com.liferay.portlet.instanceable=true",
                "javax.portlet.display-name=组织关系保留-查看详情",
                "javax.portlet.init-param.template-path=/",
                "com.liferay.portlet.requires-namespaced-parameters=false",
                "javax.portlet.init-param.view-template=/jsp/transport/retentionDetail.jsp",
                "javax.portlet.name=" + PartyPortletKeys.RetentionDetailPortlet,
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
        },
        service = Portlet.class
)
public class RetentionDetailPortlet extends MVCPortlet {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    @Reference
    private OrgDao orgDao;
    @Reference
    private UserDao userDao;
    @Reference
    private RetentionDao retentionDao;

    @Override
    public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
        String userId = (String)SessionManager.getAttribute(renderRequest.getRequestedSessionId(), "userName");

        List<Map<String, Object>> list = orgDao.findPersonByUserId(userId);
        if (list!=null&&list.size()>0) {
            User user = userDao.findUserByEthnicity(userId);
            list.get(0).put("email", user.getUser_mailbox());
            renderRequest.setAttribute("member", list.get(0));
        }
        String retentionId = PortalUtil.getOriginalServletRequest(PortalUtil.getHttpServletRequest(renderRequest)).getParameter("id");
        Retention retention = retentionDao.findById(retentionId);

        renderRequest.setAttribute("retentionJson", gson.toJson(retention));
        renderRequest.setAttribute("status", ConstantsKey.STATUS_LIST[retention.getStatus()]);
        super.doView(renderRequest, renderResponse);
    }
}