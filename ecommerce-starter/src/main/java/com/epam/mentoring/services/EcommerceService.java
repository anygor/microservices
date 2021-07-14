package com.epam.mentoring.services;

import com.epam.mentoring.repositories.OrderRepository;
import com.epam.mentoring.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EcommerceService {

    @Autowired
    OrderRepository orderRepository;



    /* ORDERS */
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }
    public Order getOrder(long id){
        return orderRepository.findById(id).get();
    }
    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }
}
