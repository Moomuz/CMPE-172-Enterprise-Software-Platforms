package com.example.springgumball;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
class GumballModel {
    @Id
    private String serialNumber;
    private String modelNumber;
    private Integer countGumballs;

    GumballModel() {}

    GumballModel(String serialNumber, String modelNumber, Integer countGumballs) {
        this.serialNumber = serialNumber;
        this.modelNumber = modelNumber;
        this.countGumballs = countGumballs;
    }


  public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setCountGumballs(Integer countGumballs) {
        this.countGumballs = countGumballs;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getModelNumber() {
        return modelNumber;
    }
    
    public Integer getCountGumballs() {
        return countGumballs;
    }
}