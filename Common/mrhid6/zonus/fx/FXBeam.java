package mrhid6.zonus.fx;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class FXBeam extends EntityFX {
	double radius;
	public FXBeam( World world, double x, double y, double z, double radius) {
		super(world, x, y, z, 0, 0, 0);

		particleMaxAge = 10;
		this.radius = radius*0.7D;
	}



	private void handleAging() {
		particleAge += 1;
		if (particleAge >= particleMaxAge) {
			setDead();
		}
	}

	@Override
	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionX = 0;
		motionY = 0;
		motionZ = 0;
		this.moveEntity(motionX, motionY, motionZ);
		motionY = 0;
		motionX = 0;
		motionZ = 0;

		handleAging();
	}

	@Override
	public void renderParticle( Tessellator tessellator, float a, float b, float c, float d, float e, float f ) {
		tessellator.draw();
		
		GL11.glPushMatrix();
		{
			Minecraft.getMinecraft().renderEngine.bindTexture("/misc/beam.png");
			float x = (float) (prevPosX + (posX - prevPosX) * a - interpPosX);
			float y = (float) (prevPosY + (posY - prevPosY) * a - interpPosY);
			float z = (float) (prevPosZ + (posZ - prevPosZ) * a - interpPosZ);	

			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			//GL11.glDisable(GL11.GL_BLEND);
			//GL11.glDepthMask(true);
			//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			
			GL11.glDepthMask(false);
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 1);
			
			renderBeam(tessellator,x,y,z,0.25F,0);
			renderBeam(tessellator,x,y,z,0.045F,0.15F);
			
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDepthMask(false);
			
			GL11.glDisable(3042);
			GL11.glDepthMask(true);
		}
		GL11.glPopMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture("/particles.png");
		tessellator.startDrawingQuads();
	}
	
	public void renderBeam(Tessellator tessellator, float x,float y,float z, float alpha, float radiusMod){
		
		float f1 = 1.0F;
		float f2 = (float)worldObj.getTotalWorldTime();
		float f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
		byte b0 = 1;
		double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);

		double d4 = (double)b0 * (radius + radiusMod); //radius
		double d5 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d4;
		double d6 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d4;
		double d7 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d4;
		double d8 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d4;
		double d9 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d4;
		double d10 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d4;
		double d11 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d4;
		double d12 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d4;
		double d13 = (double)(8 * f1); // size
		double d14 = 0.0D;
		double d15 = 1.0D;
		double d16 = (double)(-1.0F + f3);
		double d17 = (double)(256.0F * f1) * (0.5D / d4) + d16;
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 190F, 190F);
		tessellator.setBrightness(250);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, alpha);
		tessellator.addVertexWithUV(x + d5, y + d13, z + d6, d15, d17);
		tessellator.addVertexWithUV(x + d5, y, z + d6, d15, d16);
		tessellator.addVertexWithUV(x + d7, y, z + d8, d14, d16);
		tessellator.addVertexWithUV(x + d7, y + d13, z + d8, d14, d17);
		tessellator.addVertexWithUV(x + d11, y + d13, z + d12, d15, d17);
		tessellator.addVertexWithUV(x + d11, y, z + d12, d15, d16);
		tessellator.addVertexWithUV(x + d9, y, z + d10, d14, d16);
		tessellator.addVertexWithUV(x + d9, y + d13, z + d10, d14, d17);
		tessellator.addVertexWithUV(x + d7, y + d13, z + d8, d15, d17);
		tessellator.addVertexWithUV(x + d7, y, z + d8, d15, d16);
		tessellator.addVertexWithUV(x + d11, y, z + d12, d14, d16);
		tessellator.addVertexWithUV(x + d11, y + d13, z + d12, d14, d17);
		tessellator.addVertexWithUV(x + d9, y + d13, z + d10, d15, d17);
		tessellator.addVertexWithUV(x + d9, y, z + d10, d15, d16);
		tessellator.addVertexWithUV(x + d5, y, z + d6, d14, d16);
		tessellator.addVertexWithUV(x + d5, y + d13, z + d6, d14, d17);
		tessellator.draw();
	}
	
	public void updateStuff(){
		particleAge-=8;
	}
}