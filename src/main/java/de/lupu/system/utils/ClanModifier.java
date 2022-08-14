package de.lupu.system.utils;

import de.lupu.system.Main;

import java.util.UUID;

public class ClanModifier {

    public static ClanModifier fromTag(String tag){
        ClanModifier cm = new ClanModifier(Main.getPlugin().getClanManager().getClanByTag(tag));
        return cm;
    }

    public static ClanModifier fromName(String name){
        ClanModifier cm = new ClanModifier(Main.getPlugin().getClanManager().getClanByName(name));
        return cm;
    }

    Clan clan;

    public ClanModifier(Clan clan) {
        this.clan = clan;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public void addMember(UUID user){
        clan.addMember(user);
    }

    public void removeMember(UUID member){
        clan.removeMember(member);
    }

    public ClanMember getMember(UUID member){
        return clan.getMember(member);
    }

    public ClanRole getRole(UUID member){
        return getMember(member).getRole();
    }

    public void setRole(UUID member, ClanRole role){
        getMember(member).setRole(role);
    }

    public void grantRole(UUID member){
        switch (getRole(member)){
            case MEMBER -> setRole(member, ClanRole.MODERATOR);
            case MODERATOR -> setRole(member, ClanRole.ADMIN);
            case ADMIN -> setRole(member, ClanRole.OWNER);
        }
    }

    public void refuseRole(UUID member){
        switch (getRole(member)){
            case OWNER -> setRole(member, ClanRole.ADMIN);
            case ADMIN -> setRole(member, ClanRole.MODERATOR);
            case MODERATOR -> setRole(member, ClanRole.MEMBER);
        }
    }

    public void kickMember(UUID member){
        removeMember(member);
    }



}
