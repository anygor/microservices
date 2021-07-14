package com.epam.mentoring.gateway.controller;

import com.epam.mentoring.gateway.feign.ProductFeignClient;
import com.epam.mentoring.gateway.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private Validator productValidator;

	@Autowired
	private ProductFeignClient productFeignClient;

	private static final Logger logger = LogManager.getLogger(ProductController.class);

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(productValidator);
	}

	@GetMapping
	public ResponseEntity index() {
		Map<String, Object> map = new HashMap<>();
		map.put("product", productFeignClient.productIndex());
		return new ResponseEntity(map, HttpStatus.OK);
	}


	@PostMapping
	public ResponseEntity create(@RequestBody @Valid Product product) {
		Map<String, Object> map = new HashMap<>();
		map.put("product", productFeignClient.productCreate(product));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity view(@PathVariable("id") long id) {
		Map<String, Object> map = new HashMap<>();
		map.put("product", productFeignClient.productView(id));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@PostMapping(value = "/{id}")
	public ResponseEntity edit(@PathVariable("id") long id, @RequestBody @Valid Product product) {
		Map<String, Object> map = new HashMap<>();
		map.put("product", productFeignClient.productEdit(id, product));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@GetMapping("/{id}/images")
	public ResponseEntity viewImages(@PathVariable("id") String productId) {
		Map<String, Object> map = new HashMap<>();
		map.put("product", productFeignClient.productViewImages(productId));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@GetMapping("/image/{id}")
	@ResponseBody
	public ResponseEntity<String> serveFile(@PathVariable("id") String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("product", productFeignClient.productServeFile(id));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@PostMapping("/{id}/uploadimage")
	public ResponseEntity<String> handleFileUpload(
			@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

		//HttpHeaders headers = new HttpHeaders();
		//headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		//MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		//FileSystemResource fileSystemResource = FileConverter.multipartFileToFile(file);
		//body.add("file", fileSystemResource);
		//HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);
		//ResponseEntity<String> response = restTemplate.postForEntity(URL + "/" + id + "/uploadimage", request, String.class);

		Map<String, Object> map = new HashMap<>();
		map.put("product", productFeignClient.productHandleFileUpload(id, file));
		return new ResponseEntity(map, HttpStatus.OK);
	}

	// Todo: add delete method

}
