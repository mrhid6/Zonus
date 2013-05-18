package mrhid6.zonus.items;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mrhid6.zonus.Zonus;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class RecipeItems extends ItemTexturedBase {
	
	Icon[] icons; 
	private final static String[] subNames = { "Stearillium Ore", "Noxite Crystal", "Noxite Engineering Core"};
	
	public RecipeItems( int id, int maxStackSize, String name ) {
		super(id, maxStackSize, name);
		
		icons = new Icon[2];
	}

	@Override
	public int getMetadata( int par1 ) {
		return par1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons( IconRegister iconRegister ) {
		icons[0] = iconRegister.registerIcon(Zonus.Modname+"stearilliumore");
		icons[1] = iconRegister.registerIcon(Zonus.Modname+"noxitecrystal");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage( int par1 ) {
		return icons[par1];
	}
	
	
	@Override
	public String getItemDisplayName( ItemStack itemstack ) {
		return subNames[itemstack.getItemDamage()];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems( int par1, CreativeTabs par2CreativeTabs, List subItems ) {
		subItems.add(new ItemStack(this, 1, 0));
		subItems.add(new ItemStack(this, 1, 1));
	}
}
