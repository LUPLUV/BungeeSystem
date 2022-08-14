package de.lupu.system.commands;

import de.lupu.system.utils.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MsgCmd extends Command {

    public MsgCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if(!p.hasPermission("friends.msg")){
                p.sendMessage(Strings.getInstance().getFriendsNoPerms());
            }else{
                if(strings.length >= 2){
                    ProxiedPlayer t = ProxyServer.getInstance().getPlayer(strings[0]);
                    if(t != null){
                        String[] msgArgs = strings;
                        msgArgs[0] = "#34789574365783";
                        String msg = String.join(" ", msgArgs).replace("#34789574365783 ", "");
                        t.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.MsgFormatReceiver").replace("%sender%", p.getName()).replace("%receiver%", t.getName()).replace("%msg%", msg.replace("&", "§"))));
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.MsgFormatSender").replace("%sender%", p.getName()).replace("%receiver%", t.getName()).replace("%msg%", msg.replace("&", "§"))));
                        ReplyCmd.lastChatPartner.remove(p);
                        ReplyCmd.lastChatPartner.put(p, t);
                        ReplyCmd.lastChatPartner.remove(t);
                        ReplyCmd.lastChatPartner.put(t, p);
                    }else{
                        p.sendMessage(Strings.getInstance().getMessage("Friends.PlayerNotFound").replace("%player%", strings[0]));
                    }
                }
            }
        }
    }
}
