package com.example.springpayments;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Optional;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

// import files from spring cybersource
import com.example.springcybersource.*;


@Slf4j
@Controller
@RequestMapping("/")
public class PaymentsController {  
    private static boolean DEBUG = true;

    // https://www.baeldung.com/spring-value-annotation
    // get cybersource information from applcation.properties and inject into class variables
    @Value("${cybersource.merchantkeyid}") 
    String merchantKeyId;
    @Value("${cybersource.merchantsecretkey}")
    String merchantSecretKey;
    @Value("${cybersource.merchantid}")
    String merchantId;
    @Value("${cybersource.apihost}")
    String apiHost;

    // create hashmap for months (full name is mapped to number)
    private static Map<String,String> months = new HashMap<>();
    static {
        months.put("January", "01");
        months.put("February", "02");
        months.put("March", "03");
        months.put("April", "04");
        months.put("May", "05");
        months.put("June", "06");
        months.put("July", "07");
        months.put("August", "08");
        months.put("September", "09");
        months.put("October", "10");
        months.put("November", "11");
        months.put("December", "12");
    }

    // create hashmap for 50 states (full state name to 2-letter initials)
    private static Map<String,String> states = new HashMap<>();
    static {
        states.put("AL", "Alabama");
        states.put("AK", "Alaska");
        states.put("AB", "Alberta");
        states.put("AZ", "Arizona");
        states.put("AR", "Arkansas");
        states.put("BC", "British Columbia");
        states.put("CA", "California");
        states.put("CO", "Colorado");
        states.put("CT", "Connecticut");
        states.put("DE", "Delaware");
        states.put("DC", "District Of Columbia");
        states.put("FL", "Florida");
        states.put("GA", "Georgia");
        states.put("GU", "Guam");
        states.put("HI", "Hawaii");
        states.put("ID", "Idaho");
        states.put("IL", "Illinois");
        states.put("IN", "Indiana");
        states.put("IA", "Iowa");
        states.put("KS", "Kansas");
        states.put("KY", "Kentucky");
        states.put("LA", "Louisiana");
        states.put("ME", "Maine");
        states.put("MB", "Manitoba");
        states.put("MD", "Maryland");
        states.put("MA", "Massachusetts");
        states.put("MI", "Michigan");
        states.put("MN", "Minnesota");
        states.put("MS", "Mississippi");
        states.put("MO", "Missouri");
        states.put("MT", "Montana");
        states.put("NE", "Nebraska");
        states.put("NV", "Nevada");
        states.put("NB", "New Brunswick");
        states.put("NH", "New Hampshire");
        states.put("NJ", "New Jersey");
        states.put("NM", "New Mexico");
        states.put("NY", "New York");
        states.put("NF", "Newfoundland");
        states.put("NC", "North Carolina");
        states.put("ND", "North Dakota");
        states.put("NT", "Northwest Territories");
        states.put("NS", "Nova Scotia");
        states.put("NU", "Nunavut");
        states.put("OH", "Ohio");
        states.put("OK", "Oklahoma");
        states.put("ON", "Ontario");
        states.put("OR", "Oregon");
        states.put("PA", "Pennsylvania");
        states.put("PE", "Prince Edward Island");
        states.put("PR", "Puerto Rico");
        states.put("QC", "Quebec");
        states.put("RI", "Rhode Island");
        states.put("SK", "Saskatchewan");
        states.put("SC", "South Carolina");
        states.put("SD", "South Dakota");
        states.put("TN", "Tennessee");
        states.put("TX", "Texas");
        states.put("UT", "Utah");
        states.put("VT", "Vermont");
        states.put("VI", "Virgin Islands");
        states.put("VA", "Virginia");
        states.put("WA", "Washington");
        states.put("WV", "West Virginia");
        states.put("WI", "Wisconsin");
        states.put("WY", "Wyoming");
        states.put("YT", "Yukon Territory");
    }

    // create new CyberSourceAPI object
    private CyberSourceAPI api = new CyberSourceAPI();

