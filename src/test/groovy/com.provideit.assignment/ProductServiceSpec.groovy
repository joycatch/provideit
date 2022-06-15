package com.provideit.assignment


import com.provideit.assignment.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ProductServiceSpec extends Specification {

    @Autowired
    ProductRepository productRepository

    def "should have fetched and populated DB with products once application context has been initialized"() {
        expect:
        productRepository.count() > 0
    }
}