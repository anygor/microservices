package com.epam.mentoring.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    private Integer id;
    private String price;

    private Order order;
    private long productId;
    private long groupVariantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getGroupVariantId() {
        return groupVariantId;
    }

    public void setGroupVariantId(long groupVariantId) {
        this.groupVariantId = groupVariantId;
    }
}
