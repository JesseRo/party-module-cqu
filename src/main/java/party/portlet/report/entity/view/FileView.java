package party.portlet.report.entity.view;

public class FileView {
    public static final String WORD = "word";
    public static final String EXCEL = "excel";
    public static final String BOTH = "both";


    private String filename;

    private String path;

    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
