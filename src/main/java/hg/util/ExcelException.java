package hg.util;

/**
 * @author jesse
 * @Filename ExcelException
 * @description
 * @Version 1.0
 * @History <br/>
 * @Date: 2020/4/19</li>
 */

public class ExcelException extends RuntimeException {

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}