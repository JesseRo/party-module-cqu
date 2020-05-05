package hg.party.unity;

import org.apache.poi.POIDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.model.PAPBinTable;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import party.portlet.report.entity.view.WordHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WordUtils {
    /**
     * 读取doc文件内容
     *
     * @param fs 想要读取的文件
     * @param fileName 想要读取的文件名
     * @return 返回文件内容
     */
    public static List<String> importWordData(String fileName, FileInputStream fs){

        try
        {
            String ext = fileName.substring(fileName.lastIndexOf("."));
            if (".doc".equals(ext))
            {
                HWPFDocument doc = new HWPFDocument(fs);
                String str = doc.getDocumentText();
                doc.close();
                fs.close();
                return Collections.singletonList(str);
            }
            else if (".docx".equals(ext))
            {
                XWPFDocument xdoc = new XWPFDocument(fs);
                XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                List<XWPFParagraph> paragraphs = xdoc.getParagraphs();
                List<String> paragraphText = paragraphs.stream().map(XWPFParagraph::getText).collect(Collectors.toList());
                extractor.close();
                fs.close();
                return paragraphText;
            }
            else
            {
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("打开word文件出错!");
            e.printStackTrace();
        }
        return null;
    }

    public static XWPFDocument export(WordHandler wordHandler){
        XWPFDocument xwpfDocument = new XWPFDocument();
        for (String content : wordHandler.getContent()){
            XWPFParagraph paragraph = xwpfDocument.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(content);
        }
        return xwpfDocument;
    }
}
