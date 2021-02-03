package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`invoices`")
public class Invoices implements Serializable {
    @Id
    @Column(name = "`order_id`")
    private String orderId;

    /**
     * 发票类型
     */
    @Column(name = "`invoice_type`")
    private String invoiceType;

    /**
     * 发票抬头
     */
    @Column(name = "`invoice_title`")
    private String invoiceTitle;

    /**
     * 开户银行
     */
    @Column(name = "`bank`")
    private String bank;

    /**
     * 开户账号
     */
    @Column(name = "`accout`")
    private String accout;

    /**
     * 税号
     */
    @Column(name = "`tax_id`")
    private Integer taxId;

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
     * 获取发票抬头
     *
     * @return invoice_title - 发票抬头
     */
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    /**
     * 设置发票抬头
     *
     * @param invoiceTitle 发票抬头
     */
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    /**
     * 获取开户银行
     *
     * @return bank - 开户银行
     */
    public String getBank() {
        return bank;
    }

    /**
     * 设置开户银行
     *
     * @param bank 开户银行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取开户账号
     *
     * @return accout - 开户账号
     */
    public String getAccout() {
        return accout;
    }

    /**
     * 设置开户账号
     *
     * @param accout 开户账号
     */
    public void setAccout(String accout) {
        this.accout = accout;
    }

    /**
     * 获取税号
     *
     * @return tax_id - 税号
     */
    public Integer getTaxId() {
        return taxId;
    }

    /**
     * 设置税号
     *
     * @param taxId 税号
     */
    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }
}