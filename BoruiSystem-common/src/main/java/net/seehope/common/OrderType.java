package net.seehope.common;

public enum OrderType {
    NOUSED(0),USED(1);//NOUSED是表示这个订单没有用过，或者没有用完，USED表示用完了

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    OrderType(Integer type){
        this.type = type;
    }
    public Integer type;
}
