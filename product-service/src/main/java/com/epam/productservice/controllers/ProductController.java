package com.epam.productservice.controllers;

import com.epam.productservice.feign.RecommendationFeignClient;
import com.epam.productservice.feign.StatisticsFeignClient;
import com.epam.productservice.models.Product;
import com.epam.productservice.models.ProductImage;
import com.epam.productservice.models.User;
import com.epam.productservice.models.stats.StatisticsUnit;
import com.epam.productservice.services.ProductService;
import com.epam.productservice.storage.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private StorageService storageService;

	@Autowired
	Validator productValidator;

	@Autowired
	private RecommendationFeignClient recommendationFeignClient;

	@Autowired
	private StatisticsFeignClient statisticsFeignClient;

	private static final Logger logger = LogManager.getLogger(ProductController.class);

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(productValidator);
	}

	@GetMapping
	public List<Product> index() {
		return productService.getProducts();
	}

	@PostMapping
	public Product create(@RequestBody @Valid Product product) {
		return productService.saveProduct(product);
	}

	@GetMapping("/{id}")
	public Map<String, List> view(@PathVariable("id") long id) {
		String productKey = "product";
		Map<String, List> result = new HashMap<>();
		Product product = productService.getProduct(id);
		result.put(productKey, Stream.of(product).collect(Collectors.toList()));
		String recommendationsKey = "recommendations";

		List<Long> recommendedProductIds = recommendationFeignClient.getRecommendedProductIds(id);
		result.put(recommendationsKey, Stream.of(recommendedProductIds).collect(Collectors.toList()));

		statisticsFeignClient.addStats(new StatisticsUnit(null, product));
		List<StatisticsUnit> stats = statisticsFeignClient.getStats();
		return result;
	}


//	private Map<String, List> putRecommendationsOfProductToMap(@PathVariable("id") long id) {
//		String productKey = "product";
//		Map<String, List> result = new HashMap<>();
//		result.put(productKey, Stream.of(productService.getProduct(id)).collect(Collectors.toList()));
//		String recommendationsKey = "recommendations";
//
//		List<Long> recommendedProductIds = recommendationFeignClient.getRecommendedProductIds(id);
//		result.put(recommendationsKey, Stream.of(1L, 2L, 3L).collect(Collectors.toList()));
//
//		return result;
//	}

	@PostMapping(value = "/{id}")
	public Product edit(@PathVariable("id") long id, @RequestBody @Valid Product product) {

		Product updatedProduct = productService.getProduct(id);

		if (updatedProduct == null) {
			return null;
		}

		updatedProduct.setName(product.getName());
		updatedProduct.setPrice(product.getPrice());
		updatedProduct.setDescription(product.getDescription());

		return productService.saveProduct(updatedProduct);
	}

	@GetMapping("/{id}/images")
	public List<ProductImage> viewImages(@PathVariable("id") String productId) {
		//Session session = sessionFactory.openSession();
		//List<ProductImage> list =
		//    session
		//        .createQuery("FROM ProductImage WHERE product_id = :product_id")
		//        .setLong("product_id", Long.parseLong(productId))
		//        .list();
		//session.close();
		//return list;
		return productService.getAllProductImage(Long.parseLong(productId));
	}

	@GetMapping("/image/{id}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("id") String id) {

		ProductImage image = productService.getProductImage(Integer.parseInt(id));

		// Relative path to StorageProperties.rootLocation
		String path = "product-images/" + image.getProductId() + "/";

		Resource file = storageService.loadAsResource(path + image.getPath());
		String mimeType = "image/png";
		try {
			mimeType = file.getURL().openConnection().getContentType();
		} catch (IOException e) {
			System.out.println("Can't get file mimeType. " + e.getMessage());
		}
		return ResponseEntity.ok()
				//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;
				// filename=\""+file.getFilename()+"\"")
				.header(HttpHeaders.CONTENT_TYPE, mimeType)
				.body(file);
	}

	@PostMapping("/{id}/uploadimage")
	public ProductImage handleFileUpload(
			@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

		// Relative path to the rootLocation in storageService
		String path = "/product-images/" + id;
		String filename = storageService.store(file, path);

		return productService.addProductImage(id, filename);
	}

	// Todo: add delete method

}
