package mrhid6.zonus;

import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class ZonRecipes {

	public static void addRecipes() {
		GameRegistry.addRecipe(new ItemStack(ModBlocks.zoroBrick, 1), new Object[] { "ib", "bi", 'i', ModItems.zoroIngot, 'b', Block.stoneBrick });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.stearilliumStone, 1), new Object[] { "aaa", "aba", "aaa", 'a', ModItems.stearilliumOre, 'b', Block.stone });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.triniumBrick, 1), new Object[] { "aaa", "aba", "aaa", 'a', Block.brick, 'b', ModItems.triniumIngot });
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.zoroFurnace, 1), new Object[] { "aaa", "ibi", "sss", 'i', ModItems.zoroIngot, 'b', Block.furnaceIdle, 's', ModBlocks.zoroBrick, 'a', Block.stone });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.stearilliumCrafter, 1), new Object[] { "sss", "aba", "aaa", 'a', Block.stone, 'b', Block.workbench, 's', ModBlocks.stearilliumStone });

		GameRegistry.addRecipe(new ItemStack(ModItems.zoroStaff, 1), new Object[] { "a  ", " b ", "  c", 'a', ModItems.zoroIngot, 'b', Item.stick, 'c', Block.cloth });

	}
	
	
}
