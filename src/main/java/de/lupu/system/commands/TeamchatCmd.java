package de.lupu.system.commands;

import de.lupu.system.utils.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeamchatCmd extends Command {
    public TeamchatCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if(!p.hasPermission("teamchat.use")){
                p.sendMessage(new TextComponent(Strings.getInstance().getGlobalNoPerms()));
            }else{
                if(strings.length >= 1){
                    String msg = String.join(" ", strings).replace("&", "§");
                    ProxyServer.getInstance().getPlayers().forEach(all ->{
                        if(all.hasPermission("teamchat.use")){
                            all.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.TeamChatFormat")
                                    .replace("%msg%", msg).replace("%player%", p.getName())));
                        }
                    });
                }else{
                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.TeamChatCmdUsage")));
                }
            }
        }
    }
}
