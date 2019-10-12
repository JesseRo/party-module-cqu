package party.portlet.report.entity.view;

import party.portlet.org.NotMatchingExcelDataException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelHandler {
    private String fileName;

    private String path;

    private List<String> sheetNames;

    private Map<String, SheetHandler> sheets;

    public ExcelHandler(String fileName){
        this.fileName = fileName;
        this.sheetNames = new ArrayList<>();
        this.sheets = new LinkedHashMap<>();
    }

    public ExcelHandler(String fileName, Map<String, SheetHandler> sheets){
        this.fileName = fileName;
        sheetNames = new ArrayList<>(sheets.keySet());
        this.sheets = sheets;
    }

    public ExcelHandler(String fileName, String path, Map<String, List<Map<String, Object>>> data){
        this.fileName = fileName;
        this.path = path;
        sheetNames = new ArrayList<>();
        sheets = new LinkedHashMap<>();
        for (String key: data.keySet()){
            sheetNames.add(key);
            List<Map<String, Object>> sheetData = data.get(key);
            SheetHandler sheetHandler = new SheetHandler(key, sheetData);
            sheets.put(key, sheetHandler);
        }
    }

    public static ExcelHandler empty(String fileName) {
        return new ExcelHandler(fileName);
    }

    public ExcelHandler merge(ExcelHandler other) throws NotMatchingExcelDataException {
        String fileName = this.fileName + "-汇总.xlsx";
        Map<String, SheetHandler> sheets = new LinkedHashMap<>();
        for (String sheetName : sheetNames){
            try {
                sheets.put(sheetName, this.sheets.get(sheetName).merge(other.sheets.get(sheetName)));
            }catch (NotMatchingExcelDataException e){
                String message = String.format("%s文件中的%ssheet,", this.fileName, sheetName);
                throw new NotMatchingExcelDataException(message + e.getMessage());
            }
        }
        return new ExcelHandler(fileName, sheets);
    }

    public Map<String, List<Map<String, Object>>> getAll(){
        Map<String, List<Map<String, Object>>> datas = new LinkedHashMap<>();
        for (String sheetName : sheetNames){
            datas.put(sheetName, sheets.get(sheetName).getRows());
        }
        return datas;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getSheetNames() {
        return sheetNames;
    }

    public void setSheetNames(List<String> sheetNames) {
        this.sheetNames = sheetNames;
    }

    public Map<String, SheetHandler> getSheets() {
        return sheets;
    }

    public void setSheets(Map<String, SheetHandler> sheets) {
        this.sheets = sheets;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
