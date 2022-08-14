package de.lupu.system.events;

import de.lupu.system.Main;
import de.lupu.system.utils.ProfileOptions;
import de.lupu.system.utils.Strings;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.UUID;

public class PluginMessageEvent implements Listener {

    @EventHandler
    public void onMessage(net.md_5.bungee.api.event.PluginMessageEvent e){
        if(e.getTag().equalsIgnoreCase("BungeeCord")){
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            try {
                String channel = in.readUTF();
                if(channel.equals("friend")){
                    String input = in.readUTF();
                    if(input.equals("add")){
                        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(e.getReceiver().toString());
                        String t = in.readUTF();

                    }else if(input.equals("remove")){
                        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(e.getReceiver().toString());
                        String tarName = in.readUTF();
                        UUID uuid = Main.getPlugin().getNameFetcher().getUuidByNameUUID(tarName);
                        Main.getPlugin().getFriendManager().removeFriend(p.getUniqueId(), uuid);
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.Removed").replace("%player%", tarName)));
                        ProxiedPlayer t = ProxyServer.getInstance().getPlayer(tarName);
                        if (t != null) {
                            t.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.GotRemoved").replace("%player%", p.getName())));
                        }
                    }else if(input.equals("jump")){
                        ProxiedPlayer p = ProxyServer.getInstance().getPlayer(e.getReceiver().toString());
                        String tarName = in.readUTF();
                        UUID uuid = Main.getPlugin().getNameFetcher().getUuidByNameUUID(tarName);
                        ProxiedPlayer t = ProxyServer.getInstance().getPlayer(uuid);
                        if (t != null) {
                            if (ProfileOptions.loadProfile(uuid).isToggleJump()) {
                                p.connect(t.getServer().getInfo());
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.Jumped").replace("%player%", tarName)));
                            } else {
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.HasToggledJump").replace("%player%", tarName)));
                            }
                        } else {
                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.JumpNotOnline").replace("%player%", tarName)));
                        }
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
