package mrhid6.zonus;

import java.util.HashMap;
import net.minecraftforge.common.Configuration;

public class BlockIds {

	private static HashMap<String, Integer> IDs = new HashMap<String, Integer>();
	private static int lastId = 500;

	public static void addBlockID( Configuration config, String name ) {
		IDs.put(name, Integer.valueOf(config.getBlock(name, lastId).getInt()));
		lastId++;
	}

	public static int getID( String name ) {
		int res = IDs.get(name).intValue();
		return res;
	}
}
