package de.lupu.system.utils;

import java.util.ArrayList;
import java.util.List;

public class PartyMemberList {

    public static PartyMemberList fromString(String s){
        String[] parts = s.split("!");
        List<PartyMember> l = new ArrayList<>();
        for(String m : parts){
            l.add(PartyMember.fromString(m));
        }
        return new PartyMemberList(l);
    }

    List<PartyMember> list;

    public PartyMemberList(List<PartyMember> list) {
        this.list = list;
    }

    public PartyMemberList() {
        this.list = new ArrayList<>();
    }

    public List<PartyMember> getList() {
        return list;
    }

    public void setList(List<PartyMember> list) {
        this.list = list;
    }

    public void add(PartyMember member){
        list.add(member);
    }

    public void remove(PartyMember member){
        list.remove(member);
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
