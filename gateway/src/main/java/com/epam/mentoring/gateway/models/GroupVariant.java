package com.epam.mentoring.gateway.models;

public class GroupVariant {

    private long id;
    private String variantName;
    private ProductGroup group;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public ProductGroup getGroup() {
        return group;
    }

    public void setGroup(ProductGroup group) {
        this.group = group;
    }

    public String toString() {
        return getVariantName();
    }
}
