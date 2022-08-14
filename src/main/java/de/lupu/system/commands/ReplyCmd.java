package de.lupu.system.commands;

import de.lupu.system.utils.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.HashMap;
import java.util.Map;

public class ReplyCmd extends Command {

    public static Map<ProxiedPlayer, ProxiedPlayer> lastChatPartner = new HashMap<>();

    public ReplyCmd(String name, String perm, String... aliases) {
        super(name, perm, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if(!p.hasPermission("friends.reply")){
                p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
            }else{
                if(strings.length >= 1){
                    if(lastChatPartner.containsKey(p)){
                        ProxiedPlayer t = lastChatPartner.get(p);
                        String[] msgArgs = strings;
                        String msg = String.join(" ", msgArgs);
                        t.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.MsgFormatReceiver").replace("%sender%", p.getName()).replace("%receiver%", t.getName()).replace("%msg%", msg.replace("&", "§"))));
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.MsgFormatSender").replace("%sender%", p.getName()).replace("%receiver%", t.getName()).replace("%msg%", msg.replace("&", "§"))));
                    }else{
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.MsgNoLastChatPartner")));
                    }
                }
            }
        }
    }
}
