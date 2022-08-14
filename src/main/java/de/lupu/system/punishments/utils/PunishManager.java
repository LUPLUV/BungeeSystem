package de.lupu.system.punishments.utils;


import de.lupu.system.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PunishManager {

    public static Map<ProxiedPlayer, Long> reportDelays = new HashMap<>();

    public boolean isPunished(ProxiedPlayer p){
        if(Main.getPlugin().getMySQL().isConnected()){
            Punishment punishment = new Punishment(p.getUniqueId());
            return punishment.existsByUUID();
        }
        return false;
    }

    public Punishment getPunishment(ProxiedPlayer p){
        if(Main.getPlugin().getMySQL().isConnected()){
            Punishment punishment = new Punishment(p.getUniqueId());
            if(punishment.existsByUUID()){
                punishment.loadByUUID();
                return punishment;
            }
        }
        return null;
    }

    public PunishReason getPunishReason(Long id){
        if(Main.getPlugin().getMySQL().isConnected()){
            PunishReason pr = new PunishReason(id);
            if(pr.existsByID()){
                pr.loadByID();
                return pr;
            }
        }
        return null;
    }

    public boolean isAlreadyReported(UUID reporter, UUID reported){
        Report r = new Report();
        r.setReported(reported);
        r.setReporter(reporter);
        return r.existsByReportedAndReporter();
    }

    public boolean canReport(ProxiedPlayer p){
        if(!p.hasPermission("report.delay.bypass")) {
            return !reportDelays.containsKey(p);
        }else{
            return true;
        }
    }

    public void startReportDelaySyncTask(){
        ProxyServer.getInstance().getScheduler().schedule(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for(ProxiedPlayer all : reportDelays.keySet()){
                    long newVal = reportDelays.get(all)-1;
                    reportDelays.remove(all);
                    if (newVal > 0) {
                        reportDelays.put(all, newVal);
                    }
                }
            }
        }, 1, TimeUnit.SECONDS);
    }

}
