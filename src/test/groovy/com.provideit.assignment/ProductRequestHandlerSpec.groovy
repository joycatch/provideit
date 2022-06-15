package com.provideit.assignment

import com.provideit.assignment.error.InvalidRequestParameterCombinationException
import com.provideit.assignment.error.ProductNotFoundException
import com.provideit.assignment.handlers.ProductRequestHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static java.util.Optional.empty
import static java.util.Optional.of

@SpringBootTest
class ProductRequestHandlerSpec extends Specification {

    @Autowired
    ProductRequestHandler productsRequestHandler;

    def "should throw InvalidRequestParameterCombinationException when invalid combination of request parameters"() {
        when:
        productsRequestHandler.getAllProducts(page, category, min, max)

        then:
        thrown InvalidRequestParameterCombinationException

        where:
        page    | category       | min     | max
        empty() | empty()        | of(2)   | empty()
        of(1)   | empty()        | of(2)   | empty()
        empty() | empty()        | empty() | of(2)
        of(1)   | empty()        | empty() | of(2)
        empty() | of("jewelery") | empty() | of(2)
        empty() | of("jewelery") | of(2)   | empty()
        of(1)   | of("jewelery") | of(2)   | empty()
        of(1)   | of("jewelery") | empty() | of(2)
    }

    def "should throw ProductNotFoundException when trying to fetch a product with #productId"() {
        when:
        productsRequestHandler.getProductById(productId)

        then:
        thrown ProductNotFoundException

        where:
        productId << [-1, Integer.MIN_VALUE, Integer.MAX_VALUE] // Example dummy ids that should never exist
    }
}