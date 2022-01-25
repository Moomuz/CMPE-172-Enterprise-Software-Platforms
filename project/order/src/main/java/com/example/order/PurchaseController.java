package com.example.order;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


@RestController
public class PurchaseController {

    private PurchaseRepository repo;
    private List<Purchase> purchases;

    PurchaseController(PurchaseRepository repo) {
        this.repo = repo;
        purchases = new ArrayList<Purchase>();
    }

    //Ping method - check status of purchase server
    @CrossOrigin(origins = "*")
    @GetMapping(value={"/","/ping"})
    String home() {
        return "Welcome to Gong Cha - Purchase Management!";
    }


    //==================================================================================================
//Order related calls
    //Submit an order
    @PostMapping("/order/register")
    @CrossOrigin(origins = "*")
    @ResponseStatus(HttpStatus.CREATED)
    String newOrder( @RequestBody Purchase order) {
        if(purchases.isEmpty()) {
            purchases.add(order);
        }
        order.setOrderNumber(purchases.size());
        System.out.println("Placing Order (Reg ID =" + order.getOrderNumber() + ") => " + order);
        if (order.getDrink().equals("") || order.getDrinkSize().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Order Request!");
        }

        String milk = "";
        double price = 0.0;
        switch (order.getDrink()) {
        case "Green Tea":
            switch (order.getDrinkSize()) {
            case "Medium":
                price = 4.00;
                break;
            case "Large":
                price = 5.00;
                break;
            default:
                return "Invalid Size!";
            }
            break;
        case "Black Tea":
            switch (order.getDrinkSize()) {
            case "Medium":
                price = 4.25;
                break;
            case "Large":
                price = 5.00;
                break;
            default:
                return "Invalid Size!";
            }
            break;
        case "Thai Tea":
            switch (order.getDrinkSize()) {
            case "Medium":
                price = 4.50;
                break;
            case "Large":
                price = 5.00;
                break;
            default:
                return  "Invalid Size!";
            }
            break;
        default:
            return "Invalid Drink!";
        }
        switch(order.getMilk()){
            case "Milk":
                milk = "Milk";
                break;
            case "No Milk":
                milk = "No Milk";
                break;
            default:
                return "Invalid Milk!";
        }
        double rounded = Math.round(price*100.0)/100.0;
        order.setTotal(rounded);
        order.setMilk(milk);
        Purchase new_order = repo.save(order);

        purchases.add(order.getOrderNumber() , new_order);
        return "Successful Order! the order number is -" + order.getOrderNumber().toString()+"- and the cost is $"+
                order.getTotal();
             
    }

    //Get list of all orders
    @GetMapping("/orders")
    @CrossOrigin(origins = "*")
    List<Purchase> allOrders() {
        return repo.findAll();
    }

    //Get specific order with ID
    @GetMapping("/getorder")
    Purchase getActiveOrder(@RequestBody Integer orderNum, HttpServletResponse response) {
        Purchase active = purchases.get(orderNum);
        if (active != null) {
            return active;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order Not Found!");
        }
    }

}