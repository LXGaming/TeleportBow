package io.github.lxgaming.teleportbow.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TeleportBowCommand implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("teleportbow")) {
			sender.sendMessage(ChatColor.GOLD + "===== " + ChatColor.GREEN + "TeleportBow" + ChatColor.GOLD + " ===== ");
			sender.sendMessage(ChatColor.GOLD + "Version - " + ChatColor.AQUA + "0.1.0");
			sender.sendMessage(ChatColor.GOLD + "Author - " + ChatColor.AQUA + "LX_Gaming");
			return true;
		}
		return false;
	}	
}