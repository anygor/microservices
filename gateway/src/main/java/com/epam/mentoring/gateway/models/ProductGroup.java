package com.epam.mentoring.gateway.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class ProductGroup {

    private Long id;
    private String groupName;
    private String price;
    private String created;

    private List<GroupVariant> groupVariants;

    public ProductGroup(){
    }

    public ProductGroup(String id){
        this.id = Long.parseLong(id);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<GroupVariant> getGroupVariants() {
        return groupVariants;
    }

    public void setGroupVariants(List<GroupVariant> groupVariants) {
        this.groupVariants = groupVariants;
    }

    public String toString() {
        return getGroupName();
    }
}
