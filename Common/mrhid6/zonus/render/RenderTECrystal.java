package mrhid6.zonus.render;

import java.util.Random;
import mrhid6.zonus.models.ModelCrystal;
import mrhid6.zonus.tileEntity.TECrystal;
import mrhid6.zonus.tileEntity.machine.TECrystalForge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;


public class RenderTECrystal extends TileEntitySpecialRenderer {
	ModelCrystal model = new ModelCrystal();
	public RenderTECrystal() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderTileEntityAt( TileEntity tileentity, double x, double y, double z, float f ) {
		if(tileentity == null || !(tileentity instanceof TECrystal))
			return;
		
		Random rand = new Random();
		float angle = 0;
		GL11.glPushMatrix();
		{
			GL11.glTranslatef((float) x, (float) y, (float) z);
			renderShard(1, (float)x, (float)y, (float)z, angle, angle, rand, 1,f);
		}
		GL11.glPopMatrix();

	}

	public void translateOrigin(float x,float y,float z,int ori){
		switch(ori){
		case 0:
			GL11.glTranslatef(0.5F, 1.3F, 0.5F);
			GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
			break;
		case 1:
			GL11.glTranslatef(0.5F, -0.3F, 0.5F);
			break;
		case 2:
			GL11.glTranslatef(0.5F, 0.5F, 1.3F);
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			break;
		case 3:
			GL11.glTranslatef(0.5F, 0.5F, -0.3F);
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			break;
		case 4:
			GL11.glTranslatef(1.3F, 0.5F, 0.5F);
			GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
			break;
		case 5:
			GL11.glTranslatef(-0.3F, 0.5F, 0.5F);
			GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
			break;
		}
	}

	public void renderShard(int ori, float x, float y, float z, float angle1, float angle2, Random rand, float shade, float partialTick){
		
		EntityPlayer p = Minecraft.getMinecraft().thePlayer;
		GL11.glEnable(2977);
		GL11.glEnable(3042);
		GL11.glPushMatrix();
		{
			GL11.glEnable(32826);
			GL11.glBlendFunc(770, 771);

			Tessellator tessellator = Tessellator.instance; 
			tessellator.setBrightness(220);
			translateOrigin(x,y,z,ori);
			float pulse = MathHelper.sin(p.ticksExisted / (8.0F)) * 0.03F;
			
			GL11.glPushMatrix();
			{
				//GL11.glRotatef(rot, 1.0F, 0, 0F);

				//translateOrigin(x,y,z,ori);
				GL11.glRotatef(angle1, 0, 1, 0);
				GL11.glRotatef(angle2, 1, 0, 0);
				GL11.glPushMatrix();
				{
					float size1 = 3.5F * 0.075F;
					float size2 = 5.5F * 0.1F;
					
					GL11.glColor4f(0.8F, 0.0F, 0.0F, 0.9F+pulse*4);
					GL11.glScalef(size1, size2, size1);
					bindTextureByName("/mods/zonus/textures/models/Crystal.png");
					model.render();
					GL11.glScalef(1, 1, 1);
					//ModLoader.getMinecraftInstance().renderEngine.bindTexture("");
				}
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();

			GL11.glDisable(32826);
		}
		GL11.glPopMatrix();
		GL11.glDisable(3024);
		GL11.glColor4f(1, 1, 1, 1);
	}

}
