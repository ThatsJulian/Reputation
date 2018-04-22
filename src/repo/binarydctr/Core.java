package repo.binarydctr;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ******************************************************************
 * Copyright BinaryDctr (c) 2015. All Rights Reserved.
 * Any code contained within this document, and any associated APIs with similar branding
 * are the sole property of BinaryDctr. Distribution, reproduction, taking snippets, or
 * claiming any contents as your will break the terms of the license, and void any
 * agreements with you, the third party.
 * ******************************************************************
 **/
public class Core extends JavaPlugin implements Listener {

    public String prefix = ChatColor.WHITE + "[" + ChatColor.GOLD + "Reputation" + ChatColor.WHITE + "]" + ChatColor.WHITE + " ";

    private HashMap<Player, List<String>> players = new HashMap<>();

    public HashMap<Player, List<String>> getPlayers() {
        return players;
    }

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "Has been enabled, created by " + ChatColor.YELLOW + "BinaryDctr");
        getCommand("reputation").setExecutor(new ReputationCommand(this));
        getServer().getPluginManager().registerEvents(this, this);
        if(Bukkit.getOnlinePlayers().size() > 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                List<String> playerz = new ArrayList<>();
                if (getConfig().contains("Reputation.Players." + player.getName())) {
                    playerz = getConfig().getStringList("Reputation.Players." + player.getName() + ".players");
                    players.put(player, playerz);
                } else {
                    getConfig().set("Reputation.Players." + player.getName() + ".reputation", 0);
                    getConfig().set("Reputation.Players." + player.getName() + ".players", null);
                    saveConfig();
                    players.put(player, playerz);
                }
            }
        }

    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            players.remove(player);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<String> playerz = new ArrayList<>();
        if(!getConfig().contains("Reputation.Players." + player.getName())) {
            getConfig().set("Reputation.Players." + player.getName() + ".reputation", 0);
            getConfig().set("Reputation.Players." + player.getName() + ".players", null);
            saveConfig();
            players.put(player, playerz);
        } else {
            playerz = getConfig().getStringList("Reputation.Players." + player.getName() + ".players");
            players.put(player, playerz);
        }
    }

}
