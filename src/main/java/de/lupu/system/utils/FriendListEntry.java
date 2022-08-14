package de.lupu.system.utils;

import java.util.Locale;
import java.util.UUID;

public class FriendListEntry {

    public static FriendListEntry fromString(String s){
        String[] parts = s.replace("[", "").replace("]", "").split(";");
        FriendListEntry e = new FriendListEntry(UUID.fromString(parts[0]), Boolean.valueOf(parts[1]), parts[2]);
        return e;
    }

    UUID uuid;
    boolean favourite;
    String date;

    public FriendListEntry(UUID uuid, boolean favourite, String date) {
        this.uuid = uuid;
        this.favourite = favourite;
        this.date = date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString(){
        return "[" + uuid.toString() + ";" + String.valueOf(favourite).toLowerCase(Locale.ROOT) + ";" + date + "]";
    }

}
