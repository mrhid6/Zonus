package mrhid6.zonus;

import java.io.File;
import java.util.HashMap;
import mrhid6.zonus.lib.BlockIds;
import mrhid6.zonus.lib.CreativeTabXor;
import mrhid6.zonus.lib.ItemIds;
import mrhid6.zonus.lib.event.BonemealHandler;
import mrhid6.zonus.lib.event.GridEventHandler;
import mrhid6.zonus.render.BRCrystalForge;
import mrhid6.zonus.render.BRTriniumConverter;
import mrhid6.zonus.render.BRZoroChest;
import mrhid6.zonus.render.RenderBlockCable;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class Config extends Configuration {

	private static Configuration config;

	public static CreativeTabXor creativeTabXor;

	static HashMap<String, Integer> renderIds = new HashMap<String, Integer>();

	public static final int[][] SIDE_COORD_MOD = { { 0, -1, 0 }, { 0, 1, 0 }, { 0, 0, -1 }, { 0, 0, 1 }, { -1, 0, 0 }, { 1, 0, 0 } };

	public static final String Version = "1.0.25";

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
		RenderingRegistry.registerBlockHandler(new BRCrystalForge());

		RegisterEvents();

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
		BlockIds.addBlockID(config, "noxiteLogger");
		BlockIds.addBlockID(config, "noxiteTurret");
		BlockIds.addBlockID(config, "zoroGrass");
		BlockIds.addBlockID(config, "hazelspringLog");
		BlockIds.addBlockID(config, "winterbirchLog");
		BlockIds.addBlockID(config, "hazelspringLeaves");
		BlockIds.addBlockID(config, "winterbirchLeaves");
		BlockIds.addBlockID(config, "winterbirchSapling");
		BlockIds.addBlockID(config, "triniumBrick");
		BlockIds.addBlockID(config, "blockFancy");
		BlockIds.addBlockID(config, "stearilliumGlass");
		BlockIds.addBlockID(config, "stearilliumReactorCore");

		BlockIds.addBlockID(config, "zoroFlowing");
		BlockIds.addBlockID(config, "zoroStill");

		BlockIds.addBlockID(config, "zonusOres");

		BlockIds.addBlockID(config, "cableTip");
		BlockIds.addBlockID(config, "crystalForge");

		ItemIds.addItemID(config, "zoroIngot");
		ItemIds.addItemID(config, "triniumIngot");
		ItemIds.addItemID(config, "zoroBucket");
		ItemIds.addItemID(config, "zoroCable");
		ItemIds.addItemID(config, "triniumCable");
		ItemIds.addItemID(config, "zoroWrench");
		ItemIds.addItemID(config, "zonusItems");
		ItemIds.addItemID(config, "triniumHelm");
		ItemIds.addItemID(config, "triniumPlate");
		ItemIds.addItemID(config, "triniumLegs");
		ItemIds.addItemID(config, "triniumBoots");
		ItemIds.addItemID(config, "triniumSludge");
		ItemIds.addItemID(config, "debugTool");

		VersionControll.currentVersion = config.get(CATEGORY_GENERAL, "lastKwnVer", "0").getString();

		config.save();
	}

	public static void RegisterEvents() {
		MinecraftForge.EVENT_BUS.register(new BonemealHandler());
		MinecraftForge.EVENT_BUS.register(new GridEventHandler());
	}

	public static void set( String categoryName, String propertyName, String newValue ) {

		config.load();
		if (config.getCategoryNames().contains(categoryName)) {
			if (config.getCategory(categoryName).containsKey(propertyName)) {
				config.getCategory(categoryName).get(propertyName).set(newValue);
			}
		}
		config.save();
	}

	public static void setLastKnownVersion( String version ) {

		set(Configuration.CATEGORY_GENERAL, "lastKwnVer", version);
	}

}
