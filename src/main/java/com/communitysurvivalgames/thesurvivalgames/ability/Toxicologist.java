package com.communitysurvivalgames.thesurvivalgames.ability;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.util.FireworkUtil;

public class Toxicologist extends SGAbility implements Listener {

	public Toxicologist() {
		super(4);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(final PlayerInteractEvent event) {
		final Player player = event.getPlayer();
		if (this.hasAbility(player)) {
			final Location loc = player.getLocation();
			final Random rnd = new Random();
			if (player.getItemInHand().getType() == Material.CLAY_BALL && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Toxin Bomb")) {
				this.removeOneFromHand(player);
				FireworkUtil.getCircleUtil().playFireworkCircle(event.getPlayer(), FireworkEffect.builder().withColor(Color.SILVER).withFade(Color.GREEN).flicker(true).trail(false).build(), 10, 10);
				Bukkit.getScheduler().runTaskLater(SGApi.getPlugin(), new Runnable() {

					@Override
					public void run() {
						for (int i = 0; i < 40; i++) {
							Potion potion = new Potion(PotionType.POISON, 2);
							potion.setSplash(true);
							ItemStack itemStack = new ItemStack(Material.POTION);
							potion.apply(itemStack);
							ThrownPotion thrownPotion = null;
							thrownPotion = (ThrownPotion) loc.getWorld().spawnEntity(loc.add(rnd.nextInt(20) - 10, rnd.nextInt(20) - 10, rnd.nextInt(20) - 10), EntityType.SPLASH_POTION);
							thrownPotion.setItem(itemStack);
						}
					}
				}, 100L);
			}
		}
	}
}
