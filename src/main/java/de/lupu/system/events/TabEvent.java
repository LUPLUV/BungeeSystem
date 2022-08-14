package de.lupu.system.events;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TabEvent implements Listener {

    @EventHandler
    public void onTabComplete(TabCompleteEvent e){
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        String command = e.getCursor();
        String[] args = command.split(" ");
        if(command.startsWith("/friend ") || command.startsWith("/f ") || command.startsWith("/friends ")){
            if((args.length == 2 && !command.endsWith(" ")) || (args.length == 1 && command.endsWith(" "))) {
                e.getSuggestions().add("add");
                e.getSuggestions().add("remove");
                e.getSuggestions().add("list");
                e.getSuggestions().add("accept");
                e.getSuggestions().add("deny");
                e.getSuggestions().add("status");
                e.getSuggestions().add("toggle");
                e.getSuggestions().add("jump");
            }else if(((args.length == 3 && !command.endsWith(" ")) || (args.length == 2 && command.endsWith(" "))) && !args[1].equalsIgnoreCase("list")
                    && !args[1].equalsIgnoreCase("status")){
                if(args[1].equalsIgnoreCase("toggle")){
                    e.getSuggestions().add("notify");
                    e.getSuggestions().add("requests");
                    e.getSuggestions().add("jump");
                }else {
                    ProxyServer.getInstance().getPlayers().forEach(all -> {
                        e.getSuggestions().add(all.getName());
                    });
                }
            }
        }else if(command.startsWith("/msg ")){
            if((args.length == 2 && !command.endsWith(" ")) || (args.length == 1 && command.endsWith(" "))){
                ProxyServer.getInstance().getPlayers().forEach(all -> {
                    e.getSuggestions().add(all.getName());
                });
            }
        }
    }

}
