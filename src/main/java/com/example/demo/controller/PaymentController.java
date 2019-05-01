package com.example.demo.controller;
//https://www.javainuse.com/spring/spring-boot-oauth-access-token

import com.example.demo.model.Products;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.URL;
import java.net.MalformedURLException;

import javax.net.ssl.HttpsURLConnection;


import com.example.demo.repository.ProductRepository;

//import org.apache.commons.io.IOUtils;

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


import java.io.InputStream;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;



@RestController
@Controller
public class PaymentController {


    @RequestMapping("/test/token")
    public String get_access_token() {
        String username = "AX-S0XiawqbN3uwmA94PlGkeg3PQBy20Y7KrUBEC4t59HSZX-9L9Z3xNrwqwv3WslImC8h8TpFNd--3-";
        String password = "EH-8z3fcaHpNQcUoxPjSZ73-al-oa1zBUIo3V5KzFIjDtCnHsiYsZ5_HcJxGAlbFioWKa_RRelDckz78";

        String access_token= null;
        try {
            String tokenInput = "grant_type=client_credentials";
            System.setProperty("https.protocols", "TLSv1.1,TLSv1.2,SSLv3,SSLv2Hello");
            java.net.URL url = new java.net.URL("https://api.sandbox.paypal.com/v1/oauth2/token");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();

            String credentials = username + ":" + password;

            String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(tokenInput.length()));
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Language", "en_US");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            java.io.DataOutputStream output = new java.io.DataOutputStream(conn.getOutputStream());
            output.write(tokenInput.getBytes());
            output.close();

            // Read the response:
            StringBuilder sb = new StringBuilder();
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JsonObject oJson = new JsonParser().parse( sb.toString()).getAsJsonObject();
            access_token= oJson.get("access_token").toString();
            access_token = access_token.replaceAll("^\"|\"$", "");

            System.out.println("Json:" + access_token);

            reader.close();

            System.out.println("Response code:" + conn.getResponseCode());
            System.out.println("Response message:" + conn.getResponseMessage());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "access token" + access_token;
    }

    @RequestMapping("/payment")
    //https://stackoverflow.com/questions/31627733/transforming-a-paypal-curl-request-to-an-http-request-in-java

