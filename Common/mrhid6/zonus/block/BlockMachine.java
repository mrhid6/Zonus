package mrhid6.zonus.block;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.tileEntity.TEMachineBase;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class BlockMachine extends BlockTexturedBase {

	public BlockMachine( int id, String texture, String name, boolean craftable ) {
		super(id, texture, name, craftable);
	}

	@Override
	public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are ) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking()) {
			return false;
		}

		// code to open gui explained later
		player.openGui(Zonus.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void onBlockAdded( World par1World, int par2, int par3, int par4 ) {
		super.onBlockAdded(par1World, par2, par3, par4);
		this.setDefaultDirection(par1World, par2, par3, par4);
	}

	@Override
	public void onBlockPlacedBy( World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack ) {

		TEMachineBase te = (TEMachineBase) par1World.getBlockTileEntity(par2, par3, par4);

		int yaw = MathHelper.floor_double(par5EntityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

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

	public void setDefaultDirection( World par1World, int par2, int par3, int par4 ) {
		if (!par1World.isRemote) {
			int var5 = par1World.getBlockId(par2, par3, par4 - 1);
			int var6 = par1World.getBlockId(par2, par3, par4 + 1);
			int var7 = par1World.getBlockId(par2 - 1, par3, par4);
			int var8 = par1World.getBlockId(par2 + 1, par3, par4);

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

			par1World.setBlockMetadataWithNotify(par2, par3, par4, var11, 0);
		}
	}
}
