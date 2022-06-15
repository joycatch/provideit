package com.provideit.assignment.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    public int id;

    @Column
    @JsonView(Views.ProductListing.class)
    public String title;

    @Column
    @JsonView(Views.ProductListing.class)
    public double price;

    @Column(columnDefinition = "TEXT")
    @JsonView(Views.ProductDetails.class)
    public String description;

    @Column
    @JsonView(Views.ProductListing.class)
    public String category;

    @Column
    @JsonView(Views.ProductListing.class)
    public String image;

    @Embedded
    public Rating rating;
}
