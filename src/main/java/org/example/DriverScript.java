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
import static org.example.Main.connectionString;

public class DriverScript implements Runnable{



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
            int j;
            int k;
            int timer=0;
            //Methods
            while(timer != 1000000){
                timer++;
                Random random = new Random();
                i= random.nextInt(5);
                j= random.nextInt(101);
                k= random.nextInt(101);
                switch(i) {
                    case 0:
                        assignRelatedDistrictId(database);
                        break;
                    case 1:
                        assignRelatedWarehouseId(database);
                        break;
                    case 2:
                        customerDiscountCounter(database);
                        break;
                    case 3:
                        makeAllSelectedBalance(database,j,k);
                        break;
                    case 4:
                        deleteFiftyCreateFifty(database);
                        break;
                }
            }
        }
    }

    public static void assignRelatedDistrictId(MongoDatabase database){

        final MongoClient client = MongoClients.create(connectionString);
        final ClientSession clientSession = client.startSession();

        TransactionBody txnBody = new TransactionBody<String>(){
            public String execute() {
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
                System.out.println(districtDocument);
                if(districtDocument == null){
                    Bson projectionDistrict = Projections.fields(
                            Projections.include("_id")
                    );
                    documentCount = districtCollection.countDocuments();
                    randomNumber =random.nextInt((int) documentCount);
                    Document randomDistrictDocument = customerCollection.find().skip(randomNumber).limit(1).projection(projectionDistrict).first();
                    System.out.println(" -->" +randomDistrictDocument.get("_id").toString());
                    Bson query  = Filters.eq("c_data",custumerDistrictId);
                    Bson updates  = Updates.combine(Updates.set("c_data",randomDistrictDocument.get("_id").toString()));
                    UpdateResult upResult = customerCollection.updateOne(query, updates);
                    System.out.println(upResult);
                }
                return "Transaction Has Been Completed";
            }
        };

        try {
            clientSession.withTransaction(txnBody);
        } catch (RuntimeException e){
            System.out.println(e);
        }finally{
            clientSession.close();
        }
    }

    public static void assignRelatedWarehouseId(MongoDatabase database){

        final MongoClient client = MongoClients.create(connectionString);
        final ClientSession clientSession = client.startSession();

        TransactionBody txnBody = new TransactionBody<String>(){
            public String execute() {
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
                System.out.println(districtDocument);
                if(districtDocument == null){
                    Bson projectionDistrict = Projections.fields(
                            Projections.include("_id")
                    );
                    documentCount = districtCollection.countDocuments();
                    randomNumber =random.nextInt((int) documentCount);
                    Document randomDistrictDocument = customerCollection.find().skip(randomNumber).limit(1).projection(projectionDistrict).first();
                    System.out.println(" -->" +randomDistrictDocument.get("_id").toString());
                    Bson query  = Filters.eq("c_since",custumerDistrictId);
                    Bson updates  = Updates.combine(Updates.set("c_since",randomDistrictDocument.get("_id").toString()));
                    UpdateResult upResult = customerCollection.updateOne(query, updates);
                    System.out.println(upResult);
                }
                return "Transaction Has Been Completed";
            }
        };

        try {
            clientSession.withTransaction(txnBody);
        } catch (RuntimeException e){
            System.out.println(e);
        }finally{
            clientSession.close();
        }
    }

    public static void customerDiscountCounter(MongoDatabase database){
        final MongoClient client = MongoClients.create(connectionString);
        final ClientSession clientSession = client.startSession();
        TransactionBody txnBody = new TransactionBody<String>(){
            public String execute() {
                MongoCollection<Document> customerCollection = database.getCollection("customer");
                for (int i = 0; i < 10; i++) {
                    Bson projectionFields = Projections.fields(
                            Projections.include("c_d_id","c_discount", "c_balance"));
                    //,
                    //                        Projections.excludeId()

                    Document doc = customerCollection.find(eq("c_discount",i)).projection(projectionFields).sort(Sorts.descending("c_balance")).first();
                    if(doc == null){
                        System.out.println(i + " İndirim Oranında  Veri Bulunamadı");
                    }
                    else{
                        System.out.println(doc.toJson());
                    }
                }
                return "Transaction Has Been Completed";
            }
        };
        try {
            clientSession.withTransaction(txnBody);
        } catch (RuntimeException e){
            System.out.println(e);
        }finally{
            clientSession.close();
        }
    }

    public static void makeAllSelectedBalance(MongoDatabase database, int discount,int balance){

        final MongoClient client = MongoClients.create(connectionString);
        final ClientSession clientSession = client.startSession();

        TransactionBody txnBody = new TransactionBody<String>(){
            public String execute() {
                MongoCollection<Document> customerCollection = database.getCollection("customer");
                Bson projectionFields = Projections.fields(
                        Projections.include("c_discount", "c_balance"),Projections.excludeId());
                Document doc = customerCollection.find(eq("c_discount",discount)).projection(projectionFields).sort(Sorts.descending("c_balance")).first();
                int i =0;
                while(doc != null && i !=5) {
                    Bson updates = Updates.combine(Updates.set("c_balance", balance));
                    UpdateResult upResult = customerCollection.updateOne(doc, updates);
                    System.out.println(upResult);
                    doc = customerCollection.find(eq("c_discount",discount)).projection(projectionFields).sort(Sorts.descending("c_balance")).first();
                    i++;
                }
                return "Transaction Has Been Completed";
            }
        };

        try {
            clientSession.withTransaction(txnBody);
        } catch (RuntimeException e){
            System.out.println(e);
        }finally{
            clientSession.close();
        }
    }
    public static void deleteFiftyCreateFifty(MongoDatabase database){

        final MongoClient client = MongoClients.create(connectionString);
        final ClientSession clientSession = client.startSession();

        TransactionBody txnBody = new TransactionBody<String>(){
            public String execute() {
                MongoCollection<Document> customerCollection = database.getCollection("customer");
                for (int i = 0; i < 50; i++) {
                    long documentCount = customerCollection.countDocuments();
                    Random random = new Random();
                    int randomNumber = random.nextInt((int) documentCount);
                    Document randomDocument = customerCollection.find().skip(randomNumber).limit(1).first();
                    if(randomDocument != null) {
                        DeleteResult delResult = customerCollection.deleteOne(randomDocument);
                        System.out.println("Deleted a document:" + delResult);
                    }
                    Customer customer = new Customer();
                    Document customerDoc = customer.getAsDoc();
                    InsertOneResult result = customerCollection.insertOne(customerDoc);
                    BsonValue id = result.getInsertedId();
                    customer = null;
                    System.out.println("Customer Eklendi * " + id + "   *   " + LocalDateTime.now().toString());
                }
                return "Transaction Has Been Completed";
            }
        };
        try {
            clientSession.withTransaction(txnBody);
        } catch (RuntimeException e){
            System.out.println(e);
        }finally{
            clientSession.close();
        }

    }
}

/*
<dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.4</version>
</dependency>
*/