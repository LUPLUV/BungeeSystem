package de.lupu.spigot.events;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.lupu.spigot.SpigotMain;
import de.lupu.spigot.utils.FriendList;
import de.lupu.spigot.utils.FriendListEntry;
import de.lupu.spigot.utils.ProfileOptions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ClickEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType() == Material.AIR) return;

        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        String title = e.getView().getTitle();
        ItemStack item = e.getCurrentItem();
        Material mat = item.getType();
        ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());

        if(title.equalsIgnoreCase("Freundesliste")){
            e.setCancelled(true);
            if(mat == Material.COMPARATOR){
                p.openInventory(SpigotMain.getPlugin().getInventoryManager().getSettingsProfile(p));
            }else if(mat == Material.PLAYER_HEAD || mat == Material.SKELETON_SKULL){
                String name = item.getItemMeta().getDisplayName().replace("§a", "").replace("§c", "")
                        .replace(" (Online)", "").replace(" (Offline)", "").replace(" §4♥", "");
                UUID uuid = SpigotMain.getPlugin().getNameFetcher().getUuidByNameUUID(name);
                p.openInventory(SpigotMain.getPlugin().getInventoryManager().getFriendOptionsProfile(p, uuid));
            }
        }else if(title.equalsIgnoreCase("§aEinstellungen")){
            e.setCancelled(true);
            if(mat == Material.IRON_DOOR){
                p.openInventory(SpigotMain.getPlugin().getInventoryManager().getMainProfile(p));
            }else if(mat == Material.PLAYER_HEAD){
                if(po.isToggleRequests()){
                    po.setToggleRequests(false);
                }else{
                    po.setToggleRequests(true);
                }
                po.saveProfileOptions();
                p.openInventory(SpigotMain.getPlugin().getInventoryManager().getSettingsProfile(p));
            }else if(mat == Material.PAPER){
                if(po.isToggleOnlineNotify()){
                    po.setToggleOnlineNotify(false);
                }else{
                    po.setToggleOnlineNotify(true);
                }
                po.saveProfileOptions();
                p.openInventory(SpigotMain.getPlugin().getInventoryManager().getSettingsProfile(p));
            }else if(mat == Material.ENDER_PEARL){
                if(po.isToggleJump()){
                    po.setToggleJump(false);
                }else{
                    po.setToggleJump(true);
                }
                po.saveProfileOptions();
                p.openInventory(SpigotMain.getPlugin().getInventoryManager().getSettingsProfile(p));
            }else if(mat == Material.NAME_TAG){
                SpigotMain.getPlugin().getInventoryManager().openStatusEditSign(p);
            }
        }else if(title.endsWith("Profil")){
            e.setCancelled(true);
            String name = title.replace("§a's Profil", "").replace("§b", "");
            UUID uuid = SpigotMain.getPlugin().getNameFetcher().getUuidByNameUUID(name);
            if(mat == Material.NETHER_STAR){
                FriendList fl = SpigotMain.getPlugin().getFriendManager().getFriendList(p.getUniqueId());
                FriendListEntry fle = fl.getEntry(uuid);
                if(fle.isFavourite()){
                    fle.setFavourite(false);
                }else{
                    fle.setFavourite(true);
                }
                fl.remove(uuid);
                fl.add(fle);
                SpigotMain.getPlugin().getFriendManager().setFriendList(p.getUniqueId(), fl);
                p.openInventory(SpigotMain.getPlugin().getInventoryManager().getFriendOptionsProfile(p, uuid));
            }else if(mat == Material.RED_DYE){
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("friend");
                out.writeUTF("remove");
                out.writeUTF(name);
                p.sendPluginMessage(SpigotMain.getPlugin(), "BungeeCord", out.toByteArray());
                p.closeInventory();
            }else if(mat == Material.ENDER_PEARL){
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("friend");
                out.writeUTF("jump");
                out.writeUTF(name);
                p.sendPluginMessage(SpigotMain.getPlugin(), "BungeeCord", out.toByteArray());
                p.closeInventory();
            }else if(mat == Material.CAKE){
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("friend");
                out.writeUTF("jump");
                out.writeUTF(name);
                p.sendPluginMessage(SpigotMain.getPlugin(), "BungeeCord", out.toByteArray());
                p.closeInventory();
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§cZurück zu deinen Freunden")){
                p.openInventory(SpigotMain.getPlugin().getInventoryManager().getMainProfile(p));
            }
        }

    }

}