    // implicitly inject PaymentsCommandRepository 
    @Autowired
    private PaymentsCommandRepository repository;

    // Message class
    @Getter
    @Setter
    class Message {
        // private variable msg
        private String msg;

        // constructor
        public Message(String m) {
            msg = m;
        }
    }

    // ErrorMessages class
    class ErrorMessages {
        // private variable arraylist of message objects
        private ArrayList<Message> messages = new ArrayList<Message>();
        
        // add message object to arraylist messages
        public void add(String msg) {
            messages.add(new Message(msg));
        }

        // getter method for messages arraylist
        public ArrayList<Message> getMessages() {
            return messages;
        }

        // print each message in terminal
        public void print() {
            for(Message m : messages) {
                System.out.println(m.msg);
            }
        }
    }

    @GetMapping
    public String getAction( @ModelAttribute("command") PaymentsCommand command, 
                            Model model) {

        return "creditcards" ;

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") PaymentsCommand command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {
    
        log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;

        // inject cypbersource information from application.properties
        CyberSourceAPI.setHost(apiHost);
        CyberSourceAPI.setKey(merchantKeyId);
        CyberSourceAPI.setSecret(merchantSecretKey);
        CyberSourceAPI.setMerchant(merchantId);
        
        // prints out data from above onto terminal
        CyberSourceAPI.debugConfig();

        // create new ErrorMessages object 
        ErrorMessages msgs = new ErrorMessages();
    
        /* Check for Errors */
        // check for empty input
        boolean hasErrors = false;
        if(command.getFirstname().equals("")) {
            hasErrors = true;
            msgs.add("First Name Required.");
        }
        if(command.getLastname().equals("")) {
            hasErrors = true;
            msgs.add("Last Name Required.");
        }
        if(command.getAddress().equals("")) {
            hasErrors = true;
            msgs.add("Address Required.");
        }
        if(command.getCity().equals("")) {
            hasErrors = true;
            msgs.add("City Required.");
        }
        if(command.getState().equals("")) {
            hasErrors = true;
            msgs.add("State Required.");
        }
        if(command.getZip().equals("")) {
            hasErrors = true;
            msgs.add("Zip Required.");
        }
        if(command.getPhonenumber().equals("")) {
            hasErrors = true;
            msgs.add("Phone Required.");
        }
        if(command.getCreditcardnumber().equals("")) {
            hasErrors = true;
            msgs.add("Credit Card Number Required.");
        }
        if(command.getExpmonth().equals("")) {
            hasErrors = true;
            msgs.add("Expiration Month Required.");
        }
        if(command.getExpyear().equals("")) {
            hasErrors = true;
            msgs.add("Expiration Year Required.");
        }
        if(command.getCvv().equals("")) {
            hasErrors = true;
            msgs.add("CVV Required.");
        }
        if(command.getEmail().equals("")) {
            hasErrors = true;
            msgs.add("Email Required.");
        }

        // regex validation - right input format
        if(!command.getZip().matches("\\d{5}")) {
            hasErrors = true;
            msgs.add("Invalid Zip Code: " + command.getZip());
        }
        if(!command.getPhonenumber().matches("[(]\\d{3}[)] \\d{3}-\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Phone Number: " + command.getPhonenumber());
        }
        if(!command.getCreditcardnumber().matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Card Number: " + command.getCreditcardnumber());
        }
        if(!command.getExpyear().matches("\\d{4}")) {
            hasErrors = true;
            msgs.add("Invalid Expiration Year: " + command.getExpyear());
        }
        if(!command.getCvv().matches("\\d{3}")) {
            hasErrors = true;
            msgs.add("Invalid CVV: " + command.getCvv());
        }

        // validate whether month input is one of 12 months 
        if(months.get(command.getExpmonth()) == null) {
            hasErrors = true;
            msgs.add("Invalid Card Expiration Month: " + command.getExpmonth());
        }

        // validate whether state input is one of 50 states
        if(states.get(command.getState()) == null){
            hasErrors = true;
            msgs.add("Invalid State: " + command.getState());
        }

        // validate card type
        int cardFirstDigit = Integer.parseInt(command.getCreditcardnumber().substring(0, 1));

        if (cardFirstDigit == 4) {           // Visa 
            command.setCardtype("001");
        } 
        else if (cardFirstDigit == 5) {      // Mastercard
            command.setCardtype("002");
        } 
        else if(cardFirstDigit == 3) {       // American Express
            command.setCardtype("003");
        }
        else if (cardFirstDigit == 6) {      // Discover
            command.setCardtype("004");
        } 
        else {                               // Unsupported Card
            errors.reject("Unsupported Credit Card Type. Acceptable Credit Cards: Visa, Mastercard, Discover");
            msgs.add("Unsupported Credit Card Type. Acceptable Credit Cards: Visa, Mastercard, Discover");
        }

        // print error messages
        if(hasErrors) {
            msgs.print();
            model.addAttribute("messages", msgs.getMessages());
            return "creditcards";
        }

        /* process payment*/
        int min = 1239871;
        int max = 9999999;
        int randomInt = (int) Math.floor(Math.random() * (max - min + 1) + min);
        String orderNum = String.valueOf(randomInt);
        AuthRequest auth = new AuthRequest();
        auth.reference = orderNum;
        auth.billToFirstName = command.getFirstname();
        auth.billToLastName = command.getLastname();
        auth.billToAddress = command.getAddress();
        auth.billToCity = command.getCity();
        auth.billToState = command.getState();
        auth.billToZipCode = command.getZip();
        auth.billToPhone = command.getPhonenumber();
        auth.billToEmail = command.getEmail();
        auth.transactionAmount = "30.00";
        auth.transactionCurrency = "USD";
        auth.cardNumnber = command.getCreditcardnumber();
        auth.cardExpMonth = months.get(command.getExpmonth());
        auth.cardExpYear = command.getExpyear();
        auth.cardCVV = command.getCvv();
        auth.cardType = command.getCardtype();

        // if(auth.cardType.equals("ERROR")) {
        //     System.out.println("Unsupported Credit Card Type");
        //     model.addAttribute("message", "Unsupported Credit Card Type");
        //     return "creditcards";
        // }

        boolean authValid = true;
        AuthResponse authResponse = new AuthResponse();
        System.out.println("\n\nAuth Request: " +  auth.toJson());
        authResponse = api.authorize(auth);
        System.out.println("\n\nAuth Response: " + authResponse.toJson());
        if(!authResponse.status.equals("AUTHORIZED")) {
            authValid = false;
            System.out.println(authResponse.message);
            model.addAttribute("message", authResponse.message);
            return "creditcards";
        }

        boolean captureValid = true;
        CaptureRequest capture = new CaptureRequest();
        CaptureResponse captureResponse = new CaptureResponse();
        if(authValid) {
            capture.reference = orderNum;
            capture.paymentId = authResponse.id;
            capture.transactionAmount = "30.00";
            capture.transactionCurrency = "USD";
            System.out.println("\n\nCapture Request: " + capture.toJson());
            captureResponse = api.capture(capture);
            System.out.println("\n\nCapture Response: " + captureResponse.toJson());
            if(!captureResponse.status.equals("PENDING")) {
                captureValid = false;
                System.out.println(captureResponse.message);
                model.addAttribute("message", captureResponse.message);
                return "creditcards";
            }
        }

        /* Render View */
        if(authValid && captureValid) {
            command.setOrderNumber(orderNum);
            command.setTransactionAmount("30.00");
            command.setTransactionCurrency("USD");
            command.setAuthId(authResponse.id);
            command.setAuthStatus(authResponse.status);
            command.setCaptureId(captureResponse.id);
            command.setCaptureStatus(captureResponse.status);

            // save command
            repository.save(command);
        
            // send back status
            System.out.println("Thank You for Your Payment! Your Order Number is: " + orderNum);
            model.addAttribute( "message", "Thank You for Your Payment! Your Order Number is: " + orderNum ) ;
        }
        return "creditcards";
    }
}


