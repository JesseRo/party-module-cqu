package party.filter;

import org.osgi.service.component.annotations.Component;
import party.constants.PartyPortletKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import java.io.IOException;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=",
        },
        service = PortletFilter.class
)
public class CachedSessionFIlter implements RenderFilter {
    int count;
    @Override
    public void doFilter(RenderRequest renderRequest, RenderResponse renderResponse, FilterChain filterChain) throws IOException, PortletException {

        System.out.println("===============");
        System.out.println("显示数: " +count);
        System.out.println("===============");
        System.out.println("Before filter");
        filterChain.doFilter(renderRequest, renderResponse);
        System.out.println("After filter");
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws PortletException {
    }

}