package org.example;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Random;

public class Orders {
    int o_id;
    int o_d_id;
    int o_w_id;
    int o_c_id;
    int o_carrier_id;
    int o_ol_cnt;
    int o_all_local;
    String o_entry_d;

    public Orders(int o_id, int o_d_id, int o_w_id, int o_c_id, int o_carrier_id, int o_ol_cnt, int o_all_local, String o_entry_d) {
        this.o_id = o_id;
        this.o_d_id = o_d_id;
        this.o_w_id = o_w_id;
        this.o_c_id = o_c_id;
        this.o_carrier_id = o_carrier_id;
        this.o_ol_cnt = o_ol_cnt;
        this.o_all_local = o_all_local;
        this.o_entry_d = o_entry_d;
    }
    public Orders(){
        Random random = new Random();
        this.o_d_id = random.nextInt(1000000001);
        this.o_w_id = random.nextInt(1000000001);
        this.o_c_id = random.nextInt(1000000001);
        this.o_carrier_id = random.nextInt(1000000001);
        this.o_ol_cnt = random.nextInt(1000000001);
        this.o_all_local = random.nextInt(1000000001);
        this.o_entry_d = LocalDateTime.now().toString();
    }
    Document getAsDoc(){
        Document document = new Document().append("_id",new ObjectId()).append("o_d_id",o_d_id).append("o_w_id",o_w_id).append("o_c_id",o_c_id).append("o_carrier_id",o_carrier_id).append("o_ol_cnt",o_ol_cnt)
                .append("o_all_local",o_all_local).append("o_entry_d",o_entry_d);
        return document;
    }
}
