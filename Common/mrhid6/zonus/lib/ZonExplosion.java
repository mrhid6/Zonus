package mrhid6.zonus.lib;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
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

		int i;
		int j;
		int k;
		double d0;
		double d1;
		double d2;
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

		this.explosionSize *= 2.0F;
		i = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
		j = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
		k = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
		int l1 = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
		int i2 = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
		int j2 = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getAABBPool().getAABB((double)i, (double)k, (double)i2, (double)j, (double)l1, (double)j2));
		Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);

		for (int k2 = 0; k2 < list.size(); ++k2)
		{
			Entity entity = (Entity)list.get(k2);
			double d7 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

			if (d7 <= 1.0D)
			{
				d0 = entity.posX - this.explosionX;
				d1 = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
				d2 = entity.posZ - this.explosionZ;
				double d8 = (double)MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);

				if (d8 != 0.0D)
				{
					d0 /= d8;
					d1 /= d8;
					d2 /= d8;
					double d9 = (double)this.worldObj.getBlockDensity(vec3, entity.boundingBox);
					double d10 = (1.0D - d7) * d9;
					entity.attackEntityFrom(DamageSource.setExplosionSource(new Explosion(worldObj, entity, explosionX, explosionY, explosionZ, explosionSize)), (int)((d10 * d10 + d10) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D));
					double d11 = EnchantmentProtection.func_92092_a(entity, d10);
					entity.motionX += d0 * d11;
					entity.motionY += d1 * d11;
					entity.motionZ += d2 * d11;
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
