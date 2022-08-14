package de.lupu.spigot.utils;

import de.lupu.spigot.SpigotMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class InventoryManager {

    public static Map<Block, Material> editingSignBlocks = new HashMap<>();

    public Inventory getMainProfile(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*6, "Freundesliste");

        FriendList onlineFav = new FriendList(new ArrayList<>());
        FriendList onlineNonFav = new FriendList(new ArrayList<>());
        FriendList fav = new FriendList(new ArrayList<>());
        FriendList nonFav = new FriendList(new ArrayList<>());

        for(FriendListEntry e : SpigotMain.getPlugin().getFriendManager().getFriendList(p.getUniqueId()).getList()){
            ProfileOptions po = ProfileOptions.loadProfile(e.getUuid());
            if(po.getLastOnline() == -1){
                if(e.isFavourite()){
                    onlineFav.add(e);
                }else{
                    onlineNonFav.add(e);
                }
            }else{
                if(e.isFavourite()){
                    fav.add(e);
                }else{
                    nonFav.add(e);
                }
            }
        }

        for(FriendListEntry e : onlineFav.getList()){
            String name = SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid());
            ProfileOptions po = ProfileOptions.loadProfile(e.getUuid());
            Item fi = new Item(Material.PLAYER_HEAD);
            fi.setSkullOwner(SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid()));
            if(po.getLastOnline() == -1) {
                fi.setDisplayName("§a" + name + " (Online) §4♥");
            }else{
                fi.setDisplayName("§c" + name + " (Offline) §4♥");
            }
            if(po.getLastOnline() != -1){
                fi.setLore(Lore.create("§7Zul. Online vor " + Util.calculateLastOnline(po.getLastOnline()), "§7Status:", "§7§o" + po.getStatus().replace("&", "§"), " ", "§7Freunde seit: " + e.getDate()));
            }else{
                fi.setLore(Lore.create("§a" + po.getCurrentServer(), "§7Status:", "§7§o" + po.getStatus().replace("&", "§"), " ", "§7Freunde seit: " + e.getDate()));
            }
            inv.addItem(fi.build());
        }

        for(FriendListEntry e : onlineNonFav.getList()){
            String name = SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid());
            ProfileOptions po = ProfileOptions.loadProfile(e.getUuid());
            Item fi = new Item(Material.PLAYER_HEAD);
            fi.setSkullOwner(SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid()));
            if(po.getLastOnline() == -1) {
                fi.setDisplayName("§a" + name + " (Online)");
            }else{
                fi.setDisplayName("§c" + name + " (Offline)");
            }
            if(po.getLastOnline() != -1){
                fi.setLore(Lore.create("§7Zul. Online vor " + Util.calculateLastOnline(po.getLastOnline()), "§7Status:", "§7§o" + po.getStatus().replace("&", "§"), " ", "§7Freunde seit: " + e.getDate()));
            }else{
                fi.setLore(Lore.create("§a" + po.getCurrentServer(), "§7Status:", "§7§o" + po.getStatus().replace("&", "§"), " ", "§7Freunde seit: " + e.getDate()));
            }
            inv.addItem(fi.build());
        }

        for(FriendListEntry e : fav.getList()){
            String name = SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid());
            ProfileOptions po = ProfileOptions.loadProfile(e.getUuid());
            Item fi = new Item(Material.SKELETON_SKULL);
            if(po.getLastOnline() == -1) {
                fi.setDisplayName("§a" + name + " (Online) §4♥");
            }else{
                fi.setDisplayName("§c" + name + " (Offline) §4♥");
            }
            if(po.getLastOnline() != -1){
                fi.setLore(Lore.create("§7Zul. Online vor " + Util.calculateLastOnline(po.getLastOnline()), "§7Status:", "§7§o" + po.getStatus().replace("&", "§"), " ", "§7Freunde seit: " + e.getDate()));
            }else{
                fi.setLore(Lore.create("§a" + po.getCurrentServer(), "§7Status:", "§7§o" + po.getStatus().replace("&", "§"), " ", "§7Freunde seit: " + e.getDate()));
            }
            inv.addItem(fi.build());
        }

        for(FriendListEntry e : nonFav.getList()){
            String name = SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid());
            ProfileOptions po = ProfileOptions.loadProfile(e.getUuid());
            Item fi = new Item(Material.SKELETON_SKULL);
            if(po.getLastOnline() == -1) {
                fi.setDisplayName("§a" + name + " (Online)");
            }else{
                fi.setDisplayName("§c" + name + " (Offline)");
            }
            if(po.getLastOnline() != -1){
                fi.setLore(Lore.create("§7Zul. Online vor " + Util.calculateLastOnline(po.getLastOnline()), "§7Status:", "§7§o" + po.getStatus().replace("&", "§"), " ", "§7Freunde seit: " + e.getDate()));
            }else{
                fi.setLore(Lore.create("§a" + po.getCurrentServer(), "§7Status:", "§7§o" + po.getStatus().replace("&", "§"), " ", "§7Freunde seit: " + e.getDate()));
            }
            inv.addItem(fi.build());
        }

        Item friends = new Item(Material.PLAYER_HEAD);
        friends.setSkullOwner(p.getName());
        friends.setDisplayName("§aFreunde");
        friends.setShiny(true);
        inv.setItem(45, friends.build());

        Item party = new Item(Material.CAKE);
        party.setDisplayName("§aParty");
        inv.setItem(46, party.build());

        Item clans = new Item(Material.MAGMA_CREAM);
        clans.setDisplayName("§aClan");
        inv.setItem(47, clans.build());

        Item settings = new Item(Material.COMPARATOR);
        settings.setDisplayName("§aEinstellungen");
        inv.setItem(49, settings.build());

        Item requests = new Item(Material.FIREWORK_ROCKET);
        requests.setDisplayName("§aAnfragen");
        inv.setItem(51, requests.build());

        Item nextPage = new Item(Material.ARROW);
        nextPage.setDisplayName("§aNächste Seite");
        inv.setItem(53, nextPage.build());

        Item g = new Item(Material.GRAY_STAINED_GLASS_PANE);
        g.setDisplayName(" ");
        inv.setItem(48, g.build());
        inv.setItem(50, g.build());
        inv.setItem(52, g.build());

        return inv;

    }

    public Inventory getSettingsProfile(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, "§aEinstellungen");

        ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());

        Item back = new Item(Material.IRON_DOOR);
        back.setDisplayName("§6Zurück zu deinen Freunden");
        inv.setItem(0, back.build());

        Item tR = new Item(Material.PLAYER_HEAD);
        tR.setDisplayName("§6Empfange Anfragen");
        if(po.isToggleRequests()) {
            tR.setLore(Lore.create("§7Aktuell: §aAn"));
        }else{
            tR.setLore(Lore.create("§7Aktuell: §cAus"));
        }
        inv.setItem(2, tR.build());

        Item tN = new Item(Material.PAPER);
        tN.setDisplayName("§6Empfange Join Nachrichten");
        if(po.isToggleOnlineNotify()) {
            tN.setLore(Lore.create("§7Aktuell: §aAn"));
        }else{
            tN.setLore(Lore.create("§7Aktuell: §cAus"));
        }
        inv.setItem(4, tN.build());

        Item tJ = new Item(Material.ENDER_PEARL);
        tJ.setDisplayName("§6Erlaube Freunden dir nach zu springen");
        if(po.isToggleJump()) {
            tJ.setLore(Lore.create("§7Aktuell: §aAn"));
        }else{
            tJ.setLore(Lore.create("§7Aktuell: §cAus"));
        }
        inv.setItem(6, tJ.build());

        Item st = new Item(Material.NAME_TAG);
        st.setDisplayName("§6Setze deinen Status");
        st.setLore(Lore.create("§7Aktuell: §e§o" + po.getStatus().replace("&", "§")));
        inv.setItem(8, st.build());

        Item g = new Item(Material.GRAY_STAINED_GLASS_PANE);
        g.setDisplayName(" ");
        inv.setItem(1, g.build());
        inv.setItem(3, g.build());
        inv.setItem(5, g.build());
        inv.setItem(7, g.build());

        return inv;

    }

    public void openStatusEditSign(Player p){
        Block b = p.getWorld().getBlockAt(p.getLocation().getBlockX(), 250, p.getLocation().getBlockZ());
        editingSignBlocks.put(b, b.getType());
        b.getWorld().setType(b.getLocation(), Material.OAK_SIGN);
        Sign s = (Sign) b.getWorld().getBlockAt(p.getLocation().getBlockX(), 250, p.getLocation().getBlockZ());
        p.openSign(s);
    }

    public Inventory getFriendOptionsProfile(Player p, UUID friend){
        String name = SpigotMain.getPlugin().getNameFetcher().getNameByUuid(friend);
        Inventory inv = Bukkit.createInventory(null, 9*3, "§b" + name + "§a's Profil");

        Item party = new Item(Material.CAKE);
        party.setDisplayName("§aIn Party einladen");
        inv.setItem(10, party.build());

        Item jump = new Item(Material.ENDER_PEARL);
        jump.setDisplayName("§aNachspringen");
        inv.setItem(12, jump.build());

        Item clan = new Item(Material.MAGMA_CREAM);
        clan.setDisplayName("§aIn Clan einladen");
        inv.setItem(14, clan.build());

        Item remove = new Item(Material.RED_DYE);
        remove.setDisplayName("§cBeende die Freundschaft mit " + name);
        inv.setItem(inv.getSize()-1, remove.build());

        FriendListEntry fle = SpigotMain.getPlugin().getFriendManager().getFriendList(p.getUniqueId()).getEntry(friend);
        Item makeFav = new Item(Material.NETHER_STAR);
        if(fle.isFavourite()) {
            makeFav.setDisplayName("§cAls Favorit entfernen");
        }else{
            makeFav.setDisplayName("§aAls Favorit markieren");
        }
        inv.setItem(16, makeFav.build());

        ItemStack is = Util.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6L" +
                "y90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1Z" +
                "jNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==", "§cZurück zu deinen Freunden"
                , null);
        inv.setItem(inv.getSize()-1-8, is);

        return inv;

    }

    public Inventory getRequestsProfile(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*6, "§aAnfragen");

        RequestsList fRl = SpigotMain.getPlugin().getFriendManager().getRequests(p.getUniqueId());
        for(RequestsListEntry e : fRl.getList()){
            Item ri = new Item(Material.PLAYER_HEAD);
            ri.setSkullOwner(SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid()));
            if(e.getReqType() == ReqType.INCOMING) {
                ri.setDisplayName("§aFreundschaftsanfrage von §b" + SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid()));
                ri.setLore(Lore.create("&7Eingehende Freundschaftsanfrage"));
            }else if(e.getReqType() == ReqType.OUTGOING){
                ri.setDisplayName("§cFreundschaftsanfrage an §b" + SpigotMain.getPlugin().getNameFetcher().getNameByUuid(e.getUuid()));
                ri.setLore(Lore.create("&7Ausgehende Freundschaftsanfrage"));
            }
            inv.addItem(ri.build());
        }

        ItemStack is = Util.createCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6L" +
                        "y90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1Z" +
                        "jNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==", "§cZurück zu deinen Freunden"
                , null);
        inv.setItem(inv.getSize()-1-8, is);

        return inv;

    }

    public Inventory getRequestsIncomingRequestProfile(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*6, "");
        return inv;
    }

    public Inventory getMainClans(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*5, "§aDein Clan");

        return inv;

    }

}
