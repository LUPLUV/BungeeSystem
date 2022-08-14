package de.lupu.system.punishments.utils;

import de.lupu.system.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PunishReason {

    public static List<PunishReason> getAllReasons(){
        List<PunishReason> reasonList = new ArrayList<>();
        for (int i = 0; i <= 100; i++){
            PunishReason pr = new PunishReason(i);
            if(pr.existsByID()){
                pr.loadByID();
                reasonList.add(pr);
            }
        }
        return reasonList;
    }

    long id;
    String name;
    String displayName;
    long lvl;
    String duration;
    PunishType type;

    public PunishReason(long id) {
        this.id = id;
    }

    public PunishReason(long id, String name, String displayName, long lvl, String duration, PunishType type) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.lvl = lvl;
        this.duration = duration;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLvl() {
        return lvl;
    }

    public void setLvl(long lvl) {
        this.lvl = lvl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public PunishType getType() {
        return type;
    }

    public void setType(PunishType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean existsByID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_PunishReasons WHERE ID = ?");
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

    public boolean existsByName(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_PunishReasons WHERE NAME = ?");
                ps.setString(1, name);
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

    public boolean existsByDisplayName(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_PunishReasons WHERE DISPLAY_NAME = ?");
                ps.setString(1, displayName);
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
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("INSERT INTO psys_PunishReasons " +
                        "(ID,NAME,DISPLAY_NAME,LVL,DURATION,TYPE) VALUES (?,?,?,?,?,?)");
                ps.setString(1, String.valueOf(id));
                ps.setString(2, name);
                ps.setString(3, displayName);
                ps.setString(4, String.valueOf(lvl));
                ps.setString(5, duration);
                ps.setString(6, type.toString());
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_PunishReasons " +
                        "WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setName(rs.getString("NAME"));
                    setDisplayName(rs.getString("DISPLAY_NAME"));
                    setLvl(rs.getLong("LVL"));
                    setDuration(rs.getString("DURATION"));
                    setType(PunishType.valueOf(rs.getString("TYPE")));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByName(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_PunishReasons " +
                        "WHERE NAME = ?");
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setId(rs.getLong("ID"));
                    setDisplayName(rs.getString("DISPLAY_NAME"));
                    setLvl(rs.getLong("LVL"));
                    setDuration(rs.getString("DURATION"));
                    setType(PunishType.valueOf(rs.getString("TYPE")));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByDisplayName(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_PunishReasons " +
                        "WHERE DISPLAY_NAME = ?");
                ps.setString(1, displayName);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setId(rs.getLong("ID"));
                    setName(rs.getString("NAME"));
                    setLvl(rs.getLong("LVL"));
                    setDuration(rs.getString("DURATION"));
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
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("DELETE FROM psys_PunishReasons WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
