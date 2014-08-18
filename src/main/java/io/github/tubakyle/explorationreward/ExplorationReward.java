package io.github.tubakyle.explorationreward;

import io.github.tubakyle.kpslib.PlayerFinder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Created by Kyle Sferrazza on 8/8/2014.
 * This file is a part of: ExplorationReward.
 * All rights reserved.
 */
public class ExplorationReward extends JavaPlugin implements Listener{
    @Override
    public void onEnable () {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onDiscover(ChunkPopulateEvent chunkPopulateEvent) {
        Player player = PlayerFinder.findNearest(chunkPopulateEvent.getChunk());
        reloadConfig();
        List<String> enabledWorlds = getConfig().getStringList("worlds-to-operate-in");
        if (!(enabledWorlds.contains(player.getWorld().getName()))) {
            return;
        }
        List<String> commandsToRun = getConfig().getStringList("commands");
        for (String command : commandsToRun) {
            command = command.replace("%p", player.getName());
            command = command.replace("%w", player.getWorld().getName());
            command = ChatColor.translateAlternateColorCodes('&', command);
            CommandSender console = getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, command);
        }
    }
}
