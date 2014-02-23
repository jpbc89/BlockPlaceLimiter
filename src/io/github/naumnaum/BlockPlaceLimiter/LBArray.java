package io.github.naumnaum.BlockPlaceLimiter;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.block.Block;

public class LBArray {

	private ArrayList<LimitedBlock> blocks;
	
	public LBArray(){
		blocks = new ArrayList<LimitedBlock>();
	}
	
	public void put(BPLBlock b){
		int i = getIndex(b);
		if (i==-1){
			LimitedBlock newBlock = new LimitedBlock(b, -1);
			blocks.add(newBlock);
		}
	}
	
	public void put(BPLBlock b, int c){
		int i = getIndex(b);
		if (i==-1){
			LimitedBlock newBlock = new LimitedBlock(b, c);
			blocks.add(newBlock);
		}else{
			LimitedBlock listedBlock = blocks.get(i);
			listedBlock.setCount(c);
		}
	}
	
	//returns -1 if blocks isn't listed already
	//returns block index if is listed
	private int getIndex(BPLBlock b){
		int i = 0;
		for(LimitedBlock lb:blocks){
			if(!lb.sameBlock(b))
				i++;
		}
		if (i==blocks.size())
			return -1;
		return i;
	}
	
	public void put(Block b, int c){
		BPLBlock block = new BPLBlock(b);
		put(block, c);
	}
	
	//returns -1 if blocks isn't listed already
	//returns block count if is listed
	public int get(BPLBlock b){
		int i = getIndex(b);
		if (i==-1){
			return -1;
		}else{
			return blocks.get(i).getCount();
		}
	}
	
	public int get(Block b){
		return this.get(new BPLBlock(b));
	}
	
	public String toString(){
		StringBuilder b = new StringBuilder();
		b.append("{");
		Iterator<LimitedBlock> iterator = blocks.iterator();
		while(iterator.hasNext()){
			b.append(iterator.next().toString());
			b.append(", ");
		}
		b.append("}");
		return b.toString();
	}
	
}
