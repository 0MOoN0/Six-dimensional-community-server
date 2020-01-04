package entity;

import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.Getter;

@Getter
public enum StatusCode {
    OK(20000, "成功"),
    ERROR(20001, "失败"),
    LOGINERROR(20002, "用户名或密码错误"),
    ACCESSERROR(20003, "权限不足"),
    REMOTEERROR(20004, "服务调用失败"),
    REPERROR(20005, "重复操作"),
    PARAMERROR(20006, "参数错误");

    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
