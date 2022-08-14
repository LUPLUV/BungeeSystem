package de.lupu.system;

import de.lupu.bmapi.mysql.MySQL;
import de.lupu.system.commands.*;
import de.lupu.system.events.*;
import de.lupu.system.punishments.commands.AddreasonCmd;
import de.lupu.system.punishments.commands.BanCmd;
import de.lupu.system.punishments.utils.PunishManager;
import de.lupu.system.utils.*;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import java.io.IOException;

public class Main extends Plugin {

    private static Main plugin;
    private MySQL mySQL;
    private FileManager fileManager;
    private NameFetcher nameFetcher;
    private FriendManager friendManager;
    private ClanManager clanManager;
    private PartyManager partyManager;
    private PunishManager punishManager;

    @Override
    public void onEnable() {

        plugin = this;
        fileManager = new FileManager();
        try {
            fileManager.loadFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reloadMysql();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nameFetcher = new NameFetcher();
        friendManager = new FriendManager();
        clanManager = new ClanManager();
        partyManager = new PartyManager();
        punishManager = new PunishManager();

        PluginManager pm = getProxy().getPluginManager();
        pm.registerCommand(this, new FriendCmd("friend", null, "friends", "f"));
        pm.registerCommand(this, new MsgCmd("msg", null, "message", "tell", "whisper"));
        pm.registerCommand(this, new JoinmeCmd("joinme", null, "jm", "jme"));
        pm.registerCommand(this, new ReplyCmd("reply", null, "r"));
        pm.registerCommand(this, new BanCmd("ban", null, "tempban"));
        pm.registerCommand(this, new TeamchatCmd("teamchat", null, "tc"));
        pm.registerCommand(this, new AddreasonCmd("addreason", null, "addpunishreason"));
        pm.registerCommand(this, new EndCmd("end", null, "endproxy", "stopproxy", "stopbungee", "endbungee"));

        pm.registerListener(this, new JoinEvent());
        pm.registerListener(this, new TabEvent());
        pm.registerListener(this, new SwitchEvent());
        pm.registerListener(this, new LeaveEvent());
        pm.registerListener(this, new PluginMessageEvent());

        punishManager.startReportDelaySyncTask();

    }

    @Override
    public void onDisable() {
        mySQL.disconnect();
        getProxy().getScheduler().cancel(this);
    }

    public static Main getPlugin() {
        return plugin;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public NameFetcher getNameFetcher() {
        return nameFetcher;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public ClanManager getClanManager() {
        return clanManager;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }

    public PunishManager getPunishManager() {
        return punishManager;
    }

    public static void reloadMysql() throws IOException {
        FileConfiguration cfg = plugin.fileManager.getMysql();
        if(plugin.mySQL != null) {
            if (plugin.mySQL.isConnected()) {
                plugin.mySQL.disconnect();
            }
            plugin.mySQL.setHost(cfg.getString("Host"));
            plugin.mySQL.setPort(cfg.getString("Port"));
            plugin.mySQL.setDatabase(cfg.getString("Database"));
            plugin.mySQL.setUsername(cfg.getString("Username"));
            plugin.mySQL.setPassword(cfg.getString("Password"));
        }else{
            plugin.mySQL = new MySQL(cfg.getString("Host"), cfg.getString("Port"), cfg.getString("Database")
                    , cfg.getString("Username"), cfg.getString("Password"));
        }
        plugin.mySQL.connect();
        if(plugin.mySQL.isConnected()){
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_NameFetcher (UUID VARCHAR(100),NAME VARCHAR(100));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_FriendsTable (UUID VARCHAR(100),TOGGLE_REQUEST VARCHAR(16)" +
                    ",TOGGLE_ONLINE_NOTIFY VARCHAR(16),TOGGLE_JUMP VARCHAR(16),CURRENT_SERVER VARCHAR(100),LAST_ONLINE BIGINT(100)" +
                    ",STATUS VARCHAR(100),REQUESTS VARCHAR(1000),FRIENDS VARCHAR(1000));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_PartyTable (PARTY_OWNER VARCHAR(100),PUBLIC VARCHAR(100),MEMBERS VARCHAR(1000)" +
                    ",INVITES VARCHAR(1000));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_ClanTable (NAME VARCHAR(100),TAG VARCHAR(100)" +
                    ",OWNER VARCHAR(100),COLOR VARCHAR(100),DATE VARCHAR(100),PUBLIC VARCHAR(100),MEMBERS VARCHAR(1000),INVITES VARCHAR(1000));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_PunishReasons (ID BIGINT(100),NAME VARCHAR(100),DISPLAY_NAME VARCHAR(1000),LVL BIGINT(100)" +
                    ",DURATION VARCHAR(100),TYPE VARCHAR(100));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_Punishments (ID BIGINT(255),UUID VARCHAR(100),DURATION VARCHAR(100),DATE VARCHAR(100)" +
                    ",MODERATOR VARCHAR(100),REASON VARCHAR(100),IP VARCHAR(100),TYPE VARCHAR(100));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_Histories (ID BIGINT(255),UUID VARCHAR(100),DATE VARCHAR(100),MODERATOR VARCHAR(100)" +
                    ",REASON VARCHAR(100),DURATION VARCHAR(100));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_OpenReports (ID BIGINT(100),REPORTED VARCHAR(100),REPORTER VARCHAR(100)" +
                    ",REASON VARCHAR(100));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_Reports (ID BIGINT(255),REPORTED VARCHAR(100),REPORTER VARCHAR(100),REASON BIGINT(100)" +
                    ",DATE VARCHAR(100));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_ReportHistory (ID BIGINT(255),REPORTED VARCHAR(100),REPORTER VARCHAR(100)" +
                    ",REASON VARCHAR(100),DATE VARCHAR(100));");
            plugin.mySQL.update("CREATE TABLE IF NOT EXISTS psys_ReportReasons (ID BIGINT(100),NAME VARCHAR(100),DISPLAY_NAME VARCHAR(100)" +
                    ",ITEM_TYPE VARCHAR(100));");
        }
    }

}
