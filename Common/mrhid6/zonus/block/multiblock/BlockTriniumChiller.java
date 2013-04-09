package mrhid6.zonus.block.multiblock;

import java.util.Random;
import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockMachine;
import mrhid6.zonus.block.ModBlocks;
import mrhid6.zonus.tileEntity.multiblock.TETriniumChillerBase;
import mrhid6.zonus.tileEntity.multiblock.TETriniumChillerCore;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTriniumChiller extends BlockMachine {

	public BlockTriniumChiller( int id, String name ) {

		super(id, name, name, false);

		icons = new Icon[80];
	}

	@Override
	public void breakBlock( World par1World, int par2, int par3, int par4, int par5, int par6 ) {
		if (par1World.getBlockMetadata(par2, par3, par4) == 0) {
			restoreBlocks(par1World, par2, par3, par4);
		}

		for (int yy = -1; yy <= 1; yy++) {
			for (int xx = -1; xx <= 1; xx++) {
				for (int zz = -1; zz <= 1; zz++) {
					par1World.notifyBlocksOfNeighborChange(par2 + xx, par3 + yy, par4 + zz, blockID);
				}
			}
		}
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	public int calculateLevel( IBlockAccess world, int x, int y, int z ) {
		int meta = world.getBlockMetadata(x, y, z);
		int metaA = world.getBlockMetadata(x, y + 1, z);
		if ((metaA == 10) || (metaA == 0)) {
			metaA = meta;
		}
		int metaB = world.getBlockMetadata(x, y - 1, z);
		if ((metaB == 10) || (metaB == 0)) {
			metaB = meta;
		}
		int blockA = world.getBlockId(x, y + 1, z);
		int blockB = world.getBlockId(x, y - 1, z);
		if ((meta == metaA) && (meta == metaB) && (blockID == blockA) && (blockID == blockB)) {
			return 16;
		}
		if ((meta == metaA) && (blockID == blockA) && ((meta != metaB) || (blockID != blockB))) {
			return 32;
		}
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return null;
	}

	@Override
	public TileEntity createTileEntity( World world, int metadata ) {
		if (metadata == 5) {
			return new TETriniumChillerCore();
		}
		// return super.createNewTileEntity(world, metadata);
		return new TETriniumChillerBase();
	}

	@Override
	public Icon getBlockTexture( IBlockAccess world, int x, int y, int z, int side ) {

		int index = getBlockTextureInt(world, x, y, z, side);
		return (index == -1) ? icons[6] : icons[getBlockTextureInt(world, x, y, z, side)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata( int par1, int par2 ) {
		return icons[20];
	}

	public int getBlockTextureInt( IBlockAccess world, int x, int y, int z, int side ) {

		// return this.blockIcon;

		int meta = world.getBlockMetadata(x, y, z);
		int level = calculateLevel(world, x, y, z);
		int add = 0;
		if (isFrontFacing(world, x, y, z, 10, side)) {
			add = 3;
		}
		switch (side) {
		case 0:
		case 1:
			if ((side == 1) && (level == 32)) {
				switch (meta) {
				case 2:
					return 23;
				case 4:
					return 24;
				case 6:
					return 40;
				case 8:
					return 39;
				case 3:
				case 5:
				case 7:
				}
			}
			if (add != 3) {
				if (meta == 5 || meta == 11) {
					return 7;
				}
				return (meta - 1) % 3 + (meta - 1) / 3 * 16;
			}
			break;
		case 2:
			switch (meta) {
			default:
				if (level != 16) {
					return 7;
				}
				return 6;
			case 1:
				return 2 + level + add;
			case 2:
				return 1 + level + add;
			case 3:
				return 0 + level + add;
			case 10:
				return 20;
			}
		case 3:
			switch (meta) {
			default:
				if (level != 16) {
					return 7;
				}
				return 6;
			case 7:
				return 0 + level + add;
			case 8:
				return 1 + level + add;
			case 9:
				return 2 + level + add;
			case 10:
				return 20;
			}
		case 4:
			switch (meta) {
			default:
				if (level != 16) {
					return 7;
				}
				return 6;
			case 1:
				return 0 + level + add;
			case 4:
				return 1 + level + add;
			case 7:
				return 2 + level + add;
			case 10:
				return 20;
			}
		case 5:
			switch (meta) {
			default:
				if (level != 16) {
					return 7;
				}
				return 6;
			case 3:
				return 2 + level + add;
			case 6:
				return 1 + level + add;
			case 9:
				return 0 + level + add;
			case 10:
				return 20;
			}
		}

		return add == 0 ? 7 : 6;
	}

	@Override
	public int idDropped( int meta, Random par2Random, int par3 ) {

		if (((meta % 2 == 0) || (meta == 5) || (meta == 11) || (par3 == 0)) && (meta != 10)) {
			return ModBlocks.stearilliumStone.blockID;
		} else if (meta == 10) {
			return Block.fenceIron.blockID;
		} else {
			return ModBlocks.triniumBrick.blockID;
		}
	}

	public boolean isFrontFacing( IBlockAccess world, int x, int y, int z, int md, int side ) {
		if (((side > 3) && (world.getBlockId(x, y, z + 1) == blockID) && (world.getBlockMetadata(x, y, z + 1) == md)) || ((side > 3) && (world.getBlockId(x, y, z - 1) == blockID) && (world.getBlockMetadata(x, y, z - 1) == md)) || ((side > 1) && (side < 4) && (world.getBlockId(x + 1, y, z) == blockID) && (world.getBlockMetadata(x + 1, y, z) == md)) || ((side > 1) && (side < 4) && (world.getBlockId(x - 1, y, z) == blockID) && (world.getBlockMetadata(x - 1, y, z) == md)) || ((side > 1) && (world.getBlockId(x, y + 1, z) == blockID) && (world.getBlockMetadata(x, y + 1, z) == md)) || ((side > 1) && (world.getBlockId(x, y - 1, z) == blockID) && (world.getBlockMetadata(x, y - 1, z) == md))) {
			return true;
		}
		if (((side > 3) && (world.getBlockId(x, y + 1, z + 1) == blockID) && (world.getBlockMetadata(x, y + 1, z + 1) == md)) || ((side > 3) && (world.getBlockId(x, y + 1, z - 1) == blockID) && (world.getBlockMetadata(x, y + 1, z - 1) == md)) || ((side > 1) && (side < 4) && (world.getBlockId(x + 1, y + 1, z) == blockID) && (world.getBlockMetadata(x + 1, y + 1, z) == md)) || ((side > 1) && (side < 4) && (world.getBlockId(x - 1, y + 1, z) == blockID) && (world.getBlockMetadata(x - 1, y + 1, z) == md))) {
			return true;
		}
		if (((side > 3) && (world.getBlockId(x, y - 1, z + 1) == blockID) && (world.getBlockMetadata(x, y - 1, z + 1) == md)) || ((side > 3) && (world.getBlockId(x, y - 1, z - 1) == blockID) && (world.getBlockMetadata(x, y - 1, z - 1) == md)) || ((side > 1) && (side < 4) && (world.getBlockId(x + 1, y - 1, z) == blockID) && (world.getBlockMetadata(x + 1, y - 1, z) == md)) || ((side > 1) && (side < 4) && (world.getBlockId(x - 1, y - 1, z) == blockID) && (world.getBlockMetadata(x - 1, y - 1, z) == md))) {
			return true;
		}

		return false;
	}

	@Override
	public void onBlockAdded( World par1World, int par2, int par3, int par4 ) {
	}

	@Override
	public void onBlockPlacedBy( World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack ) {

	}

	@Override
	public void onNeighborBlockChange( World par1World, int par2, int par3, int par4, int par5 ) {
		int meta = par1World.getBlockMetadata(par2, par3, par4);

		if (meta == 0) {
			for (int yy = -1; yy <= 1; yy++) {
				for (int xx = -1; xx <= 1; xx++) {
					for (int zz = -1; (zz <= 1) && (((yy != 0)) || (zz != 0) || (xx != 0)); zz++) {
						int block = par1World.getBlockId(par2 + xx, par3 + yy, par4 + zz);
						if (block != blockID) {
							restoreBlocks(par1World, par2, par3, par4);
							par1World.setBlock(par2, par3, par4, 0);
							par1World.notifyBlocksOfNeighborChange(par2, par3, par4, par1World.getBlockId(par2, par3, par4));
							par1World.markBlockForUpdate(par2, par3, par4);
							return;
						}
					}
				}
			}
		}

		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {

		int i = 0;
		for (; i < 6; i++) {
			icons[i] = iconRegister.registerIcon(Zonus.Modname + "tc" + i);
		}
		icons[i] = iconRegister.registerIcon(Zonus.Modname + "triniumbrick");
		++i;
		icons[i] = iconRegister.registerIcon(Zonus.Modname + "tc17");
		i += 8;

		for (; i < 22; i++) {
			icons[i] = iconRegister.registerIcon(Zonus.Modname + "tc" + i);
		}
		i += 10;
		for (; i < 38; i++) {
			icons[i] = iconRegister.registerIcon(Zonus.Modname + "tc" + i);
		}
	}

	private void restoreBlocks( World par1World, int par2, int par3, int par4 ) {
		for (int yy = -1; yy <= 1; yy++) {
			for (int xx = -1; xx <= 1; xx++) {
				for (int zz = -1; zz <= 1; zz++) {
					int block = par1World.getBlockId(par2 + xx, par3 + yy, par4 + zz);
					int md = par1World.getBlockMetadata(par2 + xx, par3 + yy, par4 + zz);
					if (block == blockID) {
						block = idDropped(md, new Random(), yy);
						par1World.setBlock(par2 + xx, par3 + yy, par4 + zz, block);

						par1World.notifyBlocksOfNeighborChange(par2 + xx, par3 + yy, par4 + zz, par1World.getBlockId(par2 + xx, par3 + yy, par4 + zz));
						par1World.markBlockForUpdate(par2 + xx, par3 + yy, par4 + zz);
					}
				}
			}
		}
	}
}