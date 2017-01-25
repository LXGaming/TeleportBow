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

package io.github.lxgaming.teleportbow.configuration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.io.ByteStreams;

import io.github.lxgaming.teleportbow.TeleportBow;

public class Configuration {
	
	private YamlConfiguration config;
	
	public void loadConfig() {
		if (!TeleportBow.getInstance().getDataFolder().exists()) {
			TeleportBow.getInstance().getDataFolder().mkdir();
		}
		
		config = loadFile("config.yml", config);
	}
	
	public YamlConfiguration loadFile(String name, YamlConfiguration config) {
		try {
			File file = new File(TeleportBow.getInstance().getDataFolder(), name);
			
			if (!file.exists()) {
				file.createNewFile();
				InputStream inputStream = TeleportBow.getInstance().getResource(name);
				OutputStream outputStream = new FileOutputStream(file);
				ByteStreams.copy(inputStream, outputStream);
				TeleportBow.getInstance().getLogger().info("Successfully created " + name);
			}
			
			return YamlConfiguration.loadConfiguration(file);
		} catch (IOException | NullPointerException | SecurityException ex) {
			TeleportBow.getInstance().getLogger().severe("Exception loading " + name);
			ex.printStackTrace();
		}
		return null;
	}
	
	public void saveFile(String name, YamlConfiguration config) {
		try {
			File file = new File(TeleportBow.getInstance().getDataFolder(), name);
			config.save(file);
		} catch (IOException | NullPointerException | SecurityException ex) {
			TeleportBow.getInstance().getLogger().severe("Exception saving " + name);
			ex.printStackTrace();
		}
	}
	
	public YamlConfiguration getConfig() {
		return this.config;
	}
}