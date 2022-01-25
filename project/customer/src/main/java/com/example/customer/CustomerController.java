package com.example.customer;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;


@RestController
public class CustomerController {
    
    @Autowired
    private CustomerRepository repository;
    private HashMap<String, Customer> customers;
    CustomerController(CustomerRepository repository) {
        this.repository = repository;
        customers = new HashMap<>();
    }

    class Message {
        private String status;
        public String getStatus() {
            return status;
        }
        public void setStatus(String msg) {
            status = msg;
        }
    }

    private static String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI" ;

    private String hmac_sha256(String secretKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(data.getBytes());
            java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
            String hash_string = encoder.encodeToString(digest);
            return hash_string;
        } catch (InvalidKeyException e1) {
            throw new RuntimeException("invalid key exception while converting to HMAC SHA256");
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm");
        }
    }

    //Ping method - check status of customer server
    @GetMapping(value={"/","/ping"})
    String home() {
        return "Welcome to Gong Cha! - Customer Management";
    }


//==================================================================================================
//Customer related calls
    //Get all customers
    @GetMapping("/customers")
    @CrossOrigin(origins = "*")
    public List<Customer> all() {
        return repository.findAll();
    }

    //Get all customers that has password request
    @GetMapping("/customers/requests")
    @CrossOrigin(origins = "*")
    List<Customer> allWithRequest() {
        List<Customer> requestList = new ArrayList<Customer>();
        for(Customer c: repository.findAll()) {
            if(c.getHasPasswordRequest())
                requestList.add(c);
        }
        return requestList;
    }

    //Create new Customer with user's sign up info
    @PostMapping("/customers/register")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*")
    public String newCustomer(@RequestBody Customer customer, HttpServletResponse response) {
        //check if someone with provided first and last name already has an account
        if(customers.get(customer.getFirstname()+customer.getLastname()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer already has an account!");
        }
        
        //if no old account is found, create a new one
        Customer newCustomer = new Customer();
        
        newCustomer.setFirstname(customer.getFirstname());
        newCustomer.setLastname(customer.getLastname());
        // newCustomer.setUsername(customer.getUsername());
        newCustomer.setEmail(customer.getEmail());

        // hash password before POSTING
        String text = customer.getPassword();
        String hashString = hmac_sha256(key, text);
        newCustomer.setPassword(hashString);
        
        //add to hashmap
        customers.put(newCustomer.getFirstname() + newCustomer.getLastname(), newCustomer);
        
        repository.save(newCustomer);
        return "Registration Successful";
    } 

    //Get specific Customer with first and last name
    @GetMapping("/customer/{firstname}/{lastname}")
    Customer getCustomer(@PathVariable String firstname, @PathVariable String lastname, HttpServletResponse response) {
        Customer active = customers.get(firstname.concat(lastname));
        if (active != null) {
            return active;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer Not Found!");
        }
    }

    //Delete Customer
    @DeleteMapping("/customer/{firstname}/{lastname}")
    Message deleteCustomer(@PathVariable String regid) {
        Customer active = customers.get(Integer.parseInt(regid));
        if (active != null) {
            customers.remove(regid);
            Message msg = new Message();
            msg.setStatus("Customer Removed");
            return msg;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer Not Found!");
        }
    }

    //Customer Login
    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    String activate(@RequestBody Customer customer, HttpServletResponse response){
        Customer email = repository.findByEmail(customer.getEmail());
        if(email == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Account Not Found!" );
        else {
            String text = customer.getPassword();
            String hashString = hmac_sha256(key, text);
            System.out.println(hashString);
            if(email.getPassword().equals(hashString) ){
                email.setLoggedIn(true);
                repository.save(email);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Invalid Password.");
            }

        }
        return "Login Successful";
    }

    //Customer Logout
    @PostMapping("/logout")
    @CrossOrigin(origins = "*")
    String logout(@RequestBody Customer customer, HttpServletResponse response){
        Customer email = repository.findByEmail(customer.getEmail());
        if(email.isLoggedIn()){
            email.setLoggedIn(false);
            repository.save(email);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Not logged in");
        return "Logout Successful";
    }


    //Request password reset
    @PostMapping("/customer/request/")
    @CrossOrigin(origins = "*")
    String requestPasswordChange(@RequestBody Customer customer, HttpServletResponse response) {
        
        Customer cus = repository.findByEmail(customer.getEmail());
        //data from user
        String oldpassword = hmac_sha256(key, customer.getOldpassword());
        String newpassword = hmac_sha256(key, customer.getNewpassword());
        //data from user
        if (newpassword==null || newpassword.isEmpty() || newpassword.equals(" ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input. Password must not be empty!");
        }
        if (newpassword.equals(cus.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from the current one!");
        }
        if (!oldpassword.equals(cus.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect current password input!");
        }
        else {
            cus.setNewpassword(newpassword);
            cus.setHasPasswordRequest(true);
            repository.save(cus);
            return "Password change requested.";
        }

    } 


    //Reset password, set hasPasswordRequest back to false
    @PostMapping("/customer/approve-request")
    @CrossOrigin(origins = "*")
    String changePassword(@RequestBody Customer customer, HttpServletResponse response) {
        Customer cus = repository.findByEmail(customer.getEmail());
        cus.setPassword(cus.getNewpassword());
        cus.setNewpassword("");
        cus.setHasPasswordRequest(false);
        repository.save(cus);
        return "Password for " + cus.getFirstname() + " " + cus.getLastname() + " successfully changed"; 
    }
    
}
