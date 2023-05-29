package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Random;

public class New_Order {
    int no_o_id;
    int no_d_id;
    int no_w_id;
    String updateTime;

    public New_Order(int no_o_id, int no_d_id, int no_w_id, String updateTime) {
        this.no_o_id = no_o_id;
        this.no_d_id = no_d_id;
        this.no_w_id = no_w_id;
        this.updateTime = updateTime;
    }
    public New_Order(){
        Random random = new Random();
        this.no_d_id = random.nextInt(1000000001);
        this.no_w_id = random.nextInt(1000000001);
        this.updateTime = LocalDateTime.now().toString();
    }
    Document getAsDoc(){
        Document document = new Document().append("_id",new ObjectId()).append("no_d_id",no_d_id).append("no_w_id",no_d_id).append("updateTime",updateTime);
        return document;
    }
}

