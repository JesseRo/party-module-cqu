package hg.util;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

public class AssertionUtil {
	
	private static String SERVCENAME = "";
	private static String SERVERURL = "";
	private static String CASLOGINURL = "";
	static {
		PropertiesUtil propertiesUtil = new PropertiesUtil("/cas.properties");
		Properties properties = propertiesUtil.getResourceProperties();
		if (Validator.isNull(properties)) {
			try {
				throw new IllegalArgumentException("缺少cas相关配置，请检查是否存在cas.properties配置文件!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			SERVCENAME = properties.getProperty("serverName");
			SERVERURL = properties.getProperty("serverUrl");
			CASLOGINURL = properties.getProperty("loginUrl");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final Map<Long, TicketValidator> _ticketValidators = new ConcurrentHashMap();
	
	
	public static Assertion resAssertion(HttpServletRequest request, HttpServletResponse response, long companyId){
		Assertion assertion = null;
		try {
			HttpSession session = request.getSession();
			
			if(Validator.isNotNull(session.getAttribute("MY_CAS_ASSERTION"))){
				return (Assertion) session.getAttribute("MY_CAS_ASSERTION");
			}
			
			String serviceUrl = null;
			serviceUrl = CommonUtils.constructServiceUrl(request, response, serviceUrl, SERVCENAME, "ticket", false);
			String ticket = ParamUtil.getString(request, "ticket");
			
			if (Validator.isNotNull(ticket)){
				TicketValidator ticketValidator = getTicketValidator(companyId);
				assertion = ticketValidator.validate(ticket, serviceUrl);
				if (assertion != null){
					session.setAttribute("MY_CAS_ASSERTION", assertion);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    	return assertion;
	}
	public static String resCasLoginName(HttpServletRequest request, HttpServletResponse response, long companyId){
		String casLoginName = null;
		try {
			String serviceUrl = null;
			serviceUrl = CommonUtils.constructServiceUrl(request, response, serviceUrl, SERVCENAME, "ticket", false);
			String ticket = ParamUtil.getString(request, "ticket");
			
			if (Validator.isNotNull(ticket))
			{
				TicketValidator ticketValidator = getTicketValidator(companyId);
				Assertion assertion = ticketValidator.validate(ticket, serviceUrl);
				if (assertion != null)
				{
					AttributePrincipal attributePrincipal = assertion.getPrincipal();
					casLoginName = attributePrincipal.getName();
					Map<String, Object> attrs = attributePrincipal.getAttributes();
					if (attrs == null) {
						System.out.println("没有额外参数。。");
					}else{
						System.out.println("额外参数:" + attrs.size());
						for(Map.Entry<String, Object> entry : attributePrincipal.getAttributes().entrySet()){
							System.out.println("cas返回参数: " + entry.getKey() + "-" + entry.getValue());
						}
						if (attrs.containsKey("ACPIDCARD")){
							casLoginName = new String(Base64.getDecoder().decode((String)attrs.get("ACPIDCARD"))).toUpperCase().trim();
							
					
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return casLoginName;
	}

	protected static TicketValidator getTicketValidator(long companyId)throws Exception {
		    TicketValidator ticketValidator = _ticketValidators.get(companyId);
		    if (ticketValidator != null) {
		      return ticketValidator;
		    }
		    
		    Cas20ProxyTicketValidator cas20ProxyTicketValidator = new Cas20ProxyTicketValidator(SERVERURL);
		    
		    @SuppressWarnings({ "rawtypes", "unchecked" })
			Map<String, String> parameters = new HashMap();
		    
		    parameters.put("serverName", SERVCENAME);
		    parameters.put("casServerUrlPrefix", SERVERURL);
		    parameters.put("casServerLoginUrl", CASLOGINURL);
		    parameters.put("redirectAfterValidation", "false");
		    
		    cas20ProxyTicketValidator.setCustomParameters(parameters);
		    _ticketValidators.put(Long.valueOf(companyId), cas20ProxyTicketValidator);
		    
		    return cas20ProxyTicketValidator;
		  }
}
