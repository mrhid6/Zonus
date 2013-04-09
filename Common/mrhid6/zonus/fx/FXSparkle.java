package mrhid6.zonus.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FXSparkle extends EntityFX {

	int particle = (int) (Math.random() * 4 + 16);

	public FXSparkle( World par1World, double x, double y, double z ) {
		super(par1World, x, y, z, 0, 0, 0);

		particleRed = 1F;
		particleGreen = 1F;
		particleBlue = 1F;
		particleMaxAge = 15;

		particleScale = rand.nextFloat() * 1.0F + 0.8F;
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		this.moveEntity(motionX, motionY, motionZ);
		motionY *= 0.7080000019073486D;
		motionX *= 0.2080000019073486D;
		motionZ *= 0.2080000019073486D;

		if (particleMaxAge-- <= 0) {
			this.setDead();
		}
	}

	@Override
	public void renderParticle( Tessellator par1Tessellator, float f, float f1, float f2, float f3, float f4, float f5 ) {
		par1Tessellator.draw();
		GL11.glPushMatrix();
		{
			GL11.glDepthMask(false);
			GL11.glEnable(3042);
			 GL11.glBlendFunc(770, 1);

			GL11.glBindTexture(3553, ModLoader.getMinecraftInstance().renderEngine.getTexture("/mods/zonus/textures/particles/particles.png"));
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 170F, 170F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1);
			float var8 = particle % 8 / 8.0F;
			float var9 = var8 + 0.124875F;
			float var10 = particle / 8 / 8.0F;
			float var11 = var10 + 0.124875F;
			float var12 = 0.1F * particleScale;

			float var13 = (float) (prevPosX + (posX - prevPosX) * f - interpPosX);
			float var14 = (float) (prevPosY + (posY - prevPosY) * f - interpPosY);
			float var15 = (float) (prevPosZ + (posZ - prevPosZ) * f - interpPosZ);
			float var16 = 1.0F;
			par1Tessellator.setBrightness(250);
			par1Tessellator.startDrawingQuads();

			par1Tessellator.setColorRGBA_F(particleRed * var16, particleGreen * var16, particleBlue * var16, particleAlpha);
			par1Tessellator.addVertexWithUV(var13 - f1 * var12 - f4 * var12, var14 - f2 * var12, var15 - f3 * var12 - f5 * var12, var9, var11);
			par1Tessellator.addVertexWithUV(var13 - f1 * var12 + f4 * var12, var14 + f2 * var12, var15 - f3 * var12 + f5 * var12, var9, var10);
			par1Tessellator.addVertexWithUV(var13 + f1 * var12 + f4 * var12, var14 + f2 * var12, var15 + f3 * var12 + f5 * var12, var8, var10);
			par1Tessellator.addVertexWithUV(var13 + f1 * var12 - f4 * var12, var14 - f2 * var12, var15 + f3 * var12 - f5 * var12, var8, var11);

			par1Tessellator.draw();
			GL11.glDisable(3042);
			GL11.glDepthMask(true);
		}
		GL11.glPopMatrix();
		GL11.glBindTexture(3553, ModLoader.getMinecraftInstance().renderEngine.getTexture("/particles.png"));

		par1Tessellator.startDrawingQuads();

	}

}
