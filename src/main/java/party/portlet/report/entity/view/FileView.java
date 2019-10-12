package party.portlet.report.entity.view;

public class FileView {
    private String filename;

    private String path;

    public FileView(String filename, String path){
        this.filename = filename;
        this.path = path;
    }

    public FileView(){

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
