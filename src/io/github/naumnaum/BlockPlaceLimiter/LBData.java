package io.github.naumnaum.BlockPlaceLimiter;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LBData {

	private ArrayList<PlayerLB> data;
	
	public LBData(String id){
		PlayerLB plb = new PlayerLB(id);
		data = new ArrayList<PlayerLB>();
		data.add(plb);
	}
	
	public LBData(){
		data = new ArrayList<PlayerLB>();
	}
	
	public LBData(Player player){
		new LBData(player.getName());
	}
	
	public void put(String id){
		int index = this.getIndex(id);
		if (index==-1){
			PlayerLB plb = new PlayerLB(id);
			data.add(plb);
		}
	}
	
	public void put(Player player){
		put(player.getName());
	}
	
	public void put(String id, BPLBlock block, int count){
		this.put(id);
		PlayerLB plb = this.getPLB(id);
		plb.put(block, count);
	}
	
	public void put(String id, BPLBlock block){
		this.put(id);
		PlayerLB plb = this.getPLB(id);
		plb.put(block);
	}
	
	public void put(Player player, Block block){
		put(player.getName(),new BPLBlock(block));
	}
	
	public void put(Player player, Block block, int count){
		put(player.getName(),new BPLBlock(block), count);
	}
	
	private PlayerLB getPLB(String id){
		int index = this.getIndex(id);
		return data.get(index);
	}
	
	//returns -1 if blocks isn't listed already
	//returns block index if is listed
	private int getIndex(String id){
		int i = 0;
		for(PlayerLB playerLB:data){
			if(!playerLB.samePlayer(id))
				i++;
		}
		if (i==data.size())
			return -1;
		return i;
	}
	
	public int getCount(String id, BPLBlock block){
		PlayerLB plb = this.getPLB(id);
		return plb.getCount(block);
	}
	
	public int getCount(Player player, Block block){
		return this.getCount(player.getName(), new BPLBlock(block));
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("Data: {");
		Iterator<PlayerLB> iterator = data.iterator();
		while(iterator.hasNext()){
			b.append(iterator.next().toString());
			b.append(", ");
		}
		b.append("}");
		return b.toString();
	}
		
}
