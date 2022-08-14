package de.lupu.system.utils;

import java.util.UUID;

public class ClanMember {

    public static ClanMember fromString(String s){
        String[] parts = s.replace("[", "").replace("]", "").split(";");
        ClanMember m = new ClanMember(UUID.fromString(parts[0]), ClanRole.valueOf(parts[1]));
        return m;
    }

    UUID uuid;
    ClanRole role;

    public ClanMember(UUID uuid, ClanRole role) {
        this.uuid = uuid;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ClanRole getRole() {
        return role;
    }

    public void setRole(ClanRole role) {
        this.role = role;
    }

    public String toString(){
        return "[" + uuid.toString() + ";" + role.toString() + "]";
    }

}
