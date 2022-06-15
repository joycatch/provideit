package com.provideit.assignment.error;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(int id) {
        super("Could not find product with id " + id);
    }
}
