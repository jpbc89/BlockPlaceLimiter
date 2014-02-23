package io.github.naumnaum.BlockPlaceLimiter;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Events implements Listener{

	LBData max;
	LBData state;
	LBArray defaultMax;
	
	public Events(LBData max, LBData state, LBArray defaultMax){
		this.max = max;
		this.state = state;
		this.defaultMax = defaultMax;
		if (this.max==null)
			this.max=new LBData();
		if (this.state==null)
			this.state=new LBData();
		if (this.defaultMax==null)
			this.defaultMax=new LBArray();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerPlaceBlock(BlockPlaceEvent event){
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		player.sendMessage("Max");
		player.sendMessage(max.toString());
		player.sendMessage("State");
		player.sendMessage(state.toString());
		
		//if block isn't listed on config return
		if(defaultMax.get(block)==-1)
			return;
		
		//try to register player state and max
		max.put(player);
		state.put(player);
		
		//try to register blocks
		max.put(player, block);
		state.put(player, block);
		
		//set max if player hasn't
		int dBMax = defaultMax.get(block);
		int pBMax = max.getCount(player, block);
		if (pBMax == -1){
			max.put(player, block, dBMax);
			pBMax = dBMax;
		}
		
		//init pbstate if need
		int pBState = state.getCount(player, block);
		if (pBState == -1)
			pBState = 0;
		
		//try to increase state
		if (pBState<pBMax){
			state.put(player, block, ++pBState);
		}else{
			player.sendMessage(ConfigHandler.overPlaced);
			event.setCancelled(true);
			player.updateInventory();
		}
		player.sendMessage("Placed blocks: "+pBState+" of "+pBMax);
			
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerBreakBlock(BlockBreakEvent event){
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		//if block isn't listed on config return
		if(defaultMax.get(block)==-1)
			return;
		
		//try to register player state and max
		max.put(player);
		state.put(player);
		
		//try to register blocks
		max.put(player, block);
		state.put(player, block);
		
		//set max if player hasn't
		int dBMax = defaultMax.get(block);
		int pBMax = max.getCount(player, block);
		if (pBMax == -1){
			max.put(player, block, dBMax);
			pBMax = dBMax;
		}
		
		//init pbstate if need
		int pBState = state.getCount(player, block);
		if (pBState == -1)
			pBState = 0;
		
		//try to increase state
		if (pBState<=0){
			state.put(player, block, 0);
		}else{
			state.put(player, block, --pBState);
		}
		player.sendMessage("Placed blocks: "+pBState+" of "+pBMax);
			
	}
}
