package de.lupu.spigot.commands;

import de.lupu.spigot.SpigotMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendsguiCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("friends.gui")){
                p.sendMessage("Keine Rechte");
            }else{
                if(strings.length == 0){
                    p.openInventory(SpigotMain.getPlugin().getInventoryManager().getMainProfile(p));
                }else{
                    p.sendMessage("fsa");
                }
            }
        }
        return false;
    }
}
