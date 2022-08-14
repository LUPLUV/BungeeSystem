package de.lupu.spigot.utils;

import java.util.UUID;

public class RequestsListEntry {

    public static RequestsListEntry fromString(String s){
        String[] parts = s.replace("[", "").replace("]", "").split(";");
        RequestsListEntry rle = new RequestsListEntry(UUID.fromString(parts[0]), ReqType.valueOf(parts[1]));
        return rle;
    }

    UUID uuid;
    ReqType reqType;

    public RequestsListEntry(UUID uuid, ReqType reqType) {
        this.uuid = uuid;
        this.reqType = reqType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ReqType getReqType() {
        return reqType;
    }

    public void setReqType(ReqType reqType) {
        this.reqType = reqType;
    }

    public String toString(){
        return "[" + uuid.toString() + ";" + reqType.toString() + "]";
    }

}
