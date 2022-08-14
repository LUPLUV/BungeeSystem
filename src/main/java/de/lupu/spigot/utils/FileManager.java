package de.lupu.spigot.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public static String folderPrefix = "plugins//ProxySystem//";

    File folder;
    File configFile;
    File mysqlFile;

    public void loadFiles() throws IOException{
        folder = new File("plugins//ProxySystem");
        configFile = new File(folderPrefix + "config.yml");
        mysqlFile = new File(folderPrefix + "mysql.yml");
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

}
