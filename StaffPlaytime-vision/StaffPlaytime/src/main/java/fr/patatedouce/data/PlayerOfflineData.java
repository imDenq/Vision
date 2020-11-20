package fr.patatedouce.data;

import fr.patatedouce.Vision;
import fr.patatedouce.database.MongoDB;
import fr.patatedouce.utils.CC;
import com.mongodb.client.model.Filters;
import org.bson.Document;


public class PlayerOfflineData {

    public static boolean isHasData(String name) {
        MongoDB mongoDB = Vision.getInstance().getMongoDB();
        Document document = mongoDB.getStaffData().find(Filters.eq("name", name)).first();

        if (document == null) {
            return false;
        }

        return true;
    }

    public static boolean isOnList(String name) {
        MongoDB mongoDB = Vision.getInstance().getMongoDB();
        Document document = mongoDB.getStaffData().find(Filters.eq("name", name)).first();

        String uuid = document.getString("uuid");

        return Vision.getInstance().getPlayerListData().isInList(uuid);
    }

    public static String getTimePlayer(String name) {
        MongoDB mongoDB = Vision.getInstance().getMongoDB();
        Document document = mongoDB.getStaffData().find(Filters.eq("name", name)).first();

        Long time = document.getLong("playtime");

        return CC.formatTimeMillis(time);
    }
}
