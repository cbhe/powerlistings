package mongoTools;  
import org.bson.Document;   
import com.mongodb.MongoClient;  
import com.mongodb.client.*;  

public class Connection {
	private static MongoDatabase database;
	public static MongoDatabase getDatabase() {
		if (database == null) {
			MongoClient mongoClient = new MongoClient("10.142.102.33", 27017);
			database = mongoClient.getDatabase("powerlistings");
		}
		return database;
	}
	public static MongoCollection<Document> getMongoCollection(String collectionName){
			return database.getCollection(collectionName);
	}
}
