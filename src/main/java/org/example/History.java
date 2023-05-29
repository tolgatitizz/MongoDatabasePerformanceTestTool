package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

public class History {
    int h_c_id;
    int h_c_d_id;
    int h_c_w_id;
    int h_d_id;
    String h_date;
    int h_amount;
    String h_data;

    public History(int h_c_id, int h_c_d_id, int h_c_w_id, int h_d_id, String h_date, int h_amount, String h_data) {
        this.h_c_id = h_c_id;
        this.h_c_d_id = h_c_d_id;
        this.h_c_w_id = h_c_w_id;
        this.h_d_id = h_d_id;
        this.h_date = h_date;
        this.h_amount = h_amount;
        this.h_data = h_data;
    }
    public History(){
        Random random = new Random();
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        this.h_c_d_id = random.nextInt(1000000001);
        this.h_c_w_id = random.nextInt(1000000001);
        this.h_d_id = random.nextInt(1000000001);
        this.h_date = LocalDateTime.now().toString();
        this.h_amount = random.nextInt(1000000001);
        this.h_data = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
    Document getAsDoc(){
        Document document = new Document().append("_id",new ObjectId()).append("h_c_d_id",h_c_d_id).append("h_c_w_id",h_c_w_id).append("h_d_id",h_d_id).append("h_date",h_date).append("h_amount",h_amount).append("h_data",h_data);
        return document;
    }
}
