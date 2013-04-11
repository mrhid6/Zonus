package mrhid6.zonus;

import java.io.File;
import java.util.HashMap;
import mrhid6.zonus.render.BRTriniumConverter;
import mrhid6.zonus.render.BRZoroChest;
import mrhid6.zonus.render.RenderBlockCable;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class Config extends Configuration {

	private static Configuration config;

	public static CreativeTabXor creativeTabXor;
	
	public static final String Version = "1.0.1";

	static HashMap<String, Integer> renderIds = new HashMap<String, Integer>();

	public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };

	public static int[] getAdjacentCoordinatesForSide( int x, int y, int z, int side ) {
		return new int[] { x + SIDE_COORD_MOD[side][0], y + SIDE_COORD_MOD[side][1], z + SIDE_COORD_MOD[side][2] };
	}

	public static int getRenderId( String string ) {
		return renderIds.get(string);
	}

	public static void init( File configFile ) {
		creativeTabXor = new CreativeTabXor("Zonus");

		RenderingRegistry.registerBlockHandler(new RenderBlockCable());
		renderIds.put("zorocable", RenderBlockCable.renderId);

		RenderingRegistry.registerBlockHandler(new BRTriniumConverter());
		RenderingRegistry.registerBlockHandler(new BRZoroChest());

		config = new Configuration(configFile);
		config.load();

		BlockIds.addBlockID(config, "zoroCable");
		BlockIds.addBlockID(config, "triniumCable");
		BlockIds.addBlockID(config, "zoroFurnace");
		BlockIds.addBlockID(config, "zoroController");
		BlockIds.addBlockID(config, "zoroChest");
		BlockIds.addBlockID(config, "triniumMiner");
		BlockIds.addBlockID(config, "triniumConverter");
		BlockIds.addBlockID(config, "triniumChiller");
		BlockIds.addBlockID(config, "stearilliumCrafter");
		BlockIds.addBlockID(config, "stearilliumEnergyBlock");
		BlockIds.addBlockID(config, "stearilliumReactor");
		BlockIds.addBlockID(config, "zoroGrass");
		BlockIds.addBlockID(config, "hazelspringLog");
		BlockIds.addBlockID(config, "winterbirchLog");
		BlockIds.addBlockID(config, "hazelspringLeaves");
		BlockIds.addBlockID(config, "winterbirchLeaves");
		BlockIds.addBlockID(config, "zoroBrick");
		BlockIds.addBlockID(config, "triniumBrick");
		BlockIds.addBlockID(config, "stearilliumStone");
		BlockIds.addBlockID(config, "stearilliumGlass");

		BlockIds.addBlockID(config, "zoroStill");
		BlockIds.addBlockID(config, "zoroFlowing");

		BlockIds.addBlockID(config, "zoroOre");
		BlockIds.addBlockID(config, "stearilliumOre");
		BlockIds.addBlockID(config, "triniumOre");
		BlockIds.addBlockID(config, "noxiteOre");

		ItemIds.addItemID(config, "zoroIngot");
		ItemIds.addItemID(config, "zoroBucket");
		ItemIds.addItemID(config, "zoroCable");
		ItemIds.addItemID(config, "triniumCable");
		ItemIds.addItemID(config, "zoroStaff");
		ItemIds.addItemID(config, "stearilliumOre");
		ItemIds.addItemID(config, "noxiteCrystal");
		ItemIds.addItemID(config, "triniumHelm");
		ItemIds.addItemID(config, "triniumPlate");
		ItemIds.addItemID(config, "triniumLegs");
		ItemIds.addItemID(config, "triniumBoots");
		ItemIds.addItemID(config, "debugTool");
		
		VersionControll.currentVersion = config.get(CATEGORY_GENERAL, "lastKwnVer", "0").getString();
		
		config.save();
	}
	
	public static void setLastKnownVersion(String version){
		
		set(Configuration.CATEGORY_GENERAL, "lastKwnVer", version);
	}
	
	public static void set(String categoryName, String propertyName, String newValue) {

        config.load();
        if (config.getCategoryNames().contains(categoryName)) {
            if (config.getCategory(categoryName).containsKey(propertyName)) {
                config.getCategory(categoryName).get(propertyName).set(newValue);
            }
        }
        config.save();
    }

	public static int[] spiralArray( int dimension ) {

		int numberOfItem = dimension * dimension;
		int[] spiralArr = new int[numberOfItem];
		byte toggle = 0;
		int ds = dimension;
		int cnt = dimension - 1;
		int cntStart = dimension - 1;
		for (int i = 1; i <= dimension; i++) {
			spiralArr[numberOfItem - i] = i;
		}

		for (int i = numberOfItem - dimension; i > 0; i--) {
			spiralArr[i - 1] = spiralArr[i] + ds;
			cnt = cnt - 1;
			if (cnt == 0) {
				ds = (int) (ds * turnFactor(toggle, dimension));
				cntStart = cntStart - toggle;
				cnt = cntStart;
				toggle = (byte) (toggle ^ 1);
			}
		}
		return spiralArr;
	}

	private static float turnFactor( int arg, float n ) {
		if (arg == 0) {
			return -1 / n;
		} else {
			return n;
		}
	}

}
