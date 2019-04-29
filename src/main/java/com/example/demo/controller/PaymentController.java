package com.example.demo.controller;
//https://www.javainuse.com/spring/spring-boot-oauth-access-token

import com.example.demo.model.Products;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import com.example.demo.repository.ProductRepository;

import org.apache.commons.io.IOUtils;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;

//@RequestMapping("/oauth")

@RestController
@Controller
public class PaymentController {

    @RequestMapping(value = "/getEmployees", method = RequestMethod.GET)
    public ModelAndView getEmployeeInfo() {
        return new ModelAndView("getEmployees");
    }

    RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/showEmployees", method = RequestMethod.GET)

    public ModelAndView showEmployees(@RequestParam("code") String code) throws JsonProcessingException, IOException {
        ResponseEntity<String> response = null;
        System.out.println("Authorization Code------" + code);

        RestTemplate restTemplate = new RestTemplate();

//        // According OAuth documentation we need to send the client id and secret key in the header for authentication
        String credentials = "AX-S0XiawqbN3uwmA94PlGkeg3PQBy20Y7KrUBEC4t59HSZX-9L9Z3xNrwqwv3WslImC8h8TpFNd--3-:EH-8z3fcaHpNQcUoxPjSZ73-al-oa1zBUIo3V5KzFIjDtCnHsiYsZ5_HcJxGAlbFioWKa_RRelDckz78";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
        //String encodedCredentials = "EH-8z3fcaHpNQcUoxPjSZ73-al-oa1zBUIo3V5KzFIjDtCnHsiYsZ5_HcJxGAlbFioWKa_RRelDckz78";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept( Arrays.asList(MediaType.APPLICATION_JSON) );
        headers.add("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        String access_token_url = "https://api.sandbox.paypal.com/v1/oauth2/token";
        access_token_url += "?code=" + code;
        access_token_url += "&grant_type=authorization_code";

        access_token_url += "&redirect_uri= http://localhost:8085/showEmployees";

        response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);

        System.out.println("Access Token Response ---------" + response.getBody());

        return null;
    }

    @RequestMapping("/payment")
    public int test() {

        return 1;
    }

    @RequestMapping("/payment/")
    public String payment() {

        String command =
                "curl -X GET https://postman-echo.com/get?foo1=bar1&foo2=bar2";



        return "";
    }

}
