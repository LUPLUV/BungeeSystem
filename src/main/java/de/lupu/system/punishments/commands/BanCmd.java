package de.lupu.system.punishments.commands;

import de.lupu.system.Main;
import de.lupu.system.punishments.utils.PunishReason;
import de.lupu.system.punishments.utils.PunishType;
import de.lupu.system.punishments.utils.Punishment;
import de.lupu.system.utils.Strings;
import de.lupu.system.utils.Util;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCmd extends Command {
    public BanCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    /**
     *
     *
     * /ban <PLAYER> <ID|REASON> <DURATION>
     *   0     1          2           3
     *   -     0          1           2
     *
     *
     */

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if(!p.hasPermission("ban.use")){
                p.sendMessage(new TextComponent(Strings.getInstance().getPunishNoPerms()));
            }else{
                if(strings.length == 0){
                    if(!p.hasPermission("ban.reasons.list")){
                        p.sendMessage(new TextComponent(Strings.getInstance().getPunishNoPerms()));
                    }else{
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReasonsHeader")));
                        if(!PunishReason.getAllReasons().isEmpty()) {
                            for (PunishReason pr : PunishReason.getAllReasons()) {
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReasonEntry")
                                        .replace("%id%", String.valueOf(pr.getId())).replace("%name%", pr.getName())
                                        .replace("%display_name%", pr.getDisplayName().replace("&", "§"))
                                        .replace("%duration%", "coming").replace("%lvl%", String.valueOf(pr.getLvl()))
                                        .replace("%type%", pr.getType().toString())));
                            }
                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.BanCmdUsage")));
                        }else{
                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.NoReasons")));
                        }
                    }
                }else if(strings.length == 2){
                    if(!p.hasPermission("ban.ban")){
                        p.sendMessage(new TextComponent(Strings.getInstance().getPunishNoPerms()));
                    }else{
                        ProxiedPlayer t = ProxyServer.getInstance().getPlayer(strings[0]);
                        if(t != null){
                            if(!t.hasPermission("ban.exempt") || p.hasPermission("ban.exempt.bypass")) {
                                try {
                                    Long id = Long.parseLong(strings[1]);
                                    PunishReason pr = new PunishReason(id);
                                    if (id >= 1 && id <= 500) {
                                        if (pr.existsByID()) {
                                            pr.loadByID();
                                            if(pr.getType() == PunishType.BAN){
                                                if(Main.getPlugin().getPunishManager().isPunished(p)){
                                                    Punishment punishment = Main.getPlugin().getPunishManager().getPunishment(p);
                                                    if(punishment.getType() == PunishType.BAN){
                                                        if(punishment.getDuration().equalsIgnoreCase("permanent") || punishment.getDuration().equalsIgnoreCase("layout")){
                                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.IsAlreadyBanned").replace("%player%", t.getName())));
                                                            return;
                                                        }else{
                                                            punishment.delete();
                                                        }
                                                    }else{
                                                        punishment.delete();
                                                    }
                                                }
                                                Punishment newPunish = new Punishment();
                                                newPunish.createID();
                                                newPunish.setType(PunishType.BAN);
                                                newPunish.setIp(p.getSocketAddress().toString());
                                                newPunish.setReason(String.valueOf(pr.getId()));
                                                newPunish.setDuration("layout");
                                                newPunish.setMod(p.getUniqueId());
                                                newPunish.setUuid(t.getUniqueId());
                                                newPunish.setDate(Util.getCurrentDateFormatted());
                                                newPunish.create();
                                                newPunish.execute();
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.PunishedPlayer")
                                                        .replace("%player%", t.getName()).replace("%reason%", pr.getDisplayName())));
                                            }else{
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.WrongReasonTypeBan")));
                                            }
                                        } else {
                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.IdNotFound")));
                                        }
                                    } else {
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.IdFormatError")));
                                    }
                                } catch (NumberFormatException e) {
                                    String reason = strings[1];
                                    if(reason.length() <= 20){
                                        if(Main.getPlugin().getPunishManager().isPunished(p)){
                                            Punishment punishment = Main.getPlugin().getPunishManager().getPunishment(p);
                                            if(punishment.getType() == PunishType.BAN){
                                                if(punishment.getDuration().equalsIgnoreCase("permanent") || punishment.getDuration().equalsIgnoreCase("layout")){
                                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.IsAlreadyBanned")));
                                                }else{
                                                    punishment.delete();
                                                }
                                            }
                                        }
                                        Punishment newPunish = new Punishment();
                                        newPunish.createID();
                                        newPunish.setType(PunishType.BAN);
                                        newPunish.setIp(p.getSocketAddress().toString());
                                        newPunish.setReason(reason);
                                        newPunish.setDuration("permanent");
                                        newPunish.setMod(p.getUniqueId());
                                        newPunish.setUuid(t.getUniqueId());
                                        newPunish.setDate(Util.getCurrentDateFormatted());
                                        newPunish.create();
                                        newPunish.execute();
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.PunishedPlayer")
                                                .replace("%player%", t.getName()).replace("%reason%", reason)));
                                    }else{
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.MaxLengthReason")));
                                    }
                                }
                            }else{
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.NotAllowedToBan").replace("%player%", t.getName())));
                            }
                        }else{
                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.PlayerNotFound").replace("%player%", strings[0])));
                        }
                    }
                }
            }
        }
    }
}
