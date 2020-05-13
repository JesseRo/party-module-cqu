package hg.util;

/**
 * @author jesse
 * @Filename ExcelOperationUtil
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/4/19</li>
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import party.portlet.report.entity.view.ExcelHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * 提供常用excel操作<br>
 * <ul>
 *     <li></li>
 * </ul>
 */
public class ExcelOperationUtil {
    private static Logger logger = LoggerFactory.getLogger(ExcelOperationUtil.class);

    /**
     * sheet 复制，复制数据、如果同一个文件，复制样式，不同文件则只复制数据<br/>
     * 如果是同book中复制，建议使用workbook中的cloneSheet()方法<br/>
     * <p>
     * <br/>建议用于 不同book间只复制数据
     */
    public static void copySheet(Sheet srcSheet, Sheet desSheet) {
        copySheet(srcSheet, desSheet, true, true, null);
    }

    /**
     * sheet 复制，如果同一个文件，复制样式，不同文件则不复制<br/>
     * <p>
     * <br/>建议用于 同book中，只复制样式，不复制数据<br/>
     * eg: copySheet(srcSheet, desSheet, false)
     *
     * @param copyValueFlag 控制是否复制数据
     */
    public static void copySheet(Sheet srcSheet, Sheet desSheet, boolean copyValueFlag) {
        copySheet(srcSheet, desSheet, copyValueFlag, true, null);
    }

    /**
     * sheet 复制，复制数据、样式<br/>
     * <p>
     * <br/>建议用于 不同book间复制，同时复制数据和样式<br/>
     * eg: copySheet(srcSheet, desSheet, mapping)
     *
     * @param mapping 不同文件间复制时，如果要复制样式，必传，否则不复制样式
     */
    public static void copySheet(Sheet srcSheet, Sheet desSheet, StyleMapping mapping) {
        copySheet(srcSheet, desSheet, true, true, mapping);
    }

    /**
     * sheet 复制,复制数据<br/>
     * <p>
     * <br/>建议用于 同book中，只复制数据，不复制样式<br/>
     * eg: copySheet(srcSheet, desSheet, false, null)
     *
     * @param srcSheet
     * @param desSheet
     * @param copyStyleFlag
     * @param mapping
     */
    public static void copySheet(Sheet srcSheet, Sheet desSheet, boolean copyStyleFlag, StyleMapping mapping) {
        copySheet(srcSheet, desSheet, true, copyStyleFlag, mapping);
    }

    public static Workbook mergeByAppending(ExcelHandler template, ExcelHandler sourceHandler, Workbook dest) throws IOException, InvalidFormatException {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        for (String name : template.getSheetNames()) {
            if (dest.getSheet(name) == null) {
                dest.createSheet(name);
            }
        }
        Workbook wb = new XSSFWorkbook(new File(sourceHandler.getPath()));
        StyleMapping styleMapping = copyCellStyle(wb, dest);
        for (String sheetName : sourceHandler.getSheetNames()) {
            if (dest.getSheet(sheetName) != null) {
                copySheet(wb.getSheet(sheetName), dest.getSheet(sheetName), styleMapping);
            }
        }
        stopwatch.stop();
        logger.debug(stopwatch.prettyPrint());
        return dest;
    }

    public static Workbook mergeByMultipleSheet(String sheetName, ExcelHandler sourceHandler, Workbook dest) throws IOException, InvalidFormatException {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        Sheet destSheet = dest.createSheet(sheetName);
        Workbook wb = new XSSFWorkbook(new File(sourceHandler.getPath()));
        StyleMapping styleMapping = copyCellStyle(wb, dest);
        copySheet(wb.getSheetAt(0), destSheet, styleMapping);
        wb.close();

        stopwatch.stop();
        logger.debug(stopwatch.prettyPrint());
        return dest;
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        Workbook dest = new SXSSFWorkbook();
        Sheet destSheet;
        File file = new File("C:\\Users\\Administrator\\Downloads\\dest.xlsx");
        if (!file.exists()) {
            file.createNewFile();
        }
        destSheet = dest.createSheet();
        for (int i = 1; i <= 10; i++) {
            Workbook wb = new XSSFWorkbook(new File("C:\\Users\\Administrator\\Downloads\\src" + i + ".xlsx"));
            StyleMapping styleMapping = copyCellStyle(wb, dest);
            copySheet(wb.getSheetAt(0), destSheet, styleMapping);
        }
        FileOutputStream fos = new FileOutputStream(file);
        long vmFree = 0;
        long vmUse = 0;
        long vmTotal = 0;
        long vmMax = 0;
        int byteToMb = 1024 * 1024;
        Runtime rt = Runtime.getRuntime();
        vmTotal = rt.totalMemory() / byteToMb;
        vmFree = rt.freeMemory() / byteToMb;
        vmMax = rt.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        System.out.println("JVM内存已用的空间为：" + vmUse + " MB");
        System.out.println("JVM内存的空闲空间为：" + vmFree + " MB");
        System.out.println("JVM总内存空间为：" + vmTotal + " MB");
        System.out.println("JVM最大内存空间为：" + vmMax + " MB");
        dest.write(fos);
        fos.close();
        stopwatch.stop();
        logger.debug(stopwatch.prettyPrint());


    }

