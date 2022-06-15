package com.provideit.assignment

import com.provideit.assignment.domain.Product
import com.provideit.assignment.gateway.ProductGateway
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class ProductGatewaySpec extends Specification {

    def exampleProduct1 = Product.builder().id(1).title("Product 1").build()
    def exampleProduct2 = Product.builder().id(2).title("Product 2").build()
    def listOfProducts = [exampleProduct1, exampleProduct2] as Product[]

    RestTemplate restTemplate = Mock()
    ProductGateway productsGateway = new ProductGateway(restTemplate)

    def "should return expected product from external source"() {
        given:
        restTemplate.getForEntity(_, Product.class) >> new ResponseEntity(exampleProduct1, HttpStatus.OK)

        when:
        def product = productsGateway.getProductById(1)

        then:
        product == exampleProduct1
    }

    def "should return expected amount of products from external source"() {
        given:
        restTemplate.getForEntity(_, Product[].class) >> new ResponseEntity(listOfProducts, HttpStatus.OK)

        when:
        def products = productsGateway.getAllProducts()

        then:
        products.size() == listOfProducts.length
    }
}