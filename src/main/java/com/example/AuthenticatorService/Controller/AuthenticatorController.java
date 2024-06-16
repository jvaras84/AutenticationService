package com.example.AuthenticatorService.Controller;

import com.example.AuthenticatorService.service.AuthenticatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticatorController {

    @Autowired
    private AuthenticatorService authService;

    @GetMapping("/get-token")
    public ResponseEntity<Map<String, String>>  getToken() {
    	   String token = authService.getToken();
    	
           if (token.startsWith("Error:")) {
               Map<String, String> errorResponse = new HashMap<>();
               errorResponse.put("error", token);
               return ResponseEntity.<Map<String, String>>status(HttpStatus.INTERNAL_SERVER_ERROR)
                       .body(errorResponse);
           }

           String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

           Map<String, String> response = new HashMap<>();
           response.put("auth-vivelibre-token", token);
           response.put("date", date);
           return ResponseEntity.<Map<String, String>>status(HttpStatus.OK)
                   .body(response);
    }
}
