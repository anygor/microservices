package com.epam.mentoring.gateway.feign;

import com.epam.mentoring.gateway.models.CartItem;
import com.epam.mentoring.gateway.models.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@FeignClient(name = "backend-service")
public interface BackendFeignClient {
	@PostMapping("/cart")
	String cartCreate();

	@PostMapping("/cart/{id}")
	String cartAddProduct(@PathVariable("id") String cartId, @RequestBody CartItem cartItem);

	@GetMapping("/cart/{id}")
	Set<CartItem> cartGetCartItems(@PathVariable("id") String cartId);

	@DeleteMapping("/cart/{id}/{product_id}")
	String cartRemoveItem(@PathVariable("id") String cartId, @PathVariable("product_id") String productId);

	@PostMapping("/cart/{id}/quantity")
	String cartSetProductQuantity(@PathVariable("id") String cartId, @RequestBody CartItem cartItem);

	@PostMapping("/cart/{id}/order")
	Order cartCreateOrder(@PathVariable("id") String cartId, @RequestBody @Valid Order order);

	@RequestMapping(method = RequestMethod.GET, value = "/order")
	List<Order> orderIndex();

	@PostMapping("/order")
	Order orderCreate(@RequestBody @Valid Order order);

	@RequestMapping("/order/{id}")
	Order orderView(@PathVariable("id") long id);

	@RequestMapping(value = "/order/{id}", method = RequestMethod.POST)
	Order orderEdit(@PathVariable("id") long id, @RequestBody @Valid Order order);

}
