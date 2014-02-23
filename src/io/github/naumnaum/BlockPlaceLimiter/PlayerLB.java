package io.github.naumnaum.BlockPlaceLimiter;

import org.bukkit.entity.Player;

public class PlayerLB {

	private String playerId;
	private LBArray blocks;
	
	public PlayerLB(String id){
		playerId=id;
		blocks = new LBArray();
	}
	
	public PlayerLB(Player p){
		new PlayerLB(p.getName());
	}
	
	public void put(BPLBlock b, int c){
		blocks.put(b, c);
	}
	
	public int getCount(BPLBlock b){
		return blocks.get(b);
	}
	
	public boolean samePlayer(String id){
		if (this.playerId==id)
			return true;
		return false;
	}
	
	public boolean samePlayer(Player player){
		if (this.playerId==player.getName())
			return true;
		return false;
	}
	
	public void put(BPLBlock b){
		blocks.put(b);
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append(playerId+": Blocks: ");
		b.append(blocks.toString());
		return b.toString();
	}
}
