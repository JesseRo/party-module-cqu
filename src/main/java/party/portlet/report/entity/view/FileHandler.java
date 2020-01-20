package party.portlet.report.entity.view;

import party.portlet.org.NotMatchingExcelDataException;

abstract public class FileHandler <T extends FileHandler>{
    String fileName;

    String path;

    public FileHandler(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }

    public FileHandler() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    abstract T merge(T other) throws NotMatchingExcelDataException;
}
