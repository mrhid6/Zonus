package mrhid6.zonus.items;

import mrhid6.zonus.armour.TriniumArmour;
import mrhid6.zonus.lib.BlockIds;
import mrhid6.zonus.lib.ItemIds;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModItems {

	static EnumArmorMaterial ArmorMaterial = EnumHelper.addArmorMaterial("triniumArmorMaterial", 40, new int[] { 5, 14, 9, 5 }, 15);
	public static Item debug;
	public static Item triniumBoots;
	public static Item triniumCable;
	public static Item triniumHelm;

	public static Item triniumLegs;
	public static Item triniumPlate;
	public static Item zoroBucket;
	public static Item zoroCable;

	public static Item zoroWrench;

	public static void init() {

		// ingots
		Item zoroIngot = new ItemZonCrafting(ItemIds.getID("zoroIngot"), 64, "zoroIngot");
		LanguageRegistry.addName(zoroIngot, "Zoro Ingot");
		Materials.ZoroIngot = new ItemStack(zoroIngot);

		Item triniumIngot = new ItemZonCrafting(ItemIds.getID("triniumIngot"), 64, "triniumIngot");
		LanguageRegistry.addName(triniumIngot, "Trinium Ingot");
		Materials.TriniumIngot = new ItemStack(triniumIngot);

		// cable
		zoroCable = new ItemZoroCable(ItemIds.getID("zoroCable"), 64, "zoroCable");
		LanguageRegistry.addName(zoroCable, "Zoro Cable");

		triniumCable = new ItemTriniumCable(ItemIds.getID("triniumCable"), 64, "triniumCable");
		LanguageRegistry.addName(triniumCable, "Trinium Cable");

		// misc
		zoroBucket = new ItemZoroBucket(ItemIds.getID("zoroBucket"), BlockIds.getID("zoroFlowing"), "zorobucket");
		LanguageRegistry.addName(zoroBucket, "Volatile Zoro");

		zoroWrench = new ItemZoroStaff(ItemIds.getID("zoroWrench"), "zoroWrench");
		LanguageRegistry.addName(zoroWrench, "Zoro Wrench");

		debug = new ItemDebug(ItemIds.getID("debugTool"), "debug");
		LanguageRegistry.addName(debug, "debug");

		// ore drops
		Item multiItem = new RecipeItems(ItemIds.getID("zonusItems"), 64, "stearilliumore");
		GameRegistry.registerItem(multiItem, "multiItem");
		
		Materials.StearilliumOre = new ItemStack(multiItem,1,0);
		LanguageRegistry.addName(Materials.StearilliumOre, "Stearillium Ore");

		Materials.NoxiteCystal = new ItemStack(multiItem,1,1);
		LanguageRegistry.addName(Materials.NoxiteCystal, "Noxite Crystal");

		// dusts

		Item triniumSludge = new ItemTexturedBase(ItemIds.getID("triniumSludge"), 64, "triniumSludge");
		LanguageRegistry.addName(triniumSludge, "Trinium Sludge");
		Materials.TriniumSludge = new ItemStack(triniumSludge);

		Materials.NoxiteDust = Materials.TriniumSludge.copy();

		// armour
		int data = 3;

		triniumHelm = new TriniumArmour(ItemIds.getID("triniumHelm"), ArmorMaterial, data, 0).setUnlocalizedName("triniumhelm");
		LanguageRegistry.addName(triniumHelm, "Trinium Helm");

		triniumPlate = new TriniumArmour(ItemIds.getID("triniumPlate"), ArmorMaterial, data, 1).setUnlocalizedName("triniumplate");
		LanguageRegistry.addName(triniumPlate, "Trinium Plate");

		triniumLegs = new TriniumArmour(ItemIds.getID("triniumLegs"), ArmorMaterial, data, 2).setUnlocalizedName("triniumlegs");
		LanguageRegistry.addName(triniumLegs, "Trinium Legs");

		triniumBoots = new TriniumArmour(ItemIds.getID("triniumBoots"), ArmorMaterial, data, 3).setUnlocalizedName("triniumboots");
		LanguageRegistry.addName(triniumBoots, "Trinium Boots");
	}
}
