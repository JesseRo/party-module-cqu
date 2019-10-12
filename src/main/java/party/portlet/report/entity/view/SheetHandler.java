package party.portlet.report.entity.view;

import hg.util.CollectionUtils;
import party.portlet.org.NotMatchingExcelDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SheetHandler {
    private String sheetName;

    private List<String> titles;

    private List<Map<String, Object>> rows;

    public SheetHandler(String sheetName, List<String> titles, List<Map<String, Object>> rows){
        this.sheetName = sheetName;
        this.titles = titles;
        this.rows = rows;
    }

    public SheetHandler(String sheetName, List<Map<String, Object>> sheetData){
        this.sheetName = sheetName;
        if (!sheetData.isEmpty()){
            this.titles = new ArrayList<>(sheetData.get(0).keySet());
        }
        this.rows = sheetData;

    }

    public SheetHandler(){

    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public SheetHandler merge(SheetHandler other) throws NotMatchingExcelDataException {
        if (!CollectionUtils.isEquals(titles, other.titles)){
            throw new NotMatchingExcelDataException("sheet列名不匹配");
        }
        List<Map<String, Object>> sheetData = new ArrayList<>();
        sheetData.addAll(this.rows);
        sheetData.addAll(other.rows);
        return new SheetHandler(this.sheetName, sheetData);
    }
}
