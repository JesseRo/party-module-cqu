package hg.util.result;

import hg.party.entity.partyMembers.Member;
import org.apache.poi.ss.formula.functions.T;

public class ResultUtil {
    public static Result success(Object data) {
        return result(ResultCode.SUCCESS, "成功", data);
    }
    public static Result success(Object data,String message) {
        return result(ResultCode.SUCCESS, message, data);
    }
    public static Result unauthorized(Object data) {
        return result(ResultCode.UNAUTHORIZED, "登陆认证失败", data);
    }

    public static Result fail(String message) {
        return result(ResultCode.FAIL, message, null);
    }

    public static Result noAuthority(String message) {
        return result(ResultCode.NO_AUTHORITY, message, null);
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
    public static PageResult resultPage(Page<T> page) {
        return new PageResult(0, "成功", page.getData(),page.getCount());
    }
}
