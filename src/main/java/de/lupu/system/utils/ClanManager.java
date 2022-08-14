package de.lupu.system.utils;

import de.lupu.system.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ClanManager {

    public static String getClanCreationCurrentDateFormatted(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(date);
    }

    public boolean isClanExistsTag(String tag){
        try {
            PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * " +
                    "FROM psys_ClanTable WHERE TAG = ?");
            ps.setString(1, tag);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isClanExistsName(String name){
        try {
            PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * " +
                    "FROM psys_ClanTable WHERE NAME = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createClan(Clan clan){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(!isClanExistsName(clan.getName())){
                try {
                    PreparedStatement ps = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("INSERT INTO psys_ClanTable " +
                                    "(NAME,TAG,OWNER,COLOR,DATE,PUBLIC,MEMBERS) VALUES (?,?,?,?,?,?,?)");
                    ps.setString(1, clan.getName());
                    ps.setString(2, clan.getTag().toUpperCase());
                    ps.setString(3, clan.getOwner().toString());
                    ps.setString(4, clan.getColor().toString());
                    ps.setString(5, clan.getCreationDate());
                    ps.setString(6, String.valueOf(clan.isTogglePublic()));
                    ps.setString(7, clan.getMembers().toString());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createClan(String name, String tag, UUID owner, Color color, boolean togglePublic){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(!isClanExistsName(name)){
                Clan c = new Clan(name, tag, owner, color, getClanCreationCurrentDateFormatted(), togglePublic, ClanMemberList.getOnlyOwnerList(owner));
                createClan(c);
            }
        }
    }

    public Clan getClanByName(String name){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(isClanExistsName(name)){
                try {
                    PreparedStatement ps = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * " +
                                    "FROM psys_ClanTable WHERE NAME = ?");
                    ps.setString(1, name);
                    Clan c = new Clan();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        c.setName(rs.getString("NAME"));
                        c.setTag(rs.getString("TAG"));
                        c.setOwner(UUID.fromString(rs.getString("OWNER")));
                        c.setColor(Color.valueOf(rs.getString("COLOR")));
                        c.setCreationDate(rs.getString("DATE"));
                        c.setTogglePublic(Boolean.valueOf(rs.getString("PUBLIC")));
                        c.setMembers(ClanMemberList.fromString(rs.getString("MEMBERS")));
                        return c;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Clan getClanByTag(String tag){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(isClanExistsName(tag)){
                try {
                    PreparedStatement ps = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * " +
                                    "FROM psys_ClanTable WHERE TAG = ?");
                    ps.setString(1, tag);
                    Clan c = new Clan();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        c.setName(rs.getString("NAME"));
                        c.setTag(rs.getString("TAG"));
                        c.setOwner(UUID.fromString(rs.getString("OWNER")));
                        c.setColor(Color.valueOf(rs.getString("COLOR")));
                        c.setCreationDate(rs.getString("DATE"));
                        c.setTogglePublic(Boolean.valueOf(rs.getString("PUBLIC")));
                        c.setMembers(ClanMemberList.fromString(rs.getString("MEMBERS")));
                        return c;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void saveClan(Clan clan){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(!isClanExistsName(clan.getName())){
                try {
                    PreparedStatement ps = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_ClanTable " +
                                    "SET NAME = ?, TAG = ?, OWNER = ?, COLOR = ?, DATE = ?, PUBLIC = ?, MEMBERS = ? WHERE NAME = ?");
                    ps.setString(1, clan.getName());
                    ps.setString(2, clan.getTag().toUpperCase());
                    ps.setString(3, clan.getOwner().toString());
                    ps.setString(4, clan.getColor().toString());
                    ps.setString(5, clan.getCreationDate());
                    ps.setString(6, String.valueOf(clan.isTogglePublic()));
                    ps.setString(7, clan.getMembers().toString());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
