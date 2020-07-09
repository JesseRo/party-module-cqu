package hg.party.server.party;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import hg.party.entity.party.UserStatistics;
import hg.party.entity.party.BaseStatistics;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import hg.party.dao.party.PartyOrgDao;
import hg.party.entity.organization.Organization;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Yu Jiang Xia<br>
 */
@Component(immediate = true, service = PartyOrgServer.class)
public class PartyOrgServer {
	@Reference
	private PartyOrgDao partyOrgDao;
	
	//通过二级党委查询书记
	public List<Organization> findByOrgId(String org_id){
		return partyOrgDao.findByOrgId(org_id);
	}
	//报表学院参会情况
	public List<Map<String, Object>> partyEcharts(){
		return partyOrgDao.partyEcharts();
	}
	//报表会议类型分布
	public List<Map<String, Object>> partyEchartsType(){
		return partyOrgDao.partyEchartsType();
	}
	//查询二级党组织个数
	public List<Map<String, Object>> orgNumber(){
		return partyOrgDao.orgNumber();
	}
	//查询党支部个数
	public List<Map<String, Object>> branchNumber(){
		return partyOrgDao.branchNumber();
	}
	//查询二级党组织下党支部个数
	public int branchNumber(String orgId){
		return partyOrgDao.branchNumber(orgId);
	}
	//查询党员人数
	public List<Map<String, Object>> userNumber(){
		return partyOrgDao.userNumber();
	}
	//查询党员人数统计
	public UserStatistics userStatistics(){
		return partyOrgDao.userStatistics();
	}
	//根据组织查询党员人数统计
	public UserStatistics userSecondaryStatistics(String orgId){
		return partyOrgDao.userSecondaryStatistics(orgId);
	}
	//根据支部统计
	public UserStatistics userBranchStatistics(String orgId){
		return partyOrgDao.userBranchStatistics(orgId);
	}
	//查询组织活动个数
	public List<Map<String, Object>> mettingNumber(){
		return partyOrgDao.mettingNumber();
	}
	//二级详细页面分页、
	public Map<String, Object> postGresqlFind(int pageNo, int pageSize,String sql,String keyName){
		return partyOrgDao.postGresqlFind(pageNo, pageSize, sql,keyName);
	}
	public Map<String, Object> postGresqlFind(int pageNo, int pageSize,String sql){
		return partyOrgDao.postGresqlFind(pageNo, pageSize, sql);
	}
	//日访问量查询
	public Map<String, Object> dateVisit(){
		return partyOrgDao.dateVisit();
	}
	//访问量报表
	public List<Map<String, Object>> echartsVisit(){
		return partyOrgDao.echartsVisit();
	}
	//学院出勤率报表
//	public List<EchartObj> attenEcharts(){
//		List<EchartObj> objs = new ArrayList<EchartObj>();
//		EchartObj echartobj = new EchartObj();
//		String att = "";
//		double value = 0.0;
//		List<Map<String, Object>> atten = partyOrgDao.attenEcharts();
//		for(int i=0; i<atten.size(); i++){
//			att = String.valueOf(atten.get(i).get("attendance"));
//			att = att.replaceAll("%", "");
//			value = Double.valueOf(att.toString());
//			value = value/100;
//			echartobj.setKeyname(String.valueOf(atten.get(i).get("member_party_committee")));
//			echartobj.setValname(value);
//			objs.add(echartobj);
//		}
//		//分组
//		Map<String, List<EchartObj>> group = null;
//		double atte = 0.0;
//		double a = 0.0;
//		List<EchartObj> obje = new ArrayList<>();
//		EchartObj echartObj3 = new EchartObj();
//		group = objs.stream().collect(Collectors.groupingBy(p->p.getKeyname()));
//		for(String k : group.keySet()){
//			atte = 0.0;
//		   List<EchartObj> valuee = group.get(k);
//		   for (EchartObj echartObj2 : valuee) {
//			   atte += echartObj2.getValname();
//		   }
//		   a = atte/(valuee.size());
//		   echartObj3.setKeyname(k);
//		   echartObj3.setValname(a);
//		   obje.add(echartObj3);
//		}
//		
//		return obje;
//	}
	//学院出勤率报表2
	public List<Map<String, Object>> attenEchartss(){
		return partyOrgDao.attenEcharts();
	}

	//二级党组织开展活动年月统计
	public List<BaseStatistics> activitiesStatistics(int year, int month){
		return partyOrgDao.activitiesStatistics(year,month);
	}
	public List<BaseStatistics> activitiesStatistics(int year, int month,String orgId){
		return partyOrgDao.activitiesStatistics(year,month,orgId);
	}
	//开展活动起止日期统计
	public List<BaseStatistics> searchActivitiesStatistics(Timestamp startTime, Timestamp  endTime){
		return partyOrgDao.searchActivitiesStatistics(startTime,endTime);
	}
	public List<BaseStatistics> searchActivitiesStatistics(Timestamp startTime, Timestamp  endTime, String orgId){
		return partyOrgDao.searchActivitiesStatistics(startTime,endTime,orgId);
	}
	//党活动分类年月统计
	public List<BaseStatistics> activitiesTypeStatistic(int year, int month){
		return partyOrgDao.activitiesTypeStatistic(year,month);
	}
	public List<BaseStatistics> activitiesTypeStatistic(int year, int month,String orgId){
		return partyOrgDao.activitiesTypeStatistic(year,month,orgId);
	}
	//党活动分类起止日期统计
	public List<BaseStatistics> searchActivitiesTypeStatistics(Timestamp startTime, Timestamp  endTime){
		return partyOrgDao.searchActivitiesTypeStatistics(startTime,endTime);
	}
	//支部组织生活次数
	public int activitiesStatisticsCount(String orgId) {
		return partyOrgDao.activitiesStatisticsCount(orgId);
	}
}
