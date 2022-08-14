package de.lupu.system.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {

    public static String replaceAll(String s){
        return s.replace("%global_prefix%", Strings.getInstance().getGlobalPrefix())
                .replace("%friends_prefix%", Strings.getInstance().getFriendsPrefix())
                .replace("%party_prefix%", Strings.getInstance().getPartyPrefix())
                .replace("%clan_prefix%", Strings.getInstance().getClanPrefix())
                .replace("%punish_prefix%", Strings.getInstance().getPunishPrefix())
                .replace("&", "§");
    }

    public static void sendFriendsHelp(ProxiedPlayer p, int page){
        /**
         * Friends » « Hilfe Seite 1/2 »
         */
        // TODO: Add messages to messages File
        TextComponent navBar = new TextComponent(Strings.getInstance().getMessage("Friends.Help.Title").replace("%current_page%", String.valueOf(page)));
        p.sendMessage(navBar);

        TextComponent addFriend = new TextComponent(Strings.getInstance().getMessage("Friends.Help.AddFriend"));
        addFriend.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/friend add "));
        p.sendMessage(addFriend);

        TextComponent removeFriend = new TextComponent(Strings.getInstance().getMessage("Friends.Help.RemoveFriend"));
        removeFriend.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/friend remove "));
        p.sendMessage(removeFriend);

        TextComponent listFriends = new TextComponent(Strings.getInstance().getMessage("Friends.Help.ListFriends"));
        listFriends.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/friend list"));
        p.sendMessage(listFriends);

        TextComponent jumpFriend = new TextComponent(Strings.getInstance().getMessage("Friends.Help.JumpFriend"));
        jumpFriend.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/friend jump "));
        p.sendMessage(jumpFriend);

        TextComponent acceptRequest = new TextComponent(Strings.getInstance().getMessage("Friends.Help.AcceptRequest"));
        acceptRequest.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/friend accept "));
        p.sendMessage(acceptRequest);

        TextComponent denyRequest = new TextComponent(Strings.getInstance().getMessage("Friends.Help.DenyRequest"));
        denyRequest.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/friend deny "));
        p.sendMessage(denyRequest);

    }

    public static void sendPartyHelp(ProxiedPlayer p, int page){
        /**
         * Party » « Hilfe Seite 1/2 »
         */
        // TODO: Add messages to messages File
        TextComponent navBar = new TextComponent(Strings.getInstance().getMessage("Party.Help.Title").replace("%current_page%", String.valueOf(page)));
        p.sendMessage(navBar);

        TextComponent invite = new TextComponent(Strings.getInstance().getMessage("Party.Help.Invite"));
        invite.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party invite "));
        p.sendMessage(invite);

        TextComponent kick = new TextComponent(Strings.getInstance().getMessage("Party.Help.Kick"));
        kick.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party kick "));
        p.sendMessage(kick);

        TextComponent leave = new TextComponent(Strings.getInstance().getMessage("Party.Help.Leave"));
        leave.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party leave"));
        p.sendMessage(leave);

        TextComponent list = new TextComponent(Strings.getInstance().getMessage("Party.Help.List"));
        list.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party list"));
        p.sendMessage(list);

        TextComponent jump = new TextComponent(Strings.getInstance().getMessage("Party.Help.Jump"));
        jump.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party jump"));
        p.sendMessage(jump);

        TextComponent warp = new TextComponent(Strings.getInstance().getMessage("Party.Help.Warp"));
        warp.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party warp"));
        p.sendMessage(warp);

        TextComponent promote = new TextComponent(Strings.getInstance().getMessage("Party.Help.Promote"));
        promote.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party promote "));
        p.sendMessage(promote);

        TextComponent demote = new TextComponent(Strings.getInstance().getMessage("Party.Help.Demote"));
        demote.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party demote "));
        p.sendMessage(demote);

        TextComponent disband = new TextComponent(Strings.getInstance().getMessage("Party.Help.Disband"));
        disband.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party disband"));
        p.sendMessage(disband);

    }

    public static void sendRequestConfirmation(ProxiedPlayer p, String sender){
        // TODO: Add messages to messages File
        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.RequestIncoming").replace("%player%", sender)));
        TextComponent accept = new TextComponent(Strings.getInstance().getMessage("Friends.AcceptButton.Text"));
        TextComponent deny = new TextComponent(Strings.getInstance().getMessage("Friends.DenyButton.Text"));
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Strings.getInstance().getMessage("Friends.AcceptButton.HoverText")).create()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Strings.getInstance().getMessage("Friends.DenyButton.HoverText")).create()));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + sender));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + sender));
        TextComponent l = new TextComponent(Strings.getInstance().getFriendsPrefix() + "          §7");
        l.addExtra(accept);
        l.addExtra(" §8- ");
        l.addExtra(deny);
        p.sendMessage(l);
    }

    public static String getCurrentDateFormatted(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static String calculateLastOnline(long last){

        if(last != -1) {
            long l = System.currentTimeMillis()-last;
            long years = 0;
            long months = 0;
            long weeks = 0;
            long days = 0;
            long hours = 0;
            long minutes = 0;
            long seconds = 0;

            while (l >= 1000) {
                seconds++;
                l -= 1000;
            }
            while (seconds >= 60) {
                minutes++;
                seconds -= 60;
            }
            while (minutes >= 60) {
                hours++;
                minutes -= 60;
            }
            while (hours >= 24) {
                days++;
                hours -= 24;
            }
            while (days >= 7) {
                weeks++;
                days -= 7;
            }
            while (weeks >= 4) {
                months++;
                weeks -= 4;
            }
            while (months >= 12) {
                years++;
                months -= 12;
            }

            if (years > 0) {
                if (years == 1) {
                    return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Year");
                } else {
                    return years + " " + Strings.getInstance().getMessage("Friends.LastOnline.Years");
                }
            } else if (months > 0) {
                if (months == 1) {
                    return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Month");
                } else {
                    return months + " " + Strings.getInstance().getMessage("Friends.LastOnline.Months");
                }
            } else if (weeks > 0) {
                if (weeks == 1) {
                    return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Week");
                } else {
                    return weeks + " " + Strings.getInstance().getMessage("Friends.LastOnline.Weeks");
                }
            } else if (days > 0) {
                if (days == 1) {
                    return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Day");
                } else {
                    return days + " " + Strings.getInstance().getMessage("Friends.LastOnline.Days");
                }
            } else if (hours > 0) {
                if (hours == 1) {
                    return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Hour");
                } else {
                    return hours + " " + Strings.getInstance().getMessage("Friends.LastOnline.Hours");
                }
            } else if (minutes > 0) {
                if (minutes == 1) {
                    return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Minute");
                } else {
                    return minutes + " " + Strings.getInstance().getMessage("Friends.LastOnline.Minutes");
                }
            } else if (seconds > 0) {
                return Strings.getInstance().getMessage("Friends.LastOnline.NotLongAgo");
            }
        }
        return null;
    }

    public static String formatPunishDuration(String duration){
        if(duration.endsWith("S")){
            Long d = Long.valueOf(duration.replace("S", ""));
            if(d > 1){
                return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Second");
            }else{
                return d + " " + Strings.getInstance().getMessage("Friends.LastOnline.Seconds");
            }
        }else if(duration.endsWith("m")){
            Long d = Long.valueOf(duration.replace("S", ""));
            if(d > 1){
                return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Minute");
            }else{
                return d + " " + Strings.getInstance().getMessage("Friends.LastOnline.Minutes");
            }
        }else if(duration.endsWith("H")){
            Long d = Long.valueOf(duration.replace("S", ""));
            if(d > 1){
                return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Hour");
            }else{
                return d + " " + Strings.getInstance().getMessage("Friends.LastOnline.Hours");
            }
        }else if(duration.endsWith("D")){
            Long d = Long.valueOf(duration.replace("S", ""));
            if(d > 1){
                return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Day");
            }else{
                return d + " " + Strings.getInstance().getMessage("Friends.LastOnline.Days");
            }
        }else if(duration.endsWith("W")){
            Long d = Long.valueOf(duration.replace("S", ""));
            if(d > 1){
                return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Week");
            }else{
                return d + " " + Strings.getInstance().getMessage("Friends.LastOnline.Weeks");
            }
        }else if(duration.endsWith("M")){
            Long d = Long.valueOf(duration.replace("S", ""));
            if(d > 1){
                return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Month");
            }else{
                return d + " " + Strings.getInstance().getMessage("Friends.LastOnline.Months");
            }
        }else if(duration.endsWith("Y")){
            Long d = Long.valueOf(duration.replace("S", ""));
            if(d > 1){
                return "1 " + Strings.getInstance().getMessage("Friends.LastOnline.Year");
            }else{
                return d + " " + Strings.getInstance().getMessage("Friends.LastOnline.Years");
            }
        }
        return null;
    }

    public static boolean punishDurationValid(String duration){
        if(duration.equalsIgnoreCase("permanent")){
            return true;
        }else if(duration.equalsIgnoreCase("layout")){
            return true;
        }else{
            try {
                Long l = Long.parseLong(duration.replace("S", "").replace("m", "").replace("H", "")
                        .replace("D", "").replace("M", "").replace("Y", ""));
                if(l >= 1){
                    return true;
                }
            } catch (NumberFormatException ignored) {}
        }
        return false;
    }

}
