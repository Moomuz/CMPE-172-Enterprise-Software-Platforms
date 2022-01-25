package com.example.springpayments;

import lombok.Data;
import lombok.RequiredArgsConstructor;

// additional imports
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Index;
import javax.persistence.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
// additional annotations
@Entity
@Table(name="Payments")
class PaymentsCommand {
    // id value
    private @Id @GeneratedValue Long id;

    // add transient to action
    transient private String action ;
    private String firstname ;
    private String lastname ;
    
    // add rest of form inputs
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phonenumber;
    private String creditcardnumber;
    private String expmonth;
    private String expyear;
    private String cvv;
    private String email;
    private String notes;

    // additional variables for CyberSource
    private String orderNumber;
    private String transactionAmount;
    private String transactionCurrency;
    private String authId;
    private String authStatus;
    private String captureId;
    private String captureStatus;
    private String cardtype;

}


