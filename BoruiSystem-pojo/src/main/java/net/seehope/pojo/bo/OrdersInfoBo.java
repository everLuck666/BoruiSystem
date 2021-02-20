package net.seehope.pojo.bo;

import lombok.Data;

@Data
public class OrdersInfoBo {
    private String orderId;
    private String productName;
    private String productNumber;
    private String userName;
    private String phone;
    private String address;
    private String orderAmout;
    private String orderTime;
    private String remark;
    private String invoicesType;
    private String status;


}
