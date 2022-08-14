package de.lupu.system.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClanMemberList {

    public static ClanMemberList fromString(String s){
        String[] parts = s.split("!");
        List<ClanMember> l = new ArrayList<>();
        for(String m : parts){
            l.add(ClanMember.fromString(m));
        }
        return new ClanMemberList(l);
    }

    public static ClanMemberList getOnlyOwnerList(UUID owner){
        ClanMemberList cml = new ClanMemberList();
        cml.add(new ClanMember(owner, ClanRole.OWNER));
        return cml;
    }

    List<ClanMember> list;

    public ClanMemberList() {
    }

    public ClanMemberList(List<ClanMember> list) {
        this.list = list;
    }

    public List<ClanMember> getList() {
        return list;
    }

    public void setList(List<ClanMember> list) {
        this.list = list;
    }

    public void add(ClanMember member){
        list.add(member);
    }

    public void remove(ClanMember member){
        list.remove(member);
    }

    public void remove(UUID member){
        for(ClanMember cm : this.list){
            if(cm.getUuid().toString().equalsIgnoreCase(member.toString())){
                this.list.remove(cm);
            }
        }
    }

    public ClanMember get(UUID member){
        for(ClanMember cm : this.list){
            if(cm.getUuid().toString().equalsIgnoreCase(member.toString())){
                return cm;
            }
        }
        return null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(!list.isEmpty() && list.size() != 0) {
            list.forEach(all -> {
                sb.append(all.toString());
                if (list.indexOf(all) != list.size() - 1) {
                    sb.append("!");
                }
            });
            return sb.toString();
        }else{
            return "none";
        }
    }

}
