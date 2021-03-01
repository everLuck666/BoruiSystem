package net.seehope.pojo.bo;

import javax.persistence.Column;
import javax.persistence.Table;


public class StoreSendBo {

    private String user_name;

    private String phone;

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String userName) {
        this.user_name = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
