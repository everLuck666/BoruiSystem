package net.seehope.common;

public enum UserType {
    USER(0),SUPERMANAGER(1);

    UserType(Integer type){
        this.type = type;

    }

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
