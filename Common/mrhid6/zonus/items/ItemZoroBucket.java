package mrhid6.zonus.items;

import mrhid6.zonus.Config;
import mrhid6.zonus.Zonus;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemZoroBucket extends ItemBucket {

	public ItemZoroBucket( int id, int fluidId, String name ) {

		super(id, fluidId);

		setCreativeTab(Config.creativeTabXor);
		setContainerItem(Item.bucketEmpty);
		setUnlocalizedName(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons( IconRegister iconRegister ) {
		itemIcon = iconRegister.registerIcon(Zonus.Modname + "zorobucket");
	}

}
