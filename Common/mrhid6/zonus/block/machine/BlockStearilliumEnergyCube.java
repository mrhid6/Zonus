package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockMachine;
import mrhid6.zonus.tileEntity.TEMachineBase;
import mrhid6.zonus.tileEntity.TEStearilliumEnergyCube;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStearilliumEnergyCube extends BlockMachine {

	public BlockStearilliumEnergyCube( int id, String name ) {
		super(id, name, name, true);

		this.setResistance(6.0F);
		this.setHardness(6.0F);
		icons = new Icon[2];
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int par5, int par6 ) {

		TEStearilliumEnergyCube tile = (TEStearilliumEnergyCube) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.breakBlock();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return new TEStearilliumEnergyCube();
	}

	@Override
	public Icon getBlockTexture( IBlockAccess par1IBlockAccess, int x, int y, int z, int blockSide ) {

		int var6 = par1IBlockAccess.getBlockMetadata(x, y, z);
		return blockSide != var6 ? icons[0] : icons[1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata( int side, int meta ) {

		if (side == 3) {
			return icons[1];
		} else {
			return icons[0];
		}
	}

	@Override
	public void onBlockPlacedBy( World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack ) {

		int yaw = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int pitch = Math.round(par5EntityLiving.rotationPitch);

		TEMachineBase te = (TEMachineBase) par1World.getBlockTileEntity(par2, par3, par4);

		if (pitch >= 65) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1, 0);
			te.setFacing((short) 1);
		} else if (pitch <= -65) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 0);
			te.setFacing((short) 0);
		} else {
			if (yaw == 0) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 0);
				te.setFacing((short) 2);
			}

			if (yaw == 1) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 0);
				te.setFacing((short) 5);
			}

			if (yaw == 2) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 0);
				te.setFacing((short) 3);
			}

			if (yaw == 3) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 0);
				te.setFacing((short) 4);
			}
		}
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		icons[0] = iconRegister.registerIcon(Zonus.Modname + "secube_side");
		icons[1] = iconRegister.registerIcon(Zonus.Modname + "secube_front");
	}

	@Override
	public void setDefaultDirection( World par1World, int par2, int par3, int par4 ) {
		if (!par1World.isRemote) {
			int var5 = par1World.getBlockId(par2, par3, par4 - 1);
			int var6 = par1World.getBlockId(par2, par3, par4 + 1);
			int var7 = par1World.getBlockId(par2 - 1, par3, par4);
			int var8 = par1World.getBlockId(par2 + 1, par3, par4);
			int var9 = par1World.getBlockId(par2, par3 - 1, par4);
			int var10 = par1World.getBlockId(par2, par3 + 1, par4);

			byte var11 = 3;

			if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6]) {
				var11 = 3;
			}

			if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5]) {
				var11 = 2;
			}

			if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8]) {
				var11 = 5;
			}

			if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7]) {
				var11 = 4;
			}

			if (Block.opaqueCubeLookup[var9] && !Block.opaqueCubeLookup[var10]) {
				var11 = 1;
			}

			if (Block.opaqueCubeLookup[var10] && !Block.opaqueCubeLookup[var9]) {
				var11 = 0;
			}

			par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 0);
		}
	}
}
