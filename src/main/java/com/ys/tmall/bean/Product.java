package com.ys.tmall.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
    private Integer id;

    private String name;

    private String subtitle;

    private Float originalprice;

    private Float promoteprice;

    private Integer stock;

    private Integer cid;

    private Date createdate;

    //新增销量
    private Integer saleCount;

    //xz,评价数量
    private Integer reviewCount;

    //新增,用于保存第一张图片的id
    private Integer firstProductimageId;

    //新增,用户存放产品的单一图片信息
    private List<Productimage> singleProductimages;
    //新增,用户存放产品的详细图片信息
    private List<Productimage> detailProductimages;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public Float getOriginalprice() {
        return originalprice;
    }

    public void setOriginalprice(Float originalprice) {
        this.originalprice = originalprice;
    }

    public Float getPromoteprice() {
        return promoteprice;
    }

    public void setPromoteprice(Float promoteprice) {
        this.promoteprice = promoteprice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public List<Productimage> getSingleProductimages() {
        return singleProductimages;
    }

    public void setSingleProductimages(List<Productimage> singleProductimages) {
        this.singleProductimages = singleProductimages;
    }

    public List<Productimage> getDetailProductimages() {
        return detailProductimages;
    }

    public void setDetailProductimages(List<Productimage> detailProductimages) {
        this.detailProductimages = detailProductimages;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getFirstProductimageId() {
        return firstProductimageId;
    }

    public void setFirstProductimageId(Integer firstProductimageId) {
        this.firstProductimageId = firstProductimageId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", subtitle=").append(subtitle);
        sb.append(", originalprice=").append(originalprice);
        sb.append(", promoteprice=").append(promoteprice);
        sb.append(", stock=").append(stock);
        sb.append(", cid=").append(cid);
        sb.append(", createdate=").append(createdate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}