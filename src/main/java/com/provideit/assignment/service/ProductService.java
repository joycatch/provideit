package com.provideit.assignment.service;

import com.provideit.assignment.domain.Product;
import com.provideit.assignment.gateway.ProductGateway;
import com.provideit.assignment.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    public static final int PAGE_SIZE = 8;

    @Autowired
    ProductGateway productGateway;

    @Autowired
    ProductRepository productRepository;

    @PostConstruct
    public void fetchAndStoreAllProducts() {
        List<Product> products = new ArrayList<>();
        products.addAll(productGateway.getAllProducts());
        productRepository.saveAll(products);
    }

    public List<Product> getAllProducts(int page) {
        if (productRepository.count() == 0) {
            fetchAndStoreAllProducts();
        }
        return productRepository.findAll(PageRequest.of(page, PAGE_SIZE)).toList();
    }

    public List<Product> getProductsByMinAndMaxPrice(int page, double min, double max) {
        if (productRepository.count() == 0) {
            fetchAndStoreAllProducts();
        }
        return productRepository.findByMinAndMaxPrice(min, max, PageRequest.of(page, PAGE_SIZE)).toList();
    }

    public List<Product> getProductsByCategory(int page, String category) {
        if (productRepository.count() == 0) {
            fetchAndStoreAllProducts();
        }
        return productRepository.findByCategory(category, PageRequest.of(page, PAGE_SIZE)).toList();
    }

    public Product getProductById(int id) {
        Optional<Product> productFromRepository = productRepository.findById(id);
        if (productFromRepository.isPresent()) {
            return productFromRepository.get();
        }
        Product product = productGateway.getProductById(id);
        if (product != null) {
            productRepository.save(product);
        }
        return product;
    }
}
