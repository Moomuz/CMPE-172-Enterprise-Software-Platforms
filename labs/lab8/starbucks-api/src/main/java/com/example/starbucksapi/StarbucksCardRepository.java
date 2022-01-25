package com.example.starbucksapi;

// https://spring.io/guides/tutorials/rest/

import org.springframework.data.jpa.repository.JpaRepository;

interface StarbucksCardRepository extends JpaRepository<StarbucksCard, Long> {
    // https://docs.spring.io/spring/data/jpa/docks/current/reference/html/#repositories.query-methods
    // https://docs.spring.io/spring/datadata-commons/docs/current/reference/html/#repositories.query-methods
    StarbucksCard findByCardNumber(String cardNumber);
}
