package com.example.starbucksapi;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
//import javax.persistence.GeneratedType;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * https://spring.io/guides/tutorials/rest/
 * https://docs.spring.io/spring-data/data-commons/docs/current/reference/html/#mapping.fundamentals
 * https://www.baeldung.com/jpa-indexes
 */

@Entity
// @Table(indexes = @Index(name = "altIndex", columnList = "orderNumber", unique
// = true))
@Data
@RequiredArgsConstructor
public class StarbucksOrder {
    private @Id @GeneratedValue Long id;
    @Column(nullable = false)
    private String drink;
    @Column(nullable = false)
    private String milk;
    @Column(nullable = false)
    private String size;
    private double total;
    private String status;
}