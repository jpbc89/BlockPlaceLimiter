package io.github.naumnaum.BlockPlaceLimiter;

import org.bukkit.block.Block;

public class LimitedBlock {

	private BPLBlock block;
	private int count;
	
	public LimitedBlock(BPLBlock b, int c){
		block = b;
		count = c;
	}
	
	public LimitedBlock(Block b, int c){
		block = new BPLBlock(b);
		count = c;
	}
	
	public boolean sameBlock(BPLBlock b){
		if (this.block.equals(b))
			return true;
		return false;
	}
	
	public void setCount(int c){
		count = c;
	}
	
	public int getCount(){
		return count;
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("{Block: ");
		b.append(block.toString());
		b.append(" Count: ");
		b.append(count);
		b.append("}");
		return b.toString();
	}
}
