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

package io.github.lxgaming.teleportbow;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.lxgaming.teleportbow.commands.TeleportBowCommand;
import io.github.lxgaming.teleportbow.configuration.Configuration;
import io.github.lxgaming.teleportbow.events.TeleportBowEvent;

public class TeleportBow extends JavaPlugin {
	
	private static TeleportBow instance;
	private Configuration configuration;
	
	@Override
	public void onEnable() {
		instance = this;
		configuration = new Configuration();
		configuration.loadConfig();
		getCommand("teleportbow").setExecutor(new TeleportBowCommand());
		getServer().getPluginManager().registerEvents(new TeleportBowEvent(), this);
		getLogger().info("TeleportBow has started!");
	}
	
	@Override
	public void onDisable() {
		instance = null;
		getLogger().info("TeleportBow has stopped!");
	}
	
	public static TeleportBow getInstance() {
		return instance;
	}
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
}