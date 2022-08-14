package de.lupu.system.utils;

import de.lupu.system.Main;

public class Strings {

    private static Strings instance;

    public static Strings getInstance() {
        if(instance == null){
            instance = new Strings();
        }
        return instance;
    }

    public String getGlobalPrefix(){
        return Main.getPlugin().getFileManager().getMessage("GlobalPrefix").replace("&", "§");
    }

    public String getFriendsPrefix(){
        return Main.getPlugin().getFileManager().getMessage("FriendsPrefix").replace("&", "§");
    }

    public String getPartyPrefix(){
        return Main.getPlugin().getFileManager().getMessage("PartyPrefix").replace("&", "§");
    }

    public String getClanPrefix(){
        return Main.getPlugin().getFileManager().getMessage("ClanPrefix").replace("&", "§");
    }

    public String getPunishPrefix(){
        return Main.getPlugin().getFileManager().getMessage("PunishPrefix").replace("&", "§");
    }

    public String getGlobalNoPerms(){
        return getMessage("Global.NoPerms");
    }

    public String getFriendsNoPerms(){
        return getMessage("Friends.NoPerms");
    }

    public String getPartyNoPerms(){
        return getMessage("Party.NoPerms");
    }

    public String getPunishNoPerms(){
        return getMessage("Punish.NoPerms");
    }

    public String getMessage(String path){
        return Util.replaceAll(Main.getPlugin().getFileManager().getMessage(path));
    }

}
