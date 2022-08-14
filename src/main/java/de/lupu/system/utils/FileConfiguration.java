package de.lupu.system.utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileConfiguration {

    public FileConfiguration(Configuration cfg) {
        this.cfg = cfg;
    }

    Configuration cfg;

    public String getString(String path){
        return cfg.getString(path);
    }

    public List<String> getStringList(String path){
        return cfg.getStringList(path);
    }

    public int getInt(String path){
        return cfg.getInt(path);
    }

    public void set(String path, Object obj){
        cfg.set(path, obj);
    }

    public void save(File file) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(cfg, file);
    }

}
