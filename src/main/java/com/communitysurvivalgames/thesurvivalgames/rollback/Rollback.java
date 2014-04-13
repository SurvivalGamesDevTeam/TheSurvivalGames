package com.communitysurvivalgames.thesurvivalgames.rollback;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import com.communitysurvivalgames.thesurvivalgames.objects.SGArena;

public class Rollback implements Runnable {

	List<ChangedBlock> data;
	final SGArena arena;

	public Rollback(SGArena a) {
		this.data = a.changedBlocks;
		arena = a;
	}

	public void run() {

		List<ChangedBlock> data = this.data;

		for (int i = 0; i < data.size(); i++) {
			Bukkit.getLogger().info("Resetting block: " + data.get(i).getPrevid().toString());
			Location l = new Location(Bukkit.getWorld(data.get(i).getWorld()), data.get(i).getX(), data.get(i).getY(), data.get(i).getZ());
			Block b = l.getBlock();
			b.setType(data.get(i).getPrevid());
			b.setData(data.get(i).getPrevdata());
			b.getState().update();
		}
		arena.restart();
		data.clear();
	}

}