package de.lupu.system.punishments.utils;

import de.lupu.system.Main;
import de.lupu.system.utils.Strings;
import de.lupu.system.utils.Util;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class Punishment {

    long id;
    UUID uuid;
    String duration;
    String date;
    UUID mod;
    String reason;
    String ip;
    PunishType type;

    public Punishment() {
    }

    public Punishment(long id) {
        this.id = id;
    }

    public Punishment(UUID uuid) {
        this.uuid = uuid;
    }

    public Punishment(long id, UUID uuid, String duration, String date, UUID mod, String reason, String ip, PunishType type) {
        this.id = id;
        this.uuid = uuid;
        this.duration = duration;
        this.date = date;
        this.mod = mod;
        this.reason = reason;
        this.ip = ip;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UUID getMod() {
        return mod;
    }

    public void setMod(UUID mod) {
        this.mod = mod;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public PunishType getType() {
        return type;
    }

    public void setType(PunishType type) {
        this.type = type;
    }

    public void createID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            for(int i = 1; i < 500; i++){
                Punishment punish = new Punishment(i);
                if(!punish.existsByID()){
                    setId(i);
                    break;
                }
            }
        }
    }

    public void execute(){
        ProxiedPlayer t = ProxyServer.getInstance().getPlayer(uuid);
        if(t != null){
            if(duration.equalsIgnoreCase("layout")) {
                PunishReason pr = Main.getPlugin().getPunishManager().getPunishReason(Long.valueOf(reason));
                pr.loadByID();
                if(pr.getDuration().equalsIgnoreCase("permanent")) {
                    t.disconnect(new TextComponent(Strings.getInstance().getMessage("Punish.BanFormatPermanent").replace("%player%", t.getName())
                            .replace("%reason%", pr.getDisplayName())));
                }else{
                    t.disconnect(new TextComponent(Strings.getInstance().getMessage("Punish.BanFormatTemp").replace("%player%", t.getName())
                            .replace("%reason%", pr.getDisplayName()).replace("%duration%", Util.formatPunishDuration(pr.getDuration()))));
                }
            }else if(duration.equalsIgnoreCase("permanent")){
                t.disconnect(new TextComponent(Strings.getInstance().getMessage("Punish.BanFormatPermanent").replace("%player%", t.getName())
                        .replace("%reason%", "§c§l" + reason)));
            }else{
                t.disconnect(new TextComponent(Strings.getInstance().getMessage("Punish.BanFormatTemp").replace("%player%", t.getName())
                        .replace("%reason%", reason).replace("%duration%", Util.formatPunishDuration(duration))));
            }
        }
    }

    public boolean existsByID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_Punishments WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    return true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean existsByUUID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_Punishments WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    return true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public void create(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("INSERT INTO psys_Punishments " +
                        "(ID,UUID,DURATION,DATE,MOD,REASON,IP,TYPE) VALUES (?,?,?,?,?,?,?,?)");
                ps.setString(1, String.valueOf(id));
                ps.setString(2, uuid.toString());
                ps.setString(3, duration);
                ps.setString(4, date);
                ps.setString(5, mod.toString());
                ps.setString(6, reason);
                ps.setString(7, ip);
                ps.setString(8, type.toString());
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void update(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("UPDATE psys_Punishments SET " +
                        "UUID = ?, DURATION = ?, DATE = ?, MOD = ?, REASON = ?, IP = ?, TYPE = ? WHERE ID = ?");
                ps.setString(1, uuid.toString());
                ps.setString(2, duration);
                ps.setString(3, date);
                ps.setString(4, mod.toString());
                ps.setString(5, reason);
                ps.setString(6, ip);
                ps.setString(7, type.toString());
                ps.setString(8, String.valueOf(id));
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_Punishments WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setUuid(UUID.fromString(rs.getString("UUID")));
                    setDuration(rs.getString("DURATION"));
                    setDate(rs.getString("DATE"));
                    setMod(UUID.fromString(rs.getString("MOD")));
                    setReason(rs.getString("REASON"));
                    setIp(rs.getString("IP"));
                    setType(PunishType.valueOf(rs.getString("TYPE")));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByUUID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_Punishments WHERE UUID = ?");
                ps.setString(1, String.valueOf(id));
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setId(rs.getLong("ID"));
                    setDuration(rs.getString("DURATION"));
                    setDate(rs.getString("DATE"));
                    setMod(UUID.fromString(rs.getString("MOD")));
                    setReason(rs.getString("REASON"));
                    setIp(rs.getString("IP"));
                    setType(PunishType.valueOf(rs.getString("TYPE")));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("DELETE FROM psys_Punishments WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
