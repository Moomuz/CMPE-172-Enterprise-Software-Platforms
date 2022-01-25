package com.example.springgumball;

import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;


import lombok.extern.slf4j.Slf4j;

import com.example.gumballmachine.GumballMachine;

@Slf4j
@Controller
@RequestMapping("/")
public class GumballMachineController {
    // connects to GumballModelRepository
    @Autowired
    private GumballModelRepository repository;

    private static String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI";
    java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();

    // HMAC encyrption method
    private static byte[] hmac_sha256(String secretKey, String data) {
        try {
          Mac mac = Mac.getInstance("HmacSHA256") ;
          SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256") ;
          mac.init(secretKeySpec) ;
          byte[] digest = mac.doFinal(data.getBytes()) ;
          return digest ;
        } catch (InvalidKeyException e1) {
          throw new RuntimeException("Invalid key exception while converting to HMAC SHA256") ;
        } catch (NoSuchAlgorithmException e2) {
          throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm") ;
        }
    }

    @GetMapping
    public String getAction( @ModelAttribute("command") GumballCommand command, 
                            Model model, HttpSession session) {
      
        GumballModel g = new GumballModel() ;                                       // create new GumballModel object
        Optional<GumballModel> object = repository.findById("2134998871109");

        g.setModelNumber( "SB102927") ;                                             // set values in GumballModel
        g.setSerialNumber( "2134998871109") ;
        model.addAttribute( "gumball", g ) ;
        
        GumballMachine gm = new GumballMachine() ;                                  // create new GumballMachine object
        String message = gm.toString(g.getModelNumber(), g.getSerialNumber()) ;

        command.setState( gm.getState().getClass().getName() ) ;                    // set values in GumballCommand
        command.setTimestamp(String.valueOf(System.currentTimeMillis()));
        
        byte[] hash = hmac_sha256(key, command.getTimestamp());                     // hash with timestamp 
        String hashString = encoder.encodeToString(hash);  
        
        log.info("First Hash: " + hashString);                                      // log hash to terminal
        
        command.setHash(hashString);                                                // set hash in GumballCommand   

        String server_ip = "" ;                                                     // get localhost, IP Address, hostname
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
  
        } catch (Exception e) { }
  
        model.addAttribute( "hash", hashString ) ;                                  // change session ID to hash
        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;

        return "gumball" ;

    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") GumballCommand command,  
                            @RequestParam(value="action", required=true) String action,
                            Errors errors, Model model, HttpServletRequest request) {
    
        log.info( "Action: " + action ) ;
        log.info( "Command: " + command ) ;
        
        String commandState = command.getState();                               // get values from GumballCommand
        String commandHashString = command.getHash();
        String commandTimestamp = command.getTimestamp();
                            
        byte[] testHash = hmac_sha256(key, commandTimestamp.toString());        // make hash from GumballCommand
        String testHashString = encoder.encodeToString(testHash);  
        
        log.info("Test Hash: " + testHashString);                                // log GumballCommand hash to terminal    

        if (!commandHashString.equals(testHashString)) {                        // compare hashes to check for injection attack
            throw new RuntimeException("End");
        }
        
        long newTimestampLong = System.currentTimeMillis();                     // check if timestamp has a difference of greater than 1000 seconds
        long oldTimestampLong = Long.parseLong(commandTimestamp);

        if (((newTimestampLong - oldTimestampLong) / 1000) > 1000) {
            throw new RuntimeException("End");
        }

        GumballMachine gm = new GumballMachine();                               // create new GumballMachine object
        gm.setState(commandState);

        if ( action.equals("Insert Quarter") ) {                                // action for "Insert Quarter" button on webpage
            gm.insertQuarter() ;
        }

        Optional<GumballModel> object = repository.findById("2134998871109");   // create GumballModel object
        GumballModel model1 = new GumballModel();
        model1 = object.get(); 

        if ( action.equals("Turn Crank") ) {                                    // action for "Turn Crank" button on webpage
            command.setMessage("") ;
            gm.turnCrank() ;

            int prevCounter = model1.getCountGumballs();
            model1.setCountGumballs(prevCounter - 1);
            repository.save(model1);
        }     
        
        String message = gm.toString(model1.getModelNumber(), model1.getSerialNumber());                // sets text above gumball machien image on webpage

        command.setState(gm.getState().getClass().getName());                                           // set values in GumballCommand 
        command.setTimestamp(String.valueOf(System.currentTimeMillis()));

        String newHashString = encoder.encodeToString(hmac_sha256(key, command.getTimestamp()));
        command.setHash(newHashString);

        log.info("New Hash: " + newHashString);

        String server_ip = "" ;                                                                         // get localhost, IP Address, hostname
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
  
        } catch (Exception e) { }
  
        model.addAttribute( "hash", newHashString ) ;                                                   // change session ID to hash  
        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;
     

        if (errors.hasErrors()) {
            return "gumball";
        }

        return "gumball";
    }
}
