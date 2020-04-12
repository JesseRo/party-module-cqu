package party.memberEdit;

import com.dt.springjdbc.dao.impl.PostgresqlQueryResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import hg.party.dao.member.MemberEditDao;
import hg.party.entity.partyMembers.JsonPageResponse;
import hg.party.entity.partyMembers.JsonResponse;
import hg.util.ConstantsKey;
import hg.util.TransactionUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.util.StringUtils;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + PartyPortletKeys.MemberEditPortlet,
                "mvc.command.name=/hg/memberEdit/page"
        },
        service = MVCResourceCommand.class
)
public class MemberEditResourceCommand implements MVCResourceCommand {
    @Reference
    private MemberEditDao memberEditDao;
    @Reference
    TransactionUtil transactionUtil;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException {
        int page = ParamUtil.getInteger(resourceRequest, "page");
        int size = ParamUtil.getInteger(resourceRequest, "limit");
        String keyword = ParamUtil.getString(resourceRequest, "keyword");
        PostgresqlQueryResult<Map<String, Object>> data;
        if (StringUtils.isEmpty(keyword)){
            data = memberEditDao.findPage(page, size);
        }else {
            data = memberEditDao.searchPage(page, size, keyword);
        }

        HttpServletResponse res = PortalUtil.getHttpServletResponse(resourceResponse);
        res.addHeader("content-type","application/json");
        try {
            JsonPageResponse jsonPageResponse = new JsonPageResponse();
            if (data != null){
                data.getList().forEach(p->{
                    int status = (Integer) p.get("status");
                    if (status == ConstantsKey.INITIAL){
                        p.put("status", "待审批");
                    }else if (status == ConstantsKey.APPROVED){
                        p.put("status", "已审批");
                    }else if(status == ConstantsKey.REJECTED){
                        p.put("status", "已驳回");
                    }else {
                        p.put("status", null);
                    }
                });
                jsonPageResponse.setCode(0);
                jsonPageResponse.setCount(data.getTotalPage() * size);
                jsonPageResponse.setData(data.getList());
            }else {
                jsonPageResponse.setCode(0);
                jsonPageResponse.setCount(0);
                jsonPageResponse.setData(Collections.emptyList());
            }
            res.getWriter().write(gson.toJson(jsonPageResponse));
        } catch (Exception e) {
            try {
                e.printStackTrace();
                res.getWriter().write(gson.toJson(new JsonResponse(false, null, null)));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }
}
