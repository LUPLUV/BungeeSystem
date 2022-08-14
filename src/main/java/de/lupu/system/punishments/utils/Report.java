package de.lupu.system.punishments.utils;

import de.lupu.system.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Report {

    long id;
    UUID reported;
    UUID reporter;
    long reason;

    public Report(long id) {
        this.id = id;
    }

    public Report(UUID reported) {
        this.reported = reported;
    }

    public Report() {
    }

    public Report(long id, UUID reported, UUID reporter, long reason) {
        this.id = id;
        this.reported = reported;
        this.reporter = reporter;
        this.reason = reason;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getReported() {
        return reported;
    }

    public void setReported(UUID reported) {
        this.reported = reported;
    }

    public UUID getReporter() {
        return reporter;
    }

    public void setReporter(UUID reporter) {
        this.reporter = reporter;
    }

    public long getReason() {
        return reason;
    }

    public void setReason(long reason) {
        this.reason = reason;
    }

    public boolean existsByID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_Reports WHERE ID = ?");
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

    public boolean existsByReportedAndReporter(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_Reports WHERE REPORTED = ? " +
                        "AND REPORTER = ?");
                ps.setString(1, reported.toString());
                ps.setString(2, reporter.toString());
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
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("INSERT INTO psys_Reports " +
                        "(ID,REPORTED,REPORTER,REASON) VALUES (?,?,?,?)");
                ps.setString(1, String.valueOf(id));
                ps.setString(2, reported.toString());
                ps.setString(3, reporter.toString());
                ps.setString(4, String.valueOf(reason));
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByID(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_Reports " +
                        "WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setReported(UUID.fromString(rs.getString("REPORTED")));
                    setReporter(UUID.fromString(rs.getString("REPORTER")));
                    setReason(rs.getLong("REASON"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByReportedAndReporter(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("SELECT * FROM psys_Reports " +
                        "WHERE REPORTED = ? AND REPORTER = ?");
                ps.setString(1, reported.toString());
                ps.setString(2, reporter.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    setId(rs.getLong("ID"));
                    setReason(rs.getLong("REASON"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Main.getPlugin().getMySQL().isConnected()){
            try{
                PreparedStatement ps = Main.getPlugin().getMySQL().getCon().prepareStatement("DELETE FROM psys_Reports WHERE ID = ?");
                ps.setString(1, String.valueOf(id));
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
