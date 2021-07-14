package com.epam.mentoring.gateway.feign;

import com.epam.mentoring.gateway.models.Product;
import com.epam.mentoring.gateway.models.ProductGroup;
import com.epam.mentoring.gateway.models.ProductImage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {
    @GetMapping("/product")
    List<Product> productIndex();

    @PostMapping("/product")
    Product productCreate(@RequestBody @Valid Product product);

    @GetMapping("/product/{id}")
    Product productView(@PathVariable("id") long id);

    @PostMapping(value = "/product/{id}")
    Product productEdit(@PathVariable("id") long id, @RequestBody @Valid Product product);

    @GetMapping("/product/{id}/images")
    List<ProductImage> productViewImages(@PathVariable("id") String productId);

    @GetMapping("/product/image/{id}")
    @ResponseBody
    ResponseEntity<Resource> productServeFile(@PathVariable("id") String id);

    @PostMapping("/product/{id}/uploadimage")
    ProductImage productHandleFileUpload(
            @PathVariable("id") String id, @RequestParam("file") MultipartFile file);

    @GetMapping("/group")
    List<ProductGroup> groupIndex();

    @GetMapping("/group/{id}")
    ProductGroup groupView(@PathVariable("id") long id);

    @PostMapping("/group/{id}")
    ProductGroup groupEdit(
            @PathVariable(value = "id", required = false) long id,
            @RequestBody @Valid ProductGroup group);

    @PostMapping("/group")
    ProductGroup groupCreate(@RequestBody @Valid ProductGroup group);
}
