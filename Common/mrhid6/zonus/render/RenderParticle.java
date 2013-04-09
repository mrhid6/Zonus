package mrhid6.zonus.render;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.ModLoader;
import org.lwjgl.opengl.GL11;

public class RenderParticle {

	public static void render( double x, double y, double z, double s, Tessellator t ) {

		// System.out.println("render Partical!");
		x -= EntityFX.interpPosX;
		y -= EntityFX.interpPosY;
		z -= EntityFX.interpPosZ;
		// TODO: check

		float par3 = ActiveRenderInfo.rotationX;
		float par4 = ActiveRenderInfo.rotationXZ;
		float par5 = ActiveRenderInfo.rotationZ;
		float par6 = ActiveRenderInfo.rotationYZ;
		float par7 = ActiveRenderInfo.rotationXY;

		/*
		 * t.setColorRGBA(0, 0, 0, 1); t.addVertexWithUV((x - par3 * s - par6 *
		 * s), (y - par4 * s), (z - par5 * s - par7 * s), u2, v2);
		 * t.addVertexWithUV((x - par3 * s + par6 * s), (y + par4 * s), (z -
		 * par5 * s + par7 * s), u2, v1); t.addVertexWithUV((x + par3 * s + par6
		 * * s), (y + par4 * s), (z + par5 * s + par7 * s), u1, v1);
		 * t.addVertexWithUV((x + par3 * s - par6 * s), (y - par4 * s), (z +
		 * par5 * s - par7 * s), u1, v2);
		 */

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glBindTexture(3553, ModLoader.getMinecraftInstance().renderEngine.getTexture("/mods/xorbo/textures/particles/particles.png"));

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
		float var8 = 16 % 8 / 8.0F;
		float var9 = var8 + 0.124875F;
		float var10 = 16 / 8 / 8.0F;
		float var11 = var10 + 0.124875F;

		/*
		 * float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) *
		 * (double)f - interpPosX); float var14 = (float)(this.prevPosY +
		 * (this.posY - this.prevPosY) * (double)f - interpPosY); float var15 =
		 * (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)f -
		 * interpPosZ); float var16 = 1.0F;
		 */

		t.startDrawing(7);

		t.setColorRGBA_F(1, 1, 1, 1);
		/*
		 * par1Tessellator.addVertexWithUV(var13 - f1 * var12 - f4 * var12,
		 * var14 - f2 * var12, var15 - f3 * var12 - f5 * var12, var9, var11);
		 * par1Tessellator.addVertexWithUV(var13 - f1 * var12 + f4 * var12,
		 * var14 + f2 * var12, var15 - f3 * var12 + f5 * var12, var9, var10);
		 * par1Tessellator.addVertexWithUV(var13 + f1 * var12 + f4 * var12,
		 * var14 + f2 * var12, var15 + f3 * var12 + f5 * var12, var8, var10);
		 * par1Tessellator.addVertexWithUV(var13 + f1 * var12 - f4 * var12,
		 * var14 - f2 * var12, var15 + f3 * var12 - f5 * var12, var8, var11);
		 */

		t.addVertexWithUV((x - par3 * s - par6 * s), (y - par4 * s), (z - par5 * s - par7 * s), var9, var11);
		t.addVertexWithUV((x - par3 * s + par6 * s), (y + par4 * s), (z - par5 * s + par7 * s), var9, var10);
		t.addVertexWithUV((x + par3 * s + par6 * s), (y + par4 * s), (z + par5 * s + par7 * s), var8, var10);
		t.addVertexWithUV((x + par3 * s - par6 * s), (y - par4 * s), (z + par5 * s - par7 * s), var8, var11);

		t.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glBindTexture(3553, ModLoader.getMinecraftInstance().renderEngine.getTexture("/particles.png"));
	}
}
