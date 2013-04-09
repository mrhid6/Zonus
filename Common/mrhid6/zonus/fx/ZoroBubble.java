package mrhid6.zonus.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ZoroBubble extends EntityFX {

	public ZoroBubble( World par1World, double x, double y, double z ) {
		super(par1World, x, y, z, 0, 0, 0);

		particleRed = 0.1F;
		particleGreen = 0.1F;
		particleBlue = 0.1F;
		particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
		this.setParticleTextureIndex(49);

		particleScale = rand.nextFloat() * 1.0F + 0.2F;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03D;
		this.moveEntity(motionX, motionY, motionZ);
		motionX *= 0.6990000128746033D;
		motionY *= 0.6990000128746033D;
		motionZ *= 0.6990000128746033D;

		if (particleMaxAge-- <= 0) {
			this.setDead();
		}
	}

}
