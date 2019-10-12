package hg.party.server.organization;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.functors.IfClosure;
import org.osgi.service.component.annotations.Component;
import org.springframework.util.StringUtils;
import hg.party.dao.organization.AssignedPersonDao;
import hg.party.entity.organization.AssignedPerson;

@Component(immediate=true,service=AssignedPersonService.class)
public class AssignedPersonService {
	 // @Reference 
	 // private AssignedPersonDao assignedPersonDao;
	  AssignedPersonDao assignedPersonDao=new AssignedPersonDao();
	  /**  
	    * @param partyNmae   党委名称
	    * @param meetingType 会议类型
	    * @param partyBranch 党支部
	    * @param startDate   开展时间
	    * @return
	    */
      public List<AssignedPerson> findMeeting(String partyNmae,String meetingType,String partyBranch ,Date startDate){
    	 List<Map<String, Object>> meetings=assignedPersonDao.findMeeting(partyNmae,meetingType,partyBranch,startDate);
    	 List<AssignedPerson> meets=new ArrayList<>();
    	 if (meetings!=null&&meetings.size()>0) {
    	       for (Map<String, Object> map : meetings) {
    	    	   AssignedPerson a=new AssignedPerson();
    	    	   a.setMeetingType(map.get("meeting_type").toString());
    	    	   a.setMeetingTheme(map.get("meeting_theme").toString());
    	    	   a.setMeetingDate(map.get("start_date").toString());
    	    	   a.setComperePerson(map.get("compere_person").toString());
    	    	   a.setMeetingPlace(map.get("meeting_place").toString());
    	    	   a.setPersonsNumber(map.get("jion_persons").toString().split("，").length+"");
    	    	   a.setTelephone(map.get("link_person_telephone")+"");
    	    	   a.setLinkMan(map.get("link_person")+"");
    	    	   a.setId(Integer.parseInt(map.get("id").toString()));
    	    	   meets.add(a);
			}
    	       return meets;
    	 }
		   return null;  
       }
      public List<Map<String, Object>> findMeetingPlan(String branchId,String meetingType,String date){
    	  List<Map<String, Object>> list =assignedPersonDao.findMeetingPlan(branchId, meetingType, date);
    	  if (list!=null&&list.size()>0){
    		  for (Map<String, Object> map : list) {
				   String dateStr=date(map.get("start_time").toString(),"str");
				   map.put("start_time", dateStr);
			}
    	  }
    	  return list;
      }
      public List<Map<String, Object>> findSconedParty(){
    	  return assignedPersonDao.findSconedParty();
      }
    //根据输入人员获取组织名称
  	public List<Map<String, Object>> pid(String name){
		return assignedPersonDao.pid(name);
  	}
  //根据二级党组织名称查询支部
  	public List<Map<String, Object>> brinch(String userName,String orgName){
		return assignedPersonDao.brinch(userName,orgName);
  	}
  //二级党组织查询支部名称
  	public List<Map<String, Object>>brinchs(String userName,String orgId){
  		return assignedPersonDao.brinchs(userName,orgId);
  	}
      public List<Map<String, Object>> findPartyBranch(int pid){
    	  return assignedPersonDao.findPartyBranch(pid);
      }
      public List<Map<String, Object>> findPartyBranch(String pid){
    	  return assignedPersonDao.findPartyBranch(pid);
      }
      public List<Map<String, Object>> findDtail(String resourceId,String public_object){
    	  return assignedPersonDao.findDtail(resourceId, public_object);
      }
      public List<Map<String, Object>> findDtail(String resourceId){
    	  List<Map<String, Object>> list =assignedPersonDao.findDtail(resourceId);
    	  if (list!=null&&list.size()>0) {
			for (Map<String, Object> map : list) {
				if (!StringUtils.isEmpty(map.get("meeting_id"))) {
					List<Map<String, Object>> image=assignedPersonDao.findImages(map.get("meeting_id").toString());
					if (image!=null&&image.size()>0) {
						Object url=image.get(0).get("image");
						if(url!=null){
						map.put("url", url);
						continue;}
					}else{
						map.put("url", null);
						continue;
					}
				}else{
			            map.put("url", null);
					    continue;
				}
			}
		}
    	 // System.out.println(list);
    	 // return assignedPersonDao.findDtail(resourceId);
    	   return list;
      }
      public List<Map<String, Object>> findAssignPerson(){
    	  return assignedPersonDao.findAssignPerson();
      }
      /**
       * 获取指派人员 
       * @param id
       * @param orgType
       * @param orgId
       * @return
       */
      public List<Map<String, Object>> getAssignPerson(String id,String orgType,String orgId){
    	  
    	    List<Map<String, Object>> result= new ArrayList<>();
    	    result=assignedPersonDao.findAssignPersonAll();
    	    /*	List<Map<String, Object>> list= assignedPersonDao.findAssignPerson(0);
	    	List<Map<String, Object>> AlreadyAssign= assignedPersonDao.findAssignPerson(1);
	    	result.addAll(result);
	    	result.addAll(AlreadyAssign);
	    	System.out.println(list.size());
	    	System.out.println(AlreadyAssign.size());
    	  try {
    		  List<Map<String, Object>> time=assignedPersonDao.findMeetingStartTimeAndEndTimeById(id);
    		  //抽查时间
    		  Date sDate=date(time.get(0).get("start_time").toString());
    		  Date eDate=date(time.get(0).get("end_time").toString());
    		
    	    	  System.out.println(list.size());
    	    	  System.out.println(AlreadyAssign.size());
    	    	  if (list.size()>0) {
    				for (Map<String, Object> map : list) {
    					map.put("state", 1);
    				    String userId=map.get("assigne_user_id").toString();
    				    List<Map<String, Object>> groupInfo=assignedPersonDao.findGroupIdByUserId(userId);
    				    if(groupInfo==null || groupInfo.size()==0){
    				    	map.put("state", 0);
    				    	result.add(map);
    				    	continue;
    				    	}
    				    if (groupInfo.size()>0) {
    				    	Label:for (Map<String, Object> map2 : groupInfo) {
    							String groupId=map2.get("group_id").toString();
    							List<Map<String, Object>> planTime=assignedPersonDao.findMeetingStartTimeAndEndTimeByGrroupId(groupId);
    						    if (planTime==null||planTime.size()==0) {
    						    	map.put("state", 0);
    						    	result.add(map);
    								break Label;
    							}else if (planTime.size()>0) {
    								for (Map<String, Object> map3 : planTime) {
    									//会议的时间
    									Date startTime=date(map3.get("start_time").toString());
    									Date endTime=date(map3.get("end_time").toString());
    									if (sDate.getTime()>endTime.getTime()) {
    										  map.put("state", 0);
    										  result.add(map);
											  break Label;
										}else if (startTime.getTime()>eDate.getTime()) {
											  map.put("state", 0);
											  result.add(map);
											  break Label;
										}
    								}
    							}
    						}
    					}
    				}
    			}
    	    	  if (AlreadyAssign!=null&&AlreadyAssign.size()>.0){
    	    		  result.addAll(AlreadyAssign);
    	    		  System.out.println(result.size());
				}*/
    	    	  //按部门获取指派人员
	    	try{
//	    		System.out.println(result.size());
    	    	  List<Map<String, Object>> listReult=new ArrayList<>();
    	    	  if ("organization".equals(orgType)) {
    	    		return assignedPersonDao.findOrgAssignPerson(orgId);
    	    		 // return result;    
				  }else if("secondary".equals(orgType)){
					 AssigneService service=new AssigneService();
					 List<Map<String, Object>> orgids=service.findBranchByOrgParentId(orgId);
					 if (result!=null&&result.size()>0) {
						 for (Map<String, Object> map : result) {
							  String userId=map.get("assigne_user_id")+"";							
							  List<Map<String, Object>> person=assignedPersonDao.findDpartment(userId);
							  if (person!=null&&person.size()>0) {
								  String org_id=person.get(0).get("user_department_id")+"";						
								  if (orgids!=null&&orgids.size()>0) {
									 for (Map<String, Object> map2 : orgids) {
										  String orgid= map2.get("org_id")+"";
//										  System.out.println("orgid :"+orgid+ " org_id:"+org_id);
										  if (org_id.equals(orgid)||orgId.equals(orgid)) {
											  listReult.add(map);
											  break;
										}
									}
								}
							}  
						}
					}
//					 System.out.println("listReult "+listReult.size());
					 return listReult;
				  }else {
					  if (result!=null&&result.size()>0) {
						for (Map<String, Object> map : result) {
							 String userId=map.get("assigne_user_id")+"";
							 List<Map<String, Object>> person=assignedPersonDao.findDpartment(userId);
							 if (person!=null&&person.size()>0) {
								 String org_id=person.get(0).get("user_department_id")+"";
								 if (org_id.equals(orgId)) {
									  listReult.add(map);
									 continue;
								}
							}
						}
					}
					  return listReult;
				}
	           	    	  
		} catch (Exception e) {
			          e.printStackTrace();
		}   	  
    	              return null;
      }
      public List<Map<String, Object>> findAssignPerson(int state){
    	  return assignedPersonDao.findAssignPerson(state);
      }
      
      /**
       * 解析时间
       * @param args
       */
  	public static Date date(String datestr) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date=sdf.parse(datestr);
		//String datestring=sdf.format(date);
		return date;
	}
 	public static String date(String datestr,String type) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date;
		try {
			date = sdf.parse(datestr);
			String datestring=sdf.format(date);
			return datestring;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		    return null;
	}
 	
 	
// 	public static void main(String[] args) {
// 		AssignedPersonService ss=new AssignedPersonService();
// 		//System.out.println(ss.getAssignPerson("460","secondary","21e14649-e2bf-401f-b95e-c300c9103828"));
// 		System.out.println(ss.getAssignPerson("460","organization","ddddd"));
//	}
   
}
