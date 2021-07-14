package com.epam.mentoring.gateway.controller;

import com.epam.mentoring.gateway.feign.ProductFeignClient;
import com.epam.mentoring.gateway.models.ProductGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private Validator groupValidator;

	@Autowired
	private ProductFeignClient groupFeignClient;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(groupValidator);
	}

	@GetMapping
	public ResponseEntity index() {
		Map<String, Object> map = new HashMap<>();
		map.put("group", groupFeignClient.groupIndex());
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity view(@PathVariable("id") long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("group", groupFeignClient.groupView(id));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity<String> edit(@PathVariable("id") long id, @RequestBody @Valid ProductGroup group) {
		Map<String, Object> map = new HashMap<>();
		map.put("group", groupFeignClient.groupEdit(id, group));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> create(@RequestBody @Valid ProductGroup group) {
		Map<String, Object> map = new HashMap<>();
		map.put("group", groupFeignClient.groupCreate(group));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	// Todo: add delete method

}
