package mrhid6.zonus;

import java.util.HashMap;
import net.minecraftforge.common.Configuration;

public class ItemIds {

	private static HashMap<String, Integer> IDs = new HashMap<String, Integer>();
	private static int lastId = 5000;

	public static void addItemID( Configuration config, String name ) {
		IDs.put(name, Integer.valueOf(config.getItem(name, lastId).getInt()));
		lastId++;
	}

	public static int getID( String name ) {
		int res = IDs.get(name).intValue();
		return res;
	}
}
