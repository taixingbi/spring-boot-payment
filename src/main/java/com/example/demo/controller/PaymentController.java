package com.example.demo.controller;
import com.example.demo.dao.Payment;

import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.model.Checkout;
import com.example.demo.model.Orders;
import com.example.demo.model.Users;

import com.google.gson.JsonObject;


import com.example.demo.repository.OrdersRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
public class PaymentController {

    private OrdersRepository ordersRepository;

    public PaymentController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @RequestMapping("/paypal/token")
    public String get_paypal_access_token() {
        Payment payment= new Payment();
        return  payment.papyal_access_token();
    }

    @PostMapping("/test")
    public String test(@RequestBody Checkout post) {


        //System.out.println("post: "+post);

        return "nice post";
    }

    @RequestMapping("/paypal")
    public String checkout() {

        Payment payment= new Payment();

        String  access_token= payment.papyal_access_token();
        JsonObject resJon=  payment.papyal_check_out(access_token);
        Orders OrdersRow= payment.save2sql(resJon);
        ordersRepository.save(OrdersRow);

        return "pay succesully\n" + resJon;
    }


    @RequestMapping("/test/sql")
    public Orders save_sql() {

        Orders row = new Orders() ;
        row.setOrder_id("ch_1A8cGRDBxoAxoozUZHjrbDdeaasdsdfsdf");
        row.setAgent_email("dd@gmail.com");
        row.setOrder_completed(1);
        row.setPayment_type("credit_card");

        row.setTotal_price_before_tax(8.41);
        row.setTotal_price_after_tax(8.47);
        row.seTagent_price_after_tax(8.46);

        row.setTour_type("public(2h)");
        row.setDate("04/07/2019");
        row.setTime("9am");
        row.setCustomer_name("di");
        row.setCustomer_lastname("hooker");
        row.setCustomer_email("di@gmail.com");
        row.setComment("this guy is lazy");
        row.setbarcode("as4545sdfq324423sdf");

        row.setAdult(1);
        row.setChild(1);
        row.setTotal_people(2);
        row.setServed(0);

        row.setCreated_at( LocalDateTime.now() );
        row.setCompleted_at(  LocalDateTime.now() );
        row.setServed_date(  LocalDateTime.now() );


        ordersRepository.save(row);

        return row;
    }

}







