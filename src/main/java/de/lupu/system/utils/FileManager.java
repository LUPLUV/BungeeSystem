package de.lupu.system.utils;

import net.md_5.bungee.api.ProxyServer;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public static String folderPrefix = "plugins//ProxySystem//";

    File folder;
    File configFile;
    File mysqlFile;
    File messagesFile;
    File helpFile;

    public void loadFiles() throws IOException{
        folder = new File("plugins//ProxySystem");
        configFile = new File(folderPrefix + "config.yml");
        mysqlFile = new File(folderPrefix + "mysql.yml");
        messagesFile = new File(folderPrefix + "messages.yml");
        helpFile = new File(folderPrefix + "help.yml");
        if(!folder.exists()) folder.mkdir();
        if(!configFile.exists()){
            configFile.createNewFile();
        }
        if(!mysqlFile.exists()){
            mysqlFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(mysqlFile);
            cfg.set("Host", "localhost");
            cfg.set("Port", "3306");
            cfg.set("Database", "FriendsDB");
            cfg.set("Username", "friendsystem");
            cfg.set("Password", "password");
            cfg.save(mysqlFile);
        }
        if(!messagesFile.exists()){
            messagesFile.createNewFile();
            setMessage("GlobalPrefix", "&6§lSocialServices &8|&7");
            setMessage("FriendsPrefix", "&aFreunde &8|&7");
            setMessage("PartyPrefix", "&eParty &8|&7");
            setMessage("ClanPrefix", "&bClans &8|&7");
            setMessage("PunishPrefix", "&cPunish &8|&7");
            setMessage("Global.NoPerms", "%global_prefix% &cDazu bist du nicht berechtigt.");
            setMessage("Global.PlayerNotFound", "%global_prefix% &cDer Spieler %player% konnte nicht gefunden werden.");
            setMessage("Global.Error", "%global_prefix% &cEs ist leider ein Fehler aufgtreten.");
            setMessage("Global.JoinMeJoined", "%global_prefix% &7Du bist mit dem JoinMe von %creator% auf den Server %server% gejoint.");
            setMessage("Global.JoinMeServerNotFound", "%global_prefix% &cDer Server des JoinMe von %creator% konnte nicht gefunden werden.");
            setMessage("Global.JoinMeNotExisting", "%global_prefix% &cDas JoinMe existiert nicht oder ist bereits abgelaufen.");
            setMessage("Global.JoinMeCantJoinOwn", "%global_prefix% &cDu kannst deinem eigenen JoinMe nicht joinen.");
            setMessage("Global.JoinMeCmdUsage", "%global_prefix% &cFalsche Argumente. Nutze /joinme");
            setMessage("Global.JoinMeMsgLine1", "%global_prefix% &aDer Spieler &6%player% &aspielt auf &6%server%&a!");
            setMessage("Global.JoinMeMsgLine2", "%global_prefix% &aKlicke %join_button%&a, um ihm zu joinen!");
            setMessage("Global.JoinMeMsgButtonText", "&3&lHIER");
            setMessage("Global.JoinMeMsgButtonHoverText", "&7Klicke um zu joinen.");
            setMessage("Global.TeamChatCmdUsage", "%global_prefix%&cFalsche Argumente. Nutze /tc <nachricht>");
            setMessage("Global.TeamChatFormat", "&6&lTeamchat &8» &7%player% &8: &7%msg%");

            // Friends
            setMessage("Friends.NoPerms", "%friends_prefix% &cDazu hast du keine Rechte.");
            setMessage("Friends.PlayerNotFound", "%friends_prefix% &cDer Spieler %player% konnte nicht gefunden werden.");
            setMessage("Friends.SentFriendRequest", "%friends_prefix% &7Du hast %player% eine Freundschaftsanfrage gesendet.");
            setMessage("Friends.IsFriends", "%friends_prefix% &cDu bist bereits mit %player% befreundet.");
            setMessage("Friends.CantAddYourself", "%friends_prefix% &cDu kannst dich selber nicht als Freund hinzufügen.");
            setMessage("Friends.Removed", "%friends_prefix% &7Du hast die Freundschaft mit %player% beendet.");
            setMessage("Friends.GotRemoved", "%friends_prefix% &7%player% hat die Freundschaft mit dir beendet.");
            setMessage("Friends.NotFriends", "%friends_prefix% &cDu bist nicht mit %player% befreundet.");
            setMessage("Friends.Accepted", "%friends_prefix% &7Du hast die Freundschaftsanfrage von %player% akzeptiert.");
            setMessage("Friends.GotAccepted", "%friends_prefix% &7%player% hat deine Freundschaftsanfrage akzeptiert.");
            setMessage("Friends.HasNoRequest", "%friends_prefix% &cDu hast keine Freundschaftsanfrage von %player%.");
            setMessage("Friends.Denied", "%friends_prefix% &7Du hast die Freundschaftsanfrage von %player% verweigert.");
            setMessage("Friends.GotDenied", "%friends_prefix% &7%player% hat deine Freundschaftsanfrage verweigert.");
            setMessage("Friends.Jumped", "%friends_prefix% &7Du bist zu %player% gesprungen.");
            setMessage("Friends.HasToggledJump", "%friends_prefix% &cDu kannst %player% nicht nachspringen.");
            setMessage("Friends.JumpNotOnline", "%friends_prefix% &cDer Spieler %player% ist nicht online.");
            setMessage("Friends.ListHeader", "%friends_prefix% &a&lDeine Freunde &7- &8(%friends%/%max_friends%)");
            setMessage("Friends.ListDescription", "%friends_prefix% &7 (Name) - (Zuletzt Online) - (Befreundet Seit) - (Status)");
            setMessage("Friends.ListEntryOffline", "%friends_prefix% &b&l%name% &7- &7Zul. Online vor %last_online% &7- &e%friends_since% &7- &7&o%status%");
            setMessage("Friends.ListEntryOnline", "%friends_prefix% &b&l%name% &7- &a%current_server% &7- &e%friends_since% &7- &7&o%status%");
            setMessage("Friends.StatusUpdated", "%friends_prefix% &7Du hast deinen Status aktualisiert.");
            setMessage("Friends.StatusMaxWords", "%friends_prefix% &cVerwende maximal 5 Wörter.");
            setMessage("Friends.StatusCmdUsage", "%friends_prefix% &cFalsche Argumente. Nutze /status <Status>");
            setMessage("Friends.HasNoFriends", "%friends_prefix% &7Du hast noch keine Freunde.");
            setMessage("Friends.IsRequested", "%friends_prefix% &cDu hast bereits eine Freundschaftsanfrage mit %player%.");
            setMessage("Friends.RequestIncoming", "%friends_prefix% &7Du hast eine Freundschaftsanfrage von %player% erhalten.");
            setMessage("Friends.AcceptButton.Text", "&2&l[ACCEPT]");
            setMessage("Friends.AcceptButton.HoverText", "&7Klicke um die Freundschaftsanfrage anzunehmen.");
            setMessage("Friends.DenyButton.Text", "&4&l[DENY]");
            setMessage("Friends.DenyButton.HoverText", "&7Klicke um die Freundschaftsanfrage abzulehnen.");
            setMessage("Friends.MsgFormatSender", "%friends_prefix% &6&lDu &e» &6&l%receiver% &8: &r%msg%");
            setMessage("Friends.MsgFormatReceiver", "%friends_prefix% &6&l%sender% &e» &6&lDu &8: &r%msg%");
            setMessage("Friends.EnabledJump", "%friends_prefix% &7Du hast Jump &aaktiviert&7.");
            setMessage("Friends.DisabledJump", "%friends_prefix% &7Du hast Jump &cdeaktiviert&7.");
            setMessage("Friends.EnabledNotify", "%friends_prefix% &7Du hast Online Notify &aaktiviert&7.");
            setMessage("Friends.DisabledNotify", "%friends_prefix% &7Du hast Online Notify &cdeaktiviert&7.");
            setMessage("Friends.EnabledRequests", "%friends_prefix% &7Du hast Requests &aaktiviert&7.");
            setMessage("Friends.DisabledRequests", "%friends_prefix% &7Du hast Requests &cdeaktiviert&7.");
            setMessage("Friends.ToggleCmdUsage", "%friends_prefix% &cFalsche Argumente. Nutze /friends toggle <jump/notify/requests>");
            setMessage("Friends.MsgNoLastChatPartner", "%friends_prefix% &cDu hast keinen letzten Chat Partner.");

            // Friends Help
            setMessage("Friends.Help.Title", "%friends_prefix% &a&lFreunde Hilfe &7Seite %current_page%/2");
            setMessage("Friends.Help.AddFriend", "%friends_prefix% &b/friend add <Spieler> &8- &7Füge einen Freund hinzu");
            setMessage("Friends.Help.RemoveFriend", "%friends_prefix% &b/friend remove <Spieler> &8- &7Entferne einen Freund");
            setMessage("Friends.Help.ListFriends", "%friends_prefix% &b/friend list &8- &7Zeige deine Freunde");
            setMessage("Friends.Help.JumpFriend", "%friends_prefix% &b/friend jump <Spieler> &8- &7Springe zu einem Freund");
            setMessage("Friends.Help.AcceptRequest", "%friends_prefix% &b/friend accept <Spieler> &8- &7Akzeptiere eine Freundschaftsanfrage");
            setMessage("Friends.Help.DenyRequest", "%friends_prefix% &b/friend deny <Spieler> &8- &7Lehne eine Freundschaftsanfrage ab");
            setMessage("Friends.Help.CmdUsage", "%friends_prefix% &cFalsche Argumente. Nutze /friends help [1/2]");

            // LastOnline
            setMessage("Friends.LastOnline.Year", "Jahr");
            setMessage("Friends.LastOnline.Years", "Jahren");
            setMessage("Friends.LastOnline.Month", "Monat");
            setMessage("Friends.LastOnline.Months", "Monaten");
            setMessage("Friends.LastOnline.Week", "Woche");
            setMessage("Friends.LastOnline.Weeks", "Wochen");
            setMessage("Friends.LastOnline.Day", "Tag");
            setMessage("Friends.LastOnline.Days", "Tage");
            setMessage("Friends.LastOnline.Hour", "Stunde");
            setMessage("Friends.LastOnline.Hours", "Stunden");
            setMessage("Friends.LastOnline.Minute", "Minute");
            setMessage("Friends.LastOnline.Minutes", "Minuten");
            setMessage("Friends.LastOnline.Second", "Sekunde");
            setMessage("Friends.LastOnline.Seconds", "Sekunden");
            setMessage("Friends.LastOnline.NotLongAgo", "kurzem");


            // Party
            setMessage("Party.NoPerms", "%party_prefix% &cDazu hast du keine Rechte.");
            setMessage("Party.PlayerNotFound", "%party_prefix% &cDer Spieler %player% konnte nicht gefunden werden.");

            // Punish
            setMessage("Punish.ReasonsHeader", "%punish_prefix% &cBestrafungs Gründe &7» &7Liste");
            setMessage("Punish.ReasonEntry", "%punish_prefix% &e%id% &7| &c&l%display_name% &7» &c%duration% &7» &c%lvl% &7» &c%type%");
            setMessage("Punish.BanCmdUsage", "%punish_prefix% &cFalsche Argumente. Nutze /ban <spieler> <grund|id> [XS|Xm|XH|XD|XW|XM|XY]");
            setMessage("Punish.NoReasons", "%punish_prefix% &cEs sind noch keine Bestrafungs Gründe vorhanden.");
            setMessage("Punish.IsAlreadyBanned", "%punish_prefix% &c%player% ist bereits gebannt.");
            setMessage("Punish.PunishedPlayer", "%punish_prefix% &cDu hast %player% wegen %reason% gebannt.");
            setMessage("Punish.WrongReasonTypeBan", "%punish_prefix% &cFalscher Grund Typ. [&aBAN|&cKICK|&cMUTE]");
            setMessage("Punish.IdNotFound", "%punish_prefix% &cDer Ban Grund wurde nicht gefunden.");
            setMessage("Punish.MaxLengthReason", "%punish_prefix% &cDer Ban Grund darf maximal 20 Zeichen lang sein.");
            setMessage("Punish.NotAllowedToBan", "%punish_prefix% &cDu darfst %player% nicht bannen.");
            setMessage("Punish.PlayerNotFound", "%punish_prefix% &cDer Spieler %player% konnte nicht gefunden werden.");
            setMessage("Punish.NoPerms", "%punish_prefix% &cDazu hast du keine Rechte.");
            setMessage("Punish.AddReasonCmdUsage", "%punish_prefix% &cFalsche Argumente. Nutze /addreason <id> <name> <lvl> <type[BAN|KICK|MUTE]> <duration[Xm|XH|XD|XW|XM|XY]> <displayName>");
            setMessage("Punish.IdFormatError", "%punish_prefix% &cDie ID muss zwischen 1 und 500 liegen.");
            setMessage("Punish.IdAlreadyExists", "%punish_prefix% &cEs existiert bereits ein Grund mit dieser ID.");
            setMessage("Punish.ReasonNameFormatError", "%punish_prefix% &cDer Grund Name muss zwischen 4 und 20 Zeichen lang sein.");
            setMessage("Punish.ReasonLvlFormatError", "%punish_prefix% &cDas Grund Level muss zwischen 1 und 10 liegen.");
            setMessage("Punish.ReasonTypeFormatError", "%punish_prefix% &cDer Punish Type kann 'KICK', 'BAN' oder 'MUTE' sein.");
            setMessage("Punish.ReasonDurationFormatError", "%punish_prefix% &cDie Grund Dauer muss im Format DAUER + EINHEIT angegeben werden. (Beispiel: '20M' 20 Monate, '10m' 10 Minuten usw.)");
            setMessage("Punish.ReasonDisplayNameFormatError", "%punish_prefix% &cDer DisplayName muss zwischen 3 und 20 Zeichen lang sein.");
            setMessage("Punish.CreatedReason", "%punish_prefix% &7Du hast den Bestrafungs Grund &aerfolgreich &7erstellt. Nutze &c/ban &7für eine Liste.");


        }
        if(!helpFile.exists()){
            helpFile.createNewFile();
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(helpFile);
            cfg.set("Placeholers", "%global_prefix%, %friends_prefix%, %party_prefix%, %clan_prefix%, %player%");
            cfg.save(helpFile);
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public File getFolder() {
        return folder;
    }

    public File getMysqlFile() {
        return mysqlFile;
    }

    public FileConfiguration getConfig() throws IOException {
        return YamlConfiguration.loadConfiguration(configFile);
    }
    public FileConfiguration getMysql() throws IOException {
        return YamlConfiguration.loadConfiguration(mysqlFile);
    }

    public File getMessagesFile() {
        return messagesFile;
    }

    public FileConfiguration getMessages() throws IOException {
        return YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void setMessage(String path, String message) {
        try {
            FileConfiguration cfg = getMessages();
            cfg.set(path, message);
            cfg.save(messagesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String path){
        try{
            FileConfiguration cfg = getMessages();
            return cfg.getString(path);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
