package com.epam.mentoring.statisticservice.model.stats;

import com.epam.mentoring.statisticservice.model.Product;
import com.epam.mentoring.statisticservice.model.User;

public class StatisticsUnit {
	private User user;
	private Product product;

	public StatisticsUnit(User user, Product product) {
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
