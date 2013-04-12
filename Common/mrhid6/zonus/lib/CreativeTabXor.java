package mrhid6.zonus.lib;

import mrhid6.zonus.block.ModBlocks;
import net.minecraft.creativetab.CreativeTabs;

public class CreativeTabXor extends CreativeTabs {

	public CreativeTabXor( String label ) {
		super(label);
	}

	@Override
	public int getTabIconItemIndex() {
		return ModBlocks.stearilliumCrafter.blockID;
	}

	@Override
	public String getTranslatedTabLabel() {
		return "Zonus";
	}

}
