package io.github.naumnaum.BlockPlaceLimiter;

import java.io.Serializable;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

@SuppressWarnings("serial")
public class BPLBlock implements Serializable{

	private int id;
	private byte meta;
	private String name;
	
	@SuppressWarnings("deprecation")
	public BPLBlock(Block b){
		id = b.getTypeId();
		meta = b.getData();
		name = b.getType().name().toLowerCase().replace('_', ' ');
	}
	
	public BPLBlock(int id, byte meta, String name){
		this.id=id;
		this.meta=meta;
		this.name=name;
	}
	
	public boolean equals(Object o){
		BPLBlock b = (BPLBlock) o;
		if (this.id==b.id && this.meta==b.meta)
			return true;
		return false;
	}
	
	public String toString(){
		return ChatColor.LIGHT_PURPLE+"Id: "+ChatColor.RESET+id+
				ChatColor.LIGHT_PURPLE+" Meta: "+ChatColor.RESET+meta+
				ChatColor.LIGHT_PURPLE+" Name: "+ChatColor.RESET+name;
	}
	
	public int hashCode(){
		return id;
	}
	
	public static BPLBlock parseConfig(String s){
		int id = Integer.parseInt(s.trim().split(":")[0].trim());
		byte meta = Byte.parseByte(s.trim().split(":")[1].trim().split("=")[0].trim());
		String name = s.split(":")[2];
		return new BPLBlock(id, meta, name);
	}
	
	public String getName(){
		return name;
	}
	
	public int getId(){
		return id;
	}
	
	public byte getMeta(){
		return meta;
	}
}
