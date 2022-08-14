package de.lupu.system.commands;

import de.lupu.system.utils.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class EndCmd extends Command {
    public EndCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if(!p.hasPermission("proxy.end")){
                p.sendMessage(new TextComponent(Strings.getInstance().getGlobalNoPerms()));
            }else{
                if(strings.length == 0){

                }else{
                    p.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + " §cNutze /end"));
                }
            }
        }
    }
}
