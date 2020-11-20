package fr.patatedouce.data;

import fr.patatedouce.Vision;
import fr.patatedouce.database.MongoDB;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlayerListData {

    @Getter public static List<String> playersList = new ArrayList<>();
    private String staffListName = "StaffPlayTime-List";
    private boolean staffListLoaded;

    public void load() {
        MongoDB mongoDB = Vision.getInstance().getMongoDB();
        Document document = mongoDB.getStaffList().find(Filters.eq("identificador", this.staffListName)).first();

        if (document != null) {
            this.playersList = (List<String>) document.get("staff-list");
        }

        this.staffListLoaded = true;
        Vision.getInstance().getLogger().info("'data loaded:D");
    }

    public void save() {
        Document document = new Document();

        document.put("identificador", this.staffListName);
        document.put("staff-list", playersList);

        MongoDB mongoDB = Vision.getInstance().getMongoDB();

        mongoDB.getStaffList().replaceOne(Filters.eq("identificador", this.staffListName), document, new UpdateOptions().upsert(true));
        this.staffListLoaded = false;
    }

    public boolean isInList(String uuid) {
        return playersList.contains(uuid);
    }
}
