package com.example.demo.dao.payment;

import com.example.demo.dao.payment.ErrorHandling;
import com.example.demo.model.Pos_rents_orders;
import com.example.demo.repository.PosRentsOrdersRepository;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


public class Payment {
    private static final Logger logger = LogManager.getLogger(Module.class);

    @Autowired
    private PosRentsOrdersRepository posRentsOrdersRepository;

    public String payal_api(String resquestStr, String check_out_type) {
        logger.info("\n\n-----------------------------------------------payal_api-----------------------------------------------" );

        Module module = new Module();
        String  access_token= module.papyal_access_token();        //get paypal access token
        JsonObject paypalJson = module.paypalJson(resquestStr);         //prarse request json data

        return  module.paypal_placement(access_token, paypalJson); //recieptStr
    }

    public Pos_rents_orders checkout_pos_rents_orders(String resquestStr, String check_out_type, String recieptStr) {
        logger.info("\n\n-----------------------------------------------pos_rents_orders----------------------------------------------->" );

        Module module = new Module();

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
        "cashier_email": "reservation@bikerent.nyc",
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

or

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

      "pos_tours_orders": {
            "cashier_email": "reservation@bikerent.nyc",
            "total_price_before_tax": "6.72",
            "total_price_after_tax": "7.00",
            "customer_name": "customer_firstName",
            "customer_lastname": "customer_lastName",
            "customer_cc_name": "customer_cc__firstName",
            "customer_cc_lastname": "customer_cc__lastName",
            "customer_email": "customer@gmail.com",
            "order_completed": 1,
            "payment_type": "paypal",
            "reservation": 1,
            "created_at": "2017-12-24 09:36:04", // not need
            "date": "2017-12-24",    // not need
            "time": "09:36:04",  // not need
            "tour_type": "public(2h)",
            "tour_place": "Central Park",
            "adult": 1,
            "child": 2,
            "walking": 3,
            "pedicab": 4,
            "total_people": 5,
            "trailer":  6,
            "kid_trailer": 7,
            "basket": 8,
            "seat": 9,
            "insurance": 1,
            "barcode": "barcodefrw23",
            "served": 1,
            "sequantial": "CPW13707"// not need
      }
    }


 */