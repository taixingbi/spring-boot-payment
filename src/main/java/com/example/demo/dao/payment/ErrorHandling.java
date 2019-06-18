package com.example.demo.dao.payment;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.model.Pos_rents_orders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import org.apache.commons.codec.binary.Base64;
import org.apache.el.parser.ParseException;
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


public class ErrorHandling {

    private static final Logger logger = LogManager.getLogger(Module.class);

    String PaypalError= "Sorry, the payment process failed. You can try again, or try a different credit card";
    String Validation= "JUST MAKE IT USER FRIENDLY";
    String System500= "Opps, something went wrong. Try again a different time";

    private JsonObject parseJson2Obj(String in) {
        JsonObject out;

        try {
            out = new JsonParser().parse(in).getAsJsonObject();
        } catch (Exception e) {
            logger.error("JSON FORMAT ERROR " + in);
            String m= "JSON FORMAT ERROR: " + e.getMessage();
            m= System500;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m, e);
        }
        logger.info("in " + in);
        return out;
    }

    private JsonObject jsonObjGetKeyAsObj(JsonObject in, String key) {
        JsonObject out;
        try {
            out = in.get(key).getAsJsonObject();
        } catch (Exception e) {
            logger.error("JSON KEY ERROR " + "'" + key + "'" + " not found");
            String m= "JSON KEY ERROR: " + "'" + key + "'" + " not found";
            m= System500;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m);
        }
        logger.info(key + ": " + out);

        return out;
    }

    private String jsonObjGetKeyAsString(JsonObject in, String key) {
        String out;
        try {
            out = in.get(key).getAsString();
        } catch (Exception e) {
            logger.error("JSON KEY ERROR " + "'" + key + "'" + " not found");
            String m= "JSON KEY ERROR: " + "'" + key + "'" + " not found";
            m= System500;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m);
        }
        logger.info(key + ": " + out);

        return out;
    }

    private void payaplValidation(JsonObject paypalJson) {

        String type = paypalJson.get("type").getAsString();
        if (type.equals("visa") ||
                type.equals("americanexpress") ||
                type.equals("discover") ||
                type.equals("mastercard")
        ) {
            logger.info("payapl type is good ");
        } else {
            String m= "VALIDATION ERROR: payapl <type> should be one of  <visa> <mastercard> <americanexpress> <discover>";
            m= "Please input right visa type";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m);
        }

        String expireMonth = paypalJson.get("expire_month").getAsString();
        if (expireMonth.equals("01") ||
                expireMonth.equals("02") ||
                expireMonth.equals("03") ||
                expireMonth.equals("04") ||
                expireMonth.equals("05") ||
                expireMonth.equals("06") ||
                expireMonth.equals("07") ||
                expireMonth.equals("08") ||
                expireMonth.equals("09") ||
                expireMonth.equals("10") ||
                expireMonth.equals("11") ||
                expireMonth.equals("12")
        ) {
            logger.info("payapl expire_month is good ");
        } else {
            String m= "VALIDATION ERROR: payapl <expire_month> should be <01> <02>.. <12>";
            m= " Expiration month is wrong";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m);
        }

        String expireYear = paypalJson.get("expire_year").getAsString();
        if (Integer.valueOf(expireYear) >= 2019) {
            logger.info("payapl expire_year is good ");
        } else {
            String m= "VALIDATION ERROR: payapl <expire_month> should not be less current year";
            m= " Expiration Year is wrong";
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m);
        }

        String total = paypalJson.get("total").getAsString();
        String regex = "[0-9.]+";
        if (total.matches(regex)) {
            logger.info("payapl total is good ");
        } else {
            String m= "VALIDATION ERROR: payapl <total> is wrong. No letters, No space. it should be like eg <7> <7.0> ";
            m= System500;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m);
        }
    }

    public void parseJson( String resquestStr,  String check_out_type){
        logger.info("\n\n-----------------------------------------------ErrorHanding-----------------------------------------------" );

        if( !check_out_type.equals("pos_rents_orders") &&  !check_out_type.equals("pos_tours_orders"))
        {
            logger.error("URL ERROR: '.../pos_tours_orders' or '.../pos_tours_orders' not found,   " );
            String m= "URL ERROR: '.../pos_tours_orders' or '.../pos_tours_orders' not found,   ";
            m= System500;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m);
        }

        JsonObject resquestJson= parseJson2Obj(resquestStr);

        // paypal
        JsonObject paypal= jsonObjGetKeyAsObj(resquestJson, "paypal");
        jsonObjGetKeyAsString(paypal, "type");
        jsonObjGetKeyAsString(paypal, "expire_month");
        jsonObjGetKeyAsString(paypal, "expire_year");
        jsonObjGetKeyAsString(paypal, "cvv2");
        jsonObjGetKeyAsString(paypal, "first_name");
        jsonObjGetKeyAsString(paypal, "last_name");
        jsonObjGetKeyAsString(paypal, "payment_method");
        jsonObjGetKeyAsString(paypal, "total");
        jsonObjGetKeyAsString(paypal, "currency");

        payaplValidation(paypal);


        if(  check_out_type.equals("pos_rents_orders") ){
            JsonObject pos_rents_orders= jsonObjGetKeyAsObj(resquestJson, "pos_rents_orders");
            jsonObjGetKeyAsString(pos_rents_orders, "total_price_before_tax");
            jsonObjGetKeyAsString(pos_rents_orders, "total_price_after_tax");
            jsonObjGetKeyAsString(pos_rents_orders, "customer_name");
            jsonObjGetKeyAsString(pos_rents_orders, "customer_lastname");
            jsonObjGetKeyAsString(pos_rents_orders, "customer_cc_name");
            jsonObjGetKeyAsString(pos_rents_orders, "customer_cc_lastname");
            jsonObjGetKeyAsString(pos_rents_orders, "customer_email");
            jsonObjGetKeyAsString(pos_rents_orders, "duration");

            jsonObjGetKeyAsString(pos_rents_orders, "adult");
            jsonObjGetKeyAsString(pos_rents_orders, "child");
            jsonObjGetKeyAsString(pos_rents_orders, "tandem");
            jsonObjGetKeyAsString(pos_rents_orders, "road");
            jsonObjGetKeyAsString(pos_rents_orders, "mountain");
            jsonObjGetKeyAsString(pos_rents_orders, "hand");
            jsonObjGetKeyAsString(pos_rents_orders, "electric_bike");
            jsonObjGetKeyAsString(pos_rents_orders, "electric_hand");
            jsonObjGetKeyAsString(pos_rents_orders, "elliptigo");
            jsonObjGetKeyAsString(pos_rents_orders, "tricycle");
            jsonObjGetKeyAsString(pos_rents_orders, "carbon_road");
            jsonObjGetKeyAsString(pos_rents_orders, "trailer");
            jsonObjGetKeyAsString(pos_rents_orders, "kid_trailer");
            jsonObjGetKeyAsString(pos_rents_orders, "basket");
            jsonObjGetKeyAsString(pos_rents_orders, "seat");

            jsonObjGetKeyAsString(pos_rents_orders, "dropoff");
            jsonObjGetKeyAsString(pos_rents_orders, "insurance");
        }

        if(  check_out_type.equals("pos_tours_orders") ){
            JsonObject pos_tours_orders= jsonObjGetKeyAsObj(resquestJson, "pos_tours_orders");
            jsonObjGetKeyAsString(pos_tours_orders, "total_price_before_tax");
            jsonObjGetKeyAsString(pos_tours_orders, "total_price_after_tax");
            jsonObjGetKeyAsString(pos_tours_orders, "customer_name");
            jsonObjGetKeyAsString(pos_tours_orders, "customer_lastname");
            jsonObjGetKeyAsString(pos_tours_orders, "customer_cc_name");
            jsonObjGetKeyAsString(pos_tours_orders, "customer_cc_lastname");
            jsonObjGetKeyAsString(pos_tours_orders, "customer_email");

            jsonObjGetKeyAsString(pos_tours_orders, "tour_type");
            jsonObjGetKeyAsString(pos_tours_orders, "tour_place");
            jsonObjGetKeyAsString(pos_tours_orders, "adult");
            jsonObjGetKeyAsString(pos_tours_orders, "child");
            jsonObjGetKeyAsString(pos_tours_orders, "walking");
            jsonObjGetKeyAsString(pos_tours_orders, "pedicab");
            jsonObjGetKeyAsString(pos_tours_orders, "total_people");
            jsonObjGetKeyAsString(pos_tours_orders, "trailer");
            jsonObjGetKeyAsString(pos_tours_orders, "kid_trailer");
            jsonObjGetKeyAsString(pos_tours_orders, "basket");
            jsonObjGetKeyAsString(pos_tours_orders, "seat");

            jsonObjGetKeyAsString(pos_tours_orders, "insurance");
        }

        return ;
    }

}
