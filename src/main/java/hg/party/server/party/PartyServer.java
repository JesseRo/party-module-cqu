package hg.party.server.party;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.party.PartyDao;
import hg.party.entity.partyMembers.PartyMembers;

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
@Component(immediate = true, service = PartyServer.class)
public class PartyServer {
	@Reference
	private PartyDao partyDao;
	public void saveOrUpdatePartyMembers(PartyMembers partyMembers){
		partyDao.saveOrUpdate(partyMembers);
	}
}
