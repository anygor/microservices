package com.epam.productservice.models.stats;

import com.epam.productservice.models.Product;
import com.epam.productservice.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StatisticsUnit {
	private User user;
	private Product product;

	public StatisticsUnit(@JsonProperty User user, @JsonProperty Product product) {
		this.user = user;
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
