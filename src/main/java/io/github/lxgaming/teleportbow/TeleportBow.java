package io.github.lxgaming.teleportbow;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.lxgaming.teleportbow.commands.TeleportBowCommand;
import io.github.lxgaming.teleportbow.events.EventManager;

public class TeleportBow extends JavaPlugin {
	
	public static TeleportBow instance;
	
	@Override
	public void onEnable() {
		instance = this;
		this.getCommand("teleportbow").setExecutor(new TeleportBowCommand());
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new EventManager(), this);
		getLogger().info("TeleportBow has started!");
	}
	
	@Override
	public void onDisable() {
		instance = null;
		getLogger().info("TeleportBow has stopped!");
	}
}