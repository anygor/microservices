package com.epam.mentoring.gateway.controller;

import com.epam.mentoring.gateway.feign.BackendFeignClient;
import com.epam.mentoring.gateway.models.CartItem;
import com.epam.mentoring.gateway.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private BackendFeignClient backendFeignClient;

	@PostMapping()
	public ResponseEntity create() {
		Map<String, Object> map = new HashMap<>();
		map.put("cart", backendFeignClient.cartCreate());
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity addProduct(@PathVariable("id") String cartId, @RequestBody CartItem cartItem) {
		Map<String, Object> map = new HashMap<>();
		map.put("cart", backendFeignClient.cartAddProduct(cartId, cartItem));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity getCartItems(@PathVariable("id") String cartId) {
		Map<String, Object> map = new HashMap<>();
		map.put("cart", backendFeignClient.cartGetCartItems(cartId));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@DeleteMapping("{id}/{product_id}")
	public ResponseEntity removeItem(@PathVariable("id") String cartId, @PathVariable("product_id") String productId) {
		Map<String, Object> map = new HashMap<>();
		map.put("cart", backendFeignClient.cartRemoveItem(cartId, productId));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@PostMapping("{id}/quantity")
	public ResponseEntity setProductQuantity(
			@PathVariable("id") String cartId, @RequestBody CartItem cartItem) {
		Map<String, Object> map = new HashMap<>();
		map.put("cart", backendFeignClient.cartSetProductQuantity(cartId, cartItem));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@PostMapping("{id}/order")
	public ResponseEntity createOrder(@PathVariable("id") String cartId, @RequestBody @Valid Order order) {
		Map<String, Object> map = new HashMap<>();
		map.put("cart", backendFeignClient.cartCreateOrder(cartId, order));
		return new ResponseEntity(map, HttpStatus.OK);
	}
}
