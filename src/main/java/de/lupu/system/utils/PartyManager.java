package de.lupu.system.utils;

import de.lupu.system.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class PartyManager {

    public boolean isPartyExits(UUID owner){
        try {
            PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * " +
                    "FROM psys_PartyTable WHERE PARTY_OWNER = ?");
            ps.setString(1, owner.toString());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createParty(UUID owner, boolean pub, List<UUID> members){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(!isPartyExits(owner)){
                try {
                    List<PartyMember> pm = new ArrayList<>();
                    for(UUID u : members){
                        pm.add(new PartyMember(u, PartyRole.MEMBER));
                    }
                    PartyMemberList pml = new PartyMemberList(pm);
                    pml.add(new PartyMember(owner, PartyRole.OWNER));
                    PreparedStatement ps = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("INSERT INTO psys_PartyTable " +
                                    "(PARTY_OWNER,PUBLIC,MEMBERS) VALUES (?,?,?)");
                    ps.setString(1, owner.toString());
                    ps.setString(2, String.valueOf(pub).toLowerCase(Locale.ROOT));
                    ps.setString(3, pml.toString());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createParty(UUID owner, boolean pub){
        if(Main.getPlugin().getMySQL().isConnected()){
            if(!isPartyExits(owner)){
                try {
                    PartyMemberList pml = new PartyMemberList();
                    pml.add(new PartyMember(owner, PartyRole.OWNER));
                    PreparedStatement ps = Main.getPlugin().getMySQL().getCon()
                            .prepareStatement("INSERT INTO psys_PartyTable " +
                                    "(PARTY_OWNER,PUBLIC,MEMBERS) VALUES (?,?,?)");
                    ps.setString(1, owner.toString());
                    ps.setString(2, String.valueOf(pub).toLowerCase(Locale.ROOT));
                    ps.setString(3, pml.toString());
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Party getPartyByMember(UUID member){
        return new Party(member);
    }

}
