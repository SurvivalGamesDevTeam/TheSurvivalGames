package com.communitysurvivalgames.thesurvivalgames.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;

public class FakeBlockUtil {
	static Random rnd = new Random();

	public static void createFakeSphere(final Player player) {
		Bukkit.getScheduler().runTaskAsynchronously(SGApi.getPlugin(), new Runnable() {

			@Override
			public void run() {
				int RADIUS = 20;
				Location loc = player.getLocation();
				int cx = loc.getBlockX();
				int cy = loc.getBlockY();
				int cz = loc.getBlockZ();
				World w = loc.getWorld();
				int rSquared = RADIUS * RADIUS;

				for (int x = cx - RADIUS; x <= cx + RADIUS; x++) {
					for (int z = cz - RADIUS; z <= cz + RADIUS; z++) {
						if (x % 5 == 0 && z % 5 == 0) {
							player.sendBlockChange(new Location(w, x, cy - 1, z), Material.GLOWSTONE, (byte) 0);
						} else {
							player.sendBlockChange(new Location(w, x, cy - 1, z), Material.QUARTZ_BLOCK, (byte) 0);
						}
						for (int y = cy - RADIUS; y < cy + RADIUS; y++) {
							if ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y) <= rSquared && (cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y) > 324) {
								try {
									Thread.sleep(5L); //I'm bad and I know it.. hehe
								} catch (InterruptedException e) {}
								player.sendBlockChange(new Location(w, x, y, z), Material.STAINED_CLAY, (byte) rnd.nextInt(16));
							}
						}

						//ProtocolManager manager = SGProtocolManager.getSGProtocolManager().getProtocolLib();
						//WrapperPlayServerSpawnEntityLiving wrapper = new WrapperPlayServerSpawnEntityLiving();
						//
						//wrapper.setEntityID(EntityType.HORSE.getTypeId());
						//wrapper.setX(cx);
						//wrapper.setY(cy);
						//wrapper.setZ(cz);
						//
						//WrappedDataWatcher watcher = new WrappedDataWatcher();
						//watcher.setObject(0, (byte) 0); // Flags. Must be a byte.
						//watcher.setObject(1, (short) 300);
						//watcher.setObject(6, (float) 20);
						//watcher.setObject(10, "This is a test");
						//watcher.setObject(11, (byte) 1);
						//watcher.setObject(12, -9999);
						// 
						//wrapper.setMetadata(watcher);

						//try {
						//	manager.sendServerPacket(player, wrapper.getHandle());
						//} catch (InvocationTargetException e) {
						//	// TODO Auto-generated catch block
						//	e.printStackTrace();
						//}
					}
				}
				Location a;
				for (int i = 0; i < 5; i++) {
					for (int c = -5; c < 5; c++) {
						a = new Location(w, cx + 11, cy, cz);
						player.sendBlockChange(a.add(0, i, c), Material.NETHER_BRICK, (byte) 0);
						a = new Location(w, cx + 11, cy, cz);
						player.sendBlockChange(a.add(-1, i, c), Material.WALL_SIGN, (byte) 4);
					}
				}
			}
		});

	}
}
