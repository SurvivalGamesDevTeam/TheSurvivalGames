package com.communitysurvivalgames.thesurvivalgames.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.communitysurvivalgames.thesurvivalgames.configs.ConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.configs.WorldConfigTemplate;
import com.communitysurvivalgames.thesurvivalgames.managers.SGApi;
import com.communitysurvivalgames.thesurvivalgames.multiworld.SGWorld;

public class DownloadMap {
	Player sender;
	String jobName = null;
	URL dl = null;
	File fl = null;
	String x = null;
	OutputStream os = null;
	InputStream is = null;
	ProgressListener progressListener;

	public DownloadMap(Player p, String name) {
		sender = p;
		jobName = name;
	}

	public void begin() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(SGApi.getPlugin(), new Runnable() {
			public void run() {
				try {
					fl = new File(SGApi.getPlugin().getDataFolder(), jobName + ".zip");
					dl = new URL("https://s3.amazonaws.com/CommunitySG/SG_Worlds/" + jobName + ".zip");
					os = new FileOutputStream(fl);
					sender.sendMessage(ChatColor.YELLOW + "https://s3.amazonaws.com/");
					is = dl.openStream();
					sender.sendMessage(ChatColor.YELLOW + "Connection opened");
					String bytes = dl.openConnection().getHeaderField("Content-Length");
					progressListener = new ProgressListener(sender, bytes);

					DownloadCountingOutputStream dcount = new DownloadCountingOutputStream(os);
					dcount.setListener(progressListener);

					IOUtils.copy(is, dcount);

					sender.sendMessage(ChatColor.YELLOW + "File Downloaded!");
				} catch (Exception e) {
					Bukkit.getLogger().severe(e.getMessage());
					sender.sendMessage(ChatColor.RED + e.getMessage());
					return;
				} finally {
					if (os != null) {
						try {
							os.close();
						} catch (IOException e) {
							Bukkit.getLogger().severe("Failed to close output stream for downloaded map");
						}
					}
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							Bukkit.getLogger().severe("Failed to close input stream for downloaded map");
						}
					}
				}

				sender.sendMessage(ChatColor.YELLOW + "Attemption to unzip file " + jobName + ".zip");
				UnZip unZip = new UnZip((SGApi.getPlugin().getDataFolder() + File.separator + jobName + ".zip"), Bukkit.getWorldContainer().getAbsolutePath());
				unZip.begin();
				sender.sendMessage(ChatColor.YELLOW + "File unziped!");

				sender.sendMessage(ChatColor.YELLOW + "Reading map config file!");
				File file = new File(Bukkit.getWorldContainer(), jobName + ".yml");
				ConfigTemplate<SGWorld> configTemplate = new WorldConfigTemplate(file);
				SGWorld world = configTemplate.deserialize();
				sender.sendMessage(ChatColor.YELLOW + "Loaded map: " + world.getDisplayName());
				Bukkit.getLogger().info("Loaded map! " + world.toString());
				sender.sendMessage(ChatColor.YELLOW + "World files loaded!");
				SGApi.getMultiWorldManager().getWorlds().add(world);

				sender.sendMessage(ChatColor.YELLOW + "Your map was installed!");

				file.delete();
			}
		});

	}
}
