package com.example.demo.dao.payment;

import com.example.demo.model.Pos_rents_orders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.codec.binary.Base64;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Module {

    public JsonObject parseJson( String resquestStr){
        System.out.println(resquestStr);
        JsonParser parser = new JsonParser();
        JsonObject resquestJson = parser.parse(resquestStr).getAsJsonObject();
        return resquestJson;
    }

    public JsonObject paypalJson( String resquestStr){
        JsonObject resquestJson= parseJson(resquestStr);
        JsonObject paypalJson= resquestJson.get("paypal").getAsJsonObject();

        System.out.println("paypal: " + paypalJson);
        return paypalJson;
    }

    public JsonObject pos_rents_ordersJson( String resquestStr){
        JsonObject resquestJson= parseJson(resquestStr);
        JsonObject pos_rents_ordersJson= resquestJson.get("pos_rents_orders").getAsJsonObject();

        System.out.println("pos_rents_orders: " + pos_rents_ordersJson);
        return pos_rents_ordersJson;
    }

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

    public JsonObject paypal_placement(String access_token, JsonObject paypalJson) {

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
        JsonObject recieptJson= null;
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
            funding_array.add( credit_card );

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

            recieptJson = new JsonParser().parse( sb.toString() ).getAsJsonObject();

            String resStr = recieptJson.toString().replaceAll("^\"|\"$", "");

            reader.close();

            System.out.println("Response code:" + conn.getResponseCode());
            System.out.println("Response message:" + conn.getResponseMessage());

        } catch (Exception e) {
            String m = e.getMessage();

            System.out.println("error: " + m);
        }

        //System.out.println("payment id:" + recieptJson.get("id") );
        System.out.println("reciept " + recieptJson);

        return recieptJson;
    }

    public Pos_rents_orders pos_rents_orders(JsonObject pos_rents_ordersJson , JsonObject recieptJson) {

        Pos_rents_orders newRow = new Pos_rents_orders() ;

        newRow.setOrder_id( recieptJson.get("id").getAsString() );
        //newRow.setOrder_id( "PAY-4UJ945233A54211bbbbbbbbb" );

        newRow.setCashier_email( "reservation@bikerent.nyc" );

        newRow.setTotal_price_before_tax( Double.valueOf( pos_rents_ordersJson.get("total_price_before_tax").getAsString() ) );
        newRow.setTotal_price_after_tax( Double.valueOf( pos_rents_ordersJson.get("total_price_after_tax").getAsString() ) );

        newRow.setCustomer_name(pos_rents_ordersJson.get("customer_name").getAsString());
        newRow.setCustomer_lastname(pos_rents_ordersJson.get("customer_lastname").getAsString());

        newRow.setCustomer_cc_name(pos_rents_ordersJson.get("customer_cc_name").getAsString());
        newRow.setCustomer_cc_lastname(pos_rents_ordersJson.get("customer_cc_lastname").getAsString());

        newRow.setCustomer_email( pos_rents_ordersJson.get("customer_email").getAsString() );
        newRow.setOrder_completed( 1 );
        newRow.setPayment_type("paypal");
        newRow.setReservation(1);

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String[] spliter= now.split(" ");
        newRow.setCreated_at( now );
        newRow.setDate( spliter[0] );//date
        newRow.setTime( spliter[1] );//time
        newRow.setDuration( pos_rents_ordersJson.get("duration").getAsString());

        newRow.setAdult( Integer.valueOf( pos_rents_ordersJson.get("adult").getAsString() ) );
        newRow.setChild( Integer.valueOf( pos_rents_ordersJson.get("child").getAsString() ) );
        newRow.setTandem( Integer.valueOf( pos_rents_ordersJson.get("tandem").getAsString() ) );
        newRow.setRoad( Integer.valueOf( pos_rents_ordersJson.get("road").getAsString() ) );
        newRow.setMountain( Integer.valueOf( pos_rents_ordersJson.get("mountain").getAsString() ) );
        newRow.setHand( Integer.valueOf( pos_rents_ordersJson.get("hand").getAsString() ) );
        newRow.setElectric_bike( Integer.valueOf( pos_rents_ordersJson.get("electric_bike").getAsString() ) );
        newRow.setElectric_hand( Integer.valueOf( pos_rents_ordersJson.get("electric_hand").getAsString() ) );
        newRow.setElliptigo( Integer.valueOf( pos_rents_ordersJson.get("elliptigo").getAsString() ) );
        newRow.setTricycle( Integer.valueOf( pos_rents_ordersJson.get("tricycle").getAsString() ) );
        newRow.setCarbon_road( Integer.valueOf( pos_rents_ordersJson.get("carbon_road").getAsString() ) );
        newRow.setTotal_bikes( Integer.valueOf( pos_rents_ordersJson.get("total_bikes").getAsString() ) );
        newRow.setTrailer( Integer.valueOf( pos_rents_ordersJson.get("trailer").getAsString() ) );
        newRow.setKid_trailer( Integer.valueOf( pos_rents_ordersJson.get("kid_trailer").getAsString() ) );
        newRow.setBasket( Integer.valueOf( pos_rents_ordersJson.get("basket").getAsString() ) );
        newRow.setSeat( Integer.valueOf( pos_rents_ordersJson.get("seat").getAsString() ) );
        newRow.setLocks(0);//free to take for customer

        newRow.setDropoff( Integer.valueOf( pos_rents_ordersJson.get("dropoff").getAsString() ) );
        newRow.setInsurance( Integer.valueOf( pos_rents_ordersJson.get("insurance").getAsString() ) );
        //newRow.setBarcode( "generated by backend");
        newRow.setServed(1);
        //newRow.setSequantial();

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