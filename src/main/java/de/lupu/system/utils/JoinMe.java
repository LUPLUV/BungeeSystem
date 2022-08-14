package de.lupu.system.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class JoinMe {

    public static String block = "▒";

    TextComponent[] lines = {
            new TextComponent(""),
            new TextComponent(""),
            new TextComponent(""),
            new TextComponent(""),
            new TextComponent(""),
            new TextComponent(""),
            new TextComponent(""),
            new TextComponent(""),
    };
    ProxiedPlayer creator;
    BufferedImage image;
    String server;

    public ProxiedPlayer getCreator() {
        return creator;
    }

    public void setCreator(ProxiedPlayer creator) {
        this.creator = creator;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public JoinMe(BufferedImage image, ProxiedPlayer creator, String server) {
        this.image = image;
        this.creator = creator;
        this.server = server;
    }

    public void translateToLines(){
        for(int y = 0; y < 8; y++){
            for(int x = 0; x < 8; x++){
                Color c = new Color(image.getRGB(x, y));
                TextComponent toadd = new TextComponent(block);
                toadd.setColor(ChatColor.of(c));
                lines[y].addExtra(toadd);
            }
        }
    }

    public void sendToPlayer(ProxiedPlayer p){
        p.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + " §7======================================"));
        p.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + " "));
        for(TextComponent c : lines){
            TextComponent tosend = new TextComponent(Strings.getInstance().getGlobalPrefix() + " §r");
            tosend.addExtra(c);
            p.sendMessage(tosend);
        }
        p.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + " "));
        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.JoinMeMsgLine1").replace("%player%", creator.getName()).replace("%server%", server)));
        String[] msgcont = Strings.getInstance().getMessage("Global.JoinMeMsgLine2").split("%join_button%");
        TextComponent first = new TextComponent(msgcont[0]);
        TextComponent button = new TextComponent(Strings.getInstance().getMessage("Global.JoinMeMsgButtonText"));
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Strings.getInstance().getMessage("Global.JoinMeMsgButtonHoverText")).create()));
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joinme join " + creator.getName()));
        TextComponent second = new TextComponent(msgcont[1]);
        first.addExtra(button);
        first.addExtra(second);
        p.sendMessage(first);
        p.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + " "));
        p.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + " §7======================================"));
    }
}
