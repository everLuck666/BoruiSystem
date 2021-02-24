package net.seehope.exception;

import net.seehope.common.RetEnum;


public class ApiException extends Exception {
    private static final long serialVersionUID = 1L;

    private RetEnum ret;
    private String retMessage;

    public RetEnum getRet() {
        return ret;
    }

    public void setRet(RetEnum ret) {
        this.ret = ret;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public ApiException(RetEnum ret) {
        super(ret.asMessage());
        this.ret = ret;
        this.retMessage = ret.asMessage();
    }

    public ApiException(RetEnum ret, String retMessage) {
        super(retMessage);
        this.ret = ret;
        this.retMessage = retMessage;
    }

}
