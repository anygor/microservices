package com.epam.productservice.services;

import com.epam.productservice.models.Product;
import com.epam.productservice.models.ProductGroup;
import com.epam.productservice.models.ProductImage;
import com.epam.productservice.repositories.GroupRepository;
import com.epam.productservice.repositories.ProductImageRepository;
import com.epam.productservice.repositories.ProductRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductImageRepository productImageRepository;

	@Autowired
	GroupRepository groupRepository;

	/* PRODUCT */
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	public Product getProduct(long id) {
		return productRepository.findById(id).get();
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public List<ProductImage> getAllProductImage(long id) {
		return productImageRepository.getAllById(id);
	}

	public ProductImage getProductImage(long id) {
		return productImageRepository.getById(id);
	}

	public ProductImage addProductImage(final String productId, final String filename) {

		//Session session = sessionFactory.openSession();
		//session.beginTransaction();
		ProductImage image = new ProductImage();
		long id = Long.parseLong(productId);
		image.setProductId(id);
		image.setPath(filename);
		//try {
		//    String res = session.save(image).toString();
		//    session.getTransaction().commit();
		//    return res;
		//} catch (HibernateException e) {
		//    System.out.print(e.getMessage());
		//    session.getTransaction().rollback();
		//} finally {
		//    session.close();
		//}
		return productImageRepository.save(image);
	}

	/* GROUPS */
	public List<ProductGroup> getGroups() {
		return groupRepository.findAll();
	}

	public ProductGroup getGroup(long id) {
		return groupRepository.findById(id).get();
	}

	public ProductGroup saveGroup(ProductGroup group) {
		return groupRepository.save(group);
	}

}
