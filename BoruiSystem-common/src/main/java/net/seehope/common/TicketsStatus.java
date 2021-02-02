package net.seehope.common;

import io.swagger.models.auth.In;

public enum TicketsStatus {
    ON(1),OFF(0);
    TicketsStatus(Integer status){
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
