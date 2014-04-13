package com.communitysurvivalgames.thesurvivalgames.ability;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.communitysurvivalgames.thesurvivalgames.util.FireworkEffectPlayer;

public class Pacman extends SGAbility implements Listener {
	public Pacman() {
		super(5);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			if (player.getItemInHand().getType() == Material.GLOWSTONE_DUST && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Power Orb")) {
				this.removeOneFromHand(player);
				player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 2));
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
				FireworkEffect fEffect = FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).withFade(Color.YELLOW).with(Type.STAR).trail(false).build();
				try {
					FireworkEffectPlayer.getFireworkEffectPlayer().playFirework(event.getPlayer().getWorld(), event.getPlayer().getLocation(), fEffect);
				} catch (Exception e) {
					//If the firework dosen't work... to bad 
				}
			}
		}
	}
}
