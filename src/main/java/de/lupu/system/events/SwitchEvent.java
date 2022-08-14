package de.lupu.system.events;

import de.lupu.system.commands.JoinmeCmd;
import de.lupu.system.utils.ProfileOptions;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SwitchEvent implements Listener {

    @EventHandler
    public void onSwitch(ServerSwitchEvent e){
        ProxiedPlayer p = e.getPlayer();
        if(JoinmeCmd.joinMesContains(p.getName())){
            JoinmeCmd.joinMes.remove(JoinmeCmd.getJoinMe(p.getName()));
        }
        ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());
        po.setCurrentServer(p.getServer().getInfo().getName());
        po.saveProfileOptions();
    }

}
