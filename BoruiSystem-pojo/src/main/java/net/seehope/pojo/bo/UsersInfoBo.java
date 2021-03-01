package net.seehope.pojo.bo;

import lombok.Data;

@Data
public class UsersInfoBo {
    private String subscribeStatus;
    private String userName;
    private String email;
    private String phone;
    private String address;
    private String productName;
    private Double orderAmout;
    private String userId;
}
