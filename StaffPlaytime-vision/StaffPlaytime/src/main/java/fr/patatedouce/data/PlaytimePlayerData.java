package fr.patatedouce.data;

import fr.patatedouce.Vision;
import fr.patatedouce.database.MongoDB;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlaytimePlayerData {

    @Getter public static List<PlaytimePlayerData> playersCountingPlaytime = new ArrayList<>();

    private String name;
    private UUID uuid;
    private Long playtime = 0L;
    private boolean dataLoaded;

    public PlaytimePlayerData(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        playersCountingPlaytime.add(this);

        this.dataLoaded = false;
    }

    public void saveData() {
        Document document = new Document();

        document.put("name", this.name);
        document.put("uuid", getUuid().toString());
        document.put("playtime", this.playtime);

        this.dataLoaded = false;

        MongoDB mongoDB = Vision.getInstance().getMongoDB();
        mongoDB.getStaffData().replaceOne(Filters.eq("name", this.name), document, new UpdateOptions().upsert(true));

        playersCountingPlaytime.remove(this);
    }

    public void loadData() {
        MongoDB mongoDB = Vision.getInstance().getMongoDB();
        Document document = mongoDB.getStaffData().find(Filters.eq("name", this.name)).first();

        if (document != null) {
            this.playtime = document.getLong("playtime");
        }
        this.dataLoaded = true;
        Vision.getInstance().getLogger().info(PlaytimePlayerData.this.getName() + "'s data loaded.");
    }

    public static PlaytimePlayerData getByUuid(UUID uuid) {
        return playersCountingPlaytime.stream().filter(playtimePlayerData -> playtimePlayerData.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public static PlaytimePlayerData getByName(String uuid) {
        return playersCountingPlaytime.stream().filter(playtimePlayerData -> playtimePlayerData.getName().equals(uuid)).findFirst().orElse(null);
    }
}
