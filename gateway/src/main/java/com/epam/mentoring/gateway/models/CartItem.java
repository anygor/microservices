package com.epam.mentoring.gateway.models;

import lombok.Data;

@Data
public class CartItem {
	private long productId;
	private long variantId;
	private int quantity;
}
