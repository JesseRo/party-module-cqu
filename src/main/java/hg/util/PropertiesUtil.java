package hg.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static String resourceUrl = "";
	@SuppressWarnings("static-access")
	public PropertiesUtil(String resourceUrl){
		this.resourceUrl = resourceUrl;
	}

	@SuppressWarnings("static-access")
	public Properties getResourceProperties(){
		Properties pps = new Properties();
		try {
			pps.load(super.getClass().getResourceAsStream(this.resourceUrl));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pps;
	}
	
	
}
