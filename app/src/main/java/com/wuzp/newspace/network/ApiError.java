package com.wuzp.newspace.network;

/**
 * Created by wuzp on 2017/9/17.
 */
public class ApiError {    /*
    * 没有登录
    * */
    public static final int S_ERROR_NOT_LOGIN = 0x001;
    // 网络层请求成功,但是发生错误
    /*
    * 网络请求数据成功，但数据为空
        * */
    public final static int S_NULL_DATA = 0x201;
    /*
    * 网络请求数据成功,但数据异常
    * */
    public final static int S_ERROR_DATA = 0x202;
    /*
     * 数据异常，但是可以提示给用户异常信息
     */
    public final static int S_ERROR_DATA_TIP = 0x203;

    public final static int S_ERROR_NEED_LOGIN = 100097;

    // 未知错误
    public final static int S_UNKNOW_ERROR = 0x200;
    // 网络层请求失败
    /*
    * 网络无法连接
    * */
    public final static int S_NETWORK_UNCONNECT = 0x501;
    // 用户为授权
    public final static int S_USER_NOT_AUTHORIZED = 0x601;

    private int errorCode;
    private String message = "";

    public ApiError(int errorCode) {
        this.errorCode = errorCode;
    }

    public ApiError(String message) {
        this.message = message;
    }

    public ApiError(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Error{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }

    /**
     * 是否是本地异常
     * 当网络访问成功，但因
     */
    public boolean isLocalError() {
        // S_ERROR_DATA_TIP 可以给用户提示的异常
        if (this.errorCode == S_ERROR_DATA_TIP) {
            return true;
        }
        return false;
    }

    public boolean isNetError() {
        return !isLocalError();
    }

}
