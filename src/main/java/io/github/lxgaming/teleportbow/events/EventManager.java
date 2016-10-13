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
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EventManager implements Listener {
	
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
		player.playSound(player.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1.0f, 1.0f);
		arrow.remove();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().hasPermission("bowteleport.give")) {
			event.getPlayer().getInventory().setItem(2, giveBow());
			event.getPlayer().getInventory().setItem(9, giveArrow());
		}
		return;
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (event.getPlayer().hasPermission("bowteleport.give")) {
			event.getPlayer().getInventory().setItem(2, giveBow());
			event.getPlayer().getInventory().setItem(9, giveArrow());
		}
		return;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (event.getPlayer().hasPermission("bowteleport.give")) {
			event.getPlayer().getInventory().remove(giveBow());
			event.getPlayer().getInventory().remove(giveArrow());
		}
		return;
	}
	
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
		if (event.getPlayer().hasPermission("bowteleport.give")) {
			event.getPlayer().getInventory().setItem(2, giveBow());
			event.getPlayer().getInventory().setItem(9, giveArrow());
		}
		return;
	}
	
	private ItemStack giveBow() {
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.spigot().setUnbreakable(true);
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