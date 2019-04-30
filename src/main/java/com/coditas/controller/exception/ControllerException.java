package com.coditas.controller.exception;

/**
 * @author Harshal Patil
 */
public class ControllerException extends RuntimeException{

    private String exceptionMsg;
    private String errorCode = "400";

    public ControllerException() {
        super();
    }

    public ControllerException(String exceptionMsg, String errorCode) {
        super();
        this.exceptionMsg = exceptionMsg;
        this.errorCode = errorCode;
    }

    public ControllerException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public ControllerException(Throwable arg0) {
        super(arg0);
    }

    public ControllerException(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getExceptionMsg(){
        return this.exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
