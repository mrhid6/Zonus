package mrhid6.zonus.lib;

import java.util.HashMap;
import net.minecraftforge.common.Configuration;

public class BlockIds {

	private static HashMap<String, Integer> IDs = new HashMap<String, Integer>();
	public static int StartId = -1;
	private static int lastId = -1;

	public static void addBlockID( Configuration config, String name ) {
		if(StartId == -1 && lastId == -1){
			throw new NullPointerException("Start id not set!");
		}
		
		if(lastId == -1)
			lastId = StartId;
		
		IDs.put(name, Integer.valueOf(config.getBlock(name, lastId).getInt()));
		lastId++;
	}

	public static int getID( String name ) {
		int res = IDs.get(name).intValue();
		return res;
	}
}
