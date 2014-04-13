package com.communitysurvivalgames.thesurvivalgames.io;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class UnZip {
	String in;
	String out;

	public UnZip(String in, String out) {
		this.in = in;
		this.out = out;
	}

	public void begin() {
	    try {
	         ZipFile zipFile = new ZipFile(in);
	         zipFile.extractAll(out);
	    } catch (ZipException e) {
	        e.printStackTrace();
	    }
	}
}
