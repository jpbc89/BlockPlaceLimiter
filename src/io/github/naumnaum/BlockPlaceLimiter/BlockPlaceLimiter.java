package io.github.naumnaum.BlockPlaceLimiter;

import org.bukkit.plugin.java.JavaPlugin;

public class BlockPlaceLimiter extends JavaPlugin {

	Events events;
	ConfigHandler config = new ConfigHandler(this);
	DataHandler data = new DataHandler(this);
	LBData state,max;
	
	public void onEnable(){
		config.loadConfig();
		max = data.loadFlatData("max.obj");
		state = data.loadFlatData("state.obj");
		events = new Events(max, state, ConfigHandler.defaultMax);
		this.getServer().getPluginManager().registerEvents(events, this);
	}
	
	public void onDisable(){
		data.saveFlatData(max, "max.obj");
		data.saveFlatData(state, "state.obj");
	}
}
