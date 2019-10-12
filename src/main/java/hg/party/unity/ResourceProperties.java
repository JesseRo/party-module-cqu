package hg.party.unity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 文件名称： bbzf_2q<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： zhang minggang<br>
 * 创建日期： 2017年11月29日下午2:24:03<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
public class ResourceProperties {
		public Properties getResourceProperties() {
			Properties pps = new Properties();
			try {
				InputStream inStream = this.getClass().getResourceAsStream("/config.properties");
				pps.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return pps;
		}
}
