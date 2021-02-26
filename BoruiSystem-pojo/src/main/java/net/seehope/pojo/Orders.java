package net.seehope.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "`orders`")
public class Orders implements Serializable {
    @Id
    @Column(name = "`order_id`")
    private String orderId;

    @Column(name = "`user_id`")
    private String userId;

    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`product_number`")
    private String productNumber;

    @Column(name = "`order_amout`")
    private String orderAmout;

    @Column(name = "`order_time`")
    private Date orderTime;

    @Column(name = "`order_status`")
    private String orderStatus;
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }



    /**
     * 备注信息
     */
    @Column(name = "`remark`")
    private String remark;

    /**
     * 发票类型
     */
    @Column(name = "`invoice_type`")
    private String invoiceType;

    @Column(name = "`status`")
    private String status;

    private static final long serialVersionUID = 1L;

    /**
     * @return order_id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

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
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return product_number
     */
    public String getProductNumber() {
        return productNumber;
    }

    /**
     * @param productNumber
     */
    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    /**
     * @return order_amout
     */
    public String getOrderAmout() {
        return orderAmout;
    }

    public void setOrderAmout(String orderAmout) {
        this.orderAmout = orderAmout;
    }

    /**
     * @return order_time
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * @param orderTime
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * 获取备注信息
     *
     * @return remark - 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取发票类型
     *
     * @return invoice_type - 发票类型
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置发票类型
     *
     * @param invoiceType 发票类型
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}