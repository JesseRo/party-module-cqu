package party.portlet.report.entity.view;

import party.portlet.org.NotMatchingExcelDataException;

import java.util.List;

public class WordHandler extends FileHandler<WordHandler> {

    private List<String> content;

    public WordHandler(String fileName, String path, List<String> content){
        super(fileName, path);
        this.content = content;
    }

    public WordHandler(String fileName) {
        super();
        this.fileName = fileName;
    }

    public static WordHandler empty(String fileName) {
        return new WordHandler(fileName);
    }


    @Override
    public WordHandler merge(WordHandler other) throws NotMatchingExcelDataException {
        if (this.content == null || this.content.isEmpty()){
            return other;
        }else {
            this.content.addAll(other.content);
        }
        return new WordHandler(this.fileName, null, this.content);
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content){
        this.content = content;
    }
}
