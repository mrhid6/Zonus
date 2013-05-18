package mrhid6.zonus;

import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.items.Materials;
import mrhid6.zonus.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class ZonRecipes {

	public static void addRecipes() {

		// blocks
		GameRegistry.addRecipe(Materials.ZoroBrick, new Object[] { "ib", "bi", 'i', Materials.ZoroIngot, 'b', Block.stoneBrick });
		GameRegistry.addRecipe(Materials.StearilliumStone, new Object[] { " a ", "aba", " a ", 'a', Materials.StearilliumOre, 'b', Block.stone });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.triniumBrick, 1), new Object[] { "aaa", "aba", "aaa", 'a', Block.brick, 'b', Materials.TriniumIngot });

		GameRegistry.addRecipe(new ItemStack(ModBlocks.zoroFurnace, 1), new Object[] { "aaa", "ibi", "sss", 'i', Materials.ZoroIngot, 'b', Block.furnaceIdle, 's', Materials.ZoroBrick, 'a', Block.stone });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.stearilliumCrafter, 1), new Object[] { "sss", "aba", "aaa", 'a', Block.stone, 'b', Block.workbench, 's', Materials.StearilliumStone });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.zoroChest, 1), new Object[] { "sis", "sbs", "szs", 's', Block.stone, 'b', Block.chest, 'i', Materials.ZoroIngot, 'z', Materials.ZoroBrick });
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.noxiteLogger, 1), new Object[] { "sss", "aba", "aaa", 'a', Block.stone, 'b', Materials.NoxiteEngineeringCore, 's', "treeSapling" }));

		GameRegistry.addShapelessRecipe(Materials.NoxiteBlock, Materials.NoxiteCystal, Materials.NoxiteCystal, Materials.NoxiteCystal, Materials.NoxiteCystal);
		GameRegistry.addRecipe(Materials.StearilliumReactorCore, new Object[] { "aaa", "ada", "aaa", 'a', Materials.StearilliumStone, 'd', Item.diamond});
		GameRegistry.addRecipe(Materials.NoxiteEngineeringCore,new Object[] { "bbb", "bab", "bbb", 'a', Materials.StearilliumReactorCore, 'b', Materials.NoxiteBlock });
		
		// items
		GameRegistry.addRecipe(new ItemStack(ModItems.zoroWrench, 1), new Object[] { "a  ", " b ", "  c", 'a', Materials.ZoroIngot, 'b', Item.stick, 'c', Block.cloth });
		GameRegistry.addRecipe(new ItemStack(ModItems.zoroCable, 6), new Object[] { "aaa", "bbb", "aaa", 'a', Block.cloth, 'b', Materials.ZoroIngot });

		// furnace
		FurnaceRecipes.smelting().addSmelting(Materials.ZoroOre.itemID, Materials.ZoroIngot, 0.2f);
		FurnaceRecipes.smelting().addSmelting(Materials.TriniumOre.itemID, Materials.TriniumSludge, 0f);

	}

}
