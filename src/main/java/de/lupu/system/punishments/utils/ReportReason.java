package de.lupu.system.punishments.utils;

import de.lupu.system.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportReason {

    long id;
    String name;
    String displayName;
    String itemType;

    public ReportReason() {
    }

    public ReportReason(long id) {
        this.id = id;
    }

    public ReportReason(long id, String name, String displayName, String itemType) {
        this.id = id;
        this.name = name;
        this.displayName = displayName;
        this.itemType = itemType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public boolean existsByID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_ReportReasons WHERE ID = ?");
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
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_ReportReasons WHERE NAME = ?");
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
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_ReportReasons WHERE DISPLAY_NAME = ?");
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

    public boolean existsByItemType(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_ReportReasons WHERE ITEM_TYPE = ?");
                ps.setString(1, itemType);
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
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("INSERT INTO psys_ReportReasons " +
                        "(ID,NAME,DISPLAY_NAME,ITEM_TYPE) VALUES (?,?,?,?)");
                ps.setString(1, String.valueOf(id));
                ps.setString(2, name);
                ps.setString(3, displayName);
                ps.setString(4, itemType);
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_ReportReasons " +
                        "WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setName(rs.getString("NAME"));
                    setDisplayName(rs.getString("DISPLAY_NAME"));
                    setItemType(rs.getString("ITEM_TYPE"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByName(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_ReportReasons " +
                        "WHERE NAME = ?");
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setId(rs.getLong("ID"));
                    setDisplayName(rs.getString("DISPLAY_NAME"));
                    setItemType(rs.getString("ITEM_TYPE"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByDisplayName(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_ReportReasons " +
                        "WHERE DISPLAY_NAME = ?");
                ps.setString(1, displayName);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setId(rs.getLong("ID"));
                    setName(rs.getString("NAME"));
                    setItemType(rs.getString("ITEM_TYPE"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByItemType(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_ReportReasons " +
                        "WHERE ITEM_TYPE = ?");
                ps.setString(1, displayName);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setId(rs.getLong("ID"));
                    setName(rs.getString("NAME"));
                    setDisplayName(rs.getString("DISPLAY_NAME"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("DELETE FROM psys_ReportReasons WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
