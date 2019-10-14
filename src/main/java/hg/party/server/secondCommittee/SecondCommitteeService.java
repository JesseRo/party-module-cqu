package hg.party.server.secondCommittee;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.liferay.portal.kernel.util.Time;

import hg.party.dao.secondCommittee.MeetingNotesDao;
import hg.party.dao.secondCommittee.MeetingPlanDao;
import hg.party.dao.secondCommittee.OrgInformDao;
import hg.party.dao.secondCommittee.OtherDao;
import hg.party.entity.party.MeetingNote;
import hg.party.entity.party.MeetingPlan;
import hg.party.entity.party.OrgInform;


/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： Mingxiang Duan<br>
 * 创建日期： 2017年12月26日下午5:07:28<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */

@Component(immediate = true,service = SecondCommitteeService.class)
public class SecondCommitteeService {
	@Reference
	OrgInformDao orgInformDao;
	@Reference
	MeetingPlanDao meetingPlanDao;
	@Reference
	MeetingNotesDao meetingNotesDao;
	@Reference
	OtherDao otherDao;
	
	Logger logger = Logger.getLogger(SecondCommitteeService.class);
	
	//查询下拉属性值
	   public  List<Map<String, Object>> queryAllMeetingTypes(String resources_type){
		   return otherDao.queryAttributesByType(resources_type);
	   }
	   
	   public  List<Map<String, Object>> queryAllTaskStatus(int maxCode){
		   return otherDao.queryTaskStatus(maxCode);
	   }
	
	//二级党委查询所有新通知
	public List<OrgInform> queryInforms(){
		   return orgInformDao.queryInforms();
	   }
	
	 //二级党委查询根据当前通知
	   public List<OrgInform> queryInformsByUser(String user){
		   return orgInformDao.queryInformsByUser(user);
	   }
	   
//	   //二级党委查询根据当前通知
//	   public List<OrgInform> queryInformsByOrgId(String orgId){
//		   return orgInformDao.queryInformsByOrgId(orgId);
//	   }
	   
//	   //二级党委根据informId查询通知
//	   public List<Map<String, Object>> queryInformsByOrgId(String OrgId){
//		   return orgInformDao.queryInformsByOrgId(OrgId);
//	   }
	
	 //二级党委查询没有提交计划的通知
	   public List<OrgInform> queryInformsNoPlan(){
		   return orgInformDao.queryInformsNoPlan();
	   }
	
	
	   //二级党委查询所有计划
	   public List<MeetingPlan> queryAllMeetings(){
		   return meetingPlanDao.queryMeetingList();
	   }
	   
//	   // //二级党委根据当前用户查询所有计划
//	   public List<MeetingPlan> queryMeetingsByUser(String user){
//		   return meetingPlanDao.queryMeetingsByUser(user);
//	   }
	   
//	   //二级党委根据组织ID查询所有计划
//	   public List<MeetingPlan> queryMeetingsByOrgId(String orgId){
//		   return meetingPlanDao.queryMeetingsByOrgId(orgId);
//	   }
	   
	   
	   //二级党委根据组织ID查询所有通知计划
	   public Map<String, Object> queryInformMeetingsByOrgId(String orgId, String meetingType, String taskStatus, int page){
		   return meetingPlanDao.queryInformMeetingsByOrgId(orgId, meetingType, taskStatus, page);
	   }
	   
	   public List<Map<String, Object>> exportExcel(String orgId, String meetingType, String taskStatus){
		   return meetingPlanDao.exportExcel(orgId, meetingType, taskStatus);
	   }
	
	 //二级党委根据informId查询任务
	   public OrgInform queryInformByInformId(String informId){
		   return orgInformDao.queryInformByInformId(informId);
	   }
	   
	   //二级党委根据informId查询通知附件
	   public List<Map<String, Object>> queryInformAttachmentByInformId(String informId){
		   return orgInformDao.queryInformAttachmentByInformId(informId);
	   }
	   
	   
	 //二级党委根据informId查询通知信息与附件
	   public List<Map<String, Object>> queryInformAttachment(String informId){
		   return orgInformDao.queryInformAttachment(informId);
	   }
	   
	   
	   //二级党委根据informId查询通知
	   public List<Map<String, Object>> queryInformsByInformOrgId(String orgId){
		   return orgInformDao.queryInformsByInformOrgId(orgId);
	   }
	   
	   
//	 //二级党委根据informId查询通知信息
//	   public List<Map<String, Object>> queryInformByInformInformId(String informId){
//		   return orgInformDao.queryInformsByInformOrgId(orgId);
//	   }
	   
	 //二级党委根据informId查询通知信息
	   public List<Map<String, Object>> queryInformByInformorgId(String informId,String orgId){
		   return orgInformDao.queryInformByInformorgId(informId, orgId);
	   }
	   
	 //二级党委根据informId查询任务
	   public void confirmInform(String informId){
		   OrgInform OrgInform = orgInformDao.queryInformByInformId(informId);
		   OrgInform.setInform_status("已查看");
		   orgInformDao.saveOrUpdate(OrgInform);
	   }
	   
	   //二级党委根据informId修改状态
	   public void updateInformStatus(String informId,String orgId,String nextStatus){
		   orgInformDao.updateInformStatus(informId, orgId, nextStatus);
	   }
	   
	   //二级党委根据resourceid查询任务
	   public List<OrgInform> queryInformsByTypeStatus(String type,String status){
		   return orgInformDao.queryInformsByTypeStatus(type, status);
	   }
	   
