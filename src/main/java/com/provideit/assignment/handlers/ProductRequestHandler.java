package com.provideit.assignment.handlers;

import com.fasterxml.jackson.annotation.JsonView;
import com.provideit.assignment.domain.Product;
import com.provideit.assignment.domain.Views;
import com.provideit.assignment.error.InvalidRequestParameterCombinationException;
import com.provideit.assignment.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class ProductRequestHandler {

    @Autowired
    ProductService productService;

    @JsonView(Views.ProductListing.class)
    @RequestMapping("/products")
    public List<Product> getAllProducts(@RequestParam Optional<Integer> page, @RequestParam Optional<String> category,
                                        @RequestParam Optional<Double> min, @RequestParam Optional<Double> max) {
        int pageNumber = page.isPresent() ? page.get() : 0;
        validateRequestParameterCombination(category, min, max);
        if (min.isPresent() && max.isPresent()) {
            return productService.getProductsByMinAndMaxPrice(pageNumber, min.get(), max.get());
        }
        if (category.isPresent()) {
            return productService.getProductsByCategory(pageNumber, category.get());
        }
        return productService.getAllProducts(pageNumber);
    }

    @JsonView(Views.ProductDetails.class)
    @RequestMapping("/products/{productId}")
    public Product getProductById(@PathVariable(value="productId") Integer id) {
        return productService.getProductById(id);
    }

    private void validateRequestParameterCombination(Optional<String> category, Optional<Double> min, Optional<Double> max) {
        if (category.isPresent() && (min.isPresent() || max.isPresent())) {
            throw new InvalidRequestParameterCombinationException("Provide either a category or min and max request parameters");
        }
        if ((min.isPresent() && !max.isPresent()) || (!min.isPresent() && max.isPresent())) {
            throw new InvalidRequestParameterCombinationException("Provide both a min and a max request parameter");
        }
    }
}
