package de.lupu.system.punishments.commands;

import de.lupu.system.Main;
import de.lupu.system.punishments.utils.PunishManager;
import de.lupu.system.punishments.utils.Report;
import de.lupu.system.punishments.utils.ReportReason;
import de.lupu.system.utils.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ReportCmd extends Command {
    public ReportCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if(!p.hasPermission("report.use")){
                p.sendMessage(new TextComponent(Strings.getInstance().getPunishNoPerms()));
            }else {
                if (strings.length == 0) {

                } else if (Main.getPlugin().getPunishManager().canReport(p)) {
                    if (strings.length == 1) {

                    } else if (strings.length == 2) {
                        try {
                            Long l = Long.parseLong(strings[1]);
                            ReportReason rr = new ReportReason(l);
                            if (rr.existsByID()) {

                            } else {
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReportReasonNotFound")));
                            }
                        } catch (NumberFormatException e) {
                            String name = strings[1];
                            ReportReason rr = new ReportReason();
                            rr.setName(name);
                            if (rr.existsByName()) {
                                Report r = new Report();

                            } else {
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReportReasonNotFound")));
                            }
                        }
                    }
                }else{
                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Punish.ReportDelay").replace("%remaining%", String.valueOf(PunishManager.reportDelays.get(p)))));
                }
            }
        }
    }
}
