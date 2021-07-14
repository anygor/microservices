package com.epam.productservice.repositories;

import com.epam.productservice.models.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productImageRepository")
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    public List<ProductImage> getAllById(long id);
    public ProductImage getById(long id);
    public ProductImage save(ProductImage productImage);
}
