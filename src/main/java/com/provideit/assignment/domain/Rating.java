package com.provideit.assignment.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Rating {

    public double rate;
    public int count;

}
