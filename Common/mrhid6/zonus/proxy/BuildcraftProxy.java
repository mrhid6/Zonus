package mrhid6.zonus.proxy;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BuildcraftProxy {
	public static Item stoneGear = null;
	
	public BuildcraftProxy(){
		checkStoneGear();
	}
	
	public static boolean isBuildCraftInstalled(){
		try {
			return(Class.forName("buildcraft.BuildCraftCore").getField("instance").get(null)!=null);
		} catch (Exception ex) {
			return false;
		}
	}
	
	public void checkStoneGear(){
		try {
			stoneGear = ((Item)Class.forName("buildcraft.BuildCraftCore").getField("stoneGearItem").get(null));
		} catch (Exception ex) {
			return;
		}
	}
}
