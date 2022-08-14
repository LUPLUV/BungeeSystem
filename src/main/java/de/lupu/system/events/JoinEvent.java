package de.lupu.system.events;

import de.lupu.system.Main;
import de.lupu.system.utils.ProfileOptions;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinEvent implements Listener {

    @EventHandler
    public void onPostLogin(PostLoginEvent e){
        ProxiedPlayer p = e.getPlayer();
        if(!Main.getPlugin().getNameFetcher().isUserExists(p)){
            Main.getPlugin().getNameFetcher().createUser(p);
        }
        Main.getPlugin().getNameFetcher().updateName(p);
    }

    @EventHandler
    public void onServerConnected(ServerConnectedEvent e){
        ProxiedPlayer p = e.getPlayer();
        String name = e.getServer().getInfo().getName();
        if(!Main.getPlugin().getFriendManager().isUserExists(p)){
            Main.getPlugin().getFriendManager().createUser(p, name);
        }
        ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());
        po.setLastOnline(-1);
        po.saveProfileOptions();
    }

}
