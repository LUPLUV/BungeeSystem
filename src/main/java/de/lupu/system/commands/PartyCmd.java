package de.lupu.system.commands;

import de.lupu.system.Main;
import de.lupu.system.utils.Party;
import de.lupu.system.utils.Strings;
import de.lupu.system.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class PartyCmd extends Command {
    public PartyCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(Main.getPlugin().getMySQL().isConnected()) {
            if (commandSender instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) commandSender;
                if(!p.hasPermission("party.use")){
                    p.sendMessage(new TextComponent(Strings.getInstance().getPartyNoPerms()));
                }else if(strings.length == 0){
                    Util.sendPartyHelp(p, 1);
                }else if(strings[0].equalsIgnoreCase("invite")){
                    if(!p.hasPermission("party.invite")){
                        p.sendMessage(new TextComponent(Strings.getInstance().getPartyNoPerms()));
                    }else{
                        // /party invite <name>
                        if(strings.length == 2){
                            String name = strings[1];
                            if(Main.getPlugin().getNameFetcher().isUserExists(name)){
                                UUID uuid = Main.getPlugin().getNameFetcher().getUuidByNameUUID(name);
                                if(!Main.getPlugin().getPartyManager().isPartyExits(p.getUniqueId())){
                                    Main.getPlugin().getPartyManager().createParty(p.getUniqueId(), false);
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Party.PartyCreated")));
                                }
                                Party party = Main.getPlugin().getPartyManager().getPartyByMember(p.getUniqueId());
                            }else{
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Party.PlayerNotFound").replace("%player%", name)));
                            }
                        }
                    }
                }
            }
        }else{
            commandSender.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + "§cError. Could not load any Data..."));
            commandSender.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + "§cPlease contact an Admin to check the MySQL Connection."));
        }
    }
}
