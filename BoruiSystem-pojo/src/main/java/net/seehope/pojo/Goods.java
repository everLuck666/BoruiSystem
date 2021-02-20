package net.seehope.pojo;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "`goods`")
public class Goods implements Serializable {
    @Id
    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`describe`")
    private String describe;

    @Column(name = "`product_price`")
    private String productPrice;

    @Column(name = "`image_url`")
    private String imageUrl;

    @Column(name = "`status`")
    private String status;

    private static final long serialVersionUID = 1L;

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
     * @return describe
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * @param describe
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /**
     * @return product_price
     */
    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * @return image_url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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