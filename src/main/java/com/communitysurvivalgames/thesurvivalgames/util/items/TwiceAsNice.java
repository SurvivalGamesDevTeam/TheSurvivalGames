/**
 * Name: TwiceAsNice.java Edited: 19 January 2014
 *
 * @version 1.0.0
 */
package com.communitysurvivalgames.thesurvivalgames.util.items;

import java.util.List;
import java.util.Random;
import lombok.experimental.Builder;
import com.communitysurvivalgames.thesurvivalgames.callables.RandomFirework;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BlockIterator;

@Builder
public class TwiceAsNice implements Listener {

    private ItemStack item;
    private ItemMeta meta;
    private Material material;
    private boolean leftClick;
    private boolean rightClick;
    private List<String> lore;
    private String displayName;

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void playerJoin(final PlayerJoinEvent e) {

        ItemStack it = this.item.clone();
        e.getPlayer().getInventory().setItem(0, it);
        e.getPlayer().closeInventory();
        SGApi.getPlugin().getServer().getScheduler().runTask(SGApi.getPlugin(), new Runnable() {
			@Override
            public void run() {

                e.getPlayer().updateInventory();
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            ItemStack it = e.getItem();
            /*
             * if(!it.getType().equals(this.material)){ return; } try {
             * if(it.getItemMeta().getDisplayName() == null ||
             * !(it.getItemMeta()
             * .getDisplayName().equalsIgnoreCase(ChatColor.stripColor
             * (this.displayName)))){ return; } } catch (NullPointerException
             * e1) { return; }
             */
            Player p = e.getPlayer();

            double recoilBack = 0.3d;
            Projectile snowball = p.launchProjectile(Snowball.class);
            p.setVelocity(p.getLocation().getDirection().multiply(-recoilBack));
            snowball.setVelocity(p.getLocation().getDirection().multiply(3.0d));
            Firework fw = (Firework) snowball.getWorld().spawnEntity(snowball.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = (FireworkMeta) SGApi.getScheduler().runNow(new RandomFirework());
            fw.setFireworkMeta(fwm);
            fw.detonate();

        }

    }

    @EventHandler(priority = EventPriority.LOW)
    public void onLeftClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {

            ItemStack it = e.getItem();
            if (!it.getType().equals(this.material)) {
                return;
            }
            try {
                if (it.getItemMeta().getDisplayName() == null || !(it.getItemMeta().getDisplayName().equalsIgnoreCase(this.displayName))) {
                    return;
                }
            } catch (NullPointerException e1) {
                return;
            }
            Player p = e.getPlayer();
            p.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 3.0f, 1.0f);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onHit(ProjectileHitEvent event) {

        if ((event.getEntity().getShooter() instanceof Player)) {
            Snowball snowball = (Snowball) event.getEntity();
            Player player = (Player) snowball.getShooter();
            World world = snowball.getWorld();
            BlockIterator bi = new BlockIterator(world, snowball.getLocation().toVector(), snowball.getVelocity().normalize(), 0, 4);
            Block hit = null;
            while (bi.hasNext()) {
                hit = bi.next();
                if (!hit.getType().equals(Material.AIR)) {
                    break;
                }
            }
            if (hit != null) {
                System.out.println("The Real block is " + hit.getType() + " location is at  " + hit.getLocation().toVector().toString());

                BlockState blockState = hit.getState();
                player = (Player) event.getEntity().getShooter();
                System.out.println("Hit " + blockState.getType());

                if (blockState.getType().equals(Material.STONE)) {
                    player.sendMessage(ChatColor.GOLD + "Turning Stone to Gold");
                    blockState.setType(Material.GOLD_BLOCK);
                    blockState.update(true);

                }

            }

        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void entityDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getItemInHand().getType().equals(Material.DIAMOND_AXE)) {
                player.setHealth(player.getMaxHealth());
                player.setFoodLevel(20);
                System.out.println("health restored");
            }
        }
        if (event.getCause() != EntityDamageEvent.DamageCause.PROJECTILE && event.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION
                && event.getCause() != EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
            return;
        System.out.println("DaMAGE HERE");
        event.setDamage(10.0d);
        Location loc = event.getDamager().getLocation();
        Random random = new Random(100);
        event.getDamager().getWorld().playSound(loc, Sound.EXPLODE, 5.0f, 1.0f);
        event.getDamager().getWorld().playEffect(loc, Effect.STEP_SOUND, Material.REDSTONE_WIRE);
        event.getDamager().getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 2.0f, false, true);

        if (event.getEntity() instanceof Monster) {
            Creature creature = (Creature) event.getEntity();
            if (!creature.isDead()) {
                List<Entity> entities = creature.getNearbyEntities(2, 2, 2);
                for (Entity entity : entities) {
                    if (entity.getType().equals(EntityType.PLAYER)) {
                        Player player = (Player) entity;
                        player.setHealth(player.getMaxHealth());
                    } else {
                        Creature cre = (Creature) entity;
                        cre.setHealth(cre.getHealth() - event.getDamage());
                    }

                }
            }
            creature.setHealth(creature.getHealth() - event.getDamage());
            event.setCancelled(true);
        }

        return;

        // event.getDamager().getWorld().playSound(event.getDamager().getLocation(),Sound.BLAZE_HIT,5.0f,1.0f);
        // event.setDamage(8.0d);

    }

    public void makeMeta() {
        meta = Bukkit.getItemFactory().getItemMeta(material);
        item = new ItemStack(material, 1);
    }

    public void setAllLore() {

        this.meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
        this.meta.setDisplayName(displayName);
        this.meta.setLore(this.lore);
    }

    public void applyMeta() {
        item.setItemMeta(meta);
    }

}
