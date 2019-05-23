package com.example.demo.dao.payment;

import com.example.demo.model.Pos_rents_orders;
import com.example.demo.repository.PosRentsOrdersRepository;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;


public class Payment {
    @Autowired
    private PosRentsOrdersRepository posRentsOrdersRepository;

    public Pos_rents_orders process(String resquestStr) {
        System.out.println("string " + resquestStr);

        Module module = new Module();

        //get paypal access token
        String  access_token= module.papyal_access_token();

        //prarse request json data
        JsonObject paypalJson = module.paypalJson(resquestStr);

        //paypal preocess
        String recieptStr=  module.paypal_placement(access_token, paypalJson);
        //JsonObject recieptJson= null;

        //save to database
        JsonObject pos_rents_ordersJson = module.pos_rents_ordersJson(resquestStr);
        Pos_rents_orders newRowPosRentOrders= module.pos_rents_orders(pos_rents_ordersJson, recieptStr);

        return newRowPosRentOrders;
    }
}



//---------------------test demo--------------------
/*
    {
      "paypal": {
        "line1": "109 ST",
        "city": "Brooklyn",
        "state": "NY",
        "postal_code": "11217",
        "country_code": "US",

        "number": "4417119669820331",
        "type": "visa",
        "expire_month": "11",
        "expire_year": "2021",
        "cvv2": "874",
        "first_name": "Mar",
        "last_name": "Singer",

        "payment_method": "credit_card",

        "subtotal": "8.41",
        "tax":  "0.03",
        "shipping": "0.03",
        "total": "8.47",

        "currency": "USD"

      },

      "bikerental": {
        "cashier_email": "cashier@bikerent.ny",
        "total_price_before_tax": "6.72",
        "total_price_after_tax": "7.00",
        "customer_name": "customer_firstName",
        "customer_lastname": "customer_lastName",
        "customer_cc_name": "customer_cc__firstName",
        "customer_cc_lastname": "customer_cc__lastName",
        "customer_email": "customer@gmail.com",
        "order_completed": 0,
        "payment_type": "paypal",
        "reservation": 1,
        "created_at": "2017-12-24 09:36:04",
        "date": "2017-12-24",
        "time": "09:36:04",
        "duration": "2 hours",
        "adult": 1,
        "child": 2,
        "tandem": 4,
        "road": 1,
        "mountain": 5,
        "hand": 3,
        "electric_bike": 3,
        "electric_hand": 4,
        "elliptigo": 2,
        "tricycle": 1,
        "carbon_road": 3,
        "total_bikes": 12,
        "trailer":  3,
        "kid_trailer": 2,
        "basket": 3,
        "seat": 4,
        "lock": 6,
        "dropoff": 1,
        "insurance": 1,
        "barcode": "barcodefrw23",
        "served": 1,
        "sequantial": "CPW13707"
      }
    }


 */