import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J on 2017/3/10.
 */
public class Mongo {

    public static void main(String[] args) {
        try {
            MongoClient mongoClient = new MongoClient("192.168.9.194", 27017);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
            System.out.println("Connect to database successfully");
//            mongoDatabase.createCollection("test3");
//            System.out.println("集合创建成功");
            MongoCollection<Document> collection = mongoDatabase.getCollection("test3");
            System.out.println("集合 test3 选择成功");
            //插入文档
            /**
             * 1. 创建文档 org.bson.Document 参数为key-value的格式
             * 2. 创建文档集合List<Document>
             * 3. 将文档集合插入数据库集合中 mongoCollection.insertMany(List<Document>) 插入单个文档可以用 mongoCollection.insertOne(Document)
             * */
            Document document = new Document("title", "MongoDB").
                    append("description", "database").
                    append("likes", 100).
                    append("by", "J");
            List<Document> documents = new ArrayList<Document>();
            documents.add(document);
            collection.insertMany(documents);
            System.out.println("文档插1入成功");

            Document document2 = new Document("title", "MongoDB").
                    append("description", "database").
                    append("likes", 10000).
                    append("by", "J");
            List<Document> documents2 = new ArrayList<Document>();
            documents2.add(document2);
            collection.insertMany(documents2);
            System.out.println("文档插2入成功");

            //更新文档   将文档中likes=100的文档修改为likes=200
            collection.updateMany(Filters.eq("likes", 100), new Document("$set",new Document("likes",200)));
            //检索查看结果
            FindIterable<Document> findIterable2 = collection.find();
            MongoCursor<Document> mongoCursor2 = findIterable2.iterator();
            while(mongoCursor2.hasNext()) {
                System.out.println(mongoCursor2.next());
            }
            //删除符合条件的第一个文档
            collection.deleteOne(Filters.eq("likes", 200));
            //删除符合条件的文档
//            collection.deleteMany (Filters.eq("likes", 200));

            //检索所有文档
            /**
             * 1. 获取迭代器FindIterable<Document>
             * 2. 获取游标MongoCursor<Document>
             * 3. 通过游标遍历检索出的文档集合
             * */
            System.out.println("集合内容");
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while(mongoCursor.hasNext()){
                System.out.println(mongoCursor.next());
            }

        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }


    }

}
