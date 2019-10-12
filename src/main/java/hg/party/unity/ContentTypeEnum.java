package hg.party.unity;

import java.util.ArrayList;
import java.util.List;

public enum ContentTypeEnum {
	TEXT(1, "文本"),
	TEXT_IMG(2, "图文"),
	TEXT_VEDIO(3, "视频");
	
	private int id;
	private String mark;
	public int getId() { return id; }
	public String getMark() { return mark; }
	ContentTypeEnum(int id, String mark){
		this.id = id;
		this.mark = mark;
	}
	
	public static List<ContentTypeConvert> getConvertList(){
		List<ContentTypeConvert> contentTypes = new ArrayList<ContentTypeConvert>();
		ContentTypeConvert contentType = null;
		for(ContentTypeEnum enumObj : ContentTypeEnum.values()){
			contentType = new ContentTypeConvert();
			contentType.setId(enumObj.getId());
			contentType.setMark(enumObj.getMark());
			contentTypes.add(contentType);
		}
		return contentTypes;
	}
	
	public static String getMark(int id) {
        for(ContentTypeEnum objEnum : ContentTypeEnum.values()) {
            if (objEnum.getId() == id) {
                return objEnum.getMark();
            }
        }
        return null; 
    }
	
	public static int getId(String mark) {
		for(ContentTypeEnum objEnum : ContentTypeEnum.values()) {
			if(mark.equals(objEnum.getMark())) {
				return objEnum.getId();
			}
		}
		return 0; 
	}
}
