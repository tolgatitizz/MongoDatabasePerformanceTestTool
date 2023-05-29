package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class Item {
    int i_id;
    String i_name;
    int i_price;
    String i_data;
    int i_im_id;
    String updateTime;

    public Item(int i_id, String i_name, int i_price, String i_data, int i_im_id,String updateTime) {
        this.i_id = i_id;
        this.i_name = i_name;
        this.i_price = i_price;
        this.i_data = i_data;
        this.i_im_id = i_im_id;
        this.updateTime = updateTime;
    }
    public Item(){
        Random random = new Random();
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        this.i_id = random.nextInt(1000000001);
        this.i_name = UUID.randomUUID().toString();
        this.i_price = random.nextInt(100001);
        this.i_data = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.i_im_id = random.nextInt(1000000001);
        this.updateTime = LocalDateTime.now().toString();
    }
    Document getAsDoc(){
        Document document = new Document().append("_id",new ObjectId()).append("i_name",i_name).append("i_name",i_name).append("i_price",i_price).append("i_data",i_data).append("i_im_id",i_im_id).append("updateTime",updateTime);
        return document;
    }
}
