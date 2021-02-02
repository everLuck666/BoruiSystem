package net.seehope.common;


public class RestfulJson {

    private int status;
    private String msg;
    private Object data;


    public static RestfulJson isOk(Object data) {
        return new RestfulJson(200, "ok", data);

    }

    public static RestfulJson errorMsg(String msg) {
        return new RestfulJson(600, msg, null);
    }

    public static RestfulJson errorAuthorized(String msg) {
        return new RestfulJson(401, msg, null);
    }


    public RestfulJson() {

    }

    public RestfulJson(int status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStaus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}