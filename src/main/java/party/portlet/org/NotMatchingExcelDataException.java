package party.portlet.org;

import org.springframework.util.StringUtils;

public class NotMatchingExcelDataException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1963049975327759089L;
    public NotMatchingExcelDataException(String message) {
        super(message);
    }
	
    public static NotMatchingExcelDataException create(String committee, String org, String name) {
    	StringBuilder sBuilder = new StringBuilder("数据异常！\\n");
    	if (!StringUtils.isEmpty(committee)) {
        	sBuilder.append("二级党委:").append(committee).append("\\n");
		}
    	if (!StringUtils.isEmpty(org)) {
        	sBuilder.append("党支部:").append(org).append("\\n");
		}
    	if (!StringUtils.isEmpty(name)) {
        	sBuilder.append("人员:").append(name).append("\\n");
		}
    	return new NotMatchingExcelDataException(sBuilder.toString());
    }
}