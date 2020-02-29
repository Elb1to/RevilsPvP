package eu.revils.revilspvp.mongo;

import eu.revils.revilspvp.RevilsPvP;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Collections;

@Getter
public class MongoManager {
    @Getter
    public static MongoManager instance;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> statsCollection;

    public MongoManager() {
        instance = this;
        try {
            if (RevilsPvP.getInstance().getConfig().getBoolean("Mongo.Auth.Enabled")) {
                final MongoCredential credential = MongoCredential.createCredential(
                        RevilsPvP.getInstance().getConfig().getString("Mongo.Username"),
                        RevilsPvP.getInstance().getConfig().getString("Mongo.Database"),
                        RevilsPvP.getInstance().getConfig().getString("Mongo.Password").toCharArray()
                );
                mongoClient = new MongoClient(new ServerAddress(RevilsPvP.getInstance().getConfig().getString("Mongo.Host"), RevilsPvP.getInstance().getConfig().getInt("Mongo.Port")), Collections.singletonList(credential));
            } else {
                mongoClient = new MongoClient(RevilsPvP.getInstance().getConfig().getString("Mongo.Host"), RevilsPvP.getInstance().getConfig().getInt("Mongo.Port"));
            }
            mongoDatabase = mongoClient.getDatabase(RevilsPvP.getInstance().getConfig().getString("Mongo.Host"));
            statsCollection = mongoDatabase.getCollection("playerStatistics");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§6[RevilsPvP] §cFailed to connect to MongoDB");
            Bukkit.getServer().getPluginManager().disablePlugin(RevilsPvP.getInstance());
        }
    }
}