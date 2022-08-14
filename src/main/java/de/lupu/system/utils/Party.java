package de.lupu.system.utils;

import java.util.UUID;

public class Party {

    UUID partyOwner;
    boolean pub;
    PartyMemberList members;

    public Party(UUID partyOwner) {
        this.partyOwner = partyOwner;
    }

    public Party(UUID partyOwner, boolean pub, PartyMemberList members) {
        this.partyOwner = partyOwner;
        this.pub = pub;
        this.members = members;
    }

    public UUID getPartyOwner() {
        return partyOwner;
    }

    public void setPartyOwner(UUID partyOwner) {
        this.partyOwner = partyOwner;
    }

    public boolean isPub() {
        return pub;
    }

    public void setPub(boolean pub) {
        this.pub = pub;
    }

    public PartyMemberList getMembers() {
        return members;
    }

    public void setMembers(PartyMemberList members) {
        this.members = members;
    }

}
