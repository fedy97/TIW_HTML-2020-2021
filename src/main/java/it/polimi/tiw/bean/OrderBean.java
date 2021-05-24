package it.polimi.tiw.bean;

import java.io.Serializable;
import java.util.List;

public class OrderBean implements Serializable {

    private String id;
    private String seller_id;
    private String total;
    private String shipment_date;
    private String user_id;
    private String seller_name;
    private String seller_rating;
    private String name;
    private String surname;
    private String email;
    private String shipment_addr;
    private List<ArticleBean> articleBeans;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getShipment_date() {
        return shipment_date;
    }

    public void setShipment_date(String shipment_date) {
        this.shipment_date = shipment_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getSeller_rating() {
        return seller_rating;
    }

    public void setSeller_rating(String seller_rating) {
        this.seller_rating = seller_rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShipment_addr() {
        return shipment_addr;
    }

    public void setShipment_addr(String shipment_addr) {
        this.shipment_addr = shipment_addr;
    }

    public List<ArticleBean> getArticleBeans() {
        return articleBeans;
    }

    public void setArticleBeans(List<ArticleBean> articleBeans) {
        this.articleBeans = articleBeans;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "id='" + id + '\'' +
                ", seller_id='" + seller_id + '\'' +
                ", total='" + total + '\'' +
                ", shipment_date='" + shipment_date + '\'' +
                ", user_id='" + user_id + '\'' +
                ", seller_name='" + seller_name + '\'' +
                ", seller_rating='" + seller_rating + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", shipment_addr='" + shipment_addr + '\'' +
                ", articleBeans=" + articleBeans.size() +
                '}';
    }
}
