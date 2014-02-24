package io.github.naumnaum.BlockPlaceLimiter;

import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Events implements Listener{

	private BPLData data;
	private HashMap<BPLBlock, Integer> defaultMax;
	private JavaPlugin plugin;
	
	public void load(BPLData data, HashMap<BPLBlock, Integer> defaultMax){
		this.data=data;
		this.defaultMax = defaultMax;
		if (this.defaultMax==null)
			this.defaultMax=new HashMap<BPLBlock, Integer>();
		if (this.data==null)
			plugin.getLogger().info("data.obj is empty.");
		if (this.data==null)
			this.data=new BPLData(this.defaultMax);	
	}
	
	public Events(JavaPlugin plugin){
		this.plugin=plugin;
	}
	
	public BPLData getData(){
		return this.data;
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerPlaceBlock(BlockPlaceEvent event){
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if(!data.restricted(block))
			return;
		
		if (data.increase(player, block)){
			player.sendMessage("Limited block placed");
		}else{
			player.sendMessage(ConfigHandler.overPlaced);
			event.setCancelled(true);
			player.updateInventory();
		}
		player.sendMessage("Placed blocks: "+data.getState(player, block)+" of "+data.getMax(player, block));
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerBreakBlock(BlockBreakEvent event){
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if(!data.restricted(block))
			return;
		
		if (data.decrease(player, block)){
			player.sendMessage("Limited block removed");
		}
		
		player.sendMessage("Placed blocks: "+data.getState(player, block)+" of "+data.getMax(player, block));
	}
}
