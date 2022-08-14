package de.lupu.system.utils;

import java.util.UUID;

public class PartyMember {

    public static PartyMember fromString(String s){
        String[] parts = s.replace("[", "").replace("]", "").split(";");
        PartyMember m = new PartyMember(UUID.fromString(parts[0]), PartyRole.valueOf(parts[1]));
        return m;
    }

    UUID uuid;
    PartyRole role;

    public PartyMember(UUID uuid, PartyRole role) {
        this.uuid = uuid;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PartyRole getRole() {
        return role;
    }

    public void setRole(PartyRole role) {
        this.role = role;
    }

    public String toString(){
        return "[" + uuid.toString() + ";" + role.toString() + "]";
    }

}