    /**
     * sheet 复制, 灵活控制是否控制数据、样式<br/>
     * <p>
     * <br/>不建议直接使用
     *
     * @param copyValueFlag 控制是否复制数据
     * @param copyStyleFlag 控制是否复制样式
     * @param mapping       不同book中复制样式时，必传
     */
    public static void copySheet(Sheet srcSheet, Sheet desSheet, boolean copyValueFlag, boolean copyStyleFlag, StyleMapping mapping) {
        if (srcSheet.getWorkbook() == desSheet.getWorkbook()) {
            logger.warn("统一workbook内复制sheet建议使用 workbook的cloneSheet方法");
        }

        //合并区域处理
        copyMergedRegion(srcSheet, desSheet);
        int destRowCount = desSheet.getLastRowNum() > 0 ? desSheet.getLastRowNum() + 1 : 0;
        //行复制
        Iterator<Row> rowIterator = srcSheet.iterator();

        int alreadyColumn = 0;
        while (rowIterator.hasNext()) {
            Row srcRow = rowIterator.next();
            Row desRow;
            if (destRowCount > 0) {
                desRow = desSheet.createRow(srcRow.getRowNum() + destRowCount);
            } else {
                desRow = desSheet.createRow(srcRow.getRowNum());
            }
            copyRow(srcRow, desRow, copyValueFlag, copyStyleFlag, mapping);

            //调整列宽(增量调整)
            if (srcRow.getPhysicalNumberOfCells() > alreadyColumn) {
                for (int i = alreadyColumn; i < srcRow.getPhysicalNumberOfCells(); i++) {
                    desSheet.setColumnWidth(i, srcSheet.getColumnWidth(i));
                }
                alreadyColumn = srcRow.getPhysicalNumberOfCells();
            }
        }
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow) {
        copyRow(srcRow, desRow, true, true, null);
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow, boolean copyValueFlag) {
        copyRow(srcRow, desRow, copyValueFlag, true, null);
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow, StyleMapping mapping) {
        copyRow(srcRow, desRow, true, true, mapping);
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow, boolean copyStyleFlag, StyleMapping mapping) {
        copyRow(srcRow, desRow, true, copyStyleFlag, mapping);
    }

    /**
     * 复制行
     */
    public static void copyRow(Row srcRow, Row desRow, boolean copyValueFlag, boolean copyStyleFlag, StyleMapping mapping) {
        Iterator<Cell> it = srcRow.cellIterator();
        while (it.hasNext()) {
            Cell srcCell = it.next();
            Cell desCell = desRow.createCell(srcCell.getColumnIndex());
            copyCell(srcCell, desCell, copyValueFlag, copyStyleFlag, mapping);
        }
    }

