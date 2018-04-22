package repo.binarydctr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

/**
 * ******************************************************************
 * Copyright BinaryDctr (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of BinaryDctr. Distribution, reproduction, taking snippets, or
 * claiming any contents as your will break the terms of the license, and void any
 * agreements with you, the third party.
 * ******************************************************************
 **/
public class ReputationCommand implements CommandExecutor {

    Core core;

    public ReputationCommand(Core core) {
        this.core = core;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;
        FileConfiguration config = core.getConfig();

        if(args.length == 0) {
            player.sendMessage(core.prefix + "Your reputation is " + ChatColor.GOLD
                    + Integer.toString(config.getInt("Reputation.Players." + player.getName() + ".reputation")) + ChatColor.WHITE + ".");
        }

        if(args.length == 1) {
            if(args[0].equals("add")) {
                player.sendMessage(core.prefix + "Command Help");
                player.sendMessage(ChatColor.GRAY + "/reputation add <player name>, Give player +1 reputation.");
                player.sendMessage(ChatColor.GRAY + "/reputation <player name>, Check player reputation.");
            }
            Player player1 = Bukkit.getPlayer(args[0]);
            if(player1 != null) {
                if(config.contains("Reputation.Players." + player1.getName())) {
                    player.sendMessage(core.prefix + ChatColor.GOLD + player1.getName() +"'s" + ChatColor.WHITE + " reputation is " + ChatColor.GOLD
                            + Integer.toString(config.getInt("Reputation.Players." + player.getName() + ".reputation")) + ChatColor.WHITE + ".");
                } else {
                    player.sendMessage(core.prefix + "Sorry this player has no reputation.");
                }
            }
        }

        if(args.length == 2) {
            Player player1 = Bukkit.getPlayer(args[1]);
            if (args[0].equalsIgnoreCase("add")) {
                if (player1 != null) {
                    if (player1 != player) {
                        if (checkIfPlayerInList(player, player1) == false) {
                            if (config.contains("Reputation.Players." + player1.getName())) {
                                config.set("Reputation.Players." + player.getName() + ".reputation", config.getInt("Reputation.Players." + player.getName() + ".reputation") + 1);
                                core.getPlayers().get(player).add(player1.getName());
                                config.set("Reputation.Players." + player.getName() + ".players", core.getPlayers().get(player));
                                core.saveConfig();
                                player.sendMessage(core.prefix + "You just gave " + ChatColor.GOLD + player1.getName() + ChatColor.WHITE + " a +1 reputation.");
                                if (player1.isOnline()) {
                                    player1.sendMessage(core.prefix + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " just gave you a +1 reputation.");
                                }
                            } else {
                                player.sendMessage(core.prefix + "Sorry this player has no reputation.");
                            }
                        } else {
                            player.sendMessage(core.prefix + "You can't give player more than one reputation.");
                        }
                    } else {
                        player.sendMessage(core.prefix + "You can't give yourself a +1 reputation.");
                    }
                }
            }
        }
        return false;
    }

    public boolean checkIfPlayerInList(Player player, Player player1) {
        //List<String> configList = core.getConfig().getStringList("Reputation.Players." + player.getName() + ".players");
        for(int i = 0; i < core.getPlayers().get(player).size(); i++) {
            /*
            if(core.getPlayers().get(player).contains(configList.get(i))) {
                core.getPlayers().get(player).add(configList.get(i));
            }
            */
            if (core.getPlayers().get(player).get(i).equalsIgnoreCase(player1.getName())) {
                return true;
            }
        }
        return false;
    }
}
