package mrhid6.zonus.block.fancy;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;


public class ItemBlockSaplings extends ItemBlock {

	private final static String[] subNames = { "Winter Birch Sapling","Hazlespring Sapling", "Noxite Block", "Noxite Engineering Core", "lightBlue", "yellow", "lightGreen", "pink", "darkGrey", "lightGrey", "cyan", "purple", "blue", "brown", "green", "red", "black" };

	public ItemBlockSaplings( int id ) {
		super(id);
		setHasSubtypes(true);
		setUnlocalizedName("saplingsblock");
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
