package de.lupu.system.punishments.commands;

import de.lupu.system.Main;
import de.lupu.system.punishments.utils.PunishReason;
import de.lupu.system.punishments.utils.PunishType;
import de.lupu.system.utils.Strings;
import de.lupu.system.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.sql.SQLException;

public class AddreasonCmd extends Command {
    public AddreasonCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    /**
     *
     *
     * /addreason <id> <name> <lvl> <punishType> <duration> <displayName>
     *     0       1     2      3        4           5            6
     *     -       0     1      2        3           4            5
     *
     *
     */

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if(!p.hasPermission("punish.addreason")){
                p.sendMessage(new TextComponent(Strings.getInstance().getPunishNoPerms()));
            }else{
                if(strings.length == 6){
                    try {
                        Long id = Long.parseLong(strings[0]);
                        if(id >= 1 && id <= 500){
                            PunishReason pr = new PunishReason(id);
                            if(!pr.existsByID()){
                                String name = strings[1];
                                if(name.length() <= 20 && name.length() >= 4){
                                    try {
                                        Long lvl = Long.parseLong(strings[2]);
                                        if(lvl >= 1 && lvl <= 10){
                                            String ptRaw = strings[3];
                                            if(ptRaw.equalsIgnoreCase("KICK") || ptRaw.equalsIgnoreCase("BAN") || ptRaw.equalsIgnoreCase("MUTE")){
                                                PunishType pt = PunishType.valueOf(ptRaw);
                                                String duration = strings[4];
                                                if(Util.punishDurationValid(duration)){
                                                    String displayName = strings[5];
                                                    if(displayName.length() >= 3 && displayName.length() <= 20){
                                                        pr.setName(name);
                                                        pr.setLvl(lvl);
                                                        pr.setType(pt);
                                                        pr.setDisplayName(duration);
                                                        pr.setDisplayName(displayName);
                                                        pr.create();
                                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.CreatedReason")));
                                                    }else{
                                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReasonDisplayNameFormatError")));
                                                    }
                                                }else{
                                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReasonDurationFormatError")));
                                                }
                                            }else{
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReasonTypeFormatError")));
                                            }
                                        }else{
                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReasonLvlFormatError")));
                                        }
                                    }catch (NumberFormatException e){
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReasonLvlFormatError")));
                                    }
                                }else{
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReasonNameFormatError")));
                                }
                            }else{
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.IdAlreadyExists")));
                            }
                        }else{
                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.IdFormatError")));
                        }
                    }catch (NumberFormatException e){
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.IdFormatError")));
                    }
                }else{
                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.AddReasonCmdUsage")));
                }
            }
        }
    }
}
