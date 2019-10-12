package hg.party.server.party;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.party.ValueAttributeDao;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月15日下午2:07:05<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
@Component(immediate = true, service = ValueAttributeService.class)
public class ValueAttributeService {
	@Reference
	private ValueAttributeDao valueAttributeDao;
	
	public List<String> findValues(){
		return valueAttributeDao.findValues();
	}
	
	public String value(String key){
		List<String> list = new ArrayList<>();
		String value = "";
		try {
			list = valueAttributeDao.value(key);
			if(list.size()>0){
				value = list.get(0);
			}
		} catch (Exception e) {
		}
		return value;
	}
	
}
