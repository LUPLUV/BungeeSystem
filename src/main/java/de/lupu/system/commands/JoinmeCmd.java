package de.lupu.system.commands;

import de.lupu.system.utils.JoinMe;
import de.lupu.system.utils.Strings;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JoinmeCmd extends Command {

    public static List<JoinMe> joinMes = new ArrayList<>();

    public static boolean joinMesContains(String target){
        for(JoinMe j : joinMes){
            if(j.getCreator().getName().equalsIgnoreCase(target)){
                return true;
            }
        }
        return false;
    }

    public static JoinMe getJoinMe(String target){
        for(JoinMe j : joinMes){
            if(j.getCreator().getName().equalsIgnoreCase(target)){
                return j;
            }
        }
        return null;
    }

    public JoinmeCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(commandSender instanceof ProxiedPlayer){
            ProxiedPlayer p = (ProxiedPlayer) commandSender;
            if(strings.length == 0){
                if(p.hasPermission("global.joinme.create")) {
                    BufferedImage imageToSend = null;
                    try {
                        imageToSend = ImageIO.read(new URL("https://cravatar.eu/avatar/" + p.getName() + "/8.png").openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.Error")));
                    }
                    JoinMe m = new JoinMe(imageToSend, p, p.getServer().getInfo().getName());
                    m.translateToLines();
                    ProxyServer.getInstance().getPlayers().forEach(m::sendToPlayer);
                    joinMes.add(m);
                }else{
                    p.sendMessage(new TextComponent(Strings.getInstance().getGlobalNoPerms()));
                }
            }else if(strings[0].equalsIgnoreCase("join")){
                if(!p.hasPermission("global.joinme.join")){
                    p.sendMessage(new TextComponent(Strings.getInstance().getGlobalNoPerms()));
                }else{
                    if(strings.length == 2) {
                        String target = strings[1];
                        if (!target.equalsIgnoreCase(p.getName())) {
                            if (joinMesContains(target)) {
                                ServerInfo si = ProxyServer.getInstance().getServerInfo(getJoinMe(target).getServer());
                                if (si != null) {
                                    p.connect(si);
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.JoinMeJoined").replace("%server%", si.getName()).replace("%creator%", target)));
                                } else {
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.JoinMeServerNotFound").replace("%creator%", target)));
                                }
                            } else {
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.JoinMeNotExisting")));
                            }
                        }else{
                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.JoinMeCantJoinOwn")));
                        }
                    }else{
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.JoinMeCmdUsage")));
                    }
                }
            }else{
                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Global.JoinMeCmdUsage")));
            }
        }
    }
}
