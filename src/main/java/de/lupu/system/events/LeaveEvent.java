package de.lupu.system.events;

import de.lupu.system.commands.ReplyCmd;
import de.lupu.system.utils.ProfileOptions;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent e){
        ProxiedPlayer p = e.getPlayer();
        ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());
        po.setLastOnline(System.currentTimeMillis());
        po.saveProfileOptions();
        ReplyCmd.lastChatPartner.remove(p);
        if(ReplyCmd.lastChatPartner.containsValue(p)){
            ReplyCmd.lastChatPartner.remove(ReplyCmd.lastChatPartner.get(p));
        }
    }

}