	   //二级党委根据resourceid查询任务
	   public List<OrgInform> queryInformsByType(String type){
		   return orgInformDao.queryInformsByType(type);
	   }
	   
	   //二级党委根据resourceid查询任务
	   public List<OrgInform> queryInformsByStatus(String status){
		   return orgInformDao.queryInformsByStatus(status);
	   }
	   
	   
	   //二级党委根据meetingId查询会议
	   public MeetingPlan queryMeetingByMeetingId(String meetingId){
		   return meetingPlanDao.queryMeetingByMeetingId(meetingId);
	   }
	   
	   //二级党委根据meetingId查询会议附件
	   public List<Map<String, Object>> queryMeetingAttachment(String meetingId){
		   return meetingPlanDao.queryMeetingAttachment(meetingId);
	   }
	   
	   //二级党委根据meetingId查询会议
	   public List<Map<String, Object>> queryMeetingPlanByMeetingId(String meetingId){
		   return meetingPlanDao.queryMeetingPlanByMeetingId(meetingId);
	   }
	   
	   
	   //党支部根据meetingId查询会议
	   public List<Map<String, Object>> queryMeetingPlanByMeetingIdBranch(String meetingId){
		   return meetingPlanDao.queryMeetingPlanByMeetingIdBranch(meetingId);
	   }
	   
	   //党支部根据meetingId查询应到人数
	   public List<Map<String, Object>> queryShouldCount(String meetingId){
		   return meetingPlanDao.queryShouldCount(meetingId);
	   }
	   
	   //二级党委根据meetingId上传会议记录
	   public void saveMeetingNote(MeetingNote meetingNote){
		   meetingNotesDao.saveOrUpdate(meetingNote);
	   }
	   
	   //根据meetingId查询会议记录
	   public MeetingNote queryMeetingNoteByMeetingId(String meetingId){
		   return meetingNotesDao.queryMeetingNoteByMeetingId(meetingId);
	   }
	   
	   
	   //查询会议地点
	   public List<Map<String, Object>> queryAllPlace(){
		   return otherDao.queryAllPlace();
	   }
	   
	 //查询会议地址
	   public Map<String,Object> queryPlacesByPage(int pageno,String orgid){
		   return otherDao.queryPlacesByPage(pageno,orgid);
	   }
	   
	   
	   
	   
	   //删除地址
	   public void delectePlaceById(String placeId){
//		   logger.info("placeId1 :" + placeId);
		   if (placeId.contains(",")) {
			   placeId = placeId.substring(0, placeId.lastIndexOf(","));
			   logger.info("placeId2 :" + placeId);
		}
		   String[] places = placeId.split(",");
//		   logger.info("place :" + places);
		   for(int i=0;i<places.length;i++){
			   logger.info("delete place :" + places[i]);
			   otherDao.delectePlaceById(places[i]);
		   }
		   
		   
		   
	   }
	   
	   
	 //保存地址
	   public void savePlace(String place,String placeId,Timestamp now,String orgid){
		   otherDao.savePlace(place, placeId,now,orgid);
	   }
	   
	   
	   //通知人员
	   public void informParty(String meetingId,String participant_id){
		   meetingPlanDao.informParty(meetingId, participant_id);
	   }
	   
	   //查询人员
	   public  List<Map<String, Object>> queryPartys(String orgId){
		   return meetingPlanDao.queryPartys(orgId);
	   }
	   
	   //更新会议状态
	   public  void updateMeetingPlan(String meetingId,String nextStatus){
		   meetingPlanDao.updateMeetingPlan(meetingId, nextStatus);
	   }
	   
	   //更新会议检查状态
	   public  void updateMeetingPlanCheckStarus(String meetingId,String nextStatus){
		   meetingPlanDao.updateMeetingPlanCheckStarus(meetingId, nextStatus);
	   }
	   
//		 //根据当前用户，会议ID查询会议记录
//	   public  Object querySecondCommitteeByUser(String user,String meetingId){
//		   return meetingNotesDao.querySecondCommitteeByUser(user, meetingId);
//	   }
	   
	   //根据用户名查询二级党委名称
	   public List<Map<String, Object>> queryOrgByUser(String user){
		   return otherDao.queryOrgByUser(user);
	   }
	   
	   //二级党委撤回会议
	   public  void revokeMeetingPlan(String meetingId,String revokeReason){
		   meetingPlanDao.revokeMeetingPlan(meetingId,revokeReason);
	   }
	   
	   
	 //根据组织id 查询组织类型
	   public String queryOrgTypeByOrgId(String orgId) {
		   return (String) otherDao.queryOrgById(orgId).get(0).get("org_type");
	   }
	   
	   
	   //报送计划查询地点使用情况
	   public List<Map<String, Object>> queryPlaceUseStatus(String place,Timestamp start,Timestamp end){
		   return otherDao.queryPlaceUseStatus(place, start, end);
	   }
	   
	   
	   //根据地点查询有误记录
	   public boolean isPlaceExist(String place,String orgId) { 
		   List<Map<String, Object>> placeList = otherDao.queryPlaceByPlace(place,orgId);
		   logger.info("placeList :" + placeList.toString());
		   if(placeList.size() == 0){
			   logger.info(place + " does not exist ...");
			   return false;
		   }else{
			   logger.info(place + " exist ...");
			   return true;
		   }
	   }


	public Map<String, Object> queryInformMeetingsByInformId(String orgId, String meetingType, String taskStatus, String informId, int pageNo) {
	   	return meetingPlanDao.queryInformMeetingsByInformId(orgId, meetingType, taskStatus, informId, pageNo);
	}
}
