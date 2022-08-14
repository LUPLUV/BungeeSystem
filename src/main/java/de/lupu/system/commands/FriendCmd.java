package de.lupu.system.commands;

import de.lupu.system.Main;
import de.lupu.system.utils.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class FriendCmd extends Command {

    public FriendCmd(String name, String permission, String... aliases) {
        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(Main.getPlugin().getMySQL().isConnected()) {
            if (commandSender instanceof ProxiedPlayer) {
                ProxiedPlayer p = (ProxiedPlayer) commandSender;
                if (!p.hasPermission("friends.use")) {
                    p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                } else if (strings.length == 0) {
                    Util.sendFriendsHelp(p, 1);
                } else if (strings[0].equalsIgnoreCase("add")) {
                    if (!p.hasPermission("friends.add")) {
                        p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                    } else {
                        // /friend add <name>
                        if (strings.length == 2) {
                            String name = strings[1];
                            if (Main.getPlugin().getNameFetcher().isUserExists(name)) {
                                UUID uuid = Main.getPlugin().getNameFetcher().getUuidByNameUUID(name);
                                if (Main.getPlugin().getFriendManager().isUserExists(p)) {
                                    if (Main.getPlugin().getFriendManager().isUserExists(uuid)) {
                                        if(!name.equalsIgnoreCase(p.getName())) {
                                            if (!Main.getPlugin().getFriendManager().isFriends(p.getUniqueId(), uuid)) {
                                                if (!Main.getPlugin().getFriendManager().isRequested(p.getUniqueId(), uuid)) {
                                                    Main.getPlugin().getFriendManager().sendRequest(p.getUniqueId(), uuid);
                                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.SentFriendRequest").replace("%player%", name)));
                                                    ProxiedPlayer t = ProxyServer.getInstance().getPlayer(name);
                                                    if (t != null) {
                                                        Util.sendRequestConfirmation(t, p.getName());
                                                    }
                                                } else {
                                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.IsRequested").replace("%player%", name)));
                                                }
                                            } else {
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.IsFriends").replace("%player%", name)));
                                            }
                                        }else{
                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.CantAddYourself")));
                                        }
                                    }
                                }
                            } else {
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.PlayerNotFound").replace("%player%", name)));
                            }
                        }
                    }

                } else if (strings[0].equalsIgnoreCase("remove")) {
                    if (!p.hasPermission("friends.remove")) {
                        p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                    } else {
                        // /friend remove <name>
                        if (strings.length == 2) {
                            String name = strings[1];
                            if (Main.getPlugin().getNameFetcher().isUserExists(name)) {
                                UUID uuid = Main.getPlugin().getNameFetcher().getUuidByNameUUID(name);
                                if (Main.getPlugin().getFriendManager().isUserExists(p)) {
                                    if (Main.getPlugin().getFriendManager().isUserExists(uuid)) {
                                        if (Main.getPlugin().getFriendManager().isFriends(p.getUniqueId(), uuid)) {
                                            Main.getPlugin().getFriendManager().removeFriend(p.getUniqueId(), uuid);
                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.Removed").replace("%player%", name)));
                                            ProxiedPlayer t = ProxyServer.getInstance().getPlayer(name);
                                            if (t != null) {
                                                t.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.GotRemoved").replace("%player%", name)));
                                            }
                                        } else {
                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.NotFriends").replace("%player%", name)));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("accept")) {
                    if (!p.hasPermission("friends.accept")) {
                        p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                    } else {
                        // /friend accept <name>
                        if (strings.length == 2) {
                            String name = strings[1];
                            if (Main.getPlugin().getNameFetcher().isUserExists(name)) {
                                UUID uuid = Main.getPlugin().getNameFetcher().getUuidByNameUUID(name);
                                if (Main.getPlugin().getFriendManager().isUserExists(p)) {
                                    if (Main.getPlugin().getFriendManager().isUserExists(uuid)) {
                                        if (!Main.getPlugin().getFriendManager().isFriends(p.getUniqueId(), uuid)) {
                                            if (Main.getPlugin().getFriendManager().getIncomingRequests(p.getUniqueId()).contains(uuid)) {
                                                Main.getPlugin().getFriendManager().makeFriends(p.getUniqueId(), uuid, false, false);
                                                Main.getPlugin().getFriendManager().removeRequests(p.getUniqueId(), uuid);
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.Accepted").replace("%player%", name)));
                                                ProxiedPlayer t = ProxyServer.getInstance().getPlayer(name);
                                                if (t != null) {
                                                    t.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.GotAccepted").replace("%player%", p.getName())));
                                                }
                                            } else {
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.HasNoRequest").replace("%player%", name)));
                                            }
                                        } else {
                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.IsFriends").replace("%player%", name)));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("deny")) {
                    if (!p.hasPermission("friends.deny")) {
                        p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                    } else {
                        // /friend deny <name>
                        if (strings.length == 2) {
                            String name = strings[1];
                            if (Main.getPlugin().getNameFetcher().isUserExists(name)) {
                                UUID uuid = Main.getPlugin().getNameFetcher().getUuidByNameUUID(name);
                                if (Main.getPlugin().getFriendManager().isUserExists(p)) {
                                    if (Main.getPlugin().getFriendManager().isUserExists(uuid)) {
                                        if (!Main.getPlugin().getFriendManager().isFriends(p.getUniqueId(), uuid)) {
                                            if (Main.getPlugin().getFriendManager().getIncomingRequests(p.getUniqueId()).contains(uuid)) {
                                                Main.getPlugin().getFriendManager().removeRequests(p.getUniqueId(), uuid);
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.Denied").replace("%player%", name)));
                                                ProxiedPlayer t = ProxyServer.getInstance().getPlayer(name);
                                                if (t != null) {
                                                    t.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.GotDenied").replace("%player%", p.getName())));
                                                }
                                            } else {
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.HasNoRequest")));
                                            }
                                        } else {
                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.IsFriends").replace("%player%", name)));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("jump")) {
                    if (!p.hasPermission("friends.jump")) {
                        p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                    } else {
                        // /friend jump <name>
                        if (strings.length == 2) {
                            String name = strings[1];
                            if (Main.getPlugin().getNameFetcher().isUserExists(name)) {
                                UUID uuid = Main.getPlugin().getNameFetcher().getUuidByNameUUID(name);
                                if (Main.getPlugin().getFriendManager().isUserExists(p)) {
                                    if (Main.getPlugin().getFriendManager().isUserExists(uuid)) {
                                        if (Main.getPlugin().getFriendManager().isFriends(p.getUniqueId(), uuid)) {
                                            ProxiedPlayer t = ProxyServer.getInstance().getPlayer(uuid);
                                            if (t != null) {
                                                if (ProfileOptions.loadProfile(uuid).isToggleJump()) {
                                                    p.connect(t.getServer().getInfo());
                                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.Jumped").replace("%player%", name)));
                                                } else {
                                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.HasToggledJump").replace("%player%", name)));
                                                }
                                            } else {
                                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.JumpNotOnline").replace("%player%", name)));
                                            }
                                        } else {
                                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.NotFriends")));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("list")) {
                    if (!p.hasPermission("friends.list")) {
                        p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                    } else {
                        // /friend list
                        if (strings.length == 1) {
                            if (Main.getPlugin().getFriendManager().isUserExists(p)) {
                                if(Main.getPlugin().getFriendManager().getFriendList(p.getUniqueId()) != null) {
                                    FriendList fl = Main.getPlugin().getFriendManager().getFriendList(p.getUniqueId());
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.ListHeader")
                                            .replace("%friends%", String.valueOf(fl.getList().size()))
                                            .replace("%max_friends%", "10")));
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.ListDescription")));
                                    for (FriendListEntry e : fl.getList()) {
                                        ProfileOptions po = ProfileOptions.loadProfile(e.getUuid());
                                        if (po.getLastOnline() != -1) {
                                            p.sendMessage(new TextComponent(Strings.getInstance()
                                                    .getMessage("Friends.ListEntryOffline")
                                                    .replace("%name%", Main.getPlugin().getNameFetcher().getNameByUuid(e.getUuid()))
                                                    .replace("%last_online%", Util.calculateLastOnline(po.getLastOnline()))
                                                    .replace("%friends_since%", e.getDate())
                                                    .replace("%status%", po.getStatus().replace("&", "§"))));
                                        } else {
                                            p.sendMessage(new TextComponent(Strings.getInstance()
                                                    .getMessage("Friends.ListEntryOnline")
                                                    .replace("%name%", Main.getPlugin().getNameFetcher().getNameByUuid(e.getUuid()))
                                                    .replace("%current_server%", po.getCurrentServer())
                                                    .replace("%friends_since%", e.getDate())
                                                    .replace("%status%", po.getStatus().replace("&", "§"))));
                                        }
                                    }
                                }else{
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.HasNoFriends")));
                                }
                            }
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("status")) {
                    if (!p.hasPermission("friends.status")) {
                        p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                    } else {
                        // /friend status <status>
                        if (strings.length >= 2) {
                            if(strings.length <= 6) {
                                if (Main.getPlugin().getFriendManager().isUserExists(p)) {
                                    String status = String.join(" ", strings).replace("status ", "");
                                    ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());
                                    po.setStatus(status);
                                    po.saveProfileOptions();
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.StatusUpdated")));
                                }
                            }else{
                                p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.StatusMaxWords")));
                            }
                        }else{
                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.StatusCmdUsage")));
                        }
                    }
                }else if(strings[0].equalsIgnoreCase("toggle")){
                    if(strings.length == 2) {
                        if (strings[1].equalsIgnoreCase("jump")) {
                            if (!p.hasPermission("friends.toggle.jump")) {
                                p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                            } else {
                                if (strings.length == 2) {
                                    ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());
                                    if (po.isToggleJump()) {
                                        po.setToggleJump(false);
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.DisabledJump")));
                                    } else {
                                        po.setToggleJump(true);
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.EnabledJump")));
                                    }
                                    po.saveProfileOptions();
                                } else {
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.ToggleCmdUsage")));
                                }
                            }
                        } else if (strings[1].equalsIgnoreCase("notify")) {
                            if (!p.hasPermission("friends.toggle.notify")) {
                                p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                            } else {
                                if (strings.length == 2) {
                                    ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());
                                    if (po.isToggleOnlineNotify()) {
                                        po.setToggleOnlineNotify(false);
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.DisabledNotify")));
                                    } else {
                                        po.setToggleOnlineNotify(true);
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.EnabledNotify")));
                                    }
                                    po.saveProfileOptions();
                                } else {
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.ToggleCmdUsage")));
                                }
                            }
                        } else if (strings[1].equalsIgnoreCase("requests")) {
                            if (!p.hasPermission("friends.toggle.requests")) {
                                p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                            } else {
                                if (strings.length == 2) {
                                    ProfileOptions po = ProfileOptions.loadProfile(p.getUniqueId());
                                    if (po.isToggleRequests()) {
                                        po.setToggleRequests(false);
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.DisabledRequests")));
                                    } else {
                                        po.setToggleRequests(true);
                                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.EnabledRequests")));
                                    }
                                    po.saveProfileOptions();
                                } else {
                                    p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.ToggleCmdUsage")));
                                }
                            }
                        } else {
                            p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.ToggleCmdUsage")));
                        }
                    }else{
                        p.sendMessage(new TextComponent(Strings.getInstance().getMessage("Friends.ToggleCmdUsage")));
                    }
                }else if(strings[0].equalsIgnoreCase("help")){
                    if(!p.hasPermission("friends.help")){
                        p.sendMessage(new TextComponent(Strings.getInstance().getFriendsNoPerms()));
                    }else{
                        if(strings.length == 1){
                            Util.sendFriendsHelp(p, 1);
                        }else if(strings.length == 2){
                            if(strings[1].equalsIgnoreCase("1")){
                                Util.sendFriendsHelp(p, 1);
                            }else if(strings[1].equalsIgnoreCase("2")){
                                Util.sendFriendsHelp(p, 2);
                            }else{
                                p.sendMessage(Strings.getInstance().getMessage("Friends.Help.CmdUsage"));
                            }
                        }else{
                            p.sendMessage(Strings.getInstance().getMessage("Friends.Help.CmdUsage"));
                        }
                    }
                }
            }
        }else{
            commandSender.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + "§cError. Could not load any Data..."));
            commandSender.sendMessage(new TextComponent(Strings.getInstance().getGlobalPrefix() + "§cPlease contact an Admin to check the MySQL Connection."));
        }
    }

}
