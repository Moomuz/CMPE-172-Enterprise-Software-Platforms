package com.example.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Index;

@Entity
@Table
@Data
@RequiredArgsConstructor
@Setter
@Getter
public class Customer {
    @Id @GeneratedValue(strategy=GenerationType.AUTO) private Integer id;
    
    @Column(nullable=false) private String firstname;
    @Column(nullable=false) private String lastname;
    @Column(nullable=false) private String email;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private boolean loggedIn;
    private String newpassword, oldpassword;
    private boolean hasPasswordRequest = false;
    private int rewardpoints;

    public boolean getHasPasswordRequest() {
        return this.hasPasswordRequest;
    }

}