package de.lupu.spigot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendList{

    public static FriendList fromString(String s){
        if(!s.equalsIgnoreCase("none")) {
            String[] parts = s.split("!");
            List<FriendListEntry> l = new ArrayList<>();
            for (String m : parts) {
                l.add(FriendListEntry.fromString(m));
            }
            return new FriendList(l);
        }else
            return null;
    }

    List<FriendListEntry> list;

    public FriendList(List<FriendListEntry> list) {
        this.list = list;
    }

    public List<FriendListEntry> getList() {
        return list;
    }

    public void setList(List<FriendListEntry> list) {
        this.list = list;
    }

    public void add(FriendListEntry fle){
        list.add(fle);
    }

    public void remove(FriendListEntry fle){
        list.remove(fle);
    }

    public void remove(UUID uuid){
        list.removeIf(e -> e.getUuid().toString().equalsIgnoreCase(uuid.toString()));
    }

    public boolean contains(FriendListEntry fle){
        return list.contains(fle);
    }

    public FriendListEntry getEntry(UUID uuid){
        for(FriendListEntry fle : list){
            if(fle.getUuid().toString().equalsIgnoreCase(uuid.toString())){
                return fle;
            }
        }
        return null;
    }

    public boolean contains(UUID uuid){
        return contains(getEntry(uuid));
    }

    public String toString(){
        if(!list.isEmpty() && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (FriendListEntry e : list) {
                sb.append(e.toString());
                if (list.indexOf(e) != list.size() - 1) {
                    sb.append("!");
                }
            }
            return sb.toString();
        }else{
            return "none";
        }
    }

}
