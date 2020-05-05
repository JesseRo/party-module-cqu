package party.portlet.report.entity.view;

import party.portlet.org.NotMatchingExcelDataException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelHandler extends FileHandler<ExcelHandler>{
    private List<String> sheetNames;

    private Map<String, SheetHandler> sheets;

    public ExcelHandler(String fileName){
        super();
        this.fileName = fileName;
        this.sheetNames = new ArrayList<>();
        this.sheets = new LinkedHashMap<>();
    }

    public ExcelHandler(String fileName, Map<String, SheetHandler> sheets){
        super();
        this.fileName = fileName;
        sheetNames = new ArrayList<>(sheets.keySet());
        this.sheets = sheets;
    }

    public ExcelHandler(String fileName, String path, List<String> sheetNames){
        super();
        this.fileName = fileName;
        this.path = path;
        this.sheetNames = sheetNames;
    }

    public ExcelHandler(String fileName, String path, Map<String, List<Map<String, Object>>> data){
        super();
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

    @Override
    public ExcelHandler merge(ExcelHandler other) throws NotMatchingExcelDataException {
//        String fileName = this.fileName + "-汇总.xlsx";
        if (this.sheetNames == null || this.sheetNames.isEmpty()){
            return other;
        }
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

    public ExcelHandler mergeAsSheet(ExcelHandler other, String orgName) throws NotMatchingExcelDataException {
//        String fileName = this.fileName + "-汇总.xlsx";
        if (this.sheetNames == null || this.sheetNames.isEmpty()){
            this.sheetNames = new ArrayList<>();
            this.sheets = new LinkedHashMap<>();
        }

        sheets.putIfAbsent(orgName, other.sheets.get(other.sheetNames.get(0)));
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
