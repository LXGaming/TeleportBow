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

package io.github.lxgaming.teleportbow.events;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.lxgaming.teleportbow.TeleportBow;

public class TeleportBowEvent implements Listener {
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if (!(event.getEntity() instanceof Arrow)) {
			return;
		}
		
		Arrow arrow = (Arrow) event.getEntity();
		
		if (!(arrow.getShooter() instanceof Player)) {
			return;
		}
		
		Player player = (Player) arrow.getShooter();
		
		if (!player.hasPermission("bowteleport.use")) {
			return;
		}
		
		if (!player.getInventory().getItemInMainHand().getType().equals(Material.BOW) && !player.getInventory().getItemInOffHand().getType().equals(Material.BOW)) {
			return;
		}
		
		if (player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.ARROW_INFINITE) != 10 && player.getInventory().getItemInOffHand().getEnchantmentLevel(Enchantment.ARROW_INFINITE) != 10) {
			return;
		}
		
		Location location = arrow.getLocation();
		location.setPitch(player.getLocation().getPitch());
		location.setYaw(player.getLocation().getYaw());
		player.teleport(location);
		player.playSound(player.getLocation(), Sound.valueOf(TeleportBow.getInstance().getConfiguration().getConfig().getString("TeleportBow.Sound")), 1.0f, 1.0f);
		arrow.remove();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().hasPermission("bowteleport.give")) {
			event.getPlayer().getInventory().setItem(TeleportBow.getInstance().getConfiguration().getConfig().getInt("TeleportBow.Bow.Slot"), giveBow());
			event.getPlayer().getInventory().setItem(TeleportBow.getInstance().getConfiguration().getConfig().getInt("TeleportBow.Arrow.Slot"), giveArrow());
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (event.getPlayer().hasPermission("bowteleport.give")) {
			event.getPlayer().getInventory().setItem(TeleportBow.getInstance().getConfiguration().getConfig().getInt("TeleportBow.Bow.Slot"), giveBow());
			event.getPlayer().getInventory().setItem(TeleportBow.getInstance().getConfiguration().getConfig().getInt("TeleportBow.Arrow.Slot"), giveArrow());
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (event.getPlayer().hasPermission("bowteleport.give")) {
			event.getPlayer().getInventory().remove(giveBow());
			event.getPlayer().getInventory().remove(giveArrow());
		}
		return;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
		if (event.getPlayer().hasPermission("bowteleport.give")) {
			event.getPlayer().getInventory().setItem(TeleportBow.getInstance().getConfiguration().getConfig().getInt("TeleportBow.Bow.Slot"), giveBow());
			event.getPlayer().getInventory().setItem(TeleportBow.getInstance().getConfiguration().getConfig().getInt("TeleportBow.Arrow.Slot"), giveArrow());
		}
		return;
	}
	
	private ItemStack giveBow() {
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.setUnbreakable(true);
		bowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		bowMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 10, true);
		bowMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "TeleportBow");
		bowMeta.setLore(Arrays.asList(ChatColor.GOLD + "" + ChatColor.BOLD + "Fire the bow to teleport!"));
		bow.setItemMeta(bowMeta);
		return bow;
	}
	
	private ItemStack giveArrow() {
		ItemStack arrow = new ItemStack(Material.ARROW, 1);
		ItemMeta arrowMeta = arrow.getItemMeta();
		arrowMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "TeleportFuel");
		arrowMeta.setLore(Arrays.asList(ChatColor.GOLD + "" + ChatColor.BOLD + "Don't lose it!"));
		arrow.setItemMeta(arrowMeta);
		return arrow;
	}
}