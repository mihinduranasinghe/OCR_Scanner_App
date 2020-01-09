package OCR.Server;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class Database {
    public static void testDBConnection() {
        MongoClientURI uri = new MongoClientURI("mongodb://cybercats:ZJwvjnuxUbLexSxj@ocrclustor-cvf22.mongodb.net/test?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("ocr");

        MongoCollection collection = database.getCollection("test");

        Document document = new Document("Name", "Mihindu");
        document.append("Age", "22");
        document.append("Gender", "Male");
        collection.insertOne(document);
    }
}
