package mrhid6.zonus.lib;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ZonExplosion {

	public boolean doDrops = false;
	private float explosionSize;
	private double explosionX;
	private double explosionY;
	private double explosionZ;
	private World worldObj;

	public ZonExplosion( World par1World, double par3, double par5, double par7, float par9 ) {
		worldObj = par1World;
		explosionSize = par9;
		explosionX = par3;
		explosionY = par5;
		explosionZ = par7;

	}

	public void doExplosion() {
		double radius = explosionSize / 2;

		for (double X = -radius; X < radius; X++) {
			for (double Y = -radius; Y < radius; Y++) {
				for (double Z = -radius; Z < radius; Z++) {
					if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {

						int blockId = worldObj.getBlockId((int) (explosionX + X), (int) (explosionY + Y), (int) (explosionZ + Z));

						if (doDrops && worldObj.rand.nextInt(1000) < 100 && blockId != Block.bedrock.blockID) {
							List<ItemStack> stacks = Utils.getItemStackFromBlock(worldObj, (int) (explosionX + X), (int) (explosionY + Y), (int) (explosionZ + Z));

							if (stacks != null) {
								for (ItemStack s : stacks) {
									if (s != null) {
										dropStack(s, explosionX + X, explosionY + Y, explosionZ + Z);
									}
								}
							}

						}
						if (blockId != Block.bedrock.blockID) {
							worldObj.setBlock((int) (explosionX + X), (int) (explosionY + Y), (int) (explosionZ + Z), 0);
						}
					}
				}
			}
		}

		worldObj.playSoundEffect(explosionX, explosionY, explosionZ, "random.explode", 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
		worldObj.spawnParticle("largeexplode", explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);

	}

	private void dropStack( ItemStack stack, double x, double y, double z ) {

		if (stack.stackSize > 0) {
			float f = worldObj.rand.nextFloat() * 0.8F + 0.1F;
			float f1 = worldObj.rand.nextFloat() * 0.8F + 0.1F;
			float f2 = worldObj.rand.nextFloat() * 0.8F + 0.1F;

			EntityItem entityitem = new EntityItem(worldObj, x + f, y + f1 + 0.5F, z + f2, stack);

			entityitem.lifespan = 5200;
			entityitem.delayBeforeCanPickup = 10;

			float f3 = 0.05F;
			entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
			entityitem.motionY = (float) worldObj.rand.nextGaussian() * f3 + 0.5F;
			entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
		}
	}

}
