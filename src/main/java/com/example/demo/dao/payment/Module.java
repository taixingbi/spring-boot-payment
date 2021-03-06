package com.example.demo.dao.payment;

import com.example.demo.dao.payment.ErrorHandling;


import com.example.demo.model.Pos_rents_orders;
import com.example.demo.model.Pos_tours_orders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Module {
    private static final Logger logger = LogManager.getLogger(Module.class);
    boolean live= false;//live : sandbox
    boolean test= true;

    ErrorHandling errorHandling= new ErrorHandling();

    public JsonObject paypalJson( String resquestStr) {
        JsonObject resquestJson= new JsonParser().parse(resquestStr).getAsJsonObject();;
        //return jsonObjGetKeyAsObj(resquestJson, "paypal");
        return resquestJson.get("paypal").getAsJsonObject();
    }

    public JsonObject pos_rents_ordersJson( String resquestStr){
        JsonObject resquestJson= new JsonParser().parse(resquestStr).getAsJsonObject();;
        //return jsonObjGetKeyAsObj(resquestJson, "pos_rents_orders");
        return resquestJson.get("pos_rents_orders").getAsJsonObject();
    }

    public JsonObject pos_tours_ordersJson( String resquestStr){
        JsonObject resquestJson= new JsonParser().parse(resquestStr).getAsJsonObject();;
        //return jsonObjGetKeyAsObj(resquestJson, "pos_rents_orders");
        return resquestJson.get("pos_tours_orders").getAsJsonObject();
    }

    public String papyal_access_token() {

        //sanbox
        String username = "AX-S0XiawqbN3uwmA94PlGkeg3PQBy20Y7KrUBEC4t59HSZX-9L9Z3xNrwqwv3WslImC8h8TpFNd--3-";
        String password = "EH-8z3fcaHpNQcUoxPjSZ73-al-oa1zBUIo3V5KzFIjDtCnHsiYsZ5_HcJxGAlbFioWKa_RRelDckz78";
        //live
        if(live){
            username = "ATkdjMio7RqlUYJV80NAQbrSFjVP9GUCvDlXrKpL-myiwc4HKQRTnKzdmsoTMFGVDS2Ik3k_l4-gweFH";
            password = "ELia830Mmc_Ws4F8dLw3L_Q4E3eH8Cw1nZRAz4eZngdoLtE80DsoN6UjdH7K_9r24_wxWJG9ku7u4nMs";
        }

        String access_token= null;

        try {
            String tokenInput = "grant_type=client_credentials";
            System.setProperty("https.protocols", "TLSv1.1,TLSv1.2,SSLv3,SSLv2Hello");

            String urlToken= "https://api.sandbox.paypal.com/v1/oauth2/token";//sanbox
            if(live)
                urlToken= "https://api.paypal.com/v1/oauth2/token"; //live

            java.net.URL url = new java.net.URL(urlToken);
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
            e.printStackTrace();
            logger.error( "can not get access token ", e);
            String m= "can not get access token ";

            if(!test) m= errorHandling.System500;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m, e);
        }

        logger.info("DAO access_token: " + "Bearer " + access_token);

        return access_token;
    }


    public String paypal_placement(String access_token, JsonObject paypalJson) {

        String number = paypalJson.get("number").getAsString();
        String type = paypalJson.get("type").getAsString();
        String expire_month = paypalJson.get("expire_month").getAsString();
        String expire_year = paypalJson.get("expire_year").getAsString();
        String cvv2 = paypalJson.get("cvv2").getAsString();
        String first_name = paypalJson.get("first_name").getAsString();
        String last_name = paypalJson.get("last_name").getAsString();

        String payment_method = paypalJson.get("payment_method").getAsString();

        String total = paypalJson.get("total").getAsString();
        String currency = "USD";

        //--------------------------check out-----------------------------------------
        String recieptStr= null;
        try {

            URL url = new URL("https://api.sandbox.paypal.com/v1/payments/payment");
            if(live)
                url = new URL("https://api.paypal.com/v1/payments/payment");

            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            String t = "A21AAEjehr1nLau0IYzqMFW_B2Zi-pWRS6397uuhSfqXCYm2R5NNz7DYnR02TzN60CmSno52Eh-odd_9IyWZHd4RqbQUD-1ag";
            //t= access_token;
            conn.setRequestProperty("Authorization", "Bearer " + access_token);
            //conn.setRequestProperty("Authorization", "Bearer " + access_token);
            conn.setRequestProperty("Content-Type", "application/json");

            JsonObject credit = new JsonObject();
            credit.addProperty("number", number);
            credit.addProperty("type", type);
            credit.addProperty("expire_month", expire_month);
            credit.addProperty("expire_year", expire_year);
            credit.addProperty("cvv2", cvv2);
            credit.addProperty("first_name", first_name);
            credit.addProperty("last_name", last_name);
            //credit.add("billing_address", billing_address);

            JsonObject credit_card = new JsonObject();
            credit_card.add("credit_card", credit);

            JsonArray funding_array = new JsonArray();
            funding_array.add(credit_card);

            JsonObject payer = new JsonObject();
            payer.addProperty("payment_method", payment_method);
            payer.add("funding_instruments", funding_array);

            //transactions
            JsonObject amount = new JsonObject();
            amount.addProperty("total", total);
            amount.addProperty("currency", currency);
            //amount.add("details", details);

            JsonObject trans = new JsonObject();
            trans.add("amount", amount);
            trans.addProperty("description", "This is the payment transaction description.");

            JsonArray trans_array = new JsonArray();
            trans_array.add(trans);

            //integrate
            JsonObject payInfo = new JsonObject();
            payInfo.addProperty("intent", "sale");
            payInfo.add("payer", payer);
            payInfo.add("transactions", trans_array);

            //System.out.println("request json for payapl:" + payInfo + "\n");

            DataOutputStream output = new DataOutputStream(conn.getOutputStream());

            //output.writeBytes( cardJson.toString());
            output.writeBytes(payInfo.toString());

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
            logger.info("payapl resposonse " + sb);

            recieptStr = sb.toString();
            //recieptJson = new JsonParser().parse( sb.toString() ).getAsJsonObject();


            //String resStr = recieptJson.toString().replaceAll("^\"|\"$", "");

            reader.close();

            System.out.println("Response code:" + conn.getResponseCode());
            System.out.println("Response message:" + conn.getResponseMessage());

        } catch (Exception e) {
            String m = e.getMessage();

            logger.error("error: " + m);
            logger.error("reciept: " + recieptStr);

            if(!test) m= errorHandling.PaypalError;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m, e);
        }

        JsonObject recieptJson = new JsonParser().parse( recieptStr ).getAsJsonObject();
        //logger.info("recieptJson " + recieptJson);

        //System.out.println("payment id:" + recieptJson.get("id") );

        return recieptStr;
    }


    public Pos_rents_orders pos_rents_orders(JsonObject pos_rents_ordersJson , String recieptStr) {

        Pos_rents_orders newRow = new Pos_rents_orders() ;

        JsonObject recieptJson = new JsonParser().parse( recieptStr ).getAsJsonObject();

        try {
            newRow.setOrder_id(recieptJson.get("id").getAsString());
            //newRow.setOrder_id( "PAY-4UJ945233A54211bbbbbbbbb" );

            newRow.setCashier_email("reservation@bikerent.nyc");

            newRow.setTotal_price_before_tax(Double.valueOf(pos_rents_ordersJson.get("total_price_before_tax").getAsString()));
            newRow.setTotal_price_after_tax(Double.valueOf(pos_rents_ordersJson.get("total_price_after_tax").getAsString()));

            newRow.setCustomer_name(pos_rents_ordersJson.get("customer_name").getAsString());
            newRow.setCustomer_lastname(pos_rents_ordersJson.get("customer_lastname").getAsString());

            newRow.setCustomer_cc_name(pos_rents_ordersJson.get("customer_cc_name").getAsString());
            newRow.setCustomer_cc_lastname(pos_rents_ordersJson.get("customer_cc_lastname").getAsString());

            newRow.setCustomer_email(pos_rents_ordersJson.get("customer_email").getAsString());
            newRow.setOrder_completed(1);
            newRow.setPayment_type("paypal");
            newRow.setReservation(1);

            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String[] spliter = now.split(" ");
            newRow.setCreated_at(now);
            newRow.setDate(spliter[0]);//date
            newRow.setTime(spliter[1]);//time
            newRow.setDuration(pos_rents_ordersJson.get("duration").getAsString());

            newRow.setAdult(Integer.valueOf(pos_rents_ordersJson.get("adult").getAsString()));
            newRow.setChild(Integer.valueOf(pos_rents_ordersJson.get("child").getAsString()));
            newRow.setTandem(Integer.valueOf(pos_rents_ordersJson.get("tandem").getAsString()));
            newRow.setRoad(Integer.valueOf(pos_rents_ordersJson.get("road").getAsString()));
            newRow.setMountain(Integer.valueOf(pos_rents_ordersJson.get("mountain").getAsString()));
            newRow.setHand(Integer.valueOf(pos_rents_ordersJson.get("hand").getAsString()));
            newRow.setElectric_bike(Integer.valueOf(pos_rents_ordersJson.get("electric_bike").getAsString()));
            newRow.setElectric_hand(Integer.valueOf(pos_rents_ordersJson.get("electric_hand").getAsString()));
            newRow.setElliptigo(Integer.valueOf(pos_rents_ordersJson.get("elliptigo").getAsString()));
            newRow.setTricycle(Integer.valueOf(pos_rents_ordersJson.get("tricycle").getAsString()));
            newRow.setCarbon_road(Integer.valueOf(pos_rents_ordersJson.get("carbon_road").getAsString()));
            newRow.setTotal_bikes(Integer.valueOf(pos_rents_ordersJson.get("total_bikes").getAsString()));
            newRow.setTrailer(Integer.valueOf(pos_rents_ordersJson.get("trailer").getAsString()));
            newRow.setKid_trailer(Integer.valueOf(pos_rents_ordersJson.get("kid_trailer").getAsString()));
            newRow.setBasket(Integer.valueOf(pos_rents_ordersJson.get("basket").getAsString()));
            newRow.setSeat(Integer.valueOf(pos_rents_ordersJson.get("seat").getAsString()));
            newRow.setLocks(0);//free to take for customer

            newRow.setDropoff(Integer.valueOf(pos_rents_ordersJson.get("dropoff").getAsString()));
            newRow.setInsurance(Integer.valueOf(pos_rents_ordersJson.get("insurance").getAsString()));
            //newRow.setBarcode( "generated by backend");
            newRow.setServed(1);
            //newRow.setSequantial();
        }catch (Exception e) {
            logger.error( "save database pos_rents_orders " + e.getMessage() );
            String m= "parse json <pos_rents_orders> error ";
            if(!test) m= errorHandling.System500;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m + e.getMessage(), e);
        }

        logger.info( "pos_rents_orders new rows: " + newRow );

        return newRow;
    }


    public Pos_tours_orders pos_tours_orders(JsonObject pos_tours_ordersJson , String recieptStr) {

        Pos_tours_orders newRow = new Pos_tours_orders();
        JsonObject recieptJson = new JsonParser().parse( recieptStr ).getAsJsonObject();

        try {
            newRow.setOrder_id(recieptJson.get("id").getAsString());
            //newRow.setOrder_id( "PAY-4UJ945233A54211bbbbbbbbb" );

            newRow.setCashier_email("reservation@bikerent.nyc");

            newRow.setTotal_price_before_tax(Double.valueOf(pos_tours_ordersJson.get("total_price_before_tax").getAsString()));
            newRow.setTotal_price_after_tax(Double.valueOf(pos_tours_ordersJson.get("total_price_after_tax").getAsString()));

            newRow.setCustomer_name(pos_tours_ordersJson.get("customer_name").getAsString());
            newRow.setCustomer_lastname(pos_tours_ordersJson.get("customer_lastname").getAsString());

            newRow.setCustomer_cc_name(pos_tours_ordersJson.get("customer_cc_name").getAsString());
            newRow.setCustomer_cc_lastname(pos_tours_ordersJson.get("customer_cc_lastname").getAsString());

            newRow.setCustomer_email(pos_tours_ordersJson.get("customer_email").getAsString());
            newRow.setOrder_completed(1);
            newRow.setPayment_type("paypal");
            newRow.setReservation(1);

            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String[] spliter = now.split(" ");
            newRow.setCreated_at(now);
            newRow.setDate(spliter[0]);//date
            newRow.setTime(spliter[1]);//time

            newRow.setTour_type(pos_tours_ordersJson.get("tour_type").getAsString());
            newRow.setTour_place(pos_tours_ordersJson.get("tour_place").getAsString());

            newRow.setAdult(Integer.valueOf(pos_tours_ordersJson.get("adult").getAsString()));
            newRow.setChild(Integer.valueOf(pos_tours_ordersJson.get("child").getAsString()));
            newRow.setWalking(Integer.valueOf(pos_tours_ordersJson.get("walking").getAsString()));
            newRow.setPedicab(Integer.valueOf(pos_tours_ordersJson.get("pedicab").getAsString()));
            newRow.setTotal_people(Integer.valueOf(pos_tours_ordersJson.get("total_people").getAsString()));
            newRow.setTrailer(Integer.valueOf(pos_tours_ordersJson.get("trailer").getAsString()));
            newRow.setKid_trailer(Integer.valueOf(pos_tours_ordersJson.get("kid_trailer").getAsString()));
            newRow.setBasket(Integer.valueOf(pos_tours_ordersJson.get("basket").getAsString()));
            newRow.setSeat(Integer.valueOf(pos_tours_ordersJson.get("seat").getAsString()));

            newRow.setInsurance(Integer.valueOf(pos_tours_ordersJson.get("insurance").getAsString()));
            //newRow.setBarcode( "generated by backend");
            newRow.setServed(1);
            //newRow.setSequantial();
        }catch (Exception e) {
            logger.error( "save database pos_tours_orders " + e.getMessage() );
            String m= "parse json <pos_tours_orders> error "+ e.getMessage();
            if(!test) m= errorHandling.System500;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m , e);
        }

        logger.info( "pos_tours_orders new rows: " + newRow );

        return newRow;
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