package com.example.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// for hash
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  // for hash
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

  @Bean
  CommandLineRunner initDatabase(CustomerRepository repository) {

    return args -> {
       Customer cus = new Customer();
       cus.setFirstname("Manager");
       cus.setLastname(" ");
       cus.setEmail("admin@gmail.com");

       String text = "adminwelcome";
       String hashString = hmac_sha256(key, text);
       cus.setPassword(hashString);

      log.info("Preloading " + repository.save(cus));
    };
  }
}