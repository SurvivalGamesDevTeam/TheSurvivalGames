package com.communitysurvivalgames.thesurvivalgames.rollback;

import org.bukkit.Material;

public class ChangedBlock {
	private String world;
	private Material premat;
	private Material newmat;
	private byte prevdata, newdata;
	private int x, y, z;

	/**
	 * 
	 * @param premat
	 * @param newmat
	 * @param x
	 * @param y
	 * @param z
	 * 
	 * Provides a object for holding the data for block changes
	 */
	public ChangedBlock(String world, Material premat, byte prevdata, Material newmat, byte newdata, int x, int y, int z) {
		this.world = world;
		this.premat = premat;
		this.prevdata = prevdata;
		this.newmat = newmat;
		this.newdata = newdata;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String getWorld() {
		return world;
	}

	public byte getPrevdata() {
		return prevdata;
	}

	public byte getNewdata() {
		return newdata;
	}

	public Material getPrevid() {
		return premat;
	}

	public Material getNewid() {
		return newmat;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

}
