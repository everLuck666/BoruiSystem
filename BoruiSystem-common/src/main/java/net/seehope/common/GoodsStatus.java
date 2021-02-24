package net.seehope.common;

public enum  GoodsStatus {
    ON(1),OFF(0);

    GoodsStatus(int status){
        this.status = status;

    }
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
