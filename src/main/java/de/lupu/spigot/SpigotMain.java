package de.lupu.spigot;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.lupu.smapi.mysql.MySQL;
import de.lupu.spigot.commands.FriendsguiCmd;
import de.lupu.spigot.events.ClickEvent;
import de.lupu.spigot.utils.FileManager;
import de.lupu.spigot.utils.FriendManager;
import de.lupu.spigot.utils.InventoryManager;
import de.lupu.spigot.utils.NameFetcher;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SpigotMain extends JavaPlugin {

    private static SpigotMain plugin;
    private FileManager fileManager;
    private InventoryManager inventoryManager;
    private MySQL mySQL;
    private NameFetcher nameFetcher;
    private FriendManager friendManager;

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

        inventoryManager = new InventoryManager();
        nameFetcher = new NameFetcher();
        friendManager = new FriendManager();

        getCommand("friendsgui").setExecutor(new FriendsguiCmd());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ClickEvent(), this);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        mySQL.disconnect();
    }

    public static SpigotMain getPlugin() {
        return plugin;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public FriendManager getFriendManager() {
        return friendManager;
    }

    public NameFetcher getNameFetcher() {
        return nameFetcher;
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
        }
    }

}
