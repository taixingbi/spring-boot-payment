package com.example.demo.controller;
import com.example.demo.dao.payment.ErrorHandling;
import com.example.demo.dao.payment.Module;

import com.example.demo.dao.payment.Payment;
import com.example.demo.model.Pos_rents_orders;
import com.example.demo.repository.PosRentsOrdersRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ValidationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


@RestController
public class PaymentController {
    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Autowired
    private PosRentsOrdersRepository posRentsOrdersRepository;

    @RequestMapping("/payment/token")
    public String get_paypal_access_token() {
        Module payment= new Module();
        return  payment.papyal_access_token();
    }

    @CrossOrigin
    @RequestMapping(
        value = "/payment/{check_out_type}",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public HttpStatus resquestFromFrontend(@RequestBody String resquestStr,
                                           @PathVariable("check_out_type") String check_out_type
    )  {
        logger.info( "\n\n\n\n\n\n\n\n" +
                        "\n***********************************************************************************"+
                        "\nAuthor: hunter " +
                        "\nDate: 6/2019 " +
                        "\nProject: payment backend system based on payapl api for bikerent and bike tuors" +
                        "\nCheckout Type: "+check_out_type +
                        "\nDatabase: maysql" +
                        "\n***********************************************************************************");

        new ErrorHandling().parseJson(resquestStr, check_out_type);

        Payment payment= new Payment();
        String recieptStr= payment.payal_api(resquestStr, check_out_type);

        try {
            if(check_out_type.equals("pos_rents_orders"))
                posRentsOrdersRepository.save( payment.checkout_pos_rents_orders( resquestStr, check_out_type, recieptStr) );

            if(resquestStr.equals("pos_tours_orders")){

            }

        }catch (Exception e) {
            String m = e.getMessage();

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, m, e);
        }

        return HttpStatus.OK;
    }

    @RequestMapping("/test/{x}")
    public String test(  @PathVariable("x") String check_out_type ) {
        logger.info(check_out_type);

//        logger.debug("Debugging log");
//        logger.info("Info log");
//        logger.warn("Hey, This is a warning!");
//        logger.error("Oops! We have an Error. OK");
//        logger.fatal("Damn! Fatal error. Please fix me.");

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return now.split(" ")[1];
    }
}






