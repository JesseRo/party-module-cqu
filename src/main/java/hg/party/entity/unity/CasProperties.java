package hg.party.entity.unity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 文件名称：cas<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： yjx<br>
 * 修改内容： <br>
 */
public class CasProperties {
		public Properties getResourceProperties() {
			Properties pps = new Properties();
			try {
				InputStream inStream = this.getClass().getResourceAsStream("/cas.properties");
				pps.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return pps;
		}
}
