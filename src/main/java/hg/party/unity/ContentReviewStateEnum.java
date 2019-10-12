package hg.party.unity;

import java.util.ArrayList;
import java.util.List;

public enum ContentReviewStateEnum {

	SECONDPARTYREVIEWING(0, "二级党组织审核中"),
	ORGANIZATIONREVIEWING(1, "组织部审核中"),
	SECONDPARTYREFUSED(2, "二级党组织驳回"),
	PASSED(3, "审核通过"),
	ORGANIZATIONREFUSED(4, "组织部驳回"),
	SECONDPARTYRECALL(5,"党支部已撤回"),
	ORGANIZATIONRECALL(6,"二级党组织已撤回");
	
	
	private int id;
	private String mark;
	public int getId() { return id; }
	public String getMark() { return mark; }
	ContentReviewStateEnum(int id, String mark){
		this.id = id;
		this.mark = mark;
	}
	
	public static List<ContentTypeConvert> getConvertList(){
		List<ContentTypeConvert> contentTypes = new ArrayList<ContentTypeConvert>();
		ContentTypeConvert contentType = null;
		for(ContentReviewStateEnum enumObj : ContentReviewStateEnum.values()){
			contentType = new ContentTypeConvert();
			contentType.setId(enumObj.getId());
			contentType.setMark(enumObj.getMark());
			contentTypes.add(contentType);
		}
		return contentTypes;
	}
	
	public static String getMark(int id) {
        for(ContentReviewStateEnum objEnum : ContentReviewStateEnum.values()) {
            if (objEnum.getId() == id) {
                return objEnum.getMark();
            }
        }
        return null; 
    }
	
	public static int getId(String mark) {
		for(ContentReviewStateEnum objEnum : ContentReviewStateEnum.values()) {
			if(mark.equals(objEnum.getMark())) {
				return objEnum.getId();
			}
		}
		return 0; 
	}
}


