package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.model.Product;
import com.cts.repository.ProductDetailssRepo;

@Service("productDetailsService")
public class ProductDetailsService {
	
	@Autowired
	ProductDetailssRepo repo;

	public String addItem(Product pro) {
		return repo.addItem(pro);
	}
	public String removeItem(String id){
		return repo.removeItem(id);
	}
	public List<Product> getAllProducts() {
		return repo.getAllProducts();
	}
	public Product getProductById(String productId) {
		return repo.getProductById(productId);
	}
}
