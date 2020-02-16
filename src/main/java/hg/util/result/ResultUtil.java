package hg.util.result;

public class ResultUtil {
    public static Result success(Object data) {
        return result(ResultCode.SUCCESS, "成功", data);
    }

    public static Result unauthorized(Object data) {
        return result(ResultCode.UNAUTHORIZED, "登陆认证失败", data);
    }

    public static Result fail(String message) {
        return result(ResultCode.FAIL, message, null);
    }

    public static Result fail(ResultCode resultCode, String message) {
        return result(resultCode, message, null);
    }

    public static Result result(ResultCode resultCode, String message, Object data) {
        return result(resultCode.code, message, data);
    }

    public static Result result(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }
}
