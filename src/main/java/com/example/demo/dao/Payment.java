package com.example.demo.dao;

import com.example.demo.model.Orders;
import com.example.demo.repository.OrdersRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

import org.apache.commons.codec.binary.Base64;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.example.demo.model.Orders;

public class Payment {

    public String papyal_access_token() {

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

            reader.close();

            System.out.println("Response code:" + conn.getResponseCode());
            System.out.println("Response message:" + conn.getResponseMessage());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("DAO access_token: " + "Bearer " + access_token);


        return access_token;
    }


    public JsonObject papyal_check_out(String access_token) {

        String line1= "111 First Street";
        String  city="Saratoga";
        String state= "CA";
        String postal_code= "95070";
        String country_code="US";

        String number="4417119669820331";
        String type= "visa";
        String expire_month= "11";
        String expire_year= "2021";
        String cvv2= "874";
        String first_name= "mar";
        String last_name= "killer";

        String payment_method= "credit_card";

        String subtotal= "8.41";
        String tax= "0.03";
        String shipping= "0.03";

        String total= "8.47";
        String  currency= "USD";


        JsonObject resJson= null;

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

            //payer
            JsonObject billing_address = new JsonObject();
            billing_address.addProperty("line1", line1);
            billing_address.addProperty("city", city);
            billing_address.addProperty("state", state);
            billing_address.addProperty("postal_code", postal_code);
            billing_address.addProperty("country_code", country_code);

            JsonObject credit = new JsonObject();
            credit.addProperty("number", number);
            credit.addProperty("type", type);
            credit.addProperty("expire_month", expire_month);
            credit.addProperty("expire_year", expire_year);
            credit.addProperty("cvv2", cvv2);
            credit.addProperty("first_name", first_name);
            credit.addProperty("last_name", last_name);
            credit.add("billing_address", billing_address);

            JsonObject credit_card = new JsonObject();
            credit_card.add("credit_card", credit);

            JsonArray funding_array = new JsonArray();
            funding_array.add( credit_card );

            JsonObject payer = new JsonObject();
            payer.addProperty("payment_method", payment_method);
            payer.add("funding_instruments", funding_array);

            //transactions
            JsonObject details = new JsonObject();
            details.addProperty("subtotal", subtotal);
            details.addProperty("tax", tax);
            details.addProperty("shipping", shipping);

            JsonObject amount = new JsonObject();
            amount.addProperty("total", total);
            amount.addProperty("currency", currency);
            amount.add("details", details);

            JsonObject trans = new JsonObject();
            trans.add("amount", amount);
            trans.addProperty("description", "This is the payment transaction description.");

            JsonArray trans_array = new JsonArray();
            trans_array.add( trans );

            //integrate
            JsonObject payInfo = new JsonObject();
            payInfo.addProperty("intent", "sale");
            payInfo.add("payer", payer);
            payInfo.add("transactions", trans_array);

            System.out.println("request json for payapl:" + payInfo +"\n");

            DataOutputStream output = new DataOutputStream(conn.getOutputStream());

            //output.writeBytes( cardJson.toString());
            output.writeBytes( payInfo.toString());

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
            System.out.println("payapl resposonse" + sb);

            resJson = new JsonParser().parse( sb.toString() ).getAsJsonObject();

            String resStr = resJson.toString().replaceAll("^\"|\"$", "");

            reader.close();

            System.out.println("Response code:" + conn.getResponseCode());
            System.out.println("Response message:" + conn.getResponseMessage());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("json:" + resJson.get("id") );


        return resJson;
    }


    public Orders save2sql(JsonObject resJson) {
        //default
        String order_id= "ch_1A8cGRDBxoAxoozUZHjrbDdeaasdsdfasdadccccccc";
        String agent_email= "dd@gmail.com";
        int order_completed= 0;
        String payment_type= "paypal";

        double total_price_before_tax= 8.41;
        double total_price_after_tax= 8.47;
        double tagent_price_after_tax= 8.46;

        String tour_type= "public(2h)";
        String date= "04/07/2019";
        String time= "9am";
        String customer_name= "di";
        String customer_lastname= "hooker";
        String customer_email= "di@gmail.com";
        String comment= "this guy is lazy";
        String setbarcode= "as4545sdfq324423sdf";

        int adult= 1;
        int child= 1;
        int total_people= 2;
        int served= 0;

        LocalDateTime created_at= LocalDateTime.now() ;
        LocalDateTime completed_at=  LocalDateTime.now() ;
        LocalDateTime served_date= LocalDateTime.now() ;

        //set value
        order_id= resJson.get("id").getAsString();

        String create_time= resJson.get("create_time").getAsString();

        //LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());

        System.out.println("create_time:" + create_time );


        if( resJson.get("state").getAsString().equals("approved"))  order_completed= 1;

        JsonObject payer= resJson.getAsJsonObject("payer");
        payment_type= payer.get("payment_method").getAsString();


        JsonArray funding_array = payer.getAsJsonObject().getAsJsonArray("funding_instruments");
        JsonObject credit_card= funding_array.get(0).getAsJsonObject().getAsJsonObject("credit_card");

        customer_name= credit_card.get("first_name").getAsString();
        customer_lastname= credit_card.get("last_name").getAsString();

        Orders row = new Orders() ;
        row.setOrder_id(order_id);
        row.setAgent_email(agent_email);
        row.setOrder_completed(order_completed);
        row.setPayment_type(payment_type);

        row.setTotal_price_before_tax(total_price_before_tax);
        row.setTotal_price_after_tax(total_price_after_tax);
        row.seTagent_price_after_tax(tagent_price_after_tax);

        row.setTour_type(tour_type);
        row.setDate(date);
        row.setTime(time);
        row.setCustomer_name(customer_name);
        row.setCustomer_lastname(customer_lastname);
        row.setCustomer_email(customer_email);
        row.setComment(comment);
        row.setbarcode(setbarcode);

        row.setAdult(adult);
        row.setChild(child);
        row.setTotal_people(total_people);
        row.setServed(served);

        row.setCreated_at( LocalDateTime.now() );
        row.setCompleted_at(  LocalDateTime.now() );
        row.setServed_date(  LocalDateTime.now() );


        return row;
    }

}







/*
https://stackoverflow.com/questions/31627733/transforming-a-paypal-curl-request-to-an-http-request-in-java    public String payment() {


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