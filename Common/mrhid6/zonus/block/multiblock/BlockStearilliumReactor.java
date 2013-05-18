package mrhid6.zonus.block.multiblock;

import java.util.Random;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockMachine;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.tileEntity.multiblock.TEStearilliumReactor;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStearilliumReactor extends BlockMachine {

	public BlockStearilliumReactor( int id, String name ) {
		super(id, name, name, false);
		icons = new Icon[80];
		
		this.setResistance(4.0F);
		this.setHardness(4.0F);
	}

	@Override
	public void breakBlock( World par1World, int par2, int par3, int par4, int par5, int par6 ) {

		restoreBlocks(par1World, par2, par3, par4);

		for (int yy = -3; yy <= 3; yy++) {
			for (int xx = -3; xx <= 3; xx++) {
				for (int zz = -3; zz <= 3; zz++) {
					par1World.notifyBlocksOfNeighborChange(par2 + xx, par3 + yy, par4 + zz, blockID);
				}
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	public int calculateLevel( IBlockAccess world, int x, int y, int z ) {
		int meta = world.getBlockMetadata(x, y, z);

		int topblock = 0;
		for (int y1 = y; y1 <= y + 4; y1++) {

			int block = world.getBlockId(x, y1, z);
			int meta1 = world.getBlockMetadata(x, y1, z);

			if (block == 0 || block != blockID || (meta != meta1)) {
				topblock = y1;
				break;
			}
		}

		int diff = ((topblock - y) <= 0) ? 0 : (topblock - y);

		// System.out.println(diff);

		return diff * 4 - 4;
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return null;
	}

	@Override
	public TileEntity createTileEntity( World world, int metadata ) {

		return new TEStearilliumReactor();
	}

	@Override
	public Icon getBlockTexture( IBlockAccess world, int x, int y, int z, int side ) {

		int index = getBlockTextureInt(world, x, y, z, side);
		return (index <= -1) ? icons[6] : icons[getBlockTextureInt(world, x, y, z, side)];
	}

	public int getBlockTextureInt( IBlockAccess world, int x, int y, int z, int side ) {

		// return this.blockIcon;

		int meta = world.getBlockMetadata(x, y, z);
		int level = calculateLevel(world, x, y, z);
		int add = 0;
		add = 0;
		switch (side) {
		case 0:
		case 1:
			switch (meta) {
			default:
				return meta - 1;

			case 0:
				return 15;
			}
		case 2:
			switch (meta) {
			default:
				if (level != 8) {
					return 7;
				}
				return 6;
			case 1:
				return 3 + level;
			case 2:
				return 2 + level;
			case 3:
				return 1 + level;
			case 4:
				return 0 + level;
			}
		case 3:
			switch (meta) {
			default:
				if (level != 4) {
					return 7;
				}
				return 6;
			case 13:
				return 0 + level;
			case 14:
				return 1 + level;
			case 15:
				return 2 + level;
			case 0:
				return 3 + level;
			}
		case 4:
			switch (meta) {
			default:
				if (level != 8) {
					return 7;
				}
				return 6;
			case 1:
				return 0 + level;
			case 5:
				return 1 + level;
			case 9:
				return 2 + level;
			case 13:
				return 3 + level;
			}
		case 5:
			switch (meta) {
			default:
				if (level != 8) {
					return 7;
				}
				return 6;
			case 4:
				return 3 + level;
			case 8:
				return 2 + level;
			case 12:
				return 1 + level;
			case 0:
				return 0 + level;
			}
		}

		return add == 0 ? 7 : 6;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon( int par1, int par2 ) {
		return icons[0];
	}

	@Override
	public int idDropped( int meta, Random par2Random, int par3 ) {
		return ModBlocks.triniumBrick.blockID;
	}

	@Override
	public void onBlockAdded( World par1World, int par2, int par3, int par4 ) {
	}

	@Override
	public void onBlockPlacedBy( World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack ) {

	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {

		int i = 0;
		for (; i < 16; i++) {
			icons[i] = iconRegister.registerIcon(Zonus.Modname + "ser" + i);
		}
	}

	private void restoreBlocks( World par1World, int par2, int par3, int par4 ) {

		for (int yy = -3; yy <= 3; yy++) {
			for (int xx = -3; xx <= 3; xx++) {
				for (int zz = -3; zz <= 3; zz++) {
					int block = par1World.getBlockId(par2 + xx, par3 + yy, par4 + zz);
					par1World.getBlockMetadata(par2 + xx, par3 + yy, par4 + zz);
					if (block == blockID) {
						TileEntity tile = par1World.getBlockTileEntity(par2 + xx, par3 + yy, par4 + zz);
						if (tile != null && tile instanceof TEStearilliumReactor) {

							TEStearilliumReactor te = (TEStearilliumReactor) par1World.getBlockTileEntity(par2 + xx, par3 + yy, par4 + zz);

							if (te != null) {
								par1World.setBlock(par2 + xx, par3 + yy, par4 + zz, te.oldBlockId);
							}
						}
					}
				}
			}
		}
	}
}
