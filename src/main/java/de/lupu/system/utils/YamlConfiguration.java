package de.lupu.system.utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.File;
import java.io.IOException;

public class YamlConfiguration {

    public static FileConfiguration loadConfiguration(File file) throws IOException {
        Configuration cfg = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(file);
        FileConfiguration fc = new FileConfiguration(cfg);
        return fc;
    }

}
