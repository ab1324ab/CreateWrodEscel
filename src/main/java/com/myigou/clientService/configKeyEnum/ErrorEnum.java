package com.myigou.clientService.configKeyEnum;

/**
 * Created by ab1324ab on 2018/3/26.
 */
public enum ErrorEnum {

    FileError("数据源文件错误","FileError"),
    DateFormatError("日期格式设置错误","DateFormatError"),
    DateFormatMismatch("日期格式%s与数据源文件名不匹配","DateFormatMismatch"),
    SystemError("系统繁忙,请稍后再试!","SystemError");

    private String errorMsg;
    private String errorCode;

    // 构造方法
    private ErrorEnum(String errorMsg, String errorCode) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
