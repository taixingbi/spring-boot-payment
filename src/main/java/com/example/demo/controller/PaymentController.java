package com.example.demo.controller;
import com.example.demo.dao.payment.Module;

import com.example.demo.dao.payment.Payment;
import com.example.demo.model.Pos_rents_orders;
import com.example.demo.repository.PosRentsOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
        posRentsOrdersRepository.save(newRowPosRentOrders);

        return HttpStatus.OK;
    }


    @RequestMapping("/test")
    public String test() {
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String[] spliter= now.split(" ");

        return spliter[1];
    }

}






