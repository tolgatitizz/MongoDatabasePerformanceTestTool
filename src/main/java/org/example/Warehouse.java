package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class Warehouse {
    int w_id;
    int w_ytd;
    int w_tax;
    String w_name;
    String w_street_1;
    String w_street_2;
    String w_city;
    String w_state;
    String w_zip;
    String padding;
    String updateTime;

    public Warehouse(int w_id, int w_ytd, int w_tax, String w_name, String w_street_1, String w_street_2, String w_city, String w_state, String w_zip, String padding, String updateTime) {
        this.w_id = w_id;
        this.w_ytd = w_ytd;
        this.w_tax = w_tax;
        this.w_name = w_name;
        this.w_street_1 = w_street_1;
        this.w_street_2 = w_street_2;
        this.w_city = w_city;
        this.w_state = w_state;
        this.w_zip = w_zip;
        this.padding = padding;
        this.updateTime = updateTime;
    }
    public Warehouse(){
        Random random = new Random();
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        this.w_ytd = random.nextInt(1000000001);
        this.w_tax = random.nextInt(1000000001);
        this.w_name = UUID.randomUUID().toString();
        this.w_street_1 = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.w_street_2 = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.w_city = UUID.randomUUID().toString();
        this.w_state = UUID.randomUUID().toString();
        this.w_zip = UUID.randomUUID().toString();
        this.padding = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.updateTime = LocalDateTime.now().toString();
    }
    Document getAsDoc(){
        Document document = new Document().append("_id",new ObjectId()).append("w_ytd",w_ytd).append("w_tax",w_tax).append("w_name",w_name).append("w_street_1",w_street_1).append("w_street_2",w_street_2)
                .append("w_city",w_city).append("w_state",w_state).append("w_zip",w_zip).append("padding",padding).append("updateTime",updateTime);
        return document;
    }
}
