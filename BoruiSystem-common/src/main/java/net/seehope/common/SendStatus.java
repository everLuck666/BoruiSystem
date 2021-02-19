package net.seehope.common;

public enum  SendStatus {

    UNSEND(0),SEND(1);
    SendStatus(int status){
        this.status = status;
    }
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
