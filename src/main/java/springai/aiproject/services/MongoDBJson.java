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

    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "mydatabase";
    private static final String COLLECTION_NAME = "information";
    private static final String JSON_FILE_PATH = "src/main/resources/information.json";

    private static MongoClient createMongoClient(String connectionString) {
        return MongoClients.create(connectionString);
    }

    private static List<Map<String, Object>> readJsonFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), new TypeReference<List<Map<String, Object>>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON file at " + filePath, e);
        }
    }

    private static void insertDataIntoCollection(MongoCollection<Document> collection, List<Map<String, Object>> items) {
        for (Map<String, Object> item : items) {
            Document doc = new Document(item);
            collection.insertOne(doc);
        }
        System.out.println("Data successfully inserted into MongoDB!");
    }

    private static void queryCollection(MongoCollection<Document> collection, String name) {
        Document query = new Document("name", name);
        Document result = collection.find(query).first();

        if (result != null) {
            System.out.println("Information about " + name + ":");
            System.out.println(result.toJson());
        } else {
            System.out.println("No information found for " + name + ".");
        }
    }
}
