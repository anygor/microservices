package com.epam.mentoring.gateway.controller;

import com.epam.mentoring.gateway.feign.BackendFeignClient;
import com.epam.mentoring.gateway.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private BackendFeignClient backendFeignClient;

	@GetMapping
	public ResponseEntity index() {
		Map<String, Object> map = new HashMap<>();
		map.put("order", backendFeignClient.orderIndex());
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity create(@RequestBody @Valid Order order) {
		Map<String, Object> map = new HashMap<>();
		map.put("order", backendFeignClient.orderCreate(order));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@RequestMapping("/{id}")
	public ResponseEntity view(@PathVariable("id") long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("order", backendFeignClient.orderView(id));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity edit(@PathVariable("id") long id, @RequestBody @Valid Order order) {
		Map<String, Object> map = new HashMap<>();
		map.put("order", backendFeignClient.orderEdit(id, order));
		return new ResponseEntity(map, HttpStatus.OK);
	}
}
