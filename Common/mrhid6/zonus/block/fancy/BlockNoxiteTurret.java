package mrhid6.zonus.block.fancy;

import java.util.List;
import mrhid6.zonus.block.BlockTexturedBase;
import mrhid6.zonus.tileEntity.TENoxiteTurret;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockNoxiteTurret extends BlockTexturedBase {

	public BlockNoxiteTurret( int id, String name ) {
		super(id, name, name, true);
	}

	@Override
	public void addCollisionBoxesToList( World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity ) {
		setBlockBounds(0F, 0, -0.8F, 1, 1.5F, 1.8F);

		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public TileEntity createTileEntity( World world, int metadata ) {
		return new TENoxiteTurret();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool( World world, int x, int y, int z ) {

		double minX = x;
		double minY = y;
		double minZ = z - 0.8F;
		double maxX = x + 1;
		double maxY = y + 1.5F;
		double maxZ = z + 1.8F;

		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public boolean hasTileEntity( int metadata ) {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockPlacedBy( World world, int i, int j, int k, EntityLiving entityliving, ItemStack itemStack ) {
		byte chestFacing = 0;
		int facing = MathHelper.floor_double((entityliving.rotationYaw * 4F) / 360F + 0.5D) & 3;
		if (facing == 0) {
			chestFacing = 3;
		}
		if (facing == 1) {
			chestFacing = 2;
		}
		if (facing == 2) {
			chestFacing = 1;
		}
		if (facing == 3) {
			chestFacing = 0;
		}
		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te != null && te instanceof TENoxiteTurret) {
			((TENoxiteTurret) te).setFacing(chestFacing);
			world.markBlockForUpdate(i, j, k);
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState( IBlockAccess world, int x, int y, int z ) {

		setBlockBounds(0F, 0, -0.8F, 1, 1.5F, 1.8F);
	}

}
