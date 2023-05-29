package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class Customer {
    int c_id;
    int c_d_id;
    int c_w_id;
    int c_discount;
    int c_credit_lim;
    String c_last;
    String c_first;
    String c_credit;
    int c_balance;
    int c_ytd_payment;
    int c_payment_cnt;
    int c_delivery_cnt;
    String c_street_1;
    String c_street_2;
    String c_city;
    String c_state;
    String c_zip;
    String c_phone;
    String c_since;
    String c_middle;
    String c_data;
    String c_updateTime;

    public Customer(int c_id,int c_d_id, int c_w_id, int c_discount, int c_credit_lim, String c_last, String c_first, String c_credit, int c_balance, int c_ytd_payment, int c_payment_cnt, int c_delivery_cnt, String c_street_1, String c_street_2, String c_city, String c_state, String c_zip, String c_phone, String c_since, String c_middle, String c_data, String c_updateTime) {
        this.c_id = c_id;
        this.c_d_id = c_d_id;
        this.c_w_id = c_w_id;
        this.c_discount = c_discount;
        this.c_credit_lim = c_credit_lim;
        this.c_last = c_last;
        this.c_first = c_first;
        this.c_credit = c_credit;
        this.c_balance = c_balance;
        this.c_ytd_payment = c_ytd_payment;
        this.c_payment_cnt = c_payment_cnt;
        this.c_delivery_cnt = c_delivery_cnt;
        this.c_street_1 = c_street_1;
        this.c_street_2 = c_street_2;
        this.c_city = c_city;
        this.c_state = c_state;
        this.c_zip = c_zip;
        this.c_phone = c_phone;
        this.c_since = c_since;
        this.c_middle = c_middle;
        this.c_data = c_data;
        this.c_updateTime = c_updateTime;
    }
    public Customer(){
        Random random = new Random();
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        this.c_d_id = random.nextInt(1000000001);
        this.c_w_id = random.nextInt(1000000001);
        this.c_discount = random.nextInt(101);
        this.c_credit_lim = random.nextInt(100001);
        this.c_last = UUID.randomUUID().toString();
        this.c_first = UUID.randomUUID().toString();
        this.c_credit = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.c_balance = random.nextInt(101);
        this.c_ytd_payment = random.nextInt(100001);
        this.c_payment_cnt = random.nextInt(100001);
        this.c_delivery_cnt = random.nextInt(100001);
        this.c_street_1 = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.c_street_2 = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.c_city = UUID.randomUUID().toString();
        this.c_state = UUID.randomUUID().toString();
        this.c_zip = UUID.randomUUID().toString();
        this.c_phone = UUID.randomUUID().toString();
        this.c_since = UUID.randomUUID().toString();
        this.c_middle = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.c_data = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.c_updateTime = LocalDateTime.now().toString();
    }
    Document getAsDoc(){
        Document document = new Document().append("_id",new ObjectId()).append("c_d_id",c_d_id).append("c_w_id",c_w_id).append("c_discount",c_discount).append("c_credit_lim",c_credit_lim)
                .append("c_last",c_last).append("c_first",c_first).append("c_credit",c_credit).append("c_balance",c_balance).append("c_ytd_payment",c_ytd_payment).append("c_payment_cnt",c_payment_cnt)
                .append("c_delivery_cnt",c_delivery_cnt).append("c_street_1",c_street_1).append("c_street_2",c_street_2).append("c_city",c_city).append("c_state",c_state).append("c_zip",c_zip).append("c_phone",c_phone)
                .append("c_since",c_since).append("c_middle",c_middle).append("c_data",c_data).append("c_updateTime",c_updateTime);
        return document;
    }
}
