package io.github.naumnaum.BlockPlaceLimiter;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHandler {

	BlockPlaceLimiter plugin;
	FileConfiguration config;

	//use default block limits overrides player limits
	static boolean useDefault=true;
	//use player block limits
	static boolean usePlayerLimits=false;
	//message when reach limit
	static String overPlaced = "";
	//players can see other players limits
	static boolean canSeeLimits = false;
	//player can see other players placed blocks
	static boolean canSeePlaced = false;
	
	static HashMap<BPLBlock, Integer> defaultMax = new HashMap<BPLBlock, Integer>();
	

	public ConfigHandler(BlockPlaceLimiter plugin) {
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
					int count = Integer.parseInt(s.split(":")[1].trim().split("=")[1].trim());
					defaultMax.put(block, count);
				}
			}
			plugin.getLogger().info(blocks.size()+" limits loaded.");
			overPlaced = config.getString("overplaced");
			useDefault = config.getBoolean("usedefault");
			usePlayerLimits = config.getBoolean("useplayerlimits");
			canSeeLimits = config.getBoolean("canseelimits");
			canSeePlaced = config.getBoolean("canseeplaced");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		plugin.saveConfig();
		return true;
	}
	
	public boolean saveConfig() {
		try{
			config.set("usedefault", useDefault);
			config.set("useplayerlimits", usePlayerLimits);
			config.set("canseelimits", canSeeLimits);
			config.set("canseeplaced", canSeePlaced);
			plugin.saveConfig();
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
