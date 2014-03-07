package io.github.naumnaum.BlockPlaceLimiter;

import org.bukkit.plugin.java.JavaPlugin;

public class BlockPlaceLimiter extends JavaPlugin {

	Events events;
	ConfigHandler config = new ConfigHandler(this);
	DataHandler data = new DataHandler(this);
	BPLData bplData;
	CommandHandler command = new CommandHandler(this);
	
	public void onEnable(){
		config.loadConfig();
		bplData = data.loadFlatData("data.obj");
		events=new Events(this);
		events.load(bplData, ConfigHandler.defaultMax);
		this.getServer().getPluginManager().registerEvents(events, this);
		this.getCommand("bpl").setExecutor(command);
	}
	
	public void onDisable(){
		data.saveFlatData(events.getData(), "data.obj");
		config.saveConfig();
	}
}
