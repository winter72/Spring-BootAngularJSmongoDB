package conference.itstep.conference.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conference.itstep.conference.interfaces_models.RolesRepository;
import conference.itstep.conference.models_entitys.Roles;
import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.List;
import java.util.Optional;

@Configuration
public class MongoConfig {


 private  boolean findName(MongoDatabase database, String ndb){

     for (String name : database.listCollectionNames()) {
         if(name.equals(ndb)) {
             return true;
         }
     }
     return false;
 }

 @Bean
 public MongoDbFactory mongoDbFactory() throws Exception {
     MongoClient mongoClient =new MongoClient("localhost",27017);// new MongoClient("localhost",270
     MongoDatabase database = mongoClient.getDatabase("conferencemongo");

    if (!findName(database,"users")) {
         try {
             database.createCollection("users");
         } catch (MongoCommandException e) {
             System.out.println("Exist");
             e.getErrorMessage();
         }
     }

     if (!findName(database, "conferences")) {
         try {
             database.createCollection("conferences");
         } catch (MongoCommandException e) {
             e.getErrorMessage();
         }
     }

     if (!findName(database, "roles")) {
         try {
             database.createCollection("roles");
         } catch (MongoCommandException e) {
             e.getErrorMessage();
         }
     }

     if (!findName(database, "photo_userss")) {
         try {
             database.createCollection("photo_users");
         } catch (MongoCommandException e) {
             e.getErrorMessage();
         }
     }

     if (database.getCollection("roles").countDocuments() == 0) {

         MongoCollection<Document> collection = database.getCollection("roles");

         Document document = new Document()
                 .append("rolename", "ROLE_ADMIN");
         collection.insertOne(document);
         document = new Document()
                 .append("rolename", "ROLE_USER");
         collection.insertOne(document);
         document = new Document()
                 .append("rolename", "ROLE_ANONIMUS");
         collection.insertOne(document);
        // mongoClient.close();
     }
     return new SimpleMongoDbFactory(mongoClient,"conferencemongo");
     //return new SimpleMongoDbFactory(new MongoClient("127.0.0.1"), "conferencemongo");
 }

   @Bean
   public MongoTemplate mongoTemplate() throws Exception {

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
        return mongoTemplate;
    }

}
