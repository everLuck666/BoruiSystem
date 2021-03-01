package net.seehope.common;

public enum UserType {
    USER(0),SUPERMANAGER(1),SUBSCRIBESTATUS("订阅"),ALL("全部客户");

    UserType(Integer type){
        this.type = type;

    }

    private Integer type;
    private String subscribe_status;

    public String getSubscribe_status() {
        return subscribe_status;
    }

    public void setSubscribe_status(String subscribe_status) {
        this.subscribe_status = subscribe_status;
    }

    UserType(String subscribe_status){
        this.subscribe_status = subscribe_status;
    }




    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
