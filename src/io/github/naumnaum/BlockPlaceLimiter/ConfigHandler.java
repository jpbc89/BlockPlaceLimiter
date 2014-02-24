package io.github.naumnaum.BlockPlaceLimiter;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigHandler {

	JavaPlugin plugin;
	FileConfiguration config;

	static HashMap<BPLBlock, Integer> defaultMax = new HashMap<BPLBlock, Integer>();
	static String overPlaced = "";

	public ConfigHandler(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("unchecked")
	public boolean loadConfig() {
		try {
			plugin.saveDefaultConfig();
			config = plugin.getConfig();
			ArrayList<String> blocks = (ArrayList<String>) config.get("blocks");
			if (!blocks.isEmpty()) {
				for (String s : blocks) {
					BPLBlock block = BPLBlock.parseConfig(s);
					int count = Integer.parseInt(s.trim().split("=")[1].trim());
					defaultMax.put(block, count);
				}
			}
			overPlaced = config.getString("overplaced");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
