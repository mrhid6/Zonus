package mrhid6.zonus.proxy;

import net.minecraft.item.Item;

public class BuildcraftProxy {

	public static Item stoneGear = null;

	public static boolean isBuildCraftInstalled() {
		try {
			return (Class.forName("buildcraft.BuildCraftCore").getField("instance").get(null) != null);
		} catch (Exception ex) {
			return false;
		}
	}

	public BuildcraftProxy() {
		checkStoneGear();
	}

	public void checkStoneGear() {
		try {
			stoneGear = ((Item) Class.forName("buildcraft.BuildCraftCore").getField("stoneGearItem").get(null));
		} catch (Exception ex) {
			return;
		}
	}
}
