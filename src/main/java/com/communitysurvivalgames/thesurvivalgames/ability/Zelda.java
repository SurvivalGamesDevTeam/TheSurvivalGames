package com.communitysurvivalgames.thesurvivalgames.ability;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.communitysurvivalgames.thesurvivalgames.event.GameStartEvent;
import com.communitysurvivalgames.thesurvivalgames.event.PlayerKilledEvent;
import com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;

public class Zelda extends SGAbility implements Listener {
	public Zelda() {
		super(1);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onGameStart(GameStartEvent event) {
		for (String p : event.getArena().getPlayers()) {
			if (hasAbility(p)) {
				Player player = Bukkit.getPlayer(p);
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 99999, 5, false));
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (player.getItemInHand().getType() == Material.SPECKLED_MELON && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Zelda Heart")) {
				this.removeOneFromHand(player);
				if (player.getHealth() >= 14) {
					player.setHealth(20);
				}

				if (player.getHealth() < 14)
					player.setHealth(player.getHealth() + 6);
				FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).withFade(Color.GREEN).with(Type.BALL).trail(true).build();
				try {
					FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(event.getPlayer().getWorld(), event.getPlayer().getLocation(), fEffect);
				} catch (Exception e) {
					//If the firework dosen't work... to bad 
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onKill(PlayerKilledEvent event) {
		if(!(event.getKiller() instanceof Player))
			return;
		Player p = (Player) event.getKiller();
		if (hasAbility(p)) {
			ItemStack zeldaHeart = new ItemStack(Material.SPECKLED_MELON);
			ItemMeta meta = zeldaHeart.getItemMeta();
			meta.setDisplayName("Zelda Heart");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.translateAlternateColorCodes('&', "&3Heal when right clicked - hearts from the dead"));
			lore.add(ChatColor.translateAlternateColorCodes('&', "&1Zelda Kit - LvL1"));
			meta.setLore(lore);
			zeldaHeart.setItemMeta(meta);
			event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), zeldaHeart);
		}
	}
}
