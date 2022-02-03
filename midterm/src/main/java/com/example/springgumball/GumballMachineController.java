package com.example.springgumball;

import javax.validation.Valid;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

import com.example.gumballmachine.GumballMachine;

@Slf4j
@Controller
@RequestMapping("/")
public class GumballMachineController {

    private static String key = "kwRg54x2Go9iEdl49jFENRM12Mp711QI";

    java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();

    private static byte[] hmac_sha256(String secretKey, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(data.getBytes());
            return digest;
        } catch (InvalidKeyException e1) {
            throw new RuntimeException("Invalid key exception while converting to HMAC SHA256");
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Java Exception Initializing HMAC Crypto Algorithm");
        }
    }

    @Autowired
    private GumballModelRepository repository;

    @GetMapping
    public String getAction(@ModelAttribute("command") GumballCommand command, Model model, HttpSession session) {
        GumballModel g = new GumballModel();
        Optional<GumballModel> object = repository.findById("2134998871109");

        g = object.get();

        model.addAttribute("gumball", g);
        
        GumballMachine gm = new GumballMachine();
        String message = gm.toString(g.getModelNumber(), g.getSerialNumber());

        command.setState(gm.getState().getClass().getName());

        command.setTimestamp(String.valueOf(System.currentTimeMillis()));

        // hash the text of state + timestamp with the provided key
        String text = command.getState() + command.getTimestamp();
        String hashedText = encoder.encodeToString(hmac_sha256(key, text));

        command.setHash(hashedText);

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();

        } catch (Exception e) {
        }

        model.addAttribute("hash", hashedText);
        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        return "gumball";
    }

    @PostMapping
    public String postAction(@Valid @ModelAttribute("command") GumballCommand command,
            @RequestParam(value = "action", required = true) String action, Errors errors, Model model,
            HttpServletRequest request) throws Exception {

        log.info("Action: " + action);
        log.info("Command: " + command);

        String state = command.getState();
        String hash = command.getHash();
        String timestamp = command.getTimestamp();

        String textField = state + timestamp;
        String newHash = encoder.encodeToString(hmac_sha256(key, textField));

        log.info("Input Hash: " + hash);
        log.info("Calculated Hash:" + newHash);

        // compare input hash versus the hash right now to validate and prevent attacks
        if (!hash.equals(newHash)) {
            throw new Exception("End");
        }

        // check if timestamp has a difference of greater than 1000 seconds
        long newTimestamp = System.currentTimeMillis();
        long oldTimestamp = Long.parseLong(timestamp);

        if (((newTimestamp - oldTimestamp) / 1000) > 1000) {
            throw new RuntimeException("End");
        }

        GumballMachine gm = new GumballMachine();
        gm.setState(state);

        if (action.equals("Insert Quarter")) {
            gm.insertQuarter();
        }

        Optional<GumballModel> object = repository.findById("2134998871109");
        GumballModel model1 = new GumballModel();
        model1 = object.get(); 

        if (action.equals("Turn Crank")) {
            command.setMessage("");
            gm.turnCrank();
        
            int prevCounter = model1.getCountGumballs();
            model1.setCountGumballs(prevCounter - 1);
            repository.save(model1);
        }

        String message = gm.toString(model1.getModelNumber(), model1.getSerialNumber());

        command.setState(gm.getState().getClass().getName());
        command.setTimestamp(String.valueOf(System.currentTimeMillis()));

        String text = command.getState() + command.getTimestamp();
        String hashedText = encoder.encodeToString(hmac_sha256(key, text));

        command.setHash(hashedText);

        String server_ip = "";
        String host_name = "";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            server_ip = ip.getHostAddress();
            host_name = ip.getHostName();

        } catch (Exception e) {
        }

        model.addAttribute("hash", hashedText);
        model.addAttribute("message", message);
        model.addAttribute("server", host_name + "/" + server_ip);

        if (errors.hasErrors()) {
            return "gumball";
        }

        return "gumball";
    }

}