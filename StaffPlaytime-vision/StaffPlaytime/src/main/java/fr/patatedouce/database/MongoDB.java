package fr.patatedouce.database;

import fr.patatedouce.Vision;
import fr.patatedouce.utils.CC;
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

//full repo mongodb slith
public class MongoDB {

    private MongoClient client;

    private MongoDatabase mongoDatabase;

    private final String host = Vision.getInstance().getMainConfig().getString("MONGO.HOST");
    private final int port = Vision.getInstance().getMainConfig().getInt("MONGO.PORT");
    private final String database = Vision.getInstance().getMainConfig().getString("MONGO.DATABASE");
    private final boolean authentication = Vision.getInstance().getMainConfig().getBoolean("MONGO.AUTH.ENABLED");

    private final String user = Vision.getInstance().getMainConfig().getString("MONGO.AUTH.USERNAME");
    private final String password = Vision.getInstance().getMainConfig().getString("MONGO.AUTH.PASSWORD");
    private final String authDatabase = Vision.getInstance().getMainConfig().getString("MONGO.AUTH.AUTH-DATABASE");

    private boolean connected;

    private MongoCollection<Document> staffData;

    private MongoCollection<Document> staffList;


    public void connect() {
        try {
            Vision.getInstance().getLogger().info("Connecting to MongoDB...");
            if (authentication) {
                MongoCredential mongoCredential = MongoCredential.createCredential(this.user, this.authDatabase, this.password.toCharArray());
                this.client = new MongoClient(new ServerAddress(this.host, this.port), Collections.singletonList(mongoCredential));
                this.connected = true;
                Bukkit.getConsoleSender().sendMessage(CC.translate("&aSuccessfully connected to MongoDB."));
            } else {
                this.client = new MongoClient(new ServerAddress(this.host, this.port));
                this.connected = true;
                Bukkit.getConsoleSender().sendMessage(CC.translate("&aSuccessfully connected to MongoDB."));
            }
            this.mongoDatabase = this.client.getDatabase(this.database);
            this.staffData = this.mongoDatabase.getCollection("StaffPlayTime-Data");
            this.staffList = this.mongoDatabase.getCollection("StaffPlayTime-List");
        } catch (Exception e) {
            this.connected = false;
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cDisabling due to an error occurred while trying to connect to &aMongoDB."));
            Bukkit.getPluginManager().disablePlugins();
            Bukkit.shutdown();
        }
    }

    public void reconnect() {
        try {
            if (authentication) {
                MongoCredential mongoCredential = MongoCredential.createCredential(this.user, this.authDatabase, this.password.toCharArray());
                this.client = new MongoClient(new ServerAddress(this.host, this.port), Collections.singletonList(mongoCredential));
            } else {
                this.client = new MongoClient(new ServerAddress(this.host, this.port));
            }
            this.mongoDatabase = this.client.getDatabase(this.database);
            this.staffData = this.mongoDatabase.getCollection("StaffPlayTime-Data");
        } catch (Exception e) {
            Vision.getInstance().getLogger().info("[MongoDB] An error occurred while trying to connect to MongoDB.");
        }
    }

    public void disconnect() {
        if (this.client != null) {
            Vision.getInstance().getLogger().info("[MongoDB] Disconnecting...");
            this.client.close();
            this.connected = false;
            Vision.getInstance().getLogger().info("[MongoDB] Successfully disconnected.");
        }
    }
}
