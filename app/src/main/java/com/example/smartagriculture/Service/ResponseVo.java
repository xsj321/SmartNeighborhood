package com.example.smartagriculture.Service;

public class ResponseVo<T> {

    private T data;

    /**
     * 返回消息
     */

    private String msg;

    /**
     * 代码
     */

    private Integer code;

    /**
     * 是否成功
     */

    private boolean success;

    public ResponseVo() {
    }

    public ResponseVo(boolean success, Integer code, String msg) {
        this.msg = msg;
        this.code = code;
        this.success = success;
    }

    public static <T> ResponseVo<T> buildSuccessInstance() {
        return buildSuccessInstance(null);
    }

    public static <T> ResponseVo<T> buildSuccessInstance(T data) {
        ResponseVo<T> responseVo = new ResponseVo<T>();
        responseVo.setData(data);
        responseVo.setCode(1);
        responseVo.setMsg("操作成功");
        responseVo.setSuccess(true);
        return responseVo;
    }

    public static <T> ResponseVo<T> buildFailInstance() {
        return new ResponseVo<T>(false, ErrorCode.COMMON_ERROR, "fail");
    }

    public static <T> ResponseVo<T> buildFailInstance(int errorCode, String msg) {
        return new ResponseVo<T>(false, errorCode, msg);
    }

   /* public static <T> ResponseVo<T> buildFailInstance(Exception ex) {

        if (ex instanceof HermesRuntimeException) {
            return new ResponseVo<T>(false, ((HermesRuntimeException) ex).getErrorCode(), ex.getMessage());
        } else if (ex instanceof HermesException) {
            return new ResponseVo<T>(false, ((HermesException) ex).getErrorCode(), ex.getMessage());
        }
        return new ResponseVo<T>(false, ErrorCode.COMMON_ERROR, ex.getMessage());
    }*/
    
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}