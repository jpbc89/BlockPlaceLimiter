package io.github.naumnaum.BlockPlaceLimiter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@SuppressWarnings("serial")
public class BPLData implements Serializable{

	
	private HashMap<String,HashMap<BPLBlock, Integer>> playerBlocks;
	private HashMap<String,HashMap<BPLBlock, Integer>> playerMaxBlocks;
	private HashMap<BPLBlock, Integer> defaultMaxBlocks;

	public String[] listDefaultBlocks(){
		Set<BPLBlock> blocks = defaultMaxBlocks.keySet();
		ArrayList<String> s=new ArrayList<String>();
		for (BPLBlock b: blocks){
			s.add(b.toString() + ChatColor.LIGHT_PURPLE+" Limit: "+ChatColor.RESET+defaultMaxBlocks.get(b));
		}
		return s.toArray(new String[s.size()]);
	}
	
	public String[] listPlayerMaxBlocks(String name){
		init(name);
		Set<BPLBlock> blocks = playerMaxBlocks.get(name).keySet();
		ArrayList<String> s=new ArrayList<String>();
		for (BPLBlock b: blocks){
			s.add(b.toString() + ChatColor.LIGHT_PURPLE+" Limit: "+ChatColor.RESET+playerMaxBlocks.get(name).get(b));
		}
		return s.toArray(new String[s.size()]);
	}
	
	public String[] listPlayerBlocks(String name){
		init(name);
		Set<BPLBlock> blocks = playerBlocks.get(name).keySet();
		ArrayList<String> s=new ArrayList<String>();
		for (BPLBlock b: blocks){
			s.add(b.toString() + ChatColor.LIGHT_PURPLE+" Placed: "+ChatColor.RESET+playerBlocks.get(name).get(b));
		}
		return s.toArray(new String[s.size()]);
	}
	
	public HashMap<BPLBlock, Integer> getDefaultMaxBlocks(){
		return this.defaultMaxBlocks;
	}
	
	public boolean hasMaxBlock(Player player, Block block){
		return hasMaxBlock(player.getName(), new BPLBlock(block));
	}
	
	private boolean hasMaxBlock(String name, BPLBlock bplBlock){
		this.init(name);
		if (playerMaxBlocks.get(name).containsKey(bplBlock))
			return true;
		return false;
	}
	
	public BPLData(HashMap<String,HashMap<BPLBlock, Integer>> playerBlocks,
			HashMap<String,HashMap<BPLBlock, Integer>> playerMaxBlocks,
			HashMap<BPLBlock, Integer> defaultMaxBlocks){
		this.playerBlocks=playerBlocks;
		this.playerMaxBlocks=playerMaxBlocks;
		this.defaultMaxBlocks=defaultMaxBlocks;
		if (this.playerBlocks==null)
			this.playerBlocks=new HashMap<String, HashMap<BPLBlock,Integer>>();
		if (this.playerMaxBlocks==null)
			this.playerMaxBlocks=new HashMap<String, HashMap<BPLBlock,Integer>>();
		if (this.defaultMaxBlocks==null)
			this.defaultMaxBlocks=new HashMap<BPLBlock, Integer>();
	}
	
	public BPLData(HashMap<BPLBlock, Integer> defaultMaxBlocks){
		this.playerBlocks=new HashMap<String, HashMap<BPLBlock,Integer>>();
		this.playerMaxBlocks=new HashMap<String, HashMap<BPLBlock,Integer>>();
		this.defaultMaxBlocks=defaultMaxBlocks;
	}
	
	public boolean restricted(Block block){
		return restricted(new BPLBlock(block));
	}
	
	private boolean restricted(BPLBlock bplBlock){
		return defaultMaxBlocks.containsKey(bplBlock);
	}
	
	public boolean increase(Player player, Block block){
		return increase(player.getName(), new BPLBlock(block));
	}

	private boolean increase(String name, BPLBlock bplBlock) {
		init(name);
		init(name,bplBlock);
		if (addBlock(name,bplBlock))
			return true;
		return false;
	}
	
	public boolean decrease(Player player, Block block){
		return decrease(player.getName(), new BPLBlock(block));
	}
	
	private boolean decrease(String name, BPLBlock bplBlock) {
		init(name);
		init(name,bplBlock);
		if (removeBlock(name,bplBlock))
			return true;
		return false;
	}
	
	private boolean removeBlock(String name,BPLBlock bplBlock){		
		Integer state = playerBlocks.get(name).get(bplBlock);
		if(state>0){
			playerBlocks.get(name).put(bplBlock, --state);
			return true;
		}
		return false;
	}
	
	private boolean addBlock(String name,BPLBlock bplBlock){
		Integer max;
		max = defaultMaxBlocks.get(bplBlock);
		if (ConfigHandler.usePlayerLimits){
			max = playerMaxBlocks.get(name).get(bplBlock);
		}
		Integer state = playerBlocks.get(name).get(bplBlock);
		if(state<max){
			playerBlocks.get(name).put(bplBlock, ++state);
			return true;
		}
		return false;
	}
	
	private void init(String name){
		if(!playerMaxBlocks.containsKey(name))
			playerMaxBlocks.put(name, new HashMap<BPLBlock, Integer>());
		if(!playerBlocks.containsKey(name))
			playerBlocks.put(name, new HashMap<BPLBlock, Integer>());
	}
	
	private void init(String name, BPLBlock bplBlock){
		if(!playerMaxBlocks.get(name).containsKey(bplBlock))
			playerMaxBlocks.get(name).put(bplBlock, defaultMaxBlocks.get(bplBlock));
		if(!playerBlocks.get(name).containsKey(bplBlock))
			playerBlocks.get(name).put(bplBlock, 0);
	}
	
	public int getState(Player player, Block block){
		return getState(player.getName(), new BPLBlock(block));
	}
	
	private int getState(String name, BPLBlock bplBlock){
		return playerBlocks.get(name).get(bplBlock);
	}
	
	public int getMax(Player player, Block block){
		return getMax(player.getName(), new BPLBlock(block));
	}
	
	private int getMax(String name, BPLBlock bplBlock){
		return playerMaxBlocks.get(name).get(bplBlock);
	}
	
	public void setMax(String name, BPLBlock bplBlock, int max){
		init(name);
		init(name, bplBlock);
		playerMaxBlocks.get(name).put(bplBlock, max);
	}
	
	public void delMax(String name, BPLBlock bplBlock){
		init(name);
		init(name, bplBlock);
		playerMaxBlocks.get(name).remove(bplBlock);
	}
	
	public void resetDefault(String name){
		init(name);
		playerMaxBlocks.remove(name);
		init(name);
	}
	
	public void resetData(String name){
		init(name);
		playerMaxBlocks.remove(name);
		playerBlocks.remove(name);
		init(name);
	}
}
