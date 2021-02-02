package net.seehope.common;

public enum UserType {
    STUDENT(0),TEACHER(1),SUPERMANAGER(2);

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
