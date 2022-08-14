package de.lupu.system.utils;

import java.util.UUID;

public class Clan {

    String name;
    String tag;
    UUID owner;
    Color color;
    String creationDate;
    boolean togglePublic;
    ClanMemberList members;

    public Clan() {
    }

    public Clan(String name, String tag, UUID owner, Color color) {
        this.name = name;
        this.tag = tag;
        this.owner = owner;
        this.color = color;
    }

    public Clan(String name, String tag, UUID owner) {
        this.name = name;
        this.tag = tag;
        this.owner = owner;
    }

    public Clan(String name, String tag, UUID owner, Color color, String creationDate) {
        this.name = name;
        this.tag = tag;
        this.owner = owner;
        this.color = color;
        this.creationDate = creationDate;
    }

    public Clan(String name, String tag, UUID owner, Color color, String creationDate, boolean togglePublic) {
        this.name = name;
        this.tag = tag;
        this.owner = owner;
        this.color = color;
        this.creationDate = creationDate;
        this.togglePublic = togglePublic;
    }

    public Clan(String name, String tag, UUID owner, Color color, String creationDate, boolean togglePublic, ClanMemberList members) {
        this.name = name;
        this.tag = tag;
        this.owner = owner;
        this.color = color;
        this.creationDate = creationDate;
        this.togglePublic = togglePublic;
        this.members = members;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isTogglePublic() {
        return togglePublic;
    }

    public void setTogglePublic(boolean togglePublic) {
        this.togglePublic = togglePublic;
    }

    public ClanMemberList getMembers() {
        return members;
    }

    public void setMembers(ClanMemberList members) {
        this.members = members;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void addMember(UUID user){
        ClanMember cm = new ClanMember(user, ClanRole.MEMBER);
        this.members.add(cm);
    }

    public void removeMember(UUID member){
        this.members.remove(member);
    }

    public ClanMember getMember(UUID member){
        return this.members.get(member);
    }
}
