package de.lupu.spigot.utils;

import de.lupu.spigot.SpigotMain;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NameFetcher {

    public boolean isUserExists(ProxiedPlayer p){
        try {
            PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon().prepareStatement("SELECT * " +
                    "FROM psys_NameFetcher WHERE UUID = ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserExists(UUID uuid){
        try {
            PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon().prepareStatement("SELECT * " +
                    "FROM psys_NameFetcher WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserExists(String name){
        try {
            PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon().prepareStatement("SELECT * " +
                    "FROM psys_NameFetcher WHERE NAME = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateName(UUID uuid, String name){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid)){
                try {
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_NameFetcher SET NAME = ? WHERE UUID = ?");
                    ps.setString(1, name);
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateName(ProxiedPlayer p){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(p)){
                try {
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_NameFetcher SET NAME = ? WHERE UUID = ?");
                    ps.setString(1, p.getName());
                    ps.setString(2, p.getUniqueId().toString());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createUser(ProxiedPlayer p){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(!isUserExists(p.getUniqueId())){
                try {
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("INSERT INTO psys_NameFetcher (UUID,NAME) VALUES (?,?)");
                    ps.setString(1, p.getUniqueId().toString());
                    ps.setString(2, p.getName());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getNameByUuid(String uuid){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid)){
                try {
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * FROM psys_NameFetcher WHERE UUID = ?");
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        return rs.getString("NAME");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getNameByUuid(UUID uuid){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid)){
                try {
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * FROM psys_NameFetcher WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        return rs.getString("NAME");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getUuidByName(String name){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(name)){
                try {
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * FROM psys_NameFetcher WHERE NAME = ?");
                    ps.setString(1, name);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        return rs.getString("UUID");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public UUID getUuidByNameUUID(String name){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(name)){
                try {
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * FROM psys_NameFetcher WHERE NAME = ?");
                    ps.setString(1, name);
                    ResultSet rs = ps.executeQuery();
                    while(rs.next()){
                        return UUID.fromString(rs.getString("UUID"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
