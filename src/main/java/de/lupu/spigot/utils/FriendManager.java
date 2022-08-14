package de.lupu.spigot.utils;

import de.lupu.spigot.SpigotMain;
import de.lupu.system.utils.Util;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendManager {

    public boolean isUserExists(ProxiedPlayer p){
        try {
            PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon().prepareStatement("SELECT * " +
                    "FROM psys_FriendsTable WHERE UUID = ?");
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
                    "FROM psys_FriendsTable WHERE UUID = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createUser(ProxiedPlayer p, String serverName){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(!isUserExists(p.getUniqueId())){
                try {
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("INSERT INTO psys_FriendsTable " +
                                    "(UUID,TOGGLE_REQUEST,TOGGLE_ONLINE_NOTIFY,TOGGLE_JUMP,CURRENT_SERVER,LAST_ONLINE,STATUS,REQUESTS,FRIENDS) VALUES (?,?,?,?,?,?,?,?,?)");
                    ps.setString(1, p.getUniqueId().toString());
                    ps.setString(2, "true");
                    ps.setString(3, "true");
                    ps.setString(4, "true");
                    ps.setString(5, serverName);
                    ps.setString(6, "-1");
                    ps.setString(7, "&7Ich bin cool");
                    ps.setString(8, "none");
                    ps.setString(9, "none");
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public FriendList getFriendList(UUID uuid){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid)){
                try{
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * " +
                                    "FROM psys_FriendsTable WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        String raw = rs.getString("FRIENDS");
                        if(!raw.equalsIgnoreCase("none")) {
                            FriendList fl = FriendList.fromString(raw);
                            return fl;
                        }else
                            return new FriendList(new ArrayList<>());
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void setFriendList(UUID uuid, FriendList fl){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid)){
                try{
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_FriendsTable " +
                                    "SET FRIENDS = ? WHERE UUID = ?");
                    ps.setString(1, fl.toString());
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void addToFriendsList(UUID uuid, UUID toAdd, boolean fav){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid)){
                FriendList fl = getFriendList(uuid);
                if(!fl.contains(toAdd)) {
                    FriendListEntry fle = new FriendListEntry(toAdd, fav, Util.getCurrentDateFormatted());
                    fl.add(fle);
                    setFriendList(uuid, fl);
                }
            }
        }
    }

    public void makeFriends(UUID u1, UUID u2, boolean f1, boolean f2){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(u1) && isUserExists(u2)){
                addToFriendsList(u1, u2, f1);
                addToFriendsList(u2, u1, f2);
            }
        }
    }

    public void removeFriend(UUID uuid, UUID toRemove){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid) && isUserExists(toRemove)){
                if(isFriends(uuid, toRemove)) {
                    FriendList l1 = getFriendList(uuid);
                    FriendList l2 = getFriendList(toRemove);
                    l1.remove(toRemove);
                    l2.remove(uuid);
                    setFriendList(uuid, l1);
                    setFriendList(toRemove, l2);
                }
            }
        }
    }

    public RequestsList getRequests(UUID uuid){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid)){
                try{
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("SELECT * " +
                                    "FROM psys_FriendsTable WHERE UUID = ?");
                    ps.setString(1, uuid.toString());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()){
                        String raw = rs.getString("REQUESTS");
                        RequestsList l;
                        if(!raw.equalsIgnoreCase("none")) {
                            l = RequestsList.fromString(raw);
                        }else{
                            l = new RequestsList(new ArrayList<>());
                        }
                        return l;
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void setRequests(UUID uuid, RequestsList l){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(uuid)){
                try{
                    PreparedStatement ps = SpigotMain.getPlugin().getMySQL().getCon()
                            .prepareStatement("UPDATE psys_FriendsTable " +
                                    "SET REQUESTS = ? WHERE UUID = ?");
                    ps.setString(1, l.toString());
                    ps.setString(2, uuid.toString());
                    ps.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public List<UUID> getIncomingRequests(UUID uuid){
        if(SpigotMain.getPlugin().getMySQL().isConnected()) {
            if (isUserExists(uuid)) {
                List<UUID> l = new ArrayList<>();
                for(RequestsListEntry e : getRequests(uuid).getList()){
                    if(e.getReqType() == ReqType.INCOMING){
                        l.add(e.getUuid());
                    }
                }
                return l;
            }
        }
        return null;
    }

    public List<UUID> getOutgoingRequests(UUID uuid){
        if(SpigotMain.getPlugin().getMySQL().isConnected()) {
            if (isUserExists(uuid)) {
                List<UUID> l = new ArrayList<>();
                for(RequestsListEntry e : getRequests(uuid).getList()){
                    if(e.getReqType() == ReqType.OUTGOING){
                        l.add(e.getUuid());
                    }
                }
            }
        }
        return null;
    }

    public void sendRequest(UUID sender, UUID receiver){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(sender) && isUserExists(receiver)){
                RequestsList reqSender = getRequests(sender);
                RequestsList reqReceiver = getRequests(receiver);
                if(!reqSender.contains(receiver) && !reqReceiver.contains(sender)) {
                    reqSender.add(new RequestsListEntry(receiver, ReqType.OUTGOING));
                    reqReceiver.add(new RequestsListEntry(sender, ReqType.INCOMING));
                    setRequests(sender, reqSender);
                    setRequests(receiver, reqReceiver);
                }
            }
        }
    }

    public boolean isFriends(UUID u1, UUID u2){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(u1) && isUserExists(u2)){
                FriendList f1 = getFriendList(u1);
                if(f1 != null) {
                    if (f1.contains(u2)) {
                        return true;
                    }
                }else{
                    return false;
                }
            }
        }
        return false;
    }

    public void removeRequests(UUID u1, UUID u2){
        if(SpigotMain.getPlugin().getMySQL().isConnected()){
            if(isUserExists(u1) && isUserExists(u2)){
                RequestsList r1 = getRequests(u1);
                RequestsList r2 = getRequests(u2);
                r1.remove(u2);
                r2.remove(u1);
                setRequests(u1, r1);
                setRequests(u2, r2);
            }
        }
    }

    public boolean isRequested(UUID u1, UUID u2) {
        if (getRequests(u1).contains(u2) && getRequests(u2).contains(u1)){
            return true;
        }
        return false;
    }

}
