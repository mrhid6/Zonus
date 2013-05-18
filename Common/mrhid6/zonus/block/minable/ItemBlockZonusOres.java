package mrhid6.zonus.block.minable;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockZonusOres extends ItemBlock {

	private final static String[] subNames = { "Zoro Ore", "Trinium Ore", "Noxite Ore", "Stearillium Ore", "yellow", "lightGreen", "pink", "darkGrey", "lightGrey", "cyan", "purple", "blue", "brown", "green", "red", "black" };

	public ItemBlockZonusOres( int id ) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName("fancyblock");
	}

	@Override
	public String getItemDisplayName( ItemStack itemstack ) {
		return subNames[itemstack.getItemDamage()];
	}

	@Override
	public int getMetadata( int damageValue ) {
		return damageValue;
	}

}
