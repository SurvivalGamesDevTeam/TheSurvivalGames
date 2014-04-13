package com.communitysurvivalgames.thesurvivalgames.util;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DeathMessages {
	public static String getDeathMessage(Player p, DamageCause dc) {
		String message = ChatColor.GOLD + p.getDisplayName() + ChatColor.AQUA + " ";
		Random rnd = new Random();

		if (dc == DamageCause.FALL) {
			int i = rnd.nextInt(7);
			if (i == 0) {
				return message + "has apparently never heard of stairs";
			}
			if (i == 1) {
				return message + "believed they could fly!";
			}
			if (i == 2) {
				return message + "apparently can't fly ";
			}
			if (i == 3) {
				return message + "forgot about gravity";
			}
			if (i == 4) {
				return message + "learned to avoid cliffs";
			}
			if (i == 5) {
				return message + "forgot which way gravity went";
			}
			if (i == 6) {
				return message + "shouldn't have read that book on physics";
			}
		}

		if (dc == DamageCause.ENTITY_EXPLOSION) {
			int i = rnd.nextInt(5);
			if (i == 0) {
				return message + "tried to tame a creeper";
			}
			if (i == 1) {
				return message + "didn't hear a hiss";
			}
			if (i == 2) {
				return message + "hugged a creeper... and the creeper hugged back!";
			}
			if (i == 3) {
				return message + "thought that the creeper was sad...IT WAS NOT! ";
			}
			if (i == 4) {
				return message + "just wanted a hug from a creeper";
			}

		}

		if (dc == DamageCause.LIGHTNING) {
			int i = rnd.nextInt(6);
			if (i == 0) {
				return message + "was smitten";
			}
			if (i == 1) {
				return message + "was executed";
			}
			if (i == 2) {
				return message + "has a lovely interview with the almighty Thor";
			}
			if (i == 3) {
				return message + "was lightninged into a crisp";
			}
			if (i == 4) {
				return message + "thought it would be fun to stick a flagpost in the air";
			}
			if (i == 5) {
				return message + "was punished by Zeus";
			}
		}

		if (dc == DamageCause.DROWNING) {
			int i = rnd.nextInt(4);
			if (i == 0) {
				return message + "forgot how to swim";
			}
			if (i == 1) {
				return message + "doesn't have gills";
			}
			if (i == 2) {
				return message + "remembered that Steves generally need air, not water";
			}
			if (i == 3) {
				return message + "went deep sea diving, and left his torches at home";
			}
		}

		if (dc == DamageCause.THORNS) {
			return message + "forgot that spikes hurt";
		}

		if (dc == DamageCause.VOID) {
			return message + "attempted to leave this world";
		}

		if (dc == DamageCause.STARVATION) {
			int i = rnd.nextInt(3);
			if (i == 0) {
				return message + "should have made one more cookie";
			}
			if (i == 1) {
				return message + "liked the zombie flesh... his food bar didn't";
			}
			if (i == 2) {
				return message + "found out that the cake is a lie";
			}
		}

		if (dc == DamageCause.SUFFOCATION) {

		}

		if (dc == DamageCause.CONTACT) {
			int i = rnd.nextInt(1);
			
		}
		if (dc == DamageCause.MAGIC) {
			int i = rnd.nextInt(1);
			if (i == 0) {
				return message + "drank from the wrong bottle";
			}
		}
		if (dc == DamageCause.SUICIDE) {
			int i = rnd.nextInt(3);
			if (i == 0) {
				return message + "realised that jeb_ sheeps don't always save lives";
			}
			if (i == 1) {
				return message + "bit the dust";
			}
			if (i == 2) {
				return message + "rage quit";
			}
		}
		if (dc == DamageCause.WITHER) {

		}
		if (dc == DamageCause.BLOCK_EXPLOSION) {
			int i = rnd.nextInt(1);
			if (i == 0) {
				return message + "found out how TNT works";
			}
		}
		if (dc == DamageCause.LAVA) {
			int i = rnd.nextInt(1);
			if (i == 0) {
				return message + "kicked the bucket. Of lava.";
			}

		}
		if (dc == DamageCause.FIRE || dc == DamageCause.FIRE_TICK) {
			int i = rnd.nextInt(1);
			if (i == 0) {
				return message + "forgot his water bucket at home";
			}
		}

		return message + "hit the cactus too hard, then blew up whilst attempting to swim in lava";
	}
}
