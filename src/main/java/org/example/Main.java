package org.example;
import static com.mongodb.client.model.Filters.eq;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.awt.desktop.SystemEventListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

public class Main {
    static final String connectionString = "mongodb+srv://ttitiz19:0000@cluster0.v2qrlwx.mongodb.net/?retryWrites=true&w=majority";
    //static final String connectionString = "mongodb://localhost:27017/";
    static int historyCount=1;
    static int historyCountRound=0;
    static long transactionCount = 0;
    public static void main(String[] args) {
        //Veri Tabanını Temizlemek İçin İlk Çalıştırılması gereken Fonksiyon
        //Thread tDropper = new Thread(new Worker(true)); tDropper.start();
        //Boş Veri Tabanına Veri Eklemesi Yapan Fonksiyon
        //insertionTest();

        //01.15 Rastgele Çalışma İşlemlerini yapan fonksiyon
        //executionTest();
        //01.30

    }
    public static void insertionTest(){
        Thread t1 = new Thread(new VirtualUser());
        Thread t2 = new Thread(new VirtualUser());
        Thread t3 = new Thread(new VirtualUser2());
        Thread t4 = new Thread(new VirtualUser3());
        Thread t5 = new Thread(new VirtualUser4());
        Thread t6 = new Thread(new VirtualUser5());
        Thread t7 = new Thread(new VirtualUser6());
        Thread t8 = new Thread(new VirtualUser7());
        Thread t9 = new Thread(new VirtualUser8());
        Thread t10 = new Thread(new VirtualUser4());
        Thread t11 = new Thread(new VirtualUser4());
        t1.start();t2.start();t3.start();t4.start();t5.start();t6.start();t7.start();t8.start();t9.start();t10.start();t11.start();
    }
    public static void executionTest(){
        Thread t1 = new Thread(new Worker());
        Thread t2 = new Thread(new Worker());
        Thread t3 = new Thread(new Worker());
        Thread t4 = new Thread(new Worker());
        Thread t5 = new Thread(new Worker());
        Thread t6 = new Thread(new Worker());
        Thread t7 = new Thread(new Worker());
        Thread t8 = new Thread(new Worker());
        Thread t9 = new Thread(new Worker());
        Thread t10 = new Thread(new Worker());
        Thread t11 = new Thread(new Worker());
        t1.start();t2.start();t3.start();t4.start();t5.start();t6.start();t7.start();t8.start();t9.start();t10.start();t11.start();

    }




}