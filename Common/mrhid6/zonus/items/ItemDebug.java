package mrhid6.zonus.items;

import java.util.List;
import java.util.Random;
import mrhid6.zonus.entities.EntityTitan;
import mrhid6.zonus.tileEntity.TECableBase;
import mrhid6.zonus.tileEntity.TEPoweredBase;
import mrhid6.zonus.tileEntity.machine.TEZoroController;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemDebug extends ItemTexturedBase {

	public ItemDebug( int id, String name ) {
		super(id, 1, name);
	}

	@Override
	public ItemStack onItemRightClick( ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer ) {

		float f = 1.0F;
		float f1 = par3EntityPlayer.prevRotationPitch + (par3EntityPlayer.rotationPitch - par3EntityPlayer.prevRotationPitch) * f;
		float f2 = par3EntityPlayer.prevRotationYaw + (par3EntityPlayer.rotationYaw - par3EntityPlayer.prevRotationYaw) * f;
		double d0 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * f;
		double d1 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * f + 1.62D - par3EntityPlayer.yOffset;
		double d2 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * f;
		Vec3 vec3 = par2World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		MovingObjectPosition movingobjectposition = par2World.rayTraceBlocks_do(vec3, vec31, true);

		if (movingobjectposition == null) {
			return par1ItemStack;
		} else {
			Vec3 vec32 = par3EntityPlayer.getLook(f);
			boolean flag = false;
			float f9 = 1.0F;
			List list = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, par3EntityPlayer.boundingBox.addCoord(vec32.xCoord * d3, vec32.yCoord * d3, vec32.zCoord * d3).expand(f9, f9, f9));
			int i;

			for (i = 0; i < list.size(); ++i) {
				Entity entity = (Entity) list.get(i);

				if (entity.canBeCollidedWith()) {
					float f10 = entity.getCollisionBorderSize();
					AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f10, f10, f10);
					if (axisalignedbb.isVecInside(vec3)) {
						flag = true;
					}
				}
			}
			if (flag) {
				return par1ItemStack;
			} else {
				if (movingobjectposition.typeOfHit == EnumMovingObjectType.TILE) {
					i = movingobjectposition.blockX;
					int j = movingobjectposition.blockY;
					int k = movingobjectposition.blockZ;
					if (par2World.getBlockId(i, j, k) == Block.snow.blockID) {
						--j;
					}

					EntityTitan entitytitan = new EntityTitan(par2World, i + 0.5F, j + 1F, k + 0.5F);
					entitytitan.rotationYaw = ((MathHelper.floor_double(par3EntityPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) - 1) * 90;

					if (!par2World.getCollidingBoundingBoxes(entitytitan, entitytitan.boundingBox.expand(-0.1D, -0.1D, -0.1D)).isEmpty()) {
						return par1ItemStack;
					}
					if (!par2World.isRemote) {
						par2World.spawnEntityInWorld(entitytitan);
					}

					if (!par3EntityPlayer.capabilities.isCreativeMode) {
						--par1ItemStack.stackSize;
					}
				}

				return par1ItemStack;
			}
		}

	}

	@Override
	public boolean onItemUseFirst( ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ ) {

		Random rand = new Random();

		if (!world.blockExists(x, y, z) || world.isAirBlock(x, y, z)) {
			return false; }


		TileEntity te = world.getBlockTileEntity(x, y, z);

		if (te instanceof TEZoroController) {

			System.out.println(((TEZoroController) te).gridindex); } 
		else if (te instanceof TECableBase) { 
			System.out.println(((TECableBase)te).gridindex);

		} else if (te instanceof TEPoweredBase) {
			System.out.println(((TEPoweredBase) te).gridindex);

		}


		return true;
	}
}
