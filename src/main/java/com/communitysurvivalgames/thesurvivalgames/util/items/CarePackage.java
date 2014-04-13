package com.communitysurvivalgames.thesurvivalgames.util.items;

import com.communitysurvivalgames.thesurvivalgames.TheSurvivalGames;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.*;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class CarePackage implements Listener {
    private final TheSurvivalGames plugin;

    public CarePackage(TheSurvivalGames plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        final Player p = event.getPlayer();
        final Location careLocation = p.getLocation();
        if (p.getItemInHand().getData().getItemType() == Material.NETHER_STAR && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Care Package")) {
            if (SGApi.getArenaManager().isInGame(p)) {
                p.getInventory().remove(Material.NETHER_STAR);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        plugin.getServer().broadcastMessage(ChatColor.WHITE + "CARE PACKAGE INCOMING: (" + ChatColor.YELLOW + careLocation.getX() + ChatColor.WHITE + ","
                                + ChatColor.YELLOW + careLocation.getY() + ChatColor.WHITE + "," + ChatColor.YELLOW + careLocation.getZ() + ChatColor.WHITE + ")");
                        careLocation.getBlock().getRelative(BlockFace.DOWN).setType(Material.BEACON);
                        Location locTmp;
                        for (int x = -1; x < 1; x++) {
                            for (int z = -1; z < 1; z++) {
                                locTmp = new Location(p.getWorld(), careLocation.getX() + x, careLocation.getY() - 1, careLocation.getZ() - 1);
                                locTmp.getBlock().getRelative(BlockFace.DOWN).setType(Material.IRON_BLOCK);
                            }
                        }
                        try {
                            Thread.sleep(500);// -_- How else do you want me to
                                              // make the runnable wait?
                        } catch (InterruptedException ignored) {
                        }
                        Location fLoc;
                        for (int i = 0; i < 20; i++) {
                            fLoc = new Location(careLocation.getWorld(), careLocation.getX(), (careLocation.getY() + (2 * i)), careLocation.getZ());
                            Firework fw = (Firework) p.getWorld().spawnEntity(fLoc, EntityType.FIREWORK);
                            FireworkMeta fwm = fw.getFireworkMeta();
                            Type type = Type.BALL;
                            Color c1 = Color.YELLOW;
                            Color c2 = Color.FUCHSIA;
                            FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(c1).withFade(c2).with(type).trail(false).build();
                            fwm.addEffect(effect);
                            int rp = 0;
                            fwm.setPower(rp);
                            fw.setFireworkMeta(fwm);
                            try {
                                Thread.sleep(350);// -_- How else do you want me
                                                  // to get the runnable to
                                                  // wait? Nested runnables
                            } catch (InterruptedException ignored) {
                            }
                        }
                        careLocation.getBlock().getRelative(BlockFace.DOWN).setType(Material.DIRT);
                        careLocation.getBlock().setType(Material.CHEST);
                        Chest c = (Chest) careLocation.getBlock().getState();
                        c.getInventory().addItem(new ItemStack(Material.DIAMOND));
                        // TODO: Get the list of Tier 2 items and add them
                        // here
                        for (int i = 0; i < 4; i++) {
                            careLocation.getWorld().strikeLightning(careLocation);
                        }
                    }
                });
            }
        }
    }
}
