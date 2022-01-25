package com.example.springpayments;

import org.springframework.data.repository.CrudRepository;

import com.example.springpayments.PaymentsCommand;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PaymentsCommandRepository extends CrudRepository<PaymentsCommand, Integer> {

}
