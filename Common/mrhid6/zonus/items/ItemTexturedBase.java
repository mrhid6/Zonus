package mrhid6.zonus.items;

import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTexturedBase extends Item {

	public String texturename;

	public ItemTexturedBase( int id, int maxStackSize, String name ) {
		super(id);

		texturename = name.toLowerCase();

		setMaxStackSize(maxStackSize);
		setCreativeTab(Config.creativeTabXor);
		setUnlocalizedName(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateIcons( IconRegister iconRegister ) {
		iconIndex = iconRegister.registerIcon(Zonus.Modname + texturename);
	}
}
