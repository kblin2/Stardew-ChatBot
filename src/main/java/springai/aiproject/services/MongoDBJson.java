package springai.aiproject.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: Kenny Lin
 * @Date: 6/18/2024 10:20 AM
 * @Email: kennethlin728@gmail.com
 */
public class MongoDBJson {
    public static void main(String[] args) {
        // MongoDB connection string
        String uri = "mongodb://localhost:27017";

        // Database and collection names
        String dbName = "mydatabase";
        String collectionName = "information";

        // Create MongoDB client
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        // Parse JSON file and insert data into MongoDB
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read JSON file
            List<Map<String, Object>> items = mapper.readValue(new File("information.json"), new TypeReference<List<Map<String, Object>>>(){});

            // Insert each item into MongoDB collection
            for (Map<String, Object> item : items) {
                Document doc = new Document(item);
                collection.insertOne(doc);
            }
            System.out.println("Data successfully inserted into MongoDB!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Query the MongoDB collection for information about the "Eiffel Tower"
        Document query = new Document("name", "Eiffel Tower");
        Document result = collection.find(query).first();

        if (result != null) {
            System.out.println("Information about the Eiffel Tower:");
            System.out.println(result.toJson());
        } else {
            System.out.println("No information found for the Eiffel Tower.");
        }

        // Close the MongoDB client
        mongoClient.close();
    }
}
