package com.hpu.sencondhand.bean;

/**
 * Created by：何学慧
 * Detail:
 * on 2019/12/30
 */

public class Product {
    private String owner;
    private String imgPath;
    private String title;
    private String category;
    private String prie;
    private String detail;
    private String contactDetail;

    public Product(String owner, String imgPath, String title, String category, String prie,String contactDetail, String detail) {
        this.owner = owner;
        this.imgPath = imgPath;
        this.title = title;
        this.category = category;
        this.prie = prie;
        this.contactDetail=contactDetail;
        this.detail = detail;
    }

    public Product() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrie() {
        return prie;
    }

    public void setPrie(String prie) {
        this.prie = prie;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(String contactDetail) {
        this.contactDetail = contactDetail;
    }
}
