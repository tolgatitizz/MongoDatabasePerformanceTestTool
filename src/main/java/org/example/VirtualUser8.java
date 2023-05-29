package org.example;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonValue;
import org.bson.Document;

import java.time.LocalDateTime;

import static org.example.Main.connectionString;

public class VirtualUser8 implements Runnable{
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
            MongoCollection<Document> collection = database.getCollection("warehouse");
            for (int i = 0; i < 1000; i++) {
                Warehouse warehouse = new Warehouse();
                Document warehouseDoc = warehouse.getAsDoc();
                InsertOneResult result = collection.insertOne(warehouseDoc);
                BsonValue id = result.getInsertedId();
                System.out.println(id);
                warehouse = null;
                System.out.println("Warehouse Eklendi * " + id + "   *   " + LocalDateTime.now().toString());
            }

            mongoClient.close();
        }
    }
}
