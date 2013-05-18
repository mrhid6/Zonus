package mrhid6.zonus.block;

import mrhid6.zonus.block.fancy.BlockCableTip;
import mrhid6.zonus.block.fancy.BlockFancy;
import mrhid6.zonus.block.fancy.BlockHazelspringLog;
import mrhid6.zonus.block.fancy.BlockNoxiteTurret;
import mrhid6.zonus.block.fancy.BlockReactorCore;
import mrhid6.zonus.block.fancy.BlockStearilliumGlass;
import mrhid6.zonus.block.fancy.BlockTriniumBrick;
import mrhid6.zonus.block.fancy.BlockWinterBirchSapling;
import mrhid6.zonus.block.fancy.BlockWinterbirchLog;
import mrhid6.zonus.block.fancy.BlockZoroGrass;
import mrhid6.zonus.block.fancy.HazelspringLeaves;
import mrhid6.zonus.block.fancy.ItemBlockFancy;
import mrhid6.zonus.block.fancy.WinterbirchLeaves;
import mrhid6.zonus.block.machine.BlockCrystalForge;
import mrhid6.zonus.block.machine.BlockNoxiteLogger;
import mrhid6.zonus.block.machine.BlockStearilliumCrafter;
import mrhid6.zonus.block.machine.BlockStearilliumEnergyCube;
import mrhid6.zonus.block.machine.BlockTriniumConverter;
import mrhid6.zonus.block.machine.BlockTriniumMiner;
import mrhid6.zonus.block.machine.BlockZoroChest;
import mrhid6.zonus.block.machine.BlockZoroController;
import mrhid6.zonus.block.machine.BlockZoroFurnace;
import mrhid6.zonus.block.minable.ItemBlockZonusOres;
import mrhid6.zonus.block.minable.ZonusOres;
import mrhid6.zonus.block.multiblock.BlockStearilliumReactor;
import mrhid6.zonus.block.multiblock.BlockTriniumChiller;
import mrhid6.zonus.items.Materials;
import mrhid6.zonus.items.ModItems;
import mrhid6.zonus.lib.BlockIds;
import mrhid6.zonus.tileEntity.TECableBase;
import mrhid6.zonus.tileEntity.TENoxiteTurret;
import mrhid6.zonus.tileEntity.TETriniumCable;
import mrhid6.zonus.tileEntity.machine.TECrystalForge;
import mrhid6.zonus.tileEntity.machine.TENoxiteLogger;
import mrhid6.zonus.tileEntity.machine.TEStearilliumCrafter;
import mrhid6.zonus.tileEntity.machine.TEStearilliumEnergyCube;
import mrhid6.zonus.tileEntity.machine.TETriniumConverter;
import mrhid6.zonus.tileEntity.machine.TETriniumMiner;
import mrhid6.zonus.tileEntity.machine.TEZoroChest;
import mrhid6.zonus.tileEntity.machine.TEZoroController;
import mrhid6.zonus.tileEntity.machine.TEZoroFurnace;
import mrhid6.zonus.tileEntity.multiblock.TEStearilliumReactor;
import mrhid6.zonus.tileEntity.multiblock.TETriniumChillerBase;
import mrhid6.zonus.tileEntity.multiblock.TETriniumChillerCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModBlocks {

	public static BlockLeaves hazelspringLeaves;
	public static Block hazelspringLog;
	public static Block noxiteLogger;
	public static Block noxiteTurret;
	public static Block stearilliumCrafter;
	public static Block stearilliumEnergyBlock;
	public static Block stearilliumGlass;
	public static Block stearilliumReactor;
	public static Block stearilliumReactorCore;
	public static Block triniumBrick;
	public static Block triniumCable;
	public static Block triniumChiller;
	public static Block triniumConverter;
	public static Block triniumMiner;
	public static BlockLeaves winterbirchLeaves;
	public static Block winterbirchLog;
	public static BlockWinterBirchSapling winterbirchSapling;
	public static Block zoroCable;
	public static Block zoroChest;
	public static Block zoroController;
	public static BlockFluid zoroFlowing;
	public static Block zoroFurnace;
	public static Block zoroGrass;
	public static BlockFluid zoroStill;
	public static Block crystalForge;

	public static void init() {
		initOres();
		initCables();
		initMachines();
		initMultiBlocks();
		initTesting();
		initFancy();
		initTrees();
		initLiquids();

		initOreDict();
	}

	public static void initCables() {
		// Cables

		zoroCable = new BlockZoroCable(BlockIds.getID("zoroCable"), "zorocable", false);
		LanguageRegistry.addName(zoroCable, "Zoro Cable");
		GameRegistry.registerBlock(zoroCable, zoroCable.getUnlocalizedName());
		GameRegistry.registerTileEntity(TECableBase.class, "te" + zoroCable.getUnlocalizedName());

		triniumCable = new BlockTriniumCable(BlockIds.getID("triniumCable"), "triniumcable", false);
		LanguageRegistry.addName(triniumCable, "Trinium Cable");
		GameRegistry.registerBlock(triniumCable, triniumCable.getUnlocalizedName());
		GameRegistry.registerTileEntity(TETriniumCable.class, "te" + triniumCable.getUnlocalizedName());
	}

	public static void initFancy() {
		// Fancy Blocks

		zoroGrass = new BlockZoroGrass(BlockIds.getID("zoroGrass"), "zoroGrass", "zorograss");
		LanguageRegistry.addName(zoroGrass, "Zoro Grass");
		GameRegistry.registerBlock(zoroGrass, zoroGrass.getUnlocalizedName());

		triniumBrick = new BlockTriniumBrick(BlockIds.getID("triniumBrick"), "triniumBrick");
		LanguageRegistry.addName(triniumBrick, "Trinium Brick");
		GameRegistry.registerBlock(triniumBrick, triniumBrick.getUnlocalizedName());

		Block Blockfancy = new BlockFancy(BlockIds.getID("blockFancy"), "blockfancy");
		GameRegistry.registerBlock(Blockfancy, ItemBlockFancy.class, "stearilliumStone");

		Materials.ZoroBrick = new ItemStack(Blockfancy, 1, 0);
		LanguageRegistry.addName(Materials.ZoroBrick, "Zoro Brick");
		
		Materials.StearilliumStone = new ItemStack(Blockfancy, 1, 1);
		LanguageRegistry.addName(Materials.StearilliumStone, "Stearillium Stone");

		Materials.NoxiteBlock = new ItemStack(Blockfancy, 1, 2);
		LanguageRegistry.addName(Materials.NoxiteBlock, "Noxite Block");
		
		Materials.NoxiteEngineeringCore = new ItemStack(Blockfancy, 1, 3);
		LanguageRegistry.addName(Materials.NoxiteEngineeringCore, "Noxite Engineering Core");

		stearilliumGlass = new BlockStearilliumGlass(BlockIds.getID("stearilliumGlass"), "stearilliumGlass");
		LanguageRegistry.addName(stearilliumGlass, "Stearillium Glass");
		GameRegistry.registerBlock(stearilliumGlass, stearilliumGlass.getUnlocalizedName());

		stearilliumReactorCore = new BlockReactorCore(BlockIds.getID("stearilliumReactorCore"), "stearilliumReactorCore");
		LanguageRegistry.addName(stearilliumReactorCore, "Stearillium Reactor Core");
		GameRegistry.registerBlock(stearilliumReactorCore, stearilliumReactorCore.getUnlocalizedName());
		
		Materials.StearilliumReactorCore = new ItemStack(stearilliumReactorCore,1);

		Block cableTip = new BlockCableTip(BlockIds.getID("cableTip"), "cableTip");
		GameRegistry.registerBlock(cableTip, cableTip.getUnlocalizedName());

		Materials.CableTip = new ItemStack(cableTip);
	}

	public static void initLiquids() {
		// Liquids

		zoroStill = new BlockZoroStill(BlockIds.getID("zoroStill"), "zorojuice");
		zoroFlowing = new BlockZoroFlowing(BlockIds.getID("zoroFlowing"), "zorojuiceflowing");

		GameRegistry.registerBlock(zoroStill, zoroStill.getUnlocalizedName());
		GameRegistry.registerBlock(zoroFlowing, zoroFlowing.getUnlocalizedName());

		LanguageRegistry.addName(zoroStill, "Volatile Zoro");
		LanguageRegistry.addName(zoroFlowing, "Volatile Zoro");

		LiquidDictionary.getOrCreateLiquid("zorojuice", new LiquidStack(zoroStill, LiquidContainerRegistry.BUCKET_VOLUME));

		LiquidContainerRegistry.registerLiquid(new LiquidContainerData(new LiquidStack(zoroStill, LiquidContainerRegistry.BUCKET_VOLUME), new ItemStack(ModItems.zoroBucket), new ItemStack(Item.bucketEmpty)));
	}

	public static void initMachines() {

		zoroFurnace = new BlockZoroFurnace(BlockIds.getID("zoroFurnace"), "zoroFurnace", "zorofurnace", true);
		LanguageRegistry.addName(zoroFurnace, "Zoro Furnace");
		GameRegistry.registerBlock(zoroFurnace, zoroFurnace.getUnlocalizedName());
		GameRegistry.registerTileEntity(TEZoroFurnace.class, "te" + zoroFurnace.getUnlocalizedName());

		zoroController = new BlockZoroController(BlockIds.getID("zoroController"), "zoroController", "zorocontroller", true);
		LanguageRegistry.addName(zoroController, "Zoro Controller");
		GameRegistry.registerBlock(zoroController, zoroController.getUnlocalizedName());
		GameRegistry.registerTileEntity(TEZoroController.class, "te" + zoroController.getUnlocalizedName());

		zoroChest = new BlockZoroChest(BlockIds.getID("zoroChest"), "zoroChest", "zorochest");
		LanguageRegistry.addName(zoroChest, "Zoro Chest");
		GameRegistry.registerBlock(zoroChest, zoroChest.getUnlocalizedName());
		GameRegistry.registerTileEntity(TEZoroChest.class, "te" + zoroChest.getUnlocalizedName());

		triniumMiner = new BlockTriniumMiner(BlockIds.getID("triniumMiner"), "triniumMiner", "triniumminer", true);
		LanguageRegistry.addName(triniumMiner, "Trinium Miner");
		GameRegistry.registerBlock(triniumMiner, triniumMiner.getUnlocalizedName());
		GameRegistry.registerTileEntity(TETriniumMiner.class, "te" + triniumMiner.getUnlocalizedName());

		stearilliumCrafter = new BlockStearilliumCrafter(BlockIds.getID("stearilliumCrafter"), "stearilliumCrafter", "stearilliumcrafter", true);
		LanguageRegistry.addName(stearilliumCrafter, "Stearillium Crafter");
		GameRegistry.registerBlock(stearilliumCrafter, stearilliumCrafter.getUnlocalizedName());
		GameRegistry.registerTileEntity(TEStearilliumCrafter.class, "te" + stearilliumCrafter.getUnlocalizedName());

		triniumConverter = new BlockTriniumConverter(BlockIds.getID("triniumConverter"), "triniumConverter", "triniumconverter");
		LanguageRegistry.addName(triniumConverter, "Trinium Converter");
		GameRegistry.registerBlock(triniumConverter, triniumConverter.getUnlocalizedName());
		GameRegistry.registerTileEntity(TETriniumConverter.class, "te" + triniumConverter.getUnlocalizedName());

		stearilliumEnergyBlock = new BlockStearilliumEnergyCube(BlockIds.getID("stearilliumEnergyBlock"), "stearilliumenergyblock");
		LanguageRegistry.addName(stearilliumEnergyBlock, "Stearillium Energy Cube");
		GameRegistry.registerBlock(stearilliumEnergyBlock, stearilliumEnergyBlock.getUnlocalizedName());
		GameRegistry.registerTileEntity(TEStearilliumEnergyCube.class, "te" + stearilliumEnergyBlock.getUnlocalizedName());

		noxiteLogger = new BlockNoxiteLogger(BlockIds.getID("noxiteLogger"), "noxitelogger");
		LanguageRegistry.addName(noxiteLogger, "Noxite Logger");
		GameRegistry.registerBlock(noxiteLogger, noxiteLogger.getUnlocalizedName());
		GameRegistry.registerTileEntity(TENoxiteLogger.class, "te" + noxiteLogger.getUnlocalizedName());
		
		crystalForge = new BlockCrystalForge(BlockIds.getID("crystalForge"), "crystalForge");
		LanguageRegistry.addName(crystalForge, "Crystal Forge");
		GameRegistry.registerBlock(crystalForge, crystalForge.getUnlocalizedName());
		GameRegistry.registerTileEntity(TECrystalForge.class, "te" + crystalForge.getUnlocalizedName());
	}

	public static void initMultiBlocks() {
		// Multiblocks

		triniumChiller = new BlockTriniumChiller(BlockIds.getID("triniumChiller"), "triniumChiller");
		LanguageRegistry.addName(triniumChiller, "Trinium Chiller");
		GameRegistry.registerBlock(triniumChiller, "triniumchiller");

		GameRegistry.registerTileEntity(TETriniumChillerCore.class, "triniumchillercontainer");
		GameRegistry.registerTileEntity(TETriniumChillerBase.class, "triniumchillerbasecontainer");

		stearilliumReactor = new BlockStearilliumReactor(BlockIds.getID("stearilliumReactor"), "stearilliumreactor");
		LanguageRegistry.addName(stearilliumReactor, "Stearillium Reactor");
		GameRegistry.registerBlock(stearilliumReactor, stearilliumReactor.getUnlocalizedName());
		GameRegistry.registerTileEntity(TEStearilliumReactor.class, "te" + stearilliumReactor.getUnlocalizedName());
	}

	public static void initOreDict() {
		OreDictionary.registerOre("logWood", new ItemStack(hazelspringLog, 1, 0));
		OreDictionary.registerOre("logWood", new ItemStack(winterbirchLog, 1, 0));
		OreDictionary.registerOre("treeLeaves", new ItemStack(winterbirchLeaves, 1, 0));
		OreDictionary.registerOre("treeSapling", new ItemStack(winterbirchSapling, 1, 0));
	}

	public static void initOres() {
		// Ores

		Block zonusOres = new ZonusOres(BlockIds.getID("zonusOres"), "zonusOres");
		GameRegistry.registerBlock(zonusOres, ItemBlockZonusOres.class, "zonusOres");

		Materials.ZoroOre = new ItemStack(zonusOres, 1, 0);
		LanguageRegistry.addName(Materials.ZoroOre, "Zoro Ore");

		Materials.TriniumOre = new ItemStack(zonusOres, 1, 1);
		LanguageRegistry.addName(Materials.TriniumOre, "Trinium Ore");

		Materials.NoxiteOre = new ItemStack(zonusOres, 1, 2);
		LanguageRegistry.addName(Materials.NoxiteOre, "Noxite Ore");

		Materials.StearilliumOreBlock = new ItemStack(zonusOres, 1, 3);
		LanguageRegistry.addName(Materials.StearilliumOreBlock, "Stearillium Ore");

	}

	public static void initTesting() {
		// Testing

		noxiteTurret = new BlockNoxiteTurret(BlockIds.getID("noxiteTurret"), "noxiteTurret");
		LanguageRegistry.addName(noxiteTurret, "Noxite Turret");
		GameRegistry.registerBlock(noxiteTurret, noxiteTurret.getUnlocalizedName());
		GameRegistry.registerTileEntity(TENoxiteTurret.class, "te" + noxiteTurret.getUnlocalizedName());
	}

	public static void initTrees() {
		// Trees

		hazelspringLog = new BlockHazelspringLog(BlockIds.getID("hazelspringLog"), "hazelspringLog", "hazelspringlog");
		LanguageRegistry.addName(hazelspringLog, "Hazelspring Log");
		GameRegistry.registerBlock(hazelspringLog, hazelspringLog.getUnlocalizedName());

		hazelspringLeaves = new HazelspringLeaves(BlockIds.getID("hazelspringLeaves"), "hazelspringleaves");
		LanguageRegistry.addName(hazelspringLeaves, "Hazelspring Leaves");
		GameRegistry.registerBlock(hazelspringLeaves, hazelspringLeaves.getUnlocalizedName());

		winterbirchLog = new BlockWinterbirchLog(BlockIds.getID("winterbirchLog"), "winterbirchLog", "winterbirchlog");
		LanguageRegistry.addName(winterbirchLog, "Winter Birch Log");
		GameRegistry.registerBlock(winterbirchLog, winterbirchLog.getUnlocalizedName());

		winterbirchLeaves = new WinterbirchLeaves(BlockIds.getID("winterbirchLeaves"), "winterbirchleaves");
		LanguageRegistry.addName(winterbirchLeaves, "Winter Birch Leaves");
		GameRegistry.registerBlock(winterbirchLeaves, winterbirchLeaves.getUnlocalizedName());

		winterbirchSapling = new BlockWinterBirchSapling(BlockIds.getID("winterbirchSapling"), "winterbirchsapling");
		LanguageRegistry.addName(winterbirchSapling, "Winter Birch Sapling");
		GameRegistry.registerBlock(winterbirchSapling, winterbirchSapling.getUnlocalizedName());
	}
}
