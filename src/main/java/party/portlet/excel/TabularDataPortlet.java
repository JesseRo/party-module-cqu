package party.portlet.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import hg.party.entity.partyMembers.PartyMembers;
import hg.party.server.party.PartyServer;
import party.constants.PartyPortletKeys;

/**
 * 文件名称： party<br>
 * 内容摘要： @TODO<br>
 * 创建人 　： XiongZG<br>
 * 创建日期： 2017年12月12日上午9:24:15<br>
 * 版本号　 ： v1.0.0<br>
 * 公司　　 : <br>
 * 修改记录1 <br>
 * 修改日期：<br>
 * 版本号 　：<br>
 * 修改人 　：<br>
 * 修改内容： <br>
 */

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=获取Excel表格信息",
		"javax.portlet.init-param.template-path=/",	
		
		"com.liferay.portlet.requires-namespaced-parameters=false",
		"javax.portlet.portlet-mode=text/html;view,edit",
		"javax.portlet.init-param.view-template=/jsp/excel/tableData/view.jsp",
//		"javax.portlet.init-param.edit-template=/jsp/tableData/edit.jsp",
		
		"javax.portlet.name=" + PartyPortletKeys.TabularData,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class TabularDataPortlet extends MVCPortlet {
    private Workbook wb;  
    private Sheet sheet;  
    private Row row;  
    @Reference
	private PartyServer partyServer;
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		try {  
            String filepath = "D://表头信息.xlsx";  
            String ext = filepath.substring(filepath.lastIndexOf(".")); 
            InputStream is = new FileInputStream(filepath); 
            if(".xls".equals(ext)){  
                wb = new HSSFWorkbook(is); 
            }else if(".xlsx".equals(ext)){  
                wb = new XSSFWorkbook(is);  
            }else{  
                wb=null;  
            }  
            // 对读取Excel表格标题测试  
            String[] title = readExcelTitle();  
            // 对读取Excel表格内容测试  
            Map<Integer, Map<Object,Object>> map = readExcelContent(); 
            //把表标题设置为每条数据对应的key值
            Map<Object, Object> map1=new HashMap<Object,Object>();
            List<Map<Object, Object>> list=new ArrayList<Map<Object, Object>>();
            for(int i=1;i<=map.size();i++){
            	map1=map.get(i);
            	for(int j=0;j<map1.size();j++){
            		map1.put(title[j], map1.get(j));
            		map1.remove(j);
            	}
            	list.add(map1);
            }
            System.out.println("list="+list);
            for (int i = 0; i < list.size(); i++) { 
            	//声明党员基本信息实体
            	PartyMembers partyMembers = new PartyMembers();
            	partyMembers.setName((String) list.get(i).get("党员姓名"));//设置名字
            	partyMembers.setSex((String) list.get(i).get("性别"));//设置性别
            	partyMembers.setEthnic((String) list.get(i).get("民族"));//设置民族
            	partyMembers.setAge((int) Double.parseDouble((String) list.get(i).get("年龄")));//设置年龄
            	partyMembers.setDateBirth((Date) list.get(i).get("出生日期"));//设置出生日期
            	partyMembers.setIDCard((String) list.get(i).get("身份证号码"));//设置身份证号
            	partyMembers.setDegree((String) list.get(i).get("学历"));//设置学历
            	partyMembers.setJobPosition((String) list.get(i).get("工作岗位"));//设置工作岗位
            	partyMembers.setJoinPartyTime((Date) list.get(i).get("入党时间"));//设置入党时间
            	partyMembers.setPositiveDate((Date) list.get(i).get("转正日期"));//设置转正日期
            	partyMembers.setBranchParty((String) list.get(i).get("所在支部"));//设置所在支部
            	partyMembers.setPartyMembersNature((String) list.get(i).get("党员性质"));//设置党员性质
            	partyMembers.setHomeAddress((String) list.get(i).get("家庭住址"));//设置家庭住址
            	partyMembers.setMobilePhone((String) list.get(i).get("联系电话"));//设置联系电话
            	partyMembers.setFixedPhone((String) list.get(i).get("固定电话"));//设置固定电话
            	partyMembers.setLostUnionState((String) list.get(i).get("是否为失联党员"));//设置是否为失联党员
            	if("是".equals((String) list.get(i).get("是否为失联党员"))){
            		partyMembers.setLostUnionDate((Date) list.get(i).get("失联日期"));//设置失联日期
            	}
            	partyMembers.setMobilePartyMembers((String) list.get(i).get("是否为流动党员"));//设置是否为流动党员
            	if("是".equals((String) list.get(i).get("是否为流动党员"))){
            		partyMembers.setStreamWise((String) list.get(i).get("流向"));//设置流向
            	}
            	partyMembers.setPartyAffiliationStatus((String) list.get(i).get("党籍状态"));//设置党籍状态
            	partyServer.saveOrUpdatePartyMembers(partyMembers);
            }  
        } catch (FileNotFoundException e) {  
            System.out.println("未找到指定路径的文件!");  
            e.printStackTrace();  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
		super.doView(renderRequest, renderResponse);
	}
	
	
	
	/** 
     * 读取Excel表格表头的内容 
     *  
     * @param InputStream 
     * @return String 表头内容的数组 
     * @author  
     */  
    public String[] readExcelTitle() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        row = sheet.getRow(0);  
        // 标题总列数  
        int colNum = row.getPhysicalNumberOfCells();  
        System.out.println("colNum:" + colNum);  
        String[] title = new String[colNum];  
        for (int i = 0; i < colNum; i++) {  
            title[i] = (String) getCellFormatValue(row.getCell(i));  
        }  
        return title;  
    }  
  
    /** 
     * 读取Excel数据内容 
     *  
     * @param InputStream 
     * @return Map 包含单元格数据内容的Map对象 
     * @author  
     */  
    public Map<Integer, Map<Object, Object>> readExcelContent() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        Map<Integer, Map<Object, Object>> content = new HashMap<Integer, Map<Object,Object>>();  
          
        sheet = wb.getSheetAt(0);  
        // 得到总行数  
        int rowNum = sheet.getLastRowNum();  
        row = sheet.getRow(0);  
        int colNum = row.getPhysicalNumberOfCells();  
        // 正文内容应该从第二行开始,第一行为表头的标题  
        for (int i = 1; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            int j = 0;  
            Map<Object, Object> cellValue = new HashMap<Object, Object>();  
            while (j < colNum) {  
                Object obj = getCellFormatValue(row.getCell(j));  
                cellValue.put(j, obj);  
                j++;  
            }  
            content.put(i, cellValue);  
        }  
        return content;  
    }  
   
    /** 
     *  
     * 根据Cell类型设置数据 
     *  
     * @param cell 
     * @return 
     * @author 
     */  
	@SuppressWarnings("deprecation")
	private Object getCellFormatValue(Cell cell) {  
           Object cellvalue = "";  
           if (cell != null) {  
               // 判断当前Cell的Type  
               switch (cell.getCellType()) {  
               case Cell.CELL_TYPE_NUMERIC: // 如果当前Cell的Type为NUMERIC
            	   cellvalue = cell.getStringCellValue();
            	   break;
               case Cell.CELL_TYPE_FORMULA: {  
                   // 判断当前的cell是否为Date  
                   if (DateUtil.isCellDateFormatted(cell)) {  
                       // 如果是Date类型则，转化为Data格式  
                       // data格式是带时分秒的：2013-7-10 0:00:00  
                       // cellvalue = cell.getDateCellValue().toLocaleString();  
                       // data格式是不带带时分秒的：2013-7-10  
                       Date date = cell.getDateCellValue();  
                       cellvalue = date;  
                   } else {// 如果是纯数字  
                       // 取得当前Cell的数值,不按科学计数法输出
                	  // DecimalFormat df = new DecimalFormat("0");    
                	   //cellvalue = df.format(cell.getNumericCellValue()); 
                	   cellvalue = cell.getStringCellValue();
                   }  
                   break;  
               }  
               case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING  
                   // 取得当前的Cell字符串  
                   cellvalue = cell.getRichStringCellValue().getString();  
                   break;  
               default:// 默认的Cell值  
                   cellvalue = "";  
               }  
           } else {  
               cellvalue = "";  
           }  
           return cellvalue;  
       }  

	
}





