package de.lupu.spigot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RequestsList {

    public static RequestsList fromString(String s){
        if(!s.equalsIgnoreCase("none")) {
            List<RequestsListEntry> l = new ArrayList<>();
            String[] parts = s.split("!");
            for(String u : parts){
                l.add(RequestsListEntry.fromString(u));
            }
            return new RequestsList(l);
        }else{
            return null;
        }
    }

    List<RequestsListEntry> list;

    public RequestsList(List<RequestsListEntry> list) {
        this.list = list;
    }

    public List<RequestsListEntry> getList() {
        return list;
    }

    public void setList(List<RequestsListEntry> list) {
        this.list = list;
    }

    public void add(RequestsListEntry rle){
        list.add(rle);
    }

    public void remove(RequestsListEntry rle){
        list.remove(rle);
    }

    public void remove(UUID uuid){
        list.removeIf(e -> e.getUuid().toString().equalsIgnoreCase(uuid.toString()));
    }

    public boolean contains(RequestsListEntry rle){
        return list.contains(rle);
    }

    public boolean contains(UUID uuid){
        for(RequestsListEntry e : list){
            if(e.getUuid().toString().equalsIgnoreCase(uuid.toString())){
                return true;
            }
        }
        return false;
    }

    public String toString(){
        if(!list.isEmpty() && list.size() != 0){
            StringBuilder sb = new StringBuilder();
            for(RequestsListEntry rle : list){
                sb.append(rle.toString());
                if(list.indexOf(rle) != list.size()-1){
                    sb.append("!");
                }
            }
            return sb.toString();
        }else
            return "none";
    }

}
