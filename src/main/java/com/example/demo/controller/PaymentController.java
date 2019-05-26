package com.example.demo.controller;
import com.example.demo.dao.payment.Module;

import com.example.demo.dao.payment.Payment;
import com.example.demo.model.Pos_rents_orders;
import com.example.demo.repository.PosRentsOrdersRepository;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ValidationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
public class PaymentController {

    @Autowired
    private PosRentsOrdersRepository posRentsOrdersRepository;

    @RequestMapping("/payment/token")
    public String get_paypal_access_token() {
        Module payment= new Module();
        return  payment.papyal_access_token();
    }

    @CrossOrigin
    @RequestMapping(
        value = "/payment",
        method = RequestMethod.POST,
        consumes = MediaType.APPLICATION_JSON_VALUE)

    @ResponseBody
    public HttpStatus resquestFromFrontend(@RequestBody String resquestStr) throws Exception {
        Payment payment= new Payment();
        Pos_rents_orders newRowPosRentOrders= payment.process(resquestStr);

        try {
            posRentsOrdersRepository.save(newRowPosRentOrders);
        }catch (Exception e) {
            String m = e.getMessage();

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, m, e);
        }

        return HttpStatus.OK;
    }

    @RequestMapping("/test")
    public String test() {


        try {
            URL url = new URL("ttp://example.com");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");        }

        catch (Exception e) {
            String m = e.getMessage();

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, m, e);

        }

//        catch (MalformedURLException e) {
//            // Replace this with your exception handling
//            e.printStackTrace();
//        } catch (IOException e) {
//            // Replace this with your exception handling
//            e.printStackTrace();
//        }

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return now.split(" ")[1];
    }

}






