package hg.party.server.secondCommittee;

import java.util.UUID;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2018年1月6日下午1:40:06<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */
public class SecondCommitteeUtils {
	
	
	public static String createPlanId(){
		long now = System.nanoTime();
		return "PID"+now;
	}
	
	public static String createInformId(){
		long now = System.nanoTime();
		return "IID"+now;
	}
	
	public static String createMeetingNoteId(){
		long now = System.nanoTime();
		return "NID"+now;
	}
	
	public static String createPlaceeId(){
		UUID EID = UUID.randomUUID();
//		long now = System.nanoTime();
		return "E:"+EID.toString();
	}

}
