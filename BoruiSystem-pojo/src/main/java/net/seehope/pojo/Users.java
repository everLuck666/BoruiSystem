package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`users`")
public class Users implements Serializable {
    @Id
    @Column(name = "`user_id`")
    private String userId;

    @Column(name = "`user_name`")
    private String userName;

    @Column(name = "`email`")
    private String email;

    @Column(name = "`phone`")
    private String phone;

    @Column(name = "`address`")
    private String address;

    /**
     * 是否订阅
     */
    @Column(name = "`subscribe_status`")
    private String subscribeStatus;

    private static final long serialVersionUID = 1L;

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取是否订阅
     *
     * @return subscribe_status - 是否订阅
     */
    public String getSubscribeStatus() {
        return subscribeStatus;
    }

    /**
     * 设置是否订阅
     *
     * @param subscribeStatus 是否订阅
     */
    public void setSubscribeStatus(String subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }
}