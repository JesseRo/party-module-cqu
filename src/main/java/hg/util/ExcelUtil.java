package hg.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil
{
    public static String NO_DEFINE = "no_define";//未定义的字段
    private static String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";//默认日期格式
    private static int DEFAULT_COLUMN_WIDTH = 17;
    
    /**
     * 导出Excel 2007 OOXML (.xlsx)格式
     *
     * @param title       标题行
     * @param headMap     属性-列头
     * @param dataTable   数据集
     * @param datePattern 日期格式，传null值则默认 年月日
     * @param colWidth    列宽 默认 至少17个字节
     */
    public static <T> SXSSFWorkbook exportExcelX(String title, Map<String, String> headMap, List<T> dataTable, String datePattern, int colWidth)
    {
        if (datePattern == null) datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
        workbook.setCompressTempFiles(true);
        //表头样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBold(false);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        SXSSFSheet sheet = workbook.createSheet();
        //设置列宽
        int minBytes = colWidth < DEFAULT_COLUMN_WIDTH ? DEFAULT_COLUMN_WIDTH : colWidth;//至少字节数
        int[] arrColWidth = new int[headMap.size()];
        // 产生表格标题行,以及设置列宽
        String[] properties = new String[headMap.size()];
        String[] headers = new String[headMap.size()];
        int ii = 0;
        for (String fieldName : headMap.keySet())
        {
            properties[ii] = fieldName;
            headers[ii] = headMap.get(fieldName);
            
            int bytes = fieldName.getBytes().length;
            arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
            sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
            ii++;
        }
        // 遍历集合数据，产生数据行
        int rowIndex = 0;
        Map<String, Method> getters = new HashMap<>();
        for (String property : properties)
        {
            PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(dataTable.get(0).getClass(), property);
            if (pd != null)
            {
                getters.put(property, pd.getReadMethod());
            }
            
        }
        for (T data : dataTable)
        {
            if (rowIndex == 65535 || rowIndex == 0)
            {
                if (rowIndex != 0) sheet = workbook.createSheet();//如果数据超过了，则在第二页显示
                if (title != null)
                {
                    SXSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
                    titleRow.createCell(0).setCellValue(title);
                    titleRow.getCell(0).setCellStyle(titleStyle);
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headMap.size() - 1));
                    rowIndex = 1;
                }
                SXSSFRow headerRow = sheet.createRow(rowIndex); //列头 rowIndex =1
                for (int i = 0; i < headers.length; i++)
                {
                    headerRow.createCell(i).setCellValue(headers[i]);
                    headerRow.getCell(i).setCellStyle(headerStyle);
                    
                }
                rowIndex += 1;//数据内容从 rowIndex=2开始
            }
//            JSONObject jo = (JSONObject) JSONObject.toJSON(map);
            SXSSFRow dataRow = sheet.createRow(rowIndex);
            for (int i = 0; i < properties.length; i++)
            {
                SXSSFCell newCell = dataRow.createCell(i);
                Object o = null;
                try
                {
                    if (getters.get(properties[i]) != null)
                    {
                        o = getters.get(properties[i]).invoke(data);
                    }
                }
                catch (IllegalAccessException | InvocationTargetException e)
                {
                    e.printStackTrace();
                }
                String cellValue;
                if (o == null)
                {
                    cellValue = "";
                }
                else if (o instanceof Date)
                {
                    cellValue = new SimpleDateFormat(datePattern).format(o);
                }
                else if (o instanceof Float || o instanceof Double)
                {
                    cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                }
                else if (o instanceof Boolean){
                    cellValue = o.equals(true) ? "是" : "否";
                }
                else
                {
                    cellValue = o.toString();
                }
                newCell.setCellValue(cellValue);
                newCell.setCellStyle(cellStyle);
            }
            rowIndex++;
        }
        // 自动调整宽度
        /*for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }*/
       /* try {
           // workbook.write(out);
           
          //  workbook.close();
          //  workbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return workbook;
    }

    public static SXSSFWorkbook exportExcelX(String title, Map<String, List<Map<String, Object>>> dataTable, String datePattern, int colWidth)
    {
        if (datePattern == null) datePattern = DEFAULT_DATE_PATTERN;
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
        workbook.setCompressTempFiles(true);
        //表头样式
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        // 列头样式
        CellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        // 单元格样式
        CellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font cellFont = workbook.createFont();
        cellFont.setBold(false);
        cellStyle.setFont(cellFont);
        // 生成一个(带标题)表格
        SXSSFSheet sheet;
        //设置列宽
        int minBytes = colWidth < DEFAULT_COLUMN_WIDTH ? DEFAULT_COLUMN_WIDTH : colWidth;//至少字节数

        // 遍历集合数据，产生数据行

        for (Map.Entry<String, List<Map<String, Object>>> data : dataTable.entrySet())
        {
            String sheetName = data.getKey();
            List<Map<String, Object>> sheetData = data.getValue();
            if (sheetData == null){
                throw new RuntimeException("sheet为空");
            }
            sheet = workbook.createSheet();
            int colSize = sheetData.get(0).size();
            int[] arrColWidth = new int[colSize];
            // 产生表格标题行,以及设置列宽
            String[] headers = new String[colSize];
            int ii = 0;
            for (String fieldName : sheetData.get(0).keySet())
            {
                headers[ii] = fieldName;
                int bytes = fieldName.getBytes().length;
                arrColWidth[ii] = bytes < minBytes ? minBytes : bytes;
                sheet.setColumnWidth(ii, arrColWidth[ii] * 256);
                ii++;
            }
            int rowIndex = 0;

            for (Map<String , Object> rowData : sheetData) {
                if (rowIndex >= 65535) {
                    throw new RuntimeException("数据条数超过excel最大限制");
                }
                if (rowIndex == 0) {
                    if (title != null) {
                        SXSSFRow titleRow = sheet.createRow(0);//表头 rowIndex=0
                        titleRow.createCell(0).setCellValue(title);
                        titleRow.getCell(0).setCellStyle(titleStyle);
                        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, rowData.size() - 1));
                        rowIndex = 1;
                    }
                    SXSSFRow headerRow = sheet.createRow(rowIndex); //列头 rowIndex =1
                    for (int i = 0; i < headers.length; i++) {
                        headerRow.createCell(i).setCellValue(headers[i]);
                        headerRow.getCell(i).setCellStyle(headerStyle);
                    }
                    rowIndex += 1;//数据内容从 rowIndex=2开始
                }
//            JSONObject jo = (JSONObject) JSONObject.toJSON(map);
                SXSSFRow dataRow = sheet.createRow(rowIndex);
                for (int i = 0; i < headers.length; i++)
                {
                    SXSSFCell newCell = dataRow.createCell(i);
                    Object o = rowData.get(headers[i]);
                    String cellValue = "";
                    if(o==null) cellValue = "";
                    else if(o instanceof Date) cellValue = new SimpleDateFormat(datePattern).format(o);
                    else if(o instanceof Float || o instanceof Double)
                        cellValue= new BigDecimal(o.toString()).setScale(5,BigDecimal.ROUND_HALF_UP).toString();
                    else cellValue = o.toString();

                    newCell.setCellValue(cellValue);
                    newCell.setCellStyle(cellStyle);
                }
                rowIndex++;
            }
        }
        // 自动调整宽度
        /*for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }*/
       /* try {
           // workbook.write(out);

          //  workbook.close();
          //  workbook.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return workbook;
    }
    
    /**
     * 读取Excel表格表头的内容
     *
     * @param sheet Sheet
     * @return String 表头内容的数组
     * @author
     */
    private static String[] readExcelTitle(Sheet sheet) throws Exception
    {
        if (sheet == null)
        {
            throw new Exception("Sheet对象为空！");
        }
        Row row = sheet.getRow(0);
        // 标题总列数  
        int colNum = row.getPhysicalNumberOfCells();
        System.out.println("colNum:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++)
        {
            title[i] = (String) getCellFormatValue(row.getCell(i));
        }
        return title;
    }
    
    /**
     * 读取Excel数据内容
     *
     * @param sheet Sheet
     * @return Map 包含单元格数据内容的Map对象
     * @author
     */
    private static Map<Integer, Map<Object, Object>> readExcelContent(Sheet sheet) throws Exception
    {
        if (sheet == null)
        {
            throw new Exception("Workbook对象为空！");
        }
        Map<Integer, Map<Object, Object>> content = new HashMap<>();
        
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        Row row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题  
        for (int i = 1; i <= rowNum; i++)
        {
            row = sheet.getRow(i);
            int j = 0;
            Map<Object, Object> cellValue = new HashMap<>();
            while (j < colNum)
            {
                Object obj = getCellFormatValue(row.getCell(j));
                cellValue.put(j, obj);
                j++;
            }
            content.put(i, cellValue);
        }
        return content;
    }
    
    /**
     * 根据Cell类型设置数据
     *
     * @param cell Cell
     * @return 单元格内容
     * @author
     */
    private static Object getCellFormatValue(Cell cell)
    {
        Object cellValue;
        if (cell != null)
        {
            // 判断当前Cell的Type
            switch (cell.getCellTypeEnum())
            {
                case NUMERIC: // 如果当前Cell的Type为NUMERIC
                    //设置单元格类型
//        	   cell.setCellType(CellType.NUMERIC);
                    if (HSSFDateUtil.isCellDateFormatted(cell))
                    {
                        cellValue = cell.getDateCellValue();
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                        cellValue = dateFormat.format(((Date) cellValue));
                    }
                    else
                    {
                        cell.setCellType(CellType.STRING);
                        cellValue = cell.getStringCellValue();
                    }
                    break;
                case FORMULA:
                {
                    // 判断当前的cell是否为Date
                    if (DateUtil.isCellDateFormatted(cell))
                    {
                        // 如果是Date类型则，转化为Data格式
                        // data格式是带时分秒的：2013-7-10 0:00:00
                        // cellvalue = cell.getDateCellValue().toLocaleString();
                        // data格式是不带带时分秒的：2013-7-10
                        cellValue = cell.getDateCellValue();
                    }
                    else
                    {// 如果是纯数字
                        // 取得当前Cell的数值,不按科学计数法输出
                        DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(cell.getNumericCellValue());
//                        cell.setCellType(CellType.STRING);
//                        cellValue = cell.getStringCellValue();
                    }
                    break;
                }
                case STRING:// 如果当前Cell的Type为STRING
                    // 取得当前的Cell字符串
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                default:// 默认的Cell值
                    cellValue = "";
            }
        }
        else
        {
            cellValue = "";
        }
        return cellValue;
    }
    
    /**
     * @param file MultipartFile
     * @return excel表格中数据
     */
    public static List<Map<String, Object>> importExcel(MultipartFile file)
    {
        Workbook wb;
        try
        {
            String filepath = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            if (filepath != null)//文件名为空时
            {
                wb = getWorkbook(filepath, is);
            }
            else
            {
                try
                {
                    wb = new XSSFWorkbook(is);
                }
                catch (IOException e1)
                {
                    wb = new HSSFWorkbook(is);
                }
            }
            if (wb.getNumberOfSheets() > 0)
            {
                return importExcel(wb.getSheetAt(0));
            }
            else
            {
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("打开excel文件出错!");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * @param file File
     * @return excel表格中数据
     */
    public static List<Map<String , Object>> importExcel(File file)
    {
        try
        {
            String filepath = file.getPath();
            InputStream is = new FileInputStream(filepath);
            return importExcel(filepath, is);
        }
        catch (IOException e)
        {
            System.out.println("打开excel文件出错!");
            e.printStackTrace();
            return null;
        }
    }
    
    public static Map<String, List<Map<String , Object>>> importAllSheet(String fileName, InputStream inputStream)
    {
        Workbook wb = getWorkbook(fileName, inputStream);
        int number = wb.getNumberOfSheets();
        Map<String, List<Map<String , Object>>> allSheetsData = new LinkedHashMap<>();
        for (int i = 0; i < number; i++)
        {
            Sheet sheet = wb.getSheetAt(i);
            List<Map<String , Object>> sheetData = importExcel(sheet);
            allSheetsData.putIfAbsent(sheet.getSheetName(), sheetData);
        }
        return allSheetsData;
    }
    
    public static List<Map<String , Object>> importExcel(String fileName, InputStream inputStream)
    {
        Workbook wb = getWorkbook(fileName, inputStream);
        if (wb.getNumberOfSheets() > 0)
        {
            return importExcel(wb.getSheetAt(0));
        }
        else
        {
            return null;
        }
        
    }
    
    public static Workbook getWorkbook(String fileName, InputStream inputStream)
    {
        Workbook wb;
        try
        {
            String ext = fileName.substring(fileName.lastIndexOf("."));
            if (".xls".equals(ext))
            {
                wb = new HSSFWorkbook(inputStream);
            }
            else if (".xlsx".equals(ext))
            {
                wb = new XSSFWorkbook(inputStream);
            }
            else
            {
                wb = null;
            }
        }
        catch (IOException e)
        {
            System.out.println("打开excel文件出错!");
            e.printStackTrace();
            wb = null;
        }
        return wb;
    }
    
    private static List<Map<String, Object>> importExcel(Sheet sheet)
    {
        if (sheet == null)
        {
            return null;
        }
        try
        {
            // 对读取Excel表格标题测试
            String[] title = readExcelTitle(sheet);
            // 对读取Excel表格内容测试
            Map<Integer, Map<Object, Object>> map = readExcelContent(sheet);
            //把表标题设置为每条数据对应的key值
            Map<Object, Object> map1;
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 1; i <= map.size(); i++)
            {
                map1 = map.get(i);
                Map<String, Object> map2 = new LinkedHashMap<>();
                for (int j = 0; j < map1.size(); j++)
                {
                    map2.put(title[j], map1.get(j));
                    map1.remove(j);
                }
                list.add(map2);
            }
            return list;
        }
        catch (Exception e)
        {
            System.out.println("读取excel出错!");
            e.printStackTrace();
            return null;
        }
    }
    
    static public <T> T MapToEntity(Map<Object, Object> dataMap, Class<T> t, Map<String, String> fieldNamePair)
            throws IllegalAccessException, InstantiationException
    {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        T entity = t.newInstance();
        for (Map.Entry<String, String> entry : fieldNamePair.entrySet())
        {
            PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(t, entry.getKey());
            if (pd != null)
            {
                Object data = dataMap.get(entry.getValue());
                try
                {
                    Class<?> type = pd.getPropertyType();
                    if (data instanceof String)
                    {
                        if (((String) data).isEmpty())
                        {
                            continue;
                        }
                        if (type.equals(BigDecimal.class))
                        {
                            data = new BigDecimal((String) data);
                        }
                        else if (type.equals(Integer.class) || type.equals(int.class))
                        {
                            data = Integer.parseInt((String) data);
                        }
                        else if (type.equals(Boolean.class) || type.equals(boolean.class))
                        {
                            data = data.equals("是");
                        }
                        else if (type.equals(Timestamp.class))
                        {
                        	try {
                                data = new Timestamp(dateTimeFormat.parse((String) data).getTime());
                        	}catch(ParseException ex) {
                        		data = new Timestamp(dateFormat.parse((String) data).getTime());
                        	}
                        }
                    }
                    else if (data instanceof Date)
                    {
                        if (type.equals(Timestamp.class))
                        {
                            data = new Timestamp(((Date) data).getTime());
                        }
                    }
                    pd.getWriteMethod().invoke(entity, data);
                }
                catch (InvocationTargetException | ParseException | IllegalAccessException | NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return entity;
    }
    
    public static void main(String[] arg) throws IOException, NoSuchFieldException, InvocationTargetException, IllegalAccessException
    {
        boolean c = false;
        Object a = c;
        Boolean b = false;
        System.out.println(a.getClass().equals(boolean.class));
        System.out.println(a.equals(false));
        System.out.println(b.equals(false));
    }
    
}