    /**
     * 复制区域（合并单元格）
     */
    public static void copyMergedRegion(Sheet srcSheet, Sheet desSheet) {
        int sheetMergerCount = srcSheet.getNumMergedRegions();
        int destRowCount = desSheet.getLastRowNum() > 0 ? desSheet.getLastRowNum() + 1 : 0;
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress cellRangeAddress = srcSheet.getMergedRegion(i);
            cellRangeAddress.setFirstRow(cellRangeAddress.getFirstRow() + destRowCount);
            cellRangeAddress.setLastRow(cellRangeAddress.getLastRow() + destRowCount);
            desSheet.addMergedRegion(cellRangeAddress);
        }
    }

    /**
     * 复制单元格，复制数据，如果同文件，复制样式，不同文件则不复制样式
     */
    public static void copyCell(Cell srcCell, Cell desCell) {
        copyCell(srcCell, desCell, true, true, null);
    }

    /**
     * 复制单元格， 如果同文件，复制样式，不同文件则不复制样式
     *
     * @param copyValueFlag 控制是否复制数据
     */
    public static void copyCell(Cell srcCell, Cell desCell, boolean copyValueFlag) {
        copyCell(srcCell, desCell, copyValueFlag, true, null);
    }

    /**
     * 复制单元格，复制数据,复制样式
     *
     * @param mapping 不同文件间复制时，如果要复制样式，必传，否则不复制样式
     */
    public static void copyCell(Cell srcCell, Cell desCell, StyleMapping mapping) {
        copyCell(srcCell, desCell, true, true, mapping);
    }

    /**
     * 复制单元格，复制数据
     *
     * @param copyStyleFlag 控制是否复制样式
     * @param mapping       不同文件间复制时，如果要复制样式，必传，否则不复制样式
     */
    public static void copyCell(Cell srcCell, Cell desCell, boolean copyStyleFlag, StyleMapping mapping) {
        copyCell(srcCell, desCell, true, copyStyleFlag, mapping);
    }

    /**
     * 复制单元格
     *
     * @param copyValueFlag 控制是否复制单元格的内容
     * @param copyStyleFlag 控制是否复制样式
     * @param mapping       不同文件间复制时，如果需要连带样式复制，必传，否则不复制样式
     */
    public static void copyCell(Cell srcCell, Cell desCell, boolean copyValueFlag, boolean copyStyleFlag, StyleMapping mapping) {
        Workbook srcBook = srcCell.getSheet().getWorkbook();
        Workbook desBook = desCell.getSheet().getWorkbook();

        //复制样式
        //如果是同一个excel文件内，连带样式一起复制
        if (srcBook == desBook && copyStyleFlag) {
            //同文件，复制引用
            desCell.setCellStyle(srcCell.getCellStyle());
        } else if (copyStyleFlag) {
            //不同文件，通过映射关系复制
            if (null != mapping) {
                short desIndex = mapping.desIndex(srcCell.getCellStyle().getIndex());
                desCell.setCellStyle(desBook.getCellStyleAt(desIndex));
            }
        }

        //复制评论
        if (srcCell.getCellComment() != null) {
            desCell.setCellComment(srcCell.getCellComment());
        }

        //复制内容
        desCell.setCellType(srcCell.getCellTypeEnum());

        if (copyValueFlag) {
            switch (srcCell.getCellTypeEnum()) {
                case STRING:
                    desCell.setCellValue(srcCell.getStringCellValue());
                    break;
                case NUMERIC:
                    desCell.setCellValue(srcCell.getNumericCellValue());
                    break;
                case FORMULA:
                    desCell.setCellFormula(srcCell.getCellFormula());
                    break;
                case BOOLEAN:
                    desCell.setCellValue(srcCell.getBooleanCellValue());
                    break;
                case ERROR:
                    desCell.setCellValue(srcCell.getErrorCellValue());
                    break;
                case BLANK:
                    //nothing to do
                    break;
                default:
                    break;
            }
        }

    }


    /**
     * 把一个excel中的styleTable复制到另一个excel中<br>
     * 如果是同一个excel文件，就不用复制styleTable了
     *
     * @return StyleMapping 两个文件中styleTable的映射关系
     * @see StyleMapping
     */
    public static StyleMapping copyCellStyle(Workbook srcBook, Workbook desBook) {
        if (null == srcBook || null == desBook) {
            throw new ExcelException("源excel 或 目标excel 不存在");
        }
        if (srcBook.equals(desBook)) {
            throw new ExcelException("不要使用此方法在同一个文件中copy style，同一个excel中复制sheet不需要copy Style");
        }
        if ((srcBook instanceof HSSFWorkbook && desBook instanceof XSSFWorkbook) ||
                (srcBook instanceof XSSFWorkbook && desBook instanceof HSSFWorkbook)) {
            throw new ExcelException("不支持在不同的版本的excel中复制样式）");
        }

        logger.debug("src中style number:{}, des中style number:{}", srcBook.getNumCellStyles(), desBook.getNumCellStyles());
        short[] src2des = new short[srcBook.getNumCellStyles()];
        short[] des2src = new short[desBook.getNumCellStyles() + srcBook.getNumCellStyles()];

        for (short i = 0; i < srcBook.getNumCellStyles(); i++) {
            //建立双向映射
            CellStyle srcStyle = srcBook.getCellStyleAt(i);
            CellStyle desStyle = desBook.createCellStyle();
            src2des[srcStyle.getIndex()] = desStyle.getIndex();
            des2src[desStyle.getIndex()] = srcStyle.getIndex();

            //复制样式
            try {
                desStyle.cloneStyleFrom(srcStyle);
            }catch (Exception e){
                logger.info("复制样式错误");
                e.printStackTrace();
            }
        }


        return new StyleMapping(des2src, src2des);
    }

    /**
     * 存放两个excel文件中的styleTable的映射关系，以便于在复制表格时，在目标文件中获取到对应的样式
     */
    public static class StyleMapping {
        /**
         *
         */
        private short[] des2srcIndexMapping;
        /**
         *
         */
        private short[] src2desIndexMapping;

        /**
         * 不允许其他类创建此类型对象
         */
        private StyleMapping() {
        }

        public StyleMapping(short[] des2srcIndexMapping, short[] src2desIndexMapping) {
            this.des2srcIndexMapping = des2srcIndexMapping;
            this.src2desIndexMapping = src2desIndexMapping;
        }

        public short srcIndex(short desIndex) {
            if (desIndex < 0 || desIndex >= this.des2srcIndexMapping.length) {
                throw new ExcelException("索引越界：源文件styleNum=" + this.des2srcIndexMapping.length + " 访问位置=" + desIndex);
            }
            return this.des2srcIndexMapping[desIndex];
        }

        /**
         * 根据源文件的style的index,获取目标文件的style的index
         *
         * @param srcIndex 源excel中style的index
         * @return desIndex 目标excel中style的index
         */
        public short desIndex(short srcIndex) {
            if (srcIndex < 0 || srcIndex >= this.src2desIndexMapping.length) {
                throw new ExcelException("索引越界：源文件styleNum=" + this.src2desIndexMapping.length + " 访问位置=" + srcIndex);
            }

            return this.src2desIndexMapping[srcIndex];
        }
    }
}