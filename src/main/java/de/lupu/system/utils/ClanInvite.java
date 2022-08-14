package de.lupu.system.utils;

import java.util.UUID;

public class ClanInvite {

    public static ClanInvite fromString(String s){
        String[] parts = s.replace("[", "").replace("]", "").split(";");
        ClanInvite cl = new ClanInvite(UUID.fromString(parts[0]), parts[1]);
        return cl;
    }

    UUID uuid;
    String clanName;

    public ClanInvite(UUID uuid, String clanName) {
        this.uuid = uuid;
        this.clanName = clanName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

    public String toString(){
        return "[" + uuid.toString() + ";" + clanName + "]";
    }
}
