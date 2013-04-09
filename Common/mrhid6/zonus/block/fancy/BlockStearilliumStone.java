package mrhid6.zonus.block.fancy;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import mrhid6.zonus.Utils;
import mrhid6.zonus.block.BlockTexturedBase;

public class BlockStearilliumStone extends BlockTexturedBase {

	public BlockStearilliumStone( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(4.0F);
		this.setHardness(5.0F);
	}
	
	@Override
	public void onBlockAdded( World par1World, int par2, int par3, int par4 ) {
		super.onBlockAdded(par1World, par2, par3, par4);
		Utils.createReactor(par1World,par2,par3,par4);
	}

	@Override
	public void onBlockPlacedBy( World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack ) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, par6ItemStack);
		
		Utils.createReactor(par1World,par2,par3,par4);
	}

}
