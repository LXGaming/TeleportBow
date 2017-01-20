/*
 * Copyright 2017 Alex Thomson
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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