package de.lupu.spigot.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Util {

    public static ItemStack createCustomSkull(String value, String name, List<String> lore){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if(value.isEmpty()) return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", value));
        headMeta.setPlayerProfile(profile);
        headMeta.setDisplayName(name);
        headMeta.setLore(lore);
        head.setItemMeta(headMeta);
        return head;
    }

    public static String getCurrentDateFormatted(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static String calculateLastOnline(long last){

        if(last != -1) {
            long l = System.currentTimeMillis()-last;
            long years = 0;
            long months = 0;
            long weeks = 0;
            long days = 0;
            long hours = 0;
            long minutes = 0;
            long seconds = 0;

            while (l >= 1000) {
                seconds++;
                l -= 1000;
            }
            while (seconds >= 60) {
                minutes++;
                seconds -= 60;
            }
            while (minutes >= 60) {
                hours++;
                minutes -= 60;
            }
            while (hours >= 24) {
                days++;
                hours -= 24;
            }
            while (days >= 7) {
                weeks++;
                days -= 7;
            }
            while (weeks >= 4) {
                months++;
                weeks -= 4;
            }
            while (months >= 12) {
                years++;
                months -= 12;
            }

            if (years > 0) {
                if (years == 1) {
                    return "1 Jahr";
                } else {
                    return years + " Jahren";
                }
            } else if (months > 0) {
                if (months == 1) {
                    return "1 Monat";
                } else {
                    return months + " Monaten";
                }
            } else if (weeks > 0) {
                if (weeks == 1) {
                    return "1 Woche";
                } else {
                    return weeks + " Wochen";
                }
            } else if (days > 0) {
                if (days == 1) {
                    return "1 Tag";
                } else {
                    return days + " Tagen";
                }
            } else if (hours > 0) {
                if (hours == 1) {
                    return "1 Stunde";
                } else {
                    return hours + " Stunden";
                }
            } else if (minutes > 0) {
                if (minutes == 1) {
                    return "1 Minute";
                } else {
                    return minutes + " Minuten";
                }
            } else if (seconds > 0) {
                return "kurzem";
            }
        }
        return null;
    }

}
