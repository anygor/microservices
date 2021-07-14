package com.epam.mentoring.cart;

import com.epam.mentoring.models.Order;
import com.epam.mentoring.models.OrderItem;
import com.epam.mentoring.services.EcommerceService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  @Autowired private EcommerceService ecommerceService;
  private final ConcurrentMap<String, Cart> cache = new ConcurrentHashMap<>();

  @Override
  public String createNewCart() {
    return UUID.randomUUID().toString();
  }

  @Override
  public void addProduct(String cartId, CartItem cartItem) {
    Cart cart = cache.getOrDefault(cartId, new Cart());
    if (cart.getItems() == null) {
      cart.setItems(new ArrayList<>());
    }
    cart.getItems().add(cartItem);
    cache.putIfAbsent(cartId, cart);
  }

  @Override
  public void removeProduct(String cartId, String productId) {
    CartItem itemToRemove = new CartItem();
    itemToRemove.setProductId(Long.parseLong(productId));
    Cart cart = cache.get(cartId);
    if (cart != null) {
      cart.getItems().remove(itemToRemove);
    }
  }

  @Override
  public void setProductQuantity(String cartId, String productId, int quantity) {
    Cart cart = cache.get(cartId);
    if (cart != null) {
      cart.getItems()
          .forEach(
              cartItem -> {
                if (cartItem.getProductId() == Long.parseLong(productId)) {
                  cartItem.setQuantity(quantity);
                }
              });
    }
  }

  @Override
  public Set<CartItem> getItems(String cartId) {
    Cart cart = cache.get(cartId);
    if (cart != null) {
      return new HashSet<>(cart.getItems());
    }
    return Collections.emptySet();
  }

  @Override
  public Order createOrder(String cartId, Order order) {
    order = addCartItemsToOrders(cache.get(cartId).getItems(), order);
    if (order == null) {
      System.out.println("Order not set.");
    }
    order = ecommerceService.saveOrder(order);
    return order;
  }

  private Order addCartItemsToOrders(List<CartItem> cartItems, Order order) {
    cartItems.forEach(
        cartItem -> {
          int qty = cartItem.getQuantity() > 0 ? cartItem.getQuantity() : 1;
          long variantId = cartItem.getVariantId();

          for (int i = 0; i < qty; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            if (variantId > 0) {
              orderItem.setGroupVariantId(variantId);
            }
            orderItem.setOrder(order);
            order.getItems().add(orderItem);
          }
        });

    return order;
  }
}
