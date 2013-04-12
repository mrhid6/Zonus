package mrhid6.zonus.block.machine;

import mrhid6.zonus.Zonus;
import mrhid6.zonus.block.BlockMachine;
import mrhid6.zonus.render.BRZoroChest;
import mrhid6.zonus.tileEntity.machine.TEZoroChest;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockZoroChest extends BlockMachine {

	public BlockZoroChest( int id, String textureName, String name ) {
		super(id, textureName, name, true);

		this.setResistance(2.0F);
		this.setHardness(2.0F);
		// setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

	@Override
	public void breakBlock( World world, int x, int y, int z, int par5, int par6 ) {

		TEZoroChest tile = (TEZoroChest) world.getBlockTileEntity(x, y, z);

		if (tile != null) {
			tile.breakBlock();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity( World var1 ) {
		return new TEZoroChest();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTextureFromSideAndMetadata( int side, int meta ) {
		return blockIcon;
	}


	@Override
	public int getRenderType() {
		return BRZoroChest.renderID;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are ) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking()) {
			return false;
		}

		player.openGui(Zonus.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void onBlockPlacedBy( World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemStack ) {
		byte chestFacing = 0;
		int facing = MathHelper.floor_double((entityliving.rotationYaw * 4F) / 360F + 0.5D) & 3;
		if (facing == 0) {
			chestFacing = 2;
		}
		if (facing == 1) {
			chestFacing = 5;
		}
		if (facing == 2) {
			chestFacing = 3;
		}
		if (facing == 3) {
			chestFacing = 4;
		}
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te != null && te instanceof TEZoroChest) {
			((TEZoroChest) te).setFacing(chestFacing);
			world.markBlockForUpdate(i, j, k);
		}
	}

	@Override
	public void registerIcons( IconRegister iconRegister ) {
		blockIcon = iconRegister.registerIcon(Zonus.Modname + "machine");
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

}
