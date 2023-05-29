package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Random;

public class Order_Line {
    int ol_o_id;
    int ol_d_id;
    int ol_w_id;
    int ol_number;
    int ol_i_id;
    String delivery_d;
    int ol_amount;
    int ol_supply_w_id;
    int ol_quantity;
    String ol_dist_info;

    public Order_Line(int ol_o_id, int ol_d_id, int ol_w_id, int ol_number, int ol_i_id, String delivery_d, int ol_amount, int ol_supply_w_id, int ol_quantity, String ol_dist_info) {
        this.ol_o_id = ol_o_id;
        this.ol_d_id = ol_d_id;
        this.ol_w_id = ol_w_id;
        this.ol_number = ol_number;
        this.ol_i_id = ol_i_id;
        this.delivery_d = delivery_d;
        this.ol_amount = ol_amount;
        this.ol_supply_w_id = ol_supply_w_id;
        this.ol_quantity = ol_quantity;
        this.ol_dist_info = ol_dist_info;
    }
    public Order_Line(){
        Random random = new Random();
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        this.ol_d_id = random.nextInt(1000000001);
        this.ol_w_id = random.nextInt(1000000001);
        this.ol_number = random.nextInt(100001);
        this.ol_i_id = random.nextInt(1000000001);
        this.delivery_d = LocalDateTime.now().toString();
        this.ol_amount = random.nextInt(100001);
        this.ol_supply_w_id = random.nextInt(1000000001);
        this.ol_quantity = random.nextInt(101);
        this.ol_dist_info = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
    Document getAsDoc(){
        Document document = new Document().append("_id",new ObjectId()).append("ol_d_id",ol_d_id).append("ol_w_id",ol_w_id).append("ol_number",ol_number).append("ol_i_id",ol_i_id).append("delivery_d",delivery_d)
                .append("ol_amount",ol_amount).append("ol_supply_w_id",ol_supply_w_id).append("ol_quantity",ol_quantity).append("ol_dist_info",ol_dist_info);
        return document;
    }
}
