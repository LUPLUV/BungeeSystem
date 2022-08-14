package de.lupu.spigot.events;

import de.lupu.bmapi.utils.Strings;
import de.lupu.spigot.SpigotMain;
import de.lupu.spigot.utils.ProfileOptions;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignEditEvent implements Listener {

    @EventHandler
    public void onEdit(SignChangeEvent e){
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if(e.getLine(0) == null || e.getLine(0).equalsIgnoreCase("")){
            p.sendMessage(Strings.prefix + "Du musst deinen Status in die erste Zeile schreiben.");
            return;
        }
        String to = e.getLine(0);
        if (to.length() >= 2) {
            if(to.length() <= 20) {
                if (SpigotMain.getPlugin().getFriendManager().isUserExists(p.getUniqueId())) {
                    ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());
                    po.setStatus(to);
                    po.saveProfileOptions();
                    p.sendMessage(Strings.prefix + "Du hast deinen Status aktualisiert");
                }
            }else{
                p.sendMessage(Strings.prefix + "§cDu kannst nicht mehr Zeichen als 20 verwenden.");
            }
        }else{
            p.sendMessage(Strings.prefix + "§cDu musst mindestens 2 Zeichen verwenden.");
        }
    }

}
