package com.provideit.assignment.gateway;

import com.provideit.assignment.domain.Product;
import com.provideit.assignment.error.ProductNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductGateway {

    private RestTemplate restTemplate;

    private static final String BASE_URL = "https://fakestoreapi.com";

    public List<Product> getAllProducts() {
        String url = BASE_URL + "/products";
        try {
            ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            log.error("Could not fetch products from " + url + ". Returning an empty list");
            return Collections.emptyList();
        }
    }

    public Product getProductById(int id) {
        String url = BASE_URL + "/products/" + id;
        try {
            ResponseEntity<Product> response = restTemplate.getForEntity(url, Product.class);
            Product product = response.getBody();
            if (product == null) {
                throw new ProductNotFoundException(id);
            }
            return product;
        } catch (Exception e) {
            log.error("Could not fetch product with id " + id + " from " + url);
            throw new ProductNotFoundException(id);
        }
    }
}
