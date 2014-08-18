package io.github.tubakyle.explorationreward;

import io.github.tubakyle.kpslib.ConfigAccessor;
import io.github.tubakyle.kpslib.PlayerFinder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyle Sferrazza on 8/8/2014.
 * This file is a part of: ExplorationReward.
 * All rights reserved.
 */
public class ExplorationReward extends JavaPlugin implements Listener{

    private final ConfigAccessor runEvery = new ConfigAccessor(this, "events.yml");

    @Override
    public void onEnable () {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        runEvery.saveDefaultConfig();
        if (!getConfig().isSet("config-version") || !getConfig().getString("config-version").equals("1.1")) {
            Map<String, Object> configValues = getConfig().getValues(true);
            File conf = new File(getDataFolder(), "config.yml");
            conf.delete();
            reloadConfig();
            saveDefaultConfig();
            reloadConfig();
            for (Map.Entry<String, Object> entry : configValues.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                getLogger().info("KEY: " + key);
                getLogger().info("VALUE: " + value);
                getConfig().set(key, value);
            }

            saveConfig();

        }
    }

    @EventHandler
    public void onDiscover(ChunkPopulateEvent chunkPopulateEvent) {
        Player player = PlayerFinder.findNearest(chunkPopulateEvent.getChunk());
        reloadConfig();
        List<String> enabledWorlds = getConfig().getStringList("worlds-to-operate-in");
        if (!(enabledWorlds.contains(player.getWorld().getName()))) {
            return;
        }
        runEvery.reloadConfig();
        reloadConfig();
        int numOfEvents = 0;
        if (!runEvery.getConfig().isSet(player.getUniqueId().toString())) {
            runEvery.getConfig().set(player.getUniqueId().toString(), 1);
            numOfEvents = 1;
        } else {
            numOfEvents = runEvery.getConfig().getInt(player.getUniqueId().toString());
            numOfEvents++;
        }
        runEvery.getConfig().set(player.getUniqueId().toString(), numOfEvents);
        runEvery.saveConfig();
        if (!(numOfEvents >= getConfig().getInt("run-every"))) {
            return;
        }
        numOfEvents = 0;
        runEvery.getConfig().set(player.getUniqueId().toString(), numOfEvents);
        runEvery.saveConfig();
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
