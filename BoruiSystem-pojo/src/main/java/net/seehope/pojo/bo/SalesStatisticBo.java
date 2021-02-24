package net.seehope.pojo.bo;

import lombok.Data;

@Data
public class SalesStatisticBo {
    private String productName;
    private String productPrice;
    private String today;
    private String month;
    private String total;
}
