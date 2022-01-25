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

import com.example.gumballmachine.GumballMachine;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/")
public class GumballMachineController {

    @GetMapping
    public String getAction( @ModelAttribute("command") GumballCommand command, 
                            Model model, HttpSession session) {
      
        GumballModel g = new GumballModel() ;
        g.setModelNumber( "SB102927") ;
        g.setSerialNumber( "2134998871109") ;
        model.addAttribute( "gumball", g ) ;
        
        GumballMachine gm = new GumballMachine() ;
        String message = gm.toString() ;
        session.setAttribute( "gumball", gm) ;
        String session_id = session.getId() ;

        command.setState( gm.getState().getClass().getName() ) ;

        // hash with timestamp because each action occurs at different timestamp (prevents has collision)
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        // get byte[] from hmac_sha256() method
        String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI" ;
        byte[] hash = hmac_sha256(key, timestamp.toString());

        // encode byte{} to string
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder() ;
        String hashString = encoder.encodeToString(hash);  
        
        // set hash in GumballCommand.java
        command.setHash(hashString);                   

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
  
        } catch (Exception e) { }
  
        // change session ID to hash
        model.addAttribute( "hash", hashString ) ;
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
    
        HttpSession session = request.getSession() ;
        GumballMachine gm = (GumballMachine) session.getAttribute("gumball") ;

        if ( action.equals("Insert Quarter") ) {
            gm.insertQuarter() ;
        }

        if ( action.equals("Turn Crank") ) {
            command.setMessage("") ;
            gm.turnCrank() ;
        } 

        // hash with timestamp because each action occurs at different timestamp (prevents has collision)
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // get byte[] from hmac_sha256() method
        String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI" ;
        byte[] hash = hmac_sha256(key, timestamp.toString());

        // encode byte{} to string
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder() ;
        String hashString = encoder.encodeToString(hash);  
        
        // set hash in GumballCommand.java
        command.setHash(hashString);                   

        session.setAttribute( "gumball", gm) ;
        String message = gm.toString() ;
        String session_id = session.getId() ;        

        String server_ip = "" ;
        String host_name = "" ;
        try { 
            InetAddress ip = InetAddress.getLocalHost() ;
            server_ip = ip.getHostAddress() ;
            host_name = ip.getHostName() ;
  
        } catch (Exception e) { }
  
        // change session ID to hash
        model.addAttribute( "hash", hashString ) ;
        model.addAttribute( "message", message ) ;
        model.addAttribute( "server",  host_name + "/" + server_ip ) ;
     

        if (errors.hasErrors()) {
            return "gumball";
        }

        return "gumball";
    }

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
}
