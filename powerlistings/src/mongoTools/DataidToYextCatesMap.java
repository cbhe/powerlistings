package mongoTools;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.print.Doc;

import org.bson.Document;

public class DataidToYextCatesMap {
	
    private HashMap<String, String> map;

    public HashMap<String, String> getMap() {
        if(map == null) {
            map = new HashMap<String, String>();
            MongoCollection<Document> collection = Connection.getMongoCollection("cates_rcv");
            FindIterable<Document> iterable = collection.find();
            String dataid;
            String yext_cates;
            for (Document document: iterable) {
                dataid = document.getString("dataid");
                yext_cates = document.getString("yextCates");
                map.put(dataid, yext_cates);
            }
        }
        return map;
    }

    public boolean put(String dataid, String yextCates) {
        Object object = getMap().put(dataid, yextCates);
        
        //查找cates_rcv中有无此dataid对应文档，如果没有则新建
        MongoCollection<Document> collection = Connection.getMongoCollection("cates_rcv");
        
        FindIterable<Document> iterable = collection.find(new Document("dataid", dataid));
        if(iterable.first() == null) {
            collection.insertOne((new Document()).append("dataid", dataid).append("yextCates", yextCates));
        }
        
        return object != null;
    }
}
