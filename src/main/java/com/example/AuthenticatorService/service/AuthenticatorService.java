package com.example.AuthenticatorService.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@Service
public class AuthenticatorService {

    @Value("${auth.url}")
    private String authUrl;

    private final RestTemplate restTemplate;

    public AuthenticatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject json = new JSONObject();
        json.put("username", "auth-vivelibre");
        json.put("password", "password");

        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(authUrl, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JSONObject responseJson = new JSONObject(response.getBody());
                return responseJson.getString("token");  // Assuming the token is in the "token" field
            } else {
                return "Error: Unexpected response status: " + response.getStatusCode();
            }
        } catch (HttpClientErrorException e) {
            return "Error: Client error - " + e.getMessage();
        } catch (HttpServerErrorException e) {
            return "Error: Server error - " + e.getMessage();
        } catch (ResourceAccessException e) {
            return "Error: Resource access error - " + e.getMessage();
        } catch (Exception e) {
            return "Error: An unexpected error occurred - " + e.getMessage();
        }
    }
}
