package de.lupu.system.utils;

import de.lupu.system.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProfileOptions {

    public static ProfileOptions loadProfile(UUID uuid){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(Main.getPlugin().getFriendManager().isUserExists(uuid)){
                try {
                    PreparedStatement ps = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * FROM psys_FriendsTable WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    ProfileOptions po = new ProfileOptions();
                    po.setUuid(uuid);
                    while(rs.next()){
                        po.setToggleRequests(Boolean.parseBoolean(rs.getString("TOGGLE_REQUEST")));
                        po.setToggleOnlineNotify(Boolean.parseBoolean(rs.getString("TOGGLE_ONLINE_NOTIFY")));
                        po.setToggleJump(Boolean.parseBoolean(rs.getString("TOGGLE_JUMP")));
                        po.setCurrentServer(rs.getString("CURRENT_SERVER"));
                        po.setLastOnline(rs.getLong("LAST_ONLINE"));
                        po.setStatus(rs.getString("STATUS"));
                    }
                    return po;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    UUID uuid;
    boolean toggleRequests;
    boolean toggleOnlineNotify;
    boolean toggleJump;
    String currentServer;
    long lastOnline;
    String status;

    public ProfileOptions(){

    }

    public ProfileOptions(UUID uuid, boolean toggleRequests, boolean toggleOnlineNotify, boolean toggleJump, String currentServer, long lastOnline, String status) {
        this.uuid = uuid;
        this.toggleRequests = toggleRequests;
        this.toggleOnlineNotify = toggleOnlineNotify;
        this.currentServer = currentServer;
        this.lastOnline = lastOnline;
        this.status = status;
        this.toggleJump = toggleJump;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isToggleRequests() {
        return toggleRequests;
    }

    public void setToggleRequests(boolean toggleRequests) {
        this.toggleRequests = toggleRequests;
    }

    public boolean isToggleOnlineNotify() {
        return toggleOnlineNotify;
    }

    public void setToggleOnlineNotify(boolean toggleOnlineNotify) {
        this.toggleOnlineNotify = toggleOnlineNotify;
    }

    public boolean isToggleJump() {
        return toggleJump;
    }

    public void setToggleJump(boolean toggleJump) {
        this.toggleJump = toggleJump;
    }

    public String getCurrentServer() {
        return currentServer;
    }

    public void setCurrentServer(String currentServer) {
        this.currentServer = currentServer;
    }

    public long getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(long lastOnline) {
        this.lastOnline = lastOnline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void saveProfileOptions(){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(Main.getPlugin().getFriendManager().isUserExists(uuid)){
                try{
                    PreparedStatement ps1 = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_FriendsTable " +
                                    "SET TOGGLE_REQUEST = ? WHERE UUID = ?");
                    PreparedStatement ps2 = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_FriendsTable " +
                                    "SET TOGGLE_ONLINE_NOTIFY = ? WHERE UUID = ?");
                    PreparedStatement ps3 = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_FriendsTable " +
                                    "SET CURRENT_SERVER = ? WHERE UUID = ?");
                    PreparedStatement ps4 = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_FriendsTable " +
                                    "SET LAST_ONLINE = ? WHERE UUID = ?");
                    PreparedStatement ps5 = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_FriendsTable " +
                                    "SET STATUS = ? WHERE UUID = ?");
                    PreparedStatement ps6 = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_FriendsTable " +
                                    "SET TOGGLE_JUMP = ? WHERE UUID = ?");
                    ps1.setString(1, String.valueOf(toggleRequests));
                    ps1.setString(2, uuid.toString());
                    ps2.setString(1, String.valueOf(toggleOnlineNotify));
                    ps2.setString(2, uuid.toString());
                    ps3.setString(1, currentServer);
                    ps3.setString(2, uuid.toString());
                    ps4.setString(1, String.valueOf(lastOnline));
                    ps4.setString(2, uuid.toString());
                    ps5.setString(1, status);
                    ps5.setString(2, uuid.toString());
                    ps6.setString(1, String.valueOf(toggleJump));
                    ps6.setString(2, uuid.toString());
                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    ps3.executeUpdate();
                    ps4.executeUpdate();
                    ps5.executeUpdate();
                    ps6.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
