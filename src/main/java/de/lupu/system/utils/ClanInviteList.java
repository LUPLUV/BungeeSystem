package de.lupu.system.utils;

import java.util.ArrayList;
import java.util.List;

public class ClanInviteList {

    public static ClanInviteList fromString(String s){
        if(!s.equalsIgnoreCase("none")) {
            List<ClanInvite> l = new ArrayList<>();
            String[] parts = s.split("!");
            for(String u : parts){
                l.add(ClanInvite.fromString(u));
            }
            return new ClanInviteList(l);
        }else{
            return null;
        }
    }

    List<ClanInvite> list;

    public ClanInviteList(List<ClanInvite> list) {
        this.list = list;
    }

    public List<ClanInvite> getList() {
        return list;
    }

    public void setList(List<ClanInvite> list) {
        this.list = list;
    }

    public String toString(){
        if(!list.isEmpty() && list.size() != 0){
            StringBuilder sb = new StringBuilder();
            for(ClanInvite ci : list){
                sb.append(ci.toString());
                if(list.indexOf(ci) != list.size()-1){
                    sb.append("!");
                }
            }
            return sb.toString();
        }else
            return "none";
    }
}
