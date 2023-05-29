package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

public class District {
    int d_id;
    int d_w_id;
    int d_ytd;
    int d_next_o_id;
    int d_tax;
    String d_name;
    String d_street_1;
    String d_street_2;
    String d_city;
    String d_state;
    String d_zip;
    String d_padding;
    String updateTime;

    public District(int d_id, int d_w_id, int d_ytd, int d_next_o_id, int d_tax, String d_name, String d_street_1, String d_street_2, String d_city, String d_state, String d_zip, String d_padding, String updateTime) {
        this.d_id = d_id;
        this.d_w_id = d_w_id;
        this.d_ytd = d_ytd;
        this.d_next_o_id = d_next_o_id;
        this.d_tax = d_tax;
        this.d_name = d_name;
        this.d_street_1 = d_street_1;
        this.d_street_2 = d_street_2;
        this.d_city = d_city;
        this.d_state = d_state;
        this.d_zip = d_zip;
        this.d_padding = d_padding;
        this.updateTime = updateTime;
    }
    public District(){
        Random random = new Random();
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        this.d_w_id = random.nextInt(1000000001);
        this.d_ytd = random.nextInt(100001);
        this.d_next_o_id = random.nextInt(1000000001);
        this.d_tax = random.nextInt(1000000001);
        this.d_name = UUID.randomUUID().toString();
        this.d_street_1 = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.d_street_2 = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.d_city = UUID.randomUUID().toString();
        this.d_state = UUID.randomUUID().toString();
        this.d_zip = UUID.randomUUID().toString();
        randomBytes = new byte[96];
        secureRandom.nextBytes(randomBytes);
        this.d_padding = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        this.updateTime = LocalDateTime.now().toString();
    }
    Document getAsDoc(){
        Document document = new Document().append("_id",new ObjectId()).append("d_w_id",d_w_id).append("d_ytd",d_ytd).append("d_next_o_id",d_next_o_id).append("d_tax",d_tax).append("d_name",d_name).append("d_street_1",d_street_1)
                .append("d_street_2",d_street_2).append("d_city",d_city).append("d_state",d_state).append("d_zip",d_zip).append("d_padding",d_padding).append("updateTime",updateTime);
        return document;
    }
}
