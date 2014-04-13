package com.communitysurvivalgames.thesurvivalgames.configs;

import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldConfigTemplate extends ConfigTemplate<SGWorld> {
	private SGWorld world;

	private String cachedWorldName;
    private SGWorld cachedWorldCreator;

	public WorldConfigTemplate(File file) {
		super(file);
	}

	public WorldConfigTemplate(SGWorld world) {
		super("maps/" + world.getWorld().getName() + ".yml");
		this.world = world;
	}
	
	@Override
	public String[] pattern() {
		return new String[] { 
			"World-name", 
			"Display-name", 
			"Spawns", 
			"Chests" 
		};
	}

	@Override
	public Object toFile(int keyPair) {
		switch (keyPair) {
		case 0:
			return this.world.getName();
		case 1:
			return this.world.getDisplayName();
		case 2:
			List<String> stringList = new ArrayList<>();
			for (Location l : this.world.locs) {
				stringList.add(SGApi.getArenaManager().serializeLoc(l));
			}
			return stringList;
		case 3:
			List<String> list = new ArrayList<>();
			for (BlockState b : this.world.t2) {
				list.add(SGApi.getArenaManager().serializeBlock(b.getBlock()));
			}
			return list;
		}
		return null;
	}

	@Override
	public SGWorld fromFile(int index, Object o) {
		switch (index) {
		case 0:
			this.cachedWorldName = String.valueOf(o);
			break;
		case 1:
            String cachedDisplayName = String.valueOf(o);

			this.cachedWorldCreator = new SGWorld(this.cachedWorldName, cachedDisplayName);
			this.cachedWorldCreator.create();
			break;
		case 2:
			List<Location> locs = new ArrayList<>();
			for (String s : (List<String>) o) {
				locs.add(SGApi.getArenaManager().deserializeLoc(s));
			}
			this.cachedWorldCreator.locs = locs;
			break;
		case 3:
			List<BlockState> list = new ArrayList<>();
			for (String s : (List<String>) o) {
				list.add(SGApi.getArenaManager().deserializeBlock(s).getState());
			}
			this.cachedWorldCreator.t2 = list;
			break;
		}
		return this.cachedWorldCreator;
	}
}
