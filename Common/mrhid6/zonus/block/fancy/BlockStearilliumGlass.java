package mrhid6.zonus.block.fancy;

import mrhid6.zonus.Config;
import mrhid6.zonus.Utils;
import mrhid6.zonus.block.BlockTexturedBase;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockStearilliumGlass extends BlockTexturedBase {

	public BlockStearilliumGlass( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(4.0F);
		this.setHardness(5.0F);

	}

	public Icon getBlockTextureForConnect( int side, World world, int x, int y, int z ) {

		boolean[] sides = new boolean[6];

		for (int i = 0; i < 6; i++) {

			int x1 = x + Config.SIDE_COORD_MOD[i][0];
			int y1 = y + Config.SIDE_COORD_MOD[i][1];
			int z1 = z + Config.SIDE_COORD_MOD[i][2];

			int block = world.getBlockId(x1, y1, z1);
			if (block == blockID) {
				sides[i] = true;
			}
		}

		return blockIcon;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
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