    public String checkout() {

        //---------------------------------------get access token----------------------------
        String username = "AX-S0XiawqbN3uwmA94PlGkeg3PQBy20Y7KrUBEC4t59HSZX-9L9Z3xNrwqwv3WslImC8h8TpFNd--3-";
        String password = "EH-8z3fcaHpNQcUoxPjSZ73-al-oa1zBUIo3V5KzFIjDtCnHsiYsZ5_HcJxGAlbFioWKa_RRelDckz78";

        String receipt= null;
        String access_token= null;

        try {
            String tokenInput = "grant_type=client_credentials";
            System.setProperty("https.protocols", "TLSv1.1,TLSv1.2,SSLv3,SSLv2Hello");
            java.net.URL url = new java.net.URL("https://api.sandbox.paypal.com/v1/oauth2/token");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();

            String credentials = username + ":" + password;

            String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(tokenInput.length()));
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Accept-Language", "en_US");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            java.io.DataOutputStream output = new java.io.DataOutputStream(conn.getOutputStream());
            output.write(tokenInput.getBytes());
            output.close();

            // Read the response:
            StringBuilder sb = new StringBuilder();
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JsonObject oJson = new JsonParser().parse( sb.toString()).getAsJsonObject();
            access_token= oJson.get("access_token").toString();

            access_token = access_token.replaceAll("^\"|\"$", "");

            reader.close();

            System.out.println("Response code:" + conn.getResponseCode());
            System.out.println("Response message:" + conn.getResponseMessage());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("access_token: " + "Bearer " + access_token);

        //--------------------------check out-----------------------------------------
        try{

            URL url = new URL("https://api.sandbox.paypal.com/v1/payments/payment");

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String t= "A21AAEjehr1nLau0IYzqMFW_B2Zi-pWRS6397uuhSfqXCYm2R5NNz7DYnR02TzN60CmSno52Eh-odd_9IyWZHd4RqbQUD-1ag";
            //t= access_token;
            conn.setRequestProperty("Authorization", "Bearer "+ access_token);
            //conn.setRequestProperty("Authorization", "Bearer " + access_token);
            conn.setRequestProperty("Content-Type", "application/json");

            //String jsonData = "{ \"intent\":\"sale\",\"redirect_urls\":{\"return_url\":\"http://example.com/your_redirect_url.html\",\"cancel_url\":\"http://example.com/your_cancel_url.html\"},\"payer\":{\"payment_method\":\"paypal\"},\"transactions\":[{\"amount\":{\"total\":\"7.47\",\"currency\":\"USD\"}}]}";


            JsonObject details = new JsonObject();
            details.addProperty("subtotal", "8.41");
            details.addProperty("tax", "0.03");
            details.addProperty("shipping", "0.03");

            JsonObject amount = new JsonObject();
            amount.addProperty("total", "8.47");
            amount.addProperty("currency", "USD");
            amount.add("details", details);



            System.out.println("created new json:" + amount );


            JsonObject oJson= null;

            String cardStr = "{\n" +
                    "  \"intent\":\"sale\",\n" +
                    "  \"payer\":{\n" +
                    "    \"payment_method\":\"credit_card\",\n" +
                    "    \"funding_instruments\":[\n" +
                    "      {\n" +
                    "        \"credit_card\":{\n" +
                    "          \"number\":\"4417119669820331\",\n" +
                    "          \"type\":\"visa\",\n" +
                    "          \"expire_month\":11,\n" +
                    "          \"expire_year\":2021,\n" +
                    "          \"cvv2\":\"874\",\n" +
                    "          \"first_name\":\"Betsy\",\n" +
                    "          \"last_name\":\"Buyer\",\n" +
                    "          \"billing_address\":{\n" +
                    "            \"line1\":\"111 First Street\",\n" +
                    "            \"city\":\"Saratoga\",\n" +
                    "            \"state\":\"CA\",\n" +
                    "            \"postal_code\":\"95070\",\n" +
                    "            \"country_code\":\"US\"\n" +
                    "          }\n" +
                    "        }\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"transactions\":[\n" +
                    "    {\n" +
                    "      \"amount\":{\n" +
                    "        \"total\":\"8.47\",\n" +
                    "        \"currency\":\"USD\",\n" +
                    "        \"details\":{\n" +
                    "          \"subtotal\":\"8.41\",\n" +
                    "          \"tax\":\"0.03\",\n" +
                    "          \"shipping\":\"0.03\"\n" +
                    "        }\n" +
                    "      },\n" +
                    "      \"description\":\"This is the payment transaction description.\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            JsonObject cardJson = new JsonParser().parse( cardStr ).getAsJsonObject();


            String intent= "sale";

            cardJson.addProperty("intent", intent);


            System.out.println("credit card json:" + cardJson );
            // Post the data:
            DataOutputStream output = new DataOutputStream(conn.getOutputStream());
            output.writeBytes( cardJson.toString());

            //output.writeBytes(jsonData);
            output.close();

            // Read the response:
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println(sb);

            oJson = new JsonParser().parse( sb.toString() ).getAsJsonObject();

            receipt = oJson.toString().replaceAll("^\"|\"$", "");

            reader.close();

            System.out.println("Response code:" + conn.getResponseCode());
            System.out.println("Response message:" + conn.getResponseMessage());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



        return receipt;
    }

}







/*
    @RequestMapping("/payment/")
    public String payment() {


        String s= "cURL ";

        try {
            String command = "\"curl -v https://api.sandbox.paypal.com/v1/oauth2/token \\\\\\n\" +\n" +
                    "                    \"   -H \\\"Accept: application/json\\\" \\\\\\n\" +\n" +
                    "                    \"   -H \\\"Accept-Language: en_US\\\" \\\\\\n\" +\n" +
                    "                    \"   -u \\\"AX-S0XiawqbN3uwmA94PlGkeg3PQBy20Y7KrUBEC4t59HSZX-9L9Z3xNrwqwv3WslImC8h8TpFNd--3-:EH-8z3fcaHpNQcUoxPjSZ73-al-oa1zBUIo3V5KzFIjDtCnHsiYsZ5_HcJxGAlbFioWKa_RRelDckz78\\\" \\\\\\n\" +\n" +
                    "                    \"   -d \\\"grant_type=client_credentials\\\" \\\\\\n\" +\n" +
                    "                    \"   -o text.txt\"";


            Process p =Runtime.getRuntime().exec("curl http://18.216.211.37:8090/api/products -o text.txt");

            //p.waitFor();


            InputStream in = p.getInputStream();

            if( in.available()==0){
                return "Nothing of InputStream";
            }

            for (int i = 0; i < in.available(); i++) {
                s += " + ";

                System.out.println("" + in.read());
            }

            s += "  end";

            return s;


        } catch (IOException ex) {
            return "something wrong";

        }


        //return s;
    }

}



curl -v https://api.sandbox.paypal.com/v1/oauth2/token \
   -H "Accept: application/json" \
   -H "Accept-Language: en_US" \
   -u "AX-S0XiawqbN3uwmA94PlGkeg3PQBy20Y7KrUBEC4t59HSZX-9L9Z3xNrwqwv3WslImC8h8TpFNd--3-:EH-8z3fcaHpNQcUoxPjSZ73-al-oa1zBUIo3V5KzFIjDtCnHsiYsZ5_HcJxGAlbFioWKa_RRelDckz78" \
   -d "grant_type=client_credentials" \
   -o text.txt

 */