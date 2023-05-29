package org.example;

import com.mongodb.*;
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

import java.time.LocalDateTime;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;
import static org.example.Main.*;

public class Worker implements Runnable{
    boolean deleteAll;
    public Worker(){
    }
    public Worker(boolean deleteAll){
        this.deleteAll = deleteAll;
    }
    @Override
    public void run() {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server

        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
            MongoDatabase database = mongoClient.getDatabase("TPCC");
            int i;
            int timer=0;
            Random random = new Random();
            //Methods
            while(timer != 10000000){
                timer++;
                i= random.nextInt(4);
               // System.out.println("Transaction Count" +transactionCount);

                if(deleteAll == true){
                    i = 100;
                    timer = 10000000;
                }

                switch(i) {
                    case 0:
                        updateTransactionCount(database,LocalDateTime.now().toString());
                        break;
                    case 1:
                        customerDiscountCounter(database);
                        break;
                    case 2:
                        deleteTwo(database);
                        break;
                    case 3:
                        createFifty(database);
                        break;
                    case 100: deleteAllCollections(database);
                        break;
                }
            }
        }
    }
    public static void customerDiscountCounter(MongoDatabase database){
        MongoCollection<Document> customerCollection = database.getCollection("customer");
        MongoCollection<Document> historyCollection = database.getCollection("history");
        MongoCollection<Document> itemCollection = database.getCollection("item");
        MongoCollection<Document> districtCollection = database.getCollection("district");
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        MongoCollection<Document> new_orderCollection = database.getCollection("new_order");
        MongoCollection<Document> order_lineCollection = database.getCollection("order_line");
        MongoCollection<Document> warehouseCollection = database.getCollection("warehouse");
                for (int i = 0; i < 2; i++) {
                    Bson projectionFields = Projections.fields(Projections.include("_id"));
                    //,
                    //                        Projections.excludeId()

                    //Document costumerDoc = customerCollection.find().skip(100000).limit(1).first();
                    Document costumerDoc = customerCollection.find().first();
                    //Document historyDoc = historyCollection.find().skip(50000).limit(1).first();
                    Document historyDoc = historyCollection.find().first();
                    //Document itemDoc = itemCollection.find().skip(100000).limit(1).first();
                    Document itemDoc = itemCollection.find().first();
                    //Document districtDoc = districtCollection.find().skip(10000).limit(1).first();
                    Document districtDoc = districtCollection.find().first();
                    //Document ordersDoc = ordersCollection.find().skip(10000).limit(1).first();
                    Document ordersDoc = ordersCollection.find().first();
                    //Document new_orderDoc = new_orderCollection.find().skip(50000).limit(1).first();
                    Document new_orderDoc = new_orderCollection.find().first();
                    Document order_lineDoc = order_lineCollection.find().first();
                    Document warehouseDoc = warehouseCollection.find().first();
                }
                transactionCount = transactionCount +70;
    }
    public static void deleteTwo(MongoDatabase database){
                MongoCollection<Document> customerCollection = database.getCollection("customer");
        MongoCollection<Document> itemCollection = database.getCollection("item");
        MongoCollection<Document> new_orderCollection = database.getCollection("new_order");
        Random random = new Random();
                for (int i = 0; i < 10000; i++) {
                    //long documentCount = customerCollection.countDocuments();
                    //int randomNumber = random.nextInt((int) documentCount);
                    //Document randomDocument = customerCollection.find().skip(randomNumber).limit(1).first();
                    Document randomDocument = customerCollection.find().first();
                    if(randomDocument != null) {
                        DeleteResult delResult = customerCollection.deleteOne(randomDocument);
                    }

                    //long itemCount = itemCollection.countDocuments();
                    //int itemNumber = random.nextInt((int) itemCount);
                    //Document nextDoc = itemCollection.find().skip(itemNumber).limit(1).first();
                    Document nextDoc = itemCollection.find().first();
                    if(randomDocument != null) {
                        DeleteResult delResult = itemCollection.deleteOne(nextDoc);
                    }


                    //long new_orderCount = new_orderCollection.countDocuments();
                    //int new_orderNumber = random.nextInt((int) new_orderCount);
                    //nextDoc = new_orderCollection.find().skip(new_orderNumber).limit(1).first();
                    nextDoc = new_orderCollection.find().first();
                    if(randomDocument != null) {
                        DeleteResult delResult = new_orderCollection.deleteOne(nextDoc);
                    }

                }
        transactionCount = transactionCount + 3000;
    }
    public static void createFifty(MongoDatabase database){
        MongoCollection<Document> customerCollection = database.getCollection("customer");
        long documentCount = customerCollection.countDocuments();
        if(documentCount <0) {
            for (int i = 0; i < 50000; i++) {
                Customer customer = new Customer();
                Document customerDoc = customer.getAsDoc();
                InsertOneResult result = customerCollection.insertOne(customerDoc);
                BsonValue id = result.getInsertedId();
                customer = null;
                System.out.println("Customer Eklendi * " + id + "   *   " + LocalDateTime.now().toString());
                transactionCount = transactionCount +50000;
            }
        }
        MongoCollection<Document> itemCollection = database.getCollection("item");
        documentCount = itemCollection.countDocuments();
        if(documentCount <50000) {
            for (int i = 0; i < 50000; i++) {
                Item item = new Item();
                Document itemDoc = item.getAsDoc();
                InsertOneResult result = itemCollection.insertOne(itemDoc);
                BsonValue id = result.getInsertedId();
                item = null;
                System.out.println("Item Eklendi * " + id + "   *   " + LocalDateTime.now().toString());
                transactionCount = transactionCount +50000;
            }
        }
        MongoCollection<Document> new_orderCollection = database.getCollection("new_order");
        documentCount = new_orderCollection.countDocuments();
        if(documentCount <1000) {
            for (int i = 0; i < 1000; i++) {
                New_Order new_order = new New_Order();
                Document new_orderDoc = new_order.getAsDoc();
                InsertOneResult result = new_orderCollection.insertOne(new_orderDoc);
                BsonValue id = result.getInsertedId();
                new_order = null;
                System.out.println("new_order Eklendi * " + id + "   *   " + LocalDateTime.now().toString());
                transactionCount = transactionCount +50000;
            }
        }

    }
    public static void updateEndDate(MongoDatabase database,String date){

        MongoCollection<Document> historyCollection = database.getCollection("history");
        Document nextDoc = historyCollection.find().skip(historyCount).limit(1).first();
        Bson updates  = Updates.combine(Updates.set("h_date",date));
        UpdateResult upResult = historyCollection.updateOne(nextDoc, updates);
        historyCount++;
        if(historyCount >49990){
            historyCount = 0;
            historyCountRound++;
        }
        transactionCount++;
    }
    public static void updateTransactionCount(MongoDatabase database,String date){
        MongoCollection<Document> historyCollection = database.getCollection("history");
        MongoCollection<Document> customerCollection = database.getCollection("customer");
        MongoCollection<Document> districtCollection = database.getCollection("district");
        MongoCollection<Document> order_lineCollection = database.getCollection("order_line");
        MongoCollection<Document> new_orderCollection = database.getCollection("new_order");
        MongoCollection<Document> itemCollection = database.getCollection("item");
        MongoCollection<Document> ordersCollection = database.getCollection("orders");
        MongoCollection<Document> warehouseCollection = database.getCollection("warehouse");
        for (int i = 0; i < 2; i++) {
            //Document nextDoc = historyCollection.find().skip(historyCount).limit(1).first();
            Document nextDoc = historyCollection.find().first();
            //Bson updates  = Updates.combine(Updates.set("h_date",date),Updates.addToSet("h_amount",transactionCount),Updates.addToSet("h_d_id",historyCountRound));
            Bson updates = Updates.combine(Updates.set("h_date", date));
            historyCollection.updateOne(nextDoc, updates);

            //long customerCount = customerCollection.countDocuments();
            //Random random = new Random();
            //int customerNumber = random.nextInt((int) customerCount);
            //nextDoc = customerCollection.find().skip(customerNumber).limit(1).first();
            nextDoc = customerCollection.find().first();
            //updates  = Updates.combine(Updates.set("c_credit_lim",customerNumber),Updates.addToSet("c_ytd_payment",transactionCount),Updates.addToSet("c_delivery_cnt",historyCountRound));
            updates = Updates.combine(Updates.set("c_credit_lim", 0));
            customerCollection.updateOne(nextDoc, updates);

            //long district = districtCollection.countDocuments();
            //int districtNumber = random.nextInt((int) district);
            //nextDoc = districtCollection.find().skip(districtNumber).limit(1).first();
            nextDoc = districtCollection.find().first();
            //updates  = Updates.combine(Updates.set("d_ytd",districtNumber),Updates.addToSet("d_next_o_id",transactionCount),Updates.addToSet("d_tax",historyCountRound));
            updates = Updates.combine(Updates.set("d_ytd", 0));
            districtCollection.updateOne(nextDoc, updates);


            //long itemCount = itemCollection.countDocuments();
            //int itemNumber = random.nextInt((int) itemCount);
            //nextDoc = itemCollection.find().skip(itemNumber).limit(1).first();
            nextDoc = itemCollection.find().first();
            //updates  = Updates.combine(Updates.set("updateTime",date),Updates.addToSet("i_price",transactionCount),Updates.addToSet("i_im_id",historyCountRound));
            updates = Updates.combine(Updates.set("updateTime", date));
            itemCollection.updateOne(nextDoc, updates);


            //long order_lineCount = order_lineCollection.countDocuments();
            //int order_lineNumber = random.nextInt((int) order_lineCount);
            //nextDoc = order_lineCollection.find().skip(order_lineNumber).limit(1).first();
            nextDoc = order_lineCollection.find().first();
            //updates  = Updates.combine(Updates.set("delivery_d",date),Updates.addToSet("ol_number",transactionCount),Updates.addToSet("ol_amount",historyCountRound));
            updates = Updates.combine(Updates.set("delivery_d", date));
            order_lineCollection.updateOne(nextDoc, updates);


            //long new_orderCount = new_orderCollection.countDocuments();
            //int new_orderNumber = random.nextInt((int) new_orderCount);
            //nextDoc = new_orderCollection.find().skip(new_orderNumber).limit(1).first();
            nextDoc = new_orderCollection.find().first();
            //updates  = Updates.combine(Updates.set("updateTime",date),Updates.addToSet("no_d_id",transactionCount),Updates.addToSet("no_w_id",historyCountRound));
            updates = Updates.combine(Updates.set("updateTime", date));
            new_orderCollection.updateOne(nextDoc, updates);


            //long ordersCount = order_lineCollection.countDocuments();
            //int ordersNumber = random.nextInt((int) ordersCount);
            //Document order = historyCollection.find().skip(ordersNumber).limit(1).first();
            Document order = historyCollection.find().first();
            //updates  = Updates.combine(Updates.set("o_entry_d",date),Updates.addToSet("o_ol_cnt",transactionCount),Updates.addToSet("o_all_local",historyCountRound));
            updates = Updates.combine(Updates.set("o_entry_d", date));
            ordersCollection.updateOne(order, updates);


            //long warehouseCount = warehouseCollection.countDocuments();
            //int warehouseNumber = random.nextInt((int) warehouseCount);
            //nextDoc = warehouseCollection.find().skip(warehouseNumber).limit(1).first();
            nextDoc = warehouseCollection.find().first();
            //updates  = Updates.combine(Updates.set("w_street_1",date),Updates.addToSet("w_ytd",transactionCount),Updates.addToSet("w_tax",historyCountRound));
            updates = Updates.combine(Updates.set("w_street_1", date));
            warehouseCollection.updateOne(nextDoc, updates);

            historyCount++;
            if (historyCount > 49990) {
                historyCount = 0;
                historyCountRound++;
            }
            transactionCount = transactionCount + 70;
        }
        }
    public static void assignRelatedDistrictId(MongoDatabase database){


        MongoCollection<Document> customerCollection = database.getCollection("customer");
        MongoCollection<Document> districtCollection = database.getCollection("district");

        long documentCount = customerCollection.countDocuments();
        Random random = new Random();
        int randomNumber = random.nextInt((int) documentCount);
        Bson projection = Projections.fields(
                Projections.include("c_data"),Projections.excludeId()
        );
        Document randomDocument = customerCollection.find().skip(randomNumber).limit(1).projection(projection).first();
        String custumerDistrictId = randomDocument.get("c_data").toString();
        if(custumerDistrictId.length() != 24){
            custumerDistrictId = "000000000000000000000000";
        }
        Document districtDocument = districtCollection.find(eq("_id",new ObjectId(custumerDistrictId))).first();
        if(districtDocument == null){
            Bson projectionDistrict = Projections.fields(
                    Projections.include("_id")
            );
            documentCount = districtCollection.countDocuments();
            randomNumber =random.nextInt((int) documentCount);
            Document randomDistrictDocument = customerCollection.find().skip(randomNumber).limit(1).projection(projectionDistrict).first();
            Bson query  = Filters.eq("c_data",custumerDistrictId);
            Bson updates  = Updates.combine(Updates.set("c_data",randomDistrictDocument.get("_id").toString()));
            UpdateResult upResult = customerCollection.updateOne(query, updates);
        }
        transactionCount++;
    }

    public static void assignRelatedWarehouseId(MongoDatabase database){

        MongoCollection<Document> customerCollection = database.getCollection("customer");
        MongoCollection<Document> districtCollection = database.getCollection("warehouse");

        long documentCount = customerCollection.countDocuments();
        Random random = new Random();
        int randomNumber = random.nextInt((int) documentCount);
        Bson projection = Projections.fields(
                Projections.include("c_since"),Projections.excludeId()
        );
        Document randomDocument = customerCollection.find().skip(randomNumber).limit(1).projection(projection).first();
        String custumerDistrictId = randomDocument.get("c_since").toString();
        if(custumerDistrictId.length() != 24){
            custumerDistrictId = "000000000000000000000000";
        }
        Document districtDocument = districtCollection.find(eq("_id",new ObjectId(custumerDistrictId))).first();
        if(districtDocument == null){
            Bson projectionDistrict = Projections.fields(
                    Projections.include("_id")
            );
            documentCount = districtCollection.countDocuments();
            randomNumber =random.nextInt((int) documentCount);
            Document randomDistrictDocument = customerCollection.find().skip(randomNumber).limit(1).projection(projectionDistrict).first();
            Bson query  = Filters.eq("c_since",custumerDistrictId);
            Bson updates  = Updates.combine(Updates.set("c_since",randomDistrictDocument.get("_id").toString()));
            UpdateResult upResult = customerCollection.updateOne(query, updates);
        }
        transactionCount++;
    }
    public static void makeAllSelectedBalance(MongoDatabase database, int discount,int balance){

        MongoCollection<Document> customerCollection = database.getCollection("customer");
        Bson projectionFields = Projections.fields(
                Projections.include("c_discount", "c_balance"),Projections.excludeId());
        Document doc = customerCollection.find(eq("c_discount",discount)).projection(projectionFields).sort(Sorts.descending("c_balance")).first();
        int i =0;
        while(doc != null && i !=5) {
            Bson updates = Updates.combine(Updates.set("c_balance", balance));
            UpdateResult upResult = customerCollection.updateOne(doc, updates);
            doc = customerCollection.find(eq("c_discount",discount)).projection(projectionFields).sort(Sorts.descending("c_balance")).first();
            i++;
        }
        transactionCount = transactionCount+5;
    }
    public static void deleteAllCollections(MongoDatabase database){
        database.getCollection("customer").drop();
        database.getCollection("district").drop();
        database.getCollection("history").drop();
        database.getCollection("order_line").drop();
        database.getCollection("orders").drop();
        database.getCollection("new_order").drop();
        database.getCollection("item").drop();
        database.getCollection("warehouse").drop();
    }
}

/*
<dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.4</version>
</dependency>
*/


